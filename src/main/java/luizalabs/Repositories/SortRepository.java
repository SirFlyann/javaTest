package luizalabs.Repositories;

import java.util.ArrayList;
import java.util.List;

import luizalabs.Models.Filter;
import luizalabs.Models.Group;

public class SortRepository {
  public static List<Filter> getOrderByFilters(String filterString) {
    List<Filter> filters;
    String[] orderByStrings = new String[] {};
    if (filterString != null) {
      orderByStrings = filterString.split(";");
    } else {
      orderByStrings = new String[] { "stock:desc", "price:asc" };
    }
    filters = getFiltersFromStringArray(orderByStrings);
    return filters;
  }
  
  private static List<Filter> getFiltersFromStringArray(String[] filters) {
    List<Filter> filtersList = new ArrayList<Filter>();
    for(String orderByString : filters) {
      Filter newOrderFilter = new Filter(orderByString.split(":")[0], orderByString.split(":")[1]);
      filtersList.add(newOrderFilter);
    }
    return filtersList;
  }

  public static List<Group> sortGroupsUsingFilters(List<Group> groupList, List<Filter> filters) {
    for(Group group : groupList) {
      group.getItems().sort(Filter.chainedItemComparators(filters));
    }
    return groupList;
  }
}
