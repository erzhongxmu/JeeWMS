import com.base.modules.jeewms.entity.WvStock;
import com.base.modules.jeewms.service.IWvStockSttService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class TestDemo {

    @Autowired
    IWvStockSttService wvStockSttService;

    @Test
    public void test() {
        List<WvStock> stockByKwAndGoodsId = wvStockSttService.getStockByKwAndGoodsId("1001");
        stockByKwAndGoodsId.forEach(wvStock -> {
            System.out.println(wvStock);
        });
    }
}
