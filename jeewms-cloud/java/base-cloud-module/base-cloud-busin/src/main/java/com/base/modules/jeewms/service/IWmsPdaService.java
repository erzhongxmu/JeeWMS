package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.WmImNoticeI;
import com.base.modules.jeewms.entity.WmInQmI;
import com.base.modules.jeewms.pdaapi.ApiEntity;
import com.base.modules.jeewms.pdaapi.ApiresEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IWmsPdaService {

    public IPage<WmImNoticeI> queryYanshouList(String searchVal, Integer pageNo, Integer pageSize, HttpServletRequest req);

    public Result<?> getU8Caigoudaohuosh(String id) throws Exception;

    public Result<?> getU8Caigouruku(String id) throws Exception;

    public Result<?> getU8Chengpinruku(String id) throws Exception;

    public Result<?> getU8Xiaoshouchuku(String id) throws Exception;

    public Result<?> getU8Cailiaochuku(String id) throws Exception;

    public Result<?> getU8Diaobodansh(String id) throws Exception;

    public Result<?> getU8Diaobodan(String id) throws Exception;

    public Result<?> getU8Pandiandan(String id) throws Exception;

    public IPage<ApiresEntity> queryListType01(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType02(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType03(Page<ApiresEntity> page, HashMap<String, String> querymap);
    public IPage<ApiresEntity> queryListType03pltn(Page<ApiresEntity> page, HashMap<String, String> querymap);
    public IPage<ApiresEntity> queryListType26(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType04(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType05(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType06(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType07(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType08(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType09(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType10(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType11(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType12(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType13(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType14(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType15(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType16(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType18(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType19swgs(Page<ApiresEntity> page, HashMap<String, String> querymap);
    public IPage<ApiresEntity> queryListType19pltn(Page<ApiresEntity> page, HashMap<String, String> querymap);
    public IPage<ApiresEntity> queryListType19(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType20(Page<ApiresEntity> page, HashMap<String, String> querymap);
    public IPage<ApiresEntity> queryListType20pltn(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType21(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType22(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType23(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType24(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public IPage<ApiresEntity> queryListType25(Page<ApiresEntity> page, HashMap<String, String> querymap);
    public IPage<ApiresEntity> queryListType32(Page<ApiresEntity> page, HashMap<String, String> querymap);
    public IPage<ApiresEntity> queryListType33(Page<ApiresEntity> page, HashMap<String, String> querymap);

    public List<ApiEntity> queryCkxx();

    public List<ApiEntity> querySpkcxx(HashMap<String, String> querymap);

    public boolean createU8Chengpinruku(WmImNoticeI wmImNoticeI) throws Exception;

    public boolean createU8Caigouruku(WmInQmI wmInQmI) throws Exception;

}
