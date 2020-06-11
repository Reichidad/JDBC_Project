package DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import Controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnect {
    //Initial Settings
    private Connection DBCon = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;

    private ObservableList<TakesProp> takesProps;
    private ObservableList<SemesterProp> semesterProps;

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
    // Transaction 01 : search student id
    public boolean searchStudentID(int id) {
        try {
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
    // Transaction 02 : get average grade by student id
    public Double getAvg(int id) {
        try {
            pstmt = DBCon.prepareStatement(
                    "select avg(grade) from takes where id = (?)");
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            result.next();
            return result.getDouble("avg(grade)");
        } catch (SQLException ex) {
            return 0.0;
        }
    }
    // Transaction 03 : get sum of credits by student id
    public int getSum(int id) {
        try {
            pstmt = DBCon.prepareStatement(
                    "select sum(credits) from takes natural join course where id = (?)");
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            result.next();
            return result.getInt("sum(credits)");
        } catch (SQLException ex) {
            return 0;
        }
    }
    // Transaction 04 : make data for student page(all course the student taken)
    public ObservableList<TakesProp> createTakesData(int id) {
        takesProps = FXCollections.observableArrayList();
        // sql for store data
        try {
            pstmt = DBCon.prepareStatement(
                    "select * from takes natural join course where id = (?)");
            pstmt.setInt(1, id);
            ResultSet tempData = pstmt.executeQuery();
            while(tempData.next()) {
                String title = tempData.getString("title");
                String deptName = tempData.getString("dept_name");
                String category = tempData.getString("category");
                int credits = tempData.getInt("credits");
                int semester = tempData.getInt("semester");
                int year = tempData.getInt("year");
                double grade = tempData.getDouble("grade");
                takesProps.add(new TakesProp(title, deptName, category, credits, semester, year, grade));
            }
            return takesProps;
        } catch (SQLException ex) {
            System.out.println("SQLException with : " + ex);
            return null;
        }
    }
    // Transaction 05 : get sum of credits group by category
    public int[] getMySums(int id) {
        int[] results = {0, 0, 0};
        try {
            pstmt = DBCon.prepareStatement(
                    "select category, sum(credits) from takes natural join course where id = (?) group by category");
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            for (int i=0;i<3;i++) {
                result.next();
                String nowCategory = result.getString("category");
                if(nowCategory.length() <=12 && nowCategory.length() > 0) {
                    if (nowCategory.substring(0, 8).equals("Advanced"))
                        results[0] = result.getInt("sum(credits)");
                    else if (nowCategory.substring(0, 8).equals("Elective"))
                        results[1] = result.getInt("sum(credits)");
                }
                else {
                    if (nowCategory.substring(0, 12).equals("Prerequisite"))
                        results[2] = result.getInt("sum(credits)");
                }
            }
            return results;
        } catch (SQLException ex) {
            return results;
        }
    }
    // Transaction 06 : get graduate condition credits
    public int[] getConditions(int id) {
        int[] results = {0, 0, 0};
        try {
            pstmt = DBCon.prepareStatement(
                    "select dept_name from student where id = (?)");
            pstmt.setInt(1, id);
            ResultSet temp = pstmt.executeQuery();
            temp.next();
            String deptName = temp.getString("dept_name");
            pstmt = DBCon.prepareStatement(
                    "select category, condition_credits " +
                    "from graduate_condition where dept_name = (?)");
            pstmt.setString(1, deptName);
            ResultSet result = pstmt.executeQuery();
            for (int i=0;i<3;i++) {
                result.next();
                String nowCategory = result.getString("category");
                if (nowCategory.equals("Advanced"))
                    results[0] = result.getInt("condition_credits");
                if (nowCategory.equals("Elective"))
                    results[1] = result.getInt("condition_credits");
                if (nowCategory.equals("Prerequisite"))
                    results[2] = result.getInt("condition_credits");
            }
            return results;
        } catch (SQLException ex) {
            return results;
        }
    }
    // Transaction 07 : get semesters' stats
    public ObservableList<SemesterProp> createSemesterData(int id) {
        semesterProps = FXCollections.observableArrayList();
        try {
            pstmt = DBCon.prepareStatement(
                    "select year, semester, sum(credits), avg(grade) " +
                            "from takes natural join course " +
                            "where id = (?) " +
                            "group by year, semester");
            pstmt.setInt(1, id);
            ResultSet tempData = pstmt.executeQuery();
            while(tempData.next()) {
                int year = tempData.getInt("year");
                int semester = tempData.getInt("semester");
                int credits = tempData.getInt("sum(credits)");
                double gradeAvg = tempData.getDouble("avg(grade)");
                semesterProps.add(new SemesterProp(year, semester, credits, gradeAvg));
            }
            return semesterProps;
        } catch (SQLException ex) {
            System.out.println("SQLException with : " + ex);
            return null;
        }
    }
}
