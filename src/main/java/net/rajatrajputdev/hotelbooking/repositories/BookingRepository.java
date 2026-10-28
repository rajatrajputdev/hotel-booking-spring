package net.rajatrajputdev.hotelbooking.repositories;

import net.rajatrajputdev.hotelbooking.models.BookingModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<BookingModel, ObjectId> {
}
