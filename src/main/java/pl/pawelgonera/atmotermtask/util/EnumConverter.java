package pl.pawelgonera.atmotermtask.util;

import org.springframework.core.convert.converter.Converter;
import pl.pawelgonera.atmotermtask.controller.Extension;

public class EnumConverter implements Converter<String, Extension> {

    @Override
    public Extension convert(String source) {
        return Extension.valueOf(source.toUpperCase());
    }
}