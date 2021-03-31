package com.rivrs_project.blog.controllers;

import com.rivrs_project.blog.models.BlogPost;
import com.rivrs_project.blog.util.ClassifierUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NormControlController {

    @GetMapping("/norm-control")
    public String blogMain(Model model){
        model.addAttribute("title", "Нормоконтроль");
        return "user/norm-control";
    }

    @GetMapping("/norm-control/{UUID}/classify")
    public String blogEdit(@PathVariable(value = "UUID") String UUID, Model model){
        ClassifierUtil classifierUtil = new ClassifierUtil();
        classifierUtil.classify(UUID);
        return "user/norm-control";
    }
}
