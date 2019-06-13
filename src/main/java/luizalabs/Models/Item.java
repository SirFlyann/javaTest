package luizalabs.Models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Item implements Serializable {
  
  public Item(String id, String ean, String title, String brand, double price, int stock) {
    this.id = id;
    this.ean = ean;
    this.title = title;
    this.brand = brand;
    this.price = price;
    this.stock = stock;
  }
  
  private static final long serialVersionUID = 1L;
  @Getter @Setter private String id;
  @Getter @Setter private String ean;
  @Getter @Setter private String title;
  @Getter @Setter private String brand;
  @Getter @Setter private double price;
  @Getter @Setter private int stock;
}