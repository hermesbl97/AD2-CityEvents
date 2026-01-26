package com.svalero.cityEvents;

import com.svalero.cityEvents.domain.Event;
import com.svalero.cityEvents.domain.Location;
import com.svalero.cityEvents.dto.EventOutDto;
import com.svalero.cityEvents.repository.EventRepository;
import com.svalero.cityEvents.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EventServiceTests {

    @InjectMocks
    private EventService eventService;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testFindAll() {
        List<Event> mockEventList = List.of(
            new Event(1,"Campanadas Año nuevo", "Un año más las campanadas tendrán lugar en la Plaza del pilar",
                    LocalDate.now(), "Evento", 2500, 32, true, null, null, null ),
            new Event(2, "Final Copa Casademont", "Partido femenino en el que el Caseademont Zaragoza puede ganar",
                    LocalDate.now(), "Deporte", 12000, 25, true, null, null, null),
            new Event(3, "Panorama desde el Puente", "Obra de teatro novedosa",
                    LocalDate.of(2025,1,2), "Evento", 300, 25, false, null, null, null)
        );

        List<EventOutDto> mockEventOutDto = List.of(
            new EventOutDto(1,"Campanadas Año nuevo", LocalDate.now(), "Evento", 32),
            new EventOutDto(2, "Final Copa Casademont", LocalDate.now(), "Deporte", 25),
            new EventOutDto(3, "Panorama desde el Puente", LocalDate.of(2025,1,2), "Evento", 25)
        );

        when(eventRepository.findAll()).thenReturn(mockEventList);
        when(modelMapper.map(mockEventList, new TypeToken<List<EventOutDto>>() {}.getType())).thenReturn(mockEventOutDto);

        List<EventOutDto> actualEventList = eventService.findAll("", "", null);
        assertEquals(3, actualEventList.size()); //comprobamos que el tamaño del listado que nos devuelve es de tres
        assertEquals("Evento", actualEventList.getFirst().getCategory());

        verify(eventRepository, times(1)).findAll(); //comprobamos que se haya llamado una vez al método findAll.
        verify(eventRepository, times(0)).findByCategory(""); //comprobamos que no se haya llamado al método por categoría
    }

    @Test
    public void testFindAllByCategory() {
        List<Event> mockEventList = List.of(
            new Event(1,"Campanadas Año nuevo", "Un año más las campanadas tendrán lugar en la Plaza del pilar",
                    LocalDate.now(), "Evento", 2500, 32, true, null, null, null ),
            new Event(2, "Final Copa Casademont", "Partido femenino en el que el Caseademont Zaragoza puede ganar",
                    LocalDate.now(), "Deporte", 12000, 25, true, null, null, null),
            new Event(3, "Panorama desde el Puente", "Obra de teatro novedosa",
                    LocalDate.of(2025,1,2), "Evento", 300, 25, false, null, null, null)
        );

        List<EventOutDto> mockModelMapperOut = List.of(
            new EventOutDto(1,"Campanadas Año nuevo", LocalDate.now(), "Evento", 32),
            new EventOutDto(3, "Panorama desde el Puente", LocalDate.of(2025,1,2), "Evento", 18)
        );

        when(eventRepository.findByCategory("Evento")).thenReturn(mockEventList);
        when(modelMapper.map(mockEventList, new TypeToken<List<EventOutDto>>() {}.getType())).thenReturn(mockModelMapperOut);

        List<EventOutDto> actualEventList = eventService.findAll("Evento", "", null);
        assertEquals(2, actualEventList.size());
        assertEquals("Campanadas Año nuevo", actualEventList.getFirst().getName());

        verify(eventRepository, times(0)).findAll();
        verify(eventRepository, times(1)).findByCategory("Evento");

    }

    @Test
    public void testFinByLocation_Name() {


        Location location = new Location();
        location.setId(1);
        location.setName("Plaza del Pilar");

        List<Event> mockEventList = List.of(
                new Event(1,"Campanadas Año nuevo", "Un año más las campanadas tendrán lugar en la Plaza del pilar",
                        LocalDate.now(), "Evento", 2500, 32, true, null, null, null ),
                new Event(2, "Final Copa Casademont", "Partido femenino en el que el Caseademont Zaragoza puede ganar",
                        LocalDate.now(), "Deporte", 12000, 25, true, null, null, null),
                new Event(3, "Panorama desde el Puente", "Obra de teatro novedosa",
                        LocalDate.of(2025,1,2), "Evento", 300, 25, false, null, null, null),
                new Event(4, "Ofrenda de flores", "Día ne el que la gente realiza ofrenda a la Virgen del Pilar",
                        LocalDate.of(2025,11,11), "Ceremonia", 15000, 0, true, null, location, null)
        );

        List<EventOutDto> mockModelMapperOut = List.of(
                new EventOutDto(4, "Ofrenda de flores", LocalDate.of(2025,11,11), "Ceremonia", 0)
        );

        when(eventRepository.findByLocation_Name("Plaza del Pilar")).thenReturn(mockEventList);
        when(modelMapper.map(mockEventList, new TypeToken<List<EventOutDto>>() {}.getType())).thenReturn(mockModelMapperOut);

        List<EventOutDto> actualEventList = eventService.findAll(null, "Plaza del Pilar", null);
        assertEquals(1, actualEventList.size());
        assertEquals("Ofrenda de flores", actualEventList.getFirst().getName());

        verify(eventRepository, times(0)).findAll();
        verify(eventRepository, times(1)).findByLocation_Name("Plaza del Pilar");
    }

    @Test
    public void testFindByPricelessThanEqualOrderByPriceAsc() {

        List<Event> mockEventList = List.of(
                new Event(1,"Campanadas Año nuevo", "Un año más las campanadas tendrán lugar en la Plaza del pilar",
                        LocalDate.now(), "Evento", 2500, 32, true, null, null, null ),
                new Event(2, "Final Copa Casademont", "Partido femenino en el que el Caseademont Zaragoza puede ganar",
                        LocalDate.now(), "Deporte", 12000, 25, true, null, null, null),
                new Event(3, "Panorama desde el Puente", "Obra de teatro novedosa",
                        LocalDate.of(2025,1,2), "Evento", 300, 25, false, null, null, null)
        );

        List<EventOutDto> mockModelMapperOut = List.of(
                new EventOutDto(2,"Final Copa Casademont", LocalDate.now(), "Deporte", 25),
                new EventOutDto(3,"Panorama desde el Puente", LocalDate.of(2025,1,2), "Evento",25)
        );

        when(eventRepository.findByPriceLessThanEqualOrderByPriceAsc(30f)).thenReturn(mockEventList);
        when(modelMapper.map(mockEventList, new TypeToken<List<EventOutDto>>() {}.getType())).thenReturn(mockModelMapperOut);

        List<EventOutDto> actualEventList = eventService.findAll("", "", 30f);
        assertEquals(2, actualEventList.size());
        assertEquals("Panorama desde el Puente", actualEventList.getLast().getName());

        verify(eventRepository, times(0)).findAll();
        verify(eventRepository, times(1)).findByPriceLessThanEqualOrderByPriceAsc(30f);
    }

}
