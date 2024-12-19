<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="预计到货时间">
              <j-date
                placeholder="请选择开始日期"
                class="query-group-cust"
                v-model="queryParam.imData_begin"
              ></j-date>
              <span>-</span>
              <j-date
                placeholder="请选择结束日期"
                class="query-group-cust"
                v-model="queryParam.imData_end"
              ></j-date>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="进货通知单号">
              <a-input placeholder="请输入进货通知单号" v-model="queryParam.noticeId"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--              <a-form-item label="司机">-->
            <!--                <a-input placeholder="请输入司机" v-model="queryParam.imCarDri"></a-input>-->
            <!--              </a-form-item>-->
            <!--            </a-col>-->
            <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--              <a-form-item label="司机电话">-->
            <!--                <a-input placeholder="请输入司机电话" v-model="queryParam.imCarMobile"></a-input>-->
            <!--              </a-form-item>-->
            <!--            </a-col>-->

            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主编码">
                <j-search-select-tag
                  v-model="queryParam.cusCode"
                  dict="md_cus,ke_hu_bian_ma,ke_hu_bian_ma"
                  placeholder="请选择货主编码"
                />
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主名称">
                <j-search-select-tag
                  v-model="queryParam.cusCode"
                  dict="md_cus,zhong_wen_qch,ke_hu_bian_ma"
                  placeholder="请选择货主名称"
                />
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="单据状态">
                <j-dict-select-tag
                  v-model="queryParam.imSta"
                  dictCode="stock_state"
                  placeholder="请选择状态"
                />
                <!--                <a-input placeholder="请输入单据状态" v-model="queryParam.imSta"></a-input>-->
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="车号">
                <a-input placeholder="请输入车号" v-model="queryParam.imCarNo"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="订单类型">
                <j-dict-select-tag
                  type="list"
                  v-model="queryParam.orderTypeCode"
                  dictCode="ba_order_type,order_type_name,order_type_code,order_type='2'"
                  placeholder="请选择订单类型"
                />
              </a-form-item>
            </a-col>
            <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--              <a-form-item label="月台">-->
            <!--                <j-dict-select-tag placeholder="请选择月台" v-model="queryParam.platformCode" dictCode="ba_platform,platform_name,platform_code"/>-->
            <!--              </a-form-item>-->
            <!--            </a-col>-->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd('05')" type="primary" icon="plus" v-has="'imClient:add'">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('收货订单')">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
<!--      <a-button type="primary" icon="redo" @click="syncU8dd('3')">同步生产订单</a-button>-->
      <!-- <a-button type="primary" icon="redo" @click="syncU8dd('1')">同步采购订单</a-button> -->
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
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
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
          <a @click="handleCheck(record)" v-has="'imClient:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'imClient:check'" v-if="record.imSta=='计划中'" />
          <a @click="handleEdit(record)" v-has="'imClient:edit'" v-if="record.imSta=='计划中'">{{$t('编辑')}}</a>
<!--          <a-divider type="vertical" v-has="'imClient:edit'" />-->
<!--          <a   @click="print(record)" v-has="'imClient:exportBq'">打印标签</a>-->
<!--          <a-divider type="vertical" v-has="'imClient:exportBq'" />-->
<!--          <a @click="goPrintOrder(record)" v-has="'imClient:printOrder'">打印通知单</a>-->
<!--          <a-divider type="vertical" v-has="'imClient:printOrder'" />-->
<!--          <a @click="exportBill('ys',record.id)" v-has="'imClient:exportAccept'">导出验收单</a>-->
<!--          <a-divider type="vertical" v-has="'imClient:exportAccept'" />-->
<!--          <a @click="goPrintPutIn(record)" v-has="'imClient:printIn'">打印入库</a>-->
<!--          <a-divider type="vertical" v-has="'imClient:printIn'" />-->
<!--          <a @click="exportBill('rk',record.id)" v-has="'imClient:exportIn'">导出入库</a>-->

          <!--          <a-divider type="vertical" />-->
          <!--          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">-->
          <!--            <a>{{$t('删除')}}</a>-->
          <!--          </a-popconfirm>-->
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
      <div class="printBody" id="printMe" >
      <div class="item" v-for="(item,index) in data" :key="index" >
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
            <vue-qr :text="item.query03" :margin="0" colorDark="#000" colorLight="#fff" :size="100"></vue-qr>
            <p>{{item.query03}}</p>
          </div>
          <div class="qrcode1">
            <vue-qr :text="item.query06" :margin="0" colorDark="#000" colorLight="#fff" :size="100"></vue-qr>
            <p>{{item.query06}}</p>
          </div>
        </div>
      </div>
    </div>
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

  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmImClientHModal from './modules/WmImClientHModal'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JDate from '@/components/jeecg/JDate.vue'
