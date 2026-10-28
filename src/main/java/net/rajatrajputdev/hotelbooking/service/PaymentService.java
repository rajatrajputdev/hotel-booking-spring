package net.rajatrajputdev.hotelbooking.service;

import net.rajatrajputdev.hotelbooking.dto.request.PaymentRequest;
import net.rajatrajputdev.hotelbooking.dto.response.PaymentResponse;
import net.rajatrajputdev.hotelbooking.models.PaymentModel;
import net.rajatrajputdev.hotelbooking.repositories.BookingRepository;
import net.rajatrajputdev.hotelbooking.repositories.PaymentRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    private PaymentResponse toResponse(PaymentModel payment) {
        return new PaymentResponse(payment.getPaymentId().toHexString(), payment.getAmount(), payment.getPaymentDate(), payment.getPaymentMethod(), payment.getStatus(), payment.getBookingId().toHexString());
    }

    public ResponseEntity<?> createPayment(PaymentRequest request) {
        if (!ObjectId.isValid(request.bookingId())) return new ResponseEntity<>("Invalid booking ID.", HttpStatus.BAD_REQUEST);

        ObjectId bookingId = new ObjectId(request.bookingId());

        if (!bookingRepository.existsById(bookingId)) return new ResponseEntity<>("Booking not found.", HttpStatus.NOT_FOUND);

        PaymentModel payment = new PaymentModel();
        payment.setAmount(request.amount());
        payment.setPaymentDate(request.paymentDate());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setStatus(request.status());
        payment.setBookingId(bookingId);

        PaymentModel savedPayment = paymentRepository.save(payment);
        return new ResponseEntity<>(toResponse(savedPayment), HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllPayments() {
        List<PaymentResponse> payments = paymentRepository.findAll().stream().map(this::toResponse).toList();
        if (payments.isEmpty()) return new ResponseEntity<>("No payments found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    public ResponseEntity<?> getPaymentById(ObjectId paymentId) {
        Optional<PaymentModel> payment = paymentRepository.findById(paymentId);
        if (payment.isEmpty()) return new ResponseEntity<>("Payment not found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(toResponse(payment.get()), HttpStatus.OK);
    }

    public ResponseEntity<?> updatePayment(ObjectId paymentId, PaymentRequest request) {
        Optional<PaymentModel> existingPayment = paymentRepository.findById(paymentId);
        if (existingPayment.isEmpty()) return new ResponseEntity<>("Payment not found.", HttpStatus.NOT_FOUND);
        if (!ObjectId.isValid(request.bookingId())) return new ResponseEntity<>("Invalid booking ID.", HttpStatus.BAD_REQUEST);

        ObjectId bookingId = new ObjectId(request.bookingId());

        if (!bookingRepository.existsById(bookingId)) return new ResponseEntity<>("Booking not found.", HttpStatus.NOT_FOUND);

        PaymentModel payment = existingPayment.get();
        payment.setAmount(request.amount());
        payment.setPaymentDate(request.paymentDate());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setStatus(request.status());
        payment.setBookingId(bookingId);

        PaymentModel updatedPayment = paymentRepository.save(payment);
        return new ResponseEntity<>(toResponse(updatedPayment), HttpStatus.OK);
    }

    public ResponseEntity<?> deletePayment(ObjectId paymentId) {
        Optional<PaymentModel> payment = paymentRepository.findById(paymentId);
        if (payment.isEmpty()) return new ResponseEntity<>("Payment not found.", HttpStatus.NOT_FOUND);
        paymentRepository.deleteById(paymentId);
        return new ResponseEntity<>("Payment deleted successfully.", HttpStatus.OK);
    }
}
