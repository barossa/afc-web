package by.epam.afc.pool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConnectionPoolTest {

    @Test
    public void connectionPoolTest(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Assertions.assertNotNull(connectionPool);
    }
}
