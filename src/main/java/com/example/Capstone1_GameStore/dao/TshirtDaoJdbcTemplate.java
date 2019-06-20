package com.example.Capstone1_GameStore.dao;

import com.example.Capstone1_GameStore.models.Tshirt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TshirtDaoJdbcTemplate implements TshirtDao {

    JdbcTemplate tshirtJdbc;

    @Autowired
    public TshirtDaoJdbcTemplate(JdbcTemplate jdbcTemplate) {
        tshirtJdbc = jdbcTemplate;
    }

    public Tshirt mapRowToTshirt(ResultSet rs, int rowNum) throws SQLException {
        Tshirt tshirt = new Tshirt(
                rs.getString("size"),
                rs.getString("color"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getInt("quantity"));
        int id = rs.getInt("t_shirt_id");

        tshirt.setT_shirt_id(id);

        return tshirt;
    }

    private static final String ADD_TSHIRT = "insert into t_shirt (size, color, description, price, quantity) " +
            "values(?, ?, ?, ?, ?)";

    private static final String GET_TSHIRT_BY_ID = "select * from t_shirt where t_shirt_id = ?";

    private static final String UPDATE_SHIRT = "update t_shirt set size = ?, color = ?, description = ?, price = ?," +
            " quantity = ? where t_shirt_id = ?";

    private static final String DELETE_TSHIRT = "delete from t_shirt where t_shirt_id = ?";

    private static final String GET_ALL_TSHIRTS = "select * from t_shirt";

    private static final String GET_ALL_TSHIRTS_BY_COLOR = "select * from t_shirt where color like ?";

    private static final String GET_ALL_TSHIRTS_BY_SIZE = "select * from t_shirt where size like ?";

    @Override
    public Tshirt addTshirt(Tshirt tshirt) {
        tshirtJdbc.update(ADD_TSHIRT,
                tshirt.getSize(),
                tshirt.getColor(),
                tshirt.getDescription(),
                tshirt.getPrice(),
                tshirt.getQuantity());

        int id  = tshirtJdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);

        tshirt.setT_shirt_id(id);

        return tshirt;
    }

    @Override
    public Tshirt getTshirtById(int id) {

        try {
            return tshirtJdbc.queryForObject(GET_TSHIRT_BY_ID, this::mapRowToTshirt, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Tshirt updateTshirt(Tshirt tshirt) {
        int numRowUpdated = tshirtJdbc.update(UPDATE_SHIRT,
                tshirt.getSize(),
                tshirt.getColor(),
                tshirt.getDescription(),
                tshirt.getPrice(),
                tshirt.getQuantity(),
                tshirt.getT_shirt_id());

        return numRowUpdated == 1 ? tshirt : null;
    }

    @Override
    public boolean deleteTshirtById(int id) {
        int numRowDeleted = tshirtJdbc.update(DELETE_TSHIRT, id);
        return numRowDeleted == 1 ? true : false;
    }

    @Override
    public List<Tshirt> getAllTshirts() {
        return tshirtJdbc.query(GET_ALL_TSHIRTS, this::mapRowToTshirt);
    }

    @Override
    public List<Tshirt> getTshirtsByColor(String color) {
        color = color + "%";
        return tshirtJdbc.query(GET_ALL_TSHIRTS_BY_COLOR, this::mapRowToTshirt, color);
    }

    @Override
    public List<Tshirt> getTshirtsBysize(String size) {
        size = size + "%";
        return tshirtJdbc.query(GET_ALL_TSHIRTS_BY_SIZE, this::mapRowToTshirt, size);
    }
}
