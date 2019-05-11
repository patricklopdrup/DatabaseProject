package test;

import dal.*;
import dto.IIngredientDTO;
import dto.IUserDTO;
import dto.IngredientDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class IngredientTest {
    IUserDAO userDAO = new UserDAOImpl();
    IIngredientDAO ingredientDAO = new IngredientDAOImpl();

    String[][] ingredientInfo = new String[][]{
            {"Sildenafil", "1"},
            {"Estradiol", "0"},
            {"Calciumhydrogenphosphat", "0"},
            {"Magnesiumstearat", "0"}
    };


    private boolean checkIngredients(IIngredientDTO[] testIngredients) throws MySQL_conn.DALException {
        IIngredientDTO receivedIngredient;
        for (IIngredientDTO ingredient : testIngredients) {
            receivedIngredient = ingredientDAO.getIngredient(ingredient.getIngredientName());
            assertEquals(ingredient.getIngredientName(), receivedIngredient.getIngredientName());
            assertEquals(ingredient.getType(), receivedIngredient.getType());
            assertFalse(receivedIngredient.getOrderStatus());
        }
        return true;
    }

    @Test
    public void ingredientManagement() {
        try {
            IUserDTO admin = userDAO.getUser("admin");
            IIngredientDTO[] testIngredients = new IIngredientDTO[4];

            for (int i = 0 ; i < 4; i++) {
                testIngredients[i] = new IngredientDTO();
                testIngredients[i].setIngredientName(ingredientInfo[i][0]);
                testIngredients[i].setType(ingredientInfo[i][1].equals("1"));
                ingredientDAO.addIngredient(testIngredients[i], admin);
            }

            checkIngredients(testIngredients);


            for (int i = 0 ; i < 4; i++) {
                ingredientDAO.deleteIngredient(ingredientInfo[i][0], admin);
                if(ingredientDAO.getIngredient(ingredientInfo[i][0]) != null) fail();

            }

        } catch (MySQL_conn.DALException e) {
            e.printStackTrace();
            fail();
        }
    }
}
