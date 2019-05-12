package dto;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProductBatchDTO implements IProductBatchDTO {

    private int batchID;
    private int batchAmount;
    private IRecipeDTO recipe;
    private java.sql.Timestamp time;
    private List<ICommodityBatchDTO> commodityBatchList = new ArrayList<>();

    @Override
    public int getID() { return batchID; }

    @Override
    public void setBatchID(int ID){ this.batchID = ID; }

    @Override
    public void setBatchAmount(int size) { this.batchAmount = size; }

    @Override
    public int getBatchAmount() { return batchAmount; }

    @Override
    public void setRecipe(IRecipeDTO recipe) { this.recipe = recipe; }

    @Override
    public IRecipeDTO getRecipe() { return recipe; }

    @Override
    public void addCommodityBatch(ICommodityBatchDTO commodityBatch) {
        commodityBatchList.add(commodityBatch);
    }

    @Override
    public void removeCommodityBatch(ICommodityBatchDTO commodityBatch) {
        commodityBatchList.remove(commodityBatch);
    }

    @Override
    public List<ICommodityBatchDTO> getCommodityBatches() { return this.commodityBatchList; }

    @Override
    public void setTime(Timestamp time) { this.time = time; }

    @Override
    public Timestamp getTime() { return time; }
}
