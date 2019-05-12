package dto;
import java.util.ArrayList;
import java.util.List;

public class CommodityBatchDTO implements ICommodityBatchDTO {

    private int batchID;
    private int amount;
    private String manufacturer;
    private int remainder;
    private List<IIngredientDTO> ingredientList = new ArrayList<>();

    @Override
    public void setBatchID(int ID) {
        this.batchID = ID;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    @Override
    public int getBatchID() {
        return this.batchID;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public String getManufacturer() {
        return this.manufacturer;
    }

    @Override
    public int getRemainder() {
        return this.remainder;
    }

    @Override
    public List<IIngredientDTO> getIngredientList() {
        return ingredientList;
    }

    @Override
    public void addIngredientToList(IIngredientDTO ingredient) {
        ingredientList.add(ingredient);
    }

}
