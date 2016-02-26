import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

class Check1 {
    private Connection connection;
    public boolean connection(){
        while (true) {
            final int key = DbConnector.getConnectionInstance();
            if (key != 0) {
                connection = DbConnector.getConnection(key);
                System.out.println("Check 1 Connected");
                Runnable r = new Runnable() {
                    public void run() {
                        System.out.println("Check 1 Inserting");
                        insert(connection);
                        DbConnector.connectionPool.put(key, true);
                        System.out.println("Check 1 Completed");
                        for (Map.Entry<Integer, Boolean> entry : DbConnector.connectionPool.entrySet())
                        {
                            System.out.println(" Check 1 ->Key is :"+entry.getKey() + "Value is :" + entry.getValue());
                        }
                    }
                };
                new Thread(r, "Check 1").start();
                return true;
            }
            try {
                System.out.println("Waiting for Connection Check 1");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void insert(Connection connection){
        int n=100;
        for(int i=0;i<n;i++){
            String sql = "INSERT INTO Tester(Val) VALUES('"+i+"')";
            try {
                Statement statement = connection.createStatement();
                statement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}