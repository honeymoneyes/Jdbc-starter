import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRunner {
    //language=PostgreSQL

    public static final String SQL_DELETE_FLIGHTS = "DELETE FROM flight WHERE id=?";
    public static final String SQL_DELETE_TICKETS = "DELETE FROM ticket WHERE flight_id=?";

    public static void main(String[] args) throws SQLException {
        int flight_id = 8;
        deleteById(flight_id);
    }

    private static void deleteById(int flightId) throws SQLException {
        Connection connection = null;
        PreparedStatement deleteFlightStatement = null;
        PreparedStatement deleteTicketStatement = null;
        try {
            connection = ConnectionManager.open();
            deleteFlightStatement = connection.prepareStatement(SQL_DELETE_FLIGHTS);
            deleteTicketStatement = connection.prepareStatement(SQL_DELETE_TICKETS);

            connection.setAutoCommit(false);

            deleteFlightStatement.setInt(1, flightId);
            deleteTicketStatement.setInt(1, flightId);

            deleteTicketStatement.executeUpdate();
            if (true) {
                throw new RuntimeException("Oooops");
            }
            deleteFlightStatement.executeUpdate();

            connection.commit();
        } catch (Exception o) {
            if (connection != null) {
                connection.rollback();
            }
            throw o;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteFlightStatement != null) {
                deleteFlightStatement.close();
            }
            if (deleteTicketStatement != null) {
                deleteTicketStatement.close();
            }
        }
    }
}
