package by.cinema.controller;

import by.cinema.bean.Auditorium;
import by.cinema.bean.AuditoriumBooking;
import by.cinema.bean.Event;
import by.cinema.service.auditorium.AuditoriumService;
import by.cinema.service.event.EventService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kiryl_Parfiankou on 4/16/2017.
 */
@Controller
@RequestMapping("assignments")
public class AuditoriumBookingController {

    public static String ASSIGNMENT_DETAILS_TEMPLATE = "assignment-details";
    public static String ASSIGNMENT_LIST_TEMPLATE = "assignment-list";

    @Autowired
    private EventService eventService;
    @Autowired
    private AuditoriumService auditoriumService;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile file) throws IOException {

        List<AuditoriumBooking> auditoriumBookingForUpload = convert(file.getInputStream());

        auditoriumBookingForUpload.stream().forEach(
                audBook -> eventService.assignAuditorium(
                        audBook.getEvent(),
                        audBook.getAuditorium(),
                        audBook.getDate()
                ));
        return "redirect:../uploadSuccess";
    }

    private List<AuditoriumBooking> convert(InputStream inputStream) throws IOException{

        ObjectMapper mapper = new ObjectMapper();
        AuditoriumBookingInput[] input =
                mapper.readValue(inputStream, AuditoriumBookingInput[].class);

        // TODO Will be optimised!
        return Arrays.stream(input).map(audBook -> {
            Event event = eventService.getByName(audBook.getEvent());
            Auditorium auditorium = auditoriumService.get(audBook.getAuditorium());
            return new AuditoriumBooking(audBook.getDate(), auditorium, event);

        }).collect(Collectors.toList());

    }
}

class AuditoriumBookingInput {

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private Date date;
    private String auditorium;
    private String event;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
