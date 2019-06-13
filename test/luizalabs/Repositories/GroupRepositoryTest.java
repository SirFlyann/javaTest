package luizalabs.Repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import luizalabs.Models.Group;
import luizalabs.Models.Item;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepositoryTest {
  
  @Test
  public void getGroupByFieldsShouldReturnAListOfStringsGivenAGoodString() {
    String testString = "foo;bar";
    List<String> expectedList = new ArrayList<String>();
    expectedList.add("foo");
    expectedList.add("bar");
    
    List<String> actualList = GroupRepository.getGroupByFields(testString);
    
    assertEquals(expectedList, actualList);
  }

  @Test
  public void getGroupByFieldsShouldReturnAListOfDefaultStringsGivenNull() {
    List<String> expectedList = new ArrayList<String>();
    expectedList.add("ean");
    expectedList.add("title");
    expectedList.add("brand");
    List<String> actualList = GroupRepository.getGroupByFields(null);
    
    assertEquals(expectedList.size(), actualList.size());
  }
  
  @Test
  public void groupItemsUsingAttributesShouldGroupItemsAccordingToFilters() {
    List<Item> testItems= new ArrayList<Item>();
    Item item1 = new Item("123", null, null, null, 0, 0);
    Item item2 = new Item("123", null, null, null, 0, 0);
    Item item3 = new Item("124", null, null, null, 0, 0);
    testItems.add(item1);
    testItems.add(item2);
    testItems.add(item3);
    
    List<String> testFilters = new ArrayList<String>();
    testFilters.add("id");
    
    List<Group> expectedGroups = new ArrayList<Group>();
    List<Item> itemList1 = new ArrayList<Item>();
    itemList1.add(item1);
    itemList1.add(item2);
    Group group1 = new Group("123", itemList1);

    List<Item> itemList2 = new ArrayList<Item>();
    itemList1.add(item3);
    Group group2 = new Group("124", itemList2);
    
    expectedGroups.add(group1);
    expectedGroups.add(group2);

    try {
      List<Group> actualGroups = GroupRepository.groupItemsUsingAttributes(testItems, testFilters);
      
      testEachGroup(expectedGroups, actualGroups);
    } catch(Exception e) {}
  }
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  
  @Test
  public void groupItemsUsingAttributesShouldThrowExceptionCaseFilterIsInvalid() throws Exception {
    List<Item> testItems= new ArrayList<Item>();
    Item item1 = new Item("123", null, null, null, 0, 0);
    Item item2 = new Item("123", null, null, null, 0, 0);
    Item item3 = new Item("124", null, null, null, 0, 0);
    testItems.add(item1);
    testItems.add(item2);
    testItems.add(item3);
    
    List<String> testFilters = new ArrayList<String>();
    testFilters.add("foo");
    
    thrown.expect(NoSuchFieldException.class);
    thrown.expectMessage("Impossível agrupar por um campo inexistente!");

    GroupRepository.groupItemsUsingAttributes(testItems, testFilters);
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
