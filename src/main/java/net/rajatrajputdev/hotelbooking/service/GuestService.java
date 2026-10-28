package net.rajatrajputdev.hotelbooking.service;

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

    public ResponseEntity<?> getAllGuests(){
        List<GuestModel> allGuests = guestRepository.findAll();
        if(allGuests.isEmpty()){
            return new ResponseEntity<>("There are no guests staying in the Hotel at the moment.", HttpStatus.OK);
        }
        return new ResponseEntity<>(allGuests, HttpStatus.OK);
    }

    public ResponseEntity<?> getGuest(ObjectId guest_id){
        Optional<GuestModel> guest = guestRepository.findById(guest_id);
        if(guest.isPresent()){
            return new ResponseEntity<>(guest, HttpStatus.OK);
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

    public ResponseEntity<?> createGuest(GuestModel guestModel){
        guestRepository.insert(guestModel);
        return new ResponseEntity<>("Guest has been created.", HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateGuest(GuestModel guestModel, ObjectId guest_id){
        GuestModel guest = guestRepository.findById(guest_id).orElse(null);
        if(guest != null){
            guest.setEmail(guestModel.getEmail());
            guest.setName(guestModel.getName());
            guest.setPhone(guestModel.getPhone());
            guestRepository.save(guest);
            return new ResponseEntity<>("Guest has been updated.", HttpStatus.OK);
        }
        return new ResponseEntity<>("No guest with that ID exists.", HttpStatus.NOT_FOUND);
    }
}
