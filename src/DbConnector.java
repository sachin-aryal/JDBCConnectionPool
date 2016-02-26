import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sachin on 8/13/15.
 */
public class DbConnector {
    public static Connection connection;
    //    private Statement statement;
//    private ResultSet resultSet;
    private static final String driver="com.mysql.jdbc.Driver";
    private static final String dbName="ConnectionPool";
    private static final String userName="root";
    private static final String password = "root";
    private static final String  _host="jdbc:mysql://localhost:3306/"+dbName;
    public static Map<Integer,Boolean> connectionPool = new HashMap<Integer,Boolean>();
    public static Map<Integer,Connection> returnConnection = new HashMap<>();

    public static int noOfConnection=3;


    public static int getConnectionInstance(){
        if(noOfConnection>0){
            System.out.println("Number of Connection :"+noOfConnection);
            try {
                System.out.println("Accessing from first");
                Class.forName(driver);
                connection = DriverManager.getConnection(_host, userName, password);
                connectionPool.put(noOfConnection,false);
                returnConnection.put(noOfConnection,connection);
                noOfConnection--;
                return (noOfConnection+1);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else{
            Set<Integer> keys = connectionPool.keySet();
            for(int key:keys){
                if(connectionPool.get(key)){
                    System.out.println("Accessing from Second");
                    connectionPool.put(key,false);
                    return key;
                }
            }
        }
        return 0;
    }

    public static Connection getConnection(int position){
        return returnConnection.get(position);
    }


}
