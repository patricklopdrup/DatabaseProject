package dal;

import dto.IProductBatchDTO;
import dto.IUserDTO;

import java.util.List;

public interface IProductBatchDAO {
    void addproductBatch(IProductBatchDTO product, IUserDTO admin) throws MySQL_conn.DALException;

    IProductBatchDTO getProductBatch(int id) throws MySQL_conn.DALException;

    List<IProductBatchDTO> getProductBatches() throws MySQL_conn.DALException;

    void deleteProductBatch(int productID, IUserDTO admin) throws MySQL_conn.DALException;
}
