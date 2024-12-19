package com.base.modules.bidata.bientity;

import com.google.gson.annotations.SerializedName;

public class dataEntity {

    /**
     * data : 71.41
     */

    @SerializedName("data")
    private Double data;

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }
}
