<template>
  <a-card class="j-inner-table-wrapper" :bordered="false">
    <!-- 查询区域 begin -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('出库单号')">
              <a-input v-model="queryParam.omNoticeId" :placeholder="$t('请输入')" />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('子PO号')">
              <a-input v-model="queryParam.remarks" :placeholder="$t('请输入')" />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户')">
              <j-popup
                v-model="queryParam.omBeizhu"
                code="cus_jiancheng"
                org-fields="zhong_wen_qch,ke_hu_bian_ma"
                dest-fields="omBeizhu,cusCode"
                field="omBeizhu"
              />
<!-- 
              <a-input
              v-model="queryParam.omBeizhu"
              :placeholder="$t('请输入')"
            ></a-input> -->
            </a-form-item>
          </a-col>
            <!-- <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('货主名称')">
                <j-search-select-tag
                  v-model="queryParam.cusCode"
                  dict="md_cus,zhong_wen_qch,ke_hu_bian_ma"
                 :placeholder="$t('请选择')"
                />
              </a-form-item>
            </a-col> -->
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('要求交货时间')">
                <j-date
                  class="query-group-cust"
                  v-model="queryParam.delvData_begin"
                  :placeholder="$t('开始日期')"
                />
                <span class="query-group-split-cust"></span>
                <j-date
                  class="query-group-cust"
                  v-model="queryParam.delvData_end"
                  :placeholder="$t('结束日期')"
                />
              </a-form-item>
            </a-col>

            <!--            <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
            <!--              <a-form-item label="收货人">-->
            <!--            <a-input v-model="queryParam.delvMember" placeholder="请输入收货人"/>-->
            <!--          </a-form-item>-->
            <!--        </a-col>-->
            <!--          <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
            <!--            <a-form-item label="收货人电话">-->
            <!--          <a-input v-model="queryParam.delvMobile" placeholder="请输入收货人电话"/>-->
            <!--        </a-form-item>-->
            <!--      </a-col>-->
            <!--          <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
            <!--            <a-form-item label="承运人">-->
            <!--          <j-dict-select-tag v-model="queryParam.reMember" placeholder="请选择承运人" dictCode="tms_kd"/>-->
            <!--        </a-form-item>-->
            <!--      </a-col>-->
            <!--          <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
            <!--            <a-form-item label="承运人车号">-->
            <!--          <a-input v-model="queryParam.reCarno" placeholder="请输入承运人车号"/>-->
            <!--        </a-form-item>-->
            <!--      </a-col>-->
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('状态')">
                <a-input v-model="queryParam.omSta"  :placeholder="$t('请输入')"/>
              </a-form-item>
            </a-col>
            <!--          <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
            <!--            <a-form-item label="出货单号">-->
            <!--          <a-input v-model="queryParam.omNoticeId" placeholder="请输入出货单号"/>-->
            <!--        </a-form-item>-->
            <!--      </a-col>-->
            <!--          <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
            <!--            <a-form-item label="三方货主">-->
            <!--          <a-input v-model="queryParam.ocusCode" placeholder="请输入三方货主"/>-->
            <!--        </a-form-item>-->
            <!--      </a-col>-->
            <!--          <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
            <!--            <a-form-item label="订单号">-->
            <!--          <a-input v-model="queryParam.imCusCode" placeholder="请输入订单号"/>-->
            <!--        </a-form-item>-->
            <!--      </a-col>-->
            <!--          <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
            <!--            <a-form-item label="打印状态">-->
            <!--          <a-input v-model="queryParam.printStatus" placeholder="请输入打印状态"/>-->
            <!--        </a-form-item>-->
            <!--      </a-col>-->
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">{{$t('查询')}}</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域 end -->

    <!-- 操作按钮区域 begin -->
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="handAdd('13')" v-has="'WmOmMessageHList02:add'">{{$t('添加')}}</a-button>
      <a-button
        type="primary"
        icon="download"
        @click="handleExportXls($t('生产领料(倒冲)'))"
        v-has="'WmOmMessageHList02:export'"
      >{{$t('导出')}}</a-button>
      <a-button
        type="primary"
        icon="download"
        @click="handleExportModal($t('生产领料(倒冲)'))"
        v-has="'WmOmMessageHList02:exportModal'"
      >{{$t('模板下载')}}</a-button>
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
      <!--        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>-->
      <!--      </a-upload>-->
      <a-upload
        v-has="'WmOmMessageHList02:import'"
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <!-- 国生接口同步按钮 -->
      <!-- <span>
        <a-button type="primary" @click="synchronization('GS_ProductionPicking')">同步生产领料</a-button>
      </span> -->

      <!-- <a-button type="primary" icon="redo" @click="syncU8dd('1')">同步发货单</a-button>-->
      <!-- <a-button type="primary" icon="redo" @click="syncU8dd('2')">同步材料单</a-button> -->
      <!-- <a-button type="primary" icon="upload" @click="syncU8Ckd('1')">提交销售出库</a-button>-->
      <!-- <a-button type="primary" icon="upload" @click="syncU8Ckd('2')">提交材料出库</a-button> -->
      <!-- <a-button @click="batchDel"><a-icon type="delete" v-has="'WmOmMessageHList02:batchDelete'"/>{{$t('批量删除')}}</a-button>-->
    </div>
    <!-- 操作按钮区域 end -->
    <j-modal
      :visible="isShow"
      title="选择同步日期(不选默认同步所有)"
      @ok="syncShowOk"
      :width="400"
      @cancel="isShow=false"
      :confirm-loading="Loading2"
    >
      <div style="display: flex;align-items: center;margin-bottom: 20px">
        <j-date placeholder="请选择日期" style="width: 80%" v-model="valueShow"></j-date>
      </div>
    </j-modal>
    <!-- table区域 begin -->
    <div>
      <a-alert type="info" showIcon style="margin-bottom: 16px;">
        <template slot="message">
          <span>{{$t('已选择')}}</span>
          <a style="font-weight: 600;padding: 0 4px;">{{ selectedRowKeys.length }}</a>
          <span>{{$t('项目')}}</span>
          <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
        </template>
      </a-alert>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :loading="loading"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :expandedRowKeys="expandedRowKeys"
        :rowSelection="{selectedRowKeys, onChange: onSelectChange}"
        @expand="handleExpand"
        @change="handleTableChange"
        :rowClassName="setRowClsaa"
      >
        <!-- 内嵌table区域 begin -->
        <!--        <template slot="expandedRowRender" slot-scope="record">-->
        <!--          <a-tabs tabPosition="top">-->
        <!--            <a-tab-pane tab="出货详情" key="wmOmNoticeI" forceRender>-->
        <!--              <wm-om-client-h-sub-table :record="record"/>-->
        <!--            </a-tab-pane>-->
        <!--          </a-tabs>-->
        <!--        </template>-->
        <!-- 内嵌table区域 end -->

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>

        <template slot="imgSlot" slot-scope="text">
          <div style="font-size: 12px;font-style: italic;">
            <span v-if="!text">{{$t('无图片')}}</span>
            <img v-else :src="getImgView(text)" alt style="max-width:80px;height:25px;" />
          </div>
        </template>

        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            ghost
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)"
          >
            <span>{{$t('下载')}}</span>
          </a-button>
        </template>

        <template slot="action" slot-scope="text, record">
          <span v-has="'WmOmMessageHList02:finish'" v-if="record.omSta !='已完成'">
            <a-popconfirm title="确定完成吗?" @confirm="() => accomplish(record)">
              <a v-if="record.omSta !='已完成'">{{$t('完成')}}</a>
            </a-popconfirm>
            <a-divider type="vertical"  />
          </span>
          <a @click="handleCheck(record)" v-has="'WmOmMessageHList02:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'WmOmMessageHList02:check'" v-if="record.omSta !='已完成'"/>
          <a v-if="record.omSta !='已完成'" @click="handleEdit(record)" v-has="'WmOmMessageHList02:update'">{{$t('编辑')}}</a>
          <!-- WMS -->
          <!-- <span>
            <a-divider type="vertical" v-has="'imClient:printOrder'" />
            <a @click="inspectionSheet(record.id)" v-has="'imClient:exportAccept'">{{$t('导出领料单')}}</a>
          </span>
          <span>
            <a-divider type="vertical" v-has="'WmOmMessageHList02:update'" v-if="record.omSta !='已完成'" />
            <a @click="GS_Print(record,'JH')" v-has="'WmOmMessageHList02:update'">{{$t('拣货单')}}</a> -->
            <!-- <a-divider type="vertical" v-has="'WmOmMessageHList02:update'" v-if="record.omSta !='已完成'" />
            <a @click="GS_Print(record,'CK')" v-has="'WmOmMessageHList02:update'">出库单</a> -->
          <!-- </span> -->
          <!--<a-divider type="vertical" v-has="'WmOmMessageHList02:update'" />
          <a @click="print(record)" v-has="'WmOmMessageHList02:update'">{{$t('打印')}}</a>
          <a-divider type="vertical" v-has="'WmOmMessageHList02:printOut'" />
          <a @click="openPrint(record,'jh')" v-has="'WmOmMessageHList02:printJh'">拣货单</a>
          <a-divider type="vertical" v-has="'WmOmMessageHList02:printJh'" />
          <a @click="openPrint(record,'jh')" v-has="'WmOmMessageHList02:printZs'">追溯单</a>
          <a-divider type="vertical" v-has="'WmOmMessageHList02:printZs'" />
          <a @click="exportBill('ck',record.id)" v-has="'WmOmMessageHList02:exportOut'">导出出库单</a>
          <a-divider type="vertical" v-has="'WmOmMessageHList02:exportOut'" />
          <a @click="openPrint(record,'ck')" v-has="'WmOmMessageHList02:printOut'">打印出库单</a> -->
          <!-- <a-divider type="vertical" v-has="'WmOmMessageHList02:update'" />
          <a-popconfirm :title="$t('你确定吗?')" @confirm="handleDelete(record.id)">
            <a>{{$t('删除')}}</a>
          </a-popconfirm> -->

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link" >
              {{$t('更多')}}
              <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="inspectionSheet(record)" v-has="'WmOmMessageHList02:exportAccept'">{{$t('导出领料单')}}</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="GS_Print(record,'JH')" v-has="'WmOmMessageHList02:printJh'">{{$t('拣货单')}}</a>
              </a-menu-item>
              <a-menu-item >
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a  v-has="'WmOmMessageHList02:delete'">{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </template>
      </a-table>
    </div>
    <!-- table区域 end -->
    <!-- 打印弹窗区域 -->
    
    <SellOutModules ref="SellOutModules"/>
    <sellChooseModules ref="sellChooseModules"/>

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
            <p>{{item.query01}}</p>
            <p>{{item.query02}}</p>
          </div>
          <div class="title">
            <p>{{item.query03}}</p>
            <!-- <p>{{item.query04}}</p> -->
          </div>
          <div class="title">
            <p style="margin-right:80px">{{item.query04}}</p>
            <vue-qr :text="item.qrcode" :margin="0" colorDark="#000" colorLight="#fff" :size="120"></vue-qr>
          </div>
        </div>
      </div>
    </j-modal>

    <j-modal
      title="拣货单打印"
      width="1000px"
      :visible="visible"
      :footer="null"
      @cancel="canclePrint('jh')"
    >
      <i class="l-btn-left" style="cursor: pointer" v-print="'#printPutIn'">
        <a-icon type="printer" style="font-family: normal" />打印
      </i>
      <div style="font-size: 18pt;text-align: center">仓储管理系统拣货单</div>
      <table id="printPutIn" style="margin: 0 auto">
        <tbody>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="5"
              class="xl68"
              width="168"
              style="width:126pt"
            >日期：{{printData.kprq?printData.kprq:''}}</td>
            <td
              colspan="4"
              class="xl68"
              width="168"
              style="width:126pt"
            >单号：{{printData.noticeid?printData.noticeid:''}}</td>
            <td colspan="1" style="mso-ignore:colspan"></td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="5"
              height="40"
              class="xl68"
              width="242"
              style="height:30.0pt;width:182pt"
            >货主名称：{{printData.cusname?printData.cusname:''}}</td>
            <!--          <td colspan="4" height="40" class="xl68" width="242" style="height:30.0pt;width:182pt">货主：{{printData.wmOmNoticeHPage.delvMember?printData.wmOmNoticeHPage.delvMember:''}}</td>-->
            <td
              colspan="4"
            >WMS单号：{{printData.wmOmNoticeHPage.omNoticeId?printData.wmOmNoticeHPage.omNoticeId:''}}</td>
            <td rowspan="2" class="xl69">
              <img
                :src="'http://47.113.229.131:9999/jeewms/qrcode/get?q='+printData.noticeid"
                style="width:80px;height:80px;vertical-align:middle;"
              />
            </td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="5"
              class="xl68"
              width="337"
              style="width:252pt"
            >地址：{{printData.wmOmNoticeHPage.delvAddr?printData.wmOmNoticeHPage.delvAddr:''}}</td>
            <td colspan="4">拣货提醒：{{printData.jianhuoremark}}</td>
            <td></td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td colspan="5" class="xl68" width="337" style="width:252pt">总体积：{{printData.tijisum}}</td>
            <td colspan="4">总重量：{{printData.zhlsum}}</td>
            <td></td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="5"
              class="xl68"
              width="337"
              style="width:252pt"
            >备注：{{printData.wmOmNoticeHPage.omBeizhu?printData.wmOmNoticeHPage.omBeizhu:''}}</td>
            <td></td>
          </tr>
          <tr height="33" style="mso-height-source:userset;height:25.05pt">
            <td class="xl65" style="border:1.0pt solid black;text-align: center">库位</td>
            <td
              height="33"
              class="xl65"
              style="height:25.05pt;border:1.0pt solid black;text-align: center"
            >商品</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">单位</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">生产日期</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">数量</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">重量</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">体积</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">规格</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">拣货</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">托盘</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">二维码</td>
            <td></td>
          </tr>
          <tr
            height="33"
            style="mso-height-source:userset;height:50px"
            v-for="(item,key) in printDataList"
            :key="key"
          >
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.binId}}</td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center;word-break:break-all;"
            >
              <span style="word-break:break-all;width: auto;font-size: 14pt">{{item.goodsName}}</span>
            </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsUnit}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.createTime}}</td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center"
            >{{item.baseGoodscount}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.tinZhl}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.tinTj}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.shpGuiGe}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.pickNotice}}</td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center"
            >{{item.tinId}}/{{item.baozhiqi}}</td>
            <td class="xl65" align="center" valign="middle" style="border:1.0pt solid black">
              <img
                :src="'http://47.113.229.131:9999/jeewms/qrcode/get?q='+item.goodsId"
                style="width:40px;height:40px;vertical-align:middle;"
              />
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

    <j-modal
      title="出库单打印"
      width="1000px"
      :visible="visible1"
      :footer="null"
      @cancel="canclePrint('ck')"
    >
      <i class="l-btn-left" style="cursor: pointer" v-print="'#printPutOut'">
        <a-icon type="printer" style="font-family: normal" />打印
      </i>
      <div style="font-size: 18pt;text-align: center">仓储管理系统出库单</div>
      <table id="printPutOut" style="margin: 0 auto">
        <tbody>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              class="xl68"
              width="168"
              style="width:126pt"
            >公司地址：{{printData.area?printData.area:''}}</td>
            <td
              colspan="3"
              height="40"
              class="xl67"
              style="height:30.0pt"
            >出库日期：{{printData.printingTime?printData.printingTime:''}}</td>
            <td
              colspan="3"
              class="xl68"
              width="168"
              style="width:126pt"
            >出库单号：{{printData.omNoticeId?printData.omNoticeId:''}}</td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              height="40"
              class="xl68"
              width="242"
              style="height:30.0pt;width:182pt"
            >货主名称：{{printData.zhongWenQch?printData.zhongWenQch:''}}</td>
            <td
              colspan="3"
              height="40"
              class="xl68"
              width="242"
              style="height:30.0pt;width:182pt"
            >收货人：{{printData.delvMember?printData.delvMember:''}}</td>
            <td
              colspan="2"
              height="40"
              class="xl68"
              width="242"
              style="height:30.0pt;width:182pt"
            >电话：{{printData.delvMobile?printData.delvMobile:''}}</td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              class="xl68"
              width="337"
              style="width:252pt"
            >备注：{{printData.omBeizhu?printData.omBeizhu:''}}</td>
            <td colspan="3">货主采购单号：{{printData.imCusCode?printData.imCusCode:''}}</td>
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
            <td class="xl65" style="border:1.0pt solid black;text-align: center">箱数</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">数量</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">重量</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">容积</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">库存</td>
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
              <span style="word-break:break-all;width: auto;font-size: 14pt">{{item.goodsId}}</span>
            </td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center"
            >{{item.shpMingCheng}}</td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center"
            >{{item.manufactureDate}}</td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center"
            >{{item.numberOfCases}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.number}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.weight}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.volume}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.cerconNo}}</td>
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
              合计重量：{{printData.totalWeight}}
              <span
                style="mso-spacerun:yes"
              >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
              合计数量：{{printData.totalQuantity}}
              <span
                style="mso-spacerun:yes"
              >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>货主签字（盖章）
            </td>
          </tr>
        </tbody>
      </table>
    </j-modal>

    <!-- 表单区域 -->
    <wm-om-message-h-modal ref="modalForm" @ok="modalFormOk" />
    <export-spin v-if="exportOk"></export-spin>
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmOmMessageHModal from './modules/WmOmMessageHModal'
import WmOmClientHSubTable from './subTables/WmOmClientHSubTable'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import { getCurrentTime } from '@/utils/util'
import { getAction, downFile } from '@/api/manage'
import { initDictOptions, filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import JDate from '@/components/jeecg/JDate.vue'
import '@/assets/less/TableExpand.less'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import ExportSpin from '@/components/ExportSpin/ExportSpin'
import vueQr from 'vue-qr'
import SYNC from '@/views/wms/jee/sync/index.js' // 同步接口
import  sellChooseModules from '@/views/wms/jee/gsModules/sellChooseModules'
import  SellOutModules from '@/views/wms/jee/gsModules/sellOutModules'
import { STOCK_REQUISITION} from '@/utils/PLTN_EXCEL/index' // WMS导出检验单，包装领料单
export default {
  name: 'WmOmNoticeHList',
  mixins: [JeecgListMixin, commonTableRowClass, listCheckMixin],
  components: {
    WmOmMessageHModal,
    WmOmClientHSubTable,
    JDate,
    JDictSelectTag,
    JSearchSelectTag,
    ExportSpin,
    vueQr,
    sellChooseModules,
    SellOutModules
  },
  data() {
    return {
      queryParam: {
        orderTypeCode: '13'
      },
      isorter: {
        column: 'omSta',
        order: 'desc'
      },
      description: '出库管理列表管理页面',
      visible: false,
      visible1: false,
      valueShow: '',
      isShow: false,
      djType: '',
      Loading2: false,
      printData: {
        wmOmNoticeHPage: {}
      },
      printDataList: [],
      // 表头
      columns: [
        {
          title: this.$t('子PO号'),
          align: 'left',
          dataIndex: 'remarks',
          sorter: true,
        },
        {
          title:this.$t('出库单号'),
          align: 'left',
          dataIndex: 'omNoticeId',
          sorter: true
        },
        {
          title: this.$t('客户'),
          align: 'left',
          dataIndex: 'omBeizhu',
          sorter: true
        },
        {
          title:this.$t('采购包装日期'),
          align: 'left',
          dataIndex: 'packingDate',
          sorter: true
        },
        // {
        //   title:this.$t('订单类型'),
        //   align: 'left',
        //   dataIndex: 'orderTypeCode_dictText'
        // },
        // {
        //   title: '创建人名称'this.$t('操作'),
        //   align: 'left',
        //   dataIndex: 'createName'
        // },
        {
          title:this.$t('创建日期'),
          align: 'left',
          dataIndex: 'createTime',
          sorter: true
        },
        {
          title:this.$t('期限'),
          align: 'left',
          dataIndex: 'stopDate',
          sorter: true
        },
        {
          title: this.$t('状态'),
          align: 'left',
          dataIndex: 'omSta',
          sorter: true
        },
        // {
        //   title: this.$t('关联单号'),
        //   align: 'left',
        //   dataIndex: 'u8Djcode2',
        //   sorter: true,
        // },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          width: 350,
          scopedSlots: { customRender: 'action' }
        }
        // {
        //   title: '#',
        //   key: 'rowIndex',
        //   width: 60,
        //   align: 'left',
        //   customRender: (t, r, index) => parseInt(index) + 1
        // },
        // {
        //   title: '所属部门',
        //   align: 'left',
        //   dataIndex: 'sysOrgCode',
        // },
        // {
        //   title: '所属公司',
        //   align: 'left',
        //   dataIndex: 'sysCompanyCode_dictText'
        // },
        // {
        //   title: '货主名称',
        //   align: 'left',
        //   dataIndex: 'cusCode_dictText'
        // },
        // {
        //   title: '要求交货时间',
        //   align: 'left',
        //   dataIndex: 'delvData',
        // },
        // {
        //   title: '收货人',
        //   align: 'left',
        //   dataIndex: 'delvMember',
        // },
        // {
        //   title: '收货人电话',
        //   align: 'left',
        //   dataIndex: 'delvMobile',
        // },
        // {
        //   title: '收货人地址',
        //   align: 'left',
        //   dataIndex: 'delvAddr',
        // },
        // {
        //   title: '承运人',
        //   align: 'left',
        //   dataIndex: 'reMember_dictText'
        // },
        // {
        //   title: '承运人电话',
        //   align: 'left',
        //   dataIndex: 'reMobile',
        // },
        // {
        //   title: '承运人车号',
        //   align: 'left',
        //   dataIndex: 'reCarno',
        // },
        // {
        //   title: '发货月台',
        //   align: 'left',
        //   dataIndex: 'omPlatNo_dictText'
        // },
        // {
        //   title: '状态',
        //   align: 'left',
        //   dataIndex: 'omSta',
        // },
        // {
        //   title: '出货单号',
        //   align: 'left',
        //   dataIndex: 'omNoticeId',
        // },
        // {
        //   title: '附件',
        //   align: 'left',
        //   dataIndex: 'fuJian',
        //   scopedSlots: {customRender: 'fileSlot'}
        // },
        // {
        //   title: '订单类型',
        //   align: 'left',
        //   dataIndex: 'orderTypeCode',
        // },
        // {
        //   title: '三方货主',
        //   align: 'left',
        //   dataIndex: 'ocusCode_dictText'
        // },
        // {
        //   title: '三方货主名称',
        //   align: 'left',
        //   dataIndex: 'ocusName',
        // },
        // {
        //   title: '订单号',
        //   align: 'left',
        //   dataIndex: 'imCusCode',
        // },
        // {
        //   title: '打印状态',
        //   align: 'left',
        //   dataIndex: 'printStatus',
        // },
        // {
        //   title: '对接单据类型',
        //   align: 'left',
        //   dataIndex: 'piClass',
        // },
        // {
        //   title: '账套',
        //   align: 'left',
        //   dataIndex: 'piMaster',
        // },
        // {
        //   title: '备注',
        //   align: 'left',
        //   dataIndex: 'omBeizhu',
        // },
      ],
      // 展开的行
      expandedRowKeys: [],
      url: {
        list: '/jeewms/wmOmNoticeH/customerlist',
        delete: '/jeewms/wmOmNoticeH/delete',
        deleteBatch: '/jeewms/wmOmNoticeH/deleteBatch',
        exportXlsUrl: '/jeewms/wmOmNoticeH/exportXls',
        importExcelUrl: '/jeewms/wmOmNoticeH/getBankListByExcel',
        listWmOmNoticeIByMainId: '/jeewms/wmOmNoticeH/listWmOmNoticeIByMainId',
        doPrintckd: '/jeewms/wmOmNoticeH/doPrintckd',
        doPrintpage: '/jeewms/wmOmNoticeH/doPrintpage', //拣货单打印接口
        doPrintpageckd: '/jeewms/wmOmNoticeH/doPrintpageckd', //出库单打印接口
        downloadWmOmNoticeI: '/jeewms/wmOmNoticeH/downloadWmOmNoticeI', //导出模板
        getU8Fhd: '/jeewms/wmOmNoticeH/getU8Fhd', //同步发货单
        getU8Cld: '/jeewms/wmOmNoticeH/getU8Cld', //同步发料单
        getU8Xsck: '/jeewms/wmsPda/getU8Xiaoshouchuku', //提交销售出库
        getU8Clck: '/jeewms/wmsPda/getU8Cailiaochuku' //提交材料出库
      },
      dictOptions: {
        cusCode: [],
        reMember: [],
        omPlatNo: []
      },
      /* 分页参数 */
      ipagination: {
        current: 1,
        pageSize: 20,
        pageSizeOptions: ['5', '10','20', '50'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' ' + this.$t('共') + ' '  + total + ' ' + this.$t('项目')
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      visibledy10: false,
      data: [
        {
          query01: '发货单号',
          query02: '客户名称',
          query03: '备货日期',
          query04: '托盘清单',
          query05: '托盘号'
        }
      ]
    }
  },
  computed: {
    importExcelUrl() {
      return window._CONFIG['domianURL'] + this.url.importExcelUrl
    }
  },
  methods: {
    searchReset() {
      this.queryParam = {orderTypeCode: "13"}
      this.loadData()
    },
    accomplish(e) {
      getAction('/jeewms/wmOmNoticeH/updateOmNoticeById2', { noticeId: e.omNoticeId }).then(res => {
        if (res.success) {
          this.loadData()
          this.$message.success(this.$t('操作成功'))
        } else {
          this.$message.warning(this.$t('操作失败'))
        }
      })
    },
    inspectionSheet(e){
      getAction('/jeewms/wmOmNoticeH/queryplqnById', {id:e.id}).then(res=>{
         STOCK_REQUISITION(res.result)
      })
    },
    GS_Print(e,type){
      // 国生入库单打印
      if(type == 'JH'){
        this.$refs.sellChooseModules.onShow(e)
      }else if(type == 'CK'){
        this.$refs.SellOutModules.onShow(e)
      }
    },  
    synchronization(trpe) {
      this.valueShow = getCurrentTime()
      this.isShow = true
      this.djType = trpe
    },
    successCallBack() {
      // 国生同步接口成功的回调
      this.isShow = false
      this.Loading2 = false
      this.loadData()
    },
    errorCallBack() {
      // 国生同步接口失败的回调
      this.Loading2 = false
    },
    syncShowOk() {
      // 选择时间确定
      this.Loading2 = true
      var param = { date: this.valueShow }
      let Fun = SYNC[this.djType]
      Fun(param, this.successCallBack, this.errorCallBack)
    },
    handAdd(record) {
      this.$refs.modalForm.openadd(record)
    },
    canclePrint2() {
      this.data = []
      this.visibledy10 = false
    },
    print(record) {
      this.visibledy10 = true
      getAction('/jeewms/wmOmNoticeH/doPrintBqList', { id: record.id }).then(res => {
        res.result.map((item, index) => {
          item.qrcode = item.query01 + ',' + item.query04
        })
        this.data = res.result
      })
    },
    syncU8dd(type) {
      this.isShow = true
      this.djType = type
    },
    isShowOk() {
      this.Loading2 = true
      var param = {
        date: this.valueShow
      }
      if (this.djType == 1) {
        getAction(this.url.getU8Fhd, param).then(res => {
          this.Loading2 = false
          if (res.success) {
            this.loadData()
            this.isShow = false
            this.$message.success('发货单同步成功！')
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
        })
      }
      if (this.djType == 2) {
        getAction(this.url.getU8Cld, param).then(res => {
          this.Loading2 = false
          if (res.success) {
            this.loadData()
            this.isShow = false
            this.$message.success('发料单同步成功！')
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
        })
      }
    },
    syncU8Ckd(type) {
      if (this.selectedRowKeys.length != 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      var that = this
      let id = this.selectedRowKeys[0]
      if (type == 1) {
        getAction(that.url.getU8Xsck, { id: id }).then(res => {
          if (res.success) {
            that.$message.success('操作成功！')
            that.loadData()
          } else {
            that.$message.warning(this.$t('操作失败'))
          }
        })
      }
      if (type == 2) {
        getAction(that.url.getU8Clck, { id: id }).then(res => {
          if (res.success) {
            that.$message.success('操作成功！')
            that.loadData()
          } else {
            that.$message.warning(this.$t('操作失败'))
          }
        })
      }
    },
    initDictConfig() {
      initDictOptions('md_cus,zhong_wen_qch,ke_hu_bian_ma').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'cusCode', res.result)
        }
      })
      initDictOptions('tms_kd').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'reMember', res.result)
        }
      })
      initDictOptions('ba_platform,platform_name,platform_code').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'omPlatNo', res.result)
        }
      })
      initDictOptions('md_cus_other,zhong_wen_qch,ke_hu_bian_ma').then(res => {
        if (res.success) {
          this.$set(this.dictOptions, 'ocusCode', res.result)
        }
      })
    },
    clickThenSelect(record) {
      return {
        on: {
          click: () => {
            this.onSelectChange(record.id.split(','), [record])
          }
        }
      }
    },
    onClearSelected() {
      this.selectedRowKeys = []
      this.selectionRows = []
      this.selectedMainId = ''
    },
    onSelectChange(selectedRowKeys, selectionRows) {
      this.selectedMainId = selectionRows[0] ? selectionRows[0].omNoticeId : null
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
    },
    loadData(arg) {
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      this.onClearSelected()
      var params = this.getQueryParams() //查询条件
      this.loading = true
      getAction(this.url.list, params).then(res => {
        if (res.success) {
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
        }
        if (res.code === 510) {
          this.$message.warning(this.$t('操作失败'))
        }
        this.loading = false
      })
    },
    //根据出库单id获取单据商品列表
    getListWmOmNoticeIByMainId(id, type) {
      var that = this
      var url
      if (type == 'ck') {
        url = this.url.doPrintpageckd + '?id=' + id
      } else {
        url = this.url.doPrintpage + '?id=' + id
      }
      getAction(url).then(res => {
        if (res.success) {
          that.printData = res.result
          if (type == 'ck') {
            that.printDataList = res.result.listitem
            that.printData.wmOmNoticeHPage = {}
          } else {
            that.printDataList = res.result.wmOmQmIList
          }
        }
      })
    },
    openPrint(record, type) {
      console.log(record)
      var that = this
      if (type == 'ck') {
        this.visible1 = true
        this.getListWmOmNoticeIByMainId(record.id, 'ck')
      }
      if (type == 'jh') {
        this.visible = true
        this.getListWmOmNoticeIByMainId(record.id, 'jh')
      }
    },
    canclePrint(type) {
      if (type == 'ck') {
        this.visible1 = false
      }
      if (type == 'jh') {
        this.visible = false
      }
      this.printData = {
        wmOmNoticeHPage: {}
      }
      this.printDataList = []
      this.totalWeight = 0
      this.totalBulk = 0
      this.totalNum = 0
    },
    //导出
    exportBill(type, id) {
      if (type == 'tz') {
        //return `${window._CONFIG['domianURL']}/${this.url.downReceiveExcel+'?id='+id}`;
        window.open(window._CONFIG['domianURL'] + this.url.downReceiveExcel + '?id=' + id)
      } else {
        window.open(window._CONFIG['domianURL'] + this.url.doPrintckd + '?id=' + id)
      }
    },
    handleExportModal(fileName) {
      this.exportOk = true
      console.log(this.exportOk)
      if (!fileName || typeof fileName != 'string') {
        fileName = '导出文件'
      }
      let myparam = {}
      // Object.assign(myparam,this.queryParam);
      // for(var mkey in myparam){
      //   if(myparam[mkey]!=null && myparam[mkey]!='' && myparam[mkey].indexOf("*") == -1){
      //     myparam[mkey] = '*'+myparam[mkey]+'*';
      //   }
      // }
      //
      let param = { ...myparam }
      console.log('导出参数', param)
      try {
        downFile(this.url.downloadWmOmNoticeI, param)
          .then(data => {
            if (!data) {
              this.$message.warning('文件下载失败')
              this.exportOk = false
              return
            }
            if (typeof window.navigator.msSaveBlob !== 'undefined') {
              window.navigator.msSaveBlob(new Blob([data], { type: 'application/vnd.ms-excel' }), fileName + '.xls')
            } else {
              let url = window.URL.createObjectURL(new Blob([data], { type: 'application/vnd.ms-excel' }))
              let link = document.createElement('a')
              link.style.display = 'none'
              link.href = url
              link.setAttribute('download', fileName + '.xls')
              document.body.appendChild(link)
              link.click()
              document.body.removeChild(link) //下载完成移除元素
              window.URL.revokeObjectURL(url) //释放掉blob对象
            }
            this.exportOk = false
            console.log(this.exportOk)
          })
          .finally(() => {
            this.exportOk = false
          })
      } catch (e) {
        this.exportOk = false
      }
    },
    handleExpand(expanded, record) {
      console.log(expanded, record)
      this.expandedRowKeys = []
      if (expanded === true) {
        this.expandedRowKeys.push(record.id)
      }
    }
  }
}
</script>
<style lang="less" scoped>
@import '~@assets/less/common.less';
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
  display: inline-block;
  margin-left: 20px;
  width: 200px;
  text-align: center;
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