package net.rajatrajputdev.hotelbooking.service;

import net.rajatrajputdev.hotelbooking.dto.request.GuestRequest;
import net.rajatrajputdev.hotelbooking.dto.response.GuestResponse;
import net.rajatrajputdev.hotelbooking.models.GuestModel;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import net.rajatrajputdev.hotelbooking.repositories.GuestRepository;

import java.util.List;
import java.util.Optional;

@Component
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    private GuestResponse toResponse(GuestModel guest) {
        return new GuestResponse(
                guest.getGuestId().toHexString(),
                guest.getName(),
                guest.getEmail(),
                guest.getPhone()
        );
    }

    public ResponseEntity<?> getAllGuests(){
        List<GuestResponse> allGuests = guestRepository.findAll()
                .stream()
                .map(guest -> new GuestResponse(
                        guest.getGuestId().toHexString(),
                        guest.getName(),
                        guest.getEmail(),
                        guest.getPhone()
                ))
                .toList();

        return ResponseEntity.ok(allGuests);
    }

    public ResponseEntity<?> getGuest(ObjectId guest_id){
        Optional<GuestModel> guest = guestRepository.findById(guest_id);
        if(guest.isPresent()){
            GuestModel guestModel = guest.get();
            GuestResponse guestResponse = new GuestResponse(
                    guestModel.getGuestId().toHexString(),
                    guestModel.getName(),
                    guestModel.getEmail(),
                    guestModel.getPhone()
            );
            return new ResponseEntity<>(guestResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("No guest with that ID exists.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> deleteGuest(ObjectId guest_id){
        Optional<GuestModel> guest = guestRepository.findById(guest_id);
        if(guest.isPresent()){
            guestRepository.deleteById(guest_id);
            return new ResponseEntity<>("Guest has been deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<>("No guest with that ID exists.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<GuestResponse> createGuest(GuestRequest request) {

        GuestModel guestModel = new GuestModel();

        guestModel.setName(request.name());
        guestModel.setEmail(request.email());
        guestModel.setPhone(request.phone());

        GuestModel savedGuest = guestRepository.insert(guestModel);

        GuestResponse response = new GuestResponse(
                savedGuest.getGuestId().toHexString(),
                savedGuest.getName(),
                savedGuest.getEmail(),
                savedGuest.getPhone()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<?> updateGuest(GuestRequest request, ObjectId guest_id) {

        GuestModel guest = guestRepository.findById(guest_id).orElse(null);

        if (guest == null) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No guest with that ID exists.");}

        guest.setName(request.name());
        guest.setEmail(request.email());
        guest.setPhone(request.phone());

        GuestModel updatedGuest = guestRepository.save(guest);

        GuestResponse response = new GuestResponse(
                updatedGuest.getGuestId().toHexString(),
                updatedGuest.getName(),
                updatedGuest.getEmail(),
                updatedGuest.getPhone()
        );

        return ResponseEntity.ok(response);
    }
}
