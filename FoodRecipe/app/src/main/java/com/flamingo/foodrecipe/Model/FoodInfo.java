package com.flamingo.foodrecipe.Model;

public class FoodInfo {
    private String itemName;
    private String itemDescription;
    private String itemType;
    private String itemImage;
    private String key;

    public FoodInfo(){}

    public FoodInfo(String itemName, String itemDescription, String itemType, String itemImage) {
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

    public String getItemImage() {
        return itemImage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
