/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBObject;

/**
 *
 * @author willi
 */
public class Parameter {
    private int id_parameter;
    private String description_parameter;
    private int factor;

    public Parameter(int id_parameter, String description_parameter, int factor) {
        this.id_parameter = id_parameter;
        this.description_parameter = description_parameter;
        this.factor = factor;
    }

    public int getId_parameter() {
        return id_parameter;
    }

    public void setId_parameter(int id_parameter) {
        this.id_parameter = id_parameter;
    }

    public String getDescription_parameter() {
        return description_parameter;
    }

    public void setDescription_parameter(String description_parameter) {
        this.description_parameter = description_parameter;
    }

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }
    
}
