package dal;

import dto.ICommodityBatchDTO;
import dto.IIngredientDTO;
import dto.IUserDTO;

import java.util.List;

public interface ICommodityBatchDAO {
    void createCommodityBatch(ICommodityBatchDTO commodityBatch, IUserDTO user) throws MySQL_conn.DALException;

    ICommodityBatchDTO getCommodityBatch(int commodityBatch) throws MySQL_conn.DALException;

    List<ICommodityBatchDTO> getCommodityBatches() throws MySQL_conn.DALException;

    void updateCommodityBatch(ICommodityBatchDTO commodityBatch, IUserDTO user) throws MySQL_conn.DALException;

    void deleteCommodityBatch(int commodityBatchID, IUserDTO user) throws MySQL_conn.DALException;
}
