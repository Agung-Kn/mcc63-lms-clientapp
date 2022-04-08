/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.service;

import co.id.mii.mcc63lmsclientapp.model.Course;
import co.id.mii.mcc63lmsclientapp.model.Dto.CourseData;
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
public class CourseService {

    private RestTemplate restTemplate;

    @Value("${app.baseUrl}/course")
    private String url;

    @Autowired
    public CourseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Course> getAll() {
        List<Course> countries = new ArrayList<>();
        try {
            ResponseEntity<List<Course>> response = restTemplate.exchange(url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Course>>() {
            });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
        return countries;
    }

    public Course getById(Long id) {
        Course course = new Course();
        try {
            ResponseEntity<Course> response = restTemplate.exchange(url.concat("/" + id),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Course>() {
            });

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
        return course;
    }

    public void create(CourseData courseData) {
        try {
            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(courseData),
                    new ParameterizedTypeReference<CourseData>() {
            });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }

    public void update(Long id, CourseData courseData) {
        try {
            courseData.setId(id);
            restTemplate.exchange(
                    url.concat("/" + id),
                    HttpMethod.PUT,
                    new HttpEntity<>(courseData),
                    new ParameterizedTypeReference<CourseData>() {
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
                    new ParameterizedTypeReference<CourseData>() {
            });
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
}
