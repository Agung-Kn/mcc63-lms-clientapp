/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.service;

import co.id.mii.mcc63lmsclientapp.model.Category;
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
public class CategoryService {

    private RestTemplate restTemplate;

    @Value("${app.baseUrl}/category")
    private String url;

    @Autowired
    public CategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        try {
            ResponseEntity<List<Category>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Category>>() {
            });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
        return categories;
    }

    public Category getById(Long id) {
        Category category = new Category();
        try {
            ResponseEntity<Category> response = restTemplate.exchange(
                    url.concat("/" + id),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Category>() {
            });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
        return category;
    }

    public void create(Category category) {
        try {
            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(category),
                    new ParameterizedTypeReference<Category>() {
            });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }

    public void update(Long id, Category category) {
        try {
            category.setId(id);
            restTemplate.exchange(
                    url.concat("/" + id),
                    HttpMethod.PUT,
                    new HttpEntity<>(category),
                    new ParameterizedTypeReference<Category>() {
            });
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
                    new ParameterizedTypeReference<Category>() {
            });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
}
