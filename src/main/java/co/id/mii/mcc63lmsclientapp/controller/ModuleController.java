/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.controller;

import co.id.mii.mcc63lmsclientapp.model.Dto.ModuleData;
import co.id.mii.mcc63lmsclientapp.model.Dto.ResponseData;
import co.id.mii.mcc63lmsclientapp.model.Module;
import co.id.mii.mcc63lmsclientapp.service.ModuleService;
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
@RequestMapping("/module")
public class ModuleController {

    private ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("modules", moduleService.getAll());
        return "module/index";
    }

    @GetMapping("/all")
    public @ResponseBody
    ResponseEntity<List<Module>> getAll() {
        return ResponseEntity.ok(moduleService.getAll());
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("module", moduleService.getById(id));
        return "module/detail";
    }

    @PostMapping("/new")
    public @ResponseBody
    ResponseEntity create(@RequestBody ModuleData moduleData, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        moduleService.create(moduleData);
        return ResponseEntity.ok(new ResponseData("success", "Module has been successfully added."));
    }

    @PutMapping("/edit/{id}")
    public @ResponseBody
    ResponseEntity update(@PathVariable("id") Long id, @RequestBody ModuleData moduleData, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        moduleService.update(id, moduleData);
        return ResponseEntity.ok(new ResponseData("success", "Module has been successfully updated."));
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody
    ResponseEntity delete(@PathVariable("id") Long id) {
        moduleService.delete(id);
        return ResponseEntity.ok(new ResponseData("success", "Module has been successfully deleted."));
    }
}
