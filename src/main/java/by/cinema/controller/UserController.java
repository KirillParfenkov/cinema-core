package by.cinema.controller;

import by.cinema.bean.User;
import by.cinema.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Kiryl_Parfiankou on 4/10/2017.
 */
@Controller
@RequestMapping("users")
public class UserController {

    public static String TEMPLATE_NAME = "user-details";
    public static String USER_KEY = "user";

    @Autowired
    private UserService userService;

    @RequestMapping(params = {"id"})
    public ModelAndView getById(@RequestParam String id) {

        User user = userService.getById(id);
        return new ModelAndView(TEMPLATE_NAME).addObject(USER_KEY, user);
    }

    @RequestMapping(params = {"email"})
    public ModelAndView getUserByEmail(@RequestParam String email) {

        User user =  userService.getUserByEmail(email);
        return new ModelAndView(TEMPLATE_NAME).addObject(USER_KEY, user);
    }

    @RequestMapping(params = {"name"})
    public ModelAndView getUsersByName(@RequestParam String name) {

        User user =  userService.getUsersByName(name);
        return new ModelAndView(TEMPLATE_NAME).addObject(USER_KEY, user);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView register(@RequestBody MultiValueMap<String, String> request) {

        User newUser = new User(request.getFirst("email"),
                                request.getFirst("lastName"),
                                request.getFirst("firstName"));

        User user =  userService.register(newUser);
        return getById(user.getId());
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile file) throws IOException{

        User[] usersForUpload = convert(file.getInputStream());
        Arrays.stream(usersForUpload).forEach(user -> userService.register(user));

        return "redirect:../uploadSuccess";
    }

    private User[] convert(InputStream inputStream) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, User[].class);
    }
}
