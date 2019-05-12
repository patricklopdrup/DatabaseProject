package dal;

import com.mysql.cj.protocol.Resultset;
import dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAOImpl implements IRecipeDAO {
    @Override
    public void createRecipe(IRecipeDTO Recipe, IUserDTO user) throws MySQL_conn.DALException {
        UserDAOImpl test = new UserDAOImpl();
        if (!test.hasRole(user, "Pharmacist")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("insert into recipe (Name, batchSize) values(?, ?)");
            prest.setString(1, Recipe.getRecipeName());
            prest.setInt(2, Recipe.getRecipeBatchSize());
            prest.executeUpdate();

            prest = c.prepareStatement("SELECT ID FROM recipe WHERE Name = ?");
            prest.setString(1, Recipe.getRecipeName());
            ResultSet resultSet = prest.executeQuery();
            resultSet.next();

            for (IIngredientDTO ingredient : Recipe.getIngredients()) {
                prest = c.prepareStatement("insert into recipe_ingredients (recipeID, ingredientID, amount) values(?, ?, ?)");
                prest.setInt(1, resultSet.getInt("ID"));
                prest.setInt(2,ingredient.getIngredientID());
                prest.setDouble(3, ingredient.getAmount());
                prest.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }

    }

    @Override
    public IRecipeDTO getRecipe(int RecipeID) throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("SELECT * FROM recipe WHERE ID = ?");
            prest.setInt(1, RecipeID);
            ResultSet resultSet = prest.executeQuery();
            if(!resultSet.next()) return null;

            IRecipeDTO recipe = new RecipeDTO();
            recipe.setRecipeID(resultSet.getInt("ID"));
            recipe.setRecipeName(resultSet.getString("Name"));
            recipe.setRecipeBatchSize(resultSet.getInt("batchSize"));

            prest = c.prepareStatement("SELECT ID, Name, ShouldOrder, Type, amount FROM ingredients JOIN recipe_ingredients ON ingredientID = ingredients.ID WHERE recipeID = ?;");
            prest.setInt(1, RecipeID);
            resultSet = prest.executeQuery();

            while(resultSet.next()) {
                IngredientDTO ingredient = new IngredientDTO();
                ingredient.setIngredientID(resultSet.getInt("ID"));
                ingredient.setIngredientName(resultSet.getString("Name"));
                ingredient.setOrderStatus(resultSet.getBoolean("ShouldOrder"));
                ingredient.setType(resultSet.getBoolean("Type"));
                recipe.addIngredient(ingredient,resultSet.getDouble("Amount"));
            }

            return recipe;
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public IRecipeDTO getRecipe(String RecipeName) throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("SELECT * FROM recipe WHERE Name = ?");
            prest.setString(1, RecipeName);
            ResultSet resultSet = prest.executeQuery();
            if(!resultSet.next()) return null;

            IRecipeDTO recipe = new RecipeDTO();
            recipe.setRecipeID(resultSet.getInt("ID"));
            recipe.setRecipeName(resultSet.getString("Name"));
            recipe.setRecipeBatchSize(resultSet.getInt("batchSize"));

            prest = c.prepareStatement("SELECT ID, Name, ShouldOrder, Type, amount FROM ingredients JOIN recipe_ingredients ON ingredientID = ingredients.ID WHERE recipeID = ?;");
            prest.setInt(1, resultSet.getInt("ID"));
            resultSet = prest.executeQuery();

            while(resultSet.next()) {
                IngredientDTO ingredient = new IngredientDTO();
                ingredient.setIngredientID(resultSet.getInt("ID"));
                ingredient.setIngredientName(resultSet.getString("Name"));
                ingredient.setOrderStatus(resultSet.getBoolean("ShouldOrder"));
                ingredient.setType(resultSet.getBoolean("Type"));
                recipe.addIngredient(ingredient,resultSet.getDouble("Amount"));
            }

            return recipe;
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public List<IRecipeDTO> getRecipes() throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("SELECT * FROM recipe");
            ResultSet resultSet = prest.executeQuery();

            List<IRecipeDTO> recipes = new ArrayList<>();
            while(resultSet.next()) {
                IRecipeDTO recipe = new RecipeDTO();
                recipe.setRecipeID(resultSet.getInt("ID"));
                recipe.setRecipeName(resultSet.getString("Name"));
                recipe.setRecipeBatchSize(resultSet.getInt("batchSize"));

                prest = c.prepareStatement("SELECT ID, Name, ShouldOrder, Type, amount FROM ingredients JOIN recipe_ingredients ON ingredientID = ingredients.ID WHERE recipeID = ?;");
                prest.setInt(1, resultSet.getInt("ID"));
                resultSet = prest.executeQuery();

                while(resultSet.next()) {
                    IngredientDTO ingredient = new IngredientDTO();
                    ingredient.setIngredientID(resultSet.getInt("ID"));
                    ingredient.setIngredientName(resultSet.getString("Name"));
                    ingredient.setOrderStatus(resultSet.getBoolean("ShouldOrder"));
                    ingredient.setType(resultSet.getBoolean("Type"));
                    recipe.addIngredient(ingredient,resultSet.getDouble("Amount"));
                }
                recipes.add(recipe);
            }
            return recipes;
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public void updateRecipe(IRecipeDTO Recipe, IUserDTO user) throws MySQL_conn.DALException {
        UserDAOImpl test = new UserDAOImpl();
        if (!test.hasRole(user, "Pharmacist")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("UPDATE recipe SET Name = ?, batchSize = ? WHERE id = ?");
            prest.setString(1, Recipe.getRecipeName());
            prest.setInt(2, Recipe.getRecipeBatchSize());
            prest.setInt(3, Recipe.getRecipeID());

            //delete every relation
            prest = c.prepareStatement("delete from recipe_ingredients where recipeID = ?");
            prest.setInt(1, Recipe.getRecipeID());
            prest.executeUpdate();

            for(IIngredientDTO ingredient : Recipe.getIngredients()) {
                prest = c.prepareStatement("insert into recipe_ingredients (recipeID, ingredientID, amount) values(?, ?, ?)");
                prest.setInt(1, Recipe.getRecipeID());
                prest.setInt(2, ingredient.getIngredientID());
                prest.setDouble(3, ingredient.getAmount());
                prest.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public void deleteRecipe(int RecipeID, IUserDTO user) throws MySQL_conn.DALException {
        UserDAOImpl test = new UserDAOImpl();
        if (!test.hasRole(user, "Pharmacist")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("delete from recipe where ID = ?");
            prest.setInt(1, RecipeID);
            prest.executeUpdate();
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }
}
