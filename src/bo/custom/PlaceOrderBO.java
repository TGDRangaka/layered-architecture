package bo.custom;

import bo.SuperBO;
import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDetailDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;

    public boolean existItem(String id) throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    public String generateNewOrderId() throws SQLException, ClassNotFoundException;

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException;

    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails);
}
