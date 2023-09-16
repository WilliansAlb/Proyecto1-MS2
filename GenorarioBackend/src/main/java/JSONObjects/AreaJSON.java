/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONObjects;

/**
 *
 * @author willi
 */
public class AreaJSON {
    private int id_area;
    private String name_area;

    public AreaJSON(int id_area, String name_area) {
        this.id_area = id_area;
        this.name_area = name_area;
    }

    public int getId_area() {
        return id_area;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public String getName_area() {
        return name_area;
    }

    public void setName_area(String name_area) {
        this.name_area = name_area;
    }
    
    
}
