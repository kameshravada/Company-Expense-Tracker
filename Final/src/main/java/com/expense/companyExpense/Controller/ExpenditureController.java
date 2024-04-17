package com.expense.companyExpense.Controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.companyExpense.DTO.ExpenditureDTO;
import com.expense.companyExpense.DTO.ExpenseByCatTotal;
import com.expense.companyExpense.DTO.ExpenseByDeptTotal;
import com.expense.companyExpense.DTO.ExpenseInMonthDTO;
import com.expense.companyExpense.Entity.Category;
import com.expense.companyExpense.Entity.Department;
import com.expense.companyExpense.Entity.Expenditure;
import com.expense.companyExpense.Entity.PaymentMode;
import com.expense.companyExpense.Repository.CategoryRepo;
import com.expense.companyExpense.Repository.DepartmentRepo;
import com.expense.companyExpense.Repository.ExpenditureRepo;
import com.expense.companyExpense.Repository.PaymentModeRepo;


@RestController
@RequestMapping("/expenditure")
public class ExpenditureController {

    @Autowired
    ExpenditureRepo expenditureRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    PaymentModeRepo paymentModeRepo;

    @GetMapping("/all")
    public List<Expenditure> getExpenditures(){
        return expenditureRepo.findAll();
    }

    // POST

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addExpenditures(@RequestParam("catCode")String catCode,@RequestParam("deptCode")String deptCode,@RequestParam("paymentCode")String paymentCode,@RequestBody Expenditure expenditure){
        try{
            if (expenditure != null) {
            Expenditure exp = new Expenditure();
            if (expenditure.getAmount()!=null) {
                exp.setAmount(expenditure.getAmount());
            }else{
                return new ResponseEntity<>("enter exp_amount",HttpStatus.BAD_REQUEST);
            }
            if (expenditure.getExpDate()!=null) {
                exp.setExpDate(expenditure.getExpDate());
            }else{
                return new ResponseEntity<>("enter exp_date",HttpStatus.BAD_REQUEST);
            }
            if (expenditure.getDescription()!=null) {
                exp.setDescription(expenditure.getDescription());
            }else{
                return new ResponseEntity<>("enter description",HttpStatus.BAD_REQUEST);
            }
            if (expenditure.getRemarks()!=null) {
                exp.setRemarks(expenditure.getRemarks());
            }else{
                return new ResponseEntity<>("enter remarks",HttpStatus.BAD_REQUEST);
            }
            
            if (catCode!=null&&categoryRepo.existsById(catCode)) {
                Category cat = categoryRepo.findById(catCode).get();
                exp.setCategory(cat);
            }
            else 
            return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
            if (deptCode!=null&&departmentRepo.existsById(deptCode)) {
                Department dep = departmentRepo.findById(deptCode).get();
                exp.setAuthorizedby(dep.getHod());
            }
            else 
            return new ResponseEntity<>("Employee not found",HttpStatus.NOT_FOUND);
            if (deptCode!=null&&departmentRepo.existsById(deptCode)) {
                Department dep = departmentRepo.findById(deptCode).get();
                exp.setDepartment(dep);
            }
            else 
            return new ResponseEntity<>("Department not found",HttpStatus.NOT_FOUND);
            if (paymentCode!=null&&paymentModeRepo.existsById(paymentCode)) {
                PaymentMode pay = paymentModeRepo.findById(paymentCode).get();
                exp.setPaymentMode(pay);
            }
            else 
            return new ResponseEntity<>("Payment Mode not found",HttpStatus.NOT_FOUND);
            expenditureRepo.save(exp);  
        }
        return new ResponseEntity<>("Expenditure added successfully",HttpStatus.CREATED);
    }
    catch(Exception e ){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }

    // PUT

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateExpenditures(@PathVariable("id")Long id,@RequestParam("catCode")String catCode,@RequestParam("deptCode")String deptCode,@RequestParam("paymentCode")String paymentCode, @RequestBody Expenditure expenditure){
        try{
        if (expenditure!=null && id!=null) {
            if (expenditureRepo.existsById(id)) {
                Expenditure exp = expenditureRepo.findById(id).get();
                if (expenditure.getAmount()!=null) {
                    exp.setAmount(expenditure.getAmount());
                }
                if (expenditure.getExpDate()!=null) {
                    exp.setExpDate(expenditure.getExpDate());
                }
                if (expenditure.getAuthorizedby()!=null) {
                    Department dep = departmentRepo.findById(deptCode).get();
                    exp.setAuthorizedby(dep.getHod());
                }
                else 
                return new ResponseEntity<>("Employee not found",HttpStatus.NOT_FOUND);
                if (expenditure.getDescription()!=null) {
                    exp.setDescription(expenditure.getDescription());
                }
                if (expenditure.getRemarks()!=null) {
                    exp.setRemarks(expenditure.getRemarks());
                }

                if (catCode!=null&&categoryRepo.existsById(catCode)) {
                    Category cat = categoryRepo.findById(catCode).get();
                    exp.setCategory(cat);
                }
                else 
                return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
                if (deptCode!=null&&departmentRepo.existsById(deptCode)) {
                    Department dep = departmentRepo.findById(deptCode).get();
                    exp.setDepartment(dep);
                }
                else 
                return new ResponseEntity<>("Department not found",HttpStatus.NOT_FOUND);
                if (paymentCode!=null&&paymentModeRepo.existsById(paymentCode)) {
                    PaymentMode pay = paymentModeRepo.findById(paymentCode).get();
                    exp.setPaymentMode(pay);
                }
                else 
                return new ResponseEntity<>("Payment Mode not found",HttpStatus.NOT_FOUND);

                expenditureRepo.save(exp);
    
            }
        }
        return new ResponseEntity<>("Expenditure Updated successfully",HttpStatus.CREATED);
    }
    
        catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }


    // DELETE

    @DeleteMapping("/del/{exp_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delExpenditures(@PathVariable("expId") Long expId){
        try {
            if(expId!=null&expenditureRepo.existsById(expId)){
        expenditureRepo.deleteById(expId);
        }
        return new ResponseEntity<>("Expenditure deleted Successfully",HttpStatus.OK);
        }

        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Get all In DTO format
    @GetMapping("/DTOall")
        public ResponseEntity<?> getAll(){
        try{
        List<Expenditure> expenditures=expenditureRepo.findAll();
        if (expenditures.isEmpty()) {
            return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        }
            List<ExpenditureDTO> expendituresDto =new ArrayList<>();
            for (Expenditure expenditure : expenditures) {
                ExpenditureDTO expDto=new ExpenditureDTO();
                expDto.setExpId(expenditure.getExpId());
                expDto.setAmount(expenditure.getAmount());
                expDto.setExpDate(expenditure.getExpDate());
                expDto.setDescription(expenditure.getDescription());
                expDto.setRemarks(expenditure.getRemarks());
                expDto.setCatCode(expenditure.getCategory().getCatCode());
                expDto.setDeptCode(expenditure.getDepartment().getDeptCode());
                expDto.setPaymentCode(expenditure.getPaymentMode().getPaymentCode());
                expDto.setAuthorizedBy(expenditure.getAuthorizedby());
                expendituresDto.add(expDto);
            }
            return new ResponseEntity<>(expendituresDto,HttpStatus.OK); 
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        
    }

    // 5 Get List of Expenditures By Category with Pagenation And Sort By Id

    @GetMapping("/expenseByCategoryWithPagenationAndSortById")
    public ResponseEntity<?> getExpenditureByCategoryWithPagenation(@RequestParam("catCode")String catCode,@RequestParam("page number")int pagenum,@RequestParam("page size")int pagesize){
        try{
        Category category = categoryRepo.findById(catCode).orElse(null);
        // Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "exp_id"));
        Pageable pageable = PageRequest.of(pagenum, pagesize,Sort.by("expId"));
        List<Expenditure> l= expenditureRepo.findAllByCategory(category, pageable).getContent();
        if (l.isEmpty()) {
            return new ResponseEntity<>("Data Not Found",HttpStatus.NOT_FOUND); 
        }
        List<ExpenditureDTO> expenditureDTO = new ArrayList<>();
        for (Expenditure expenditure : l) {
            ExpenditureDTO expDto=new ExpenditureDTO();
            expDto.setExpId(expenditure.getExpId());
            expDto.setAmount(expenditure.getAmount());
            expDto.setExpDate(expenditure.getExpDate());
            expDto.setDescription(expenditure.getDescription());
            expDto.setRemarks(expenditure.getRemarks());
            expDto.setCatCode(expenditure.getCategory().getCatCode());
            expDto.setDeptCode(expenditure.getDepartment().getDeptCode());
            expDto.setPaymentCode(expenditure.getPaymentMode().getPaymentCode());
            expDto.setAuthorizedBy(expenditure.getAuthorizedby());
            expenditureDTO.add(expDto);
         }
         return new ResponseEntity<>(expenditureDTO,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 6 Get List of Expenditures By PaymentMode with Pagenation and sorting
    @GetMapping("/expenseByPaymentModeWithPagenationAndSorting")
    public ResponseEntity<?> getExpenditureByPaymentWithPagenation(@RequestParam("paymentCode")String paymentCode,@RequestParam("page number")int pagenum,@RequestParam("page size")int pagesize){
        try{
        PaymentMode paymentMode = paymentModeRepo.findById(paymentCode).orElse(null);
        // Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "exp_id"));
        Pageable pageable = PageRequest.of(pagenum, pagesize,Sort.by("expId"));
        List<Expenditure> l= expenditureRepo.findAllByPaymentMode(paymentMode, pageable).getContent();
        if (l.isEmpty()) {
            return new ResponseEntity<>("Data Not Found",HttpStatus.NOT_FOUND); 
        }
        List<ExpenditureDTO> expenditureDTO = new ArrayList<>();
        for (Expenditure expenditure : l) {
            ExpenditureDTO expDto=new ExpenditureDTO();
            expDto.setExpId(expenditure.getExpId());
            expDto.setAmount(expenditure.getAmount());
            expDto.setExpDate(expenditure.getExpDate());
            expDto.setDescription(expenditure.getDescription());
            expDto.setRemarks(expenditure.getRemarks());
            expDto.setCatCode(expenditure.getCategory().getCatCode());
            expDto.setDeptCode(expenditure.getDepartment().getDeptCode());
            expDto.setPaymentCode(expenditure.getPaymentMode().getPaymentCode());
            expDto.setAuthorizedBy(expenditure.getAuthorizedby());
            expenditureDTO.add(expDto);
         }
         return new ResponseEntity<>(expenditureDTO,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 7 Get List of Expenditures By DateBetween with Pagenation And Sort BY Date In DESC
    @GetMapping("/expenseByDateBetweenWithPagenationAndSortByDateInDESC")
    public ResponseEntity<?> getExpenditureByDateBetweenWithPagenation(@RequestParam("fromDate")LocalDate date1,@RequestParam("toDate")LocalDate date2,@RequestParam("page number")int pagenum,@RequestParam("page size")int pagesize) throws ParseException{    
        try{
        Pageable pageable = PageRequest.of(pagenum, pagesize,Sort.by(Sort.Direction.DESC, "expDate"));  
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // java.util.Date fromDate = sdf.parse(date1);
        // java.util.Date toDate = sdf.parse(date2);
        List<Expenditure> l= expenditureRepo.findAllByExpDateBetween(date1,date2,pageable).getContent();
        if (l.isEmpty()) {
            return new ResponseEntity<>("Data not found in between the given dates",HttpStatus.NOT_FOUND);  
        }
        List<ExpenditureDTO> expenditureDTO = new ArrayList<>();
        for (Expenditure expenditure : l) {
            ExpenditureDTO expDto=new ExpenditureDTO();
            expDto.setExpId(expenditure.getExpId());
            expDto.setAmount(expenditure.getAmount());
            expDto.setExpDate(expenditure.getExpDate());
            expDto.setDescription(expenditure.getDescription());
            expDto.setRemarks(expenditure.getRemarks());
            expDto.setCatCode(expenditure.getCategory().getCatCode());
            expDto.setDeptCode(expenditure.getDepartment().getDeptCode());
            expDto.setPaymentCode(expenditure.getPaymentMode().getPaymentCode());
            expDto.setAuthorizedBy(expenditure.getAuthorizedby());
            expenditureDTO.add(expDto);
         }
         return new ResponseEntity<>(expenditureDTO,HttpStatus.OK); 
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 8 List expenses summary (total amount) for each category in a given month
    @GetMapping("/expenseBySummaryInMonth")
    public ResponseEntity<?> getexpenseSummary(@RequestParam("month") String month,@RequestParam("year") String year){
        try{
        List<Object[]> expenditures = expenditureRepo.findExpenditureSummaryByCategoryAndMonth(month,year);
        if (expenditures.isEmpty()) {
            return new ResponseEntity<>("No data found in the month",HttpStatus.NOT_FOUND);
        }
        List<ExpenseInMonthDTO> expenseInMonths = new ArrayList<>();
        for (Object[] expenditure : expenditures) {
            ExpenseInMonthDTO exp = new ExpenseInMonthDTO();
            exp.setDeptCode((String) expenditure[0]);
            exp.setCatCode((String) expenditure[1]);
            exp.setTotalAmount((double) expenditure[2]);
            expenseInMonths.add(exp);
        }
        return new ResponseEntity<>(expenseInMonths,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 9 Get List expenses of a dept between given dates
    @GetMapping("/expByDepartmentAndDateBetween")
    public ResponseEntity<?> getAllByDepartmentAndExpDateBetween(@RequestParam("dept_code")String dept_code,@RequestParam("from_date")LocalDate date1,@RequestParam("to_date")LocalDate date2)throws ParseException{
       try{
        Department department = departmentRepo.findById(dept_code).get();
    //    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //     java.util.Date fromDate = sdf.parse(date1);
    //     java.util.Date toDate = sdf.parse(date2);
       List<Expenditure> expenditures = expenditureRepo.findAllByDepartmentAndExpDateBetween(department, date1, date2);
       if (expenditures.isEmpty()) {
        return new ResponseEntity<>("No data found in between the dates",HttpStatus.NOT_FOUND);
       }
       List<ExpenditureDTO> expenditureDTO = new ArrayList<>();
       for (Expenditure expenditure : expenditures) {
           ExpenditureDTO expDto=new ExpenditureDTO();
           expDto.setExpId(expenditure.getExpId());
           expDto.setAmount(expenditure.getAmount());
           expDto.setExpDate(expenditure.getExpDate());
           expDto.setDescription(expenditure.getDescription());
           expDto.setRemarks(expenditure.getRemarks());
           expDto.setCatCode(expenditure.getCategory().getCatCode());
           expDto.setDeptCode(expenditure.getDepartment().getDeptCode());
           expDto.setPaymentCode(expenditure.getPaymentMode().getPaymentCode());
           expDto.setAuthorizedBy(expenditure.getAuthorizedby());
           expenditureDTO.add(expDto);
        }
        return new ResponseEntity<>(expenditureDTO,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 


    // 10  GetAll By Authorised by names
    @GetMapping("/expenseByEmployeename")
    public ResponseEntity<?> getExpenseByAuthorisedBy(@RequestParam("name")String name){
        try{
        List<Department> departments = departmentRepo.findAllByHod(name);
        
        if (departments.isEmpty()) {
            return new ResponseEntity<>("No Employee found with this name",HttpStatus.NOT_FOUND);
        }
        List<Expenditure> expenditures = new ArrayList<>();
        for (Department department : departments) {
            List<Expenditure> expenditures1 = expenditureRepo.findAllByDepartment(department);
            expenditures.addAll(expenditures1);
        }
        if (expenditures.isEmpty()) {
            return new ResponseEntity<>("No expenditurs found with this name",HttpStatus.NOT_FOUND);
        }
        List<ExpenditureDTO> expenditureDTO = new ArrayList<>();
       for (Expenditure expenditure : expenditures) {
           ExpenditureDTO expDto=new ExpenditureDTO();
           expDto.setExpId(expenditure.getExpId());
           expDto.setAmount(expenditure.getAmount());
           expDto.setExpDate(expenditure.getExpDate());
           expDto.setDescription(expenditure.getDescription());
           expDto.setRemarks(expenditure.getRemarks());
           expDto.setCatCode(expenditure.getCategory().getCatCode());
           expDto.setDeptCode(expenditure.getDepartment().getDeptCode());
           expDto.setPaymentCode(expenditure.getPaymentMode().getPaymentCode());
           expDto.setAuthorizedBy(expenditure.getAuthorizedby());
           expenditureDTO.add(expDto);
        }
        return new ResponseEntity<>(expenditureDTO,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 11 List all By containing Description
    @GetMapping("/expensesByContainingDescription")
    public List<ExpenditureDTO> getExpensesByContainingDescription(@RequestParam("des")String des){
        List<Expenditure> expenditures = expenditureRepo.findAllByDescriptionContaining(des);
        List<ExpenditureDTO> expenditureDTO = new ArrayList<>();
        for (Expenditure expenditure : expenditures) {
           ExpenditureDTO expDto=new ExpenditureDTO();
           expDto.setExpId(expenditure.getExpId());
           expDto.setAmount(expenditure.getAmount());
           expDto.setExpDate(expenditure.getExpDate());
           expDto.setDescription(expenditure.getDescription());
           expDto.setRemarks(expenditure.getRemarks());
           expDto.setCatCode(expenditure.getCategory().getCatCode());
           expDto.setDeptCode(expenditure.getDepartment().getDeptCode());
           expDto.setPaymentCode(expenditure.getPaymentMode().getPaymentCode());
           expDto.setAuthorizedBy(expenditure.getAuthorizedby());
           expenditureDTO.add(expDto);
        }
        return expenditureDTO;
        
    }

    //12 . Get all Expenditures between given amount
    @GetMapping("/expendituresBetweenMinMax")
    public ResponseEntity<?> getAmountBetween(@RequestParam("min") Double min, @RequestParam("max") Double max) {
      try{
      List<Expenditure> expenditures = expenditureRepo.findAllByAmountBetween(min, max);
      if (expenditures.isEmpty()) {
        return new ResponseEntity<>("No data found between the provided range",HttpStatus.NOT_FOUND);
        // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("vnvxb");
      }
      List<ExpenditureDTO> expendituresDTO = new ArrayList<>();
        for (Expenditure expenditure : expenditures) {
            ExpenditureDTO expDTO = new ExpenditureDTO();
            expDTO.setExpId(expenditure.getExpId());
            expDTO.setAmount(expenditure.getAmount());
            expDTO.setExpDate(expenditure.getExpDate());
            expDTO.setDescription(expenditure.getDescription());
            expDTO.setRemarks(expenditure.getRemarks());
            expDTO.setCatCode(expenditure.getCategory().getCatCode());
            expDTO.setDeptCode(expenditure.getDepartment().getDeptCode());
            expDTO.setPaymentCode(expenditure.getPaymentMode().getPaymentCode());
            expDTO.setAuthorizedBy(expenditure.getAuthorizedby());
            expendituresDTO.add(expDTO);
        }
      return new ResponseEntity<>(expendituresDTO,HttpStatus.OK);
    }
    catch(Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    // 14 List all department and total amount spent on that department
    @GetMapping("/expenseByDepartmentAndTotal")
    public List<ExpenseByDeptTotal> getDepartmentAndTotal(){
            List<Object[]> expenditures = expenditureRepo.findAllByDepartmentAndTotal();
            List<ExpenseByDeptTotal> eByDeptTotals = new ArrayList<>();
            for (Object[] object : expenditures) {
                ExpenseByDeptTotal deptTotals =new ExpenseByDeptTotal();
                deptTotals.setDeptCode((String) object[0]);
                deptTotals.setTotalAmount((double) object[1]);
                eByDeptTotals.add(deptTotals);
            }
            
            return eByDeptTotals;
    }


    // 15 List all category and total amount spent on that category
    @GetMapping("/expenseByCategoryAndTotal")
    public List<ExpenseByCatTotal> getCaytegorytAndTotal(){
            List<Object[]> expenditures = expenditureRepo.findAllByCategoryAndTotal();
            List<ExpenseByCatTotal> eByCatTotals = new ArrayList<>();
            for (Object[] object : expenditures) {
                ExpenseByCatTotal deptTotals =new ExpenseByCatTotal();
                deptTotals.setCatCode((String)object[0]);
                deptTotals.setTotalAmount((double) object[1]);
                eByCatTotals.add(deptTotals);
            }
            
            return eByCatTotals;
    }

    
        
         
     // Get List of Expense By Category
         @GetMapping("/expByCategory")
         public ResponseEntity<?> getAllByCategory(@RequestParam("cat_code")String cat_code){
            try{
            Category category = categoryRepo.findById(cat_code).get();
            List<Expenditure> expenditures = expenditureRepo.findAllByCategory(category);
            if (expenditures.isEmpty()) {
                return new ResponseEntity<>("Data Not Found",HttpStatus.NOT_FOUND);
            }
            List<ExpenditureDTO> expenditureDTO = new ArrayList<>();
            for (Expenditure expenditure : expenditures) {
                ExpenditureDTO expDto=new ExpenditureDTO();
                expDto.setExpId(expenditure.getExpId());
                expDto.setAmount(expenditure.getAmount());
                expDto.setExpDate(expenditure.getExpDate());
                expDto.setDescription(expenditure.getDescription());
                expDto.setRemarks(expenditure.getRemarks());
                expDto.setCatCode(expenditure.getCategory().getCatCode());
                expDto.setDeptCode(expenditure.getDepartment().getDeptCode());
                expDto.setPaymentCode(expenditure.getPaymentMode().getPaymentCode());
                expDto.setAuthorizedBy(expenditure.getAuthorizedby());
                expenditureDTO.add(expDto);
             }
             return new ResponseEntity<>(expenditureDTO,HttpStatus.OK);
            }
            catch(Exception e){
                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
            }
         } 


    // Get List of Expenditures By DateBetween
    @GetMapping("/expenseByDateBetween")
    public ResponseEntity<?> getExpenditureByDateBetween(@RequestParam("from_date")LocalDate date1,@RequestParam("to_date")LocalDate date2) throws ParseException{        
        try{
        List<Expenditure> l= expenditureRepo.findAllByExpDateBetween(date1,date2);
        if (l.isEmpty()) {
            return new ResponseEntity<>("Data not found in between the given dates",HttpStatus.NOT_FOUND);  
        }
        List<ExpenditureDTO> expenditureDTO = new ArrayList<>();
        for (Expenditure expenditure : l) {
            ExpenditureDTO expDto=new ExpenditureDTO();
            expDto.setExpId(expenditure.getExpId());
            expDto.setAmount(expenditure.getAmount());
            expDto.setExpDate(expenditure.getExpDate());
            expDto.setDescription(expenditure.getDescription());
            expDto.setRemarks(expenditure.getRemarks());
            expDto.setCatCode(expenditure.getCategory().getCatCode());
            expDto.setDeptCode(expenditure.getDepartment().getDeptCode());
            expDto.setPaymentCode(expenditure.getPaymentMode().getPaymentCode());
            expDto.setAuthorizedBy(expenditure.getAuthorizedby());
            expenditureDTO.add(expDto);
         }
         return new ResponseEntity<>(expenditureDTO,HttpStatus.OK); 
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delAll")
    public ResponseEntity<?> deleteAllExpendditures(){
        List<Expenditure> expenditures = expenditureRepo.findAll();
        if (expenditures.isEmpty()) {
            return new ResponseEntity<>("There is no Expenditures to Delete",HttpStatus.NOT_FOUND);
        }
        expenditureRepo.deleteAll();
        return new ResponseEntity<>("All Expenditures Deleted Successfully",HttpStatus.OK);
    }


    
}

