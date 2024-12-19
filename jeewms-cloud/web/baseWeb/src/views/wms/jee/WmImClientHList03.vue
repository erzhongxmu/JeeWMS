<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
     <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="8" :lg="11" :md="12" :sm="24">
            <a-form-item :label="$t('预计到货时间')">
              <j-date
                :placeholder="$t('开始日期')"
                class="query-group-cust"
                v-model="queryParam.imData_begin"
              ></j-date>
              <span>-</span>
              <j-date
                :placeholder="$t('结束日期')"
                class="query-group-cust"
                v-model="queryParam.imData_end"
              ></j-date>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('入库单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.noticeId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('供应商')">
              <j-popup
                v-model="queryParam.supCode"
                code="md_sup"
                org-fields="zhong_wen_qch,gys_bian_ma"
                dest-fields="supName,supCode"
                field="supCode"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户')">
              <j-popup
                v-model="queryParam.cusCode"
                code="cus_jiancheng"
                org-fields="zhong_wen_qch,ke_hu_bian_ma"
                dest-fields="cusName,cusCode"
                field="cusCode"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('单据状态')">
              <j-dict-select-tag
                v-model="queryParam.imSta"
                dictCode="stock_state"
                :placeholder="$t('请选择')"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('主PO号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.imBeizhu"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('销售单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.u8ReturnCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('内部发票号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.astreanum"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
               <!-- v-has="'WmImClientHList01:search'" -->
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'WmImClientHList01:search'" >{{$t('查询')}}</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button
        @click="handleAdd('09')"
        type="primary"
        icon="plus"
        v-has="'WmImClientHList03:add'"
      >{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls($t('收货订单'))" v-has="'WmImClientHList03:export'">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import" v-has="'WmImClientHList03:import'">{{$t('导入')}}</a-button>
      </a-upload>
      <!--      <a-button type="primary" icon="redo" @click="syncU8dd('3')">同步生产订单</a-button>-->
      <!--      <a-button type="primary" icon="redo" @click="syncU8dd('1')">同步采购订单</a-button>-->
      <!--      <a-button type="primary" icon="redo" @click="syncU8dd('2')">同步到货单</a-button>-->
      <!--      <a-button type="primary" icon="upload" @click="syncCgrk()">提交采购入库</a-button>-->
      <!--      <a-button type="primary" icon="upload" @click="syncScrk()">提交生产入库</a-button>-->
      <!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
      <!--        <a-menu slot="overlay">-->
      <!--      <a-button key="1" @click="batchDel" v-has="'imClient:batchDelete'"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>-->
      <!--      <a-button type="primary" icon="plus" @click="handlePrintbq" >打印托标签</a-button>-->
      <!--      <a-button type="primary" icon="plus" @click="handleRePrintbq" >重新打印</a-button>-->
      <!--        </a-menu>-->
      <!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
      <!--      </a-dropdown>-->
    </div>
    <j-modal
      :visible="isShow"
      title="选择同步日期(不选默认同步所有)"
      @ok="isShowOk"
      :width="400"
      @cancel="isShow=false"
      :confirm-loading="Loading2"
    >
      <div style="display: flex;align-items: center;margin-bottom: 20px">
        <j-date placeholder="请选择日期" style="width: 80%" v-model="valueShow"></j-date>
      </div>
    </j-modal>
    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>
        {{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :rowClassName="setRowClsaa"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt
            style="max-width:80px;font-size: 12px;font-style: italic;"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)"
          >{{$t('下载')}}</a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <span v-has="'WmImClientHList03:check'">
            <a @click="handleCheck(record)" >{{$t('查看')}}</a>
            <a-divider type="vertical" v-if="record.imSta=='计划中'" />
          </span>
          <span v-has="'WmImClientHList03:edit'">
            <a
            @click="handleEdit(record)"
            v-if="record.imSta=='计划中'"
            >{{$t('编辑')}}</a>
            <a-divider type="vertical"/>
          </span>
          <!-- <span>
            <a-divider type="vertical" v-has="'imClient:printOrder'" />
            <a @click="inspectionSheet(record.id)" v-has="'imClient:exportAccept'">{{$t('导出检验单')}}</a>
            <a-divider type="vertical" v-if="record.imSta=='计划中'" />
            <a @click="UploadIMG(record)" v-if="record.imSta=='计划中'">{{$t('上传检验图片')}}</a>
          </span>
          <a-divider type="vertical" v-has="'imClient:exportBq'" />
          <a @click="goPrintOrder(record)" v-has="'imClient:printOrder'">打印通知单</a>
          <a-divider type="vertical" v-has="'imClient:delete'" />
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a v-has="'imClient:delete'">{{$t('删除')}}</a>
          </a-popconfirm> -->


          <a-dropdown>
            <a class="ant-dropdown-link" >
              {{$t('更多')}}
              <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="inspectionSheet(record.id)" v-has="'WmImClientHList03:exportExpress'">{{$t('导出检验单')}}</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="UploadIMG(record)" v-has="'WmImClientHList03:uploadImg'">{{$t('上传检验图片')}}</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="goPrintOrder(record)" v-has="'WmImClientHList03:printOrder'">{{$t('打印通知单')}}</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a v-has="'WmImClientHList03:delete'">{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
          <!-- <a-divider type="vertical" v-has="'imClient:edit'" />
          <a   @click="print(record)" v-has="'imClient:exportBq'">打印标签</a>
          
          <a-divider type="vertical" v-has="'imClient:printOrder'" />
          <a @click="exportBill('ys',record.id)" v-has="'imClient:exportAccept'">导出验收单</a>
          <a-divider type="vertical" v-has="'imClient:exportAccept'" />
          <a @click="goPrintPutIn(record)" v-has="'imClient:printIn'">打印入库</a>
          <a-divider type="vertical" v-has="'imClient:printIn'" />
          <a @click="exportBill('rk',record.id)" v-has="'imClient:exportIn'">导出入库</a>-->

          <!--          <a-divider type="vertical" />-->

          <!--          <a-dropdown>-->
          <!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
          <!--            <a-menu slot="overlay">-->
          <!--              <a-menu-item>-->
          <!--                -->
          <!--              </a-menu-item>-->
          <!--            </a-menu>-->
          <!--          </a-dropdown>-->
        </span>
      </a-table>
    </div>

    <wmImClientH-modal ref="modalForm" @ok="modalFormOk"></wmImClientH-modal>

    <j-modal
      :visible="visibledy1"
      title="打印标签"
      @ok="handlegetBqCode"
      :width="600"
      @cancel="visibledy1=false"
    >
      <div style="display: flex;align-items: center;margin-bottom: 20px">
        <span>请选择打印数量：</span>
        <span>
          <a-input-number v-model="bqnum" :min="1" :max="200"></a-input-number>
        </span>
      </div>
    </j-modal>

    <j-modal
      :visible="visibledy3"
      title="打印标签"
      @ok="handlegetBqCode1"
      :width="400"
      @cancel="visibledy3=false"
    >
      <div style="display: flex;align-items: center;margin-bottom: 20px">
        <span>请输入编码：</span>
        <span>
          <a-input v-model="bqcode"></a-input>
        </span>
      </div>
    </j-modal>

    <j-modal
      title="打印标签"
      :width="800"
      :visible="visibledy10"
      :footer="null"
      @cancel="canclePrint2()"
    >
      <i class="l-btn-left" style="cursor: pointer" v-print="'#printMe'">
        <a-icon type="printer" style="font-family: normal" />打印
      </i>
      <div class="printBody" id="printMe">
        <div class="item" v-for="(item,index) in data" :key="index">
          <div class="title">
            批次号:
            <p>{{item.query01}}</p>
            <p>{{item.query02}}</p>
          </div>
          <div class="title">
            存货编码:
            <p>{{item.query03}}</p>
          </div>
          <div class="title">
            存货名称:
            <p>{{item.query04}}</p>
          </div>
          <div class="title">
            产品规格:
            <p>{{item.query05}}</p>
          </div>
          <div class="qrcode">
            <div class="qrcode1">
              <vue-qr
                :text="item.query03"
                :margin="0"
                colorDark="#000"
                colorLight="#fff"
                :size="100"
              ></vue-qr>
              <p>{{item.query03}}</p>
            </div>
            <div class="qrcode1">
              <vue-qr
                :text="item.query06"
                :margin="0"
                colorDark="#000"
                colorLight="#fff"
                :size="100"
              ></vue-qr>
              <p>{{item.query06}}</p>
            </div>
          </div>
        </div>
      </div>
    </j-modal>
    <j-modal
      :visible="IMGModalShow"
      :title="$t('上传图片')"
      @ok="IMGModalOk"
      :width="400"
      @cancel="IMGModalShow=false;IMGModalId='';IMGModal=''"
    >
      <j-image-upload v-model="IMGModal"></j-image-upload>
    </j-modal>
    <j-modal
      title="托盘标签打印"
      width="500px"
      :visible="visibledy2"
      :footer="null"
      @cancel="canclePrint()"
    >
      <i class="l-btn-left" style="cursor: pointer" v-print="'#printbq'">
        <a-icon type="printer" style="font-family: normal" />打印
      </i>
      <div id="printbq">
        <div v-for="(item,key) in printIds" :key="key" style="margin-bottom:105px;">
          &nbsp;
          <vue-qr :text="item" :size="150" :margin="0"></vue-qr>
          <!--<img :src="'http://47.113.229.131:9999/jeewms/qrcode/get?q='+item" style="width:80px;height:80px;vertical-align:middle;">-->
          <br />
          <span style="font-size:30px;">{{item}}</span>
        </div>
      </div>
    </j-modal>

    <j-modal
      title="入库单打印"
      width="1000px"
      :visible="visible"
      :footer="null"
      @cancel="canclePrintPutIn"
    >
      <i class="l-btn-left" style="cursor: pointer" v-print="'#printPutIn'">
        <a-icon type="printer" style="font-family: normal" />打印
      </i>
      <table id="printPutIn" style="margin: 0 auto">
        <tbody>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="9"
              class="xl68"
              width="168"
              style="width:126pt"
            >公司地址：{{printData.sysCompanyCode?printData.sysCompanyCode:''}}</td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              height="40"
              class="xl67"
              style="height:30.0pt"
            >到货日期：{{printData.imData?printData.imData:''}}</td>
            <td
              colspan="3"
              class="xl68"
              width="168"
              style="width:126pt"
            >预约单号：{{printData.noticeId?printData.noticeId:''}}</td>
            <td colspan="2" style="mso-ignore:colspan"></td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              height="40"
              class="xl68"
              width="242"
              style="height:30.0pt;width:182pt"
            >货主名称：{{printData.cusCode_dictText?printData.cusCode_dictText:''}}</td>
            <!--          <td colspan="3" height="40" class="xl68" width="242" style="height:30.0pt;width:182pt">供应商：{{printData.supName?printData.supName:''}}</td>-->
            <td></td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              class="xl68"
              width="337"
              style="width:252pt"
            >备注：{{printData.imBeizhu?printData.imBeizhu:''}}</td>
            <td colspan="3">采购单号：{{printData.imCusCode?printData.imCusCode:''}}</td>
            <td></td>
          </tr>

          <tr height="33" style="mso-height-source:userset;height:25.05pt">
            <td class="xl65" style="border:1.0pt solid black;text-align: center">序号</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">商品编码</td>
            <td
              height="33"
              class="xl65"
              style="height:25.05pt;border:1.0pt solid black;text-align: center"
            >商品</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">生产日期</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">收货温度</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">单位</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">数量</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">重量</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">容积</td>
            <td></td>
          </tr>

          <tr
            height="33"
            style="mso-height-source:userset;height:50px"
            v-for="(item,key) in printDataList"
            :key="key"
          >
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{key+1}}</td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center;word-break:break-all;"
            >
              <span style="word-break:break-all;width: auto;font-size: 14pt">{{item.goodsCode}}</span>
            </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsName}}</td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center"
            >{{item.goodsPrdData}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsCode}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsUnit}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsCount}}</td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center"
            >{{item.goodsWeight}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsFvol}}</td>
            <td></td>
          </tr>

          <tr height="20" style="height:25.0pt">
            <td
              height="20"
              class="xl66"
              colspan="8"
              style="height:15.0pt;mso-ignore:colspan;text-align: justify"
            >
              制单人：
              <span
                style="mso-spacerun:yes"
              >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
              合计重量：{{totalWeight}}
              <span
                style="mso-spacerun:yes"
              >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
              合计数量：{{totalNum}}
              <span
                style="mso-spacerun:yes"
              >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>签字（盖章）
            </td>
          </tr>
        </tbody>
      </table>
    </j-modal>

    <j-modal
      title="入库通知单打印"
      width="1000px"
      :visible="visible1"
      :footer="null"
      @cancel="canclePrintOrder"
    >
      <i class="l-btn-left" style="cursor: pointer" v-print="'#printOrder'">
        <a-icon type="printer" style="font-family: normal" />打印
      </i>
      <div style="font-size: 18pt;text-align: center">仓储管理系统入库通知单</div>
      <table id="printOrder" style="margin: 0 auto">
        <tbody>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td colspan="4"></td>
            <td colspan="4"></td>
            <td rowspan="3" class="xl69">
              <vue-qr :text="printData.noticeId" :size="80" :margin="0"></vue-qr>
              <!--            <img :src="'http://47.113.229.131:9999/jeewms/qrcode/get?q='+printData.noticeId" style="width:80px;height:80px;vertical-align:right">-->
            </td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              height="40"
              class="xl68"
              width="242"
              style="height:30.0pt;width:182pt"
            >货主名称：{{printData.cusCode_dictText?printData.cusCode_dictText:''}}</td>
            <!--          <td colspan="4" height="40" class="xl68" width="242" style="height:30.0pt;width:182pt">供应商：{{printData.supName?printData.supName:''}}</td>-->
            <td></td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              class="xl68"
              width="168"
              style="width:126pt"
            >单号:{{printData.imCusCode?printData.imCusCode:''}}</td>
            <td colspan="2" style="mso-ignore:colspan"></td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              class="xl68"
              width="337"
              style="width:252pt"
            >备注：{{printData.imBeizhu?printData.imBeizhu:''}}</td>
            <td colspan="4">WMS单号：{{printData.noticeId?printData.noticeId:''}}</td>
            <td></td>
          </tr>

          <tr height="33" style="mso-height-source:userset;height:25.05pt">
            <td class="xl65" style="border:1.0pt solid black;text-align: center">商品编码</td>

            <td
              height="33"
              class="xl65"
              style="height:25.05pt;border:1.0pt solid black;text-align: center"
            >商品</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">规格</td>

            <td class="xl65" style="border:1.0pt solid black;text-align: center">单位</td>
            <!--          <td class="xl65" style="border:1.0pt solid black;text-align: center">生产日期</td>-->
            <!--          <td class="xl65" style="border:1.0pt solid black;text-align: center">保质期</td>-->

            <td class="xl65" style="border:1.0pt solid black;text-align: center">数量</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">体积</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">重量</td>

            <td class="xl65" style="border:1.0pt solid black;text-align: center">库位</td>

            <td class="xl65" style="border:1.0pt solid black;text-align: center">二维码</td>

            <td></td>
          </tr>

          <tr
            height="33"
            style="mso-height-source:userset;height:50px"
            v-for="(item,key) in printDataList"
            :key="key"
          >
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsCode}}</td>

            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center;word-break:break-all;"
            >
              <span style="word-break:break-all;width: auto;font-size: 14pt">{{item.goodsName}}</span>
            </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.shpGuiGe}}</td>

            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsUnit}}</td>
            <!--          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsPrdData}}</td>-->
            <!--          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.bzhiQi}}</td>-->

            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsCount}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsFvol}}</td>

            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center"
            >{{item.goodsWeight}}</td>

            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.binPlan}}</td>

            <td class="xl65" align="center" valign="middle" style="border:1.0pt solid black">
              <vue-qr :text="item.goodsCode" :size="40" :margin="0"></vue-qr>
            </td>
            <td></td>
          </tr>

          <tr height="20" style="height:25.0pt">
            <td
              height="20"
              class="xl66"
              colspan="8"
              style="height:15.0pt;mso-ignore:colspan;text-align: justify"
            >
              主管：
              <span
                style="mso-spacerun:yes"
              >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>制单：
              <span
                style="mso-spacerun:yes"
              >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>仓管：
              <span
                style="mso-spacerun:yes"
              >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>叉车：
              <span style="mso-spacerun:yes"></span>
            </td>
            <td></td>
          </tr>
        </tbody>
      </table>
    </j-modal>
    <!-- v-show="false" v-if="isShow2"-->
    <!-- 入库通知单 -->
    <EncasementModules ref="EncasementModules" />
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmImClientHModal from './modules/WmImClientHModal'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JDate from '@/components/jeecg/JDate.vue'
import { getAction, httpAction } from '@/api/manage'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import '@/assets/less/TableExpand.less'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import EncasementModules from '@/views/wms/jee/pltn/EncasementModules'
import vueQr from 'vue-qr'
import { INSPECTION_SHEET } from '@/utils/PLTN_EXCEL/index' // WMS导出检验单，包装领料单

