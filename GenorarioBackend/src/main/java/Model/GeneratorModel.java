/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.ConnectionDAO;
import DAO.CurseDAO;
import DAO.GenerateDAO;
import DAO.ParameterDAO;
import DAO.PeriodDAO;
import DAO.SalonDAO;
import DAO.ScheduleDAO;
import DAO.TeacherDAO;
import DAO.WeightDAO;
import DBObject.AssignedCurseDBO;
import DBObject.CurseForWeight;
import DBObject.Parameter;
import DBObject.TotalAreaSalon;
import DBObject.TotalAreaTeacher;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author willi
 */
public class GeneratorModel {

    ArrayList<PeriodModel> periodList;
    ArrayList<AreaModel> semesters;
    public double efficienty = 0.00;

    public ArrayList<SalonModel> generateWeights(int generateSchedule) {
        ConnectionDAO con = new ConnectionDAO();
        Connection cn = con.getConnection();
        CurseDAO c = new CurseDAO();
        ParameterDAO p = new ParameterDAO();
        TeacherDAO ta = new TeacherDAO();
        SalonDAO sa = new SalonDAO();
        PeriodDAO pa = new PeriodDAO();
        WeightDAO w = new WeightDAO();
        ArrayList<CurseForWeight> curses = c.getDataCurses(cn);
        ArrayList<Parameter> parameters = p.getParameters(cn);
        ArrayList<TotalAreaTeacher> totals = ta.getTotalTeachers(cn);
        ArrayList<TotalAreaSalon> totalSalon = sa.getTotalSalons(cn);
        semesters = new ArrayList<>();
        for (int i = 0; i < totals.size(); i++) {
            semesters.add(new AreaModel());
        }
        if (generateSchedule != 0) {
            for (int i = 2; i < 11; i++) {
                p.insertParameterUsed(cn, parameters.get(i), generateSchedule);
            }
        }
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
            if (generateSchedule != 0) {
                w.insertWeight(cn, curse.getCode_curse(), generateSchedule, (int) curse.getWeight());
            }
            System.out.println("----------------------------------------------");
            System.out.println("Code: " + curse.getCode_curse() + "\tName: " + curse.getName_curse() + "\tSemester: " + curse.getSemester_curse());
            System.out.println("\t\tWeight: " + curse.getWeight());
            AssignModel an = new AssignModel();
            an.setCurse(curse);
            if (priority_selected == 1) {
                assignedTeacher(curse, an, teachersForArea, c, cn);
                System.out.println(an.getMessage() + " en el periodo " + an.getPeriod().getId_period() + " - " + an.getPeriod().getEnd_period());
                assignedSalon(curse, an, salonsForArea, c, cn, salonList);
                if (generateSchedule != 0) {
                    an.setSchedule(generateSchedule);
                    c.insertAssignedCurse(cn, an);
                }
            } else {

            }
            switch (an.getStatus()) {
                case Status.NOASSIGN:
                    efficienty += 0;
                    break;
                case Status.OK:
                    efficienty += 1;
                    break;
                case Status.OVERLAP:
                    efficienty += 0.3;
                    break;
                case Status.TEACHEROUT:
                    efficienty += 0.65;
                    break;
                case Status.SALONDIFF:
                    efficienty += 0.9;
                    break;
                case Status.CRASH:
                    efficienty += 0.35;
                    break;
                case Status.OVERQUORUM:
                    efficienty += 0.95;
                    break;
                default:
                    efficienty += 0;
                    break;
            }
        }
        efficienty = (efficienty / curses.size()) * 100;
        if (generateSchedule != 0) {
            GenerateDAO gdao = new GenerateDAO();
            gdao.updateEfficiency(cn, generateSchedule, efficienty);
        }
        con.closeConnection(cn);
        return salonList;
    }

    public ArrayList<SalonModel> generatedSchedule(int generateSchedule) {
        ConnectionDAO con = new ConnectionDAO();
        Connection cn = con.getConnection();
        CurseDAO c = new CurseDAO();
        ParameterDAO p = new ParameterDAO();
        TeacherDAO ta = new TeacherDAO();
        SalonDAO sa = new SalonDAO();
        PeriodDAO pa = new PeriodDAO();
        WeightDAO w = new WeightDAO();
        ArrayList<SalonModel> salonList = sa.getSalonListModel(cn);
        ArrayList<CurseForWeight> curses = c.getDataCurses(cn);
        ArrayList<AssignedCurseDBO> assignedCurses = c.getAssignedCurses(cn, generateSchedule);
        periodList = pa.getPeriodListModel(cn);
        ArrayList<TeacherModel> teacherList = ta.getTeacherListModel(cn);
        for (AssignedCurseDBO assignedCurse : assignedCurses) {
            AssignModel an = new AssignModel();
            for (CurseForWeight curse : curses) {
                if (assignedCurse.getCurse_assign().equals(curse.getCode_curse())) {
                    an.setCurse(curse);
                    break;
                }
            }
            for (PeriodModel period : periodList) {
                if (period.getId_period() == assignedCurse.getPeriod_assign()) {
                    an.setPeriod(period);
                    break;
                }
            }
            for (SalonModel salon : salonList) {
                if (salon.getId_salon() == assignedCurse.getSalon_assign()) {
                    salon.getAssign()[an.getPeriod().getId_period() - 1] = an;
                    an.setSalon(salon.getId_salon());
                    break;
                }
            }
            an.setSchedule(generateSchedule);
            an.setStatus(assignedCurse.getStatus_assign());
            an.setMessage(assignedCurse.getMessage());
            for (TeacherModel teacherModel : teacherList) {
                if (assignedCurse.getTeacher_assign() == teacherModel.getId_teacher()) {
                    an.setTeacher(teacherModel);
                    break;
                }
            }
        }
        con.closeConnection(cn);
        return salonList;
    }

    public void assignedSalon(CurseForWeight curse, AssignModel an,
            Map<Integer, List<SalonModel>> salonsForArea, CurseDAO c, Connection cn, ArrayList<SalonModel> salonList) {
        if (curse.getCount_salons() == 0) {
            List<SalonModel> selected = salonsForArea.get(curse.getArea_curse());
            SalonModel choosen = null;
            for (SalonModel salonModel : selected) {
                if (salonModel.getAssign()[an.getPeriod().getId_period() - 1] == null) {
                    salonModel.getAssign()[an.getPeriod().getId_period() - 1] = an;
                    choosen = salonModel;
                    an.setSalon(choosen.getId_salon());
                    if (salonModel.getCapacity() < an.getCurse().getAssigned_students()) {
                        an.setStatus(Status.OVERQUORUM);
                        an.setMessage(an.getMessage() + " - Se supera la capacidad del salon");
                    }
                    break;
                }
            }
            if (choosen == null) {
                //an.setMessage(an.getMessage()+"\n No se le pudo asignar un salon al curso");
                for (SalonModel salonModel : salonList) {
                    if (salonModel.getAssign()[an.getPeriod().getId_period() - 1] == null) {
                        salonModel.getAssign()[an.getPeriod().getId_period() - 1] = an;
                        choosen = salonModel;
                        an.setSalon(choosen.getId_salon());
                        if (salonModel.getCapacity() < an.getCurse().getAssigned_students()) {
                            an.setStatus(Status.OVERQUORUM);
                            an.setMessage(an.getMessage() + " - Se supera la capacidad del salon");
                        }
                        break;
                    }
                }
                if (choosen == null) {
                    System.out.println("\t\tSalon: INDENIFIDO pues no hay salones libres para ese periodo");
                    System.out.println(an.getTeacher().getCount_assigned());
                    an.setStatus(Status.NOASSIGN);
                } else {
                    if (choosen.getArea_salon() != 1) {
                        String[] areas = {"Sistemas", "Civil", "Mecanica", "Industrial", "Mecanica Industrial"};
                        System.out.println("\t\tSalon: " + choosen.getName_salon() + " se está usando un salon de " + areas[choosen.getArea_salon() - 1]);
                        an.setStatus(Status.SALONDIFF);
                    } else {
                        System.out.println("\t\tSalon: " + choosen.getName_salon());
                    }
                }
            } else {
                System.out.println("\t\tSalon: " + choosen.getName_salon());
            }
        } else {
            ArrayList<Integer> salons_id = c.getCurseSalonAssign(cn, curse.getCode_curse());
            for (SalonModel salonModel : salonList) {
                if (salonModel.getId_salon() == salons_id.get(0)) {
                    if (salonModel.getAssign()[an.getPeriod().getId_period() - 1] != null) {
                        AssignModel previous = salonModel.getAssign()[an.getPeriod().getId_period() - 1];
                        previous.setStatus(Status.NOASSIGN);
                        an.setStatus(Status.CRASH);
                        an.setMessage(an.getMessage() + " - CHOQUE: el curso " + previous.getCurse().getName_curse() + " chocó con este salon en el mismo periodo. Solo se asigno este por tener prioridad de salon");
                    }
                    salonModel.getAssign()[an.getPeriod().getId_period() - 1] = an;
                    an.setSalon(salonModel.getId_salon());
                    if (salonModel.getCapacity() < an.getCurse().getAssigned_students()) {
                        an.setStatus(Status.OVERQUORUM);
                        an.setMessage(an.getMessage() + " - Se supera la capacidad del salon");
                    }
                    break;
                }
            }
        }
    }

    public void assignedTeacher(CurseForWeight curse, AssignModel an,
            Map<Integer, List<TeacherModel>> teachersForArea, CurseDAO c, Connection cn) {
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
    }

    public void selectedTeacher(List<TeacherModel> selected, AssignModel an) {
        int period = 0;
        Collections.shuffle(selected);
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
            an.setStatus(Status.TEACHEROUT);
        } else {
//            for (PeriodDisponibility periodTemp : an.getTeacher().getMax_curses()) {
//                if (!periodTemp.isTaken()) {
//                    periodTemp.setTaken(true);
//                    period = periodTemp.getStart();
//                    an.getTeacher().setCount_assigned(an.getTeacher().getCount_assigned() + 1);
//                    //status = 1;
//                    //messageResponse = "";
//                    an.setMessage("Asignado " + an.getTeacher().getName_teacher());
//                    an.setStatus(1);
//                    break;
//                }
//            }
            boolean findOut = false;
            do {
                Random ran = new Random(System.currentTimeMillis());
                int sel = ran.nextInt(an.getTeacher().getMax_curses().size());
                if (!an.getTeacher().getMax_curses().get(sel).isTaken()) {
                    an.getTeacher().getMax_curses().get(sel).setTaken(true);
                    period = an.getTeacher().getMax_curses().get(sel).getStart();
                    an.getTeacher().setCount_assigned(an.getTeacher().getCount_assigned() + 1);
                    an.setStatus(Status.OK);
                    an.setMessage("Asignado " + an.getTeacher().getName_teacher());
                    findOut = true;
                }
            } while (!findOut);
        }

        an.setPeriod(getPeriod(period));
        period = an.getPeriod().getId_period();
        AreaModel temp = semesters.get(an.getCurse().getArea_curse() - 1);
        if (temp.getCount(an.getCurse().getSemester_curse(), period) > 0) {
            an.setStatus(Status.OVERLAP);
            an.setMessage("Traslape con otro curso del mismo semestre y area");
        }
        temp.sumCount(an.getCurse().getSemester_curse(), period);
    }

    public PeriodModel getPeriod(int start) {
        for (PeriodModel temp : periodList) {
            if (temp.getStart_period() == start) {
                return temp;
            }
        }
        return null;
    }
}
