package com.base.modules.jeewms.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeewms.entity.WmImNoticeI;
import com.base.modules.jeewms.pdaapi.ApiEntity;
import com.base.modules.jeewms.pdaapi.ApiresEntity;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface WmsPdaMapper {


    public IPage<WmImNoticeI> queryYanshouList(Page page, @Param("searchVal") String searchVal);

    public List<ApiresEntity> queryListType01(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType02(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType03(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);
    public List<ApiresEntity> queryListType03pltn(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);
    public List<ApiresEntity> queryListType26(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType04(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType05(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType06(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType07(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType08(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType09(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType10(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType11(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType12(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType13(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType14(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType15(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType16(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType18(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType19(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);
    public List<ApiresEntity> queryListType19swgs(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);
    public List<ApiresEntity> queryListType19pltn(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType20(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);
    public List<ApiresEntity> queryListType20pltn(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType21(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType22(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType23(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType24(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiresEntity> queryListType25(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);
    public List<ApiresEntity> queryListType32(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);
    public List<ApiresEntity> queryListType33(Page<ApiresEntity> page, @Param("querMap") HashMap<String, String> querymap);

    public List<ApiEntity> queryCkxx();

    public List<ApiEntity> querySpkcxx(@Param("querMap") HashMap<String, String> querymap);
}
