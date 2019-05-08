package dal;

import dto.IUserDTO;
import dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO Rename class
public class UserDAOImpl implements IUserDAO {

    String database = "";

    private Connection createConnection() throws DALException {
        try {
            return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185092?"
                    + "user=s185092&password=C7uzj8I1GztZQ40cOeE7f");
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public IUserDTO getUser(int userId) throws DALException {
        //TODO Implement this - should retrieve a user from db and parse it to a UserDTO
        try (Connection c = createConnection()){
            PreparedStatement prest = c.prepareStatement("select * from " + database + " where user_id = ?");
            prest.setInt(1, userId);
            ResultSet resultSet = prest.executeQuery();

            PreparedStatement role = c.prepareStatement("select * from roles where user_id = ?");
            role.setInt(1, userId);
            ResultSet roleSet = role.executeQuery();
            //makeing user from resultSet
            IUserDTO user = new UserDTO();
            while(resultSet.next()) {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("name"));
                user.setIni(resultSet.getString("ini"));
            }
            while (roleSet.next()) {
                user.addRole(roleSet.getString("role"));
            }
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
        try (Connection c = createConnection()) {
            List<IUserDTO> users = new ArrayList<>();
            Statement st = c.createStatement();
            ResultSet resultSet = st.executeQuery("select * from " + database);

            while(resultSet.next()) {
                st = c.createStatement();
                int userId = resultSet.getInt("user_id");
                ResultSet roleSet = st.executeQuery("select * from roles where user_id = " + userId);
                IUserDTO user = new UserDTO();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("name"));
                user.setIni(resultSet.getString("ini"));

                while (roleSet.next()) {
                    user.addRole(roleSet.getString("role"));
                }
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void createUser(IUserDTO user) throws DALException {
        //TODO Implement this - Should insert a user into the db using data from UserDTO object.
        try (Connection c = createConnection()){
            //user_id, name, ini
            PreparedStatement prest = c.prepareStatement("insert into " + database +
                    "values(?,?,?)");
            prest.setInt(1, user.getUserId());
            prest.setString(2, user.getUserName());
            prest.setString(3, user.getIni());
            prest.executeUpdate();

            for(String role: user.getRoles()) {
                PreparedStatement roleSt = c.prepareStatement("insert into roles values(?,?)");
                roleSt.setInt(1, user.getUserId());
                roleSt.setString(2 ,role);
                roleSt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void updateUser(IUserDTO user) throws DALException {
        //TODO Implement this - Should update a user in the db using data from UserDTO object.
        try (Connection c = createConnection()) {
            //delete every role
            PreparedStatement prest = c.prepareStatement("delete from roles where user_id = ?");
            prest.setInt(1, user.getUserId());
            prest.executeUpdate();

            //update database
            PreparedStatement values = c.prepareStatement("update " + database + "set name = ?, ini = ? where user_id = ?");
            values.setString(1, user.getUserName());
            values.setString(2, user.getIni());
            values.setInt(3, user.getUserId());
            values.executeUpdate();

            //update roles
            for(String role: user.getRoles()) {
                PreparedStatement roleSt = c.prepareStatement("insert into roles values(?,?)");
                roleSt.setInt(1, user.getUserId());
                roleSt.setString(2 ,role);
                roleSt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        //TODO Implement this - Should delete a user with the given userid from the db.
        try (Connection c = createConnection()) {
            PreparedStatement prest = c.prepareStatement("delete from " + database + "where user_id = ?");
            prest.setInt(1, userId);
            prest.executeUpdate();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
}
