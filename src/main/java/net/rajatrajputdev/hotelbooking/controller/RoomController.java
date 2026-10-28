package net.rajatrajputdev.hotelbooking.controller;

import jakarta.validation.Valid;
import net.rajatrajputdev.hotelbooking.dto.request.RoomRequest;
import net.rajatrajputdev.hotelbooking.service.RoomService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{room_id}")
    public ResponseEntity<?> getRoom(@PathVariable("room_id") String roomId) {
        if (!ObjectId.isValid(roomId)) {
            return new ResponseEntity<>("Please enter a valid room ID.", HttpStatus.BAD_REQUEST);
        }
        return roomService.getRoom(new ObjectId(roomId));
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@Valid @RequestBody RoomRequest roomRequest) {
        return roomService.createRoom(roomRequest);
    }

    @PutMapping("/{room_id}")
    public ResponseEntity<?> updateRoom(@Valid @RequestBody RoomRequest roomRequest, @PathVariable("room_id") String roomId) {
        if (!ObjectId.isValid(roomId)) {
            return new ResponseEntity<>("Please enter a valid room ID.", HttpStatus.BAD_REQUEST);
        }
        return roomService.updateRoom(roomRequest, new ObjectId(roomId));
    }

    @DeleteMapping("/{room_id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("room_id") String roomId) {
        if (!ObjectId.isValid(roomId)) {
            return new ResponseEntity<>("Please enter a valid room ID.", HttpStatus.BAD_REQUEST);
        }
        return roomService.deleteRoom(new ObjectId(roomId));
    }
}