/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.service;

import co.id.mii.mcc63lmsclientapp.model.Module;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Agung
 */
@Service
public class ModuleService {

    private RestTemplate restTemplate;

    @Value("${app.baseUrl}/module")
    private String url;

    @Autowired
    public ModuleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Module> getAll() {
        List<Module> modules = new ArrayList<>();
        try {
            ResponseEntity<List<Module>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Module>>() {
            });
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
        return modules;
    }
    
    public Module getById(Long id) {
        Module module = new Module();
        try {
            ResponseEntity<Module> response = restTemplate.exchange(url.concat("/" + id), 
                                                        HttpMethod.GET, 
                                                        null, 
                                                        new ParameterizedTypeReference<Module>() {}
                                                    );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
        return module;
    }
    
    public void create(Module module) {
        try {
            restTemplate.exchange(
                                url,
                                HttpMethod.POST, 
                                new HttpEntity<>(module), 
                                new ParameterizedTypeReference<Module>() {}
                            );
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
    
    public void update(Long id, Module module) {
        try {
            module.setId(id);
            restTemplate.exchange(
                                url.concat("/" + id), 
                                HttpMethod.PUT, 
                                new HttpEntity<>(module), 
                                new ParameterizedTypeReference<Module>() {}
                            );
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
    
    public void delete(Long id) {
        try {
            restTemplate.exchange(
                            url.concat("/" + id), 
                            HttpMethod.DELETE, 
                            null, 
                            new ParameterizedTypeReference<Module>() {}
                        );
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }

}
