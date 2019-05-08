package test;

import dal.IUserDAO;
import dal.UserDAOImpl;
import dto.IUserDTO;
import dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

public class DALTest {

    IUserDAO userDAO = new UserDAOImpl();

    @Test
    public void test() {
        try {
            //create testUser
            UserDTO testUser = new UserDTO();
            testUser.setUserId(420);
            testUser.setUserName("Bo Boesen");
            testUser.setIni("BB");
            List<String> roles = new ArrayList<>();
            roles.add("Laborant");
            testUser.setRoles(roles);
            userDAO.createUser(testUser);

            //receive testUser
            IUserDTO receivedUser = userDAO.getUser(420);
            assertEquals(testUser.getEmployeeName(), receivedUser.getEmployeeName());
            assertEquals(testUser.getIni(), receivedUser.getIni());
            assertEquals(testUser.getRoles().get(0), receivedUser.getRoles().get(0));
            assertEquals(testUser.getRoles().size(), receivedUser.getRoles().size());
            List<IUserDTO> allUsers = userDAO.getUserList();
            boolean found = false;
            for(IUserDTO user: allUsers) {
                if(user.getEmployeeID() == testUser.getEmployeeID()) {
                    assertEquals(testUser.getEmployeeName(), user.getEmployeeName());
                    assertEquals(testUser.getIni(), user.getIni());
                    assertEquals(testUser.getRoles().get(0), user.getRoles().get(0));
                    assertEquals(testUser.getRoles().size(), user.getRoles().size());
                    found = true;
                }
            }
            if(!found) {fail();}

            //update testUser
            testUser.setUserName("Ib Ibsen");
            testUser.setIni("II");
            roles.remove(0);
            roles.add("Farmaceut");
            testUser.setRoles(roles);
            userDAO.updateUser(testUser);

            //check if updated
            receivedUser = userDAO.getUser(420);
            assertEquals(testUser.getEmployeeName(), receivedUser.getEmployeeName());
            assertEquals(testUser.getIni(), receivedUser.getIni());
            assertEquals(testUser.getRoles().get(0), receivedUser.getRoles().get(0));
            assertEquals(testUser.getRoles().size(), receivedUser.getRoles().size());

            //create an Administrator
            UserDTO admin = new UserDTO();
            admin.setUserId(1);
            admin.setUserName("Admin Administrator");
            admin.setIni("AA");
            List<String> adminRole = new ArrayList<>();
            adminRole.add("Administrator");
            admin.setRoles(adminRole);

            //deleting the testUser using the admin
            userDAO.deleteUser(testUser.getEmployeeID(), admin);

            //check if deleted
            allUsers = userDAO.getUserList();
            for(IUserDTO user: allUsers) {
                if(user.getEmployeeID() == testUser.getEmployeeID()) {
                    fail();
                }
            }

        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
            fail();
        }
    }

}
