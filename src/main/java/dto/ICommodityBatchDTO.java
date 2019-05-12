package dto;

import java.util.List;

public interface ICommodityBatchDTO {

    void setBatchID(int batchID);

    void setAmount(int amount);

    void setManufacturer(String manufacturer);

    void setRemainder(int remainder);

    int getBatchID();

    int getAmount();

    String getManufacturer();

    int getRemainder();

    List<IIngredientDTO> getIngredientList();

    void addIngredientToList(IIngredientDTO ingredient);
}
