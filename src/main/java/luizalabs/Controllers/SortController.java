package luizalabs.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import luizalabs.Models.GroupedItems;
import luizalabs.Models.Item;
import luizalabs.Models.Filter;
import luizalabs.Models.Group;
import luizalabs.Models.Items;
import luizalabs.Repositories.FilterRepository;
import luizalabs.Repositories.GroupRepository;
import luizalabs.Repositories.SortRepository;

@RestController
public class SortController {
  
  @RequestMapping(
      value="/sort", 
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> sort(@RequestBody Items items) {
    List<Filter> filters = new ArrayList<Filter>();
    if (items.getFilter() != null) {
      filters = FilterRepository.getFilters(items.getFilter());
    }
    List<Filter> orderByFilters = SortRepository.getOrderByFilters(items.getOrderBy());
    try {
      List<Item> filteredItemsList = FilterRepository.applyFiltersToObject(filters, items.getItems());
      Items filteredItems = new Items(filteredItemsList);
      
      List<String> groupAttributes = GroupRepository.getGroupByFields(items.getGroupBy());
      GroupedItems groupedItems = new GroupedItems(GroupRepository.groupItemsUsingAttributes(filteredItems.getItems(), groupAttributes));
      List<Group> groupedItemsList = SortRepository.sortGroupsUsingFilters(groupedItems.getData(), orderByFilters);
      GroupedItems sortedItems = new GroupedItems(groupedItemsList);
      return new ResponseEntity<GroupedItems>(sortedItems, HttpStatus.OK);
    } catch(NoSuchFieldException e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
