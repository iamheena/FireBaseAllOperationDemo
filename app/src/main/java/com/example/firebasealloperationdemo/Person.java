package com.example.firebasealloperationdemo;

public class Person {
    String name,course;
    String rollno;
    public Person(String name, String course, String rollno) {
        this.name = name;
        this.course = course;
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public Person() {

    }
}
