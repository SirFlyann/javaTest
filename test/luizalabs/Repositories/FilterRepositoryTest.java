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

}
