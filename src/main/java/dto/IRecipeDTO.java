package dto;

import java.util.List;

public interface IRecipeDTO {
    int getRecipeID();

    void setRecipeID(int recipeID);

    String getRecipeName();

    void setRecipeBatchSize(int size);

    int getRecipeBatchSize();

    void setRecipeName(String Name);

    void addIngredient(IIngredientDTO ingredient, double weight);

    void removeIngredient(IIngredientDTO ingredient);

    List<IIngredientDTO> getIngredients();

}
