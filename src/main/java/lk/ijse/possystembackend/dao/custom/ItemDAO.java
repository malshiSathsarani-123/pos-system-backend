package lk.ijse.possystembackend.dao.custom;

import lk.ijse.possystembackend.dao.SuperDAO;
import lk.ijse.possystembackend.dto.ItemDTO;
import lk.ijse.possystembackend.entity.Item;
import lk.ijse.possystembackend.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemDAO extends SuperDAO {

    void saveItem(Item item, Connection connection);

    void updateItem(Item item, Connection connection);

    void deleteItem(String code, Connection connection);

    ArrayList<ItemDTO> getAllItem(Connection connection);

    boolean updateQtyOrder(List<OrderDetail> orderDetailsList, Connection connection) throws SQLException;

}
