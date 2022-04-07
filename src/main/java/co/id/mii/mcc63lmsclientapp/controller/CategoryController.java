/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.controller;

import co.id.mii.mcc63lmsclientapp.model.Category;
import co.id.mii.mcc63lmsclientapp.model.Dto.ResponseData;
import co.id.mii.mcc63lmsclientapp.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public @ResponseBody
    ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        return "category/detail";
    }

    @PostMapping("/create")
    public @ResponseBody
    ResponseEntity create(@RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        categoryService.create(category);
        return ResponseEntity.ok(new ResponseData("success", "Category created"));
    }

    @PutMapping("/update/{id}")
    public @ResponseBody
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        categoryService.update(id, category);
        return ResponseEntity.ok(new ResponseData("success", "Category updated"));
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    ResponseEntity delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(new ResponseData("success", "Category deleted"));
    }
}
