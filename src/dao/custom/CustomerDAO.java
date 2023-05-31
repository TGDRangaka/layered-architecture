package dao.custom;

import dao.CrudDAO;
import db.DBConnection;
import entity.Customer;
import model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer> {
}
