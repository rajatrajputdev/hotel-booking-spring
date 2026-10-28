package net.rajatrajputdev.hotelbooking.service;

import net.rajatrajputdev.hotelbooking.dto.request.BookingRoomRequest;
import net.rajatrajputdev.hotelbooking.dto.response.BookingRoomResponse;
import net.rajatrajputdev.hotelbooking.models.BookingRoomModel;
import net.rajatrajputdev.hotelbooking.repositories.BookingRepository;
import net.rajatrajputdev.hotelbooking.repositories.BookingRoomRepository;
import net.rajatrajputdev.hotelbooking.repositories.RoomRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingRoomService {

    private final BookingRoomRepository bookingRoomRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingRoomService(BookingRoomRepository bookingRoomRepository, BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRoomRepository = bookingRoomRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    private BookingRoomResponse toResponse(BookingRoomModel bookingRoom) {
        return new BookingRoomResponse(bookingRoom.getBookingRoomId().toHexString(), bookingRoom.getBookingId().toHexString(), bookingRoom.getRoomId().toHexString());
    }

    public ResponseEntity<?> createBookingRoom(BookingRoomRequest request) {
        if (!ObjectId.isValid(request.bookingId())) return new ResponseEntity<>("Invalid booking ID.", HttpStatus.BAD_REQUEST);
        if (!ObjectId.isValid(request.roomId())) return new ResponseEntity<>("Invalid room ID.", HttpStatus.BAD_REQUEST);

        ObjectId bookingId = new ObjectId(request.bookingId());
        ObjectId roomId = new ObjectId(request.roomId());

        if (!bookingRepository.existsById(bookingId)) return new ResponseEntity<>("Booking not found.", HttpStatus.NOT_FOUND);
        if (!roomRepository.existsById(roomId)) return new ResponseEntity<>("Room not found.", HttpStatus.NOT_FOUND);

        BookingRoomModel bookingRoom = new BookingRoomModel();
        bookingRoom.setBookingId(bookingId);
        bookingRoom.setRoomId(roomId);

        BookingRoomModel savedBookingRoom = bookingRoomRepository.save(bookingRoom);
        return new ResponseEntity<>(toResponse(savedBookingRoom), HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllBookingRooms() {
        List<BookingRoomResponse> bookingRooms = bookingRoomRepository.findAll().stream().map(this::toResponse).toList();
        if (bookingRooms.isEmpty()) return new ResponseEntity<>("No booking-room records found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bookingRooms, HttpStatus.OK);
    }

    public ResponseEntity<?> getBookingRoomById(ObjectId bookingRoomId) {
        Optional<BookingRoomModel> bookingRoom = bookingRoomRepository.findById(bookingRoomId);
        if (bookingRoom.isEmpty()) return new ResponseEntity<>("Booking-room record not found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(toResponse(bookingRoom.get()), HttpStatus.OK);
    }

    public ResponseEntity<?> updateBookingRoom(ObjectId bookingRoomId, BookingRoomRequest request) {
        Optional<BookingRoomModel> existingBookingRoom = bookingRoomRepository.findById(bookingRoomId);
        if (existingBookingRoom.isEmpty()) return new ResponseEntity<>("Booking-room record not found.", HttpStatus.NOT_FOUND);
        if (!ObjectId.isValid(request.bookingId())) return new ResponseEntity<>("Invalid booking ID.", HttpStatus.BAD_REQUEST);
        if (!ObjectId.isValid(request.roomId())) return new ResponseEntity<>("Invalid room ID.", HttpStatus.BAD_REQUEST);

        ObjectId bookingId = new ObjectId(request.bookingId());
        ObjectId roomId = new ObjectId(request.roomId());

        if (!bookingRepository.existsById(bookingId)) return new ResponseEntity<>("Booking not found.", HttpStatus.NOT_FOUND);
        if (!roomRepository.existsById(roomId)) return new ResponseEntity<>("Room not found.", HttpStatus.NOT_FOUND);

        BookingRoomModel bookingRoom = existingBookingRoom.get();
        bookingRoom.setBookingId(bookingId);
        bookingRoom.setRoomId(roomId);

        BookingRoomModel updatedBookingRoom = bookingRoomRepository.save(bookingRoom);
        return new ResponseEntity<>(toResponse(updatedBookingRoom), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteBookingRoom(ObjectId bookingRoomId) {
        Optional<BookingRoomModel> bookingRoom = bookingRoomRepository.findById(bookingRoomId);
        if (bookingRoom.isEmpty()) return new ResponseEntity<>("Booking-room record not found.", HttpStatus.NOT_FOUND);
        bookingRoomRepository.deleteById(bookingRoomId);
        return new ResponseEntity<>("Booking-room record deleted successfully.", HttpStatus.OK);
    }
}