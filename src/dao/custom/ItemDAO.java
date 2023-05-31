package dao.custom;

import dao.CrudDAO;
import db.DBConnection;
import entity.Item;
import model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {
}
