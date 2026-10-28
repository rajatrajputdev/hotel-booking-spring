package net.rajatrajputdev.hotelbooking.controller;

import jakarta.validation.Valid;
import net.rajatrajputdev.hotelbooking.dto.request.BookingRequest;
import net.rajatrajputdev.hotelbooking.service.BookingService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable String bookingId) {
        if (!ObjectId.isValid(bookingId)) return ResponseEntity.badRequest().body("Invalid booking ID.");
        return bookingService.getBookingById(new ObjectId(bookingId));
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBooking(@PathVariable String bookingId, @Valid @RequestBody BookingRequest request) {
        if (!ObjectId.isValid(bookingId)) return ResponseEntity.badRequest().body("Invalid booking ID.");
        return bookingService.updateBooking(new ObjectId(bookingId), request);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable String bookingId) {
        if (!ObjectId.isValid(bookingId)) return ResponseEntity.badRequest().body("Invalid booking ID.");
        return bookingService.deleteBooking(new ObjectId(bookingId));
    }
}
