/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONObjects;

import com.google.gson.JsonArray;

/**
 *
 * @author willi
 */
public class SalonJSON {
    private int id;
    private String nombre;
    private int capacidad;
    private int area;
    private JsonArray cursos;

    public SalonJSON(int id, String nombre, int capacidad, int area, JsonArray cursos) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.area = area;
        this.cursos = cursos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public JsonArray getCursos() {
        return cursos;
    }

    public void setCursos(JsonArray cursos) {
        this.cursos = cursos;
    }
    
    
}
