package dto;

import java.util.List;

public interface IUserDTO {
    int getEmployeeID();

    void setUserId(int userId);

    String getEmployeeName();

    void setUserName(String userName);

    String getIni();

    void setIni(String ini);

    List<String> getRoles();

    void setRoles(List<String> roles);

    void addRole(String role);

    boolean removeRole(String role);
}
