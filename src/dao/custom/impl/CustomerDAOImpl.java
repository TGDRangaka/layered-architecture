package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.CustomerDAO;
import entity.Customer;
import model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Customer");

        while (rst.next()) {
            Customer customer = new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }


    public boolean add(Customer dto) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Customer (id,name, address) VALUES (?,?,?)";
        return SQLUtil.executeQuery(query, dto.getId(), dto.getName(), dto.getAddress());
    }

    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        String query = "UPDATE Customer SET name=?, address=? WHERE id=?";
        return SQLUtil.executeQuery(query, dto.getName(), dto.getAddress(), dto.getId());
    }

    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT id FROM Customer WHERE id=?", id);
        return rs.next();
    }

    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("DELETE FROM Customer WHERE id=?", id);
    }


    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Customer WHERE id=?", id);
        rst.next();
        return new Customer(id + "", rst.getString("name"), rst.getString("address"));
    }

}
