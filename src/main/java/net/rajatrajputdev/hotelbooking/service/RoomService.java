package net.rajatrajputdev.hotelbooking.service;

import net.rajatrajputdev.hotelbooking.dto.request.RoomRequest;
import net.rajatrajputdev.hotelbooking.dto.response.RoomResponse;
import net.rajatrajputdev.hotelbooking.models.RoomModel;
import net.rajatrajputdev.hotelbooking.repositories.RoomRepository;
import net.rajatrajputdev.hotelbooking.repositories.RoomTypeRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    public RoomService(RoomRepository roomRepository, RoomTypeRepository roomTypeRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    private RoomResponse toResponse(RoomModel room) {
        return new RoomResponse(
                room.getRoomId().toHexString(),
                room.getRoomNumber(),
                room.getStatus(),
                room.getRoomTypeId().toHexString()
        );
    }

    public ResponseEntity<?> getAllRooms() {
        List<RoomResponse> allRooms = roomRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(allRooms);
    }

    public ResponseEntity<?> getRoom(ObjectId roomId) {
        Optional<RoomModel> room = roomRepository.findById(roomId);

        if (room.isPresent()) {
            return ResponseEntity.ok(toResponse(room.get()));
        }

        return new ResponseEntity<>("No room with that ID exists.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> createRoom(RoomRequest request) {
        if (!roomTypeRepository.existsById(request.roomTypeId())) {
            return new ResponseEntity<>("No room type with that ID exists.", HttpStatus.BAD_REQUEST);
        }

        RoomModel room = new RoomModel();

        room.setRoomNumber(request.roomNumber());
        room.setStatus(request.status());
        room.setRoomTypeId(request.roomTypeId());

        RoomModel savedRoom = roomRepository.insert(room);

        return new ResponseEntity<>(toResponse(savedRoom), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateRoom(RoomRequest request, ObjectId roomId) {
        RoomModel room = roomRepository.findById(roomId).orElse(null);

        if (room == null) {
            return new ResponseEntity<>("No room with that ID exists.", HttpStatus.NOT_FOUND);
        }

        if (!roomTypeRepository.existsById(request.roomTypeId())) {
            return new ResponseEntity<>("No room type with that ID exists.", HttpStatus.BAD_REQUEST);
        }

        room.setRoomNumber(request.roomNumber());
        room.setStatus(request.status());
        room.setRoomTypeId(request.roomTypeId());

        RoomModel savedRoom = roomRepository.save(room);

        return new ResponseEntity<>(toResponse(savedRoom), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteRoom(ObjectId roomId) {
        Optional<RoomModel> room = roomRepository.findById(roomId);

        if (room.isPresent()) {
            roomRepository.deleteById(roomId);
            return new ResponseEntity<>("Room has been deleted.", HttpStatus.OK);
        }

        return new ResponseEntity<>("No room with that ID exists.", HttpStatus.NOT_FOUND);
    }
}