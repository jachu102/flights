package com.bsd.exampleapp.springboot.flights.config;

import com.bsd.exampleapp.springboot.flights.converter.OwnerDtoConverter;
import com.bsd.exampleapp.springboot.flights.converter.PigeonConverter;
import com.bsd.exampleapp.springboot.flights.converter.PigeonDtoConverter;
import com.bsd.exampleapp.springboot.flights.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PigeonConverter(ownerRepository));
        registry.addConverter(new PigeonDtoConverter());
        registry.addConverter(new OwnerDtoConverter());
    }
}