package org.jeecg.common.bpm.api.fallback;

import com.base.common.api.vo.Result;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.bpm.api.IBpmBaseExtAPI;


/**
 * 进入fallback的方法 检查是否token未设置
 */
@Slf4j
public class BpmBaseExtAPIFallback implements IBpmBaseExtAPI {

    @Setter
    private Throwable cause;

    @Override
    public Result<String> startMutilProcess(String flowCode, String id, String formUrl, String formUrlMobile,
        String username, String jsonData) throws Exception {
        return null;
    }

    @Override
    public Result<String> startDesFormMutilProcess(String flowCode, String id, String formUrl, String formUrlMobile,
        String username, String jsonData) throws Exception {
        return null;
    }

    @Override
    public Result<String> saveMutilProcessDraft(String flowCode, String id, String formUrl, String formUrlMobile,
        String username, String jsonData) throws Exception {
        return null;
    }
}
