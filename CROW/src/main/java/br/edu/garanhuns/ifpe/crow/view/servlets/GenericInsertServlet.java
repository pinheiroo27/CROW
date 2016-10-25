/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.garanhuns.ifpe.crow.view.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 1860915
 */

@WebServlet(name = "GenericInsertServlet", urlPatterns = {"/GenericInsertServlet"})
public class GenericInsertServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        Class usedBean = (Class)session.getAttribute("usedBean");
        Class usedController = (Class)session.getAttribute("usedController");
        
        Object objectBean = null;
        Object objectController = null;
        
        try {
            objectBean = usedBean.newInstance();
            objectController = usedController.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        
        String parametros[] = request.getParameter("param").split(";");
        
        Method[] methods = objectBean.getClass().getMethods();
        
        for(String p:parametros){
            for(Method m:methods){
                
                if(m.getName().toLowerCase().contains("set")){
                    if(m.getName().toLowerCase().contains(p.split(":")[0])){
                        if(m.getParameterTypes()[0].getTypeName().contains("int")){
                            try {
                                m.invoke(objectBean, Integer.parseInt(p.split(":")[1]));
                            } catch (IllegalAccessException ex) {
                                ex.printStackTrace();
                            } catch (IllegalArgumentException ex) {
                                ex.printStackTrace();    
                            } catch (InvocationTargetException ex) {
                                ex.printStackTrace();
                            }
                        }else{
                            try {
                                m.invoke(objectBean, p.split(":")[1]);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(GenericInsertServlet.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(GenericInsertServlet.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(GenericInsertServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                
            }
        }
        
        Method controllerMethod;
        try {
            controllerMethod = objectController.getClass().getDeclaredMethod("create",objectBean.getClass());
            
            controllerMethod.invoke(objectController, objectBean);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("Cadastrado com Sucesso");
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
