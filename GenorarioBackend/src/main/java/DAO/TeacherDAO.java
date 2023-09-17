/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBObject.Parameter;
import DBObject.TotalAreaTeacher;
import JSONObjects.TeacherJSON;
import Model.PeriodModel;
import Model.TeacherModel;
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
public class TeacherDAO {
    
    
    public boolean insertTeacher(Connection cn, TeacherJSON teacher) {
        try {
            String sql = "INSERT INTO Teacher (id_teacher, name_teacher, start_hour, end_hour, area_teacher, alter_teacher) VALUES (?,?,?,?,?,?)";
            try ( // Crea una PreparedStatement para ejecutar la sentencia SQL
                    PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
                preparedStatement.setInt(1, teacher.getId());
                preparedStatement.setString(2, teacher.getNombre());
                preparedStatement.setInt(3, teacher.getEntrada());
                preparedStatement.setInt(4, teacher.getSalida());
                preparedStatement.setInt(5, teacher.getArea());
                preparedStatement.setString(6, teacher.getAlter());
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
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<TotalAreaTeacher> getTotalTeachers(Connection cn){
        ArrayList<TotalAreaTeacher> total = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  (SELECT COUNT(*) FROM teacher ta WHERE ta.area_teacher = a.id_area)  AS total, a.id_area AS area_teacher FROM area a LEFT JOIN teacher t ON a.id_area = t.area_teacher GROUP BY a.id_area;");
            while (resultSet.next()) {
                total.add(new TotalAreaTeacher(resultSet.getInt(1), resultSet.getInt(2)));
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    public ArrayList<TeacherModel> getTeacherListModel(Connection cn) {
        ArrayList<TeacherModel> teachers = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_teacher, name_teacher, start_hour, end_hour, area_teacher, alter_teacher FROM teacher");
            while (resultSet.next()) {
                teachers.add(new TeacherModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
                resultSet.getInt(4),resultSet.getInt(5),resultSet.getString(6)));
            }
            return teachers;
        } catch (SQLException ex) {
            Logger.getLogger(SalonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers;
    }
}
