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
public class CurseJSON {

    private String codigo;
    private String curso;
    private String abreviatura;
    private int area;
    private int semestre;
    private int asignados;
    private JsonArray catedraticos;

    public CurseJSON(String codigo, String curso, String abreviatura, int area, int semestre, int asignados, JsonArray catedraticos) {
        this.codigo = codigo;
        this.curso = curso;
        this.abreviatura = abreviatura;
        this.area = area;
        this.semestre = semestre;
        this.asignados = asignados;
        this.catedraticos = catedraticos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getAsignados() {
        return asignados;
    }

    public void setAsignados(int asignados) {
        this.asignados = asignados;
    }

    public JsonArray getCatedraticos() {
        return catedraticos;
    }

    public void setCatedraticos(JsonArray catedraticos) {
        this.catedraticos = catedraticos;
    }

    
}
