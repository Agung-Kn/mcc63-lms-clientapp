package co.id.mii.mcc63lmsclientapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

  private Long id;
  private String fullName;
  private String phoneNumber;
  private String bio;
  private String organization;
}
