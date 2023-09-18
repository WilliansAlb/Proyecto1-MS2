/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBObject.ScheduleDBO;
import Model.TeacherModel;
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
public class ScheduleDAO {
    public ScheduleDBO getSchedule(Connection cn, int id_schedule) {
        ScheduleDBO schedule = null;
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM GeneratedSchedule WHERE id_schedule = "+id_schedule);
            while (resultSet.next()) {
                schedule = new ScheduleDBO(resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4));
            }
            return schedule;
        } catch (SQLException ex) {
            Logger.getLogger(SalonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return schedule;
    }
    
    public ArrayList<ScheduleDBO> getSchedules(Connection cn) {
        ArrayList<ScheduleDBO> schedules = new ArrayList<>();
        ParameterDAO pa = new ParameterDAO();
        try {
            Statement statement = cn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM GeneratedSchedule ORDER BY efficiency_schedule DESC");
            while (resultSet.next()) {
                ScheduleDBO temp = new ScheduleDBO(resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4));
                temp.setParameters_used(pa.getParametersUsed(cn, resultSet.getInt(1)));
                schedules.add(temp);
            }
            return schedules;
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return schedules;
    }
}
