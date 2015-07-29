/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import controllers.MainController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.SendModel;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Ioana.Radoi
 */
@WebServlet(name = "Incoming", urlPatterns = {"/Incoming"})
public class Incoming extends HttpServlet {

    private Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String body = IOUtils.toString(request.getInputStream());
        SendModel modelPrimit = gson.fromJson(body, SendModel.class);

      
        if (MainController.getInstance().getUserByCode(modelPrimit.getUser_code()) == null){ //daca nu exista user-ul deja, il adaug in baza
            MainController.getInstance().addNewUser(modelPrimit.getUser_code());
        }          
        
        Double timestamp = (double)System.currentTimeMillis();
        MainController.getInstance().addNewUserArea(modelPrimit.getUser_code(), modelPrimit.getZona_name(), modelPrimit.getDurata(), timestamp);

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {          
            out.append(gson.toJson("Ok"));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
