/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author willi
 */
public class AreaModel {
    private final int[][] semesters;

    public AreaModel() {
        int semester = 10; // Número de semestres
        int period = 9; // Número de periodos
        int[][] matriz = new int[semester][period];
        for (int i = 0; i < semester; i++) {
            for (int j = 0; j < period; j++) {
                matriz[i][j] = 0;
            }
        }
        this.semesters = matriz;
    }

    public int getCount(int semester, int period){
        return this.semesters[semester-1][period-1];
    }
    
    public void sumCount(int semester,int period){
        this.semesters[semester-1][period-1]++;
    }
}
