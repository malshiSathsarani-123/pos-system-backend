package lk.ijse.possystembackend.dao.custom.impl;

import lk.ijse.possystembackend.dao.custom.ItemDAO;
import lk.ijse.possystembackend.dto.ItemDTO;
import lk.ijse.possystembackend.entity.Item;
import lk.ijse.possystembackend.entity.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    private static final Logger logger = LoggerFactory.getLogger(ItemDAOImpl.class);

    private static final String UPDATE_ORDER_ITEM_DATA = "UPDATE item SET qty = (qty - ?) WHERE code = ?";
    private static final String SAVE_ITEM_DATA ="INSERT INTO item (code,description,qty,unitPrice) VALUES(?,?,?,?)" ;
    private static final String UPDATE_ITEM_DATA ="UPDATE item SET description=? ,qty=?,unitPrice=? WHERE code=?";
    private static final String GET_ITEM_DATA = "SELECT * FROM item";

    private static final String DELETE_ITEM_DATA = "DELETE  FROM item WHERE code =?";

    @Override
    public void saveItem(Item item, Connection connection) {
        try {
            var ps = connection.prepareStatement(SAVE_ITEM_DATA);
            ps.setString(1, item.getCode());
            ps.setString(2, item.getDescription());
            ps.setInt(3, item.getQty());
            ps.setDouble(4, item.getUnitPrice());

            if (ps.executeUpdate() != 0) {
                logger.info("Item information Saved successfully!!!");
            } else {
                logger.error("Item information saved unsuccessfully!!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(Item item, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_ITEM_DATA);
            ps.setString(1, item.getDescription());
            ps.setInt(2, item.getQty());
            ps.setDouble(3, item.getUnitPrice());
            ps.setString(4, item.getCode());

            if (ps.executeUpdate() != 0) {
                logger.info("Item information updated successfully!!!");
            } else {
                logger.error("Item information updated unsuccessfully!!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteItem(String code, Connection connection) {
        try {
            var ps = connection.prepareStatement(DELETE_ITEM_DATA);
            ps.setString(1, code);

            if (ps.executeUpdate() != 0) {
                logger.info("Item information deleted successfully!!!");
            } else {
                logger.error("Item information deleted unsuccessfully!!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<ItemDTO> getAllItem(Connection connection) {
        try {
            var preparedStatement = connection.prepareStatement(GET_ITEM_DATA);

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
            while(resultSet.next()){
                ItemDTO itemDTO = new ItemDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getDouble(4)
                );
                itemDTOS.add(itemDTO);

            }
            return itemDTOS;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateQtyOrder(List<OrderDetail> orderDtoList, Connection connection) throws SQLException {
        for (OrderDetail dto : orderDtoList) {
            if (!updateQtyOrder(dto,connection)){
                return false;
            }
        }
        return true;
    }

    public boolean updateQtyOrder(OrderDetail dto,Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(UPDATE_ORDER_ITEM_DATA);
            ps.setInt(1, dto.getQty());
            ps.setString(2, dto.getItemCode());

            if (ps.executeUpdate() != 0) {
                logger.info("Item Qty updated successfully!!!");
                return true;
            } else {
                logger.error("Item Qty updated unsuccessfully!!!");
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
