package dba;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class LoadDriver {
    public static Connection getConnection(){
         Connection cn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn=DriverManager.getConnection("jdbc:mysql://localhost/epidemiologia","root","");
        }catch(Exception e1){
            System.out.println("Error: " + e1.getMessage());
        }
    return  cn;
    
    }
}