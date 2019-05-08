package dal;

import dto.IUserDTO;
import dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {

    public String[] roller = {"Farmaceut", "Produktionsleder", "Laborant", "Administrator"};

    private Connection createConnection() throws DALException {
        try {
            return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185092?"
                    + "user=s185092&password=C7uzj8I1GztZQ40cOeE7f");
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public IUserDTO getUser(int employeeID) throws DALException {
        try (Connection c = createConnection()){
            PreparedStatement prest = c.prepareStatement("select * from employee where employeeID = ?");
            prest.setInt(1, employeeID);
            ResultSet resultSet = prest.executeQuery();

            PreparedStatement role = c.prepareStatement("select * from roles where roleID = ?");
            role.setInt(1, employeeID);
            ResultSet roleSet = role.executeQuery();
            //makeing user from resultSet
            IUserDTO user = new UserDTO();
            while(resultSet.next()) {
                user.setUserId(resultSet.getInt("employeeID"));
                user.setUserName(resultSet.getString("employeeName"));
                user.setIni(resultSet.getString("employeeIni"));
            }
            while (roleSet.next()) {
                user.addRole(roleSet.getString("roleName"));
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
        try (Connection c = createConnection()) {
            List<IUserDTO> users = new ArrayList<>();
            Statement st = c.createStatement();
            ResultSet resultSet = st.executeQuery("select * from employee");

            while(resultSet.next()) {
                st = c.createStatement();
                int employeeID = resultSet.getInt("employeeID");
                ResultSet roleSet = st.executeQuery("select * from roles where roleID = " + employeeID);
                IUserDTO user = new UserDTO();
                user.setUserId(resultSet.getInt("employeeID"));
                user.setUserName(resultSet.getString("employeeName"));
                user.setIni(resultSet.getString("employeeIni"));

                while (roleSet.next()) {
                    user.addRole(roleSet.getString("roleName"));
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
        try (Connection c = createConnection()){
            //employeeID, employeeName, employeeIni
            PreparedStatement prest = c.prepareStatement("insert into employee values(?,?,?)");
            prest.setInt(1, user.getEmployeeID());
            prest.setString(2, user.getEmployeeName());
            prest.setString(3, user.getIni());
            prest.executeUpdate();

            for(String role: user.getRoles()) {
                PreparedStatement roleSt = c.prepareStatement("insert into roles(roleID, roleName) values(?,?)");
                roleSt.setInt(1, user.getEmployeeID());
                roleSt.setString(2, role);
                roleSt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }


    @Override
    public boolean checkRoles(List<String> rolesToCheck) {
        int counter = 0;
        for(String roleToBe: roller) {
            for(String role: rolesToCheck) {
                if(roleToBe.equals(role)) {
                    counter++;
                }
            }
        }
        //check if eath role is a role you can actually be
        return counter == rolesToCheck.size();
    }


    @Override
    public void updateUser(IUserDTO user) throws DALException {
        try (Connection c = createConnection()) {
            //delete every role
            PreparedStatement prest = c.prepareStatement("delete from roles where roleID = ?");
            prest.setInt(1, user.getEmployeeID());
            prest.executeUpdate();

            //update database
            PreparedStatement values = c.prepareStatement("update employee set employeeName = ?, employeeIni = ? where employeeID = ?");
            values.setString(1, user.getEmployeeName());
            values.setString(2, user.getIni());
            values.setInt(3, user.getEmployeeID());
            values.executeUpdate();

            //update roles
            for(String role: user.getRoles()) {
                PreparedStatement roleSt = c.prepareStatement("insert into roles(roleID, roleName) values(?,?)");
                roleSt.setInt(1, user.getEmployeeID());
                roleSt.setString(2, role);
                roleSt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public boolean deleteUser(int employeeID, IUserDTO admin) throws DALException {
        try (Connection c = createConnection()) {

            for(String role: admin.getRoles()) {
                if(role.equals("Administrator")) {
                    PreparedStatement prest = c.prepareStatement("delete from employee where employeeID = ?");
                    prest.setInt(1, employeeID);
                    prest.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return false;
    }


    public static void main(String[] args) throws DALException {
        IUserDAO userDAO = new UserDAOImpl();
        IUserDTO user = userDAO.getUser(1);

        System.out.println(user.getRoles());

        System.out.println(userDAO.deleteUser(3, user));

        List<String> hej = new ArrayList<>();
        hej.add("Farmaceut");
        hej.add("Laborant");

        System.out.println(((UserDAOImpl) userDAO).checkRoles(hej));
    }
}

