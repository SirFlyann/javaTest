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
import luizalabs.Models.Group;
import luizalabs.Models.Item;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SortRepositoryTest {
  
  @Test
  public void getOrderByFiltersShouldReturnAListOfFilterGivenAString() {
    String testString = "id:asc;price:desc";
    
    List<Filter> expectedFilters = new ArrayList<Filter>();
    Filter filter1 = new Filter("id", "asc");
    Filter filter2 = new Filter("price", "desc");
    expectedFilters.add(filter1);
    expectedFilters.add(filter2);
    
    List<Filter> actualResult = SortRepository.getOrderByFilters(testString);
    
    assertEquals(expectedFilters.size(), actualResult.size());
    
    testEachFilter(expectedFilters, actualResult);
  }

  @Test
  public void getOrderByFiltersShouldReturnAListOfFilterGivenNull() {
    List<Filter> expectedFilters = new ArrayList<Filter>();
    Filter filter1 = new Filter("stock", "desc");
    Filter filter2 = new Filter("price", "asc");
    expectedFilters.add(filter1);
    expectedFilters.add(filter2);
    
    List<Filter> actualResult = SortRepository.getOrderByFilters(null);
    
    assertEquals(expectedFilters.size(), actualResult.size());
    
    testEachFilter(expectedFilters, actualResult);
  }
  
  @Test
  public void sortGroupsUsingFiltersShouldReturnAnListOfGroupsWithTheirItemsOrdered() {
    List<Group> testGroups = new ArrayList<Group>();
    
    List<Item> itemList1 = new ArrayList<Item>();
    Item item1 = new Item("2", "123", "foo", "bar", 5, 0);
    Item item2 = new Item("1", "123", "foo", "bar", 10, 0);
    Item item3 = new Item("1", "123", "foo", "bar", 7, 0);
    itemList1.add(item1);
    itemList1.add(item2);
    itemList1.add(item3);
    Group group1 = new Group("123", itemList1);
    testGroups.add(group1);

    List<Filter> testFilters = new ArrayList<Filter>();
    Filter filter1 = new Filter("id", "asc");
    Filter filter2 = new Filter("price", "desc");
    testFilters.add(filter1);
    testFilters.add(filter2);
    
    List<Group> expectedResult = new ArrayList<Group>();
    List<Item> expectedItemList = new ArrayList<Item>();
    expectedItemList.add(0, item2);
    expectedItemList.add(1, item3);
    expectedItemList.add(2, item1);
    Group expectedGroup = new Group("123", expectedItemList);
    expectedResult.add(expectedGroup);
    
    List<Group> actualResult = SortRepository.sortGroupsUsingFilters(testGroups, testFilters);
    testEachGroup(expectedResult, actualResult);
    
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
  
  public void testEachItem(List<Item> expectedItems, List<Item> actualItems) {
    for(int i = 0; i < 3; i++) {
      Item expectedItem = expectedItems.get(i);
      Item actualItem = actualItems.get(i);
      
      testSingleItem(expectedItem, actualItem);
    }
  }
  
  public void testSingleItem(Item expectedItem, Item actualItem) {
    assertThat(expectedItem.getId().equals(actualItem.getId())).isTrue();
    assertThat(expectedItem.getEan().equals(actualItem.getEan())).isTrue();
    assertThat(expectedItem.getTitle().equals(actualItem.getTitle())).isTrue();
    assertThat(expectedItem.getBrand().equals(actualItem.getBrand())).isTrue();
    assertThat(expectedItem.getPrice() == actualItem.getPrice()).isTrue();
    assertThat(expectedItem.getStock() == actualItem.getStock()).isTrue();
  }
  
  public void testEachGroup(List<Group> expectedGroups, List<Group> actualGroups) {
    Iterator<Group> expectedIterator = expectedGroups.iterator();
    Iterator<Group> actualIterator = actualGroups.iterator();
    while(expectedIterator.hasNext()) {
      Group expectedGroup = expectedIterator.next();
      Group actualGroup = actualIterator.next();

      assertThat(expectedGroup.getDescription().equals(actualGroup.getDescription())).isTrue();
      testEachItem(expectedGroup.getItems(), actualGroup.getItems());
    }
    
  }

}
