package com.expense.companyExpense.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.expense.companyExpense.Entity.Department;
import com.expense.companyExpense.Repository.DepartmentRepo;


@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentRepo departmentRepo;


    //All
    
    @GetMapping("/all")
    public ResponseEntity<?> getDepartments(){
        List<Department>departments = departmentRepo.findAll();
        if (departments.isEmpty()) {
            return new ResponseEntity<>("data not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(departments,HttpStatus.OK);
    }

    //find by deptcpde
    @GetMapping("/{id}")
        public ResponseEntity<?> getCategoryById(@PathVariable("id") String dept_code) {
        try {
            Department department = departmentRepo.findById(dept_code).orElse(null);
            if (department != null) {
                return ResponseEntity.ok(department);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        }

    //Add

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addDeparments(@RequestBody Department department){
        if (department.getDeptCode()==null) {
            return new ResponseEntity<>("enter dept_code",HttpStatus.BAD_REQUEST);
        }
        if (department.getDeptName()==null) {
            return new ResponseEntity<>("enter dept_name",HttpStatus.BAD_REQUEST);
        }
        if (department.getHod()==null) {
            return new ResponseEntity<>("enter Hod name",HttpStatus.BAD_REQUEST);
        }
        if (department.getDeptCode()!=null&&department.getDeptName()!=null&&department.getHod()!=null) {
                departmentRepo.save(department);
        }
        else{
            return new ResponseEntity<>("Enter all information [dept_code,dept_name,hod]",HttpStatus.BAD_REQUEST);
        }
        return  ResponseEntity.status(HttpStatus.OK).body("Department added successfully");
    }

    //Put

    @PutMapping("/update/{dept_code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDepartments(@PathVariable("dept_code")String dept_code,@RequestBody Department department){
        try{
            if (!departmentRepo.existsById(dept_code)) {
                return new ResponseEntity<>("No department found ",HttpStatus.NOT_FOUND);
            }
        if (dept_code!=null&&departmentRepo.existsById(dept_code)) {
            Department dep = departmentRepo.findById(dept_code).get();
             
            if (department.getDeptName()!=null) {
                dep.setDeptName(department.getDeptName());
            }
            if (department.getHod()!=null) {
                dep.setHod(department.getHod());
            }
            departmentRepo.save(dep);
        }
        return ResponseEntity.ok().body("Department with deptcode "+dept_code+" Updated Successfully");
        }
        catch(Exception e ){
            return ResponseEntity.internalServerError().build();
        }
    }

    //Delete
    @DeleteMapping("/del/{dept_code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delDepartments(@PathVariable("dept_code") String dept_code){
        try{
            if (!departmentRepo.existsById(dept_code)) {
                return new ResponseEntity<>("No department found ",HttpStatus.NOT_FOUND);
            }
        if (dept_code!=null && departmentRepo.existsById(dept_code)) {
            departmentRepo.deleteById(dept_code);
        }
        return ResponseEntity.ok().body("Department with deptcode "+dept_code+" is deleted successfully");
    }
    catch(Exception e ){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
    }
    
    // addAll
    @PostMapping("/addAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAllDeparments(@RequestBody List<Department> department){
        departmentRepo.saveAll(department);
        return  ResponseEntity.status(HttpStatus.OK).body("Department added successfully");
    }
}
