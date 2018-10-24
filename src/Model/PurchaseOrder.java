package Model;

import TEmPoSmgr.TEmPoSmgr;
import javafx.beans.property.SimpleStringProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class PurchaseOrder {

    private String id;
    private String branchId;
    private SimpleStringProperty status;
    private SimpleStringProperty UID;

    public PurchaseOrder(){
        this.id = "";
        this.branchId = "";
        this.status = new SimpleStringProperty();
        this.UID = new SimpleStringProperty();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getUID() {
        return UID.get();
    }

    public SimpleStringProperty UIDProperty() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID.set(UID);
    }

    public Map<String, String> getParameters(){
        Map<String, String> parameters= new LinkedHashMap<>();
        parameters.put("id" , this.getId());
        parameters.put("branchId" , this.getBranchId());
        parameters.put("status" , this.getStatus());
        parameters.put("UID" , this.getUID());
        return parameters;
    }

}
