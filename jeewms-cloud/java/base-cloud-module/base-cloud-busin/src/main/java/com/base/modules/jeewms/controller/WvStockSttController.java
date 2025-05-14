package com.base.modules.jeewms.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.util.DateUtils;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.WvStockMapper;
import com.base.modules.jeewms.service.*;
import com.base.modules.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.base.controller.JeecgController;;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: wv_stock_stt
 * @Author: base-boot
 * @Date: 2021-05-26
 * @Version: V1.0
 */
@Api(tags = "wv_stock_stt")
@RestController
@RequestMapping("/jeewms/wvStockStt")
@Slf4j
public class WvStockSttController extends JeecgController<WvStockStt, IWvStockSttService> {
    @Autowired
    private IWvStockSttService wvStockSttService;
    @Autowired
    private IBaKwService baKwService;
    @Autowired
    private IWmToUpGoodsService wmToUpGoodsService;

    @Autowired
    private IWmToDownGoodsService wmToDownGoodsService;


    @Autowired
    private IMdCusService mdCusService;

    @Value("${jeecg.pritIp}")
    private String pritIp;

    @Value("${jeecg.pritIp2}")
    private String pritIp2;

    @Autowired
    private IBaUnitService baUnitService;

    @Autowired
    private IWmInQmIService wmInQmIService;

    @Autowired
    private IWmImNoticeIService wmImNoticeIService;

    @Autowired
    private IWmToMoveGoodsService wmToMoveGoodsService;

    @Autowired
    private WvStockMapper wvStockMapper;

    @Autowired
    private IFxWmService fxWmService;

    @Autowired
    private IFxWmsKucunService fxWmsKucunService;

