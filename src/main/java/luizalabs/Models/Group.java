package luizalabs.Models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Group {
  public Group(String description, List<Item> itemList) {
    this.description = description;
    this.items = itemList;
  }
  @Getter @Setter private String description;
  
  @Getter @Setter private List<Item> items;
}
