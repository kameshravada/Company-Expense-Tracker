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

import com.expense.companyExpense.Entity.PaymentMode;
import com.expense.companyExpense.Repository.PaymentModeRepo;

@RestController
@RequestMapping("/payment")
public class PaymentModeController {

    @Autowired
    PaymentModeRepo paymentModeRepo;

    //All

    @GetMapping("/all")
    public ResponseEntity<?> getPaymentModes(){
        List<PaymentMode>paymentModes = paymentModeRepo.findAll();
        if (paymentModes.isEmpty()) {
            return new ResponseEntity<>("data not found",HttpStatus.NOT_FOUND);  
        }
        return new ResponseEntity<>(paymentModes,HttpStatus.OK);
    }
    //find by deptcpde
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") String payment_code) {
    try {
        PaymentMode paymentMode = paymentModeRepo.findById(payment_code).orElse(null);
        if (paymentMode != null) {
            return ResponseEntity.ok(paymentMode);
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
    public ResponseEntity<?> addPaymentModes(@RequestBody PaymentMode paymentMode){
        if (paymentMode.getPaymentCode()==null) {
            return new ResponseEntity<>("enter payment_code",HttpStatus.BAD_REQUEST);
        }
        if (paymentMode.getPaymentName()==null) {
            return new ResponseEntity<>("enter payment_name",HttpStatus.BAD_REQUEST);
        }
        if (paymentMode.getPaymentRemarks()==null) {
            return new ResponseEntity<>("enter payment_remarks",HttpStatus.BAD_REQUEST);
        }
        if(paymentMode.getPaymentCode()!=null&&paymentMode.getPaymentName()!=null&&paymentMode.getPaymentRemarks()!=null){
            paymentModeRepo.save(paymentMode);
        }
        else{
            return new ResponseEntity<>("Enter all information [paymentCode,paymentName,payment_remarks]",HttpStatus.BAD_REQUEST);
        }
        return  ResponseEntity.status(HttpStatus.OK).body("PaymentMode added successfully");
    }

    //Put
    @PutMapping("/update/{payment_code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePaymentModes(@PathVariable("payment_code") String payment_code,@RequestBody PaymentMode paymentMode){
        try{
            if (!paymentModeRepo.existsById(payment_code)) {
                return new ResponseEntity<>("No Paymentmode found ",HttpStatus.NOT_FOUND);
            }
        if (payment_code!=null && paymentModeRepo.existsById(payment_code)) {
            PaymentMode payment = paymentModeRepo.findById(payment_code).get();
        
            if (paymentMode.getPaymentName()!=null) {
                payment.setPaymentName(paymentMode.getPaymentName());
            }
            if (paymentMode.getPaymentRemarks()!=null) {
                payment.setPaymentRemarks(paymentMode.getPaymentRemarks());
            }
            paymentModeRepo.save(payment);
        }
        return ResponseEntity.ok().body("Payment with deptcode "+payment_code+" Updated Successfully");
    }
    catch(Exception e){
        return ResponseEntity.internalServerError().build();
    }
    }


    //delete
    @DeleteMapping("/del/{payment_code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delPaymentModes(@PathVariable("payment_code") String payment_code){
        try{
            if (!paymentModeRepo.existsById(payment_code)) {
                return new ResponseEntity<>("No PaymentMode found ",HttpStatus.NOT_FOUND);
            }
        if(payment_code != null){
            paymentModeRepo.deleteById(payment_code);
        }
        return ResponseEntity.ok().body("PaymentMode with pmcode "+payment_code+" is deleted successfully");

    }
    catch(Exception e ){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
    }
    
    @SuppressWarnings("unused")
    @DeleteMapping("/delAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delAllPaymentModes(){
        try{
            if (paymentModeRepo.findAll()==null) {
                return new ResponseEntity<>("No PaymentModes to delete ",HttpStatus.NOT_FOUND);
            }
            else{
                paymentModeRepo.deleteAll();
                return ResponseEntity.ok().body("PaymentModes are deleted successfully");
            }
    }
    catch(Exception e ){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
    }

    // addAll
    @PostMapping("/addAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAllpaymentModes(@RequestBody List<PaymentMode> paymentModes){
        paymentModeRepo.saveAll(paymentModes);
        return  ResponseEntity.status(HttpStatus.OK).body("PaymentModes added successfully");
    }
}
