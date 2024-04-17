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

import com.expense.companyExpense.Entity.Category;
import com.expense.companyExpense.Repository.CategoryRepo;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
     CategoryRepo categoryRepo;

    //All

    @GetMapping("/all")
    public ResponseEntity<?> getCategories(){
        List<Category> categories = categoryRepo.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>("data not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    //find by catcode

        @GetMapping("/{id}")
        public ResponseEntity<?> getCategoryById(@PathVariable("id") String cat_code) {
        try {
            Category category = categoryRepo.findById(cat_code).orElse(null);
            if (category != null) {
                return ResponseEntity.ok(category);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("category not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        }

    //Add

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCategories(@RequestBody Category category){
        if (category.getCatCode()==null) {
            return new ResponseEntity<>("enter catCode",HttpStatus.BAD_REQUEST);
        }
        if (category.getCategoryName()==null) {
            return new ResponseEntity<>("enter categoryName",HttpStatus.BAD_REQUEST);
        }
        if (category.getCatCode()!=null && category.getCategoryName()!=null) {
            categoryRepo.save(category);
        }
        else
        {
            return new ResponseEntity<>("Provide all information [catcode,categoryName]",HttpStatus.BAD_REQUEST);
        }
        return  ResponseEntity.status(HttpStatus.OK).body("Category added successfully");
    }

    //Put

    @PutMapping("/update/{cat_code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategories(@PathVariable("cat_code") String catCode,@RequestBody Category category){
        try{
            if (!categoryRepo.existsById(catCode)) {
                return new ResponseEntity<>("No category found ",HttpStatus.NOT_FOUND);
            }
        if (catCode!=null&&categoryRepo.existsById(catCode)) {
            Category cat = categoryRepo.findById(catCode).get();
            if (category.getCategoryName()!=null) {
                cat.setCategoryName(category.getCategoryName());
            }
            categoryRepo.save(cat);
        }
        return ResponseEntity.ok().body("Category with catcode "+catCode+" Updated Successfully");
    }
    catch(Exception e ){
        return ResponseEntity.internalServerError().build();
    }
    }

    //Delete
    
    @DeleteMapping("/del/{catCode}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delCategories(@PathVariable("catCode") String catCode){
        try{
            if (!categoryRepo.existsById(catCode)) {
                return new ResponseEntity<>("No category found ",HttpStatus.NOT_FOUND);
            }
        if (catCode!=null&&categoryRepo.existsById(catCode)) {
            categoryRepo.deleteById(catCode);
        }
        return ResponseEntity.ok().body("Category with catcode "+catCode+" is deleted successfully");
    }
    catch(Exception e ){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
    }

    // addAll
    @PostMapping("/addAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAllCategories(@RequestBody List<Category> categories){
        categoryRepo.saveAll(categories);
        return  ResponseEntity.status(HttpStatus.OK).body("Categories added successfully");
    }
}