import { getAction } from '@/api/manage'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import '@/assets/less/TableExpand.less'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import vueQr from 'vue-qr'

export default {
  name: 'WmImNoticeHList',
  mixins: [JeecgListMixin, commonTableRowClass, listCheckMixin],
  components: {
    vueQr,
    JDictSelectTag,
    JDate,
    WmImClientHModal,
    JSearchSelectTag
  },
  data() {
    return {
      queryParam:{
        orderTypeCode:'05'
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
        
        // {
        //   title: '#',
        //   dataIndex: '',
        //   key:'rowIndex',
        //   width:60,
        //   align: 'left',
        //   customRender:function (t,r,index) {
        //     return parseInt(index)+1;
        //   }
        // },
        {
          title: '进货通知单号',
          align: 'left',
          dataIndex: 'noticeId'
        },
        // {
        //   title: 'U8单号',
        //   align: 'left',
        //   dataIndex: 'u8Dhcode',
        //   customRender: function(text,e) {
        //     return text || e.u8Cgcode
        //   }
        // },
        {
          title: '货主编码',
          align: 'left',
          dataIndex: 'cusCode'
        },
        {
          title: '货主名称',
          align: 'left',
          dataIndex: 'cusCode_dictText'
        },
        {
          title: '订单类型',
          align: 'left',
          dataIndex: 'orderTypeCode_dictText'
        },
        {
          title: '单据状态',
          align: 'left',
          dataIndex: 'imSta'
        },
        {
          title: '预计到货时间',
          align: 'left',
          dataIndex: 'imData',
          customRender: (text) => {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '司机',
          align: 'left',
          dataIndex: 'imCarDri'
        },
        {
          title: '司机电话',
          align: 'left',
          dataIndex: 'imCarMobile'
        },
        {
          title: '车号',
          align: 'left',
          dataIndex: 'imCarNo'
        },
        {
          title: '备注',
          align: 'left',
          dataIndex: 'imBeizhu'
        },
        {
          title: '创建人',
          align: 'left',
          dataIndex: 'createBy'
        },
        {
          title: '创建日期',
          align: 'left',
          dataIndex: 'createTime',
          customRender: (text) => {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '订单号',
          align: 'left',
          dataIndex: 'imCusCode'
        },

        {
          title: '月台',
          align: 'left',
          dataIndex: 'platformCode_dictText'
        },
        {
          title: '附件',
          align: 'left',
          dataIndex: 'fuJian',
          scopedSlots: { customRender: 'fileSlot' }
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          width: 50,
          scopedSlots: { customRender: 'action' }
        },
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
      visibledy10:false,
      printIds: [],
      bqnum: 1,
      bqcode: ''
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    handleAdd(e){
      this.$refs.modalForm.add(e)
    },
    canclePrint2(){
      this.data = []
      this.visibledy10 = false
    },
    print(record) {
      this.visibledy10 = true
      getAction('/jeewms/wmImNoticeH/doPrintBqList', {id:record.id}).then(res=>{
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
      this.printData = record
      this.getListWmImNoticeIByMainId(record.noticeId)
      console.log(this.printDataList)
      this.visible1 = true
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
  padding: 20px;
}
.printBody .item {
  width: 600px;
  height: 400px;
  font-size: 24px;
  font-weight: 600;
  color: #000;
}
.printBody .item .title p {
  display: inline;
  margin-left: 20px;
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
.qrcode1 div span{
  width: 100%;
  display: block;
}
</style>