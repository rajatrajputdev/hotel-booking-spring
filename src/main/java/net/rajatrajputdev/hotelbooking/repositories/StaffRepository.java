package net.rajatrajputdev.hotelbooking.repositories;

import net.rajatrajputdev.hotelbooking.models.StaffModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StaffRepository extends MongoRepository<StaffModel, ObjectId> {
}
