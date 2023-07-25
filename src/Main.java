import dao.TicketDao;

import java.sql.SQLException;

public class Main {
    public static final TicketDao ticketDao = TicketDao.getInstance();

    public static void main(String[] args) throws SQLException {

        String tableName = "ticket";

//        ticketDao.selectFromTable(tableName).forEach(System.out::println);

        ticketDao.selectFromTableById(5).forEach(System.out::println);
    }
}