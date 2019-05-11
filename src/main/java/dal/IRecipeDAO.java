package dal;

import dto.IRecipeDTO;

import java.util.List;

public interface IRecipeDAO {
    void createRecipe(IRecipeDTO Recipe, int userID) throws MySQL_conn.DALException;

    IRecipeDTO getRecipe(int RecipeID) throws MySQL_conn.DALException;

    IRecipeDTO getRecipe(String RecipeName) throws MySQL_conn.DALException;

    List<IRecipeDTO> getRecipes() throws MySQL_conn.DALException;

    void updateRecipe(IRecipeDTO Recipe, int userID) throws MySQL_conn.DALException;

    void deleteRecipe(int RecipeID, int userID) throws MySQL_conn.DALException;



}
