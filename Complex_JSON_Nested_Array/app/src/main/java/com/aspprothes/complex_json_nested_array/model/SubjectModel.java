package com.aspprothes.complex_json_nested_array.model;

public class SubjectModel {
    private String semester;
    private String subject;

    public SubjectModel(String semester, String subject) {
        this.semester = semester;
        this.subject = subject;
    }

    public String getSemester() {
        return semester;
    }

    public String getSubject() {
        return subject;
    }
}
