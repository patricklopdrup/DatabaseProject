package dto;


import java.util.ArrayList;
import java.util.List;

public class RecipeDTO implements IRecipeDTO {
    private int ID;
    private String name;
    private int batchSize;
    private List<IIngredientDTO> ingredients = new ArrayList<>();

    @Override
    public int getRecipeID() {
        return ID;
    }

    @Override
    public void setRecipeID(int recipeID) {
        ID = recipeID;
    }

    @Override
    public String getRecipeName() {
        return name;
    }

    @Override
    public void setRecipeBatchSize(int size) {
        batchSize = size;
    }

    @Override
    public int getRecipeBatchSize() {
        return batchSize;
    }

    @Override
    public void setRecipeName(String name) {
        this.name = name;
    }

    @Override
    public void addIngredient(IIngredientDTO ingredient, double weight) {
        ingredient.setAmount(weight);
        if (!ingredients.contains(ingredient)) ingredients.add(ingredient);
    }

    @Override
    public void removeIngredient(IIngredientDTO ingredient) {
        ingredients.remove(ingredient);
    }

    @Override
    public List<IIngredientDTO> getIngredients() {
        return ingredients;
    }
}
