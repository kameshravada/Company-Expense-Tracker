package com.expense.companyExpense.Entity;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Expenditure")
public class Expenditure {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expId;

 
    @ManyToOne

    @JoinColumn(name = "catCode")
    private Category category;

   
    @ManyToOne

    @JoinColumn(name = "deptCode")
    private Department department;

    private Double amount;

    private LocalDate expDate;
    
    
    
    private String authorizedby;

    // private String authrorizedby;

    private String description;

    @ManyToOne

    @JoinColumn(name = "paymentCode")
    private PaymentMode paymentMode;

    private String remarks;

    public Expenditure() {
    }



    public Expenditure(Category category, Department department, Double amount, LocalDate expDate, String authorizedby,
            String description, PaymentMode paymentMode, String remarks) {
        this.category = category;
        this.department = department;
        this.amount = amount;
        this.expDate = expDate;
        this.authorizedby = authorizedby;
        this.description = description;
        this.paymentMode = paymentMode;
        this.remarks = remarks;
    }

    

    public Long getExpId() {
        return expId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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

    public String getAuthorizedby() {
        return authorizedby;
    }

    public void setAuthorizedby(String authorizedby) {
        this.authorizedby = authorizedby;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Expenditure [expId=" + expId + ", category=" + category + ", department=" + department + ", amount="
                + amount + ", expDate=" + expDate + ", authorizedby=" + authorizedby + ", description=" + description + ", paymentMode="
                + paymentMode + ", remarks=" + remarks + "]";
    }

    
}
