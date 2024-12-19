package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.BaPartType;
import com.base.modules.jeewms.service.IBaPartTypeService;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmsUserPartType;
import com.base.modules.jeewms.service.IWmsUserPartTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 用户商品分类中间表
 * @Author: base-boot
 * @Date: 2021-06-24
 * @Version: V1.0
 */
@Api(tags = "用户商品分类中间表")
@RestController
@RequestMapping("/jeewms/wmsUserPartType")
@Slf4j
public class WmsUserPartTypeController extends JeecgController<WmsUserPartType, IWmsUserPartTypeService> {
    @Autowired
    private IWmsUserPartTypeService wmsUserPartTypeService;
    @Autowired
    private IBaPartTypeService baPartTypeService;
    /**
     * 分页列表查询
     *
     * @param wmsUserPartType
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "用户商品分类中间表-分页列表查询")
    @ApiOperation(value = "用户商品分类中间表-分页列表查询", notes = "用户商品分类中间表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WmsUserPartType wmsUserPartType,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<WmsUserPartType> queryWrapper = QueryGenerator.initQueryWrapper(wmsUserPartType, req.getParameterMap());
        Page<WmsUserPartType> page = new Page<WmsUserPartType>(pageNo, pageSize);
        IPage<WmsUserPartType> pageList = wmsUserPartTypeService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param wmsUserPartType
     * @return
     */
    @AutoLog(value = "用户商品分类中间表-添加")
    @ApiOperation(value = "用户商品分类中间表-添加", notes = "用户商品分类中间表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WmsUserPartType wmsUserPartType) {
        String userIds = wmsUserPartType.getUserId();
        String userNames = wmsUserPartType.getUserName();
        String partTypeIds = wmsUserPartType.getPartTypeId();
        if (StringUtils.isEmpty(userNames)) {
            return Result.error("请选择用户");
        }
        if (StringUtils.isEmpty(partTypeIds)) {
            return Result.error("请选择商品分类");
        }


        String[] userNameArr = userNames.split(",");
        String[] partTypeIdArr = partTypeIds.split(",");

        QueryWrapper<WmsUserPartType> queryWrapper = new QueryWrapper<>();
        for (String s : partTypeIdArr) {
            queryWrapper.or().eq("part_type_id", s);
        }
        List<WmsUserPartType> tmp = wmsUserPartTypeService.list(queryWrapper);
        if(!(tmp == null || tmp.isEmpty())){
            List<String> ids = tmp.stream().map(WmsUserPartType::getId).collect(Collectors.toList());
            wmsUserPartTypeService.removeByIds(ids);
        }
        List<WmsUserPartType> list = new ArrayList<>(userNameArr.length * partTypeIdArr.length);
        WmsUserPartType wupt = null;
        String user= "";
        for (String userName : userNameArr) {
            for (String partTypeId : partTypeIdArr) {
                wupt = new WmsUserPartType();
                wupt.setUserId(userIds);
                wupt.setUserName(userName);
                user = user + ","+ userName;
                wupt.setPartTypeId(partTypeId);

                list.add(wupt);
            }
        }
        wmsUserPartTypeService.saveBatch(list);
        BaPartType ba =baPartTypeService.getById(partTypeIds);
        ba.setAttr2(user);
        baPartTypeService.updateById(ba);

        return Result.ok("绑定成功！");
    }

    /**
     * 编辑
     *
     * @param wmsUserPartType
     * @return
     */
    @AutoLog(value = "用户商品分类中间表-编辑")
    @ApiOperation(value = "用户商品分类中间表-编辑", notes = "用户商品分类中间表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmsUserPartType wmsUserPartType) {
        wmsUserPartTypeService.updateById(wmsUserPartType);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "用户商品分类中间表-通过id删除")
    @ApiOperation(value = "用户商品分类中间表-通过id删除", notes = "用户商品分类中间表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        wmsUserPartTypeService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "用户商品分类中间表-批量删除")
    @ApiOperation(value = "用户商品分类中间表-批量删除", notes = "用户商品分类中间表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wmsUserPartTypeService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "用户商品分类中间表-通过id查询")
    @ApiOperation(value = "用户商品分类中间表-通过id查询", notes = "用户商品分类中间表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WmsUserPartType wmsUserPartType = wmsUserPartTypeService.getById(id);
        if (wmsUserPartType == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmsUserPartType);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wmsUserPartType
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsUserPartType wmsUserPartType) {
        return super.exportXls(request, wmsUserPartType, WmsUserPartType.class, "用户商品分类中间表");
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
        return super.importExcel(request, response, WmsUserPartType.class);
    }

}
