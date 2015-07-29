/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Oana
 */
public class UserDetails {

    private String mail;
    private String cnp;
    private String adress;

    public UserDetails() {
    }

    public UserDetails(String mail, String cnp, String adress) {
        this.mail = mail;
        this.cnp = cnp;
        this.adress = adress;
    }

    public String getName() {
        return mail;
    }

    public void setName(String mail) {
        this.mail = mail;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

}
