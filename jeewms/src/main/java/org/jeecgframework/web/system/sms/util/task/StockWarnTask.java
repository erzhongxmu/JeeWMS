package org.jeecgframework.web.system.sms.util.task;

import com.zzjee.wm.entity.WvStockEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.entity.TSSmsEntity;
import org.jeecgframework.web.system.sms.entity.TSSmsSqlEntity;
import org.jeecgframework.web.system.sms.service.TSSmsServiceI;
import org.jeecgframework.web.system.sms.util.TuiSongMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Package org.jeecgframework.web.system.sms.util.task
 * @date 2021/7/13 15:11
 * @description
 */
@Service
public class StockWarnTask {

        @Autowired
        private SystemService systemService;
        @Autowired
        private TSSmsServiceI tsSmsServiceI;

        @Scheduled(cron="0 0 8 * * ?")
        public void run() {
            //查询库存
            List<Map<String,String>> list= systemService.findListbySql("SELECT t.goods_id ,t.goods_name from  (SELECT s.goods_id,s.shp_ming_cheng goods_name,SUM(base_goodscount) num , g.min_stock from wv_stock s left join md_goods g on s.goods_id = g.shp_bian_ma \n" +
                    "GROUP BY s.goods_id) t where t.num <= t.min_stock");

            //查询到所有低于最小库存的商品
            //给admin发型消息
            Date date = new Date();
        if (list.size() > 0) {
            List<TSSmsEntity> tsSmsEntityList = new ArrayList<>();
            for (Map<String, String> map : list) {
                TSSmsEntity tsSmsEntity = new TSSmsEntity();
                tsSmsEntity.setEsTitle("库存预警提醒");
                tsSmsEntity.setEsSender("system");
                tsSmsEntity.setEsReceiver("admin");
                tsSmsEntity.setEsType("3");
                tsSmsEntity.setEsStatus("1");
                tsSmsEntity.setEsContent(map.get("goods_id")+"货品库存已到达库存临界点，请及时补货");
                tsSmsEntity.setEsSendtime(date);
                tsSmsEntityList.add(tsSmsEntity);
            }
            tsSmsServiceI.batchSave(tsSmsEntityList);
        }


    }
}
