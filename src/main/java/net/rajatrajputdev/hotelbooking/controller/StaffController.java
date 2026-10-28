package net.rajatrajputdev.hotelbooking.controller;

import jakarta.validation.Valid;
import net.rajatrajputdev.hotelbooking.dto.request.StaffRequest;
import net.rajatrajputdev.hotelbooking.models.StaffModel;
import net.rajatrajputdev.hotelbooking.service.StaffService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<?> getAllStaff(){
        return staffService.getAllStaff();
    }

    @GetMapping("/{staff_id}")
    public ResponseEntity<?> getStaff(@PathVariable("staff_id") String staffId){
        if (!ObjectId.isValid(staffId)) {
            return new ResponseEntity<>("Please enter a valid staff ID.", HttpStatus.BAD_REQUEST);
        }
        return staffService.getStaff(new ObjectId(staffId));
    }

    @PostMapping
    public ResponseEntity<?> createStaff(@Valid @RequestBody StaffRequest staffRequest){
        return staffService.createStaff(staffRequest);
    }

    @PutMapping("/{staff_id}")
    public ResponseEntity<?> updateStaff(@Valid @RequestBody StaffRequest staffRequest, @PathVariable("staff_id") String staffId){
        if (!ObjectId.isValid(staffId)) {
            return new ResponseEntity<>("Please enter a valid staff ID.", HttpStatus.BAD_REQUEST);
        }
        return staffService.updateStaff(staffRequest, new ObjectId(staffId));
    }

    @DeleteMapping("/{staff_id}")
    public ResponseEntity<?> deleteStaff(@PathVariable("staff_id") String staffId){
        if (!ObjectId.isValid(staffId)) {
            return new ResponseEntity<>("Please enter a valid staff ID.", HttpStatus.BAD_REQUEST);
        }
        return staffService.deleteStaff(new ObjectId(staffId));
    }

}
