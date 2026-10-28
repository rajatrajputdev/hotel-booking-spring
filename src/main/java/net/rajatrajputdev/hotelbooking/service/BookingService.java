package net.rajatrajputdev.hotelbooking.service;

import net.rajatrajputdev.hotelbooking.dto.request.BookingRequest;
import net.rajatrajputdev.hotelbooking.dto.response.BookingResponse;
import net.rajatrajputdev.hotelbooking.enums.BookingStatus;
import net.rajatrajputdev.hotelbooking.models.BookingModel;
import net.rajatrajputdev.hotelbooking.models.GuestModel;
import net.rajatrajputdev.hotelbooking.models.StaffModel;
import net.rajatrajputdev.hotelbooking.repositories.BookingRepository;
import net.rajatrajputdev.hotelbooking.repositories.GuestRepository;
import net.rajatrajputdev.hotelbooking.repositories.StaffRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookingService {

    private final GuestRepository guestRepository;

    private final StaffRepository staffRepository;

    private final BookingRepository bookingRepository;

    public BookingService(GuestRepository guestRepository, StaffRepository staffRepository, BookingRepository bookingRepository) {
        this.guestRepository = guestRepository;
        this.staffRepository = staffRepository;
        this.bookingRepository = bookingRepository;
    }

    private BookingResponse toResponse(BookingModel booking) {
        return new BookingResponse(
                booking.getBookingId().toHexString(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getStatus(),
                booking.getAmount(),
                booking.getGuests().stream().map(guest -> guest.getGuestId().toHexString()).toList(),
                booking.getStaff() != null ? booking.getStaff().getStaffId().toHexString() : null
        );
    }

    public ResponseEntity<?> createBooking(BookingRequest request) {

        if (!request.checkOut().isAfter(request.checkIn())) {
            return new ResponseEntity<>("Check-out date must be after check-in date.", HttpStatus.BAD_REQUEST);
        }

        List<GuestModel> guests = new ArrayList<>();

        for (String guestId : request.guestIds()) {
            if (!ObjectId.isValid(guestId)) {
                return new ResponseEntity<>("Invalid guest ID: " + guestId, HttpStatus.BAD_REQUEST);
            }

            Optional<GuestModel> guest = guestRepository.findById(new ObjectId(guestId));

            if (guest.isEmpty()) {
                return new ResponseEntity<>("Guest not found: " + guestId, HttpStatus.NOT_FOUND);
            }

            guests.add(guest.get());
        }

        StaffModel staff = null;

        if (request.staffId() != null && !request.staffId().isBlank()) {
            if (!ObjectId.isValid(request.staffId())) {
                return new ResponseEntity<>("Invalid staff ID.", HttpStatus.BAD_REQUEST);
            }

            Optional<StaffModel> existingStaff = staffRepository.findById(new ObjectId(request.staffId()));

            if (existingStaff.isEmpty()) {
                return new ResponseEntity<>("Staff member not found.", HttpStatus.NOT_FOUND);
            }

            staff = existingStaff.get();
        }

        BookingModel booking = new BookingModel();
        booking.setCheckIn(request.checkIn());
        booking.setCheckOut(request.checkOut());
        booking.setGuests(guests);
        booking.setStaff(staff);
        booking.setStatus(BookingStatus.PENDING);
        booking.setAmount(0);

        BookingModel savedBooking = bookingRepository.save(booking);

        return new ResponseEntity<>(toResponse(savedBooking), HttpStatus.CREATED);
    }
    public ResponseEntity<?> getAllBookings() {
        List<BookingResponse> bookings = bookingRepository.findAll().stream().map(this::toResponse).toList();
        if (bookings.isEmpty()) return new ResponseEntity<>("No bookings found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    public ResponseEntity<?> getBookingById(ObjectId bookingId) {
        Optional<BookingModel> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) return new ResponseEntity<>("Booking not found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(toResponse(booking.get()), HttpStatus.OK);
    }
    public ResponseEntity<?> updateBooking(ObjectId bookingId, BookingRequest request) {
        Optional<BookingModel> existingBooking = bookingRepository.findById(bookingId);
        if (existingBooking.isEmpty()) return new ResponseEntity<>("Booking not found.", HttpStatus.NOT_FOUND);

        if (!request.checkOut().isAfter(request.checkIn())) return new ResponseEntity<>("Check-out date must be after check-in date.", HttpStatus.BAD_REQUEST);

        List<GuestModel> guests = new ArrayList<>();
        for (String guestId : request.guestIds()) {
            if (!ObjectId.isValid(guestId)) return new ResponseEntity<>("Invalid guest ID: " + guestId, HttpStatus.BAD_REQUEST);
            Optional<GuestModel> guest = guestRepository.findById(new ObjectId(guestId));
            if (guest.isEmpty()) return new ResponseEntity<>("Guest not found: " + guestId, HttpStatus.NOT_FOUND);
            guests.add(guest.get());
        }

        StaffModel staff = null;
        if (request.staffId() != null && !request.staffId().isBlank()) {
            if (!ObjectId.isValid(request.staffId())) return new ResponseEntity<>("Invalid staff ID.", HttpStatus.BAD_REQUEST);
            Optional<StaffModel> existingStaff = staffRepository.findById(new ObjectId(request.staffId()));
            if (existingStaff.isEmpty()) return new ResponseEntity<>("Staff member not found.", HttpStatus.NOT_FOUND);
            staff = existingStaff.get();
        }

        BookingModel booking = existingBooking.get();
        booking.setCheckIn(request.checkIn());
        booking.setCheckOut(request.checkOut());
        booking.setGuests(guests);
        booking.setStaff(staff);

        BookingModel updatedBooking = bookingRepository.save(booking);
        return new ResponseEntity<>(toResponse(updatedBooking), HttpStatus.OK);
    }
    public ResponseEntity<?> deleteBooking(ObjectId bookingId) {
        Optional<BookingModel> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) return new ResponseEntity<>("Booking not found.", HttpStatus.NOT_FOUND);
        bookingRepository.deleteById(bookingId);
        return new ResponseEntity<>("Booking deleted successfully.", HttpStatus.OK);
    }
}
