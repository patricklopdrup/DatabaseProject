package dto;
import java.sql.Timestamp;
import java.util.List;
import dal.ICommodityBatchDAO;

public interface IProductBatchDTO {
    int getID();

    void setBatchID(int id);

    void setBatchAmount(int size);

    int getBatchAmount();

    void setRecipe(IRecipeDTO recipe);

    IRecipeDTO getRecipe();

    void setTime(Timestamp time);

    Timestamp getTime();


    void addCommodityBatch(ICommodityBatchDTO commodity);


    void removeCommodityBatch(ICommodityBatchDTO commodity);

    List<ICommodityBatchDTO> getCommodityBatches();
}
