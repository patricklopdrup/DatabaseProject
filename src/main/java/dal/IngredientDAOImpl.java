package dal;

import com.mysql.cj.protocol.Resultset;
import dto.IIngredientDTO;
import dto.IUserDTO;
import dto.IngredientDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAOImpl implements IIngredientDAO {
    @Override
    public void addIngredient(IIngredientDTO ingredient, IUserDTO user) throws MySQL_conn.DALException {
        UserDAOImpl test = new UserDAOImpl();
        if (!test.hasRole(user, "Administrator")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("insert into ingredients (Name, ShouldOrder, Type) values(?,?,?)");
            prest.setString(1, ingredient.getIngredientName());
            prest.setInt(2, ingredient.getOrderStatus() ? 1 : 0);
            prest.setBoolean(3, ingredient.getType());
            prest.executeUpdate();
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public IIngredientDTO getIngredient(int id) throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("SELECT * FROM ingredients WHERE ID = ?");
            prest.setInt(1, id);
            ResultSet result = prest.executeQuery();

            IIngredientDTO ingredient = new IngredientDTO();
            if (result.next()){
                ingredient.setIngredientID(result.getInt("ID"));
                ingredient.setIngredientName(result.getString("Name"));
                ingredient.setType(result.getBoolean("Type"));
                ingredient.setOrderStatus(result.getBoolean("ShouldOrder"));
            }
            return ingredient;
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public IIngredientDTO getIngredient(String name) throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("SELECT ID FROM ingredients WHERE Name = ?");
            prest.setString(1, name);
            ResultSet result = prest.executeQuery();
            if (!result.next()) return null;

            return getIngredient(result.getInt("ID"));
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public List<IIngredientDTO> getIngredients() throws MySQL_conn.DALException {
        try {
            Connection c = MySQL_conn.getConnection();
            List<IIngredientDTO> ingredients = new ArrayList<>();
            Statement st = c.createStatement();
            ResultSet resultSet = st.executeQuery("select * from ingredients");
            while(resultSet.next()) {
                IIngredientDTO ingredient = new IngredientDTO();
                ingredient.setIngredientID(resultSet.getInt("ID"));
                ingredient.setIngredientName(resultSet.getString("Name"));
                ingredient.setType(resultSet.getBoolean("Type"));
                ingredient.setOrderStatus(resultSet.getBoolean("ShouldOrder"));
                ingredients.add(ingredient);
            }
            return ingredients;
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }

    @Override
    public void deleteIngredient(int ingredientID, IUserDTO user) throws MySQL_conn.DALException {
        UserDAOImpl test = new UserDAOImpl();
        if (!test.hasRole(user, "Administrator")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("delete from ingredients where ID = ?");
            prest.setInt(1, ingredientID);
            prest.executeUpdate();
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }

    }

    @Override
    public void deleteIngredient(String ingredientName, IUserDTO user) throws MySQL_conn.DALException {
        UserDAOImpl test = new UserDAOImpl();
        if (!test.hasRole(user, "Administrator")) return;
        try {
            Connection c = MySQL_conn.getConnection();
            PreparedStatement prest = c.prepareStatement("delete from ingredients where Name = ?");
            prest.setString(1, ingredientName);
            prest.executeUpdate();
        } catch (SQLException e) {
            throw new MySQL_conn.DALException(e.getMessage());
        }
    }
}
