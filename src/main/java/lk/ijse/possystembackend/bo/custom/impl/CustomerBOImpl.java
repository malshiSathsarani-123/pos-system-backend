package lk.ijse.possystembackend.bo.custom.impl;

import lk.ijse.possystembackend.bo.custom.CustomerBO;
import lk.ijse.possystembackend.dao.custom.CustomerDAO;
import lk.ijse.possystembackend.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.possystembackend.dto.CustomerDTO;
import lk.ijse.possystembackend.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    private static final String GET_CUSTOMER_DATA = "SELECT * FROM Customer";

    CustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    public void saveCustomer(CustomerDTO customerDTO, Connection connection) {
        customerDAO.saveCustomer(new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getEmail(),customerDTO.getContact()),connection);
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO, Connection connection) {
        customerDAO.updateCustomer(new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getEmail(),customerDTO.getContact()),connection);
    }

    @Override
    public void deleteCustomer(String id, Connection connection) {
        customerDAO.deleteCustomer(id,connection);
    }

   @Override
    public ArrayList<CustomerDTO> getAllCustomer(Connection connection) {
        ArrayList<CustomerDTO> allCustomer = customerDAO.getAllCustomer(connection);
        return allCustomer;
    }
}
