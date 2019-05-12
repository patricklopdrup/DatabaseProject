package dto;

public class IngredientDTO implements IIngredientDTO {
    private int ingredientID;
    private String ingredientName;
    private boolean status;
    private boolean type;
    private double amount;

    @Override
    public void setIngredientID(int ID) {
        ingredientID = ID;
    }

    @Override
    public int getIngredientID() {
        return ingredientID;
    }

    @Override
    public void setIngredientName(String name) {
        ingredientName = name;
    }

    @Override
    public String getIngredientName() {
        return ingredientName;
    }

    @Override
    public void setOrderStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean getOrderStatus() {
        return status;
    }

    @Override
    public void setType(boolean type) {
        this.type = type;
    }

    @Override
    public boolean getType() {
        return type;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public double getAmount() {
        return amount;
    }
}
