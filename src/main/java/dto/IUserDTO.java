package dto;

import java.util.List;

public interface IUserDTO {
    int getEmployeeID();

    void setUserId(int userId);

    String getUserName();

    void setUserName(String userName);

    void setFirstName(String firstName);

    void setSurname(String surname);

    String getSurname();

    String getIni();

    String getFirstName();

    void setIni(String ini);

    List<String> getRoles();

    void setRoles(List<String> roles);

    void addRole(String role);

    boolean removeRole(String role);
}
