package dto;

public interface IProductBatchDTO {
    int getID();

    void setSize(int size);

    int getSize();

    void setRecipeID(int ID);

    int getRecipeID();

    //TODO Add commodity batch DTO when created
    void addCommodityBatch();

    //TODO Add commodity batch DTO when created
    void removeCommodityBatch();

    int[] getCommodityBatches();
}
