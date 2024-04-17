package com.expense.companyExpense.DTO;

public class ExpenseInMonthDTO {
    private String deptCode;
    private String catCode;
    private double totalAmount;

    public ExpenseInMonthDTO() {
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
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
