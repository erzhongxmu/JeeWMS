package com.zzjee.wm.api;

import com.zzjee.ba.entity.BaGoodsCategoryEntity;
import com.zzjee.ba.entity.BaGoodsTypeEntity;
import com.zzjee.ba.service.BaGoodsCategoryServiceI;
import com.zzjee.ba.service.BaGoodsTypeServiceI;
import com.zzjee.md.entity.*;
import com.zzjee.md.service.MdBinServiceI;
import com.zzjee.md.service.MdCusServiceI;
import com.zzjee.md.service.MdGoodsServiceI;
import com.zzjee.md.service.MdSupServiceI;
import com.zzjee.wm.entity.*;
import com.zzjee.wm.page.WmImNoticeHPage;
import com.zzjee.wm.page.WmOmNoticeHPage;
import com.zzjee.wm.service.WmImNoticeHServiceI;
import com.zzjee.wm.service.WmOmNoticeHServiceI;
import com.zzjee.wmutil.wmUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.jwt.util.JwtUtils;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.util.Constants;
import org.jeecgframework.web.system.sms.util.TuiSongMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.zzjee.wm.controller
 * @date 2021/7/22 13:39
 * @description
 */
@RestController
@RequestMapping("/wmsPdagetApiController")
public class WmsPdagetApiController {

}
