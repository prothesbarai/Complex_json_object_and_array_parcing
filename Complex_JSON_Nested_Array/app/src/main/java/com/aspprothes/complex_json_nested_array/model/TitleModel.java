package com.aspprothes.complex_json_nested_array.model;
import java.util.ArrayList;

public class TitleModel {
    private String name;
    private String dept;
    private ArrayList<SubjectModel> subjects;

    public TitleModel(String name, String dept, ArrayList<SubjectModel> subjects) {
        this.name = name;
        this.dept = dept;
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public ArrayList<SubjectModel> getSubjects() {
        return subjects;
    }
}

