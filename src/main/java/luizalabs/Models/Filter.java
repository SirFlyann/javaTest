package luizalabs.Models;

import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import luizalabs.Utils.Reflection;
import luizalabs.Utils.Types;

public class Filter {
  
  public Filter(String key, String value) {
    this.key = key;
    this.value = value;
  }
  
  
  @Getter @Setter private String key;
  
  @Getter @Setter private String value;
  
  public Comparator<Item> itemComparator() {
    return (item1, item2) -> {
      try {
        String value1 = Reflection.getFieldValue(this.key, item1);
        String value2 = Reflection.getFieldValue(this.key, item2);

        if (Types.isString(value1)) {
          if (this.value.equals("asc")) {
            return value1.compareTo(value2);
          }
          return value2.compareTo(value1);
        } else {
          double doubleValue1 = Double.parseDouble(value1);
          double doubleValue2 = Double.parseDouble(value2);

          if (this.value.equals("asc")) {
            return (int) Math.round((doubleValue1 - doubleValue2));
          }
            return (int) Math.round((doubleValue2 - doubleValue1));
        }
      } catch (NoSuchFieldException e) {
        return 0;
      }
    };
  }
  
  public static Comparator<Item> chainedItemComparators(List<Filter> filters) {
    return filters.stream()
        .map(Filter::itemComparator)
        .reduce((item1,  item2) -> 0, (f1, f2) -> f1.thenComparing(f2));
  }
}
