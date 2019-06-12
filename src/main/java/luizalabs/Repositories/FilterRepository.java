package luizalabs.Repositories;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import luizalabs.Models.Filter;
import luizalabs.Models.Item;
import luizalabs.Models.Items;

public class FilterRepository {
  
  public static List<Filter> getFilters(String filterString) {
    List<Filter> filters = new ArrayList<Filter>();
    if (filterString != null) {
      String[] filterStrings = filterString.split(";");
      for(String filter : filterStrings) {
        String[] filterData = filter.split("=");
        if (filterData.length > 1) {
          String fieldName = filterData[0];
          String fieldValue = filterData[1];
          Filter newFilter = new Filter(fieldName, fieldValue);
          filters.add(newFilter);
        }
      }
    }
    return filters;
  }
  
  public static Items applyFiltersToObject(List<Filter> filters, Items items) throws NoSuchFieldException {
    List<Item> itemsList = items.getItems();
    Class<Item> itemClass = Item.class;
    
    for(Filter filter : filters) {
      Iterator<Item> iterator = itemsList.iterator();
      while(iterator.hasNext()) {
        Item item = iterator.next();
        
        Field field;
        try {
          field = itemClass.getDeclaredField(filter.getKey());
          field.setAccessible(true);
          Object itemValue = field.get(item);
          
          if (!filter.getValue().equals(String.valueOf(itemValue))) {
            iterator.remove();
          }
        } catch (Exception e) {
          throw new NoSuchFieldException("O campo " + filter.getKey() + " não existe ou não pode ser acessado!");
        }         
      }
    }
    items.setItems(itemsList);
    return items;
  }
}
