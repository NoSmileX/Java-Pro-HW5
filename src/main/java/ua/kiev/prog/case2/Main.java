package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS Clients");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            Client c = new Client("test", 1);
            dao.add(c);

            int id = c.getId();
            System.out.println(id);

            List<Client> listWithoutParams = dao.getAll(Client.class);
            for (Client cli : listWithoutParams)
                System.out.println(cli);

            listWithoutParams.get(0).setAge(55);
            dao.update(listWithoutParams.get(0));


            List<Client> listWithTwoParams = dao.getAll(Client.class, "name", "age");
            List<Client> listWithOneParam = dao.getAll(Client.class, "age");
            for (Client cli : listWithTwoParams)
                System.out.println(cli);

            for (Client cli : listWithOneParam)
                System.out.println(cli);


            dao.delete(listWithoutParams.get(0));
        }
    }
}
