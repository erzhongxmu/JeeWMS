package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.entity.BaStore;
import com.base.modules.jeewms.entity.gs.MdCusgs;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.service.IMdCusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 客户
 * @Author: base-boot
 * @Date: 2021-05-17
 * @Version: V1.0
 */
@Api(tags = "客户")
@RestController
@RequestMapping("/jeewms/mdCus")
@Slf4j
public class MdCusController extends JeecgController<MdCus, IMdCusService> {
    @Autowired
    private IMdCusService mdCusService;

    /**
     * 分页列表查询
     *
     * @param mdCus
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "客户-分页列表查询")
    @ApiOperation(value = "客户-分页列表查询", notes = "客户-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MdCus mdCus,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<MdCus> queryWrapper = QueryGenerator.initQueryWrapper(mdCus, req.getParameterMap());
        if(StringUtils.isNotEmpty(mdCus.getZhongWenQch())){
            queryWrapper.lambda().like(MdCus::getZhongWenQch,mdCus.getZhongWenQch());
        }
        if(StringUtils.isNotEmpty(mdCus.getKeHuBianMa())){
            queryWrapper.lambda().like(MdCus::getKeHuBianMa,mdCus.getKeHuBianMa());
        }
        Page<MdCus> page = new Page<MdCus>(pageNo, pageSize);
        IPage<MdCus> pageList = mdCusService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 通过编码查询信息
     *
     * @param keHuBianMa
     * @return
     */
    @AutoLog(value = "客户-通过编码查询信息")
    @ApiOperation(value = "客户-通过编码查询信息", notes = "客户-通过编码查询信息")
    @GetMapping(value = "/findByKeHuBianMa")
    public Result<?> findByKeHuBianMa(@RequestParam(name = "keHuBianMa", required = true) String keHuBianMa) {
        return mdCusService.findByKeHuBianMa(keHuBianMa);
    }

    /**
     * 添加
     *
     * @param mdCus
     * @return
     */
    @AutoLog(value = "客户-添加")
    @ApiOperation(value = "客户-添加", notes = "客户-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MdCus mdCus) {
        //查询编码是否已存在
        if (mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa,mdCus.getKeHuBianMa()).count() > 0) {
            throw new JeecgBootException("编码不能重复");
        }
        if (mdCusService.lambdaQuery().eq(MdCus::getZhongWenQch,mdCus.getZhongWenQch()).count() > 0) {
            throw new JeecgBootException("名称不能重复");
        }
        if (mdCusService.lambdaQuery().eq(MdCus::getKeHuJianCheng,mdCus.getKeHuJianCheng()).count() > 0) {
            throw new JeecgBootException("简称不能重复");
        }
        mdCusService.save(mdCus);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param mdCus
     * @return
     */
    @AutoLog(value = "客户-编辑")
    @ApiOperation(value = "客户-编辑", notes = "客户-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MdCus mdCus) {

        MdCus mdCuss = mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa, mdCus.getKeHuBianMa())
                .ne(MdCus::getId, mdCus.getId()).one();
        if (mdCuss != null) {
            return Result.error("编码已存在，请重新输入");
        }else {
            mdCusService.updateById(mdCus);
        }
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "客户-通过id删除")
    @ApiOperation(value = "客户-通过id删除", notes = "客户-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        mdCusService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "客户-批量删除")
    @ApiOperation(value = "客户-批量删除", notes = "客户-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.mdCusService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "客户-通过id查询")
    @ApiOperation(value = "客户-通过id查询", notes = "客户-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        MdCus mdCus = mdCusService.getById(id);
        if (mdCus == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(mdCus);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param mdCus
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdCus mdCus) {
        return super.exportXls(request, mdCus, MdCus.class, "客户");
    }


    /**
     * 导出excel
     *
     * @param request
     * @param mdCus
     */
    @RequestMapping(value = "/exportXlsgs")
    public ModelAndView exportXlsgs(HttpServletRequest request, MdCus mdCus) {
        // Step.1 组装查询条件
        QueryWrapper<MdCus> queryWrapper = QueryGenerator.initQueryWrapper(mdCus, request.getParameterMap());
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        System.err.println(loginUser);
        LoginUser sysUser = JwtUtil.getLoginUser();
        List<MdCus> pageList = mdCusService.list(queryWrapper);
        List<MdCus> exportList = null;

        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }
        List<MdCusgs> list = new ArrayList<MdCusgs>();
        for (MdCus main : exportList) {
            MdCusgs vo = new MdCusgs();
            BeanUtils.copyProperties(main, vo);
            list.add(vo);
        }
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "供应商");
        mv.addObject(NormalExcelConstants.CLASS, MdCusgs.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("供应商", "导出人:" + sysUser.getRealname(), "供应商"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
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
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<MdCus> list = ExcelImportUtil.importExcel(file.getInputStream(), MdCus.class, params);
                long start = System.currentTimeMillis();
                List<MdCus> listsave = new ArrayList<>();
                if (list.size() > 0) {
                    for(MdCus mdCus : list){
                        if(org.apache.commons.lang3.StringUtils.isEmpty(mdCus.getKeHuBianMa())){
                            continue;
                        }
                        List<MdCus> list1 = mdCusService.lambdaQuery().in(MdCus::getKeHuBianMa, mdCus.getKeHuBianMa()).list();
                        for (MdCus kw : list1) {
                            mdCusService.removeById(kw.getId());
                        }
                        listsave.add(mdCus);
                    }
                }
                mdCusService.saveBatch(listsave);
                log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
                return Result.ok("文件导入成功！数据行数：" + list.size());
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
        return Result.error("文件导入失败！");
    }

}
