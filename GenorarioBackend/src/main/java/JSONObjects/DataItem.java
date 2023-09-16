/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONObjects;

import com.google.gson.JsonObject;
import java.util.List;

/**
 *
 * @author willi
 */
public class DataItem {
    private String type;
    private List<JsonObject> data;

    public DataItem(String type, List<JsonObject> data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<JsonObject> getData() {
        return data;
    }

    public void setData(List<JsonObject> data) {
        this.data = data;
    }
    
}
