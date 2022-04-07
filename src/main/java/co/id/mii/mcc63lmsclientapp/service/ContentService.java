/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.service;

import co.id.mii.mcc63lmsclientapp.model.Content;
import co.id.mii.mcc63lmsclientapp.model.Dto.ContentData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
                    new ParameterizedTypeReference<List<Content>>() {
            }
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
                    new ParameterizedTypeReference<Content>() {
            }
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
        return content;
    }

    public void create(ContentData contentData) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("title", contentData.getTitle());
        body.add("description", contentData.getDescription());
        body.add("modulId", contentData.getModuleId());
        body.add("file", contentData.getFile());
        
        System.out.println(contentData.getFile().toString());
        
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<ContentData>() {
            }
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
                    new ParameterizedTypeReference<Content>() {
            }
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
                    new ParameterizedTypeReference<Content>() {
            }
            );
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
}
