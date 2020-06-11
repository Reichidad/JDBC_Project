package Controller;

import javafx.beans.property.*;

public class TakesProp {
    private StringProperty title, deptName, category;
    private IntegerProperty credits, semester, year;
    private DoubleProperty grade;

    public TakesProp(String t, String d, String c, int cr, int s, int y, double g) {
        this.title = new SimpleStringProperty(t);
        this.deptName = new SimpleStringProperty(d);
        this.category = new SimpleStringProperty(c);
        this.credits = new SimpleIntegerProperty(cr);
        this.semester = new SimpleIntegerProperty(s);
        this.year = new SimpleIntegerProperty(y);
        this.grade = new SimpleDoubleProperty(g);
    }
    public StringProperty getTitle() {
        return title;
    }
    public StringProperty getDeptName() {
        return deptName;
    }
    public StringProperty getCategory() {
        return category;
    }
    public IntegerProperty getCredits() {
        return credits;
    }
    public IntegerProperty getSemester() {
        return semester;
    }
    public IntegerProperty getYear() {
        return year;
    }
    public DoubleProperty getGrade() {
        return grade;
    }
}
