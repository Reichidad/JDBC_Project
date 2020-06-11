package Controller;

import javafx.beans.property.*;

public class SemesterProp {
    private IntegerProperty year, semester, credits;
    private DoubleProperty gradeAvg;

    public SemesterProp(int y, int s, int c, double g) {
        this.year = new SimpleIntegerProperty(y);
        this.semester = new SimpleIntegerProperty(s);
        this.credits = new SimpleIntegerProperty(c);
        this.gradeAvg = new SimpleDoubleProperty(g);
    }
    public IntegerProperty getYear() {
        return year;
    }
    public IntegerProperty getSemester() {
        return semester;
    }
    public IntegerProperty getCredits() {
        return credits;
    }
    public DoubleProperty getGradeAvg() {
        return gradeAvg;
    }
}
