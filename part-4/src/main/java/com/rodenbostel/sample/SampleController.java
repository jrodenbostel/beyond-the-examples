package main.java.com.rodenbostel.sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SampleController {

    @RequestMapping("/")
    public String index(Model model) {
        Gizmo gizmo = new Gizmo();
        gizmo.getChildren().add(new GizmoChild());
        model.addAttribute("gizmo", gizmo);
        return "hello";
    }

    @RequestMapping("/save")
    public String save(Gizmo gizmo) {
        System.out.println(gizmo.getField1());
        System.out.println(gizmo.getField2());
        for(GizmoChild child : gizmo.getChildren()) {
            System.out.println(child.getChildField1());
            System.out.println(child.getChildField2());
        }
        return "redirect:/";
    }
}