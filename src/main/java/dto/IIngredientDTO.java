package dto;

public interface IIngredientDTO {
    void setIngredientID(int ID);

    int getIngredientID();

    void setIngredientName(String name);

    String getIngredientName();

    void setOrderStatus(boolean status);

    boolean getOrderStatus();

    void setType(boolean type);

    boolean getType();

}