export default {
  name: 'WmImNoticeHList',
  mixins: [JeecgListMixin, commonTableRowClass, listCheckMixin],
  components: {
    vueQr,
    JDictSelectTag,
    JDate,
    WmImClientHModal,
    JSearchSelectTag,
    EncasementModules
  },
  data() {
    return {
      queryParam: {
        orderTypeCode: '09'
      },
      isorter: {
        column: 'imSta',
        order: 'desc'
      },
      isShow2: false,
      data: [],
      description: 'wm_im_notice_h管理页面',
      visible: false,
      visible1: false,
      valueShow: '',
      isShow: false,
      djType: '',
      Loading2: false,
      printData: {
        imData: '',
        noticeId: '',
        sysCompanyCode: '',
        cusCode_dictText: '',
        supName: '',
        imBeizhu: '',
        imCusCode: '',
        createTime: ''
      },
      printDataList: [],
      totalWeight: 0,
      totalNum: 0,
      // 表头
      columns: [
        {
          title: this.$t('主PO号'),
          align: 'left',
          dataIndex: 'imBeizhu',
          sorter: true
        },
        {
          title: this.$t('客户'),
          align: 'left',
          dataIndex: 'cusCode_dictText',
          sorter: true
        },
        // {
        //   title: '客户',
        //   align: 'left',
        //   dataIndex: 'imclientcode_dictText'
        // },
        {
          title: this.$t('供应商'),
          align: 'left',
          dataIndex: 'supCode_dictText',
          sorter: true
        },
        // {
        //   title: this.$t('订单类型'),
        //   align: 'left',
        //   dataIndex: 'orderType',
        //   customRender: (text) => {
        //     return text == '06' ? this.$t('采购入库') : this.$t('入库')
        //   }
        // },
        {
          title: this.$t('单据状态'),
          align: 'left',
          dataIndex: 'imSta',
          customRender: text => {
            return text ? this.$t(text) : ''
          },
          sorter: true
        },
        {
          title: this.$t('预计到货时间'),
          align: 'left',
          dataIndex: 'imData',
          customRender: text => {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
          sorter: true
        },

        {
          title: this.$t('销售单号'),
          align: 'left',
          dataIndex: 'u8ReturnCode',
          sorter: true,
          width: 100,
          checked:true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer'
              }
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text?(text.length > 20?text.substring(0,20)+'...':text):''}
            </a-tooltip>
          )
        },
        {
          title: this.$t('内部发票号'),
          align: 'left',
          dataIndex: 'astreanum',
          sorter: true,
          width: 100,
          checked:true,
          customCell: () => {
            return {
              style: {
                width: '100px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer'
              }
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text?(text.length > 20?text.substring(0,20)+'...':text):''}
            </a-tooltip>
          )
        },
        // {
        //   title: '司机',
        //   align: 'left',
        //   dataIndex: 'imCarDri'
        // },
        // {
        //   title: '司机电话',
        //   align: 'left',
        //   dataIndex: 'imCarMobile'
        // },
        // {
        //   title: '车号',
        //   align: 'left',
        //   dataIndex: 'imCarNo'
        // },
        // {
        //   title: '备注',
        //   align: 'left',
        //   dataIndex: 'imBeizhu'
        // },
        {
          title: this.$t('入库单号'),
          align: 'left',
          dataIndex: 'noticeId',
          sorter: true
        },
        // {
        //   title:this.$t('创建人'),
        //   align: 'left',
        //   dataIndex: 'createBy',
        //   sorter: true
        // },
        {
          title:this.$t('采购员'),
          align: 'left',
          dataIndex: 'purchasename',
          sorter: true
        },
        {
          title: this.$t('创建日期'),
          align: 'left',
          dataIndex: 'createTime',
          customRender: text => {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
          sorter: true
        },
        // {
        //   title: '订单号',
        //   align: 'left',
        //   dataIndex: 'imCusCode'
        // },

        // {
        //   title: '月台',
        //   align: 'left',
        //   dataIndex: 'platformCode_dictText'
        // },
        // {
        //   title: '附件',
        //   align: 'left',
        //   dataIndex: 'fuJian',
        //   scopedSlots: { customRender: 'fileSlot' }
        // },
        //{
        //  title: this.$t('关联类型'),
        //  align: 'left',
        //  dataIndex: 'piClass',
        //  sorter: true,
        //},
        //{
        //  title: this.$t('关联单号'),
        //  align: 'left',
        //  dataIndex: 'u8Dhcode',
        //  sorter: true,
        //},
        {
          title: this.$t('检验图片'),
          align: 'left',
          dataIndex: 'fuJian',
          scopedSlots: { customRender: 'imgSlot' }
        },
        {
          title: this.$t('订单类型'),
          align: 'left',
          dataIndex: 'orderTypes',
          sorter: true
          // customRender: (text) => {
          //   return text == '06' ? this.$t('采购入库') : this.$t('入库')
          // }
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/jeewms/wmImNoticeH/cusList',
        delete: '/jeewms/wmImNoticeH/delete',
        deleteBatch: '/jeewms/wmImNoticeH/deleteBatch',
        exportXlsUrl: '/jeewms/wmImNoticeH/exportXls',
        importExcelUrl: 'jeewms/wmImNoticeH/importExcel',
        listWmImNoticeIByMainId: '/jeewms/wmImNoticeH/queryWmImNoticeIByMainId',
        doPrintRkd: '/jeewms/wmImNoticeH/doPrintRkd', //导出入库单
        doPrintYsd: '/jeewms/wmImNoticeH/doPrintYsd', //导出验收单
        doPrintBq: '/jeewms/wmImNoticeH/doPrintBq', //导出验收单
        getU8Scdd: '/jeewms/wmImNoticeH/getU8Scdd', //同步到货单
        getU8Dhd: '/jeewms/wmImNoticeH/getU8Dhd', //同步到货单
        getU8Cgdd: '/jeewms/wmImNoticeH/getU8Cgdd', //同步采购订单
        getU8Cgrk: '/jeewms/wmsPda/getU8Caigouruku', //提交采购入库
        getU8Scrk: '/jeewms/wmsPda/getU8Chengpinruku' //提交生产入库
      },
      dictOptions: {},
      visibledy1: false,
      visibledy2: false,
      visibledy3: false,
      visibledy10: false,
      printIds: [],
      bqnum: 1,
      bqcode: '',
      IMGModalShow: false,
      IMGModal: '',
      IMGModalId: 0
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    searchReset() {
      this.queryParam = {orderTypeCode: "09"}
      this.loadData()
    },
    IMGModalOk2() {
      this.IMGModalShow2 = false
      this.IMGModal = ''
    },
    lookImg(e) {
      window.open(e)
    },
    UploadIMG(e) {
      this.IMGModalShow = true
      this.IMGModalId = e.id
    },
    IMGModalOk() {
      let data = {
        id: this.IMGModalId,
        fuJian: this.IMGModal
      }
      httpAction('/jeewms/wmImNoticeH/edit', data, 'put').then(res => {
        if (res.success) {
          this.IMGModalShow = false
          this.loadData()
          this.$message.success(this.$t('操作成功'))
        } else {
          this.$message.error(this.$t('操作失败'))
        }
      })
    },
    // WMS-检验单导出
    inspectionSheet(id) {
      getAction('/jeewms/wmImNoticeH/queryplqnById', { id: id }).then(res => {
        INSPECTION_SHEET(res.result)
      })
    },
    handleAdd(e) {
      this.$refs.modalForm.add(e)
    },
    canclePrint2() {
      this.data = []
      this.visibledy10 = false
    },
    print(record) {
      this.visibledy10 = true
      getAction('/jeewms/wmImNoticeH/doPrintBqList', { id: record.id }).then(res => {
        this.data = res.result
      })
    },
    isShowOk() {
      this.Loading2 = true
      var param = {
        date: this.valueShow
      }
      if (this.djType == 1) {
        getAction(this.url.getU8Cgdd, param).then(res => {
          this.Loading2 = false
          if (res.success) {
            this.loadData()
            this.isShow = false
            this.$message.success('采购订单同步成功！')
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
        })
      }
      if (this.djType == 2) {
        getAction(this.url.getU8Dhd, param).then(res => {
          this.Loading2 = false
          if (res.success) {
            this.loadData()
            this.isShow = false
            this.$message.success('到货单同步成功！')
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
        })
      }
      if (this.djType == 3) {
        getAction(this.url.getU8Scdd, param).then(res => {
          this.Loading2 = false
          if (res.success) {
            this.loadData()
            this.isShow = false
            this.$message.success('生产订单同步成功！')
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
        })
      }
    },
    syncU8dd(type) {
      this.isShow = true
      this.djType = type
    },
    syncCgrk() {
      if (this.selectedRowKeys.length != 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      var that = this
      let id = this.selectedRowKeys[0]
      getAction(that.url.getU8Cgrk, { id: id }).then(res => {
        if (res.success) {
          that.$message.success('操作成功！')
          that.loadData()
        } else {
          that.$message.warning(this.$t('操作失败'))
        }
      })
    },
    syncScrk() {
      if (this.selectedRowKeys.length != 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      var that = this
      let id = this.selectedRowKeys[0]
      getAction(that.url.getU8Scrk, { id: id }).then(res => {
        if (res.success) {
          that.$message.success('操作成功！')
          that.loadData()
        } else {
          that.$message.warning(this.$t('操作失败'))
        }
      })
    },
    initDictConfig() {},
    handlePrintbq() {
      this.visibledy1 = true
      this.bqnum = 1
    },
    handleRePrintbq() {
      this.visibledy3 = true
      this.bqcode = ''
    },
    canclePrint() {
      this.visibledy2 = false
    },
    handlegetBqCode1() {
      if (!this.bqcode) {
        this.$message.error('请输入编码')
        return
      }
      this.printIds = []
      this.printIds.push(this.bqcode)
      this.visibledy2 = true
    },
    handlegetBqCode() {
      this.$http
        .get('/chief/wmsTray/createBq', {
          params: {
            num: this.bqnum + ''
          }
        })
        .then(res => {
          if (res.success) {
            //            this.$message.success("保存成功")
            this.printIds = res.result
            this.visibledy2 = true
          } else {
            this.$message.error(this.$t('操作失败'))
          }
        })
    },
    //根据入库单id获取单据商品列表
    getListWmImNoticeIByMainId(id) {
      var params = {
        id: id
      }
      getAction(this.url.listWmImNoticeIByMainId, params).then(res => {
        if (res.success) {
          this.printDataList = res.result
          this.printDataList.forEach(item => {
            if (item.goodsWeight) {
              this.totalWeight += parseInt(item.goodsWeight)
            }
            if (item.goodsCount) {
              this.totalNum += parseInt(item.goodsCount)
            }
          })
        }
      })
    },
    //打开打印入库单
    goPrintPutIn(record) {
      this.printData = record
      this.getListWmImNoticeIByMainId(record.noticeId)
      this.visible = true
    },
    //关闭打印入库单
    canclePrintPutIn() {
      this.printData = {
        imData: '',
        noticeId: '',
        sysCompanyCode: '',
        cusCode_dictText: '',
        supName: '',
        imBeizhu: '',
        imCusCode: '',
        createTime: ''
      }
      this.printDataList = []
      this.totalWeight = 0
      this.totalNum = 0
      this.visible = false
    },

    //打开打印入库通知单
    goPrintOrder(record) {
      this.$refs.EncasementModules.onShow(record)
      // this.printData = record
      // this.getListWmImNoticeIByMainId(record.noticeId)
      // this.visible1 = true
    },

    //关闭打印入库通知单单
    canclePrintOrder() {
      this.printData = {
        imData: '',
        noticeId: '',
        sysCompanyCode: '',
        cusCode_dictText: '',
        supName: '',
        imBeizhu: '',
        imCusCode: '',
        createTime: ''
      }
      this.visible1 = false
    },
    exportBill(type, id) {
      if (type == 'rk') {
        //return `${window._CONFIG['domianURL']}/${this.url.downReceiveExcel+'?id='+id}`;
        window.open(window._CONFIG['domianURL'] + this.url.doPrintRkd + '?id=' + id)
      } else if (type == 'ys') {
        window.open(window._CONFIG['domianURL'] + this.url.doPrintYsd + '?id=' + id)
      } else if (type == 'bq') {
        window.open(window._CONFIG['domianURL'] + this.url.doPrintBq + '?id=' + id)
      } else {
      }
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
>>> .ant-table-row-cell-break-word {
  text-align: left !important;
}
/*>>>.ant-table-row-cell-break-word span{*/
/*  padding-left: 15px !important;*/
/*}*/
>>> .ant-table-thead .ant-table-row-cell-break-word {
  text-align: center !important;
}
>>> .ant-table-thead .ant-table-row-cell-break-word span {
  padding-left: 0 !important;
}
.printBody {
  width: 800px;
  padding: 1px;
}
.printBody .item {
  width: 500px;
  height: 270px;
  font-size: 18px;
  font-weight: 600;
  color: #000;
}
.printBody .item .title p {
  display: inline;
  margin-left: 10px;
}
.qrcode1 {
  margin-top: 10px;
  width: 50%;
  display: inline-block;
}
.qrcode1 div {
  width: 100px;
  height: 100px;
  background: #f0f;
}
.qrcode1 div span {
  width: 100%;
  display: block;
}
.query-group-cust{
  width: 44% !important;
}
</style>