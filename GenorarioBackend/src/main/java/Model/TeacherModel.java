/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author willi
 */
public class TeacherModel {

    private int id_teacher;
    private String name_teacher;
    private int start_hour;
    private int end_hour;
    private int area_teacher;
    private String alter_teacher;
    private int count_assigned;
    private ArrayList<PeriodDisponibility> max_curses;

    public TeacherModel(int id_teacher, String name_teacher, int start_hour, int end_hour, int area_teacher, String alter_teacher) {
        this.id_teacher = id_teacher;
        this.name_teacher = name_teacher;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
        this.area_teacher = area_teacher;
        this.alter_teacher = alter_teacher;
        this.count_assigned = 0;
        this.max_curses = new ArrayList<>();
    }

    public ArrayList<PeriodDisponibility> getMax_curses() {
        return max_curses;
    }

    public void setMax_curses(ArrayList<PeriodDisponibility> max_curses) {
        this.max_curses = max_curses;
    }

    public ArrayList<PeriodDisponibility> max_curses() {
        int[] valuesStart = {1340, 1430, 1520, 1610, 1700, 1750, 1840, 1930, 2020, 2110};
        ArrayList<PeriodDisponibility> count = new ArrayList<>();
        if (max_curses.isEmpty()) {
            for (int i = 1; i < valuesStart.length; i++) {
                if (start_hour <= valuesStart[i - 1] && end_hour >= valuesStart[i]) {
                    max_curses.add(new PeriodDisponibility(valuesStart[i - 1], false));
                }
                if (end_hour < valuesStart[i]) {
                    break;
                }
            }
        }
        return count;
    }

    public int getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(int id_teacher) {
        this.id_teacher = id_teacher;
    }

    public String getName_teacher() {
        return name_teacher;
    }

    public void setName_teacher(String name_teacher) {
        this.name_teacher = name_teacher;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getArea_teacher() {
        return area_teacher;
    }

    public void setArea_teacher(int area_teacher) {
        this.area_teacher = area_teacher;
    }

    public String getAlter_teacher() {
        return alter_teacher;
    }

    public void setAlter_teacher(String alter_teacher) {
        this.alter_teacher = alter_teacher;
    }

    public int getCount_assigned() {
        return count_assigned;
    }

    public void setCount_assigned(int count_assigned) {
        this.count_assigned = count_assigned;
    }

}
