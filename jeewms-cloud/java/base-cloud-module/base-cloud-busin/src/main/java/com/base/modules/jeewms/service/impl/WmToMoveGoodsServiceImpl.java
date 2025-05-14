package com.base.modules.jeewms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.common.system.vo.LoginUser;
import com.base.modules.jeewms.controller.dto.StockMoveDto;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.*;
import com.base.modules.jeewms.pdaapi.ApiresEntity;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.vo.EditBatchWmToMoveGoodsVo;
import com.base.modules.jeewms.vo.addWmToMoveVo;
import com.base.modules.util.ConstUtil;
import com.base.modules.util.NotNullUtils;
import jodd.util.CollectionUtil;
import jodd.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 库存转移
 * @Author: base-boot
 * @Date: 2021-05-26
 * @Version: V1.0
 */
@Service
public class WmToMoveGoodsServiceImpl extends ServiceImpl<WmToMoveGoodsMapper, WmToMoveGoods> implements IWmToMoveGoodsService {

    @Autowired
    private WmToMoveGoodsMapper wmToMoveGoodsMapper;

    @Autowired
    private WvStockMapper wvStockMapper;//托盘转移

    @Autowired
    private MdCusMapper mdCusMapper;//查询客户

    @Autowired
    private MdGoodsMapper mdGoodsMapper;//商品

    @Autowired
    private WmToUpGoodsMapper wmToUpGoodsMapper;//上架信息

    @Autowired
    private WmToDownGoodsMapper wmToDownGoodsMapper;//下架信息

    @Autowired
    private GoodsMoveTask moveTask;

    @Autowired
    private IWmInQmIService iWmInQmIService;

    @Autowired
    private IBaKwService baKwService;
    @Autowired
    private WmTuopanMapper wmTuopanMapper;

    @Autowired
    private IBaStoreAreaService baStoreAreaService;

    @Autowired
    private IBaStoreService baStoreService;
    /**
     * @param list
     * @return
     * @Describe 批量修改转移
     * @Author zly
     * @Create Date 2021/5/26
     */
    @Override
    public Result<?> editBatch(List<EditBatchWmToMoveGoodsVo> list) {
        if ("true".equals(verification(list).get("type"))) {
            return Result.ok(verification(list).get("massge"));
        }
        int eer = list.size();
        for (EditBatchWmToMoveGoodsVo vo : list) {
            if(baKwService.lambdaQuery().eq(BaKw::getKwCode,vo.getBinTo()).one()==null){
                 throw new JeecgBootException("库位不存在");
            }
            //获取当前数据
            WmToMoveGoods en = wmToMoveGoodsMapper.selectById(vo.getId());
            //转移修改商品储存位
            //updateMdGoods(en);
            //修改参数
            en.setMoveSta(ConstUtil.wm_sta4);
            BeanUtils.copyProperties(vo, en, NotNullUtils.getNullPropertyNames(vo));
            if (!StringUtils.isEmpty(vo.getBaseGoodscount())) {
                en.setBaseGoodscount(vo.getBaseGoodscount());
            }
            if (!StringUtils.isEmpty(vo.getBinTo())) {
                en.setBinTo(vo.getBinTo());
            }
            if (!StringUtils.isEmpty(vo.getTinId())) {
                en.setTinId(vo.getTinId());
            }
            //修改数据
            int row = wmToMoveGoodsMapper.updateById(en);
            eer = eer - row;
        }
        if (eer == 0) {
            moveTask.run();
            return Result.ok("转移成功");
        }
        return Result.ok("转移失败" + eer + "条");
    }

    private Map<String, Object> verification(List<EditBatchWmToMoveGoodsVo> list) {
        Map<String, Object> map = new HashMap<>(1024);
        for (EditBatchWmToMoveGoodsVo vo : list) {
            WmToMoveGoods en = wmToMoveGoodsMapper.selectById(vo.getId());
            if ("TPZY".equals(en.getOrderTypeCode())) {
                if (StringUtils.isEmpty(vo.getTinId())) {
                    map.put("type", "true");
                    map.put("massge", "请输入到哪个托盘！！");
                    return map;
                }
            }
            if ("KCZY".equals(en.getOrderTypeCode())) {
                if (StringUtils.isEmpty(vo.getBinTo())) {
                    map.put("type", "true");
                    map.put("massge", "请输入到哪个库位！！");
                    return map;
                }
            }
        }
        map.put("type", "false");
        return map;
    }


