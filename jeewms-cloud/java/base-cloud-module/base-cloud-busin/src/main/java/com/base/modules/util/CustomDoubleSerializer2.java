package com.base.modules.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class CustomDoubleSerializer2 extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        if (null == value) {
            //write the word 'null' if there's no value available
            jgen.writeNull();
        } else {
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
            BigDecimal add = bigDecimal.multiply(new BigDecimal("1000"));
            String numberString = add.toString();
            int dotIndex = numberString.indexOf(".");
            String integerString = numberString.substring(0, dotIndex);
            BigDecimal bigDecimal1 = new BigDecimal(integerString);
            BigDecimal divide = bigDecimal1.divide(new BigDecimal("1000"));
            String string = divide.toString();
            int i = string.indexOf(".");
            if(i == -1){
                string = string + ".000";
            }else {
                int dotIndex2 = string.indexOf(".");
                String decimalPart = string.substring(dotIndex2 + 1);
                if(decimalPart.length() < 2){
                    string = string + "00";
                }else  if(decimalPart.length() < 3){
                    string = string + "0";
                }
            }
            jgen.writeString(string);
        }
    }
}
