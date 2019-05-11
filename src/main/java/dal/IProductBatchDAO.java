package dal;

import dto.IProductBatchDTO;

import java.util.List;

public interface IProductBatchDAO {
    void addproductBatch(IProductBatchDTO product, int userID) throws MySQL_conn.DALException;

    IProductBatchDTO getProductBatch(int id) throws MySQL_conn.DALException;

    List<IProductBatchDTO> getProductBatches() throws MySQL_conn.DALException;

    void deleteProductBatch(int productID, int userID) throws MySQL_conn.DALException;
}
