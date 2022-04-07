/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.controller;

import co.id.mii.mcc63lmsclientapp.model.Course;
import co.id.mii.mcc63lmsclientapp.model.Dto.CourseData;
import co.id.mii.mcc63lmsclientapp.model.Dto.ResponseData;
import co.id.mii.mcc63lmsclientapp.service.CourseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Agung
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("courses", courseService.getAll());
        return "course/index";
    }

    @GetMapping("/all")
    public @ResponseBody
    ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @PostMapping("/new")
    public @ResponseBody
    ResponseEntity create(@RequestBody CourseData courseData, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        courseService.create(courseData);
        return ResponseEntity.ok(new ResponseData("success", "Course created"));
    }

    @PutMapping("/edit/{id}")
    public @ResponseBody
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody CourseData courseData, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        courseService.update(id, courseData);
        return ResponseEntity.ok(new ResponseData("success", "Course updated"));
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    ResponseEntity delete(@PathVariable("id") Long id) {
        courseService.delete(id);
        return ResponseEntity.ok(new ResponseData("success", "Course deleted"));
    }
}
