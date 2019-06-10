package luizalabs.Models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GroupedItems {
  @Getter @Setter private List<Group> data;

  public GroupedItems(List<Group> groupList) {
    this.data = groupList;
  }
}
