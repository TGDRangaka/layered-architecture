package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.ItemDAO;
import db.DBConnection;
import model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new ItemDTO(rst.getString("code"), rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand")));
        }
        return allItems;
    }

    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("DELETE FROM Item WHERE code=?", code);
    }

    public boolean add(ItemDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        String query = "INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)";
        return SQLUtil.executeQuery(query, dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand());
    }

    public boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException {
        String query = "UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
        return SQLUtil.executeQuery(query, dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand(), dto.getCode());
    }

    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT code FROM Item WHERE code=?", code);
        return rs.next();
    }

    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

    public ItemDTO search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Item WHERE code=?", code + "");
        rst.next();
        return new ItemDTO(code + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
    }
}
