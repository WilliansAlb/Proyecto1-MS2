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
