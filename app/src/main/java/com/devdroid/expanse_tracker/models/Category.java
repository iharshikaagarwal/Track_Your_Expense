package com.devdroid.expanse_tracker.models;

import java.util.Locale;


public class Category {
    private String categoryName;
    private int categoryImage;
    private  int categoryColor;
    public Category(){

    }


    public Category(int categoryImage , String categoryName, int categoryColor) {
        this.categoryName = categoryName;
        this.categoryImage=categoryImage;
        this.categoryColor = categoryColor;
    }


    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryNamee) {
        this.categoryName = categoryName;
    }

    public int getCategoryColor() {
        return categoryColor;
    }
    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
    }
}
