package luizalabs.Repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import luizalabs.Models.Group;
import luizalabs.Models.Item;
import luizalabs.Utils.Reflection;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

public class GroupRepository {
  
  public static List<String> getGroupByFields(String groupByString) {
    String[] groupByStrings = new String[] {};
    if (groupByString != null) {
      groupByStrings = groupByString.split(";");
    } else {
      groupByStrings = new String[] { "ean", "title", "brand" };
    }
    List<String> groupByList = Arrays.asList(groupByStrings);
    return groupByList;
  }

  public static List<Group> groupItemsUsingAttributes(List<Item> itemsList, List<String> groupByAttributes) throws NoSuchFieldException {
    List<Group> groupList = new ArrayList<Group>();
    for (String attribute : groupByAttributes) {
      try {
        HashMap<String, List<Item>> itemGroup = groupItemsByAttribute(itemsList, attribute);
        int itemGroupSize = getItemGroupSize(itemGroup);
        if (itemGroupSize < itemsList.size()) {
          groupList = createGroupsFromHashMap(itemGroup);
          break;
        }
      } catch(NoSuchFieldException e) {
        throw new NoSuchFieldException("Impossível agrupar por um campo inexistente!");
      }
    }
    return groupList; 
  }
  
  private static int getItemGroupSize(HashMap<String, List<Item>> itemGroup) {
    List<String> itemGroupKeys = new ArrayList<String>();
    for (String key : itemGroup.keySet()) {
      itemGroupKeys.add(key);
    }
    return itemGroupKeys.size();
  }
  
  private static List<Group> createGroupsFromHashMap(HashMap<String, List<Item>> itemGroup) {
    List<Group> groupList = new ArrayList<Group>();
    for (Entry<String, List<Item>> entry : itemGroup.entrySet()) {
      Group newGroup = new Group(entry.getKey(), entry.getValue());
      groupList.add(newGroup);
    }
    return groupList;
  }
  
  private static HashMap<String, List<Item>> groupItemsByAttribute(List<Item> items, String attribute) throws NoSuchFieldException {
    HashMap<String, List<Item>> itemGroup = new HashMap<String, List<Item>>();
    for (Item item : items) {
      try {
        String attributeValue = Reflection.getFieldValue(attribute, item);
        String groupName = attributeValue;
        if (attribute.equals("title")) {
          List<String> possibleGroups = getAllPossibleGroupNames(itemGroup);
          groupName = getBestGroupName(attributeValue, possibleGroups);
        }
        itemGroup = addFieldsToOldGroupOrCreateNew(itemGroup, groupName, item);
      } catch(NoSuchFieldException e) {
        throw new NoSuchFieldException("Houve um erro ao recuperar o valor do atributo " + attribute);
      }
    }
    return itemGroup;
  }
  
  private static String getBestGroupName(String subject, List<String> possibleGroups) {
    if (possibleGroups.size() > 1) {
      ExtractedResult bestGroupMatch = FuzzySearch.extractOne(subject, possibleGroups);
      if (bestGroupMatch.getScore() > 70) {
        return bestGroupMatch.getString();
      }
    } 
    return subject;
  }
  
  private static List<String> getAllPossibleGroupNames(HashMap<String, List<Item>> itemGroup) {
    List<String> possibleGroupNames = new ArrayList<String>();
    for (String key : itemGroup.keySet()) {
      possibleGroupNames.add(key);
    }
    return possibleGroupNames;
  }
  
  private static HashMap<String, List<Item>> addFieldsToOldGroupOrCreateNew(HashMap<String, List<Item>> oldGroups, String groupName, Item newItem) {
    HashMap<String, List<Item>> newItemGroup = oldGroups;
    if (!newItemGroup.containsKey(groupName)) {
      List<Item> itemGroupList = new ArrayList<Item>();
      itemGroupList.add(newItem);
      
      newItemGroup.put(groupName, itemGroupList);
    } else {
      newItemGroup.get(groupName).add(newItem);
    }
    return newItemGroup;
  }

}
