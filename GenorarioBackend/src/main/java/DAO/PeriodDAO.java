/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.PeriodModel;
import Model.SalonModel;
import java.sql.Connection;
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
public class PeriodDAO {
     public ArrayList<PeriodModel> getPeriodListModel(Connection cn) {
        ArrayList<PeriodModel> periods = new ArrayList<>();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_period, start_period, end_period FROM period");
            while (resultSet.next()) {
                periods.add(new PeriodModel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3)));
            }
            return periods;
        } catch (SQLException ex) {
            Logger.getLogger(SalonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return periods;
    }
}
