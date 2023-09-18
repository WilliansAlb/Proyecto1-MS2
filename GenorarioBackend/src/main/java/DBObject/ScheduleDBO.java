/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBObject;

import java.util.ArrayList;

/**
 *
 * @author willi
 */
public class ScheduleDBO {
    private int id_schedule;
    private String date_schedule;
    private double efficiency_schedule;
    private String name_schedule;
    private ArrayList<Parameter> parameters_used = new ArrayList<>();

    public ScheduleDBO(int id_schedule, String date_schedule, double efficiency_schedule, String name_schedule) {
        this.id_schedule = id_schedule;
        this.date_schedule = date_schedule;
        this.efficiency_schedule = efficiency_schedule;
        this.name_schedule = name_schedule;
    }

    public int getId_schedule() {
        return id_schedule;
    }

    public void setId_schedule(int id_schedule) {
        this.id_schedule = id_schedule;
    }

    public String getDate_schedule() {
        return date_schedule;
    }

    public void setDate_schedule(String date_schedule) {
        this.date_schedule = date_schedule;
    }

    public double getEfficiency_schedule() {
        return efficiency_schedule;
    }

    public void setEfficiency_schedule(double efficiency_schedule) {
        this.efficiency_schedule = efficiency_schedule;
    }

    public String getName_schedule() {
        return name_schedule;
    }

    public void setName_schedule(String name_schedule) {
        this.name_schedule = name_schedule;
    }

    public ArrayList<Parameter> getParameters_used() {
        return parameters_used;
    }

    public void setParameters_used(ArrayList<Parameter> parameters_used) {
        this.parameters_used = parameters_used;
    }
    
    
}
