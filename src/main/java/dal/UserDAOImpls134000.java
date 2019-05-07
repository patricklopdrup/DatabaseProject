package dal;

import dto.IUserDTO;
import dto.UserDTO;

import java.sql.*;
import java.util.List;

//TODO Rename class
public class UserDAOImpls134000 implements IUserDAO {
    //TODO Make a connection to the database
    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/chbu?"
                    + "user=chbu&password=4thVbCaMOxnXi3aJ4");
    }

    @Override
    public IUserDTO getUser(int userId) throws DALException {
        //TODO Implement this - should retrieve a user from db and parse it to a UserDTO
       try (Connection connection = createConnection()){
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("SKRIV DIT QUERY HER!!!!!");
           IUserDTO user = new UserDTO();
           //TODO: Make a user from the resultset
           return user;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }

    @Override
    public IUserDTO getUserByIni(String initials) throws DALException {
        //TODO Implement this - Should retrieve a user from db and parse it to a UserDTO

        return null;
    }


    @Override
    public List<IUserDTO> getUserList() throws DALException {
        //TODO Implement this - Should retrieve ALL users from db and parse the resultset to a List of UserDTO's.
        return null;
    }

    @Override
    public void createUser(IUserDTO user) throws DALException {
        //TODO Implement this - Should insert a user into the db using data from UserDTO object.
    }

    @Override
    public void updateUser(IUserDTO user) throws DALException {
        //TODO Implement this - Should update a user in the db using data from UserDTO object.
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        //TODO Implement this - Should delete a user with the given userid from the db.
    }
}
