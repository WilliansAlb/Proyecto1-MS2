/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package generadorjava.genorariojava.resources;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import generadorjava.genorariojava.Curse;
import generadorjava.genorariojava.Weight;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

        // Parsear el JSON en una lista de objetos Curso
        Curse[] cursos = gson.fromJson(jsonBody.toString(), Curse[].class);

        // Acceder a los cursos y sus atributos, incluyendo los catedráticos
        for (Curse curso : cursos) {
            System.out.println("Curso: " + curso.getCurso());
            System.out.println("Código: " + curso.getCodigo());
            System.out.println("Abreviatura: " + curso.getAbreviatura());
            System.out.println("Área: " + curso.getArea());
            System.out.println("Semestre: " + curso.getSemestre());
            System.out.println("Asignados: " + curso.getAsignados());

            if (curso.getCatedraticos() != null) {// Acceder a los catedráticos como objetos JSON
                System.out.println("si hay");
                JsonArray catedraticos = curso.getCatedraticos();
                for (JsonElement catedraticoElement : catedraticos) {
                    JsonObject catedratico = catedraticoElement.getAsJsonObject();
                    String nombreCatedratico = catedratico.get("nombre").getAsString();
                    System.out.println("Catedrático: " + nombreCatedratico);
                }
            }
        }
        // Send a JSON response back to the client
        response.setStatus(HttpServletResponse.SC_OK);
// Process the JSON data or send a response
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("message", "Received JSON data successfully");
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
