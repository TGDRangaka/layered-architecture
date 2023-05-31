package dao.custom;

import dao.CrudDAO;
import db.DBConnection;
import entity.Order;
import model.OrderDTO;

import java.sql.*;

public interface OrderDAO extends CrudDAO<Order> {
}
