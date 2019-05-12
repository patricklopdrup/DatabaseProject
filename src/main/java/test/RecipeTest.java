package test;

import dal.*;
import dto.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class RecipeTest {
    IUserDAO userDAO = new UserDAOImpl();
    IIngredientDAO ingredientDAO = new IngredientDAOImpl();
    IRecipeDAO recipeDAO = new RecipeDAOImpl();

    String[][] recipeInfo = new String[][]{
            {"Sildenafil", "50"},
            {"Norethisteron", "500"}
    };

    double[][][] recipeIngredients = new double[][][] {
            {{55, 25}, {45, 20}, {46, 25}, {47, 10}, {48, 5}, {49, 6}, {50, 0.13}, {51, 0.5}, {52, 20}, {53, 1}, {54, 0.02}},
            {{56, 1}, {57, 0.5}, {58, 50}, {59, 10}, {60, 15}, {61, 120}}
    };

    private boolean checkRecipes(IRecipeDTO[] testRecipes) throws MySQL_conn.DALException {
        IRecipeDTO receivedRecipe;
        for (IRecipeDTO recipe : testRecipes) {
            receivedRecipe = recipeDAO.getRecipe(recipe.getRecipeName());
            for(int i = 0; i < recipe.getIngredients().size(); i++) {
                assertEquals(recipe.getIngredients().get(i).getIngredientName(), recipe.getIngredients().get(i).getIngredientName());
                assertEquals(recipe.getIngredients().get(i).getType(), recipe.getIngredients().get(i).getType());
                assertEquals(recipe.getIngredients().get(i).getAmount(), recipe.getIngredients().get(i).getAmount(), 0.0);
            }
            assertEquals(recipe.getRecipeBatchSize(), receivedRecipe.getRecipeBatchSize());
        }
        return true;
    }

    @Test
    public void recipeManagement() {
        try {
            IUserDTO user = userDAO.getUser("farmaceut");
            IRecipeDTO[] testRecipes = new IRecipeDTO[2];

            for (int i = 0; i < 2; i++) {
                testRecipes[i] = new RecipeDTO();
                testRecipes[i].setRecipeName(recipeInfo[i][0]);
                testRecipes[i].setRecipeBatchSize(Integer.parseInt(recipeInfo[i][1]));
                for (double[] in : recipeIngredients[i]) {
                    IIngredientDTO ingredient = ingredientDAO.getIngredient((int)in[0]);
                    testRecipes[i].addIngredient(ingredient, in[1]);
                }
                recipeDAO.createRecipe(testRecipes[i], user);
            }

            checkRecipes(testRecipes);

            for (int i = 0 ; i < 2; i++) {
                testRecipes[i] = recipeDAO.getRecipe(testRecipes[i].getRecipeName());
                recipeDAO.deleteRecipe(testRecipes[i].getRecipeID(), user);
                if(recipeDAO.getRecipe(testRecipes[i].getRecipeID()) != null) fail();
            }

            MySQL_conn.getConnection().close();
        } catch (MySQL_conn.DALException | SQLException e) {
            e.printStackTrace();
            fail();
        }
    }
}
