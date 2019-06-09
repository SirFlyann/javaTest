package luizalabs.Models;

import lombok.Getter;
import lombok.Setter;

public abstract class Filter {
  @Getter @Setter private String key;
  
  @Getter @Setter private String value;
}
