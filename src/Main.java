import dao.TicketDao;

import java.sql.SQLException;

public class Main {
    public static final TicketDao ticketDao = TicketDao.getInstance();

    public static void main(String[] args) throws SQLException {

        String tableName = "ticket";

//        ticketDao.selectAllTable(tableName).forEach(System.out::println);
        System.out.println(ticketDao.selectTicketById(5, tableName));
    }
}