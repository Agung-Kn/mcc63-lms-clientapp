/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.service;

import co.id.mii.mcc63lmsclientapp.model.Content;
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
public class ContentService {
    
    private RestTemplate restTemplate;

    @Value("${app.baseUrl}/content")
    private String url;

    @Autowired
    public ContentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<Content> getAll() {
        List<Content> countries = new ArrayList<>();
        try {
            ResponseEntity<List<Content>> response = restTemplate.exchange(url, 
                                                        HttpMethod.GET, 
                                                        null, 
                                                        new ParameterizedTypeReference<List<Content>>() {}
                                                    );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());     
        }
        return countries;
    }
    
    public Content getById(Long id) {
        Content content = new Content();
        try {
            ResponseEntity<Content> response = restTemplate.exchange(url.concat("/" + id), 
                                                        HttpMethod.GET, 
                                                        null, 
                                                        new ParameterizedTypeReference<Content>() {}
                                                    );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
        return content;
    }
    
    public void create(Content content) {
        try {
            restTemplate.exchange(
                                url,
                                HttpMethod.POST, 
                                new HttpEntity<>(content), 
                                new ParameterizedTypeReference<Content>() {}
                            );
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
    
    public void update(Long id, Content content) {
        try {
            content.setId(id);
            restTemplate.exchange(
                                url.concat("/" + id), 
                                HttpMethod.PUT, 
                                new HttpEntity<>(content), 
                                new ParameterizedTypeReference<Content>() {}
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
                            new ParameterizedTypeReference<Content>() {}
                        );
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
}
