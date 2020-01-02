package com.copy.model;

import com.google.gson.annotations.SerializedName;

public class VillageReportRec {

    @SerializedName("village_name")
    private String villageName;

    private Double consumption;

    public VillageReportRec(String villageName, Double consumption) {
        this.villageName = villageName;
        if (consumption == null) {
            this.consumption = 0.;
        } else {
            this.consumption = consumption;
        }
    }

    public String getVillageName() {
        return villageName;
    }

    public Double getConsumption() {
        return consumption;
    }
}
