package net.rajatrajputdev.hotelbooking.controller;

import jakarta.validation.Valid;
import net.rajatrajputdev.hotelbooking.dto.request.RoomTypeRequest;
import net.rajatrajputdev.hotelbooking.service.RoomTypeService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room-type")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRoomTypes() {
        return roomTypeService.getAllRoomTypes();
    }

    @GetMapping("/{room_type_id}")
    public ResponseEntity<?> getRoomType(@PathVariable("room_type_id") String roomTypeId) {
        if (!ObjectId.isValid(roomTypeId)) {
            return new ResponseEntity<>("Please enter a valid room type ID.", HttpStatus.BAD_REQUEST);
        }
        return roomTypeService.getRoomType(new ObjectId(roomTypeId));
    }

    @PostMapping
    public ResponseEntity<?> createRoomType(@Valid @RequestBody RoomTypeRequest roomTypeRequest) {
        return roomTypeService.createRoomType(roomTypeRequest);
    }

    @PutMapping("/{room_type_id}")
    public ResponseEntity<?> updateRoomType(@Valid @RequestBody RoomTypeRequest roomTypeRequest, @PathVariable("room_type_id") String roomTypeId) {
        if (!ObjectId.isValid(roomTypeId)) {
            return new ResponseEntity<>("Please enter a valid room type ID.", HttpStatus.BAD_REQUEST);
        }
        return roomTypeService.updateRoomType(roomTypeRequest, new ObjectId(roomTypeId));
    }

    @DeleteMapping("/{room_type_id}")
    public ResponseEntity<?> deleteRoomType(@PathVariable("room_type_id") String roomTypeId) {
        if (!ObjectId.isValid(roomTypeId)) {
            return new ResponseEntity<>("Please enter a valid room type ID.", HttpStatus.BAD_REQUEST);
        }
        return roomTypeService.deleteRoomType(new ObjectId(roomTypeId));
    }
}