    private void updateMdGoods(WmToMoveGoods en) {
        //根据储位和托盘信息获取商品信息
        QueryWrapper<MdGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shp_bian_ma", en.getGoodsId());
        MdGoods mdGoods = mdGoodsMapper.selectOne(queryWrapper);

        //根据储位 托盘 商品Id查询上架信息
        QueryWrapper<WmToUpGoods> queryWrapper1 = new QueryWrapper<>();
        WmToUpGoods wmToUpGoods = wmToUpGoodsMapper.selectOne(queryWrapper1);

        //根据储位 托盘 商品Id查询下架信息
        QueryWrapper<WmToDownGoods> queryWrapper2 = new QueryWrapper<>();
        WmToDownGoods wmToDownGoods = wmToDownGoodsMapper.selectOne(queryWrapper2);

        //计算库存数量
        BigDecimal sum = new BigDecimal(wmToUpGoods.getGoodsQua()).subtract(new BigDecimal(wmToDownGoods.getGoodsQua()));

        BigDecimal sum1 = new BigDecimal(en.getGoodsQua());
        if (sum.compareTo(sum1) == -1) {
            throw new JeecgBootException("数量不足");
        }

        BeanUtils.copyProperties(wmToUpGoods, wmToDownGoods, NotNullUtils.getNullPropertyNames(wmToUpGoods));
        wmToDownGoods.setId("");
        wmToDownGoods.setKuWeiBianMa(en.getBinFrom());
        wmToDownGoods.setBinIdFrom(en.getTinFrom());
        wmToDownGoods.setGoodsQua(en.getGoodsQua());
        wmToDownGoods.setGoodsQuaok(en.getGoodsQua());
        wmToDownGoods.setBaseGoodscount(en.getBaseGoodscount());

        wmToUpGoods.setId("");
        wmToUpGoods.setKuWeiBianMa(en.getBinTo());
        wmToUpGoods.setBinId(en.getTinId());
        wmToUpGoods.setGoodsQua(en.getGoodsQua());
        wmToUpGoods.setBaseGoodscount(en.getBaseGoodscount());

        wmToDownGoodsMapper.insert(wmToDownGoods);
        wmToUpGoodsMapper.insert(wmToUpGoods);


    }

    /**
     * @param wvStock
     * @param pageNo
     * @param pageSize
     * @param wvStock
     * @return
     * @Describe 生成托盘转移-分页列表查询
     * @Author zly
     * @Create Date 2021/5/26
    @Override
    public Result<?> findPageLists(WvStockStt wvStock, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        Page<WvStockStt> page = new Page<WvStockStt>(pageNo, pageSize);
        HashMap<String, String> querymap = new HashMap<>(1024);
        querymap.put("shpMingCheng", wvStock.getShpMingCheng());
        querymap.put("goodsCode", wvStock.getGoodsId());
        querymap.put("KuWeiBianMa", wvStock.getKuWeiBianMa());
        querymap.put("binId", wvStock.getBinId());
        querymap.put("kuctype", wvStock.getKuctype());
        querymap.put("tenantId", wvStock.getTenantId());
        List<WvStockStt> wvStockStts = wvStockMapper.queryListTypeFxkw(page, querymap);
        List<WvStockStt> newList = getNewList(wvStockStts);
        IPage<WvStockStt> wvStockSttPage = page.setRecords(newList);
        return Result.ok(wvStockSttPage);
    }*/


    /**
     * @param wvStock
     * @param pageNo
     * @param pageSize
     * @param wvStock
     * @return
     * @Describe 生成托盘转移-分页列表查询
     * @Author zly
     * @Create Date 2021/5/26
     */
    @Override
    public Result<?> findPageLists(WvStockStt wvStock, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        String storeCode = null;
        Map<String,String[]> paramMap = req.getParameterMap();
        if (paramMap.containsKey("storeCode")) {
            storeCode = req.getParameterMap().get("storeCode")[0];
        }

        List<String> kwCodeList = new ArrayList<>();

        //查询库位
            List<BaKw> baKwList = baKwService.lambdaQuery().in(BaKw::getStoreCode,storeCode).list();
            if (baKwList.size() > 0) {
                for (BaKw baKw : baKwList) {
                    kwCodeList.add(baKw.getKwCode());
                }
            }
    IPage<WvStockStt> wvStockSttPage = null;
    if(kwCodeList.size()>0){
    Page<WvStockStt> page = new Page<WvStockStt>(pageNo, pageSize);
    HashMap<String, Object> querymap = new HashMap<>(1024);
        querymap.put("shpMingCheng", wvStock.getShpMingCheng());
        querymap.put("goodsCode", wvStock.getGoodsId());
        querymap.put("KuWeiBianMa", wvStock.getKuWeiBianMa());
        querymap.put("binId", wvStock.getBinId());
        querymap.put("kuctype", wvStock.getKuctype());
        querymap.put("tenantId", wvStock.getTenantId());
        querymap.put("KuWeiBianMa2", kwCodeList);
        List<WvStockStt> wvStockStts = wvStockMapper.queryListTypeFxkw(page, querymap);
        wvStockSttPage = page.setRecords(wvStockStts);
        }
        return Result.ok(wvStockSttPage);
    }







