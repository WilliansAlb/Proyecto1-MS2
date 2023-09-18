/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBObject;

/**
 *
 * @author willi
 */
public class AssignedCurseDBO {
    private int period_assign;
    private String curse_assign;
    private int salon_assign;
    private int teacher_assign;
    private int schedule_assign;
    private int status_assign;
    private String message;

    public AssignedCurseDBO(int period_assign, String curse_assign, int salon_assign, int teacher_assign, int schedule_assign, int status_assign, String message) {
        this.period_assign = period_assign;
        this.curse_assign = curse_assign;
        this.salon_assign = salon_assign;
        this.teacher_assign = teacher_assign;
        this.schedule_assign = schedule_assign;
        this.status_assign = status_assign;
        this.message = message;
    }

    public int getPeriod_assign() {
        return period_assign;
    }

    public void setPeriod_assign(int period_assign) {
        this.period_assign = period_assign;
    }

    public String getCurse_assign() {
        return curse_assign;
    }

    public void setCurse_assign(String curse_assign) {
        this.curse_assign = curse_assign;
    }

    public int getSalon_assign() {
        return salon_assign;
    }

    public void setSalon_assign(int salon_assign) {
        this.salon_assign = salon_assign;
    }

    public int getTeacher_assign() {
        return teacher_assign;
    }

    public void setTeacher_assign(int teacher_assign) {
        this.teacher_assign = teacher_assign;
    }

    public int getSchedule_assign() {
        return schedule_assign;
    }

    public void setSchedule_assign(int schedule_assign) {
        this.schedule_assign = schedule_assign;
    }

    public int getStatus_assign() {
        return status_assign;
    }

    public void setStatus_assign(int status_assign) {
        this.status_assign = status_assign;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
