/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author willi
 */
public class SalonModel {
    private int id_salon;
    private String name_salon;
    private int capacity;
    private int area_salon;
    private AssignModel assign[] = new AssignModel[9];

    public SalonModel(int id_salon, String name_salon, int capacity, int area_salon) {
        this.id_salon = id_salon;
        this.name_salon = name_salon;
        this.capacity = capacity;
        this.area_salon = area_salon;
    }

    public int getId_salon() {
        return id_salon;
    }

    public void setId_salon(int id_salon) {
        this.id_salon = id_salon;
    }

    public String getName_salon() {
        return name_salon;
    }

    public void setName_salon(String name_salon) {
        this.name_salon = name_salon;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getArea_salon() {
        return area_salon;
    }

    public void setArea_salon(int area_salon) {
        this.area_salon = area_salon;
    }

    public AssignModel[] getAssign() {
        return assign;
    }

    public void setAssign(AssignModel[] assign) {
        this.assign = assign;
    }
    
}
