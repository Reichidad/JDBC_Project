package DB;
import java.sql.*;

public class DBConnect {
    //Initial Settings
    private Connection DBCon = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    public boolean connectDB() {
        //Strings for DB Connection
        String DBName = "dbsys";
        String DBUserName = "root";
        String DBPassword = "dudgus1590";
        System.out.println("DBCONNECTION TRY");
        try {
            //DB Connection
            DBCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBName + "?useUnicode=true&"
                    + "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", DBUserName, DBPassword);
            System.out.println("SUCCESS");
            return true;
        } catch (SQLException ex) {
            System.out.println("DBCONNECTION FAILED WITH: " + ex);
        }
        return false;
    }
    public boolean searchStudentID(int id) {
        try {
            stmt = DBCon.createStatement();
            pstmt = DBCon.prepareStatement(
                    "select count(id) from dbsys.student where student.id = (?)");
            pstmt.setInt(1, id);
            ResultSet count = pstmt.executeQuery();
            count.next();
            int idExist = count.getInt("count(id)");

            return (idExist == 1);
        } catch (SQLException ex) {
            return false;
        }
    }
}
