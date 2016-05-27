package com.contact.web;

import com.contact.domain.City;
import com.contact.domain.State;
import com.contact.service.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

@RestController
@EnableSwagger2
@EnableAutoConfiguration
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    public CityController() {
    }

    @RequestMapping(value = "/city/{id}", method = RequestMethod.GET)
    public City getCity(@PathVariable(value = "id") Long id, Model model){
        return cityRepository.findOne(id);
    }

    @RequestMapping(value = "/city/{id}", method = RequestMethod.PUT)
    public City updateCity(@PathVariable(value = "id") Long id, Model model){
        Map<String, Object> modelMap = model.asMap();
        String name = (String) modelMap.get("name");
        State state = (State) modelMap.get("state");
        City city = cityRepository.findOne(id);
        cityRepository.save(city);
        return cityRepository.findOne(city.getId());
    }
}
