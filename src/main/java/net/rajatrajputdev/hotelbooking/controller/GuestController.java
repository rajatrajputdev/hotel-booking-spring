package net.rajatrajputdev.hotelbooking.controller;

import net.rajatrajputdev.hotelbooking.models.GuestModel;
import org.bson.types.ObjectId;
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
    public ResponseEntity<?> addGuest(@RequestBody GuestModel guestModel){
        return guestService.createGuest(guestModel);
    }

    @PutMapping("/{guest_id}")
    public ResponseEntity<?> updateGuest(@RequestBody GuestModel guestModel, @PathVariable ObjectId guest_id){
        return guestService.updateGuest(guestModel, guest_id);
    }
}
