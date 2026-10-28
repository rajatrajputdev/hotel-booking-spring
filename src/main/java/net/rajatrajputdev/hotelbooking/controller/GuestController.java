package net.rajatrajputdev.hotelbooking.controller;

import jakarta.validation.Valid;
import net.rajatrajputdev.hotelbooking.dto.request.CreateGuestRequest;
import net.rajatrajputdev.hotelbooking.dto.request.UpdateGuestRequest;
import net.rajatrajputdev.hotelbooking.models.GuestModel;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.rajatrajputdev.hotelbooking.service.GuestService;

@RestController
@RequestMapping("/guest")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public ResponseEntity<?> fetchAllGuests(){
        return guestService.getAllGuests();
    }

    @GetMapping("/{guest_id}")
    public ResponseEntity<?> fetchGuest(@PathVariable ObjectId guest_id){
        return guestService.getGuest(guest_id);
    }

    @DeleteMapping("/{guest_id}")
    public ResponseEntity<?> removeGuest(@PathVariable ObjectId guest_id){
        return guestService.deleteGuest(guest_id);
    }

    @PostMapping
    public ResponseEntity<?> addGuest(@Valid @RequestBody CreateGuestRequest request){
        return guestService.createGuest(request);
    }

    @PutMapping("/{guest_id}")
    public ResponseEntity<?> updateGuest(@PathVariable("guest_id") String guestId, @Valid @RequestBody UpdateGuestRequest request) {

        if (!ObjectId.isValid(guestId)) {
            return new ResponseEntity<>("Please enter a valid guest ID.",HttpStatus.BAD_REQUEST);
        }

        return guestService.updateGuest(request, new ObjectId(guestId));
    }
}
