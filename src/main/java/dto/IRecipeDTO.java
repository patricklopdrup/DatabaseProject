package dto;

public interface IRecipeDTO {
    int getRecipeID();

    void setRecipeID(int recipeID);

    String getRecipeName();

    void setRecipeName(String Name);

    void addIngredient(IngredientDTO ingredient);

    void removeIngredient(IngredientDTO ingredient);

}
