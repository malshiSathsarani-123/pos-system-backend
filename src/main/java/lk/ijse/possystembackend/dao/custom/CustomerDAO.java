package lk.ijse.possystembackend.dao.custom;

import lk.ijse.possystembackend.dao.SuperDAO;
import lk.ijse.possystembackend.dto.CustomerDTO;
import lk.ijse.possystembackend.entity.Customer;

import java.sql.Connection;
import java.util.ArrayList;

//public interface CustomerDAO extends CrudDAO <Customer,String>{
public interface CustomerDAO extends SuperDAO {

    void saveCustomer(Customer customer, Connection connection);

    void updateCustomer(Customer customer, Connection connection);

    void deleteCustomer(String id, Connection connection);

    ArrayList<CustomerDTO> getAllCustomer(Connection connection);
}
