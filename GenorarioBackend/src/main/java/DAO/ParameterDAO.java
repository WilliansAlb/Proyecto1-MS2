/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBObject.Parameter;
import JSONObjects.SalonJSON;
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
public class ParameterDAO {

    public ArrayList<Parameter> getParameters(Connection cn) {
        ArrayList<Parameter> parameter = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM parameter");
            while (resultSet.next()) {
                parameter.add(new Parameter(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
            }
            return parameter;
        } catch (SQLException ex) {
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parameter;
    }
    
    public ArrayList<Parameter> getParametersUsed(Connection cn, int schedule) {
        ArrayList<Parameter> parameter = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ParameterUsed WHERE id_schedule_used = "+schedule);
            while (resultSet.next()) {
                parameter.add(new Parameter(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
            }
            return parameter;
        } catch (SQLException ex) {
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parameter;
    }
    
    public boolean insertParameterUsed(Connection cn, Parameter parameter, int schedule) {
        try {
            String sql = "INSERT INTO ParameterUsed (id_parameter_used, id_schedule_used, value_used) VALUES (?,?,?)";
            try ( // Crea una PreparedStatement para ejecutar la sentencia SQL
                    PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
                preparedStatement.setInt(1, parameter.getId_parameter());
                preparedStatement.setInt(2, schedule);
                preparedStatement.setInt(3, parameter.getFactor());
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
            Logger.getLogger(ParameterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    };

    public boolean updateFactor(Connection cn, int id_parameter, int nuevoFactor) {
        try {
            // SQL para actualizar el valor en la tabla "parameter"
            String sql = "UPDATE parameter SET factor = ? WHERE id_parameter = ?";

            // Crea una sentencia preparada
            PreparedStatement pstmt = cn.prepareStatement(sql);
            pstmt.setInt(1, nuevoFactor);
            pstmt.setInt(2, id_parameter);

            // Ejecuta la actualización y verifica si se realizaron actualizaciones
            int filasActualizadas = pstmt.executeUpdate();

            return filasActualizadas > 0; // Devuelve true si se actualizó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Devuelve false en caso de error
        }
    }
    
    
}
