package com.base.modules.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2022年05月12日 9:26
 */
public class PageUtil {

    /**
     * 分页函数
     * @param pageNo   当前页数
     * @param pageSize  每一页的数据条数
     * @param list  要进行分页的数据列表
     * @return  当前页要展示的数据
     */
    private static Page getPages(Integer pageNo, Integer pageSize, List list) {
        Page page = new Page();
        int size = list.size();
        if(size == 0) {
            return null;
        }
        if(pageSize > size) {
            pageSize = size;
        }
        // 求出最大页数，防止pageNo越界
        int maxPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
        if(pageNo > maxPage) {
            pageNo = maxPage;
        }
        // 当前页第一条数据的下标
        int curIdx = pageNo > 1 ? (pageNo - 1) * pageSize : 0;

        List pageList = new ArrayList();
        // 将当前页的数据放进pageList
        for(int i = 0; i < pageSize && curIdx + i < size; i++) {
            pageList.add(list.get(curIdx + i));
        }
        page.setCurrent(pageNo).setSize(pageSize).setTotal(list.size()).setRecords(pageList);
        return page;
    }
}
