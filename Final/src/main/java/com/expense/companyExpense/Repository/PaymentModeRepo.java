package com.expense.companyExpense.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.companyExpense.Entity.PaymentMode;

public interface PaymentModeRepo extends JpaRepository<PaymentMode,String>{
    
}
