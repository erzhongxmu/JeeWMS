package com.zzjee.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/tmsapi")
public class tmsapi {
    @Autowired
    private SystemService systemService;
    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;
    @RequestMapping(value = "/getdoc/{doctype}", method = RequestMethod.GET)
    @ResponseBody
//    @ApiOperation(value="根据doctype获取单据",notes="根据doctype获取单据",httpMethod="GET",produces="application/json")
    public ResponseEntity<?> getdocbydoctype(@ApiParam(required=true,name="doctype",value="doctype") @PathVariable("doctype") String doctype, @RequestParam(value="username", required=false) String username, @RequestParam(value="docid", required=false)String docid,@RequestParam(value="searchstr1", required=false)String searchstr1, @RequestParam(value="searchstr2", required=false)String searchstr2, @RequestParam(value="searchstr3", required=false)String searchstr3, @RequestParam(value="searchstr4", required=false)String searchstr4, @RequestParam(value="searchstr5", required=false)String searchstr5) {
        ResultDO D0 = new  ResultDO();
        if(StringUtil.isNotEmpty(docid)){
            docid = docid.trim();
        }

            D0.setOK(true);
            List<wmientity> reslit = new ArrayList<wmientity>();
        List<wmi1entity> reslitkc = new ArrayList<wmi1entity>();

                if(StringUtil.isEmpty(docid)){
                    D0.setOK(false);
                    D0.setErrorMsg("输入单号");
                    return new ResponseEntity(D0, HttpStatus.OK);
                }
        wmientity wmientity = new wmientity();
        wmientity.setWmi1List(reslitkc);
        reslit.add(wmientity);
            D0.setObj(reslit);


        return new ResponseEntity(D0, HttpStatus.OK);
    }


    @RequestMapping(value = "/docltcreate", method = RequestMethod.GET)
            //consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="创建上下架")
    public ResponseEntity<?>  createlt( @RequestParam(required=false ,value="doctype")  String doctype,@RequestParam(value="docid", required=false) String docid, @RequestParam(value="tSapLtttstr", required=false) String  tSapLtttstr,
                                     UriComponentsBuilder uriBuilder) {
        ResultDO D0 = new ResultDO();
        String msg = null;
        D0.setErrorMsg("保存成功");
        D0.setOK(true);
        return new ResponseEntity(D0, HttpStatus.OK);
    }

}
