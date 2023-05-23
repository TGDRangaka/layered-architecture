package dao;

import db.DBConnection;
import model.ItemDTO;
import model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderDAOImpl {
    public ItemDTO findItem(String newItemCode) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
        pstm.setString(1, newItemCode + "");
        ResultSet rst = pstm.executeQuery();
        rst.next();

        return new ItemDTO(newItemCode + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
    }

    public String generateNewId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

        ArrayList<String> customerIds = new ArrayList<>();
        while (rst.next()) {
            customerIds.add(rst.getString("id"));
        }

        return customerIds;
    }

    public ArrayList<String> getAllItemCodes() throws SQLException, ClassNotFoundException{
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item");

        ArrayList<String> itemCodes = new ArrayList<>();

        while (rst.next()) {
            itemCodes.add(rst.getString("code"));
        }

        return itemCodes;
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT oid FROM `Orders` WHERE oid=?");
        stm.setString(1, orderId);
        /*if order id already exist*/
        if (stm.executeQuery().next()) {

        }

        connection.setAutoCommit(false);
        stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
        stm.setString(1, orderId);
        stm.setDate(2, Date.valueOf(orderDate));
        stm.setString(3, customerId);

        if (stm.executeUpdate() != 1) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detail : orderDetails) {
            boolean isOrderDetailSaved = saveOrderDetails(orderId, detail);

            if (!isOrderDetailSaved) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

//                //Search & Update Item
            ItemDTO item = findItem(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

            ItemDAOImpl itemDAO = new ItemDAOImpl();
            boolean isItemUpdated = itemDAO.updateItem(item);

            if (!isItemUpdated) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    private boolean saveOrderDetails(String orderId, OrderDetailDTO detail) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)");

        stm.setString(1, orderId);
        stm.setString(2, detail.getItemCode());
        stm.setBigDecimal(3, detail.getUnitPrice());
        stm.setInt(4, detail.getQty());

        return stm.executeUpdate() > 0;
    }
}
