package org.example.gfgspringboot.controllers;

import org.apache.hc.core5.http.ParseException;
import org.example.gfgspringboot.clients.WeatherClient;
import org.example.gfgspringboot.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserControllerTest {
     @Mock
     private UserService userService;

     @Mock
     private WeatherClient weatherClient;

     @InjectMocks
     private UserController userController;

     @BeforeEach
     public void setup() {
         MockitoAnnotations.initMocks(this);
     }

     @Test
    public void testGetWeatherSuccess() throws IOException, ParseException {
         String city = "San Francisco";
         Mockito.when(weatherClient.getWeather(city)).thenReturn("test weather");
         String weather = userController.getWeather(city);
         assertEquals("test weather", weather);
     }

    @Test
    public void testGetWeatherFailure() throws IOException, ParseException {
        String city = "San Francisco";
        Mockito.when(weatherClient.getWeather(city)).thenThrow(new ParseException());
        String weather = userController.getWeather(city);
        assertNull(weather);
    }

}
