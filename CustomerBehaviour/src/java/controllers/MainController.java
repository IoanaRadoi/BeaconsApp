/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.RollbackFailureException;
import db.Areas;
import db.UserArea;
import db.UserDB;
import db.Users;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import model.User;


/**
 *
 * @author Ioana.Radoi
 */
public class MainController {

    private static MainController singleton;
    private Context ctx;
    private UserTransaction utx;
    private EntityManager em;

    private AreasJpaController areasJpaController;
    private UserAreaJpaController userZonaJpaController;
    private UsersJpaController usersJpaController;
    
    private UserDBJpaController userContoller; 


    public MainController() {
        try {

            ctx = new InitialContext();
            utx = (UserTransaction) ctx.lookup("java:comp/env/UserTransaction");
            em = (EntityManager) ctx.lookup("java:comp/env/persistence/LogicalName");

            areasJpaController = new AreasJpaController(utx, em.getEntityManagerFactory());
            userZonaJpaController = new UserAreaJpaController(utx, em.getEntityManagerFactory());
            usersJpaController = new UsersJpaController(utx, em.getEntityManagerFactory());
            userContoller = new UserDBJpaController(utx, em.getEntityManagerFactory());        

        } catch (NamingException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static MainController getInstance() {
        if (singleton == null) {
            singleton = new MainController();
        }
        return singleton;
    }

    public void addNewUser(String code) {

        try {
            usersJpaController.create(new Users(0, code));
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

    public Users getUserByCode(String code) {
        Users user = new Users();
        try {
            user = usersJpaController.getIdUserByCode(code);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public Areas getAreaByName(String name_area) {
        Areas area = new Areas();
        try {
            area = areasJpaController.getIdAreaByName(name_area);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return area;
    }

    public void addNewUserArea(String nume_user, String nume_zona, Float durata, Double timestamp) {
        Users user = new Users();
        Areas area = new Areas();

        user = getUserByCode(nume_user);
        area = getAreaByName(nume_zona);
        
        try {
            userZonaJpaController.create(new UserArea(0, user.getId(), area.getId(), durata, timestamp));
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public boolean inregistrare(String user, String parola) {
        UserDB u = userContoller.getUserByUser(user);
        if (u == null) {
            try {
                userContoller.create(new UserDB(0, user, parola, 1));
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        return false;
    }

    public User login(String user, String parola) {

        UserDB u = userContoller.getUserByUser(user);
        if (u != null) {
            if (u.getParola().equals(parola)) {
                return new User(u.getId(), u.getUser(), u.getParola());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }    
    
    public int getTotalTimeByZone(int id_zona) {   //TO add parameter zi_curenta
        int[] zone = new int[3];
        zone[0] = 1;
        zone[1] = 2;
        zone[2] = 3;
        return userZonaJpaController.getTimeByZone(id_zona);

    }  

}
