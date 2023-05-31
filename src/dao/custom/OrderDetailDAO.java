package dao.custom;

import dao.CrudDAO;
import db.DBConnection;
import entity.OrderDetails;
import model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface OrderDetailDAO extends CrudDAO<OrderDetails> {
}
