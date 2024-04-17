package com.expense.companyExpense.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PaymentMode")
public class PaymentMode {
    
    @Id
    private String paymentCode;

    private String paymentName;

    private String paymentRemarks;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "paymentMode")
    List<Expenditure> expenditures;

    public PaymentMode() {
    }

    public PaymentMode(String paymentCode, String paymentName, String paymentRemarks) {
        this.paymentCode = paymentCode;
        this.paymentName = paymentName;
        this.paymentRemarks = paymentRemarks;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPaymentRemarks() {
        return paymentRemarks;
    }

    public void setPaymentRemarks(String paymentRemarks) {
        this.paymentRemarks = paymentRemarks;
    }

    @Override
    public String toString() {
        return "PaymentMode [paymentCode=" + paymentCode + ", paymentName=" + paymentName + ", paymentRemarks="
                + paymentRemarks + "]";
    }

    
}
