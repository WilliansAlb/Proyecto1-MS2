/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBObject;

/**
 *
 * @author willi
 */
public class CurseForWeight {
    private String code_curse;
    private String name_curse;
    private int semester_curse;
    private int assigned_students;
    private int area_curse;
    private String abbr_curse;
    private int count_teachers;
    private int count_salons;
    private double weight;

    public CurseForWeight(String code_curse, String name_curse, int semester_curse, int assigned_students, int area_curse, String abbr_curse, int count_teachers, int count_salons) {
        this.code_curse = code_curse;
        this.name_curse = name_curse;
        this.semester_curse = semester_curse;
        this.assigned_students = assigned_students;
        this.area_curse = area_curse;
        this.abbr_curse = abbr_curse;
        this.count_teachers = count_teachers;
        this.count_salons = count_salons;
    }

    public String getCode_curse() {
        return code_curse;
    }

    public void setCode_curse(String code_curse) {
        this.code_curse = code_curse;
    }

    public String getName_curse() {
        return name_curse;
    }

    public void setName_curse(String name_curse) {
        this.name_curse = name_curse;
    }

    public int getSemester_curse() {
        return semester_curse;
    }

    public void setSemester_curse(int semester_curse) {
        this.semester_curse = semester_curse;
    }

    public int getAssigned_students() {
        return assigned_students;
    }

    public void setAssigned_students(int assigned_students) {
        this.assigned_students = assigned_students;
    }

    public int getArea_curse() {
        return area_curse;
    }

    public void setArea_curse(int area_curse) {
        this.area_curse = area_curse;
    }

    public String getAbbr_curse() {
        return abbr_curse;
    }

    public void setAbbr_curse(String abbr_curse) {
        this.abbr_curse = abbr_curse;
    }

    public int getCount_teachers() {
        return count_teachers;
    }

    public void setCount_teachers(int count_teachers) {
        this.count_teachers = count_teachers;
    }

    public int getCount_salons() {
        return count_salons;
    }

    public void setCount_salons(int count_salons) {
        this.count_salons = count_salons;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    
}
