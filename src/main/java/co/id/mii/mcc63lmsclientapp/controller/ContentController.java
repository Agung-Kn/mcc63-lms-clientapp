/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.controller;

import co.id.mii.mcc63lmsclientapp.model.Content;
import co.id.mii.mcc63lmsclientapp.model.Dto.ResponseData;
import co.id.mii.mcc63lmsclientapp.service.ContentService;
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
@RequestMapping("/content")
public class ContentController {
    private ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("contents", contentService.getAll());
        return "content/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("content", contentService.getById(id));
        return "content/detail";
    }

    @PostMapping("/create")
    public @ResponseBody
    ResponseEntity create(@RequestBody Content content, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        contentService.create(content);
        return ResponseEntity.ok(new ResponseData("success", "Content has been successfully added."));
    }

    @PutMapping("/update/{id}")
    public @ResponseBody
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody Content content, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        contentService.update(id, content);
        return ResponseEntity.ok(new ResponseData("success", "Content has been successfully updated."));
    }
    
    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    ResponseEntity delete(@PathVariable("id") Long id) {
        contentService.delete(id);
        return ResponseEntity.ok(new ResponseData("success", "Content has been successfully deleted."));
    }
}
