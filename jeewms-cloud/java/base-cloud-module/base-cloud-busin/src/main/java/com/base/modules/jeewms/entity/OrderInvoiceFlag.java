package com.base.modules.jeewms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInvoiceFlag implements Serializable {


  private String  sellerTaxName	; //销方税号名称(注意这里是驼峰的)
  private int     num		    ; //发票数量



}
