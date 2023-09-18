/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBObject.AssignedCurseDBO;
import DBObject.CurseForWeight;
import JSONObjects.CurseJSON;
import JSONObjects.SalonJSON;
import JSONObjects.TeacherJSON;
import Model.AssignModel;
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
            Logger.getLogger(CurseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return curses;
    }
    
    public ArrayList<Integer> getCurseTeacherAssign(Connection cn, String code_curse) {
        ArrayList<Integer> teachers_id = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT teacher_assigned FROM TeacherAssigned WHERE curse_assigned = "+code_curse);
            while (resultSet.next()) {
                teachers_id.add(resultSet.getInt(1));
            }
            return teachers_id;
        } catch (SQLException ex) {
            Logger.getLogger(CurseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers_id;
    }
    
    public boolean insertAssignedCurse(Connection cn, AssignModel an) {
        try {
            String sql = "INSERT INTO AssignedCurse (period_assign, curse_assign, salon_assign, teacher_assign,"
                    + "schedule_assign, status, message) VALUES (?,?,?,?,?,?,?)";
            try ( // Crea una PreparedStatement para ejecutar la sentencia SQL
                    PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
                preparedStatement.setInt(1, an.getPeriod().getId_period());
                preparedStatement.setString(2, an.getCurse().getCode_curse());
                preparedStatement.setInt(3, an.getSalon());
                preparedStatement.setInt(4, an.getTeacher().getId_teacher());
                preparedStatement.setInt(5, an.getSchedule());
                preparedStatement.setInt(6, an.getStatus());
                preparedStatement.setString(7, an.getMessage());
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
            Logger.getLogger(WeightDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<Integer> getCurseSalonAssign(Connection cn, String code_curse) {
        ArrayList<Integer> salons_id = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT salon_assigned FROM SalonAssigned WHERE curse_assigned = " + code_curse);
            while (resultSet.next()) {
                salons_id.add(resultSet.getInt(1));
            }
            return salons_id;
        } catch (SQLException ex) {
            Logger.getLogger(CurseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salons_id;
    }
    
    public ArrayList<CurseForWeight> getDataCurses(Connection cn){
        ArrayList<CurseForWeight> curses = new ArrayList<>();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT c.*, "
                    + "(SELECT COUNT(*) FROM teacherassigned ta WHERE ta.curse_assigned = c.code_curse) AS count_teachers, "
                    + "(SELECT COUNT(*) FROM salonassigned ta WHERE ta.curse_assigned = c.code_curse) AS count_salons " 
                    + "FROM Curse c;");
            while (rs.next()){
                curses.add(new CurseForWeight(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getInt(7),rs.getInt(8)));
            }
            return curses;
        } catch (SQLException ex){
            Logger.getLogger(CurseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return curses;
    }
    
    public ArrayList<AssignedCurseDBO> getAssignedCurses(Connection cn, int id_schedule){
        ArrayList<AssignedCurseDBO> curses = new ArrayList<>();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM AssignedCurse WHERE schedule_assign = "+id_schedule);
            while (rs.next()){
                curses.add(new AssignedCurseDBO(rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getString(8)));
            }
            return curses;
        } catch (SQLException ex){
            Logger.getLogger(CurseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return curses;
    }
}
