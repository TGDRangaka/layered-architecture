package dao.custom;

import dao.CrudDAO;
import db.DBConnection;
import model.OrderDTO;

import java.sql.*;

public interface OrderDAO extends CrudDAO<OrderDTO> {
}
