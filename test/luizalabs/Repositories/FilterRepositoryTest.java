package luizalabs.Repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import luizalabs.Models.Filter;
import luizalabs.Models.Item;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilterRepositoryTest {
  
  @Test
  public void getFiltersShouldReturnAListOfFiltersGivenAGoodString() {
    String testString = "id=123;name=foo";
    Filter filter1 = new Filter("id", "123");  
    Filter filter2 = new Filter("name", "foo"); 
    List<Filter> expectedResult = new ArrayList<Filter>();
    expectedResult.add(filter1);
    expectedResult.add(filter2);
    
    List<Filter> actualResult = FilterRepository.getFilters(testString);
    
    assertEquals(actualResult.size(), expectedResult.size());   
    testEachFilter(expectedResult, actualResult);
    
  }
  
  @Test
  public void getFiltersShouldReturnAnEmptyListGivenABadString() {
    String testString = "foobar";
    List<Filter> expectedResult = new ArrayList<Filter>();
    List<Filter> actualResult = FilterRepository.getFilters(testString);
    
    assertEquals(actualResult.size(), expectedResult.size());   
    testEachFilter(expectedResult, actualResult);
    
  }
  
  @Test
  public void getFiltersShouldReturnAnEmptyStringGivenNoString() {
    List<Filter> expectedResult = new ArrayList<Filter>();
    List<Filter> actualResult = FilterRepository.getFilters(null);
    
    assertEquals(actualResult.size(), expectedResult.size());   
    testEachFilter(expectedResult, actualResult);
  }
  
  public void testEachFilter(List<Filter> expectedResult, List<Filter> actualResult) {
    Iterator<Filter> expectedIterator = expectedResult.iterator();
    Iterator<Filter> actualIterator = actualResult.iterator();
    while(expectedIterator.hasNext()) {
      Filter expectedFilter = expectedIterator.next();
      Filter actualFilter = actualIterator.next();

      assertThat(expectedFilter.getKey().equals(actualFilter.getKey())).isTrue();
      assertThat(expectedFilter.getValue().equals(actualFilter.getValue())).isTrue();
    }
  }
  
  @Test
  public void applyFiltersToObjectShouldReturnFilteredItems() {
    List<Filter> testFilters = new ArrayList<Filter>();
    Filter filter1 = new Filter("id", "123");
    testFilters.add(filter1);
    
    
    List<Item> testItemsList = new ArrayList<Item>();
    Item goodItem = new Item("123", null, null, null, 0, 0);
    Item badItem = new Item("foo", null, null, null, 0, 0);
    testItemsList.add(goodItem);
    testItemsList.add(badItem);
    
    List<Item> expectedResult = new ArrayList<Item>();
    expectedResult.add(goodItem);
    
    try {
      List<Item> actualResult = FilterRepository.applyFiltersToObject(testFilters, testItemsList);
      assertEquals(expectedResult.size(), actualResult.size());
      
      testEachItem(expectedResult, actualResult);
    } catch(Exception e) {}
  }
  
  public void testEachItem(List<Item> expectedItems, List<Item> actualItems) {
    Iterator<Item> expectedIterator = expectedItems.iterator();
    Iterator<Item> actualIterator = actualItems.iterator();
    while(expectedIterator.hasNext()) {
      Item expectedFilter = expectedIterator.next();
      Item actualFilter = actualIterator.next();

      assertThat(expectedFilter.getId().equals(actualFilter.getId())).isTrue();
      assertThat(expectedFilter.getEan().equals(actualFilter.getEan())).isTrue();
      assertThat(expectedFilter.getTitle().equals(actualFilter.getTitle())).isTrue();
      assertThat(expectedFilter.getBrand().equals(actualFilter.getBrand())).isTrue();
      assertThat(expectedFilter.getPrice() == actualFilter.getPrice()).isTrue();
      assertThat(expectedFilter.getStock() == actualFilter.getStock()).isTrue();
    }
  }

}
