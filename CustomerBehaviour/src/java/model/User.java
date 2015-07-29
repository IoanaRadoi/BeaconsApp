/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author Oana
 */
public class User implements java.io.Serializable {

    private int id;
    private String user;
    private String parola;
    private boolean conectat;
    private UserDetails ud;

    public User() {
    }

    public User(String user, String parola) {
        this.user = user;
        this.parola = parola;
    }

    public User(int id, String user, String parola) {
        this.id = id;
        this.user = user;
        this.parola = parola;
    }

    public User(UserDetails ud) {
        this.ud = ud;
    }

    public UserDetails getUd() {
        return ud;
    }

    public void setUd(UserDetails ud) {
        this.ud = ud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public boolean isConectat() {
        return conectat;
    }

    public void setConectat(boolean conectat) {
        this.conectat = conectat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.user);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

}
