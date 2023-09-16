/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import JSONObjects.AreaJSON;
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
public class AreaDAO {

    public int getAreas(Connection cn) {
        try {
            int total_areas = 0;
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM area");

            while (resultSet.next()) {
                total_areas += resultSet.getInt(1);
            }
            System.out.println(total_areas);
            return total_areas;
        } catch (SQLException ex) {
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<AreaJSON> getAreasList(Connection cn) {
        ArrayList<AreaJSON> areas = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_area, name_area FROM area");
            while (resultSet.next()) {
                areas.add(new AreaJSON(resultSet.getInt(1), resultSet.getString(2)));
            }
            return areas;
        } catch (SQLException ex) {
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return areas;
    }

    public boolean insertArea(Connection cn, String nombreArea) {
        try {
            String sql = "INSERT INTO Area (name_area) VALUES (?)";
            try ( // Crea una PreparedStatement para ejecutar la sentencia SQL
                    PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
                preparedStatement.setString(1, nombreArea);
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
;
}
