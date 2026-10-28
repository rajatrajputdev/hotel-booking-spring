package net.rajatrajputdev.hotelbooking.controller;

import net.rajatrajputdev.hotelbooking.dto.request.BookingRoomRequest;
import net.rajatrajputdev.hotelbooking.service.BookingRoomService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking-room")
public class BookingRoomController {

    private final BookingRoomService bookingRoomService;

    public BookingRoomController(BookingRoomService bookingRoomService) {
        this.bookingRoomService = bookingRoomService;
    }

    @PostMapping
    public ResponseEntity<?> createBookingRoom(@Valid @RequestBody BookingRoomRequest request) {
        return bookingRoomService.createBookingRoom(request);
    }

    @GetMapping
    public ResponseEntity<?> getAllBookingRooms() {
        return bookingRoomService.getAllBookingRooms();
    }

    @GetMapping("/{bookingRoomId}")
    public ResponseEntity<?> getBookingRoomById(@PathVariable String bookingRoomId) {
        if (!ObjectId.isValid(bookingRoomId)) return ResponseEntity.badRequest().body("Invalid booking-room ID.");
        return bookingRoomService.getBookingRoomById(new ObjectId(bookingRoomId));
    }

    @PutMapping("/{bookingRoomId}")
    public ResponseEntity<?> updateBookingRoom(@PathVariable String bookingRoomId, @Valid @RequestBody BookingRoomRequest request) {
        if (!ObjectId.isValid(bookingRoomId)) return ResponseEntity.badRequest().body("Invalid booking-room ID.");
        return bookingRoomService.updateBookingRoom(new ObjectId(bookingRoomId), request);
    }

    @DeleteMapping("/{bookingRoomId}")
    public ResponseEntity<?> deleteBookingRoom(@PathVariable String bookingRoomId) {
        if (!ObjectId.isValid(bookingRoomId)) return ResponseEntity.badRequest().body("Invalid booking-room ID.");
        return bookingRoomService.deleteBookingRoom(new ObjectId(bookingRoomId));
    }
}