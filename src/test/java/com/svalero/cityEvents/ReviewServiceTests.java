package com.svalero.cityEvents;

import com.svalero.cityEvents.domain.Event;
import com.svalero.cityEvents.domain.Review;
import com.svalero.cityEvents.domain.User;
import com.svalero.cityEvents.dto.EventOutDto;
import com.svalero.cityEvents.dto.ReviewOutDto;
import com.svalero.cityEvents.repository.ReviewRepository;
import com.svalero.cityEvents.service.ReviewService;
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
public class ReviewServiceTests {

    @InjectMocks
    private ReviewService reviewService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ReviewRepository reviewRepository;

    @Test
    public void testFindAll() {

        List<Review> mockReviewList = List.of(
            new Review(1, 4.0f, "Me ha gustado mucho", LocalDate.of(2022,3,2),
                  true, 35, true, null, null),
            new Review(2, 3.2f, "Ha estado bien, pero mejorable", LocalDate.of(2023,5,12),
                    false, 22, true, null, null),
            new Review(3, 1.3f, "No lo recomiendo", LocalDate.of(2023,2,1), false,
                    1, false, null, null)
        );

        List<ReviewOutDto> mockReviewOutDto = List.of(
            new ReviewOutDto(1,4.0f, "Me ha gustado mucho", LocalDate.of(2022,3,2), 35, true),
            new ReviewOutDto(2, 3.2f, "Ha estado bien, pero mejorable", LocalDate.of(2023,5,12),22, true),
                new ReviewOutDto(3, 1.3f, "No lo recomiendo", LocalDate.of(2023,2,1),1, false)
        );

        when(reviewRepository.findAll()).thenReturn(mockReviewList);
        when(modelMapper.map(mockReviewList, new TypeToken<List<ReviewOutDto>>() {}.getType())).thenReturn(mockReviewOutDto);

        List<ReviewOutDto> actualEventList = reviewService.findAll("","",null);
        assertEquals(3, actualEventList.size());
        assertEquals(35, actualEventList.getFirst().getLikes());

        verify(reviewRepository, times(1)).findAll();
        verify(reviewRepository, times(0)).findByEvent_Name("");
    }

    @Test
    public void testFindByUserUsername() {

        User user = new User();
        user.setId(3);
        user.setUsername("martin123");

        List<Review> mockReviewList = List.of(
                new Review(1, 4.0f, "Me ha gustado mucho", LocalDate.of(2022,3,2),
                        true, 35, true, null, user),
                new Review(2, 3.2f, "Ha estado bien, pero mejorable", LocalDate.of(2023,5,12),
                        false, 22, true, null, null),
                new Review(3, 1.3f, "No lo recomiendo", LocalDate.of(2023,2,1), false,
                        1, false, null, null)
        );

        List<ReviewOutDto> mockReviewOutDto = List.of(
                new ReviewOutDto(1,4.0f, "Me ha gustado mucho", LocalDate.of(2022,3,2), 35, true)
        );

        when(reviewRepository.findByUserUsername("martin123")).thenReturn(mockReviewList);
        when(modelMapper.map(mockReviewList, new TypeToken<List<ReviewOutDto>>() {}.getType())).thenReturn(mockReviewOutDto);

        List<ReviewOutDto> actualEventList = reviewService.findAll("martin123","",null);
        assertEquals(1, actualEventList.size());
        assertEquals(35, actualEventList.getFirst().getLikes());

        verify(reviewRepository, times(0)).findAll();
        verify(reviewRepository, times(1)).findByUserUsername("martin123");
    }

    @Test
    public void testFindByEvent_Name() {

        Event event = new Event();
        event.setId(2);
        event.setName("Musical Navideño");

        List<Review> mockReviewList = List.of(
                new Review(1, 4.0f, "Me ha gustado mucho", LocalDate.of(2022,3,2),
                        true, 35, true, null, null),
                new Review(2, 3.2f, "Ha estado bien, pero mejorable", LocalDate.of(2023,5,12),
                        false, 22, true, event, null),
                new Review(3, 1.3f, "No lo recomiendo", LocalDate.of(2023,2,1), false,
                        1, false, event, null)
        );

        List<ReviewOutDto> mockReviewOutDto = List.of(
                new ReviewOutDto(2, 3.2f, "Ha estado bien, pero mejorable", LocalDate.of(2023,5,12),22, true),
                new ReviewOutDto(3, 1.3f, "No lo recomiendo", LocalDate.of(2023,2,1),1, false)
        );

        when(reviewRepository.findByEvent_Name("Musical Navideño")).thenReturn(mockReviewList);
        when(modelMapper.map(mockReviewList, new TypeToken<List<ReviewOutDto>>() {}.getType())).thenReturn(mockReviewOutDto);

        List<ReviewOutDto> actualEventList = reviewService.findAll("","Musical Navideño",null);
        assertEquals(2, actualEventList.size());
        assertEquals(22, actualEventList.getFirst().getLikes());

        verify(reviewRepository, times(0)).findAll();
        verify(reviewRepository, times(1)).findByEvent_Name("Musical Navideño");
    }

    @Test
    public void testFindByRateGreaterThan() {

        List<Review> mockReviewList = List.of(
                new Review(1, 4.0f, "Me ha gustado mucho", LocalDate.of(2022,3,2),
                        true, 35, true, null, null),
                new Review(2, 3.2f, "Ha estado bien, pero mejorable", LocalDate.of(2023,5,12),
                        false, 22, true, null, null),
                new Review(3, 1.3f, "No lo recomiendo", LocalDate.of(2023,2,1), false,
                        1, false, null, null)
        );

        List<ReviewOutDto> mockReviewOutDto = List.of(
                new ReviewOutDto(1,4.0f, "Me ha gustado mucho", LocalDate.of(2022,3,2), 35, true),
                new ReviewOutDto(2, 3.2f, "Ha estado bien, pero mejorable", LocalDate.of(2023,5,12),22, true)
        );

        when(reviewRepository.findByRateGreaterThan(3f)).thenReturn(mockReviewList);
        when(modelMapper.map(mockReviewList, new TypeToken<List<ReviewOutDto>>() {}.getType())).thenReturn(mockReviewOutDto);

        List<ReviewOutDto> actualEventList = reviewService.findAll("","",3f);
        assertEquals(2, actualEventList.size());
        assertEquals(22, actualEventList.getLast().getLikes());

        verify(reviewRepository, times(0)).findAll();
        verify(reviewRepository, times(1)).findByRateGreaterThan(3f);
    }



}
