package net.rajatrajputdev.hotelbooking.repositories;

import net.rajatrajputdev.hotelbooking.models.GuestModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GuestRepository extends MongoRepository<GuestModel, ObjectId> {
}
