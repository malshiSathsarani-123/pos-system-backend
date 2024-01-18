package lk.ijse.possystembackend.bo.custom.impl;

import lk.ijse.possystembackend.bo.custom.ItemBO;
import lk.ijse.possystembackend.dao.custom.ItemDAO;
import lk.ijse.possystembackend.dao.custom.impl.ItemDAOImpl;
import lk.ijse.possystembackend.dto.CustomerDTO;
import lk.ijse.possystembackend.dto.ItemDTO;
import lk.ijse.possystembackend.entity.Item;

import java.sql.Connection;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = new ItemDAOImpl();
    @Override
    public void saveItem(ItemDTO itemDTO, Connection connection) {
        itemDAO.saveItem(new Item(itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getQty(),itemDTO.getUnitPrice()),connection);
    }

    @Override
    public void updateItem(ItemDTO itemDTO, Connection connection) {
        itemDAO.updateItem(new Item(itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getQty(),itemDTO.getUnitPrice()),connection);
    }

    @Override
    public void deleteItem(String code, Connection connection) {
        itemDAO.deleteItem(code,connection);
    }
    @Override
    public ArrayList<ItemDTO> getAllItem(Connection connection) {
        ArrayList<ItemDTO>allItem = itemDAO.getAllItem(connection);
        return allItem;
    }
}
