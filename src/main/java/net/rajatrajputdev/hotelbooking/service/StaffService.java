package net.rajatrajputdev.hotelbooking.service;

import net.rajatrajputdev.hotelbooking.dto.request.StaffRequest;
import net.rajatrajputdev.hotelbooking.dto.response.StaffResponse;
import net.rajatrajputdev.hotelbooking.models.StaffModel;
import net.rajatrajputdev.hotelbooking.repositories.StaffRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    private StaffResponse toResponse(StaffModel staff) {
        return new StaffResponse(
                staff.getStaffId().toHexString(),
                staff.getName(),
                staff.getRole()
        );
    }

    public ResponseEntity<?> getAllStaff(){
        List<StaffResponse> allStaff = staffRepository.findAll()
                .stream()
                .map(staff -> new StaffResponse(
                        staff.getStaffId().toHexString(),
                        staff.getName(),
                        staff.getRole()
                ))
                .toList();

        return ResponseEntity.ok(allStaff);
    }

    public ResponseEntity<?> getStaff(ObjectId staffId){
        StaffModel staffModel = staffRepository.findById(staffId).orElse(null);
        if(staffModel != null){
            StaffResponse staffResponse = new StaffResponse(
                    staffModel.getStaffId().toHexString(),
                    staffModel.getName(),
                    staffModel.getRole()
            );
            return new ResponseEntity<>(staffResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("No staff with that ID exists.",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> deleteStaff(ObjectId staffId){
        Optional<StaffModel> staff = staffRepository.findById(staffId);
        if(staff.isPresent()){
            staffRepository.deleteById(staffId);
            return new ResponseEntity<>("Staff has been deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<>("No staff with that ID exists.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> createStaff(StaffRequest request){
        StaffModel staffModel = new StaffModel();

        staffModel.setName(request.name());
        staffModel.setRole(request.role());

        StaffModel savedStaff = staffRepository.insert(staffModel);
        StaffResponse staffResponse = new StaffResponse(
                savedStaff.getStaffId().toHexString(),
                savedStaff.getName(),
                savedStaff.getRole()
        );
        return new ResponseEntity<>(staffResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateStaff(StaffRequest request, ObjectId staffId){
        StaffModel staffModel = staffRepository.findById(staffId).orElse(null);
        if(staffModel != null){
            staffModel.setName(request.name());
            staffModel.setRole(request.role());
            StaffModel savedStaff = staffRepository.save(staffModel);
            StaffResponse staffResponse = new StaffResponse(
                    savedStaff.getStaffId().toHexString(),
                    savedStaff.getName(),
                    savedStaff.getRole()
            );
            return new ResponseEntity<>(staffResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("No staff with that ID exists.", HttpStatus.NOT_FOUND);
    }

}