    @Override
    public Result<?> findPageList(WvStockStt wvStock, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        QueryWrapper<WvStockStt> queryWrapper = QueryGenerator.initQueryWrapper(wvStock, req.getParameterMap());
        queryWrapper.lambda().eq(WvStockStt::getKuctype, "库存");
//        queryWrapper.lambda().ge(WvStockStt::getGoodsQua,"0");
        queryWrapper.orderByDesc("create_time");
        IPage<WvStockStt> page = new Page<>(pageNo, pageSize);
        IPage<WvStockStt> list = wvStockMapper.selectPage(page, queryWrapper);
        return Result.ok(list);
    }

    /**
     * @param vo
     * @return
     * @Describe 添加托盘转移
     * @Author zly
     * @Create Date 2021/5/26
    @Override
    public Result<?> addWmToMove(addWmToMoveVo vo) {
        if (null == vo.getIds() || vo.getIds().isEmpty()) {
            return Result.ok("生成失败，请重新生成！！");
        }

        int eer = 0;

        List<WvStockStt> wvStockStts = wvStockMapper.queryListTypes();

        List<WvStockStt> newList = getNewList(wvStockStts);
            for (String id : vo.getIds()) {
                for (WvStockStt wvStockStt : newList) {
                    if(id.equals(wvStockStt.getId())){
                        if(Integer.parseInt(vo.getTransquas()) <= wvStockStt.getGoodsQua()){
                            //获取托盘转移单
                            WvStockStt en = wvStockMapper.selectById(id);
                            if (null == en) {
                                ++eer;
                                continue;
                            }else {
                                    List<WmTuopan> wmTuopans = wmTuopanMapper.selectList(new QueryWrapper<WmTuopan>().eq("tin_id", en.getBinId()));
                                    String Boxmark ="";
                                //MdCus mdCus = mdCusMapper.selectOne(new QueryWrapper<MdCus>().eq("cus_code", en.getKuWeiBianMa()));
                                if(CollectionUtils.isNotEmpty(wmTuopans)){
                                    WmTuopan wmTuopan = wmTuopans.get(0);
                                        wmTuopan.setTinLength(vo.getTinLength());
                                        wmTuopan.setTinWidth(vo.getTinWidth());
                                        wmTuopan.setTinHigh(vo.getTinHigh());
                                        wmTuopan.setTinWeight(vo.getTinWeight());
                                        //体积
                                        BigDecimal decimal = NumberUtil.mul(vo.getTinLength(), vo.getTinWidth(), vo.getTinHigh());
                                        wmTuopan.setTinVolume(decimal.toString());
                                        if(StringUtil.isNotEmpty(wmTuopans.get(0).getBoxmark())){
                                            Boxmark = wmTuopans.get(0).getBoxmark();
                                            wmTuopan.setBoxmark(wmTuopans.get(0).getBoxmark());
                                        }

                                        //修改
                                        wmTuopanMapper.updateById(wmTuopan);
                                    }
                                    WmTuopan wmTuopanx = wmTuopanMapper.selectOne(new QueryWrapper<WmTuopan>().eq("tin_id", vo.getTinId()));
                                    if(wmTuopanx!=null){
                                        wmTuopanx.setTinLength(vo.getTinLength());
                                        wmTuopanx.setTinWidth(vo.getTinWidth());
                                        wmTuopanx.setTinHigh(vo.getTinHigh());
                                        wmTuopanx.setTinWeight(vo.getTinWeight());
                                        //体积
                                        BigDecimal decimal = NumberUtil.mul(vo.getTinLength(), vo.getTinWidth(), vo.getTinHigh());
                                        wmTuopanx.setBoxmark(wmTuopanx.getBoxmark());
                                        wmTuopanx.setTinVolume(decimal.toString());
                                        wmTuopanMapper.updateById(wmTuopanx);
                                    }else {
                                        WmTuopan wmTuopan2 = new WmTuopan();
                                        wmTuopan2.setTinId(vo.getTinId());
                                        wmTuopan2.setTinLength(vo.getTinLengths());
                                        wmTuopan2.setTinWidth(vo.getTinWidths());
                                        wmTuopan2.setTinHigh(vo.getTinHighs());
                                        wmTuopan2.setTinWeight(vo.getTinWeights());
                                        //体积
                                        BigDecimal decimal2 = NumberUtil.mul(vo.getTinLengths(), vo.getTinWidths(), vo.getTinHighs());
                                        wmTuopan2.setBoxmark(Boxmark);
                                        wmTuopan2.setTinVolume(decimal2.toString());
                                        wmTuopanMapper.insert(wmTuopan2);
                                    }

                            }

                            WmToMoveGoods wmtomove = getWmToMoveGoods(en, vo.getTinId(),vo.getTransquas());
                            wmtomove.setOrderTypeCode("TPZY");
                            int row = wmToMoveGoodsMapper.insert(wmtomove);
                        }else {
                            throw  new JeecgBootException("转移数量大于库存数量");
                        }
                    }

                }

        }

        if (eer > 0) {
            return Result.ok("添加托盘转移失败" + eer + "条");
        }
        return Result.ok("添加托盘转移成功");
    }
*/

