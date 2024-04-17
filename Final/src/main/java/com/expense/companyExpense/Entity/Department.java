package com.expense.companyExpense.Entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Department")
public class Department {
    @Id
    private String deptCode;

    private String deptName;


    private String hod;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "department")
    List<Expenditure> expenditures;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "authorizedby")
    List<Expenditure> expenditures2;

    public Department() {
    }

    

   
    public Department(String deptCode, String deptName, String hod) {
        this.deptCode = deptCode;
        this.deptName = deptName;
        this.hod = hod;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getHod() {
        return hod;
    }

    public void setHod(String hod) {
        this.hod = hod;
    }


    @Override
    public String toString() {
        return "Department [deptCode=" + deptCode + ", deptName=" + deptName + ", hod=" + hod + "]";
    }
    
    

}
