package pl.pawelgonera.atmotermtask.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");

    @Override
    public LocalDate unmarshal(String xmlDate)  {
        return (LocalDate) formatter.parse(xmlDate);
    }

    @Override
    public String marshal(LocalDate localDate)  {
        return formatter.format(localDate);
    }

}