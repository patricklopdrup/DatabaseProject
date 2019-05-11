package test;

import dal.IUserDAO;
import dal.MySQL_conn;
import dal.UserDAOImpl;
import dto.IUserDTO;
import dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

public class UserTest {

    IUserDAO userDAO = new UserDAOImpl();

    String[][][] userInfo = new String[][][]{
            {{"GHansen", "Gert", "Hansen", "GHA"}, {"Pharmacist"}},
            {{"FLarsen", "Frederik", "Larsen", "FLA"}, {"Project Manager", "Administrator"}}
    };

    String[][][] userInfo_update = new String[][][]{
            {{"GHansen", "Gert", "Larsen", "FLA"}, {"Lab Technician"}},
            {{"FLarsen", "Frederik", "Hansen", "GHA"}, {"Pharmacist"}}
    };

    private boolean checkUsers(IUserDTO[] testUsers) throws MySQL_conn.DALException {
        IUserDTO receivedUser;
        for (IUserDTO user : testUsers) {
            receivedUser = userDAO.getUser(user.getUserName());
            assertEquals(user.getUserName(), receivedUser.getUserName());
            assertEquals(user.getFirstName(), receivedUser.getFirstName());
            assertEquals(user.getSurname(), receivedUser.getSurname());
            assertEquals(user.getIni(), receivedUser.getIni());
            assertEquals(user.getRoles().get(0), receivedUser.getRoles().get(0));
            assertEquals(user.getRoles().size(), receivedUser.getRoles().size());
        }
        return true;
    }

    @Test
    public void userManagement() {
        try {
            IUserDTO admin = userDAO.getUser("admin");
            IUserDTO[] testUsers = new UserDTO[2];
            for (int i = 0 ; i < 2; i++) {
                testUsers[i] = new UserDTO();
                testUsers[i].setUserName(userInfo[i][0][0]);
                testUsers[i].setFirstName(userInfo[i][0][1]);
                testUsers[i].setSurname(userInfo[i][0][2]);
                testUsers[i].setIni(userInfo[i][0][3]);
                for(String role : userInfo[i][1]) {
                    testUsers[i].addRole(role);
                }
                userDAO.createUser(testUsers[i], admin);
            }
            checkUsers(testUsers);



            testUsers = new UserDTO[2];
            for (int i = 0 ; i < 2; i++) {
                testUsers[i] = new UserDTO();
                testUsers[i] = userDAO.getUser(userInfo_update[i][0][0]);
                testUsers[i].setFirstName(userInfo_update[i][0][1]);
                testUsers[i].setSurname(userInfo_update[i][0][2]);
                testUsers[i].setIni(userInfo_update[i][0][3]);
                List<String> roles = new ArrayList<>(Arrays.asList(userInfo[i][1]));
                testUsers[i].setRoles(roles);
                userDAO.updateUser(testUsers[i], admin);
            }

            checkUsers(testUsers);




            for (int i = 0 ; i < 2; i++) {
                userDAO.deleteUser(userInfo_update[i][0][0], admin);
                if(userDAO.getUser(userInfo_update[i][0][0]).getUserName() != null) fail();

            }
        } catch (MySQL_conn.DALException e) {
            e.printStackTrace();
            fail();
        }
    }

//    @Test
//    public void test() {
//        try {
//            IUserDTO admin = userDAO.getUser("admin");
//
//            //create testUser
//            UserDTO testUser = new UserDTO();
//            testUser.setUserName("Bobbo");
//            testUser.setFirstName("Bob");
//            testUser.setSurname("Bøsse");
//            testUser.setIni("BB");
//            List<String> roles = new ArrayList<>();
//            roles.add("Lab Technician");
//            testUser.setRoles(roles);
//            userDAO.createUser(testUser, admin);
//
//            //receive testUser
//            IUserDTO receivedUser = userDAO.getUser("Bobbo");
//            assertEquals(testUser.getUserName(), receivedUser.getUserName());
//            assertEquals(testUser.getFirstName(), receivedUser.getFirstName());
//            assertEquals(testUser.getSurname(), receivedUser.getSurname());
//            assertEquals(testUser.getIni(), receivedUser.getIni());
//            assertEquals(testUser.getRoles().get(0), receivedUser.getRoles().get(0));
//            assertEquals(testUser.getRoles().size(), receivedUser.getRoles().size());
//            List<IUserDTO> allUsers = userDAO.getUserList();
//            boolean found = false;
//            for(IUserDTO user: allUsers) {
//                if(user.getEmployeeID() == testUser.getEmployeeID()) {
//                    assertEquals(testUser.getUserName(), user.getUserName());
//                    assertEquals(testUser.getIni(), user.getIni());
//                    assertEquals(testUser.getRoles().get(0), user.getRoles().get(0));
//                    assertEquals(testUser.getRoles().size(), user.getRoles().size());
//                    found = true;
//                }
//            }
//            if(!found) {fail();}
//
//            //update testUser
//            testUser.setUserName("IbIbsen");
//            testUser.setFirstName("Ib");
//            testUser.setSurname("Ibsen");
//            testUser.setIni("II");
//            roles.remove(0);
//            roles.add("Pharmacist");
//            testUser.setRoles(roles);
//            userDAO.updateUser(testUser, admin);
//
//            //check if updated
//            receivedUser = userDAO.getUser("IbIbsen");
//            assertEquals(testUser.getUserName(), receivedUser.getUserName());
//            assertEquals(testUser.getIni(), receivedUser.getIni());
//            assertEquals(testUser.getRoles().get(0), receivedUser.getRoles().get(0));
//            assertEquals(testUser.getRoles().size(), receivedUser.getRoles().size());
//
//            //create an Administrator
//            UserDTO admin2 = new UserDTO();
//            admin2.setUserName("Admin Administrator");
//            admin2.setIni("AA");
//            List<String> adminRole = new ArrayList<>();
//            adminRole.add("Administrator");
//            admin2.setRoles(adminRole);
//
//            //deleting the testUser using the admin
//            userDAO.deleteUser(testUser.getUserName(), admin2);
//
//            //check if deleted
//            allUsers = userDAO.getUserList();
//            for(IUserDTO user: allUsers) {
//                if(user.getUserName() == testUser.getUserName()) {
//                    fail();
//                }
//            }
//
//        } catch (IUserDAO.DALException e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//
//
////    @Test
////    public void checkRolesTest() {
////        try {
////            UserDTO testUser = new UserDTO();
////            testUser.setUserId(131);
////            testUser.setUserName("Jeg Tester");
////            testUser.setIni("JT");
////
////            List<String> testRoles = new ArrayList<>();
////            testRoles.add("Farmaceut");
////            testRoles.add("Administrator");
////            testRoles.add("fail");
////            testUser.setRoles(testRoles);
////            userDAO.createUser(testUser);
////
////            //test passes if false; should be because role:"fail"
////            assertFalse(userDAO.checkRoles(testUser.getRoles()));
////
////            //create an Administrator
////            UserDTO admin = new UserDTO();
////            admin.setUserId(1);
////            admin.setUserName("Admin Administrator");
////            admin.setIni("AA");
////            List<String> adminRole = new ArrayList<>();
////            adminRole.add("Administrator");
////            admin.setRoles(adminRole);
////
////            //deleting user again
////            userDAO.deleteUser(testUser.getEmployeeID(), admin);
////
////
////        } catch (IUserDAO.DALException e) {
////            e.printStackTrace();
////            fail();
////        }
////    }


}
