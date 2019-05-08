package dto;

public interface IIngredientDTO {


    int getIngredientId();

    String getIngredientName();
    void setIngredientName(String ingredientName);



    //måske have en boolean der hedder buyMore??
    boolean toBuyMore();
}
