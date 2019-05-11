package dal;

import dto.IIngredientDTO;
import dto.IUserDTO;

import java.util.List;

public interface IIngredientDAO {
    void addIngredient(IIngredientDTO product, IUserDTO user) throws MySQL_conn.DALException;

    IIngredientDTO getIngredient(int id) throws MySQL_conn.DALException;

    IIngredientDTO getIngredient(String name) throws MySQL_conn.DALException;

    List<IIngredientDTO> getIngredients() throws MySQL_conn.DALException;

    void deleteIngredient(int ingredientID, IUserDTO user) throws MySQL_conn.DALException;

    void deleteIngredient(String ingredientName, IUserDTO user) throws MySQL_conn.DALException;
}
