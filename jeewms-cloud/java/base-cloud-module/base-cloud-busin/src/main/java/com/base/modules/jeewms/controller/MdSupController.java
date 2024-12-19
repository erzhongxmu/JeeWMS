package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.entity.MdGoods;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.MdSup;
import com.base.modules.jeewms.service.IMdSupService;
import com.base.modules.jeewms.vo.EditMdSupVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
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

/**
 * @Description: 供应商
 * @Author: base-boot
 * @Date: 2021-05-17
 * @Version: V1.0
 */
@Api(tags = "供应商")
@RestController
@RequestMapping("/jeewms/mdSup")
@Slf4j
public class MdSupController extends JeecgController<MdSup, IMdSupService> {
    @Autowired
    private IMdSupService mdSupService;

    /**
     * 分页列表查询
     *
     * @param mdSup
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "供应商-分页列表查询")
    @ApiOperation(value = "供应商-分页列表查询", notes = "供应商-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MdSup mdSup,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<MdSup> queryWrapper = QueryGenerator.initQueryWrapper(mdSup, req.getParameterMap());
        Page<MdSup> page = new Page<MdSup>(pageNo, pageSize);
        IPage<MdSup> pageList = mdSupService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param mdSup
     * @return
     */
    @AutoLog(value = "供应商-添加")
    @ApiOperation(value = "供应商-添加", notes = "供应商-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MdSup mdSup) {
        if (mdSupService.lambdaQuery().eq(MdSup::getGysBianMa,mdSup.getGysBianMa()).count() > 0) {
            throw new JeecgBootException("编码不能重复");
        }
        if (mdSupService.lambdaQuery().eq(MdSup::getGysJianCheng,mdSup.getGysJianCheng()).count() > 0) {
            throw new JeecgBootException("简称不能重复");
        }
        mdSupService.save(mdSup);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param mdSup
     * @return
     */
    @AutoLog(value = "供应商-编辑")
    @ApiOperation(value = "供应商-编辑", notes = "供应商-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MdSup mdSup) {
        MdSup mdSups = mdSupService.lambdaQuery()
                .eq(MdSup::getGysBianMa, mdSup.getGysBianMa())
                .ne(MdSup::getId, mdSup.getId()).one();

        MdSup mdSupss = mdSupService.lambdaQuery()
                .eq(MdSup::getGysJianCheng, mdSup.getGysJianCheng())
                .ne(MdSup::getId, mdSup.getId()).one();
        if (mdSups != null) {
            return Result.error("编码已存在，请重新输入");
        }else if (mdSupss != null){
            return Result.error("简称已存在，请重新输入");
        }else {
            mdSupService.updateById(mdSup);
        }
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "供应商-通过id删除")
    @ApiOperation(value = "供应商-通过id删除", notes = "供应商-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        mdSupService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "供应商-批量删除")
    @ApiOperation(value = "供应商-批量删除", notes = "供应商-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.mdSupService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "供应商-通过id查询")
    @ApiOperation(value = "供应商-通过id查询", notes = "供应商-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        MdSup mdSup = mdSupService.getById(id);
        if (mdSup == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(mdSup);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param mdSup
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdSup mdSup) {
        return super.exportXls(request, mdSup, MdSup.class, "供应商");
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
                List<MdSup> list = ExcelImportUtil.importExcel(file.getInputStream(), MdSup.class, params);
                long start = System.currentTimeMillis();
                List<MdSup> listsave = new ArrayList<>();
                if (list.size() > 0) {
                    for(MdSup mdSup : list){
                        if(org.apache.commons.lang3.StringUtils.isEmpty(mdSup.getGysBianMa())){
                            continue;
                        }
                        List<MdSup> list1 = mdSupService.lambdaQuery().in(MdSup::getGysBianMa, mdSup.getGysBianMa()).list();
                        for (MdSup kw : list1) {
                            mdSupService.removeById(kw.getId());
                        }
                        listsave.add(mdSup);
                    }
                }
                mdSupService.saveBatch(listsave);
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

    /**
     * @Describe : 供应商审核编辑
     * @Author: zly
     * @Create Date: 2021-05-18
     */
    @AutoLog(value = "供应商-编辑审核")
    @ApiOperation(value = "供应商-编辑审核", notes = "供应商-编辑审核")
    @PutMapping(value = "/editStatus")
    public Result<?> editStatus(@RequestBody EditMdSupVo vo) {
        Result<?> resuit = mdSupService.editStatus(vo);
        return resuit;
    }

}
