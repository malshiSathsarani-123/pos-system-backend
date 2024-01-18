package lk.ijse.possystembackend.bo.custom.impl;

import lk.ijse.possystembackend.bo.custom.OrderBO;
import lk.ijse.possystembackend.dao.custom.ItemDAO;
import lk.ijse.possystembackend.dao.custom.OrderDAO;
import lk.ijse.possystembackend.dao.custom.OrderDetailDAO;
import lk.ijse.possystembackend.dao.custom.impl.ItemDAOImpl;
import lk.ijse.possystembackend.dao.custom.impl.OrderDAOImpl;
import lk.ijse.possystembackend.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.possystembackend.db.DBConnection;
import lk.ijse.possystembackend.dto.OrderDTO;
import lk.ijse.possystembackend.dto.OrderDetailDTO;
import lk.ijse.possystembackend.entity.Order;
import lk.ijse.possystembackend.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = new OrderDAOImpl();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    ItemDAO itemDAO = new ItemDAOImpl();
    @Override
    public boolean perchesOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetailDTOList, String customerId, Connection connection) throws SQLException {
        List<OrderDetail> orderDetailsList= new ArrayList<>();
        for (OrderDetailDTO o : orderDetailDTOList) {
            orderDetailsList.add(new OrderDetail(o.getOrderId(),o.getItemCode(),o.getDescription(),o.getUnitPrice(),o.getQty(),o.getTotal()));
        }
        try {
            connection.setAutoCommit(false);

            boolean isSave = orderDAO.save(new Order(orderDTO.getOrderId(),orderDTO.getCustomerId(), orderDTO.getPrice(), LocalDate.now()),connection,customerId);
            if (isSave){
                boolean isUpdate = itemDAO.updateQtyOrder(orderDetailsList,connection);
                if (isUpdate){
                    boolean isSaveOrderDetail = orderDetailDAO.save(orderDTO.getOrderId(),orderDetailsList,connection);
                    if (isSaveOrderDetail){
                        connection.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public String getOrderId(Connection connection) {
        return orderDAO.getOrderId(connection);
    }
}
