/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author willi
 */
public class PeriodModel {
    private int id_period;
    private int start_period;
    private int end_period;

    public PeriodModel(int id_period, int start_period, int end_period) {
        this.id_period = id_period;
        this.start_period = start_period;
        this.end_period = end_period;
    }

    public int getId_period() {
        return id_period;
    }

    public void setId_period(int id_period) {
        this.id_period = id_period;
    }

    public int getStart_period() {
        return start_period;
    }

    public void setStart_period(int start_period) {
        this.start_period = start_period;
    }

    public int getEnd_period() {
        return end_period;
    }

    public void setEnd_period(int end_period) {
        this.end_period = end_period;
    }
    
    
}
