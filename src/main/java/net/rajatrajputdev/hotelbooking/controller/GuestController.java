package net.rajatrajputdev.hotelbooking.controller;

import jakarta.validation.Valid;
import net.rajatrajputdev.hotelbooking.dto.request.GuestRequest;
import net.rajatrajputdev.hotelbooking.service.GuestService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guest")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public ResponseEntity<?> fetchAllGuests() {
        return guestService.getAllGuests();
    }

    @GetMapping("/{guest_id}")
    public ResponseEntity<?> fetchGuest(@PathVariable("guest_id") String guestId) {

        if (!ObjectId.isValid(guestId)) {
            return new ResponseEntity<>("Please enter a valid guest ID.", HttpStatus.BAD_REQUEST);
        }

        return guestService.getGuest(new ObjectId(guestId));
    }

    @DeleteMapping("/{guest_id}")
    public ResponseEntity<?> removeGuest(@PathVariable("guest_id") String guestId) {

        if (!ObjectId.isValid(guestId)) {
            return new ResponseEntity<>("Please enter a valid guest ID.", HttpStatus.BAD_REQUEST);
        }

        return guestService.deleteGuest(new ObjectId(guestId));
    }

    @PostMapping
    public ResponseEntity<?> addGuest(@Valid @RequestBody GuestRequest request) {

        return guestService.createGuest(request);
    }

    @PutMapping("/{guest_id}")
    public ResponseEntity<?> updateGuest(@PathVariable("guest_id") String guestId, @Valid @RequestBody GuestRequest request) {

        if (!ObjectId.isValid(guestId)) {
            return new ResponseEntity<>("Please enter a valid guest ID.", HttpStatus.BAD_REQUEST);
        }

        return guestService.updateGuest(
                request,
                new ObjectId(guestId)
        );
    }

}