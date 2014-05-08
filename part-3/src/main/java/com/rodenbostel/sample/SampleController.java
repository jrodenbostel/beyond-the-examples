package com.rodenbostel.sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("gizmo", new Gizmo());
        return "hello";
    }
}