    /**
     * @param vo
     * @return
     * @Describe 添加托盘转移
     * @Author zly
     * @Create Date 2021/5/26
     */
    @Override
    public Result<?> addWmToMove(addWmToMoveVo vo) {
        if (null == vo.getIds() || vo.getIds().isEmpty()) {
            return Result.ok("生成失败，请重新生成！！");
        }

        int eer = 0;

        List<WvStockStt> wvStockStts = wvStockMapper.queryListTypes();
        int row = 0;
        for (String id : vo.getIds()) {
            for (WvStockStt wvStockStt : wvStockStts) {
                if(id.equals(wvStockStt.getId())){
                    if(Integer.parseInt(vo.getTransquas()) <= wvStockStt.getGoodsQua()){
                        //获取托盘转移单
                        WvStockStt en = wvStockMapper.selectById(id);
                        if (null == en) {
                            ++eer;
                            continue;
                        }else {
                            List<WmTuopan> wmTuopans = wmTuopanMapper.selectList(new QueryWrapper<WmTuopan>().eq("tin_id", en.getBinId()));
                            String Boxmark ="";
                            //MdCus mdCus = mdCusMapper.selectOne(new QueryWrapper<MdCus>().eq("cus_code", en.getKuWeiBianMa()));
                            if(CollectionUtils.isNotEmpty(wmTuopans)){
                                WmTuopan wmTuopan = wmTuopans.get(0);
                                wmTuopan.setTinLength(vo.getTinLength());
                                wmTuopan.setTinWidth(vo.getTinWidth());
                                wmTuopan.setTinHigh(vo.getTinHigh());
                                wmTuopan.setTinWeight(vo.getTinWeight());
                                //体积
                                BigDecimal decimal = NumberUtil.mul(vo.getTinLength(), vo.getTinWidth(), vo.getTinHigh());
                                wmTuopan.setTinVolume(decimal.toString());
                                if(StringUtil.isNotEmpty(wmTuopans.get(0).getBoxmark())){
                                    Boxmark = wmTuopans.get(0).getBoxmark();
                                    wmTuopan.setBoxmark(wmTuopans.get(0).getBoxmark());
                                }

                                //修改
                                wmTuopanMapper.updateById(wmTuopan);
                            }
                            WmTuopan wmTuopanx = wmTuopanMapper.selectOne(new QueryWrapper<WmTuopan>().eq("tin_id", vo.getTinId()));
                            if(wmTuopanx!=null){
                                wmTuopanx.setTinLength(vo.getTinLengths());
                                wmTuopanx.setTinWidth(vo.getTinWidths());
                                wmTuopanx.setTinHigh(vo.getTinHighs());
                                wmTuopanx.setTinWeight(vo.getTinWeights());
                                //体积
                                BigDecimal decimal = NumberUtil.mul(vo.getTinLengths(), vo.getTinWidths(), vo.getTinHighs());
                                wmTuopanx.setBoxmark(wmTuopanx.getBoxmark());
                                wmTuopanx.setTinVolume(decimal.toString());
                                wmTuopanMapper.updateById(wmTuopanx);
                            }else {
                                WmTuopan wmTuopan2 = new WmTuopan();
                                wmTuopan2.setTinId(vo.getTinId());
                                wmTuopan2.setTinLength(vo.getTinLengths());
                                wmTuopan2.setTinWidth(vo.getTinWidths());
                                wmTuopan2.setTinHigh(vo.getTinHighs());
                                wmTuopan2.setTinWeight(vo.getTinWeights());
                                //体积
                                BigDecimal decimal2 = NumberUtil.mul(vo.getTinLengths(), vo.getTinWidths(), vo.getTinHighs());
                                wmTuopan2.setBoxmark(Boxmark);
                                wmTuopan2.setTinVolume(decimal2.toString());
                                wmTuopanMapper.insert(wmTuopan2);
                            }

                        }

                        WmToMoveGoods wmtomove = getWmToMoveGoods(en, vo.getTinId(),vo.getTransquas());
                        wmtomove.setOrderTypeCode("TPZY");
                        row = wmToMoveGoodsMapper.insert(wmtomove);
                    }else {
                        throw  new JeecgBootException("转移数量大于库存数量");
                    }
                }

            }

        }
            moveTask.run();
        if (eer > 0) {
            return Result.ok("添加托盘转移失败" + eer + "条");
        }
        return Result.ok("添加托盘转移成功");
    }




