package com.expense.companyExpense.DTO;

public class ExpenseByCatTotal {
    private String catCode;
    private double totalAmount;
    public ExpenseByCatTotal() {
    }
    
    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
