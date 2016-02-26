import java.sql.Connection;
import java.util.Map;

/**
 * Created by sachin on 2/24/2016.
 */
public class Check4 {
    Connection connection;
    public boolean connection(){
        while (true){
            final int key = DbConnector.getConnectionInstance();
            if(key!=0) {
                connection = DbConnector.getConnection(key);
                System.out.println("Check 4 Connected");
                Runnable r = new Runnable() {
                    public void run() {
                        System.out.println("Check 4 Inserting");
                        insert();
                        DbConnector.connectionPool.put(key, true);
                        System.out.println("Check 4 Completed");
                        for (Map.Entry<Integer, Boolean> entry : DbConnector.connectionPool.entrySet())
                        {
                            System.out.println("Check 4 -> Key is :"+entry.getKey() + "Value is :" + entry.getValue());
                        }
                    }
                };
                new Thread(r, "Check 4").start();
                return true;
            }
            try {
                System.out.println("Waiting for Connection Check 4");
                Thread.sleep(5000);
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
