package dal;

import dto.IUserDTO;

import java.util.List;

public interface IUserDAO {
    //Create
    void createUser(IUserDTO user, IUserDTO admin) throws MySQL_conn.DALException;
    //Read

    IUserDTO getUser(String userName) throws MySQL_conn.DALException;

    List<IUserDTO> getUserList() throws MySQL_conn.DALException;
    //Update
    void updateUser(IUserDTO user, IUserDTO admin) throws MySQL_conn.DALException;
    //Delete
    boolean deleteUser(String userName, IUserDTO admin) throws MySQL_conn.DALException;


    int getRoleID(String role) throws MySQL_conn.DALException;

    boolean hasRole(IUserDTO user, String role) throws MySQL_conn.DALException;

}