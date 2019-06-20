package com.example.Capstone1_GameStore.dao;

import com.example.Capstone1_GameStore.models.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConsoleDaoJdbcTemplate implements ConsoleDao {
    JdbcTemplate consoleJdbc;

    public Console mapRowToConsole(ResultSet rs, int rowNum) throws SQLException {
        Console console = new Console(
                rs.getString("model"),
                rs.getString("manufacturer"),
                rs.getString("memory_amount"),
                rs.getString("processor"),
                rs.getBigDecimal("price"),
                rs.getInt("quantity")
        );

        console.setConsole_id(rs.getInt("console_id"));

        return console;
    }

    private static final String ADD_CONSOLE = "insert into console (model, manufacturer, memory_amount, processor," +
            " price, quantity) values (?, ?, ?, ?, ?, ?)";

    private static final String GET_CONSOLE_BY_ID = "select * from console where console_id = ?";

    private static final String DELETE_CONSOLE_BY_ID = "delete from console where console_id = ?";

    private static final String GET_ALL_CONSOLES = "select * from console";

    private static final String UPDATE_CONSOLE = "update console set model = ?, manufacturer = ?, memory_amount = ?," +
            "processor = ?, price = ?, quantity = ? where console_id = ?";

    private static final String GET_ALL_CONSOLES_BY_MANUFACTURER = "select * from console where manufacturer like ?";

    @Autowired
    public ConsoleDaoJdbcTemplate(JdbcTemplate consoleJdbc) {
        this.consoleJdbc = consoleJdbc;
    }

    @Override
    @Transactional
    public Console addConsole(Console console) {
        consoleJdbc.update(ADD_CONSOLE,
                console.getModel(),
                console.getManufacturer(),
                console.getMemory_amount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity());

        int id = consoleJdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        console.setConsole_id(id);

        return console;
    }

    @Override
    public Console getConsoleById(int id) {
        return consoleJdbc.queryForObject(GET_CONSOLE_BY_ID, this::mapRowToConsole, id);
    }

    @Override
    @Transactional
    public Console updateConsole(Console console) {
        int rowsUpdated = consoleJdbc.update(UPDATE_CONSOLE,
                console.getModel(),
                console.getManufacturer(),
                console.getMemory_amount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity(),
                console.getConsole_id());

        return rowsUpdated == 1 ? console : null;
    }

    @Override
    public boolean deleteConsoleById(int id) {
        boolean consoleDeleted = false;
        if (consoleJdbc.update(DELETE_CONSOLE_BY_ID, id) == 1) {
            consoleDeleted = true;
        }
        return consoleDeleted;
    }

    @Override
    public List<Console> getAllConsoles() {
        return consoleJdbc.query(GET_ALL_CONSOLES, this::mapRowToConsole);
    }

    @Override
    public List<Console> getAllConsolesByManufacturere(String manufacturer) {
        manufacturer = manufacturer + "%";
        return consoleJdbc.query(GET_ALL_CONSOLES_BY_MANUFACTURER, this::mapRowToConsole, manufacturer);
    }
}
