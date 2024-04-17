package com.expense.companyExpense.DTO;

public class ExpenseByDeptTotal {
    private String deptCode;
    private double totalAmount;
    public ExpenseByDeptTotal() {
    }
    public String getDeptCode() {
        return deptCode;
    }
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    

}
