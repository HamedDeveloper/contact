package com.contact.web;

import com.contact.domain.City;
import com.contact.domain.State;
import com.contact.service.CityRepository;
import com.contact.service.StateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class StateControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMVC;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private static HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }


    public static String jsonify(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    public static State objectify(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, State.class);
    }

    @Before
    public void setUp(){
        this.mockMVC = webAppContextSetup(webApplicationContext).build();

        cityRepository.deleteAll();
        stateRepository.deleteAll();

        State nyState = new State("New York");
        State njState = new State("New Jersey");
        stateRepository.save(nyState);
        stateRepository.save(njState);


        cityRepository.save(new City("Denver"));
        cityRepository.save(new City("Detroit"));
        cityRepository.save(new City("Chicago"));
        cityRepository.save(new City("New York", nyState));
        cityRepository.save(new City("Plainsboro", njState));
    }

    @Test
    public void getState() throws Exception {
        MockHttpServletResponse response = mockMVC.perform(get("/state/1")).andReturn().getResponse();
        State state = objectify(response.getContentAsString());
        assertEquals(stateRepository.findOne(1L), state);
    }

    @Test
    public void postState() throws Exception {
        String michiganStr = "Michigan";
        State michigan = new State(michiganStr);
        String michiganJson = jsonify(michigan);
        mockMVC.perform(post("/state")
                .contentType(contentType)
                .content(michiganJson)).andExpect(status().isOk());

        assertEquals(3, stateRepository.count());
        Iterable<State> all = stateRepository.findAll();
        List<State> stateByName = stateRepository.findByName(michiganStr);
        assertEquals(michigan, stateByName.get(0));
    }

}
