package by.cinema.controller;


import by.cinema.bean.Event;
import by.cinema.service.event.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 4/10/2017.
 */
@Controller
@RequestMapping("events")
public class EventController {

    public static String EVENT_DETAILS_TEMPLATE = "event-details";
    public static String EVENT_LIST_TEMPLATE = "event-list";

    @Autowired
    private EventService eventService;

    @RequestMapping(params={"name"})
    public ModelAndView getByName(String name) {
        Event event = eventService.getByName(name);
        return new ModelAndView(EVENT_DETAILS_TEMPLATE).addObject("event", event);
    }

    @RequestMapping
    public ModelAndView getAll() {
        List<Event> events = eventService.getAll();
        return new ModelAndView(EVENT_LIST_TEMPLATE).addObject("events", events);
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile file) throws IOException {

        Event[] usersForUpload = convert(file.getInputStream());
        Arrays.stream(usersForUpload).forEach(event -> eventService.create(event));
        return "redirect:../uploadSuccess";
    }

    @RequestMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ModelAndView getAllPdf() {
        List<Event> events = eventService.getAll();
        return new ModelAndView("pdfPage", "list", events);
    }

    private Event[] convert(InputStream inputStream) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, Event[].class);
    }
}
