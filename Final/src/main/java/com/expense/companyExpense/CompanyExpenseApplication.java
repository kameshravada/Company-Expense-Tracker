package com.expense.companyExpense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.expense.companyExpense.Controller.ExpenditureController;
import com.expense.companyExpense.Repository.DepartmentRepo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;



@SpringBootApplication
@OpenAPIDefinition(info = @Info(description = " project", title = "Company Expense Tracker", version = "2.0.2"))
public class CompanyExpenseApplication implements CommandLineRunner{
	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	ExpenditureController expenditureController;

	public static void main(String[] args) {
		SpringApplication.run(CompanyExpenseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("You Can Start");
		
	}

}
