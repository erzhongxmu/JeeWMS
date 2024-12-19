package com.base.modules.jeeerp.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeeerp.entity.*;
import com.base.modules.jeeerp.mapper.BusiPoItemMapper;
import com.base.modules.jeeerp.service.IBaseCraftService;
import com.base.modules.jeeerp.service.IBusiPoService;
import com.base.modules.jeeerp.vo.BusiOmPage;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.MdSup;
import com.base.modules.jeewms.service.IMdGoodsService;
import com.base.modules.jeewms.service.IMdSupService;
import com.base.modules.util.GenerateCodeUtil;
import com.base.modules.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Description: base_craft
* @Author: base-boot
* @Date:   2022-11-19
* @Version: V1.0
*/
@Api(tags="导入")
@RestController
@RequestMapping("/jeeerp/Import")
@Slf4j
public class ImportController extends BaseController<BaseCraft, IBaseCraftService> {
    @Autowired
    private IBusiPoService busiPoService;

    @Autowired
    private GenerateCodeUtil generateCodeUtil;

    @Autowired
    private BusiPoItemMapper busiPoItemMapper;
    @Autowired
    private IMdSupService mdSupService;
    @Autowired
    private IMdGoodsService mdGoodsService;
    /**
     * 打样合同 通过excel导入数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/SampleMakingOrderImportExcel", method = RequestMethod.POST)
    public Result<?> SampleMakingOrderImportExcel(HttpServletRequest request, HttpServletResponse response) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
//            params.setTitleRows(2);
//            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<ExportSampleMakingOrder> list = ExcelImportUtil.importExcel(file.getInputStream(), ExportSampleMakingOrder.class, params);
                List<ExportSampleMakingOrder> list2 = null;
                try {
                    list2 = list.stream()
                            .collect(Collectors.collectingAndThen(
                                    Collectors.toCollection(() -> new TreeSet<>(
                                            Comparator.comparing(o -> o.getQuery13()))), ArrayList::new));
                } catch (Exception e) {

                }
                List<BusiPo> busiPoList = new ArrayList<>();
                for (ExportSampleMakingOrder e : list) {
                    try {
                        if(StringUtil.isBlankOrNull(e.getQuery08())){
                            return Result.OK("供应商不能为空" );
                        }else{
                            QueryWrapper<MdSup> MdSupqueryWrapper = new QueryWrapper<>();
                            MdSupqueryWrapper.lambda().eq(MdSup::getGysBianMa,e.getQuery08());
                            MdSup one = mdSupService.getOne(MdSupqueryWrapper, false);
                            if(one==null){
                                return Result.OK(e.getQuery08()+"供应商不存在" );
                            }
                        }
                        if(StringUtil.isBlankOrNull(e.getQuery10())){
                            return Result.OK("商品编码不能为空"  );
                        }else{
                            QueryWrapper<MdGoods> MdGoodsqueryWrapper =  new QueryWrapper<>();
                            MdGoodsqueryWrapper.lambda().eq(MdGoods::getShpBianMa,e.getQuery10());
                            MdGoods one1 = mdGoodsService.getOne(MdGoodsqueryWrapper, false);
                            if(one1==null){
                                return Result.OK(e.getQuery10()+"商品编码不存在" );
                            }
                        }

                        //商品总价格
                        e.setNum05(e.getNum01()*e.getNum04());
                        //商品含税单价
                        e.setNum06(e.getNum04()+e.getNum10()*e.getNum04());
                        //商品含税总价格
                        e.setNum07(e.getNum01()*e.getNum06());
                    } catch (Exception ex) {

                    }
                }
                try {
                    for (ExportSampleMakingOrder eqc : list2) {
                        //总金额
                        Double sumNum08 = 0.0;
                        //含税总金额
                        Double sumNum09 = 0.0;
                        //尾款
                        Double sumNum12 = 0.0;
                        //含税尾款
                        Double sumNum11 = 0.0;

                        for (ExportSampleMakingOrder e : list) {
                          if(eqc.getQuery13().equals(e.getQuery13())){
                              sumNum08+=e.getNum05();
                              sumNum09+=e.getNum06();
                              sumNum12+=e.getNum05();
                              sumNum11+=e.getNum06();
                          }

                        }
                        eqc.setNum08(sumNum08);
                        eqc.setNum09(sumNum09);
                        eqc.setNum12(sumNum12);
                        eqc.setNum11(sumNum11);
                    }
                } catch (Exception e) {

                }
                for (ExportSampleMakingOrder eqc : list2) {
                    String code = generateCodeUtil.generateCode("busi_po","YP");
                    String code2 = generateCodeUtil.generateCode("busi_po_item","DYJD");
                    int index = 0;
                    for (ExportSampleMakingOrder e : list) {
                        if(eqc.getQuery13().equals(e.getQuery13())){
                            index++;
                            e.setNum08(eqc.getNum08());
                            e.setNum09(eqc.getNum09());
                            e.setNum12(eqc.getNum12());
                            e.setNum11(eqc.getNum11());
                            BusiPo busiPo = new BusiPo();
                            BeanUtils.copyProperties(e, busiPo);
                            busiPo.setQuery01("YP");
                            busiPo.setNum02(busiPo.getNum01());
                            busiPo.setNum03(new Double(0));
                            busiPo.setQuery04(code);
                            busiPo.setQuery02("计划中");
                            String serial = busiPo.getQuery04()+ "-" +(index*10);
                            busiPo.setQuery23(serial);
                            busiPoList.add(busiPo);

                            // 添加默认的打样进度
                            BusiPoItem busiPoItem = new BusiPoItem();
                            BeanUtils.copyProperties(busiPo, busiPoItem);
                            busiPoItem.setId("");
                            busiPoItem.setCreateBy("");
                            busiPoItem.setCreateName("");
                            busiPoItem.setCreateTime(null);
                            busiPoItem.setUpdateBy("");
                            busiPoItem.setUpdateName("");
                            busiPoItem.setUpdateTime(null);
                            busiPoItem.setQuery04(code2);
                            busiPoItem.setQuery01("DYJD");
                            busiPoItem.setQuery02("");
                            busiPoItem.setLink01("样品单");
                            busiPoItem.setLink02(serial);
                            busiPoItem.setLink02(serial);
                            busiPoItem.setText01("");
                            busiPoItem.setText02("");
                            busiPoItemMapper.insert(busiPoItem);
                        }
                    }
                }

                busiPoService.saveBatch(busiPoList);
                return Result.OK("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.OK("文件导入失败！");
    }

    /**
     * 打样合同 通过excel导入数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/SampleMakingOrderImportExcel2", method = RequestMethod.POST)
    public Result<?> SampleMakingOrderImportExcel2(HttpServletRequest request, HttpServletResponse response) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
//            params.setTitleRows(2);
//            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<ExportSampleMakingOrder> list = ExcelImportUtil.importExcel(file.getInputStream(), ExportSampleMakingOrder.class, params);
                List<ExportSampleMakingOrder> list2 = list.stream()
                        .collect(Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(
                                        Comparator.comparing(o -> o.getQuery13()))), ArrayList::new));
                List<BusiPo> busiPoList = new ArrayList<>();
                try {
                    for (ExportSampleMakingOrder e : list) {

                        if(StringUtil.isBlankOrNull(e.getQuery08())){
                            return Result.OK("供应商不能为空" );
                        }else{
                            QueryWrapper<MdSup> MdSupqueryWrapper = new QueryWrapper<>();
                            MdSupqueryWrapper.lambda().eq(MdSup::getGysBianMa,e.getQuery08());
                            MdSup one = mdSupService.getOne(MdSupqueryWrapper, false);
                            if(one==null){
                                return Result.OK(e.getQuery08()+"供应商不存在" );
                            }
                        }
                        if(StringUtil.isBlankOrNull(e.getQuery10())){
                            return Result.OK("商品编码不能为空"  );
                        }else{
                            QueryWrapper<MdGoods> MdGoodsqueryWrapper =  new QueryWrapper<>();
                            MdGoodsqueryWrapper.lambda().eq(MdGoods::getShpBianMa,e.getQuery10());
                            MdGoods one1 = mdGoodsService.getOne(MdGoodsqueryWrapper, false);
                            if(one1==null){
                                return Result.OK(e.getQuery10()+"商品编码不存在" );
                            }
                        }
                        //商品总价格
                        e.setNum05(e.getNum01()*e.getNum04());
                        //商品含税单价
                        e.setNum06(e.getNum04()+e.getNum10()*e.getNum04());
                        //商品含税总价格
                        e.setNum07(e.getNum01()*e.getNum06());
                    }
                } catch (Exception e) {

                }
                for (ExportSampleMakingOrder eqc : list2) {
                    //总金额
                    Double sumNum08 = 0.0;
                    //含税总金额
                    Double sumNum09 = 0.0;
                    //尾款
                    Double sumNum12 = 0.0;
                    //含税尾款
                    Double sumNum11 = 0.0;
                    for (ExportSampleMakingOrder e : list) {
                        if(eqc.getQuery13().equals(e.getQuery13())){
                            sumNum08+=e.getNum05();
                            sumNum09+=e.getNum06();
                            sumNum12+=e.getNum05();
                            sumNum11+=e.getNum06();
                        }
                    }
                    eqc.setNum08(sumNum08);
                    eqc.setNum09(sumNum09);
                    eqc.setNum12(sumNum12);
                    eqc.setNum11(sumNum11);
                }
                for (ExportSampleMakingOrder eqc : list2) {
                    String code = generateCodeUtil.generateCode("busi_po","YP");
                    int index = 0;
                    for (ExportSampleMakingOrder e : list) {
                        if(eqc.getQuery13().equals(e.getQuery13())){
                            index++;
                            e.setNum08(eqc.getNum08());
                            e.setNum09(eqc.getNum09());
                            e.setNum12(eqc.getNum12());
                            e.setNum11(eqc.getNum11());
                            BusiPo busiPo = new BusiPo();
                            BeanUtils.copyProperties(e, busiPo);
                            busiPo.setQuery01("CGD");
                            busiPo.setNum02(busiPo.getNum01());
                            busiPo.setNum03(new Double(0));
                            busiPo.setQuery02("计划中");
                            busiPo.setQuery04(code);
                            busiPo.setQuery23(busiPo.getQuery04()+ "-" +(index*10));
                            busiPoList.add(busiPo);
                        }
                    }
                }
                busiPoService.saveBatch(busiPoList);
                return Result.OK("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.OK("文件导入失败！");
    }


    /**
     * 导出excel
     *
     * @param request
     */
    @RequestMapping(value = "/SampleMakingOrderExportXls")
    public ModelAndView exportXls(HttpServletRequest request, BusiPo busiPo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // Step.4 AutoPoi 导出Excel
        List<ExportSampleMakingOrder> exportSampleMakingOrderL = new ArrayList();
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "busi_po列表");
        mv.addObject(NormalExcelConstants.CLASS, ExportSampleMakingOrder.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("busi_po数据", "导出人:"+sysUser.getRealname(), "busi_po"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportSampleMakingOrderL);
        return mv;
    }

}
