package com.expense.companyExpense.DTO;

import java.time.LocalDate;

public class ExpenditureDTO {
    private Long expId;
    private String catCode;
    private String deptCode;
    private Double amount;
    private LocalDate expDate;
    private String authorizedBy;
    private String description;
    private String paymentCode;
    private String remarks;
    public ExpenditureDTO() {
    }
    public Long getExpId() {
        return expId;
    }
    public void setExpId(Long expId) {
        this.expId = expId;
    }
    public String getCatCode() {
        return catCode;
    }
    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }
    public String getDeptCode() {
        return deptCode;
    }
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public LocalDate getExpDate() {
        return expDate;
    }
    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }
    public String getAuthorizedBy() {
        return authorizedBy;
    }
    public void setAuthorizedBy(String authorizedBy) {
        this.authorizedBy = authorizedBy;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPaymentCode() {
        return paymentCode;
    }
    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    
    
}
