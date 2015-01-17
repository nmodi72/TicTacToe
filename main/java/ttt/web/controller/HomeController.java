package ttt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"/home.html"})
    public String index(ModelMap map)
    {
    	map.put("id", "Nirav Modi");
        return "home";
    }

}