    /**
     * 分箱 根据对象的获取商品编号,箱码,库位进行数据合并
     * @param list 分箱数据
     * @return
     */
    private static List<WvStockStt> getNewList(List<WvStockStt> list) {
        HashMap<String, WvStockStt> tempMap = new HashMap<String, WvStockStt>();
        for (WvStockStt wvStockStt : list) {
            if (wvStockStt.getGoodsQua()>0){
                String keys = wvStockStt.getGoodsCode()+wvStockStt.getKuWeiBianMa()+wvStockStt.getBinId();//获取商品编号+箱码+库位
                if (tempMap.containsKey(keys)) {
                    WvStockStt w = new WvStockStt();
                    BeanUtils.copyProperties(wvStockStt, w);
                    w.setGoodsQua(tempMap.get(keys).getGoodsQua()+wvStockStt.getGoodsQua());
                    tempMap.put(keys, w);
                } else {
                    tempMap.put(keys, wvStockStt);
                }
            }
        }

        List<WvStockStt> newList = new ArrayList<WvStockStt>();
        for(String temp:tempMap.keySet()){
            WvStockStt w = tempMap.get(temp);
            Integer goodsQua = w.getGoodsQua();
            if(goodsQua != 0){
                newList.add(tempMap.get(temp));
            }
        }
        return newList;
    }

    /**
     * @param ids
     * @return
     * @Describe 添加储位转移
     * @Author zly
     * @Create Date 2021/5/26
     *//*
    @Override
    public Result<?> addWmToMoveGoods(List<String> ids) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String relTenantIds = loginUser.getRelTenantIds();
        Integer integer = Convert.toInt(relTenantIds);
        if (ids == null || ids.isEmpty()) {
            return Result.ok("生成失败，请重新生成！！");
        }
        int eer = 0;
        for (String id : ids) {
            //获取托盘转移单
            WvStockStt en = wvStockMapper.selectById(id);
            if (en == null) {
                ++eer;
                continue;
            }
            WmToMoveGoods wmtomove = getWmToMoveGoods(en, "","");
            wmtomove.setOrderTypeCode("KCZY");
            wmtomove.setTinId(en.getBinId());
            wmtomove.setTenantId(integer);
            int row = wmToMoveGoodsMapper.insert(wmtomove);
        }

        if (eer > 0) {
            return Result.ok("添加储位转移失败" + eer + "条");
        }
        return Result.ok("添加储位转移成功");
    }*/

