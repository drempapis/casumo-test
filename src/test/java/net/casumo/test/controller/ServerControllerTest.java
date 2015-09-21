package net.casumo.test.controller;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class ServerControllerTest {

    @InjectMocks
    private ServerController serverController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(serverController).build();
    }

    @Test
    public void serverControllerTest() throws Exception {
        this.mockMvc.perform(post( "/test").
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_FORM_URLENCODED)).
                andExpect(status().is(HttpStatus.SC_OK)).andReturn();
    }
}
