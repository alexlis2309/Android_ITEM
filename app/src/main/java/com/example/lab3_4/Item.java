package com.example.lab3_4;
public class Item {


    private static String name;
    private static String size;
    private static String tag;
    private static String description;
    private static String Author;
    private static String saleRent;

    public static String getType() {
        return name;
    }

    public static void setType(String name) {
        Item.name = name;
    }

    public static String getArea() {
        return size;
    }

    public static void setArea(String size) {
        Item.size = size;
    }

    public static String getLocation() {
        return tag;
    }

    public static void setLocation(String tag) {
        Item.tag = tag;
    }

    public static String getPrice() {
        return description;
    }

    public static void setPrice(String description) {
        Item.description = description;
    }

    public static String getState() {
        return Author;
    }

    public static void setState(String Author) {
        Item.Author = Author;
    }

    public static String getSaleRent() {
        return saleRent;
    }

    public static void setSaleRent(String saleRent) {
        Item.saleRent = saleRent;
    }
}
