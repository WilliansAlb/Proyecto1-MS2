/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import JSONObjects.TeacherJSON;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
}
