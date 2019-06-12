package luizalabs.Repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import luizalabs.Models.Group;
import luizalabs.Models.Items;
import luizalabs.Models.Item;
import luizalabs.Utils.Reflection;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

public class GroupRepository {
  
  public static List<String> getGroupByFields(Items items) {
    String[] groupByStrings = new String[] {};
    if (items.getGroupBy() != null) {
      groupByStrings = items.getGroupBy().split(";");
    } else {
      groupByStrings = new String[] { "ean", "title", "brand" };
    }
    List<String> groupByList = Arrays.asList(groupByStrings);
    return groupByList;
  }

  public static List<Group> groupItemsUsingAttributes(Items items, List<String> groupByAttributes) throws NoSuchFieldException {
    List<Group> groupList = new ArrayList<Group>();
    List<Item> itemsList = items.getItems();
    for (String attribute : groupByAttributes) {
      try {
        HashMap<String, List<Item>> itemGroup = groupItemsByAttribute(itemsList, attribute);
        List<String> itemGroupKeys = new ArrayList<String>();
        for (String key : itemGroup.keySet()) {
          itemGroupKeys.add(key);
        }
        if (itemGroupKeys.size() < itemsList.size()) {
          for (Entry<String, List<Item>> entry : itemGroup.entrySet()) {
            Group newGroup = new Group(entry.getKey(), entry.getValue());
            groupList.add(newGroup);
          }
          break;
        }
      } catch(NoSuchFieldException e) {
        throw new NoSuchFieldException();
      }
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
          List<String> possibleGroups = new ArrayList<String>();
          for (String key : itemGroup.keySet()) {
            possibleGroups.add(key);
          }
          if (possibleGroups.size() > 1) {
            ExtractedResult bestGroupMatch = FuzzySearch.extractOne(attributeValue, possibleGroups);
            if (bestGroupMatch.getScore() > 70) {
              groupName = bestGroupMatch.getString();
            }
          } else {
            groupName = attributeValue;
          }
        }
        if (!itemGroup.containsKey(groupName)) {
          List<Item> itemGroupList = new ArrayList<Item>();
          itemGroupList.add(item);
          
          itemGroup.put(groupName, itemGroupList);
        } else {
          itemGroup.get(groupName).add(item);
        }
      } catch(NoSuchFieldException e) {
        throw new NoSuchFieldException("Houve um erro ao recuperar o valor do atributo " + attribute);
      }
    }
    return itemGroup;
      
  }

}
