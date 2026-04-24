package com.svalero.cityEvents;

import com.svalero.cityEvents.domain.User;
import com.svalero.cityEvents.dto.UserInDto;
import com.svalero.cityEvents.dto.UserOutDto;
import com.svalero.cityEvents.exception.UserNotFoundException;
import com.svalero.cityEvents.repository.UserRepository;
import com.svalero.cityEvents.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testFindAll() {

        List<User> mockUserList = List.of(
            new User(1, "martin123", "Jorge", "Ruiz", LocalDate.of(1987,2,5), 615987325, true, null),
            new User(2, "sofiladf", "Sofia", "Labarta", LocalDate.of(1995,1,2), 614784236, false, null),
            new User(3,"mondi", "Mónica", "Díez", LocalDate.of(2003,4,8), 698514736, true, null)
        );

        List<UserOutDto> mockUserOutDto = List.of(
          new UserOutDto(1,"martin123", "Ruiz", LocalDate.of(1987,2,5)),
          new UserOutDto(2, "sofiladf", "Labarta", LocalDate.of(1995,1,2)),
          new UserOutDto(3, "mondi", "Díez", LocalDate.of(2003,4,8))
        );

        when(userRepository.findAll()).thenReturn(mockUserList);
        when(modelMapper.map(mockUserList, new TypeToken<List<UserOutDto>>() {}.getType())).thenReturn(mockUserOutDto);

        List<UserOutDto> actualUserList = userService.findAll("",null,null);
        assertEquals(3, actualUserList.size());
        assertEquals("mondi", actualUserList.getLast().getUsername());

        verify(userRepository, times(1)).findAll();
        verify(userRepository, times(0)).findUserByName("");
        verify(userRepository, times(0)).findByBirthDateBefore(null);
        verify(userRepository, times(0)).findByActiveFalse();
    }

    @Test
    public void testFindUserByName() {

        List<User> mockUserList = List.of(
                new User(1, "martin123", "Jorge", "Ruiz", LocalDate.of(1987,2,5), 615987325, true, null),
                new User(2, "sofiladf", "Sofia", "Labarta", LocalDate.of(1995,1,2), 614784236, false, null),
                new User(3,"mondi", "Mónica", "Díez", LocalDate.of(2003,4,8), 698514736, true, null)
        );

        List<UserOutDto> mockUserOutDto = List.of(
                new UserOutDto(3, "mondi", "Díez", LocalDate.of(2003,4,8))
        );

        when(userRepository.findUserByName("Mónica")).thenReturn(mockUserList);
        when(modelMapper.map(mockUserList, new TypeToken<List<UserOutDto>>() {}.getType())).thenReturn(mockUserOutDto);

        List<UserOutDto> actualEventList = userService.findAll("Mónica",null,null);
        assertEquals(1, actualEventList.size());
        assertEquals("mondi", actualEventList.getLast().getUsername());

        verify(userRepository, times(0)).findAll();
        verify(userRepository, times(1)).findUserByName("Mónica");
        verify(userRepository, times(0)).findByBirthDateBefore(null);
        verify(userRepository, times(0)).findByActiveFalse();
    }

    @Test
    public void testFindByBirthDateBefore() {

        List<User> mockUserList = List.of(
                new User(1, "martin123", "Jorge", "Ruiz", LocalDate.of(1987,2,5), 615987325, true, null),
                new User(2, "sofiladf", "Sofia", "Labarta", LocalDate.of(1995,1,2), 614784236, false, null),
                new User(3,"mondi", "Mónica", "Díez", LocalDate.of(2003,4,8), 698514736, true, null)
        );

        List<UserOutDto> mockUserOutDto = List.of(
                new UserOutDto(1,"martin123", "Ruiz", LocalDate.of(1987,2,5)),
                new UserOutDto(2, "sofiladf", "Labarta", LocalDate.of(1995,1,2))
        );

        when(userRepository.findByBirthDateBefore(LocalDate.of(2000,1,1))).thenReturn(mockUserList);
        when(modelMapper.map(mockUserList, new TypeToken<List<UserOutDto>>() {}.getType())).thenReturn(mockUserOutDto);

        List<UserOutDto> actualUserList = userService.findAll("",LocalDate.of(2000,1,1),null);
        assertEquals(2, actualUserList.size());
        assertEquals("sofiladf", actualUserList.getLast().getUsername());

        verify(userRepository, times(0)).findAll();
        verify(userRepository, times(0)).findUserByName("");
        verify(userRepository, times(1)).findByBirthDateBefore(LocalDate.of(2000,1,1));
        verify(userRepository, times(0)).findByActiveFalse();
    }

    @Test
    public void testFindByActiveFalse() {

        List<User> mockUserList = List.of(
                new User(1, "martin123", "Jorge", "Ruiz", LocalDate.of(1987,2,5), 615987325, true, null),
                new User(2, "sofiladf", "Sofia", "Labarta", LocalDate.of(1995,1,2), 614784236, false, null),
                new User(3,"mondi", "Mónica", "Díez", LocalDate.of(2003,4,8), 698514736, true, null)
        );

        List<UserOutDto> mockUserOutDto = List.of(
                new UserOutDto(2, "sofiladf", "Labarta", LocalDate.of(1995,1,2))
        );

        when(userRepository.findByActiveFalse()).thenReturn(mockUserList);
        when(modelMapper.map(mockUserList, new TypeToken<List<UserOutDto>>() {}.getType())).thenReturn(mockUserOutDto);

        List<UserOutDto> actualUserList = userService.findAll("",null,true);
        assertEquals(1, actualUserList.size());
        assertEquals("sofiladf", actualUserList.getLast().getUsername());

        verify(userRepository, times(0)).findAll();
        verify(userRepository, times(0)).findUserByName("");
        verify(userRepository, times(0)).findByBirthDateBefore(null);
        verify(userRepository, times(1)).findByActiveFalse();
    }

    @Test
    public void testFindUserById() throws UserNotFoundException {
        User mockUser = new User(2, "sofiladf", "Sofia", "Labarta", LocalDate.of(1995,1,2), 614784236,
                false, null);


        when(userRepository.findById(2L)).thenReturn(Optional.of(mockUser));

        User user = userService.findUserById(2L);
        assertEquals("sofiladf", user.getUsername());
        assertFalse(user.isActive());

        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    public void testFindUserByIdNotFound() throws UserNotFoundException {

        when(userRepository.findById(65L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(65L));

        verify(userRepository, times(1)).findById(65L);
    }

    @Test
    public void testAddUser() {

        UserInDto userInDto = new UserInDto("martina35", "Martina", "Soro", LocalDate.of(1994,3,2),
                623159865);

        User registerUser = new User(3, "martina35", "Martina", "Soro", LocalDate.of(1994,3,2),
                623159865, true, null);

        when(userRepository.save(any(User.class))).thenReturn(registerUser);

        User resultUser = userService.add(userInDto);

        assertEquals(registerUser, resultUser);
        assertEquals("martina35", resultUser.getUsername());
        assertEquals("Soro", resultUser.getSurname());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testModifyUser() throws UserNotFoundException {

        User existingUser = new User();
        existingUser.setName("Marcos");
        existingUser.setId(17);

        User updatingUser = new User();
        updatingUser.setName("Sergio");

        when(userRepository.findById(17L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        userService.modify(17L, updatingUser);

        verify(modelMapper).map(updatingUser,existingUser);
        verify(userRepository, times(1)).findById(17L);
        verify(userRepository).save(existingUser);
    }

    @Test
    public void testModifyUserNotFound() {

        User user = new User();

        when(userRepository.findById(15L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.modify(15L, user));

        verify(userRepository, times(1)).findById(15L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {

        User user = new User();
        user.setId(15L);

        when(userRepository.findById(15L)).thenReturn(Optional.of(user));

        userService.delete(15L);

        verify(userRepository, times(1)).findById(15L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteUserNotFound() {

        when(userRepository.findById(19L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(19));

        verify(userRepository, times(1)).findById(19L);
        verify(userRepository, times(0)).delete(any(User.class));
    }

}
