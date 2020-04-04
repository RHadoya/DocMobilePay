package com.example.docmobilepaydoctor.modele;

public class Person {
    private int id;
    private String name;
    private String firstName;
    private int isDoctor;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(int isDoctor) {
        this.isDoctor = isDoctor;
    }
}
