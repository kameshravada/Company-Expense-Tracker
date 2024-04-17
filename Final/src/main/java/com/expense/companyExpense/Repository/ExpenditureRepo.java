package com.expense.companyExpense.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.expense.companyExpense.Entity.Category;
import com.expense.companyExpense.Entity.Department;
import com.expense.companyExpense.Entity.Expenditure;
import com.expense.companyExpense.Entity.PaymentMode;

public interface ExpenditureRepo extends JpaRepository<Expenditure,Long>{

    public List<Expenditure> findAllByAmountBetween(Double min, Double max);

    public List<Expenditure> findAllByCategory(Category category);

    Page<Expenditure> findAllByCategory(Category category,Pageable pageable);

    Page<Expenditure> findAllByPaymentMode(PaymentMode paymentMode,Pageable pageable);

    public Page<Expenditure> findAllByExpDateBetween(LocalDate date1, LocalDate date2,Pageable pageable);

    public List<Expenditure> findAllByExpDateBetween(LocalDate date1, LocalDate date2);

    public List<Expenditure> findAllByDepartmentAndExpDateBetween(Department department, LocalDate date1, LocalDate date2);

    @Query(value = "SELECT deptCode,catCode ,SUM(amount)total from expenditure where MONTH(expDate)= :month And YEAR(expDate)= :year GROUP BY catCode,deptCode",nativeQuery=true)
    public List<Object[]> findExpenditureSummaryByCategoryAndMonth(String month,String year);

    public List<Expenditure> findAllByDepartment(Department department);

    @Query(value = "SELECT dept_code,SUM(amount)total from expenditure  GROUP BY dept_code",nativeQuery = true)
    public List<Object[]> findAllByDepartmentAndTotal();

    @Query(value = "SELECT cat_code,SUM(amount)total from expenditure  GROUP BY cat_code",nativeQuery = true)
    public List<Object[]> findAllByCategoryAndTotal();

    public List<Expenditure> findAllByDescriptionContaining(String des);



}
