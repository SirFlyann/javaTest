package luizalabs.Controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import luizalabs.Models.Items;
import luizalabs.Repositories.FilterRepository;

@RestController
public class SortController {
  
  @RequestMapping(
      value="/sort", 
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> sort(@RequestBody Items items) {
    Map<String, String> filters = FilterRepository.getFilters(items);
    try {
      Items filteredItems = FilterRepository.applyFiltersToObject(filters, items);
      return new ResponseEntity<Items>(filteredItems, HttpStatus.OK);
    } catch(NoSuchFieldException e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}