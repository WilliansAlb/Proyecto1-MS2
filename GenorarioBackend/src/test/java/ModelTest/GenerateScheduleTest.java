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

    ArrayList<PeriodModel> periodList;

    @Test
    public void generateWeights() {
        ConnectionDAO con = new ConnectionDAO();
        Connection cn = con.getConnection();
        CurseDAO c = new CurseDAO();
        ParameterDAO p = new ParameterDAO();
        TeacherDAO ta = new TeacherDAO();
        SalonDAO sa = new SalonDAO();
        PeriodDAO pa = new PeriodDAO();
        ArrayList<CurseForWeight> curses = c.getDataCurses(cn);
        ArrayList<Parameter> parameters = p.getParameters(cn);
        ArrayList<TotalAreaTeacher> totals = ta.getTotalTeachers(cn);
        ArrayList<TotalAreaSalon> totalSalon = sa.getTotalSalons(cn);
        int priority_selected = 1;
        for (CurseForWeight curse : curses) {
            double weight = 0;
            weight += curse.getAssigned_students() * parameters.get(2).getFactor();
            int number_teachers, number_salons;
            switch (curse.getCount_teachers()) {
                case 0:
                    number_teachers = totals.get(curse.getArea_curse() - 1).getTotal();
                    break;
                default:
                    number_teachers = curse.getCount_teachers();
                    break;
            }

            switch (curse.getCount_salons()) {
                case 0:
                    number_salons = totalSalon.get(curse.getArea_curse() - 1).getTotal();
                    break;
                default:
                    number_salons = curse.getCount_salons();
                    break;
            }
            weight += (int) (parameters.get(3).getFactor() * (1 / number_teachers));
            weight += (int) (parameters.get(4).getFactor() * (1 / number_salons));
            if (curse.getSemester_curse() % 2 != 0) {
                weight = weight - (weight * ((double) (parameters.get(10).getFactor()) / 100.00));
            }

            switch (curse.getArea_curse()) {
                case 2:
                    weight += 100 * parameters.get(5).getFactor();
                    break;
                case 3:
                    weight += 100 * parameters.get(6).getFactor();
                    break;
                case 4:
                    weight += 100 * parameters.get(7).getFactor();
                    break;
                case 5:
                    weight += 100 * parameters.get(8).getFactor();
                    break;
                case 6:
                    weight += 100 * parameters.get(9).getFactor();
                    break;
                default:
                    break;
            }
            curse.setWeight(weight);
        }

        Collections.sort(curses, (curse1, curse2) -> (int) curse2.getWeight() - (int) curse1.getWeight());

        ArrayList<SalonModel> salonList = sa.getSalonListModel(cn);
        periodList = pa.getPeriodListModel(cn);
        ArrayList<TeacherModel> teacherList = ta.getTeacherListModel(cn);
        Map<Integer, List<SalonModel>> salonsForArea = salonList.stream()
                .collect(Collectors.groupingBy(SalonModel::getArea_salon));
        Map<Integer, List<TeacherModel>> teachersForArea = teacherList.stream()
                .collect(Collectors.groupingBy(TeacherModel::getArea_teacher));

        for (CurseForWeight curse : curses) {
            System.out.println("----------------------------------------------");
            System.out.println("Code: " + curse.getCode_curse() + "\tName: " + curse.getName_curse() + "\tSemester: " + curse.getSemester_curse());
            System.out.println("\t\tWeight: " + curse.getWeight());
            AssignModel an = new AssignModel();
            an.setCurse(curse);
            if (priority_selected == 1) {
                if (curse.getCount_teachers() == 0) {
                    List<TeacherModel> selected = teachersForArea.get(curse.getArea_curse());
                    selectedTeacher(selected, an);
                } else {
                    ArrayList<Integer> teachers_id = c.getCurseTeacherAssign(cn, curse.getCode_curse());
                    List<TeacherModel> selected = teachersForArea.get(curse.getArea_curse());
                    List<TeacherModel> te = new ArrayList<>();
                    boolean flag = false;
                    for (TeacherModel temp : selected) {
                        for (int i = 0; i < teachers_id.size() && !flag; i++) {
                            if (temp.getId_teacher() == teachers_id.get(i)) {
                                te.add(temp);
                                if (teachers_id.size() == te.size()) {
                                    flag = true;
                                }
                            }
                        }
                        if (flag) {
                            break;
                        }
                    }
                    selectedTeacher(te, an);
                }
                System.out.println(an.getMessage()+" en el periodo "+an.getPeriod().getStart_period()+" - "+an.getPeriod().getEnd_period());
                
            } else {

            }
        }
    }

    public void selectedTeacher(List<TeacherModel> selected, AssignModel an) {
        int period = 0;
        Collections.sort(selected, (teacher1, teacher2) -> (int) teacher1.getCount_assigned() - (int) teacher2.getCount_assigned());
        for (TeacherModel temp : selected) {
            temp.max_curses();
            if (temp.getMax_curses().size() > temp.getCount_assigned()) {
                an.setTeacher(temp);
                break;
            }
        }
        if (an.getTeacher() == null) {
            Random aleatorio = new Random(System.currentTimeMillis());
            an.setTeacher(selected.get(aleatorio.nextInt(selected.size())));
            period = an.getTeacher().getMax_curses().get(aleatorio.nextInt(an.getTeacher().getMax_curses().size())).getStart();
            an.getTeacher().setCount_assigned(an.getTeacher().getCount_assigned() + 1);
            //status = 0;
            //messageResponse = "El catedratico ya da clases en el mismo periodo";
            an.setMessage("El catedratico " + an.getTeacher().getName_teacher() + " ya da clases en el mismo periodo");
            an.setStatus(0);
        } else {
            for (PeriodDisponibility periodTemp : an.getTeacher().getMax_curses()) {
                if (!periodTemp.isTaken()) {
                    periodTemp.setTaken(true);
                    period = periodTemp.getStart();
                    an.getTeacher().setCount_assigned(an.getTeacher().getCount_assigned() + 1);
                    //status = 1;
                    //messageResponse = "";
                    an.setMessage("Asignado " + an.getTeacher().getName_teacher());
                    an.setStatus(1);
                    break;
                }
            }
        }
        an.setPeriod(getPeriod(period));
    }
    
    public PeriodModel getPeriod(int start){
        for (PeriodModel temp : periodList){
            if (temp.getStart_period()==start){
                return temp;
            }
        }
        return null;
    }

}
