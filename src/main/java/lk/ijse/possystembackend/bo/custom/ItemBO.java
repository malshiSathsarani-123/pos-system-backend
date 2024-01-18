package lk.ijse.possystembackend.bo.custom;

import lk.ijse.possystembackend.bo.SuperBO;
import lk.ijse.possystembackend.dto.ItemDTO;

import java.sql.Connection;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {

    void saveItem(ItemDTO itemDTO, Connection connection);

    void updateItem(ItemDTO itemDTO, Connection connection);

    void deleteItem(String code, Connection connection);

    ArrayList<ItemDTO> getAllItem(Connection connection);
}
