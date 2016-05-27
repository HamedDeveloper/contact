package com.contact;

import com.contact.domain.City;
import com.contact.domain.State;
import com.contact.service.CityRepository;
import com.contact.service.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


    @Bean
    public CommandLineRunner demo(CityRepository cityRepository, StateRepository stateRepository) {
        return (args) -> {
//            State nyState = new State("New York");
//            State njState = new State("New Jersey");
//            stateRepository.save(nyState);
//            stateRepository.save(njState);
//
//            cityRepository.save(new City("Denver"));
//            cityRepository.save(new City("Detroit"));
//            cityRepository.save(new City("Chicago"));
//            cityRepository.save(new City("New York", nyState));
//            cityRepository.save(new City("Plainsboro", njState));

        };
    }
}
