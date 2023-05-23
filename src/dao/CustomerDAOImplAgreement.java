package dao;

import model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAOImplAgreement {
    ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;
    boolean saveCustomer(CustomerDTO customerDTO)  throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomerDTO customerDTO)  throws SQLException, ClassNotFoundException;
    boolean existCustomer(String id)  throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id)  throws SQLException, ClassNotFoundException;
    String generateNewId() throws SQLException, ClassNotFoundException;
}
