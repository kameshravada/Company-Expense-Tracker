package com.expense.companyExpense.DTO;

public class ExpenseSummaryForEachCategory {
    
    private String catCode;
    private double totalExp;
    public ExpenseSummaryForEachCategory() {
    }
    public String getCatCode() {
        return catCode;
    }
    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }
    public double getTotalExp() {
        return totalExp;
    }
    public void setTotalExp(double totalExp) {
        this.totalExp = totalExp;
    }
    

    
}
