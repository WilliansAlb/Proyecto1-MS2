/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package generadorjava.genorariojava.resources;

import DAO.AreaDAO;
import DAO.ConnectionDAO;
import DAO.CurseDAO;
import DAO.GenerateDAO;
import DAO.ParameterDAO;
import DAO.SalonDAO;
import DAO.TeacherDAO;
import DAO.WeightDAO;
import JSONObjects.CurseJSON;
import JSONObjects.DataItem;
import JSONObjects.SalonJSON;
import JSONObjects.TeacherJSON;
import Model.GeneratorModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import generadorjava.genorariojava.Curse;
import generadorjava.genorariojava.Weight;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author willi
 */
public class loader extends HttpServlet {

    private Gson gson = new Gson();

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
            out.println("<title>Servlet loader</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loader at " + request.getContextPath() + "</h1>");
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
        Weight weight = new Weight("2808", 50);
        String stringWeight = this.gson.toJson(weight);
        PrintWriter out = response.getWriter();
        ConnectionDAO connection = new ConnectionDAO();
        Connection cn = connection.getConnection();
        if (cn != null) {
            System.out.println("Conectado sin problemas");
        } else {
            System.out.println("No se conecto");
        }
        connection.closeConnection(cn);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(stringWeight);
        out.flush();
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

        ConnectionDAO con = new ConnectionDAO();
        // Create a StringBuilder to store the JSON data
        StringBuilder jsonBody = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<DataItem> dataItems = gson.fromJson(jsonBody.toString(), new TypeToken<List<DataItem>>() {
        }.getType());
        JsonObject responseJson = new JsonObject();
        for (DataItem item : dataItems) {
            if ("area".equals(item.getType())) {
                List<JsonObject> areas = item.getData();
                AreaDAO insertArea = new AreaDAO();
                Connection cn = con.getConnection();
                if (insertArea.insertArea(cn, areas.get(0).get("name").getAsString())) {
                    responseJson.addProperty("message", "OK");
                } else {
                    responseJson.addProperty("message", "NOK");
                }
                con.closeConnection(cn);
            } else if ("fileTeachers".equals(item.getType())) {
                List<JsonObject> teachers = item.getData();
                String message = "";
                Connection cn = con.getConnection();
                TeacherDAO tc = new TeacherDAO();
                for (JsonObject element : teachers) {
                    TeacherJSON teacher = gson.fromJson(element, TeacherJSON.class);
                    if (tc.insertTeacher(cn, teacher)) {
                        message += "\n " + teacher.getAlter() + "  ✔";
                    } else {
                        message += "\n " + teacher.getAlter() + "  X";
                    }
                }
                responseJson.addProperty("message", "OK " + message);
                con.closeConnection(cn);
            } else if ("fileCurses".equals(item.getType())) {
                List<JsonObject> teachers = item.getData();
                String message = "";
                Connection cn = con.getConnection();
                CurseDAO cd = new CurseDAO();
                for (JsonObject element : teachers) {
                    CurseJSON curse = gson.fromJson(element, CurseJSON.class);
                    if (cd.insertCurse(cn, curse)) {
                        if (curse.getCatedraticos() != null) {
                            for (int i = 0; i < curse.getCatedraticos().size(); i++) {
                                System.out.println("Estado " + cd.insertTeacherAssign(cn, curse.getCatedraticos().get(i).getAsJsonObject().get("id").getAsInt(), curse.getCodigo()));
                            }
                        }
                        message += "\n " + curse.getAbreviatura() + "  ✔";
                    } else {
                        message += "\n " + curse.getAbreviatura() + "  X";
                    }
                }
                responseJson.addProperty("message", "OK " + message);
                con.closeConnection(cn);
            } else if ("fileSalons".equals(item.getType())) {
                List<JsonObject> salons = item.getData();
                String message = "";
                Connection cn = con.getConnection();
                SalonDAO sd = new SalonDAO();
                for (JsonObject element : salons) {
                    SalonJSON salon = gson.fromJson(element, SalonJSON.class);
                    if (sd.insertSalon(cn, salon)) {
                        if (salon.getCursos() != null) {
                            for (int i = 0; i < salon.getCursos().size(); i++) {
                                System.out.println("Estado " + sd.insertSalonAssign(cn, salon.getId(), salon.getCursos().get(i).getAsJsonObject().get("codigo").getAsString()));
                            }
                        }
                        message += "\n " + salon.getNombre() + "  ✔";
                    } else {
                        message += "\n " + salon.getNombre() + "  X";
                    }
                }
                responseJson.addProperty("message", "OK " + message);
                con.closeConnection(cn);
            } else if ("parameter".equals(item.getType())) {
                List<JsonObject> salons = item.getData();
                String message = "";
                Connection cn = con.getConnection();
                ParameterDAO insertion = new ParameterDAO();
                int conteo = 1;
                for (JsonObject element : salons) {
                    if (insertion.updateFactor(cn, conteo, element.get("value").getAsInt())) {
                        message += " " + conteo + " ";
                    } else {
                        message += " X" + conteo + " ";
                    }
                    conteo++;
                }
                responseJson.addProperty("message", "OK " + message);
                con.closeConnection(cn);
            } else if ("generate".equals(item.getType())) {
                List<JsonObject> generate = item.getData();
                String message = "";
                Connection cn = con.getConnection();
                GenerateDAO gn = new GenerateDAO();
                GeneratorModel generator = new GeneratorModel();
                for (JsonObject element : generate) {
                    int id = gn.insertSchedule(cn, 0.00, element.get("name").getAsString());
                    if (id != -1) {
                        generator.generateWeights(id);
                        responseJson.addProperty("id", id);
                    } else {
                        message += " ERROR";
                    }
                }
                responseJson.addProperty("message", "OK " + message);
                con.closeConnection(cn);
            }
        }
        // Send a JSON response back to the client
        response.setStatus(HttpServletResponse.SC_OK);
        // Send a JSON response back to the client
        response.getWriter().write(responseJson.toString());
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
