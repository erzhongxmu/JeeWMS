package com.zzjee.wmutil;

import com.zzjee.md.entity.MdCusOtherEntity;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.md.entity.MdSupEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;

import java.util.HashMap;
import java.util.Map;

public class wmIntUtil {
    public static void   getproduct(String formDate){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("formDate",formDate);
//        paramMap.put("lastUpdateTime","2017-01-01");
//        paramMap.put("pi_class","采购验收单");
        try{
            productResult productResult =uasUtil.getProduct(paramMap);
            SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);


            if(productResult.getResult().size()>0){
                for(int i=0;i< productResult.getResult().size();i++){
                    String ShpBianMa =  productResult.getResult().get(i).getPrCode();
                    MdGoodsEntity mdn = new MdGoodsEntity();
                    mdn.setShpBianMa(productResult.getResult().get(i).getPrCode());
                    mdn.setChlShl("1");
                    mdn.setSuoShuKeHu(ResourceUtil.getConfigByName("uas.cuscode"));
                    mdn.setShpMingCheng(productResult.getResult().get(i).getPrDetail());
                    mdn.setBzhiQi(productResult.getResult().get(i).getPrValiddays());
                    mdn.setChpShuXing(productResult.getResult().get(i).getPrKind());
                    mdn.setJshDanWei(productResult.getResult().get(i).getPrUnit());
                    mdn.setShlDanWei(productResult.getResult().get(i).getPrUnit());
                    mdn.setShpTiaoMa(productResult.getResult().get(i).getPrBarcode());
                    mdn.setCfWenCeng("常温");
                    mdn.setJiZhunwendu("1");
                    mdn.setTiJiCm("1");
                    mdn.setZhlKg("1");
                    mdn.setChlKongZhi("N");
                    mdn.setJfShpLei("10");
                    mdn.setMpCengGao("99");
                    mdn.setMpDanCeng("99");
                    if("进口".equals(productResult.getResult().get(i).getPrPlace())){
                        try{
                            if(StringUtil.isNotEmpty(mdn.getBzhiQi())){
                                int  bzhiq = Integer.parseInt(mdn.getBzhiQi());
                                mdn.setZhlKgm(Integer.toString(bzhiq/2));
                            }
                        }catch (Exception e){

                        }

                    }else{
                        try{
                            if(StringUtil.isNotEmpty(mdn.getBzhiQi())){
                                int  bzhiq = Integer.parseInt(mdn.getBzhiQi());
                                mdn.setZhlKgm(Integer.toString(bzhiq/3*2));
                            }
                        }catch (Exception e){

                        }
                    }

                    MdGoodsEntity mdGoods = systemService.findUniqueByProperty(
                            MdGoodsEntity.class, "shpBianMa", ShpBianMa);
                    if(mdGoods==null){
                        mdGoods = new MdGoodsEntity();
                        MyBeanUtils.copyBeanNotNull2Bean(mdn,mdGoods);
                        systemService.save(mdGoods);
                    }else{
                        MyBeanUtils.copyBeanNotNull2Bean(mdn,mdGoods);
                        systemService.updateEntitie(mdGoods);
                    }
                }
            }
        }catch (Exception e){

        }

    }

    public static void   getCus(String formDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("formDate",formDate);
        try{
            customerResult customerResult =uasUtil.getCustomer(paramMap);
            SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);

            if(customerResult.getResult().size()>0){
                for(int i=0;i< customerResult.getResult().size();i++){
                    MdCusOtherEntity mdn = new MdCusOtherEntity();
                    String kehubianma=customerResult.getResult().get(i).getCuCode();
                    MdCusOtherEntity mdcusother = systemService.findUniqueByProperty(
                            MdCusOtherEntity.class, "keHuBianMa", kehubianma);
                    mdn.setSuoShuKeHu(ResourceUtil.getConfigByName("uas.cuscode"));
                    mdn.setKeHuBianMa(kehubianma);
                    mdn.setZhongWenQch(customerResult.getResult().get(i).getCuArname());
                    if(mdcusother==null){
                        mdcusother = new MdCusOtherEntity();
                        MyBeanUtils.copyBeanNotNull2Bean(mdn,mdcusother);
                        systemService.save(mdcusother);
                    }else{
                        MyBeanUtils.copyBeanNotNull2Bean(mdn,mdcusother);
                        systemService.updateEntitie(mdcusother);
                    }
                }
            }
        }catch (Exception e){

        }

    }
    public static void   getSup(String formDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("formDate",formDate);
        try{
            vendorResult vendorResult =uasUtil.getVendor(paramMap);
            SystemService systemService =ApplicationContextUtil.getContext().getBean(SystemService.class);

            if(vendorResult.getResult().size()>0){
                for(int i=0;i< vendorResult.getResult().size();i++){
                    MdSupEntity mdn = new MdSupEntity();
                    String gysBianMa=vendorResult.getResult().get(i).getVeApvendcode();
                    MdSupEntity mdsup = systemService.findUniqueByProperty(
                            MdSupEntity.class, "gysBianMa", gysBianMa);
                    mdn.setGysBianMa(gysBianMa);
                    mdn.setZhongWenQch(vendorResult.getResult().get(i).getVeName());
                    if(mdsup==null){
                        mdsup = new MdSupEntity();
                        MyBeanUtils.copyBeanNotNull2Bean(mdn,mdsup);
                        systemService.save(mdsup);
                    }else{
                        MyBeanUtils.copyBeanNotNull2Bean(mdn,mdsup);
                        systemService.updateEntitie(mdsup);
                    }
                }
            }
        }catch (Exception e){

        }
    }
    public static void   getWarehouse(String formDate) {

    }

    public static billResult   getBillin(Map<String, Object> paramMap) {
        return uasUtil.getBil(paramMap);
    }
    public static sdresult   getsdBillin(Map<String, Object> paramMap) {
        return uasUtil.getsdBil(paramMap);
    }
    public static resResult postBill(String data,String master){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("data",data);
        paramMap.put("master",master);
        return uasUtil.postBill(paramMap);
    }
    }
