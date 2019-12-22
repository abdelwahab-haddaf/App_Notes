package com.aug.noteapp;

public class Category {
    private String idCategory;
    private String nameCategory;
    private int backgroundCategorySelected;
    private long createAt;
    private long updateAt;

    public Category() {
        // Default constructor required for calls to DataSnapshot.getValue(Category.class)
    }

    public Category(String nameCategory, int backgroundCategorySelected, long createAt, long updateAt) {
        this.nameCategory = nameCategory;
        this.backgroundCategorySelected = backgroundCategorySelected;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public int getBackgroundCategorySelected() {
        return backgroundCategorySelected;
    }

    public void setBackgroundCategorySelected(int backgroundCategorySelected) {
        this.backgroundCategorySelected = backgroundCategorySelected;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }
}
