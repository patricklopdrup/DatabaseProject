package dal;

import dto.IRecipeDTO;
import dto.IUserDTO;

import java.util.List;

public interface IRecipeDAO {
    void createRecipe(IRecipeDTO Recipe, IUserDTO user) throws MySQL_conn.DALException;

    IRecipeDTO getRecipe(int RecipeID) throws MySQL_conn.DALException;

    IRecipeDTO getRecipe(String RecipeName) throws MySQL_conn.DALException;

    List<IRecipeDTO> getRecipes() throws MySQL_conn.DALException;

    void updateRecipe(IRecipeDTO Recipe, IUserDTO user) throws MySQL_conn.DALException;

    void deleteRecipe(int RecipeID, IUserDTO user) throws MySQL_conn.DALException;
}
