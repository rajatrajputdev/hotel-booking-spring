package net.rajatrajputdev.hotelbooking.controller;

import jakarta.validation.Valid;
import net.rajatrajputdev.hotelbooking.dto.request.PaymentRequest;
import net.rajatrajputdev.hotelbooking.service.PaymentService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentRequest request) {
        return paymentService.createPayment(request);
    }

    @GetMapping
    public ResponseEntity<?> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentById(@PathVariable String paymentId) {
        if (!ObjectId.isValid(paymentId)) return ResponseEntity.badRequest().body("Invalid payment ID.");
        return paymentService.getPaymentById(new ObjectId(paymentId));
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<?> updatePayment(@PathVariable String paymentId, @Valid @RequestBody PaymentRequest request) {
        if (!ObjectId.isValid(paymentId)) return ResponseEntity.badRequest().body("Invalid payment ID.");
        return paymentService.updatePayment(new ObjectId(paymentId), request);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable String paymentId) {
        if (!ObjectId.isValid(paymentId)) return ResponseEntity.badRequest().body("Invalid payment ID.");
        return paymentService.deletePayment(new ObjectId(paymentId));
    }
}
