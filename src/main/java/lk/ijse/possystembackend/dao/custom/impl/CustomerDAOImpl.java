package lk.ijse.possystembackend.dao.custom.impl;

import lk.ijse.possystembackend.dao.custom.CustomerDAO;
import lk.ijse.possystembackend.dto.CustomerDTO;
import lk.ijse.possystembackend.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);
    private static final String SAVE_CUSTOMER_DATA ="INSERT INTO customer (id,name,address,email,contact) VALUES(?,?,?,?,?)" ;

    private static final String UPDATE_CUSTOMER_DATA ="UPDATE customer SET name=? ,address=?,email=?,contact=? WHERE id=?;";

    private static final String GET_CUSTOMER_DATA = "SELECT * FROM Customer";

    private static final String DELETE_CUSTOMER_DATA = "DELETE  FROM customer WHERE id =?";
    @Override
    public void saveCustomer(Customer customer, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER_DATA);
            ps.setString(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getEmail());
            ps.setInt(5, customer.getContact());

            if (ps.executeUpdate() != 0) {
                logger.info("Customer information saved successfully!!!");
            } else {
                logger.error("Customer information saved unsuccessfully!!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCustomer(Customer customer, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMER_DATA);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getEmail());
            ps.setInt(4, customer.getContact());
            ps.setString(5, customer.getId());

            if (ps.executeUpdate() != 0) {
                logger.info("Customer information updated successfully!!!");
            } else {
                logger.error("Customer information updated unsuccessfully!!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCustomer(String id, Connection connection) {
        try {
            var ps = connection.prepareStatement(DELETE_CUSTOMER_DATA);
            ps.setString(1, id);

            if (ps.executeUpdate() != 0) {
                logger.info("Customer information deleted successfully!!!");
            } else {
                logger.error("Customer information deleted unsuccessfully!!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer(Connection connection) {
        try {
            var preparedStatement = connection.prepareStatement(GET_CUSTOMER_DATA);

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
            while(resultSet.next()){
                CustomerDTO customerDTO = new CustomerDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)
                );
                customerDTOS.add(customerDTO);

            }
            return customerDTOS;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
