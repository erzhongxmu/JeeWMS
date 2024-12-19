package com.base.modules.bidata.biapi;

import com.base.modules.baseUtil.FxjoutputEntity;
import com.base.modules.baseUtil.StringUtil;
import com.base.modules.baseUtil.hanautil;
import com.base.modules.baseUtil.otherutil;
import com.base.modules.bi.entity.zdOnlCgreportHead;
import com.base.modules.bi.service.IzdOnlCgreportHeadService;
import com.base.modules.bidata.bientity.*;
import com.base.modules.bidata.entity.BiSql;
import com.base.modules.bidata.service.IBiSqlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Api(value = "basebiRest", description = "bi", tags = "basebiRest")
@RestController
@RequestMapping(value = "/notokenbasebi")
public class NoTokenBasebiController {
    @Autowired
    private IBiSqlService biSqlService;
    @Autowired
    private IzdOnlCgreportHeadService onlCgreportHeadService;

    @ApiOperation(value = "gethanabidate", produces = "application/json", httpMethod = "GET")
    @RequestMapping(value = "/gethanabidate/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getbidatafromhana(@ApiParam(required = true, name = "id", value = "id") @PathVariable("id") String id, HttpServletRequest request) {
        // 验证
        String hanasql = "";
        String label = "";
        hanautil hana = new hanautil();
        String bitype = "";
        BiSql biSqlEntity = biSqlService.getById(id);

        bitype = biSqlEntity.getBiType();
        label = biSqlEntity.getBiLabel();
        hanasql = biSqlEntity.getBiSql();
        labelEntity labelEntity = new labelEntity();
        dataEntity dataEntity = new dataEntity();
        barEntity barEntity = new barEntity();
        pieDate pieDate = new pieDate();
        perEntity perEntity = new perEntity();
        listEntity listEntity = new listEntity();


        if (StringUtil.strPos(bitype, "bar")) {
            List<FxjoutputEntity> list = hana.getbilist(hanasql);
            com.base.modules.bidata.bientity.barEntity.DataBean dataBean = new barEntity.DataBean();
            List<String> categories = new ArrayList<>();
            List<String> series1 = new ArrayList<>();
            List<String> series2 = new ArrayList<>();
            for (FxjoutputEntity t : list) {
                categories.add(t.getFxjx1());
                series1.add(t.getFxjx2());
                series2.add(t.getFxjx3());
            }
            dataBean.setCategories(categories);//x

            List<com.base.modules.bidata.bientity.barEntity.DataBean.SeriesBean> series = new ArrayList<>();

            com.base.modules.bidata.bientity.barEntity.DataBean.SeriesBean seriesBean1 = new com.base.modules.bidata.bientity.barEntity.DataBean.SeriesBean();
            seriesBean1.setData(series1);
            seriesBean1.setName("1");
            series.add(seriesBean1);

            com.base.modules.bidata.bientity.barEntity.DataBean.SeriesBean seriesBean2 = new com.base.modules.bidata.bientity.barEntity.DataBean.SeriesBean();
            seriesBean2.setData(series2);
            seriesBean2.setName("2");
            series.add(seriesBean2);

            dataBean.setSeries(series);
            barEntity.setData(dataBean);


            return new ResponseEntity(barEntity, HttpStatus.OK);
        } else if (StringUtil.strPos(bitype, "oneb")) {
            List<FxjoutputEntity> list = hana.getbilist(hanasql);
            com.base.modules.bidata.bientity.barEntity.DataBean dataBean = new barEntity.DataBean();
            List<String> categories = new ArrayList<>();
            List<String> series1 = new ArrayList<>();
            List<String> series2 = new ArrayList<>();
            for (FxjoutputEntity t : list) {
                categories.add(t.getFxjx1());
                series1.add(t.getFxjx2());
                series2.add(t.getFxjx3());
            }
            dataBean.setCategories(categories);//x

            List<com.base.modules.bidata.bientity.barEntity.DataBean.SeriesBean> series = new ArrayList<>();

            com.base.modules.bidata.bientity.barEntity.DataBean.SeriesBean seriesBean1 = new com.base.modules.bidata.bientity.barEntity.DataBean.SeriesBean();
            seriesBean1.setData(series1);
            seriesBean1.setName("上架");
            series.add(seriesBean1);

//			org.jeecg.modules.bidata.bientity.barEntity.DataBean.SeriesBean seriesBean2 = new org.jeecg.modules.bidata.bientity.barEntity.DataBean.SeriesBean();
//			seriesBean2.setData(series2);
//			seriesBean2.setName("下架");
//			series.add(seriesBean2);

            dataBean.setSeries(series);
            barEntity.setData(dataBean);


            return new ResponseEntity(barEntity, HttpStatus.OK);
        } else if (StringUtil.strPos(bitype, "data")) {
            String outstring = "0";

            outstring = hana.getBione(hanasql);
            if (StringUtil.isEmpty(outstring)) {
                outstring = "0";
            }
            try {
                dataEntity.setData(Double.parseDouble(outstring));
            } catch (Exception e) {

            }
            return new ResponseEntity(dataEntity, HttpStatus.OK);
        } else if (StringUtil.strPos(bitype, "label")) {
            labelEntity.DataBean dataBean = new labelEntity.DataBean();

            List<FxjoutputEntity> list = hana.getbilist(hanasql);
            dataBean.setLabel(label);
            dataBean.setData(list.get(0).getFxjx2());
            dataBean.setValue(list.get(0).getFxjx1());
            labelEntity.setData(dataBean);

            return new ResponseEntity(labelEntity, HttpStatus.OK);
        } else if (StringUtil.strPos(bitype, "pie")) {

            List<FxjoutputEntity> list = hana.getbilist(hanasql);
            List<com.base.modules.bidata.bientity.pieDate.DataBean> dataBeans = new ArrayList<>();
            for (FxjoutputEntity t : list) {
                com.base.modules.bidata.bientity.pieDate.DataBean dataBean = new pieDate.DataBean();
                dataBean.setName(t.getFxjx1());
                dataBean.setValue(t.getFxjx2());
                dataBeans.add(dataBean);
            }

            pieDate.setData(dataBeans);
            return new ResponseEntity(pieDate, HttpStatus.OK);
        } else if (StringUtil.strPos(bitype, "list")) {
            List<com.base.modules.bidata.bientity.listEntity.DataBean> listdata = new ArrayList<>();
            List<FxjoutputEntity> list = hana.getbilist(hanasql);
            for (FxjoutputEntity t : list) {
                com.base.modules.bidata.bientity.listEntity.DataBean dataBean = new listEntity.DataBean();
                dataBean.setValue1(t.getFxjx1() + "");
                if ("下架".equals(t.getFxjx2())) {
                    dataBean.setValue2(t.getFxjx2());
                } else {
                    dataBean.setValue2(t.getFxjx2());
                }
                dataBean.setValue3(t.getFxjx3());
                dataBean.setValue4(t.getFxjx4());
                dataBean.setValue5(t.getFxjx5());
                dataBean.setValue6(t.getFxjx6());
                listdata.add(dataBean);
            }
            listEntity.setData(listdata);
            return new ResponseEntity(listEntity, HttpStatus.OK);
        } else if (StringUtil.strPos(bitype, "per")) {
            perEntity.DataBean dataBean = new perEntity.DataBean();
            dataBean.setMax(100);
            dataBean.setMin(0);
            dataBean.setUnit("%");
            dataBean.setName(label);
            String per = hana.getBione(hanasql);
            if (StringUtil.isEmpty(per)) {
                per = "0";
            }
            dataBean.setValue(Integer.parseInt(per));
            perEntity.setData(dataBean);
            return new ResponseEntity(perEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity("无数据", HttpStatus.OK);
        }
    }

    @ApiOperation(value = "getbiotherdate", produces = "application/json", httpMethod = "GET")
    @RequestMapping(value = "/getbiotherdate/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getbiother(@ApiParam(required = true, name = "id", value = "id") @PathVariable("id") String id, HttpServletRequest request) {
        // 验证
        String label = "";
        String bitype = "";
        String bicode = id;

        zdOnlCgreportHead head = onlCgreportHeadService.getById(id);
        bitype = head.getRepository();
        label = head.getName();
        labelEntity labelEntity = new labelEntity();
        dataEntity dataEntity = new dataEntity();
        barEntity barEntity = new barEntity();//柱状图

        FpqEntity fpqEntity = new FpqEntity();//数字
        zztEntity zztEntity = new zztEntity();//柱状图
        zxtEntity zxtEntity = new zxtEntity();//折线图
        ybpEntity ybpEntity = new ybpEntity();//仪表盘
        hxtEntity hxtEntity = new hxtEntity();//环形图
        bgEntity bgEntity = new bgEntity();//表格
        jdtEntity jdtEntity = new jdtEntity();//进度条
        zflEntity zflEntity = new zflEntity();//
        bnzflEntity bnzflEntity = new bnzflEntity();
        dyflEntity dyflEntity = new dyflEntity();
        dtflEntity dtflEntity = new dtflEntity();


        pieDate pieDate = new pieDate();
        perEntity perEntity = new perEntity();
        listEntity listEntity = new listEntity();
        otherutil other = new otherutil();

        if (StringUtil.strPos(bitype, "zzt")) {//柱状图
            List<FxjoutputEntity> list = other.getbilist(bicode);
            com.base.modules.bidata.bientity.zztEntity.DBean dataBean = new zztEntity.DBean();
            List<String> categories = new ArrayList<>();
            List<String> series1 = new ArrayList<>();
            List<String> series2 = new ArrayList<>();
            for (FxjoutputEntity t : list) {
                categories.add(t.getFxjx1());
                series1.add(t.getFxjx2());
                series2.add(t.getFxjx3());
            }
            dataBean.setCategories(categories);
            List<com.base.modules.bidata.bientity.zztEntity.DBean.SeriesBan> series = new ArrayList<>();
            com.base.modules.bidata.bientity.zztEntity.DBean.SeriesBan seriesBean1 = new com.base.modules.bidata.bientity.zztEntity.DBean.SeriesBan();
            seriesBean1.setData(series1);
            seriesBean1.setName("数量");
            series.add(seriesBean1);
//            com.base.modules.bidata.bientity.zztEntity.DBean.SeriesBan seriesBean2 = new com.base.modules.bidata.bientity.zztEntity.DBean.SeriesBan();
//            seriesBean2.setData(series2);
//            seriesBean2.setName("下架");
//            series.add(seriesBean2);
            dataBean.setSeries(series);
            return new ResponseEntity(dataBean, HttpStatus.OK);
        } else if (StringUtil.strPos(bitype, "zxt")) {//折线图
            List<FxjoutputEntity> list = other.getbilist(bicode);
            com.base.modules.bidata.bientity.zxtEntity.Bean dataBean = new zxtEntity.Bean();
            List<String> categories = new ArrayList<>();
            List<Long> series1 = new ArrayList<>();
            List<Long> series2 = new ArrayList<>();
            for (FxjoutputEntity t : list) {
                categories.add(t.getFxjx1());
                series1.add(Long.parseLong(t.getFxjx2()));
//                series2.add(Long.parseLong(t.getFxjx3()));
            }

            dataBean.setCategories(categories);
            List<com.base.modules.bidata.bientity.zxtEntity.Bean.SeriesB> series = new ArrayList<>();
            com.base.modules.bidata.bientity.zxtEntity.Bean.SeriesB seriesBean1 = new com.base.modules.bidata.bientity.zxtEntity.Bean.SeriesB();
            seriesBean1.setData(series1);
            seriesBean1.setName("数量");
            series.add(seriesBean1);
//            com.base.modules.bidata.bientity.zxtEntity.Bean.SeriesB seriesBean2 = new com.base.modules.bidata.bientity.zxtEntity.Bean.SeriesB();
//            seriesBean2.setData(series2);
//            seriesBean2.setName("下架");
//            series.add(seriesBean2);
            dataBean.setSeries(series);
            return new ResponseEntity(dataBean, HttpStatus.OK);

        } else if (StringUtil.strPos(bitype, "fpq")) {
            String outstring = "0";
            outstring = other.getBione(bicode);
            if (StringUtil.isEmpty(outstring)) {
                outstring = "0";
            }
            try {
                fpqEntity.setValue(Double.parseDouble(outstring));
            } catch (Exception e) {

            }
            return new ResponseEntity(fpqEntity, HttpStatus.OK);

        } else if (StringUtil.strPos(bitype, "zfl")) {
            String outstring = "0";
            outstring = other.getBione(bicode);
            if (StringUtil.isEmpty(outstring)) {
                outstring = "0";
            }
            try {
                zflEntity.setValue(Long.parseLong(outstring));
            } catch (Exception e) {

            }
            return new ResponseEntity(zflEntity, HttpStatus.OK);
        } else if (StringUtil.strPos(bitype, "bnz")) {
            String outstring = "0";
            outstring = other.getBione(bicode);
            if (StringUtil.isEmpty(outstring)) {
                outstring = "0";
            }
            try {
                bnzflEntity.setValue(Long.parseLong(outstring));
            } catch (Exception e) {

            }
            return new ResponseEntity(bnzflEntity, HttpStatus.OK);

        } else if (StringUtil.strPos(bitype, "dtfl")) {
            String outstring = "0";
            outstring = other.getBione(bicode);
            if (StringUtil.isEmpty(outstring)) {
                outstring = "0";
            }
            try {
                dtflEntity.setValue(Long.parseLong(outstring));
            } catch (Exception e) {

            }
            return new ResponseEntity(dtflEntity, HttpStatus.OK);

        } else if (StringUtil.strPos(bitype, "dyfl")) {
            String outstring = "0";
            outstring = other.getBione(bicode);
            if (StringUtil.isEmpty(outstring)) {
                outstring = "0";
            }
            try {
                dyflEntity.setValue(Long.parseLong(outstring));
            } catch (Exception e) {

            }
            return new ResponseEntity(dyflEntity, HttpStatus.OK);

        } else if (StringUtil.strPos(bitype, "bg")) {
            String outstring = "0";
            List<FxjoutputEntity> list = other.getbilist(bicode);
            if (StringUtil.isEmpty(outstring)) {
                outstring = "0";
            }
            List<com.base.modules.bidata.bientity.bgEntity.DataBean> ll = new ArrayList<com.base.modules.bidata.bientity.bgEntity.DataBean>();

            try {
                for (FxjoutputEntity f : list) {
                    com.base.modules.bidata.bientity.bgEntity.DataBean bean = new bgEntity.DataBean();
                    bean.setType1(f.getFxjx1());
                    bean.setType2(f.getFxjx2());
                    ll.add(bean);
                }
            } catch (Exception e) {

            }
            return new ResponseEntity(ll, HttpStatus.OK);
        } else if (StringUtil.strPos(bitype, "hxt")) {//环形图
            hxtEntity.DataBean dataBean = new hxtEntity.DataBean();
            List<FxjoutputEntity> list = other.getbilist(bicode);  //这是bicode   不是bitype
            dataBean.setLabel(label);
            dataBean.setData(list.get(0).getFxjx2());
            dataBean.setValue(list.get(0).getFxjx1());
            return new ResponseEntity(dataBean, HttpStatus.OK);


        } else if (StringUtil.strPos(bitype, "label")) {//进度条
            labelEntity.DataBean dataBean = new labelEntity.DataBean();
            List<FxjoutputEntity> list = other.getbilist(bicode);
            dataBean.setLabel(label);
            dataBean.setData(list.get(0).getFxjx2());
            dataBean.setValue(list.get(0).getFxjx1());
            labelEntity.setData(dataBean);

            return new ResponseEntity(labelEntity, HttpStatus.OK);

        } else if (StringUtil.strPos(bitype, "ybp")) {//仪表盘
            ybpEntity.DBean dBean = new ybpEntity.DBean();
            dBean.setMax(100);
            dBean.setMin(1);
            dBean.setUnit("%");
            dBean.setLabel("空余储位");
            //dBean.setValue(44);
            String ybp = other.getBione(bicode);
            if (StringUtil.isEmpty(ybp)) {
                ybp = "0";
            }
            dBean.setValue(Integer.parseInt(ybp));
            return new ResponseEntity(dBean, HttpStatus.OK);


        } else if (StringUtil.strPos(bitype, "list")) {
            List<com.base.modules.bidata.bientity.listEntity.DataBean> listdata = new ArrayList<>();
            List<FxjoutputEntity> list = other.getbilist(bicode);

            for (FxjoutputEntity t : list) {

                com.base.modules.bidata.bientity.listEntity.DataBean dataBean = new listEntity.DataBean();
                dataBean.setValue1(t.getFxjx1() + "");
                if ("下架".equals(t.getFxjx2())) {
                    dataBean.setValue2(t.getFxjx2());

                } else {
                    dataBean.setValue2(t.getFxjx2());
                }
                dataBean.setValue3(t.getFxjx3());
                dataBean.setValue4(t.getFxjx4());
                dataBean.setValue5(t.getFxjx5());
                dataBean.setValue6(t.getFxjx6());
                listdata.add(dataBean);

            }
            listEntity.setData(listdata);
            return new ResponseEntity(listEntity, HttpStatus.OK);
        } else if (StringUtil.strPos(bitype, "per")) {//仪表盘
//			hanasql = Constantswm2.biper11;
            perEntity.DataBean dataBean = new perEntity.DataBean();
            dataBean.setMax(100);
            dataBean.setMin(0);
            dataBean.setUnit("%");
            dataBean.setName(label);
            String per = other.getBione(bicode);
            if (StringUtil.isEmpty(per)) {
                per = "0";
            }
            dataBean.setValue(Integer.parseInt(per));
            perEntity.setData(dataBean);
            return new ResponseEntity(perEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity("无数据", HttpStatus.OK);
        }

    }


}
