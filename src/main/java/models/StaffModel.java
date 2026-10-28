package models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "staff")
public class StaffModel {

    @Id
    private ObjectId staffId;

    @NotBlank(message = "Staff name is required")
    private String name;

    @NotBlank(message = "Staff role is required")
    private String role;
}