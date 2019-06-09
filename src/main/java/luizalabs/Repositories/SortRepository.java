package luizalabs.Repositories;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import luizalabs.Models.Item;
import luizalabs.Models.Items;

public class SortRepository {
  
  public static Map<String, String> getFilters(Items items) {
    Map<String, String> filters = new HashMap<>();
    if (items.getFilter() != null) {
      String[] filterStrings = items.getFilter().split(";");
      for(String filter : filterStrings) {
        String[] filterData = filter.split("=");
        String fieldName = filterData[0];
        String fieldValue = filterData[1];
        filters.put(fieldName, fieldValue);
      }
    }    
    return filters;
  }
  
  public static Items applyFiltersToObject(Map<String, String> filters, Items items) throws NoSuchFieldException {
    List<Item> itemsList = items.getItems();
    Class<Item> itemClass = Item.class;
    
//    for(Item item: itemsList) {
//      itemsList.remove(item);
//    }
//    
//    itemsList.stream().forEach(item -> {
//      
//    });
    
    for(Map.Entry<String, String> filter : filters.entrySet()) {
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
