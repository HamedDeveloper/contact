package com.contact.web;

import com.contact.domain.State;
import com.contact.service.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@EnableAutoConfiguration
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    public StateController() {
    }

    @RequestMapping(value = "/state/{id}", method = RequestMethod.GET)
    public State getState(@PathVariable(value = "id") Long id, Model model){
        return stateRepository.findOne(id);
    }

    @RequestMapping(value = "/state", method = RequestMethod.POST)
    public void createState(@RequestBody State state, Model model){
//        State state = new State(stateJson.name);
        stateRepository.save(state);
        State one = stateRepository.findOne(5L);
        System.out.println(one);
    }
}
