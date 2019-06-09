package luizalabs.Models;

import lombok.Getter;
import lombok.Setter;

public class Filter {
  
  public Filter(String key, String value) {
    this.key = key;
    this.value = value;
  }
  
  @Getter @Setter private String key;
  
  @Getter @Setter private String value;
}
