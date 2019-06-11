package luizalabs.Repositories;

import java.util.ArrayList;
import java.util.List;

import luizalabs.Models.Filter;
import luizalabs.Models.Group;
import luizalabs.Models.GroupedItems;
import luizalabs.Models.Items;

public class SortRepository {
  public static List<Filter> getOrderByFilters(Items items) {
    List<Filter> filters = new ArrayList<Filter>();
    String[] orderByStrings = new String[] {};
    if (items.getOrderBy() != null) {
      orderByStrings = items.getOrderBy().split(";");
    } else {
      orderByStrings = new String[] { "stock:desc", "price:asc" };
    }
    for(String orderByString : orderByStrings) {
      String fieldName = orderByString.split(":")[0];
      String fieldValue = orderByString.split(":")[1];
      Filter newOrderFilter = new Filter(fieldName, fieldValue);
      filters.add(newOrderFilter);
    }
    return filters;
  }

  public static GroupedItems sortGroupsUsingFilters(GroupedItems groups, List<Filter> filters) throws NoSuchFieldException{
    List<Group> groupList = groups.getData();
    for(Group group : groupList) {
      group.getItems().sort(Filter.chainedItemComparators(filters));
//      group.getItems().sort(Comparator.comparing(Item::getStock).reversed()
//          .thenComparing(Comparator.comparing(Item::getPrice)));
    }
    return new GroupedItems(groupList);
  }
  

}
