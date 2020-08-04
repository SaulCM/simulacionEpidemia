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
            Class.forName("org.sqlite.JDBC");
            cn=DriverManager.getConnection("jdbc:sqlite::resource:dba/epidemiologia.db");
        }catch(Exception e1){
            System.out.println("Error: " + e1.getMessage());
        }
    return  cn;
    
    }
}