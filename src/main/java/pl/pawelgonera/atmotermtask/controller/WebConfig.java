package pl.pawelgonera.atmotermtask.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.pawelgonera.atmotermtask.util.EnumConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry){

        registry.addConverter(new EnumConverter());
    }
}
