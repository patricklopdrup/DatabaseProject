package dal;

import dto.IUserDTO;
import dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {
    @Override
    public IUserDTO getUser(String userName) throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("select * from employee where userName = ?");
            prest.setString(1, userName);
            ResultSet resultSet = prest.executeQuery();
            if (!resultSet.next()) return new UserDTO();

            PreparedStatement role = c.prepareStatement("SELECT roles.Name FROM roles JOIN employee_roles ON roles.ID = employee_roles.roleID WHERE employee_roles.employeeID = ?");
            role.setInt(1, resultSet.getInt("ID"));
            ResultSet roleSet = role.executeQuery();
            IUserDTO user = new UserDTO();

            user.setUserId(resultSet.getInt("ID"));
            user.setUserName(resultSet.getString("Username"));
            user.setFirstName(resultSet.getString("FirstName"));
            user.setSurname(resultSet.getString("Surname"));
            user.setIni(resultSet.getString("Initials"));

            while (roleSet.next()) {
                user.addRole(roleSet.getString("Name"));
            }
            return user;
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public List<IUserDTO> getUserList() throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            List<IUserDTO> users = new ArrayList<>();
            Statement st = c.createStatement();
            ResultSet resultSet = st.executeQuery("select * from employee");

            while(resultSet.next()) {
                st = c.createStatement();
                int employeeID = resultSet.getInt("ID");
                ResultSet roleSet = st.executeQuery("SELECT roles.Name FROM roles JOIN employee_roles ON roles.ID = employee_roles.roleID WHERE employee_roles.employeeID = " + employeeID);
                IUserDTO user = new UserDTO();
                user.setUserId(resultSet.getInt("ID"));
                user.setUserName(resultSet.getString("Username"));
                user.setFirstName(resultSet.getString("FirstName"));
                user.setSurname(resultSet.getString("Surname"));
                user.setIni(resultSet.getString("Initials"));

                while (roleSet.next()) {
                    user.addRole(roleSet.getString("Name"));
                }
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public void createUser(IUserDTO user, IUserDTO admin) throws MySQL_conn.DALException {
        if (!hasRole(admin, "Administrator")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            //employeeID, employeeName, employeeIni
            PreparedStatement prest = c.prepareStatement("insert into employee (Username, FirstName, Surname, Initials) values(?,?,?,?)");
            prest.setString(1, user.getUserName());
            prest.setString(2, user.getFirstName());
            prest.setString(3, user.getSurname());
            prest.setString(4, user.getIni());
            prest.executeUpdate();

            Statement st = c.createStatement();
            ResultSet resultSet = st.executeQuery("select ID from employee WHERE userName = '" + user.getUserName() + "'");
            resultSet.next();
            user.setUserId(resultSet.getInt("ID"));

            for(String role: user.getRoles()) {
                PreparedStatement roleSt = c.prepareStatement("insert into employee_roles(employeeID, roleID) values(?,?)");
                roleSt.setInt(1, user.getEmployeeID());
                roleSt.setInt(2, getRoleID(role));
                roleSt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public int getRoleID(String role) throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement roleSt = c.prepareStatement("SELECT ID FROM roles WHERE roles.Name = ?");
            roleSt.setString(1, role);
            ResultSet result = roleSt.executeQuery();
            result.next();
            return result.getInt("ID");
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public boolean hasRole(IUserDTO user, String role) throws MySQL_conn.DALException {
        int roleID = getRoleID(role);
        try  {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement roleSt = c.prepareStatement("SELECT * FROM employee_roles WHERE employeeID = ? AND roleID = ?");
            roleSt.setInt(1, user.getEmployeeID());
            roleSt.setInt(2, roleID);
            ResultSet result = roleSt.executeQuery();
            return result.next();
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public void updateUser(IUserDTO user, IUserDTO admin) throws MySQL_conn.DALException {
        if (!hasRole(admin, "Administrator")) return;
        if (user.getUserName().equalsIgnoreCase("admin")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            //delete every role
            PreparedStatement prest = c.prepareStatement("delete from employee_roles where employeeID = ?");
            prest.setInt(1, user.getEmployeeID());
            prest.executeUpdate();

            //update database
            PreparedStatement values = c.prepareStatement("UPDATE employee SET Username = ?, FirstName = ?, Surname = ?, Initials = ? WHERE ID = ?");
            values.setString(1, user.getUserName());
            values.setString(2, user.getFirstName());
            values.setString(3, user.getSurname());
            values.setString(4, user.getIni());
            values.setInt(5, user.getEmployeeID());
            values.executeUpdate();

            //update roles
            for(String role: user.getRoles()) {
                PreparedStatement roleSt = c.prepareStatement("insert into employee_roles(employeeID, roleID) values(?,?)");
                roleSt.setInt(1, user.getEmployeeID());
                roleSt.setInt(2, getRoleID(role));
                roleSt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public boolean deleteUser(String userName, IUserDTO admin) throws MySQL_conn.DALException {
        if (!hasRole(admin, "Administrator")) return false;
        if (userName.equals("admin")) return false;
        try  {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("delete from employee where Username = ?");
            prest.setString(1, userName);
            prest.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }
}

