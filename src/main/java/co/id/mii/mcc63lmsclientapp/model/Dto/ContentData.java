/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.model.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Agung
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentData {

    private Long id;
    private String title;
    private String description;
    private Long moduleId;
    private MultipartFile file;
}
