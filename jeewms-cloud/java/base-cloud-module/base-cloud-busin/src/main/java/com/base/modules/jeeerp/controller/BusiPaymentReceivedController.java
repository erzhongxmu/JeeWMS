package com.base.modules.jeeerp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import cn.hutool.core.collection.CollectionUtil;
import com.base.common.api.vo.Result;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeeerp.api.ApiPostPage;
import com.base.modules.jeeerp.api.entity.VOmsPoWq;
import com.base.modules.jeeerp.entity.*;
import com.base.modules.jeeerp.mapper.BusiPoMapper;
import com.base.modules.jeeerp.service.IBusiPaymentReceivedService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeeerp.service.IBusiPoService;
import com.base.modules.jeeerp.service.IBusiPrdOrdService;
import com.base.modules.jeeerp.service.IConfErpService;
import com.base.modules.jeeerp.vo.BusiOmPage;
import com.base.modules.jeeerp.vo.BusiPoPage;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.service.IMdCusService;
import com.base.modules.util.GenerateCodeUtil;
import com.base.modules.util.PltnSetState;
import lombok.extern.slf4j.Slf4j;

import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseRightShift;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import com.base.common.system.base.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

import static cn.hutool.core.date.DateTime.now;

/**
 * @Description: busi_payment_received
 * @Author: base-boot
 * @Date: 2022-11-19
 * @Version: V1.0
 */
@Api(tags = "busi_payment_received")
@RestController
@RequestMapping("/jeeerp/busiPaymentReceived")
@Slf4j
public class BusiPaymentReceivedController extends BaseController<BusiPaymentReceived, IBusiPaymentReceivedService> {
    @Autowired
    private IBusiPaymentReceivedService busiPaymentReceivedService;
    @Autowired
    private GenerateCodeUtil generateCodeUtil;
    @Autowired
    private IConfErpService confErpService;

    @Autowired
    private IBusiPoService busiPoService;

    @Autowired
    private IMdCusService mdCusService;

