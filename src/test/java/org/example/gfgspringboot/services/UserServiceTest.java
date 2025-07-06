package org.example.gfgspringboot.services;

import org.example.gfgspringboot.models.User;
import org.example.gfgspringboot.repositories.AssetRepository;
import org.example.gfgspringboot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class UserServiceTest {



    @Mock
    private  AssetRepository assetRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testRegisterUserSuccess() {
        /*userRepository = Mockito.mock(UserRepository.class);
        assetRepository = Mockito.mock(AssetRepository.class);
        userService = new UserService(userRepository, assetRepository);*/
        Mockito.when(userRepository.findByEmail(any())).thenReturn(null);
        Mockito.when(userRepository.save(any())).thenReturn(new User());

        String result = userService.registerUser(new User());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
        assertEquals("User Registered Successfully", result);
    }

    @Test
    public void testRegisterUserALreadyExists() {
        userRepository = Mockito.mock(UserRepository.class);
        assetRepository = Mockito.mock(AssetRepository.class);
        userService = new UserService(userRepository, assetRepository);
        Mockito.when(userRepository.findByEmail(any())).thenReturn(new User());
       // Mockito.when(userRepository.save(any())).thenReturn(new User());

        String result = userService.registerUser(new User());
        assertEquals("Email Already Exists", result);
    }

    @Test
    public void testRegisterUserWithDBError() {
        userRepository = Mockito.mock(UserRepository.class);
        assetRepository = Mockito.mock(AssetRepository.class);
        userService = new UserService(userRepository, assetRepository);
        Mockito.when(userRepository.findByEmail(any())).thenReturn(null);
        Mockito.when(userRepository.save(any())).thenThrow(new RuntimeException("Database Error"));

        Assertions.assertThrows(RuntimeException.class, () -> userService.registerUser(new User()));
       // String result = userService.registerUser(new User());
       // assertEquals("Email Already Exists", result);
    }


}
