package net.rajatrajputdev.hotelbooking.repositories;

import net.rajatrajputdev.hotelbooking.models.BookingModel;
import net.rajatrajputdev.hotelbooking.models.BookingRoomModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRoomRepository extends MongoRepository<BookingRoomModel, ObjectId> {
}
