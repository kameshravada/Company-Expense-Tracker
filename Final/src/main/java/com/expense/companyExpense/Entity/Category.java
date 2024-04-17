package com.expense.companyExpense.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Category")
public class Category {

    @Id
    private String catCode;

    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    List<Expenditure> expenditure;

    
    public Category() {
    }


    public Category(String catCode, String categoryName) {
        this.catCode = catCode;
        this.categoryName = categoryName;   
    }

    public String getCatCode() {
        return catCode;
    }


    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }


    public String getCategoryName() {
        return categoryName;
    }


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    @Override
    public String toString() {
        return "Category [catCode=" + catCode + ", categoryName=" + categoryName + "]";
    }

}