    /**
     * 分页列表查询
     *
     * @param busiPaymentReceived
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "busi_payment_received-分页列表查询")
    @ApiOperation(value = "busi_payment_received-分页列表查询", notes = "busi_payment_received-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(BusiPaymentReceived busiPaymentReceived,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<BusiPaymentReceived> queryWrapper = QueryGenerator.initQueryWrapper(busiPaymentReceived, req.getParameterMap());
        Page<BusiPaymentReceived> page = new Page<BusiPaymentReceived>(pageNo, pageSize);
        IPage<BusiPaymentReceived> pageList = busiPaymentReceivedService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param busiPaymentReceived
     * @return
     */
    @AutoLog(value = "busi_payment_received-添加")
    @ApiOperation(value = "busi_payment_received-添加", notes = "busi_payment_received-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody BusiPaymentReceived busiPaymentReceived) {
        busiPaymentReceivedService.save(busiPaymentReceived);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param busiPaymentReceived
     * @return
     */
    @AutoLog(value = "busi_payment_received-编辑")
    @ApiOperation(value = "busi_payment_received-编辑", notes = "busi_payment_received-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody BusiPaymentReceived busiPaymentReceived) {
        busiPaymentReceivedService.updateById(busiPaymentReceived);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "busi_payment_received-通过id删除")
    @ApiOperation(value = "busi_payment_received-通过id删除", notes = "busi_payment_received-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        BusiPaymentReceived byId = busiPaymentReceivedService.getById(id);
        QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04, byId.getQuery04());
        List<BusiPaymentReceived> list = busiPaymentReceivedService.list(queryWrapper);
        for (BusiPaymentReceived busiPaymentReceived : list) {
            busiPaymentReceivedService.removeById(busiPaymentReceived.getId());
        }
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "busi_payment_received-批量删除")
    @ApiOperation(value = "busi_payment_received-批量删除", notes = "busi_payment_received-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            BusiPaymentReceived byId = busiPaymentReceivedService.getById(s);
            QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04, byId.getQuery04());
            List<BusiPaymentReceived> list = busiPaymentReceivedService.list(queryWrapper);
            for (BusiPaymentReceived busiPaymentReceived : list) {
                busiPaymentReceivedService.removeById(busiPaymentReceived.getId());
            }
        }
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "busi_payment_received-通过id查询")
    @ApiOperation(value = "busi_payment_received-通过id查询", notes = "busi_payment_received-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        BusiPaymentReceived busiPaymentReceived = busiPaymentReceivedService.getById(id);
        if (busiPaymentReceived == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(busiPaymentReceived);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param busiPaymentReceived
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BusiPaymentReceived busiPaymentReceived) {
        return super.exportXls(request, busiPaymentReceived, BusiPaymentReceived.class, "busi_payment_received");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, BusiPaymentReceived.class);
    }


    // TODO 新增的计划款  接口

    /**
     * 分页列表查询，按照单号分组
     *
     * @param busiPaymentReceived
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "busi_payment_received-分页列表查询")
    @ApiOperation(value = "busi_payment_received-分页列表查询", notes = "busi_payment_received-分页列表查询")
    @GetMapping(value = "/list2")
    public Result<?> queryPageList2(BusiPaymentReceived busiPaymentReceived,
                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                    HttpServletRequest req) {
        QueryWrapper<BusiPaymentReceived> queryWrapper = QueryGenerator.initQueryWrapper(busiPaymentReceived, req.getParameterMap());
        queryWrapper.lambda().groupBy(BusiPaymentReceived::getQuery04);
        Page<BusiPaymentReceived> page = new Page<BusiPaymentReceived>(pageNo, pageSize);
        IPage<BusiPaymentReceived> pageList = busiPaymentReceivedService.page(page, queryWrapper);
        for (BusiPaymentReceived record : pageList.getRecords()) {
            if (record.getNum10() == null) {
                record.setNum10(new Double(0));
            }
//            String query22 = record.getQuery22();
//            QueryWrapper<ConfErp> confErp = new QueryWrapper<>();
//            confErp.lambda().eq(ConfErp::getQuery01, "HLPZ");
//            confErp.lambda().eq(ConfErp::getQuery05, "RMB");
//            confErp.lambda().eq(ConfErp::getQuery04, query22);
//            List<ConfErp> list = confErpService.list(confErp);
//            if (CollectionUtil.isNotEmpty(list)) {
//                ConfErp confErp1 = list.get(0);
//                if (record.getNum08() != null) {
//                    Double num = record.getNum08() / Double.parseDouble(confErp1.getQuery06());
//                    num = (double) Math.round(num * 100) / 100;
//                    record.setNum14(num);
//                    Double num2 = record.getNum09() / Double.parseDouble(confErp1.getQuery06());
//                    num2 = (double) Math.round(num2 * 100) / 100;
//                    record.setNum15(num2);
//                }
//            } else {
//                record.setQuery14(record.getQuery08());
//                record.setQuery15(record.getQuery09());


//                QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
//                MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,record.getQuery24());
//                MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
//
//                record.setXingYeFenLei(one2.getXingYeFenLei());
//            }
//            QueryWrapper<BusiPaymentReceived> queryWrapper09 = new QueryWrapper<>();
//            queryWrapper09.eq("query04",record.getQuery04());
//            List<BusiPaymentReceived> list1 = busiPaymentReceivedService.list(queryWrapper09);
//            Double num09 = 0.00;
//            for (BusiPaymentReceived paymentReceived : list1) {
//                num09 = num09 + paymentReceived.getNum09();
//            }
//            record.setNum09(num09);

        }
        return Result.OK(pageList);
    }

    /**
     * 批量添加
     *
     * @param busiPaymentReceived
     * @return
     */
    @AutoLog(value = "busi_payment_received-批量添加")
    @ApiOperation(value = "busi_payment_received-批量添加", notes = "busi_payment_received-批量添加")
    @PostMapping(value = "/batchAdd")
    @Transactional
    public Result<?> add(@RequestBody List<BusiPaymentReceived> busiPaymentReceived) {
        if (CollectionUtil.isEmpty(busiPaymentReceived)) {
            return Result.error("数据为空");
        }
        int index = 0;
        BusiPaymentReceived busiPaymentReceived1 = busiPaymentReceived.get(0);
        String code = generateCodeUtil.generateCode("busi_payment_received", busiPaymentReceived1.getQuery01());
        // 修改源单据状态
//		 if(StringUtils.isNotEmpty(busiPaymentReceived1.getQuery04())){
//			 if(busiPaymentReceived1.getQuery01().equals("CGFKJH") || busiPaymentReceived1.getQuery01().equals("YPFKJH")){
//				 pltnSetState.setState("付款中",busiPaymentReceived1.getQuery01(),busiPaymentReceived1.getQuery04());
//			 }else if(busiPaymentReceived1.getQuery01().equals("XSSKJH")){
//				 pltnSetState.setState("收款中",busiPaymentReceived1.getQuery01(),busiPaymentReceived1.getQuery04());
//			 }else if(busiPaymentReceived1.getQuery01().equals("FKD")){
//				 pltnSetState.setState("已付款",busiPaymentReceived1.getQuery01(),busiPaymentReceived1.getQuery04());
//				 pltnSetState.setState("已付款","CGFKJH",busiPaymentReceived1.getLink02());
//			 }else if(busiPaymentReceived1.getQuery01().equals("SKD")){
//				 pltnSetState.setState("已收款",busiPaymentReceived1.getQuery01(),busiPaymentReceived1.getLink02());
//				 pltnSetState.setState("已收款","XSSKJH",busiPaymentReceived1.getQuery04());
//			 }
//		 }
        if (StringUtils.isNotEmpty(busiPaymentReceived1.getQuery04())) {
            if ("FKD".equals(busiPaymentReceived1.getQuery01())) {
                QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04, busiPaymentReceived1.getQuery04());
                List<BusiPaymentReceived> list = busiPaymentReceivedService.list(queryWrapper);
                if (CollectionUtil.isNotEmpty(list)) {
                    for (BusiPaymentReceived busiPrdOrd : list) {
                        busiPrdOrd.setQuery02("已付款");
                        busiPaymentReceivedService.updateById(busiPrdOrd);
                    }
                    QueryWrapper<BusiPo> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.lambda().eq(BusiPo::getQuery04, list.get(0).getLink02());
                    List<BusiPo> list1 = busiPoService.list(queryWrapper2);
                    for (BusiPo busiPo : list1) {
                        if ("CGD".equals(busiPo.getQuery01())) {
                            busiPo.setNum11(busiPo.getNum11() - list.get(0).getNum02());
                            busiPo.setNum12(busiPo.getNum12() - list.get(0).getNum03());
                        } else {
                            busiPo.setNum11(busiPo.getNum11() - list.get(0).getNum08());
                            busiPo.setNum12(busiPo.getNum12() - list.get(0).getNum09());
                        }
                        if (busiPo.getNum11() == 0 || busiPo.getNum12() == 0) {
                            busiPo.setQuery02("付款完成");
                        }
                        busiPoService.updateById(busiPo);
                    }
                }
            } else if ("SKD".equals(busiPaymentReceived1.getQuery01())) {
                QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04, busiPaymentReceived1.getQuery04());
                List<BusiPaymentReceived> list = busiPaymentReceivedService.list(queryWrapper);
                if (CollectionUtil.isNotEmpty(list)) {
                    for (BusiPaymentReceived busiPrdOrd : list) {
                        busiPrdOrd.setQuery02("已付款");
                        busiPaymentReceivedService.updateById(busiPrdOrd);
                    }
                }
            }
        }
        // 循环新增
        for (BusiPaymentReceived PaymentReceived : busiPaymentReceived) {
            index++;
            BusiPaymentReceived BusiPayment = new BusiPaymentReceived();
            BeanUtils.copyProperties(PaymentReceived, BusiPayment);
            if ("CGFKJH".equals(busiPaymentReceived1.getQuery01()) || "YPFKJH".equals(busiPaymentReceived1.getQuery01())) {
                BusiPayment.setQuery02("付款中");
                String str = "";
                // 客户编码
                QueryWrapper<MdCus> MdCusqueryWrapper = new QueryWrapper<>();
                MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa, BusiPayment.getQuery24());
                MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
                str = one2.getZhongWenQch(); // 客户名称
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                String format = simpleDateFormat.format(date);
                String[] split = format.split("-");
                str = str + " - " + split[0] + "M" + split[1];
                int index2 = 0;
                for (BusiPaymentReceived PaymentReceived2 : busiPaymentReceived) {
                    if (index2 == 0) {
                        str = str + "-" + PaymentReceived2.getNum01().toString() + " " + PaymentReceived2.getQuery11();
                    } else {
                        str = str + "/" + PaymentReceived2.getNum01().toString() + " " + PaymentReceived2.getQuery11();
                    }
                    index2++;
                }
                BusiPayment.setText01(str);
            } else if ("XSSKJH".equals(busiPaymentReceived1.getQuery01()) || "YPSKJH".equals(busiPaymentReceived1.getQuery01())) {
                BusiPayment.setQuery02("收款中");
            } else if ("SKD".equals(busiPaymentReceived1.getQuery01())) {
                BusiPayment.setQuery02("已收款");
            } else if ("FKD".equals(busiPaymentReceived1.getQuery01())) {
                BusiPayment.setQuery02("已付款");
            } else {
                BusiPayment.setQuery02("计划中");
            }
            BusiPayment.setQuery04(code);
            String serial = code + (index * 10);
            BusiPayment.setQuery23(serial);
            BusiPayment.setNum11(BusiPayment.getNum15());
            BusiPayment.setNum12(BusiPayment.getNum14());
            BusiPayment.setId("");
            BusiPayment.setCreateBy(null);
            BusiPayment.setCreateName(null);
            BusiPayment.setCreateTime(null);
            BusiPayment.setUpdateBy(null);
            BusiPayment.setUpdateName(null);
            BusiPayment.setUpdateTime(null);
            if (BusiPayment.getNum07() < 0) {
                BusiPayment.setLink02(busiPaymentReceived1.getLink02());
            } else {
                BusiPayment.setLink02(busiPaymentReceived1.getQuery04());
            }
            busiPaymentReceivedService.save(BusiPayment);
        }
        return Result.OK("添加成功！");
    }

    /**
     * 批量添加
     *
     * @param busiPaymentReceived
     * @return
     */
    @AutoLog(value = "busi_payment_received-批量编辑")
    @ApiOperation(value = "busi_payment_received-批量编辑", notes = "busi_payment_received-批量编辑")
    @PutMapping(value = "/batchEdit")
    @Transactional
    public Result<?> batchEdit(@RequestBody List<BusiPaymentReceived> busiPaymentReceived) {
        // 查询使用单号查询
        QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04, busiPaymentReceived.get(0).getQuery04());
        List<BusiPaymentReceived> busiPaymentRece = busiPaymentReceivedService.list(queryWrapper);
        for (BusiPaymentReceived busiPaymentReceItem : busiPaymentRece) {
            busiPaymentReceivedService.removeById(busiPaymentReceItem.getId());
        }
        // 重新插入数据
        int index = 0;
        for (BusiPaymentReceived PaymentPage : busiPaymentReceived) {
            index++;
            BusiPaymentReceived busiOm = new BusiPaymentReceived();
            BeanUtils.copyProperties(PaymentPage, busiOm);
            busiOm.setId("");
            busiPaymentReceivedService.save(busiOm);
        }
        return Result.OK("编辑成功!");
    }

    /**
     * 批量修改状态
     *
     * @param busiPaymentReceived
     * @return
     */
    @AutoLog(value = "busi_payment_received-批量编辑")
    @ApiOperation(value = "busi_payment_received-批量编辑", notes = "busi_payment_received-批量编辑")
    @PutMapping(value = "/EditQuery02")
    @Transactional
    public Result<?> EditQuery02(@RequestBody BusiPaymentReceived busiPaymentReceived) {
        // 查询使用单号查询
        QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04, busiPaymentReceived.getQuery04());
        List<BusiPaymentReceived> busiPaymentRece = busiPaymentReceivedService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(busiPaymentRece)) {
            for (BusiPaymentReceived busiPaymentReceItem : busiPaymentRece) {
                busiPaymentReceItem.setQuery02(busiPaymentReceived.getQuery02());
                busiPaymentReceivedService.updateById(busiPaymentReceItem);
            }
        } else {
            return Result.error("未查到单据，请确认！");
        }
        return Result.OK("修改成功!");
    }


    /**
     * 批量修改状态
     *
     * @param busiPaymentReceived
     * @return
     */
    @AutoLog(value = "busi_payment_received-批量编辑")
    @ApiOperation(value = "busi_payment_received-批量编辑", notes = "busi_payment_received-批量编辑")
    @PutMapping(value = "/EditQuery19")
    @Transactional
    public Result<?> EditQuery19(@RequestBody BusiPaymentReceived busiPaymentReceived) {
//        // 查询使用单号查询
        String query04 = busiPaymentReceived.getQuery04();
        String[] split = query04.split(",");
        for (String s : split) {
            QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04, s);
            List<BusiPaymentReceived> busiPaymentRece = busiPaymentReceivedService.list(queryWrapper);
            if (CollectionUtil.isNotEmpty(busiPaymentRece)) {
                for (BusiPaymentReceived busiPaymentReceItem : busiPaymentRece) {
                    busiPaymentReceItem.setQuery19(busiPaymentReceived.getQuery19());
                    busiPaymentReceivedService.updateById(busiPaymentReceItem);
                }
            }
        }
        return Result.OK("修改成功!");
    }

    /**
     * 上传水单
     *
     * @param busiPaymentReceived
     * @return
     */
    @AutoLog(value = "busi_payment_received-上传水单")
    @ApiOperation(value = "busi_payment_received-上传水单", notes = "busi_payment_received-上传水单")
    @PutMapping(value = "/uploadingBankReceipt")
    @Transactional
    public Result<?> uploadingBankReceipt(@RequestBody BusiPaymentReceived busiPaymentReceived) {
        // 查询使用单号查询
        QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04, busiPaymentReceived.getQuery04());
        List<BusiPaymentReceived> busiPaymentRece = busiPaymentReceivedService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(busiPaymentRece)) {
            for (BusiPaymentReceived busiPaymentReceItem : busiPaymentRece) {
                busiPaymentReceItem.setAttr4(busiPaymentReceived.getAttr4());
                busiPaymentReceivedService.updateById(busiPaymentReceItem);
            }
        } else {
            return Result.error("未查到单据，请确认！");
        }
        return Result.OK("修改成功!");
    }


    @AutoLog(value = "WEB提交记录")
    @ApiOperation(value = "WEB提交记录", notes = "WEB提交记录")
    @PostMapping(value = "/postdata")
    public Result<?> postdata(@RequestBody ApiPostPage apiPostPage,
                              HttpServletRequest req) throws Exception {
        LoginUser login = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<VOmsPoWq> list01 = apiPostPage.getList01();
        String postType = apiPostPage.getPostType();
        for (VOmsPoWq vOmsPoWq : list01) {
            String code = generateCodeUtil.generateCode("busi_payment_received", postType);
            BusiPaymentReceived busiPaymentReceived = new BusiPaymentReceived();
            BeanUtils.copyProperties(vOmsPoWq, busiPaymentReceived);
            busiPaymentReceived.setQuery01(postType);
            busiPaymentReceived.setQuery03(now());
            busiPaymentReceived.setId(null);
            busiPaymentReceived.setCreateTime(now());
            busiPaymentReceived.setCreateBy(login.getUsername());
            busiPaymentReceived.setCreateName(login.getRealname());
            busiPaymentReceived.setUpdateBy(null);
            busiPaymentReceived.setUpdateName(null);
            busiPaymentReceived.setUpdateTime(null);
            busiPaymentReceived.setQuery04(code);
            busiPaymentReceived.setAttr1("");
            busiPaymentReceived.setAttr2("");
            busiPaymentReceived.setLink02(vOmsPoWq.getQuery04());
            QueryWrapper<MdCus> MdCusqueryWrapper = new QueryWrapper<>();
            MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa, vOmsPoWq.getQuery24());
            MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
            busiPaymentReceived.setQuery31(one2.getXingYeFenLei());

            if ("SKD".equals(postType)) {
                busiPaymentReceived.setQuery02("已收款");
            }
            if ("FKD".equals(postType)) {
                if (busiPaymentReceived.getNum08() == 0) {
                    busiPaymentReceived.setQuery02("已付全款");
                } else {
                    busiPaymentReceived.setQuery02("已付款");
                }
            }
            busiPaymentReceivedService.save(busiPaymentReceived);
            try {
                changeStatus(postType, vOmsPoWq);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Result.OK("提交成功");
    }


    void changeStatus(String postType, VOmsPoWq vOmsPoWq) {

        if ("FKD".equals(postType)) {
            // 查询本次付款的所有同一计划的付款单
            QueryWrapper<BusiPaymentReceived> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(BusiPaymentReceived::getLink02, vOmsPoWq.getQuery04());
            List<BusiPaymentReceived> list = busiPaymentReceivedService.list(queryWrapper2);
            double num = 0;
            for (BusiPaymentReceived busiPaymentReceived : list) {
                num += busiPaymentReceived.getNum07();
            }

            // 查询本次付款的付款计划
            QueryWrapper<BusiPaymentReceived> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.lambda().eq(BusiPaymentReceived::getQuery04, vOmsPoWq.getQuery04());
            List<BusiPaymentReceived> list2 = busiPaymentReceivedService.list(queryWrapper3);
            for (BusiPaymentReceived byId : list2) {
                byId.setQuery02("已付款");
                byId.setNum11(num);
                byId.setNum08(byId.getNum08() - vOmsPoWq.getNum07());
                busiPaymentReceivedService.updateById(byId);
            }
            // 查询使用单号查询
            QueryWrapper<BusiPo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BusiPo::getQuery13, vOmsPoWq.getQuery13());
            List<BusiPo> busiPoList = busiPoService.list(queryWrapper);
            for (BusiPo busiPo : busiPoList) {
                if (!"已完成".equals(busiPo.getQuery02())&& !"打样已完成".equals(busiPo.getQuery02())) {
                    busiPo.setQuery02("生产中");
                    busiPoService.updateById(busiPo);
                }
            }
        }
        if ("SKD".equals(postType)) {
            BusiPaymentReceived byId = busiPaymentReceivedService.getById(vOmsPoWq.getId());
            byId.setQuery02("已收款");
            busiPaymentReceivedService.updateById(byId);
        }


    }
}
