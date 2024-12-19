package com.base.modules.bidata.bientity;

import com.google.gson.annotations.SerializedName;

public class dyflEntity {
    @SerializedName("value")
    private long value;
    public void setValue(long value) {
        this.value = value;
    }
    public long getValue() {
        return value;
    }
}
