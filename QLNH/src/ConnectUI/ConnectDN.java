
package ConnectUI;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConnectDN {
    public static void getConnection(){
         try{
           Class.forName("oracle.jdbc.OracleDriver");// propeties -> driver class
           String url = "jdbc:oracle:thin:@//localhost:1521/orcl21";
           String uname = "System";
           String upass = "25102004";
           Connection conn = DriverManager.getConnection(url, uname, upass);
           String sql = "Select * From DANGNHAP";
           PreparedStatement pst = conn.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
           if(rs.next()){
               System.out.println("Name"+"\t"+"Email");
               System.out.println(rs.getString(1)+"\t"+rs.getString(2));
           }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public static void main(String[] args) {
      getConnection();
    } 
}