    /**
     * @param ids
     * @return
     * @Describe 添加储位转移
     * @Author zly
     * @Create Date 2021/5/26
    @Override
    public Result<?> addWmToMoveGoods(List<String> ids) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String relTenantIds = loginUser.getRelTenantIds();
        Integer integer = Convert.toInt(relTenantIds);
        if (ids == null || ids.isEmpty()) {
            return Result.ok("生成失败，请重新生成！！");
        }
//        int eer = 0;
        List<WvStockStt> wvStockStts = wvStockMapper.queryListTypes();
        List<WvStockStt> newList = getNewList(wvStockStts);

        for (String id : ids) {
            //获取托盘转移单
            for (WvStockStt en : newList) {
              if(en.getId().equals(id)){
//                  if (en == null) {
//                      ++eer;
//                      continue;
//                  }
                  WmToMoveGoods wmtomove = getWmToMoveGoods(en, "","");
                  wmtomove.setOrderTypeCode("KCZY");
                  wmtomove.setTinId(en.getBinId());
                  wmtomove.setTenantId(integer);
                  int row = wmToMoveGoodsMapper.insert(wmtomove);
              }
            }
        }

//        if (eer > 0) {
//            return Result.ok("添加储位转移失败" + eer + "条");
//        }
        return Result.ok("添加储位转移成功");
    }*/



