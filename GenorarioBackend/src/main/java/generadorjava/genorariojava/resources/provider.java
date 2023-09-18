/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package generadorjava.genorariojava.resources;

import DAO.ConnectionDAO;
import DAO.CurseDAO;
import DAO.ParameterDAO;
import DAO.SalonDAO;
import DAO.ScheduleDAO;
import DBObject.Parameter;
import DBObject.ScheduleDBO;
import JSONObjects.CurseJSON;
import JSONObjects.SalonJSON;
import Model.AssignModel;
import Model.GeneratorModel;
import Model.SalonModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author willi
 */
public class provider extends HttpServlet {

    Gson gson = new Gson();

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet provider</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet provider at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        response.setCharacterEncoding("UTF-16");
        ConnectionDAO con = new ConnectionDAO();

        JsonObject responseJson = new JsonObject();
        String type = request.getParameter("type");
        if (type.equals("parameter")) {
            Connection cn = con.getConnection();
            ParameterDAO parameters = new ParameterDAO();
            CurseDAO curses = new CurseDAO();
            responseJson.add("parameters", gson.toJsonTree(parameters.getParameters(cn), new TypeToken<ArrayList<Parameter>>() {
            }.getType()));
            responseJson.add("curses", gson.toJsonTree(curses.getCursesNameCode(cn), new TypeToken<ArrayList<CurseJSON>>() {
            }.getType()));
            con.closeConnection(cn);
        } else if (type.equals("schedule")) {
            GeneratorModel g = new GeneratorModel();
            ParameterDAO parameters = new ParameterDAO();
            if (request.getParameter("id") == null) {
                responseJson.add("schedule", gson.toJsonTree(g.generateWeights(0), new TypeToken<ArrayList<SalonModel>>() {
                }.getType()));
                Connection cn = con.getConnection();
                responseJson.add("parameters", gson.toJsonTree(parameters.getParameters(cn), new TypeToken<ArrayList<Parameter>>() {
                }.getType()));
                con.closeConnection(cn);
            } else {
                int schedule = Integer.parseInt(request.getParameter("id"));
                Connection cn = con.getConnection();
                ScheduleDAO sch = new ScheduleDAO();
                responseJson.add("schedule", gson.toJsonTree(g.generatedSchedule(schedule), new TypeToken<ArrayList<SalonModel>>() {
                }.getType()));
                responseJson.add("dataSchedule", gson.toJsonTree(sch.getSchedule(cn, schedule), new TypeToken<ScheduleDBO>() {
                }.getType()));
                responseJson.add("parameters", gson.toJsonTree(parameters.getParametersUsed(cn,schedule), new TypeToken<ArrayList<Parameter>>() {
                }.getType()));
                con.closeConnection(cn);
            }
            responseJson.addProperty("efficienty", g.efficienty);
        } else if (type.equals("schedules")) {
            Connection cn = con.getConnection();
            ScheduleDAO sch = new ScheduleDAO();
            responseJson.add("schedules", gson.toJsonTree(sch.getSchedules(cn), new TypeToken<ArrayList<ScheduleDBO>>() {
            }.getType()));
        }

        // Send a JSON response back to the client
        response.getWriter().write(responseJson.toString());
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
