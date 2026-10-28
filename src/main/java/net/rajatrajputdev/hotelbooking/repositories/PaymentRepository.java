package net.rajatrajputdev.hotelbooking.repositories;

import net.rajatrajputdev.hotelbooking.models.PaymentModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<PaymentModel, ObjectId> {
}