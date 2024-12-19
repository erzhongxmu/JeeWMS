package com.base.modules.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.util.UUIDGenerator;
import com.base.modules.jeeerp.entity.BillSeq;
import com.base.modules.jeeerp.entity.ConfCode;
import com.base.modules.jeeerp.service.IBillSeqService;

import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Package com.base.modules.basic.util
 * @date 2021/11/10 5:45 下午
 * @description
 */
@Component
public class GenerateCodeUtil {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    /*@Autowired
    private ConfCodeServiceImpl codeService;*/

    @Autowired
    private WmsRedisUtil wmsRedisUtil;
    @Autowired
    private IBillSeqService billSeqServic;
    /**
     *
     * @param codeType 编码类型 例如：naru
     * @return 序号
     */
    public synchronized String generateCode(String tableName, String codeType) {

        //查询编码配置项
        ConfCode code = (ConfCode) wmsRedisUtil.queryConfTableType("get_conf_code",tableName,codeType,null,null,null,null,null,null,null,null);
        if (code == null){
            code = (ConfCode) wmsRedisUtil.queryConfTableType("get_conf_code","tongyong","10",null,null,null,null,null,null,null,null);
        }
        String codePer = code.getCodePer();
        String timeForm = code.getTimeForm();
        String codeKey = "";
        if (StringUtils.isNoneBlank(timeForm)){
            codeKey = new SimpleDateFormat(timeForm).format(Calendar.getInstance().getTime());
        }


        int maxlen = 6;
        try {
            maxlen = Integer.parseInt(code.getCodeLast());
        }catch (Exception E){
            E.printStackTrace();
        }
        String seq = getTableSequencenew(tableName,codeKey,maxlen,codeType);

        return codePer + seq;

    }

    @Transactional
    public synchronized String getTableSequencenew (String tableName, String time, int maxlen, String codeType) {
        String code = "";
        QueryWrapper<BillSeq> billSeqQueryWrapper = new QueryWrapper<>();
        billSeqQueryWrapper.eq("ID1",tableName);
        if(!StringUtil.isBlankOrNull(time)){
            billSeqQueryWrapper.eq("ID2",time);
        }
        billSeqQueryWrapper.eq("ID3",codeType);
        try{
            BillSeq billSeq = billSeqServic.getOne(billSeqQueryWrapper, true);
            if(billSeq==null){
                code = time + StringUtil.leftPad(1,maxlen,'0') ;;
                billSeq = new BillSeq();
                billSeq.setId1(tableName);
                billSeq.setId2(time);
                billSeq.setId3(codeType);
                billSeq.setSeq(new BigDecimal("2"));
                billSeqServic.save(billSeq);
            }else{
                code =time + StringUtil.leftPad(billSeq.getSeq().intValue(),maxlen,'0');
                BigDecimal big = billSeq.getSeq();
                big = big.add(new BigDecimal("1"));
                billSeq.setSeq(big.setScale(0, RoundingMode.HALF_UP));
                billSeqServic.updateById(billSeq);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw  new JeecgBootException("请配置编号管理："+tableName+"，类型："+codeType+"error:"+e.getMessage());
         }
        return  code;
    }

    public String getSonPO(int index, String str) {
        String code = "";
        String[] names = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        code = str + names[index - 1];
        return  code;
    }

}
