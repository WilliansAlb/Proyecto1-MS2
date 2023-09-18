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
public class WeightDAO {
    public boolean insertWeight(Connection cn, String curse_weight, int schedule_weight, int weight) {
        try {
            String sql = "INSERT INTO WeightCalculed (curse_weight, schedule_weight, weight) VALUES (?,?,?)";
            try ( // Crea una PreparedStatement para ejecutar la sentencia SQL
                    PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
                preparedStatement.setString(1, curse_weight);
                preparedStatement.setInt(2, schedule_weight);
                preparedStatement.setInt(3, weight);
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
}
