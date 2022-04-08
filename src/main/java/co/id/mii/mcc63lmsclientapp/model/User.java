package co.id.mii.mcc63lmsclientapp.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private Long id;
  private String email;
  private String password;
  private Boolean isAccountLocked;
  private Boolean isEnabled;
  private Profile profile;
  private List<Role> roles;
}