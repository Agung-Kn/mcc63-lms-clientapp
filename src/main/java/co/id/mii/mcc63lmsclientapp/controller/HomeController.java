/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.controller;

import org.springframework.stereotype.Controller;
/**
 *
 * @author Agung
 */
@Controller
public class HomeController {

    @GetMapping
    public String home(Model model) {
        return "index";
    }
}