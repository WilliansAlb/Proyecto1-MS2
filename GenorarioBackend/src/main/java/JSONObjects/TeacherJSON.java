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
public class TeacherJSON {
    private int id;
    private String nombre;
    private String alter;
    private int entrada;
    private int salida;
    private int area;
    private JsonArray cursos;

    public TeacherJSON(int id, String nombre, String alter, int entrada, int salida, int area, JsonArray cursos) {
        this.id = id;
        this.nombre = nombre;
        this.alter = alter;
        this.entrada = entrada;
        this.salida = salida;
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

    public String getAlter() {
        return alter;
    }

    public void setAlter(String alter) {
        this.alter = alter;
    }

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }

    public int getSalida() {
        return salida;
    }

    public void setSalida(int salida) {
        this.salida = salida;
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
