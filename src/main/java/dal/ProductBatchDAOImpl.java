package dal;

import com.mysql.cj.protocol.Resultset;
import dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBatchDAOImpl implements IProductBatchDAO{

    IRecipeDAO recipeDAO = new RecipeDAOImpl();
    ICommodityBatchDAO commodityBatchDAO = new CommodityBatchDAOImpl();

    @Override
    public void createProductBatch(IProductBatchDTO product, IUserDTO user) throws MySQL_conn.DALException {
        UserDAOImpl testUser = new UserDAOImpl();

        if(!testUser.hasRole(user,"Project Manager")) return;
        try{
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prepst = c.prepareStatement("INSERT into productBatch(ID, Amount) values (?,?)");
            prepst.setInt(1,product.getID());
            prepst.setInt(2,product.getBatchAmount());
            prepst.executeUpdate();

            for( ICommodityBatchDTO commodity: product.getCommodityBatches()) {
                prepst = c.prepareStatement("INSERT into productbatch_commoditybatch(productBatchID, commodityBatchID) values(?,?)");
                prepst.setInt(1,product.getID());
                prepst.setInt(2,commodity.getBatchID());
                prepst.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IProductBatchDTO getProductBatch(int id) throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prepst = c.prepareStatement("SELECT * FROM productBatch WHERE BatchID = ?");
            prepst.setInt(1,id);
            ResultSet productBatchResult = prepst.executeQuery();

            if(!productBatchResult.next()) return null;

            IProductBatchDTO productBatchDTO = new ProductBatchDTO();

            productBatchDTO.setBatchID(productBatchResult.getInt("ID"));
            productBatchDTO.setBatchAmount(productBatchResult.getInt("Amount"));
            productBatchDTO.setTime(productBatchResult.getTimestamp("Date"));

            prepst = c.prepareStatement("SELECT recipeID FROM productbatch_recipe WHERE producbatch_recipe.productID = ?");
            prepst.setInt(1,id);
            ResultSet resultOfJoin = prepst.executeQuery();
            resultOfJoin.next();

            IRecipeDTO recipe = recipeDAO.getRecipe(resultOfJoin.getInt("recipeID"));
            productBatchDTO.setRecipe(recipe);

            prepst = c.prepareStatement("SELECT commodityBatchID FROM productbatch_commoditybatch WHERE productBatchID = ?");
            prepst.setInt(1,id);
            ResultSet commodityIDResult = prepst.executeQuery();

            while(commodityIDResult.next()){
                ICommodityBatchDTO commodityBatch = commodityBatchDAO.getCommodityBatch(commodityIDResult.getInt("commodityBatchID"));
                productBatchDTO.addCommodityBatch(commodityBatch);
            }

            return productBatchDTO;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<IProductBatchDTO> getProductBatches() throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prepst = c.prepareStatement("SELECT * FROM productBatch");
            ResultSet productBatchResult = prepst.executeQuery();
            List<IProductBatchDTO> productBatchDTOList = new ArrayList<>();
            while(!productBatchResult.next()){
                IProductBatchDTO productBatchDTO = new ProductBatchDTO();

                productBatchDTO.setBatchID(productBatchResult.getInt("ID"));
                productBatchDTO.setBatchAmount(productBatchResult.getInt("Amount"));
                productBatchDTO.setTime(productBatchResult.getTimestamp("Date"));

                prepst = c.prepareStatement("SELECT recipeID FROM productbatch_recipe WHERE producbatch_recipe.productID = ?");
                prepst.setInt(1,productBatchResult.getInt("ID"));
                ResultSet resultOfJoin = prepst.executeQuery();
                resultOfJoin.next();

                IRecipeDTO recipe = recipeDAO.getRecipe(resultOfJoin.getInt("recipeID"));
                productBatchDTO.setRecipe(recipe);

                prepst = c.prepareStatement("SELECT commodityBatchID FROM productbatch_commoditybatch WHERE productBatchID = ?");
                prepst.setInt(1,productBatchResult.getInt("ID"));
                ResultSet commodityIDResult = prepst.executeQuery();

                while(commodityIDResult.next()){
                    ICommodityBatchDTO commodityBatch = commodityBatchDAO.getCommodityBatch(commodityIDResult.getInt("commodityBatchID"));
                    productBatchDTO.addCommodityBatch(commodityBatch);
                }

                productBatchDTOList.add(productBatchDTO);
            }
            return productBatchDTOList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProductBatch(int productID, IUserDTO admin) throws MySQL_conn.DALException {

    }
}
