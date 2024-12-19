package com.base.modules.jeeerp.mapper;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeeerp.vo.BusiOmPage;
import com.base.modules.jeeerp.vo.BusiPoPage;
import com.base.modules.jeeerp.vo.BusiQueryPage;
import com.base.modules.jeewms.pdaapi.ApiresEntity;
import com.base.modules.jeewms.vo.AvlKwVo;
import org.apache.ibatis.annotations.Param;
import com.base.modules.jeeerp.entity.BusiPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: busi_po
 * @Author: base-boot
 * @Date:   2022-11-23
 * @Version: V1.0
 */
public interface BusiPoMapper extends BaseMapper<BusiPo> {

    IPage<BusiPoPage> MakeOutAnInvoiceData(@Param("iPage") IPage<BusiPoPage> page, @Param("querymap") HashMap<String, String> querymap);
    IPage<BusiPoPage> selectTotalMoneyList(@Param("iPage") IPage<BusiPoPage> page, @Param("querymap") HashMap<String, String> querymap);
    IPage<BusiPoPage> selectTotalMoneyListKH(@Param("iPage") IPage<BusiPoPage> page, @Param("querymap") HashMap<String, String> querymap);

    List<BusiPoPage> selectTotalMoneyList2(@Param("querymap") HashMap<String, String> querymap);
    List<BusiPoPage> selectTotalMoneyList2KH(@Param("querymap") HashMap<String, String> querymap);

    IPage<BusiQueryPage> canPinGenJinTongJi(@Param("iPage") IPage<BusiQueryPage> page, @Param("querymap") HashMap<String, String> querymap);
    IPage<BusiQueryPage> chaXunMeiTianDingDan(@Param("iPage") IPage<BusiQueryPage> page, @Param("querymap") HashMap<String, String> querymap);
    IPage<BusiQueryPage> XiangChuKuXiangQing(@Param("iPage") IPage<BusiQueryPage> page, @Param("querymap") HashMap<String, String> querymap);
    IPage<BusiQueryPage> huokuanzhichu(@Param("iPage") IPage<BusiQueryPage> page, @Param("querymap") HashMap<String, String> querymap);
    IPage<BusiQueryPage> rukumingxi(@Param("iPage") IPage<BusiQueryPage> page, @Param("querymap") HashMap<String, String> querymap);


    List<BusiPo> selectPOquery02();
    List<BusiPo> selectPOquery03();
}
