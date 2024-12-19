package com.base.modules.jeewms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRegisterRequest {

    private List<GoodsRegister> goods;

}