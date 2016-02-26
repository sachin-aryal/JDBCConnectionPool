import java.sql.Connection;
import java.util.Map;

class Check3 {
    Connection connection=null;
    public boolean connection(){
        while(true) {
            final int key = DbConnector.getConnectionInstance();
            if (key != 0) {
                connection = DbConnector.getConnection(key);
                System.out.println("Check 3 Connected");
                Runnable r = new Runnable() {
                    public void run() {
                        System.out.println("Check 3 Inserting Data");
                        insert();
                        DbConnector.connectionPool.put(key, true);
                        System.out.println("Check 3 Completed");
                        for (Map.Entry<Integer, Boolean> entry : DbConnector.connectionPool.entrySet())
                        {
                            System.out.println("Check 3 ->Key is :"+entry.getKey() + "Value is :" + entry.getValue());
                        }
                    }
                };
                new Thread(r, "Check 3").start();
                return true;
            }
            try {
                System.out.println("Waiting for Connection Check 3");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void insert(){
        Check1 c1 = new Check1();
        c1.insert(connection);
    }
}