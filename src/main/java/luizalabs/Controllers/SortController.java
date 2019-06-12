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
import luizalabs.Models.Filter;
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
    List<Filter> orderByFilters = SortRepository.getOrderByFilters(items);
    try {
      Items filteredItems = FilterRepository.applyFiltersToObject(filters, items);
      
      List<String> groupAttributes = GroupRepository.getGroupByFields(items);
      GroupedItems groupedItems = new GroupedItems(GroupRepository.groupItemsUsingAttributes(filteredItems, groupAttributes));
      GroupedItems sortedItems = SortRepository.sortGroupsUsingFilters(groupedItems, orderByFilters);
      return new ResponseEntity<GroupedItems>(sortedItems, HttpStatus.OK);
    } catch(NoSuchFieldException e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
