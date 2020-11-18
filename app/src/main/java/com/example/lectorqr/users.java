package com.example.lectorqr;

import java.util.Objects;

public class users {

    private String userId;
    private String name;
    private String lastName;
    private String rol;
    private int semestre;

    public users(String userId, String name, String lastName, String rol, int semestre) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.rol = rol;
        this.semestre = semestre;
    }

    public users() {
    }

    public users(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        return "users{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rol='" + rol + '\'' +
                ", semestre=" + semestre +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        users users = (users) o;
        return userId.equals(users.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
