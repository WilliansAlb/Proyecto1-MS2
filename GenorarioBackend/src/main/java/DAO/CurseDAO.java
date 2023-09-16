/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import JSONObjects.CurseJSON;
import JSONObjects.SalonJSON;
import JSONObjects.TeacherJSON;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
public class CurseDAO {

    public boolean insertCurse(Connection cn, CurseJSON curse) {
        try {
            String sql = "INSERT INTO Curse (code_curse, name_curse, semester_curse, assigned_students, area_curse, abbr_curse) VALUES (?,?,?,?,?,?)";
            try ( // Crea una PreparedStatement para ejecutar la sentencia SQL
                    PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
                preparedStatement.setString(1, curse.getCodigo());
                preparedStatement.setString(2, curse.getCurso());
                preparedStatement.setInt(3, curse.getSemestre());
                preparedStatement.setInt(4, curse.getAsignados());
                preparedStatement.setInt(5, curse.getArea());
                preparedStatement.setString(6, curse.getAbreviatura());
                // Ejecuta la inserción
                int filasAfectadas = preparedStatement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Inserción exitosa.");
                    return true;
                } else {
                    System.out.println("La inserción no se realizó correctamente.");
                    return false;
                }
                // Cierra la conexión y la sentencia preparada
            }
        } catch (SQLException ex) {
            Logger.getLogger(CurseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean insertTeacherAssign(Connection cn, int id_teacher, String code_curse) {
        try {
            String sql = "INSERT INTO TeacherAssigned (teacher_assigned, curse_assigned) VALUES (?,?)";
            try ( // Crea una PreparedStatement para ejecutar la sentencia SQL
                    PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
                preparedStatement.setInt(1, id_teacher);
                preparedStatement.setString(2, code_curse);
                // Ejecuta la inserción
                int filasAfectadas = preparedStatement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Inserción exitosa.");
                    return true;
                } else {
                    System.out.println("La inserción no se realizó correctamente.");
                    return false;
                }
                // Cierra la conexión y la sentencia preparada
            }
        } catch (SQLException ex) {
            Logger.getLogger(CurseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<CurseJSON> getCursesNameCode(Connection cn) {
        ArrayList<CurseJSON> curses = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT code_curse, name_curse FROM curse");
            while (resultSet.next()) {
                curses.add(new CurseJSON(resultSet.getString(1), resultSet.getString(2), "", 0, 0, 0, null));
            }
            return curses;
        } catch (SQLException ex) {
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return curses;
    }
}
