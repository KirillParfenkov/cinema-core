package by.cinema.controller;

import by.cinema.bean.Auditorium;
import by.cinema.service.auditorium.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 4/10/2017.
 */
@Controller
@RequestMapping("/auditoriums")
public class AuditoriumController {

    @Autowired
    private AuditoriumService auditoriumService;

    @RequestMapping
    public ModelAndView getAuditoriums() {
        ModelAndView modelAndView = new ModelAndView("auditorium-list");
        modelAndView.addObject("auditoriums", auditoriumService.getAuditoriums());
        return modelAndView;
    }

    @RequestMapping("{id}")
    public ModelAndView get(@PathVariable String id) {

        ModelAndView modelAndView = new ModelAndView("auditorium-details");
        modelAndView.addObject("auditorium", auditoriumService.get(id));
        return modelAndView;
    }

    @RequestMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ModelAndView getAllPdf() {
        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        return new ModelAndView("pdfPage", "list", auditoriums);
    }
}
