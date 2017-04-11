package by.cinema.configuration;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Kiryl_Parfiankou on 4/11/2017.
 */
@ControllerAdvice("by.cinema.controller")
public class ControllerAdviceConfig {

    @ExceptionHandler
    public ModelAndView handleException(Exception e) {
        return new ModelAndView("error").addObject("exception", e);
    }
}
