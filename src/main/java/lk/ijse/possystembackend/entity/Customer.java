package lk.ijse.possystembackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
    private String id;
    private String name;
    private String address;
    private String email;
    private Integer contact;
}
