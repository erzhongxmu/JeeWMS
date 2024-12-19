package com.base.modules.bidata.bientity;
//总储位
import com.google.gson.annotations.SerializedName;
public class FpqEntity {
    /**
     *
     * data : 71.41
     */

    @SerializedName("value")
    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


}
