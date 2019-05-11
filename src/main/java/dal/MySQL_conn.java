package dal;

import dal.IUserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL_conn {
    private static Connection connection = null;
    public static Connection getConnection() throws DALException {
        if (connection != null) {
            return connection;
        } else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185092?"
                        + "user=s185092&password=C7uzj8I1GztZQ40cOeE7f");
                return connection;
            } catch (SQLException e) {
                throw new DALException(e.getMessage());
            }
        }
    }

    public static class DALException extends Exception {
        //Til Java serialisering...
        private static final long serialVersionUID = 7355418246336739229L;

        public DALException(String msg, Throwable e) {
            super(msg,e);
        }

        public DALException(String msg) {
            super(msg);
        }

    }
}
