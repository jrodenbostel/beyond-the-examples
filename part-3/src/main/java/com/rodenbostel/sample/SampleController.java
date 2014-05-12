package com.rodenbostel.sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SampleController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("gizmo", new Gizmo());
        return "hello";
    }

    @RequestMapping("/save")
    public String save(Gizmo gizmo) {
        System.out.println(gizmo.getField1());
        System.out.println(gizmo.getField2());
        return "hello";
    }
}