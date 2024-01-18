package lk.ijse.possystembackend.dao.custom;

import lk.ijse.possystembackend.entity.Order;

import java.sql.Connection;

public interface OrderDAO {
    boolean save(Order order, Connection connection, String customerId);

    String getOrderId(Connection connection);
}
