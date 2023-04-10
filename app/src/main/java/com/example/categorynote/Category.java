package com.example.categorynote;

public class Category {

    String categoryName ;
    String categoryId;


//    public Category(){}

    public Category(String categoryId,String categoryName) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }

    public String getId() {
        return categoryId;
    }

    public void setId(String categoryId) {
        this.categoryId = categoryId;
    }



}
