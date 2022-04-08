/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package co.id.mii.mcc63lmsclientapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Agung
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    private Long id;
    private String title;
    private String description;
    private Long price;
    private Boolean isActive;
    private String courseImage;
    private User user;
    private Category category;
}
