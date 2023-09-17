/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DBObject.CurseForWeight;

/**
 *
 * @author willi
 */
public class AssignModel {
    private PeriodModel period;
    private CurseForWeight curse;
    private TeacherModel teacher;
    private int schedule;
    private int status;
    private String message;

    public AssignModel(PeriodModel period, CurseForWeight curse, TeacherModel teacher, int schedule, int status, String message) {
        this.period = period;
        this.curse = curse;
        this.teacher = teacher;
        this.schedule = schedule;
        this.status = status;
        this.message = message;
    }

    public AssignModel() {
    }

    public PeriodModel getPeriod() {
        return period;
    }

    public void setPeriod(PeriodModel period) {
        this.period = period;
    }

    public CurseForWeight getCurse() {
        return curse;
    }

    public void setCurse(CurseForWeight curse) {
        this.curse = curse;
    }

    public TeacherModel getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherModel teacher) {
        this.teacher = teacher;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
