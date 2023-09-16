/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generadorjava.genorariojava;

import com.google.gson.JsonArray;
import java.util.ArrayList;

/**
 *
 * @author willi
 */
public class Curse {
    private String curso;
    private String codigo;
    private String abreviatura;
    private String area;
    private int semestre;
    private int asignados;

    public Curse(String curso, String codigo, String abreviatura, String area, int semestre, int asignados, JsonArray catedraticos) {
        this.curso = curso;
        this.codigo = codigo;
        this.abreviatura = abreviatura;
        this.area = area;
        this.semestre = semestre;
        this.asignados = asignados;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
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
}
