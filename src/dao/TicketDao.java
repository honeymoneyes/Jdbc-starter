package dao;

import model.Ticket;
import util.ConnectionManager;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class TicketDao {

    public static final TicketDao INSTANCE = new TicketDao();
    public static final String SELECT_FROM_TICKET = "SELECT * FROM ticket";
    public static final String SELECT_FROM_TICKET_BY_ID = "SELECT * FROM ticket WHERE id=?";

    private TicketDao() {
    }

    public List<Ticket> selectFromTable(String tableName) throws SQLException {

        List<Ticket> ticketList = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_TICKET);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ticketList.add(getTicket(resultSet));
            }
            System.out.println(connection.getTransactionIsolation());
        }
        return ticketList;
    }

    public List<Ticket> selectFromTableById(int id) throws SQLException {

        List<Ticket> ticketList = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_TICKET_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ticketList.add(getTicket(resultSet));
            }
        }
        return ticketList;
    }

    private Ticket getTicket(ResultSet resultSet) {
        try {
            return new Ticket(
                    resultSet.getObject("id", BigInteger.class),
                    resultSet.getObject("passenger_no", String.class),
                    resultSet.getObject("passenger_name", String.class),
                    resultSet.getObject("flight_id", BigInteger.class),
                    resultSet.getObject("seat_no", String.class),
                    resultSet.getObject("cost", BigDecimal.class)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }
}
