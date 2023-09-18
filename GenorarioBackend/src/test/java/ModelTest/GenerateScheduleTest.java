/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelTest;

import DAO.ConnectionDAO;
import DAO.CurseDAO;
import DAO.ParameterDAO;
import DAO.PeriodDAO;
import DAO.SalonDAO;
import DAO.TeacherDAO;
import DBObject.CurseForWeight;
import DBObject.Parameter;
import DBObject.TotalAreaSalon;
import DBObject.TotalAreaTeacher;
import Model.AssignModel;
import Model.PeriodDisponibility;
import Model.PeriodModel;
import Model.SalonModel;
import Model.TeacherModel;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 *
 * @author willi
 */
public class GenerateScheduleTest {

    @Test
    public void generateWeights() {
        ConnectionDAO con = new ConnectionDAO();
        Connection cn = con.getConnection();
        con.closeConnection(cn);
    }
}
