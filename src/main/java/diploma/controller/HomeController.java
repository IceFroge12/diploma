package diploma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by IO on 27.11.2016.
 */
@Controller
public class HomeController {



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "lisen";
    }

    @RequestMapping(value = "/song", method = RequestMethod.GET)
    public String song(){
        return "addSong";
    }
}
