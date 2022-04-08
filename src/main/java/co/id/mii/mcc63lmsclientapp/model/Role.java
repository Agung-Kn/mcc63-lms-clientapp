package co.id.mii.mcc63lmsclientapp.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

  private Long id;
  private String name;
  private List<Privilege> privileges;
}
