package au.com.polonious.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
//@RequestMapping("ecosDemo")
public class MvcController {
    @Autowired
    EcosController ecosController;

    @RequestMapping("/")
    public String home() {
        System.out.println("Going home...");
        return "index";
    }

    @RequestMapping("/register")
    public String showForm(Model model) {
        UserData userData = new UserData();
        userData.inputList = ecosController.getAllInput();

        List<String> professionList = Arrays.asList("Developer", "Designer", "Tester");

        model.addAttribute("xmlList", userData.inputList);
        model.addAttribute("professionList", professionList);

        return "register_form";
    }

    @PostMapping("/register_form")
    public String submitForm(@ModelAttribute("user") UserData userData) {

        System.out.println(userData);

        return "register_success";
    }

//    @GetMapping({"/", "/hello"})
//    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
//        model.addAttribute("name", name);
//        return "hello";
//    }

}
