package dal;

import com.mysql.cj.protocol.Resultset;
import dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommodityBatchDAOImpl implements ICommodityBatchDAO {


    @Override
    public void createCommodityBatch(ICommodityBatchDTO commodityBatch, IUserDTO user) throws MySQL_conn.DALException {
        UserDAOImpl test = new UserDAOImpl();
        if(!test.hasRole(user,"Production Manager")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("insert into commodityBatch (BatchID, Amount, Manufacturer, Remainder)");
            prest.setInt(2, commodityBatch.getBatchID());
            prest.setInt(2, commodityBatch.getAmount());
            prest.setString(3, commodityBatch.getManufacturer());
            prest.setInt(4, commodityBatch.getAmount());
            prest.executeUpdate();

            prest = c.prepareStatement("insert into commoditybatch_ingredient (commodityBatchID, ingredientID) values(?, ?)");
            prest.setInt(1, commodityBatch.getBatchID());
            prest.setInt(2, commodityBatch.getIngredient().getIngredientID());
            prest.executeUpdate();
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public ICommodityBatchDTO getCommodityBatch(int commodityBatchID) throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("SELECT * FROM commodityBatch WHERE BatchID = ?");
            prest.setInt(1, commodityBatchID);
            ResultSet resultSet = prest.executeQuery();
            if(!resultSet.next()) return null;

            ICommodityBatchDTO commodityBatch = new CommodityBatchDTO();
            commodityBatch.setBatchID(resultSet.getInt("BatchID"));
            commodityBatch.setManufacturer(resultSet.getString("Manufacturer"));
            commodityBatch.setAmount(resultSet.getInt("Amount"));
            commodityBatch.setRemainder(resultSet.getInt("Remaninder"));

            prest = c.prepareStatement("SELECT ID, Name, ShouldOrder, Type, amount FROM ingredients JOIN commoditybatch_ingredient ON ingredientID = ingredients.ID WHERE commodityBatchID = ?;");
            prest.setInt(1, commodityBatchID);
            resultSet = prest.executeQuery();

            IngredientDTO ingredient = new IngredientDTO();
            ingredient.setIngredientID(resultSet.getInt("ID"));
            ingredient.setIngredientName(resultSet.getString("Name"));
            ingredient.setOrderStatus(resultSet.getBoolean("ShouldOrder"));
            ingredient.setType(resultSet.getBoolean("Type"));
            commodityBatch.setIngredient(ingredient);

            return commodityBatch;
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public List<ICommodityBatchDTO> getCommodityBatches() throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("SELECT * FROM commodityBatch");
            ResultSet resultSet = prest.executeQuery();

            List<ICommodityBatchDTO> batches = new ArrayList<>();
            while(resultSet.next()) {
                ICommodityBatchDTO commodityBatch = new CommodityBatchDTO();
                commodityBatch.setBatchID(resultSet.getInt("BatchID"));
                commodityBatch.setManufacturer(resultSet.getString("Manufacturer"));
                commodityBatch.setAmount(resultSet.getInt("Amount"));
                commodityBatch.setRemainder(resultSet.getInt("Remaninder"));

                prest = c.prepareStatement("SELECT ID, Name, ShouldOrder, Type, amount FROM ingredients JOIN commoditybatch_ingredient ON ingredientID = ingredients.ID WHERE commodityBatchID = ?;");
                prest.setInt(1, resultSet.getInt("BatchID"));
                resultSet = prest.executeQuery();

                IngredientDTO ingredient = new IngredientDTO();
                ingredient.setIngredientID(resultSet.getInt("ID"));
                ingredient.setIngredientName(resultSet.getString("Name"));
                ingredient.setOrderStatus(resultSet.getBoolean("ShouldOrder"));
                ingredient.setType(resultSet.getBoolean("Type"));
                commodityBatch.setIngredient(ingredient);

                batches.add(commodityBatch);
            }
            return batches;
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public void updateCommodityBatch(ICommodityBatchDTO commodityBatch, IUserDTO user) throws MySQL_conn.DALException {
        UserDAOImpl test = new UserDAOImpl();
        if(!test.hasRole(user,"Production Manager")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("UPDATE commodityBatch SET Amount = ?, Manufacturer = ?, Remainder = ? WHERE id = ?");
            prest.setInt(1, commodityBatch.getAmount());
            prest.setString(2, commodityBatch.getManufacturer());
            prest.setInt(3, commodityBatch.getRemainder());
            prest.setInt(4, commodityBatch.getBatchID());

            //delete every relation
            prest = c.prepareStatement("delete from commoditybatch_ingredient where commodityBatchID = ?");
            prest.setInt(1, commodityBatch.getBatchID());
            prest.executeUpdate();

            prest = c.prepareStatement("insert into commoditybatch_ingredient (commodityBatchID, ingredientID) values(?, ?)");
            prest.setInt(1, commodityBatch.getBatchID());
            prest.setInt(2, commodityBatch.getIngredient().getIngredientID());
            prest.executeUpdate();
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public void deleteCommodityBatch(int commodityBatchID, IUserDTO user) throws MySQL_conn.DALException {
        UserDAOImpl test = new UserDAOImpl();
        if(!test.hasRole(user,"Production Manager")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("delete from commodityBatch where BatchID = ?");
            prest.setInt(1, commodityBatchID);
            prest.executeUpdate();
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }
}
