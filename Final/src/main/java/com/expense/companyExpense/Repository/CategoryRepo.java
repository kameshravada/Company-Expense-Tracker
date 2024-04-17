package com.expense.companyExpense.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

import com.expense.companyExpense.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category,String>{

    // @Query(value = "dsufhih",nativeQuery = true)
    // findByName();

}
