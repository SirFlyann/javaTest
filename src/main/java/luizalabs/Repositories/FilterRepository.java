package luizalabs.Repositories;

import java.util.ArrayList;
import java.util.List;
import luizalabs.Models.Filter;
import luizalabs.Models.Item;
import luizalabs.Utils.Reflection;

public class FilterRepository {
  
  public static List<Filter> getFilters(String filterString) {
    List<Filter> filters = new ArrayList<Filter>();
    if (filterString != null) {
      String[] filterStrings = filterString.split(";");
      for(String filter : filterStrings) {
        Filter newFilter = createFilterFromString(filter);
        if (newFilter.getKey() != null) {
          filters.add(newFilter);
        }
      }
    }
    return filters;
  }
  
  private static Filter createFilterFromString(String filter) {
    String[] filterData = filter.split("=");
    if (filterData.length > 1) {
      return new Filter(filterData[0], filterData[1]);
    }
    return new Filter(null, null);
  }
  
  public static List<Item> applyFiltersToObject(List<Filter> filters, List<Item> itemsList) throws NoSuchFieldException {
    for(Filter filter : filters) {
      for(Item item : itemsList) {
        try {
          String value = Reflection.getFieldValue(filter.getKey(), item);
          if (!filter.getValue().equals(value)) {
            itemsList.remove(item);
          }
        } catch (Exception e) {
          throw new NoSuchFieldException("O campo " + filter.getKey() + " não existe ou não pode ser acessado!");
        }         
      }
    }
    return itemsList;
  }
}
