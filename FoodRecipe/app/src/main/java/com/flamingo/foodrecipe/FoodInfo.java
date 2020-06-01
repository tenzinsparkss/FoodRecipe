package com.flamingo.foodrecipe;

public class FoodInfo {

    private String itemName;
    private String itemDescription;
    private String itemType;
    private int itemImage;

    public FoodInfo(String itemName, String itemDescription, String itemType, int itemImage) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemType = itemType;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemType() {
        return itemType;
    }

    public int getItemImage() {
        return itemImage;
    }
}
