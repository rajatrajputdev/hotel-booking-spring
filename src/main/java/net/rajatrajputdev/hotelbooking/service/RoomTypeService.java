package net.rajatrajputdev.hotelbooking.service;

import net.rajatrajputdev.hotelbooking.dto.request.RoomTypeRequest;
import net.rajatrajputdev.hotelbooking.dto.response.RoomTypeResponse;
import net.rajatrajputdev.hotelbooking.models.RoomTypeModel;
import net.rajatrajputdev.hotelbooking.repositories.RoomTypeRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    private RoomTypeResponse toResponse(RoomTypeModel roomType) {
        return new RoomTypeResponse(
                roomType.getRoomTypeId().toHexString(),
                roomType.getName(),
                roomType.getDescription(),
                roomType.getPricePerNight(),
                roomType.getCapacity()
        );
    }

    public ResponseEntity<?> getAllRoomTypes() {
        List<RoomTypeResponse> allRoomTypes = roomTypeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(allRoomTypes);
    }

    public ResponseEntity<?> getRoomType(ObjectId roomTypeId) {
        Optional<RoomTypeModel> roomType = roomTypeRepository.findById(roomTypeId);

        if (roomType.isPresent()) {
            return ResponseEntity.ok(toResponse(roomType.get()));
        }

        return new ResponseEntity<>("No room type with that ID exists.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> createRoomType(RoomTypeRequest request) {
        RoomTypeModel roomType = new RoomTypeModel();

        roomType.setName(request.name());
        roomType.setDescription(request.description());
        roomType.setPricePerNight(request.pricePerNight());
        roomType.setCapacity(request.capacity());

        RoomTypeModel savedRoomType = roomTypeRepository.insert(roomType);

        return new ResponseEntity<>(toResponse(savedRoomType), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateRoomType(RoomTypeRequest request, ObjectId roomTypeId) {
        RoomTypeModel roomType = roomTypeRepository.findById(roomTypeId).orElse(null);

        if (roomType != null) {
            roomType.setName(request.name());
            roomType.setDescription(request.description());
            roomType.setPricePerNight(request.pricePerNight());
            roomType.setCapacity(request.capacity());

            RoomTypeModel savedRoomType = roomTypeRepository.save(roomType);

            return new ResponseEntity<>(toResponse(savedRoomType), HttpStatus.OK);
        }

        return new ResponseEntity<>("No room type with that ID exists.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> deleteRoomType(ObjectId roomTypeId) {
        Optional<RoomTypeModel> roomType = roomTypeRepository.findById(roomTypeId);

        if (roomType.isPresent()) {
            roomTypeRepository.deleteById(roomTypeId);
            return new ResponseEntity<>("Room type has been deleted.", HttpStatus.OK);
        }

        return new ResponseEntity<>("No room type with that ID exists.", HttpStatus.NOT_FOUND);
    }
}