    /**
     * @param ids
     * @return
     * @Describe 添加储位转移
     * @Author zly
     * @Create Date 2021/5/26
     */
    @Override
    public Result<?> addWmToMoveGoods(List<String> ids) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String relTenantIds = loginUser.getRelTenantIds();
        Integer integer = Convert.toInt(relTenantIds);
        if (ids == null || ids.isEmpty()) {
            return Result.ok("生成失败，请重新生成！！");
        }
//        int eer = 0;
        List<WvStockStt> wvStockStts = wvStockMapper.queryListTypes();
        String binid = "";
        for (String id : ids) {
            //获取托盘转移单
            for (WvStockStt en : wvStockStts) {
                if(en.getId().equals(id)){
                    List<WmToMoveGoods> wmToMoveGoods = wmToMoveGoodsMapper.selectList(new QueryWrapper<WmToMoveGoods>().
                            eq("tin_id", en.getBinId()).eq("move_sta", "计划中"));
                    if(CollectionUtils.isEmpty(wmToMoveGoods)){
                        WmToMoveGoods wmtomove = getWmToMoveGoods(en, "","");
                        wmtomove.setOrderTypeCode("KCZY");
                        wmtomove.setTinId(en.getBinId());
                        wmtomove.setTenantId(integer);
                        int row = wmToMoveGoodsMapper.insert(wmtomove);
                    }else {
                        binid = binid + ',' + en.getBinId();
                    }
//                  if (en == null) {
//                      ++eer;
//                      continue;
//                  }
                }
            }
        }
      if(StringUtil.isNotEmpty(binid)){
          return Result.ok("添加储位转移成功，失败箱码："+ binid + "失败原因：当前箱码在转移任务中已存在，不可重复生成任务");
      }


//        if (eer > 0) {
//            return Result.ok("添加储位转移失败" + eer + "条");
//        }
        return Result.ok("添加储位转移成功");
    }

    @Override
    public void pdaStockMove(StockMoveDto vo) {
        WvStock t = wvStockMapper.selectStockById(vo.getId());
        try {
            WmToMoveGoods wmtomove = new WmToMoveGoods();
            wmtomove.setOrderTypeCode("TPZY");
            wmtomove.setBinFrom(t.getKuWeiBianMa());
            wmtomove.setBinTo(vo.getKwCode());
            wmtomove.setCusCode(t.getCusCode());
            wmtomove.setCusName(t.getZhongWenQch());
            wmtomove.setToCusCode(t.getCusCode());
            wmtomove.setToCusName(t.getZhongWenQch());
            try {
                MdCus mdcus = mdCusMapper.selectOne(new QueryWrapper<MdCus>().lambda().eq(MdCus::getKeHuBianMa,t.getCusCode()));
                wmtomove.setCusName(mdcus.getZhongWenQch());
                wmtomove.setToCusName(mdcus.getZhongWenQch());

            } catch (Exception e) {
                e.printStackTrace();
            }
            wmtomove.setGoodsId(t.getGoodsId());
            wmtomove.setGoodsName(t.getShpMingCheng());
            wmtomove.setGoodsProData(t.getGoodsProData());
            wmtomove.setGoodsQua(t.getGoodsQua().toString());
            wmtomove.setGoodsUnit(t.getGoodsUnit());
            wmtomove.setBaseGoodscount(t.getGoodsQua().toString());
            wmtomove.setBaseUnit(t.getGoodsUnit());
            wmtomove.setMoveSta("计划中");
            wmtomove.setTinFrom(t.getBinId());
            wmtomove.setTinId(vo.getTinCode());
            wmtomove.setRunSta("计划中");
            this.save(wmtomove);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JeecgBootException("转移失败");
        }
    }
    private WmToMoveGoods getWmToMoveGoods(WvStockStt en, String tinId, String transquas) {
        String id ="";
        if (StringUtil.isNotEmpty(en.getId())){
            id = en.getId().split("/")[0];
        }
        QueryWrapper<MdGoods> wrapper = new QueryWrapper<>();
        wrapper.eq("shp_bian_ma",en.getGoodsId());
        List<MdGoods> mdGoods = mdGoodsMapper.selectList(wrapper);

        WmToUpGoods wmToUpGoods = wmToUpGoodsMapper.selectById(id);
        WmToMoveGoods wmtomove = new WmToMoveGoods();
        wmtomove.setPareId(id);

        if (wmToUpGoods != null) {
            wmtomove.setOrderId(wmToUpGoods.getOrderId());
            wmtomove.setOrderIdI(wmToUpGoods.getOrderIdI());
        }else {
            WmToDownGoods downGoods = wmToDownGoodsMapper.selectById(id);
            if (downGoods != null){
                wmtomove.setOrderId(downGoods.getOrderId());
                wmtomove.setOrderIdI(downGoods.getOrderIdI());
            }
        }
        wmtomove.setBinFrom(NotNullUtils.tranObj2Str(en.getKuWeiBianMa()));
        wmtomove.setGoodsBatch(NotNullUtils.tranObj2Str(en.getGoodsBatch()));
        wmtomove.setBinTo(NotNullUtils.tranObj2Str(en.getKuWeiBianMa()));
        wmtomove.setCusCode(NotNullUtils.tranObj2Str(en.getCusCode()));
        wmtomove.setCusName(NotNullUtils.tranObj2Str(en.getZhongWenQch()));
        wmtomove.setToCusCode(NotNullUtils.tranObj2Str(en.getCusCode()));
        wmtomove.setToCusName(NotNullUtils.tranObj2Str(en.getZhongWenQch()));
        wmtomove.setGoodsId(NotNullUtils.tranObj2Str(en.getGoodsId()));
        wmtomove.setGoodsName(NotNullUtils.tranObj2Str(en.getShpMingCheng()));
        wmtomove.setGoodsProData(NotNullUtils.tranObj2Str(en.getGoodsProData()));
        wmtomove.setGoodsQua(NotNullUtils.tranObj2Str(en.getGoodsQua().toString()));
        if (CollectionUtils.isNotEmpty(mdGoods)){
            if (StringUtil.isNotEmpty(mdGoods.get(0).getShlDanWei())){
                wmtomove.setGoodsUnit(mdGoods.get(0).getShlDanWei());
                wmtomove.setBaseUnit(mdGoods.get(0).getShlDanWei());
            }else {
                wmtomove.setGoodsUnit(NotNullUtils.tranObj2Str(en.getGoodsUnit()));
                wmtomove.setBaseUnit(NotNullUtils.tranObj2Str(en.getGoodsUnit()));
            }
        }
        wmtomove.setBaseGoodscount(NotNullUtils.tranObj2Str(en.getGoodsQua().toString()));
        if (StringUtil.isNotEmpty(transquas)){
            wmtomove.setBaseGoodscount(NotNullUtils.tranObj2Str(transquas.toString()));
        }
        wmtomove.setBaseUnit(NotNullUtils.tranObj2Str(en.getGoodsUnit()));
        if (StringUtils.isEmpty(tinId)) {
            wmtomove.setMoveSta("计划中");
        } else {
            wmtomove.setMoveSta("已完成");
            wmtomove.setTinId(tinId);
        }
        wmtomove.setTinFrom(NotNullUtils.tranObj2Str(en.getBinId()));
        wmtomove.setRunSta("计划中");
        wmtomove.setCusName(getCusName(en.getCusCode()));
        wmtomove.setToCusName(getCusName(en.getCusCode()));
        return wmtomove;
    }

    private String getCusName(String cusCode) {
        //根据编码获取客户名称
        QueryWrapper<MdCus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ke_hu_bian_ma", cusCode);
        MdCus mdcus = mdCusMapper.selectOne(queryWrapper);
        if (mdcus == null) {
            return "";
        }
        return mdcus.getZhongWenQch();
    }
}
