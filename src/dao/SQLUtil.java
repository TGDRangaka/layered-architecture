package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtil {
    public static <T>T executeQuery(String query, Object... args) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(query);

        for(int i = 0; i < args.length; i++){
            pstm.setObject(i + 1, args[i]);
        }

        if(query.startsWith("SELECT") || query.startsWith("select")){
            return (T)pstm.executeQuery();
        }else{
            return (T)(Boolean)(pstm.executeUpdate() > 0);
        }
    }
}
