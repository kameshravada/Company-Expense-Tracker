package com.expense.companyExpense.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.companyExpense.Entity.Department;

public interface DepartmentRepo extends JpaRepository<Department,String>{

    List<Department> findAllByHod(String name);
    
}
