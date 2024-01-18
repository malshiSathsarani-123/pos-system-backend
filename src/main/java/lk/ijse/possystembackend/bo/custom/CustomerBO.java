package lk.ijse.possystembackend.bo.custom;

import lk.ijse.possystembackend.bo.SuperBO;
import lk.ijse.possystembackend.dto.CustomerDTO;

import java.sql.Connection;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    void saveCustomer(CustomerDTO customerDTO, Connection connection);

    void updateCustomer(CustomerDTO customerDTO, Connection connection);

    void deleteCustomer(String id, Connection connection);

    ArrayList<CustomerDTO> getAllCustomer(Connection connection);
}
