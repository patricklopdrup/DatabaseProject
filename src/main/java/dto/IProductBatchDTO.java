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

    //TODO Add commodity batch DTO when created
    void addCommodityBatch(CommodityBatchDTO commodity);

    //TODO Add commodity batch DTO when created
    void removeCommodityBatch(CommodityBatchDTO commodity);

    List<ICommodityBatchDTO> getCommodityBatches();
}
