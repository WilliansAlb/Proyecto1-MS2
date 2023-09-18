/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import JSONObjects.SalonJSON;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
public class GenerateDAO {

    public int insertSchedule(Connection cn, double efficiency_schedule, String name_schedule) {
        Date fechaActual = new Date();
        // Define el formato de fecha deseado
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        // Convierte la fecha actual al formato deseado como una cadena
        String fechaConFormato = formato.format(fechaActual);
        int generate_key = -1;
        if (name_schedule.equals("")){
            name_schedule = "HORARIO "+fechaConFormato;
        }
        try {
            String sql = "INSERT INTO GeneratedSchedule (date_schedule, efficiency_schedule, name_schedule) VALUES (?,?,?)";
            try ( // Crea una PreparedStatement para ejecutar la sentencia SQL
                    PreparedStatement preparedStatement = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, fechaConFormato);
                preparedStatement.setDouble(2, efficiency_schedule);
                preparedStatement.setString(3, name_schedule);
                // Ejecuta la inserción
                int filasInsertadas = preparedStatement.executeUpdate();

                if (filasInsertadas > 0) {
                    // Recupera el ID generado automáticamente
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        generate_key = generatedKeys.getInt(1);
                    }
                } else {
                    System.out.println("No se pudo insertar el registro.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CurseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generate_key;
    }
    
    public void updateEfficiency(Connection cn, int id_schedule, double efficiency_schedule) {
        String sql = "UPDATE generatedSchedule SET efficiency_schedule = ? WHERE id_schedule = ?";

        try (PreparedStatement preparedStatement = cn.prepareStatement(sql)) {

            preparedStatement.setDouble(1, efficiency_schedule);
            preparedStatement.setInt(2, id_schedule);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Efficiency updated successfully.");
            } else {
                System.out.println("No rows were updated. ID not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating efficiency: " + e.getMessage());
        }
    }
}
