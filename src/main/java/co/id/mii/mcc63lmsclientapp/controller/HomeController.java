/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.controller;

import co.id.mii.mcc63lmsclientapp.service.CategoryService;
import co.id.mii.mcc63lmsclientapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author Agung
 */
@Controller
@RequestMapping
public class HomeController {
    
    private CategoryService categoryService;
    private CourseService courseService;

    @Autowired
    public HomeController(CategoryService categoryService, CourseService courseService) {
        this.categoryService = categoryService;
        this.courseService = courseService;
    }
    
    @GetMapping
    public String home(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("courses", courseService.getAll());
        return "index";
    }
    
    @GetMapping("/dashboard")
    public String course() {
        return "dashboard";
    }
    
    @GetMapping("/all-course")
    public String course(Model model) {
        model.addAttribute("courses", courseService.getAll());
        return "course/all-course";
    }
}