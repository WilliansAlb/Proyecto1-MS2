/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBObject.TotalAreaSalon;
import DBObject.TotalAreaTeacher;
import JSONObjects.AreaJSON;
import JSONObjects.SalonJSON;
import Model.SalonModel;
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
public class SalonDAO {

    public boolean insertSalon(Connection cn, SalonJSON salon) {
        try {
            String sql = "INSERT INTO Salon (id_salon, name_salon, capacity, area_salon) VALUES (?,?,?,?)";
            try ( // Crea una PreparedStatement para ejecutar la sentencia SQL
                    PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
                preparedStatement.setInt(1, salon.getId());
                preparedStatement.setString(2, salon.getNombre());
                preparedStatement.setInt(3, salon.getCapacidad());
                preparedStatement.setInt(4, salon.getArea());
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

    public boolean insertSalonAssign(Connection cn, int id_salon, String code_curse) {
        try {
            String sql = "INSERT INTO SalonAssigned (salon_assigned, curse_assigned) VALUES (?,?)";
            try ( // Crea una PreparedStatement para ejecutar la sentencia SQL
                    PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
                preparedStatement.setInt(1, id_salon);
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

    public ArrayList<SalonJSON> getSalonList(Connection cn) {
        ArrayList<SalonJSON> areas = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_salon, name_salon, capacity, area_salon FROM salon");
            while (resultSet.next()) {
                areas.add(new SalonJSON(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4), null));
            }
            return areas;
        } catch (SQLException ex) {
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return areas;
    }

    public ArrayList<SalonModel> getSalonListModel(Connection cn) {
        ArrayList<SalonModel> areas = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_salon, name_salon, capacity, area_salon FROM salon");
            while (resultSet.next()) {
                areas.add(new SalonModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4)));
            }
            return areas;
        } catch (SQLException ex) {
            Logger.getLogger(SalonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return areas;
    }

    public ArrayList<TotalAreaSalon> getTotalSalons(Connection cn) {
        ArrayList<TotalAreaSalon> total = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  (SELECT COUNT(*) FROM salon sa WHERE sa.area_salon = a.id_area)  AS total, a.id_area AS area_salon FROM area a LEFT JOIN salon s ON a.id_area = s.area_salon GROUP BY a.id_area;");
            while (resultSet.next()) {
                total.add(new TotalAreaSalon(resultSet.getInt(1), resultSet.getInt(2)));
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    
}