    /**
     * 分页列表查询
     *
     * @param wvStockStt
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "动碰盘-分页列表查询", notes = "动碰盘-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WvStockStt wvStockStt,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        FxWmsKucun fxWmsKucun = new FxWmsKucun();
        BeanUtil.copyProperties(wvStockStt,fxWmsKucun);
        /*QueryWrapper<WvStockStt> queryWrapper = QueryGenerator.initQueryWrapper(wvStockStt, req.getParameterMap());
        queryWrapper.eq("kuctype", "库存");
        queryWrapper.ne("goods_qua", "0");
        Page<WvStockStt> page = new Page<WvStockStt>(pageNo, pageSize);
        IPage<WvStockStt> pageList = wvStockSttService.page(page, queryWrapper);*/
        QueryWrapper<FxWmsKucun> queryWrapper = QueryGenerator.initQueryWrapper(fxWmsKucun, req.getParameterMap());
        Page<FxWmsKucun> page = new Page<FxWmsKucun>(pageNo, pageSize);
        queryWrapper.gt("goods_qua",0);
        IPage<FxWmsKucun> pageList = fxWmsKucunService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 分页列表查询
     *
     * @param wvStockStt
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "综合盘点-分页列表查询", notes = "综合盘点-分页列表查询")
    @GetMapping(value = "/zhList")
    public Result<?> queryPageZhList(WvStockStt wvStockStt,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                     HttpServletRequest req) {
        /*QueryWrapper<WvStockStt> queryWrapper = QueryGenerator.initQueryWrapper(wvStockStt, req.getParameterMap());
        queryWrapper.eq("kuctype", "库存");
        Page<WvStockStt> page = new Page<WvStockStt>(pageNo, pageSize);
        IPage<WvStockStt> resultPage = wvStockSttService.getZhList(wvStockStt, queryWrapper, page, req);
        resultPage.getRecords().forEach(item -> {
            BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, item.getBinId()).one();
            if (baKw != null) {
                item.setAreaCode(baKw.getStoreAreaCode());
            }
        });*/
        FxWmsKucun fxWmsKucun = new FxWmsKucun();
        BeanUtil.copyProperties(wvStockStt,fxWmsKucun);
        QueryWrapper<FxWmsKucun> queryWrapper = QueryGenerator.initQueryWrapper(fxWmsKucun, req.getParameterMap());
        queryWrapper.eq("kuctype", "库存");
        queryWrapper.gt("goods_qua", 0);
        Page<FxWmsKucun> page = new Page<FxWmsKucun>(pageNo, pageSize);
        IPage<FxWmsKucun> resultPage = fxWmsKucunService.getZhList(fxWmsKucun, queryWrapper, page, req);
        /*resultPage.getRecords().forEach(item -> {
            BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, item.getBinId()).one();
            if (baKw != null) {
                item.setAreaCode(baKw.getStoreAreaCode());
            }
        });*/
        return Result.ok(resultPage);
    }

    /**
     * 分页列表查询
     *
     * @param wvStockStt
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "复盘-分页列表查询", notes = "复盘-分页列表查询")
    @GetMapping(value = "/fpList")
    public Result<?> queryPageFpList(WvStockStt wvStockStt,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                     HttpServletRequest req) {
        QueryWrapper<WvStockStt> queryWrapper = QueryGenerator.initQueryWrapper(wvStockStt, req.getParameterMap());
        Page<WvStockStt> page = new Page<WvStockStt>(pageNo, pageSize);
        IPage<WvStockStt> pageList = wvStockSttService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "wv_stock_stt-通过id查询", notes = "wv_stock_stt-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WvStockStt wvStockStt = wvStockSttService.getById(id);
        if (wvStockStt == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wvStockStt);
    }

    /**
     * 生成
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "生成明盘", notes = "生成明盘")
    @PostMapping(value = "/generate")
    public Result<?> generate(@RequestBody List<String> id) {
        wvStockSttService.generate(id, "1");
        return Result.ok("生成成功");
    }

    @ApiOperation(value = "生成暗盘", notes = "生成暗盘")
    @PostMapping(value = "/generate1")
    public Result<?> generate1(@RequestBody List<String> id) {
        wvStockSttService.generate(id, "2");
        return Result.ok("生成成功");
    }


    /**
     * 生成
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "生成明盘", notes = "生成明盘")
    @PostMapping(value = "/generateSw1")
    public Result<?> generateSw1(@RequestBody List<String> id) {
        wvStockSttService.generateSw(id, "1");
        return Result.ok("生成成功");
    }

    @ApiOperation(value = "生成暗盘", notes = "生成暗盘")
    @PostMapping(value = "/generateSw2")
    public Result<?> generateSw2(@RequestBody List<String> id) {
        wvStockSttService.generateSw(id, "2");
        return Result.ok("生成成功");
    }


    @AutoLog(value = "plqn标签打印")
    @ApiOperation(value = "标签打印", notes = "标签打印")
    @GetMapping(value = "/labelPrinting")
    public Result<?> labelPrinting(@RequestParam(name = "ids", required = true) String ids, HttpServletRequest request) throws InterruptedException {
        String[] split = ids.split(",");
        Page<WvStockStt> page = new Page<WvStockStt>(1, 100);
        HashMap<String, String> querymap = new HashMap<>(1024);
        for (String id : split) {
            String[] split1 = id.split("/");
            String upid = "";
            if (split1.length > 0) {
                upid = split1[0];
            }
            WvStockStt stockStt = new WvStockStt();
            WmToUpGoods wmToUpGoods = wmToUpGoodsService.getById(upid);
            if (wmToUpGoods == null) {
                querymap.put("id", upid);
                List<WvStockStt> wvStockStts = wvStockMapper.queryListType(page, querymap);
                if (CollectionUtil.isNotEmpty(wvStockStts)) {
                    stockStt = wvStockStts.get(0);
                }
                List<WmToUpGoods> list = wmToUpGoodsService.lambdaQuery().eq(WmToUpGoods::getKuWeiBianMa, stockStt.getKuWeiBianMa())
                        .eq(WmToUpGoods::getBinId, stockStt.getBinId())
                        .eq(WmToUpGoods::getCusCode, stockStt.getCusCode())
                        .eq(WmToUpGoods::getGoodsId, stockStt.getGoodsId())
                        .eq(WmToUpGoods::getGoodsUnit, stockStt.getGoodsUnit())
                        .eq(WmToUpGoods::getGoodsProData, stockStt.getGoodsProData())
                        .list();
                if (CollectionUtil.isNotEmpty(list)) {
                    for (WmToUpGoods toUpGoods : list) {
                        if (StringUtils.isNotEmpty(toUpGoods.getOrderId())) {
                            wmToUpGoods = toUpGoods;
                        }
                    }
                }
            }
            querymap.put("id", "");
            querymap.put("shpMingCheng", wmToUpGoods.getGoodsName());
            querymap.put("goodsCode", wmToUpGoods.getGoodsId());
            querymap.put("KuWeiBianMa", wmToUpGoods.getKuWeiBianMa());
            querymap.put("binId", wmToUpGoods.getBinId());
            querymap.put("kuctype", "库存");
            List<WvStockStt> wvStockSttss = wvStockMapper.queryListType(page, querymap);
            if (CollectionUtil.isNotEmpty(wvStockSttss)) {
                for (WvStockStt stockSttss : wvStockSttss) {
                    if (id.equals(stockSttss.getId())){
                        stockStt = stockSttss;
                    }
                }
            }

            Map map1 = new LinkedHashMap();
            String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
            List<BaUnit> baUnits = baUnitService.lambdaQuery().eq(BaUnit::getUnitZhName, wmToUpGoods.getGoodsUnit()).list();
            BaUnit baUnit = new BaUnit();
            if (CollectionUtil.isNotEmpty(baUnits)) {
                baUnit = baUnits.get(0);
            }
            Map map2 = new LinkedHashMap();
            List<Map> mapList = new ArrayList<>();
            //箱号
            Thread.sleep(3);
            map1.put("type", "ruku");

            map2.put("data01", wmToUpGoods.getGoodsId());
            map2.put("data02", wmToUpGoods.getCusName());
            map2.put("data03", wmToUpGoods.getGoodsName());
            if (baUnit != null) {
                map2.put("data04", stockStt.getGoodsQua() + " " + baUnit.getUnitEnName());
            } else {
                map2.put("data04", stockStt.getGoodsQua());
            }
            if (StringUtils.isEmpty(wmToUpGoods.getOrderIdI())){
                String pareId = wmToUpGoods.getPareId();
                String[] pareId1 = pareId.split("/");
                String pareId2 = "";
                if (pareId1.length > 0) {
                    pareId2 = pareId1[0];
                }
                WmToUpGoods wmToUpGoods1 = wmToUpGoodsService.getById(pareId2);
                wmToUpGoods.setOrderId(wmToUpGoods1.getOrderId());
                wmToUpGoods.setOrderIdI(wmToUpGoods1.getOrderIdI());
                wmToUpGoods.setWmToUpId(wmToUpGoods1.getWmToUpId());
            }
            WmInQmI inQmI = wmInQmIService.getById(wmToUpGoods.getOrderIdI());
            if (inQmI != null) {
                WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(inQmI.getImNoticeItem());
                map2.put("data05", wmImNoticeI.getContractlno());
            } else {
                map2.put("data05", "");
            }
            map2.put("data06", DateUtil.format(wmToUpGoods.getCreateTime(), "yyyy-MM-dd"));
            map2.put("data07", wmToUpGoods.getBinId());
            mapList.add(map2);
            map1.put("printitem", mapList);
            JSONObject jsonObj = new JSONObject(map1);
            String Json = jsonObj.toString();
            String post = HttpUtil.post(pritIp + "util/uwms/client/print/ruku/admin", Json);
            String post1 = HttpUtil.post(pritIp + "util/uwms/client/print/ruku/admin", Json);
//				String post1 = HttpUtil.post("http://192.168.0.106:8082/util/uwms/client/print/ruku/admin", Json);
            Thread.sleep(2);
        }
        return Result.error("打印成功");
    }



    @AutoLog(value = "plqn箱麦打印")
    @ApiOperation(value = "plqn箱麦打印", notes = "plqn箱麦打印")
    @GetMapping(value = "/labelPrints")
    public Result<?> labelPrints(@RequestParam(name = "ids", required = true) String ids, HttpServletResponse response) throws InterruptedException {
        String[] split = ids.split(",");
        Page<WvStockStt> page = new Page<WvStockStt>(1, 100);
        HashMap<String, String> querymap = new HashMap<>(1024);
        for (String id : split) {
            String[] split1 = id.split("/");
            String upid = "";
            if (split1.length > 0) {
                upid = split1[0];
            }
            WvStockStt stockStt = new WvStockStt();
            WmToUpGoods wmToUpGoods = wmToUpGoodsService.getById(upid);
            if (wmToUpGoods == null) {
                querymap.put("id", upid);
                List<WvStockStt> wvStockStts = wvStockMapper.queryListType(page, querymap);
                if (CollectionUtil.isNotEmpty(wvStockStts)) {
                    stockStt = wvStockStts.get(0);
                }
                List<WmToUpGoods> list = wmToUpGoodsService.lambdaQuery().eq(WmToUpGoods::getKuWeiBianMa, stockStt.getKuWeiBianMa())
                        .eq(WmToUpGoods::getBinId, stockStt.getBinId())
                        .eq(WmToUpGoods::getCusCode, stockStt.getCusCode())
                        .eq(WmToUpGoods::getGoodsId, stockStt.getGoodsId())
                        .eq(WmToUpGoods::getGoodsUnit, stockStt.getGoodsUnit())
                        .eq(WmToUpGoods::getGoodsProData, stockStt.getGoodsProData())
                        .list();
                if (CollectionUtil.isNotEmpty(list)) {
                    for (WmToUpGoods toUpGoods : list) {
                        if (StringUtils.isNotEmpty(toUpGoods.getOrderId())) {
                            wmToUpGoods = toUpGoods;
                        }
                    }
                }
            }
            querymap.put("id", "");
            querymap.put("shpMingCheng", wmToUpGoods.getGoodsName());
            querymap.put("goodsCode", wmToUpGoods.getGoodsId());
            querymap.put("KuWeiBianMa", wmToUpGoods.getKuWeiBianMa());
            querymap.put("binId", wmToUpGoods.getBinId());
            querymap.put("kuctype", "库存");
            List<WvStockStt> wvStockSttss = wvStockMapper.queryListType(page, querymap);
            if (CollectionUtil.isNotEmpty(wvStockSttss)) {
                for (WvStockStt stockSttss : wvStockSttss) {
                    if (id.equals(stockSttss.getId())){
                        stockStt = stockSttss;
                    }
                }
            }

            Map map1 = new LinkedHashMap();
            String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
            List<BaUnit> baUnits = baUnitService.lambdaQuery().eq(BaUnit::getUnitZhName, wmToUpGoods.getGoodsUnit()).list();
            BaUnit baUnit = new BaUnit();
            if (CollectionUtil.isNotEmpty(baUnits)) {
                baUnit = baUnits.get(0);
            }
            Map map2 = new LinkedHashMap();
            List<Map> mapList = new ArrayList<>();
            //客户
            map1.put("data01", wmToUpGoods.getCusName());
            map1.put("type", "xiangmai");
            map2.put("data01", wmToUpGoods.getGoodsId());
            map2.put("data02", wmToUpGoods.getGoodsName());
            if (baUnit != null) {
                map2.put("data03", stockStt.getGoodsQua() + " " + baUnit.getUnitEnName());
            } else {
                map2.put("data03", stockStt.getGoodsQua());
            }
            if (StringUtils.isEmpty(wmToUpGoods.getOrderIdI())){
                String pareId = wmToUpGoods.getPareId();
                String[] pareId1 = pareId.split("/");
                String pareId2 = "";
                if (pareId1.length > 0) {
                    pareId2 = pareId1[0];
                }
                WmToUpGoods wmToUpGoods1 = wmToUpGoodsService.getById(pareId2);
                wmToUpGoods.setOrderId(wmToUpGoods1.getOrderId());
                wmToUpGoods.setOrderIdI(wmToUpGoods1.getOrderIdI());
                wmToUpGoods.setWmToUpId(wmToUpGoods1.getWmToUpId());
            }
            WmInQmI inQmI = wmInQmIService.getById(wmToUpGoods.getOrderIdI());
            if (inQmI != null) {
                WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(inQmI.getImNoticeItem());
                map2.put("data04", wmImNoticeI.getContractlno());
            } else {
                map2.put("data04", "");
            }
            mapList.add(map2);
            map1.put("printitem", mapList);
            JSONObject jsonObj = new JSONObject(map1);
            String Json = jsonObj.toString();
//				String post1 = HttpUtil.post(pritIp2+"util/uwms/client/print/xiangmai/admin", Json);
            String post1 = HttpUtil.post(pritIp2 + "util/uwms/client/print/ruku/admin", Json);
            String post = HttpUtil.post(pritIp2 + "util/uwms/client/print/ruku/admin", Json);
//				String post = HttpUtil.post(pritIp2+"util/uwms/client/print/xiangmai/admin", Json);
            Thread.sleep(3);
        }
        return Result.error("打印成功");
    }
}
