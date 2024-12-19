<template>
  <j-modal
    :title="title"
    :width="1500"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form-model :model="model" :rules="validatorRules" ref="formName">
        <a-row>
          <!--            <a-form-model-item label="创建人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['createName']" :placeholder="title==$t('查看')? '':'请输入创建人名称'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <j-date :placeholder="title==$t('查看')? '':'请选择创建日期'" v-decorator="['createTime']" :trigger-change="true" style="width: 100%" :disabled="title==$t('查看')?true:false"/>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <j-dict-select-tag type="list" v-decorator="['sysCompanyCode']" :trigger-change="true" dictCode="ba_com,com_zh_aname,com_code" :placeholder="title==$t('查看')? '':'请选择所属公司'" :disabled="title==$t('查看')?true:false"/>-->
          <!--            </a-form-model-item>-->
          <!-- <a-form-model-item label="货主编码" :labelCol="labelCol" :wrapperCol="wrapperCol"  prop="cusCode">
              <j-search-select-tag  @change="e => cusChange(e)" type="list" v-model="model.cusCode" :trigger-change="true" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')? '':'请选择货主'" :disabled="title==$t('查看')?true:false"/>
          </a-form-model-item>-->
          <a-form-model-item
            :label="$t('客户')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="cusCode"
            v-if="model.orderTypeCode != 13"
          >
            <j-popup
              v-model="model.cusCode"
              code="cus_jiancheng"
              org-fields="zhong_wen_qch,ke_hu_bian_ma"  
              dest-fields="cusName,cusCode"
              field="cusCode"/>
          </a-form-model-item>

          <a-form-model-item
            :label="$t('要求交货时间')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="delvData"
            v-if="model.orderTypeCode == 12"
          >
            <j-date
              :showTime="true"
              dateFormat="YYYY-MM-DD HH:mm:ss"
              :placeholder="$t('请选择')"
              v-model="model.delvData"
              :trigger-change="true"
              style="width: 100%"
              :disabled="title==$t('查看')?true:false"
            />
          </a-form-model-item>

          
          <a-form-model-item
            :label="$t('采购包装日期')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="model.orderTypeCode == 13"
            prop="packingDate"
          >
            <j-date
              :showTime="true"
              dateFormat="YYYY-MM-DD"
              :placeholder="$t('请选择')"
              v-model="model.packingDate"
              style="width: 100%"
              :disabled="title==$t('查看')?true:false"
            />
          </a-form-model-item>
          <a-form-model-item
            :label="$t('期限')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="model.orderTypeCode == 13"
            prop="stopDate"
          >
            <a-range-picker
              @change="onDateChange"
              v-model="stopDate"
              dateFormat="YYYY/MM/DD'"
              style="width: 100%"
              :disabled="title==$t('查看')?true:false"
            />
          </a-form-model-item>
          <!-- <a-form-model-item :label="$t('出库单号')"  :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-model="model.omNoticeId" placeholder="自动生成" :disabled="true"></a-input>
          </a-form-model-item>-->
          <a-form-model-item
            :label="$t('出库单号')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="title==$t('查看')"
          >
            <a-input
              v-model="model.omNoticeId"
              :placeholder="$t('请输入')"
              :disabled="true"
            ></a-input>
          </a-form-model-item>
          <!--          <a-form-model-item label="货主单号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--            <a-input v-decorator="['imCusCode', validatorRules.imCusCode]" :placeholder="title==$t('查看')? '':'请输入货主单号'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--          </a-form-model-item>-->
          <!-- <a-form-model-item label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-model="model.orderTypeCode" :trigger-change="true" dictCode="ba_order_type,order_type_name,order_type_code,order_type='1'" :placeholder="title!='查看'?'':'请选择出库类型'" :disabled="true"/>
          </a-form-model-item>-->
          <!-- <a-form-model-item
            label="订单类型"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="orderTypes"
          >
            <j-search-select-tag
              type="list"
              v-model="model.orderTypes"
              :trigger-change="true"
              :dictOptions="[{text:'销售出库',value:'销售出库'},{text:'退货出库',value:'退货出库'}]"
              :placeholder="title!=$t('新增')?'':'请选择订单类型'"
              :disabled="title!=$t('新增')?true:false"
            />
          </a-form-model-item>-->
          <a-form-model-item
            label="部门选择"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="isShow == 14"
          >
            <j-select-depart v-model="model.sysOrgCode" />
          </a-form-model-item>
          <!--            <a-form-model-item label="收货人" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['delvMember']" :placeholder="title==$t('查看')? '':'请输入收货人'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="收货人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['delvMobile']" :placeholder="title==$t('查看')? '':'请输入收货人电话'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="收货人地址" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['delvAddr']" :placeholder="title==$t('查看')? '':'请输入收货人地址'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="承运人" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['reMember']" :placeholder="title==$t('查看')? '':'请输入承运人'" :disabled="title==$t('查看')?true:false"/>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="承运人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['reMobile']" :placeholder="title==$t('查看')? '':'请输入承运人电话'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="承运人车号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['reCarno']" :placeholder="title==$t('查看')? '':'请输入承运人车号'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="发货月台" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <j-dict-select-tag type="list" v-decorator="['omPlatNo']" :trigger-change="true" dictCode="ba_platform,platform_name,platform_code" :placeholder="title==$t('查看')? '':'请选择发货月台'" :disabled="title==$t('查看')?true:false"/>-->
          <!--            </a-form-model-item>-->
          <a-form-model-item
            :label="$t('状态')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="title==$t('查看')"
          >
            <a-input v-model="model.planSta" :disabled="true"></a-input>
          </a-form-model-item>

          <a-form-model-item
            :label="$t('入库单号')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="u8ReturnCode"
            v-if="model.orderTypeCode == 12"
          >
            <a-input-group compact>
              <a-input
                v-model="model.u8ReturnCode"
                style="width: calc(100% - 110px)"
                :placeholder="$t('请选择')"
                :disabled="true"
              />
              <a-button @click="BillsShow" type="primary" :disabled="title==$t('查看')?true:false">{{$t('选择')}}</a-button>
            </a-input-group>
          </a-form-model-item>

          <a-form-model-item
            :label="$t('装箱单号')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="model.orderTypeCode == 12"
          >
            <a-input-group compact>
              <a-input
                v-model="model.u8Djcode1"
                :placeholder="$t('请输入')"
              />
            </a-input-group>
          </a-form-model-item>
            <a-form-model-item
              :label="$t('订单类型')"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              prop="orderTypes"
              v-if="model.orderTypeCode == '19'"
            >
              <!-- <j-search-select-tag
                type="list"
                v-model="model.orderTypes"
                :dictOptions="[{value:$t('退货'),text:$t('退货')},{value:$t('报废'),text:$t('报废')},{value:$t('其他'),text:$t('其他')}]"
                :placeholder="$t('请输入')"
                :disabled="title==$t('查看')?true:false"
              /> -->
              <j-dict-select-tag
                v-model="model.orderTypes"
                dictCode="qtckOrder_state"
                :placeholder="$t('请选择')"
              />
            </a-form-model-item>
          <!--            <a-form-model-item label="打印状态" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="title==$t('查看')">-->
          <!--              <a-input v-decorator="['printStatus']" :disabled="true"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="出货单号" :labelCol="labelCol" :wrapperCol="wrapperCol" v-if="title==$t('查看')">-->
          <!--              <a-input v-decorator="['omNoticeId']" :disabled="true"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--          <a-form-model-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--            <a-input v-decorator="['imCusCode']" :placeholder="title==$t('查看')? '':'请输入订单号'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--          </a-form-model-item>-->
          <!--            <a-form-model-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['omSta']" :placeholder="title==$t('查看')? '':'请输入状态'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="出货单号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['omNoticeId']" :placeholder="title==$t('查看')? '':'请输入出货单号'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          
            <!-- model.orderTypeCode != 13?$t('备注'):$t('客户') -->
            <!-- :type="model.orderTypeCode != 13? 'textarea' : 'input'" -->
          <a-form-model-item :label="model.orderTypeCode != 13?$t('备注'):$t('客户')" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-model="model.omBeizhu"
              :placeholder="$t('请输入')"
              :disabled="title==$t('查看')?true:false"
              type="textarea"
              v-if="model.orderTypeCode != 13"
            ></a-input>

            <j-popup
              v-model="model.omBeizhu"
              code="cus_jiancheng"
              org-fields="zhong_wen_qch,ke_hu_bian_ma"  
              dest-fields="omBeizhu,cusCode"
              v-if="model.orderTypeCode == 13"
              field="omBeizhu"/>
          </a-form-model-item>

          <a-form-model-item
            :label="$t('主PO号')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="model.orderTypeCode == 12"
            prop="remarks"
          >
            <a-input-group compact>
              <a-input
                v-model="model.remarks"
                :placeholder="$t('请输入')"
              />
            </a-input-group>
          </a-form-model-item>
          <a-form-model-item
            :label="$t('出货方式')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="model.orderTypeCode == 12"
          >
            <j-dict-select-tag  v-model="model.shipmentWay" dictCode="shipmentWay"/>
          </a-form-model-item>
          <a-form-model-item
            :label="$t('出货地址')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="model.orderTypeCode == 12"
          >
            <j-dict-select-tag  v-model="model.shipmentAddress" dictCode="shipmentAddress"/>
          </a-form-model-item>

          <a-form-model-item
            :label="$t('业务员')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="model.orderTypeCode == 12"
          >
          <j-search-select-tag placeholder="请选择业务员" v-model="model.piMaster" dict="YEWUY"/>
          </a-form-model-item>

          <a-form-model-item
            :label="$t('税率')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            v-if="model.orderTypeCode == 12"
          >
          <j-search-select-tag placeholder="请选择税率" v-model="model.delvMember" dict="taxRate" @change="delvMemberChange"/>
          </a-form-model-item>
          
          <!-- <a-form-model-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-upload  v-model="model.fuJian"  :trigger-change="true" :disabled="title==$t('查看')?true:false"></j-upload>
          </a-form-model-item>-->
          <!--            <a-form-model-item label="订单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['orderTypeCode']" :placeholder="title==$t('查看')? '':'请输入订单类型'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="三方货主" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <j-search-select-tag v-decorator="['ocusCode']" dict="md_cus_other,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')? '':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="三方货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['ocusName']" :placeholder="title==$t('查看')? '':'请输入三方货主名称'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="打印状态" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['printStatus']" :placeholder="title==$t('查看')? '':'请输入打印状态'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="对接单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['piClass']" :placeholder="title==$t('查看')? '':'请输入对接单据类型'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
          <!--            <a-form-model-item label="账套" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--              <a-input v-decorator="['piMaster']" :placeholder="title==$t('查看')? '':'请输入账套'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--            </a-form-model-item>-->
        </a-row>
      </a-form-model>
      <a-button
        @click="addTable"
        class="btn"
        type="primary"
        icon="plus"
        style="margin-bottom: 10px;"
      >{{$t('添加')}}</a-button>
      <a-table
        ref="table"
        size="middle"
        :scroll="{ x: true }"
        bordered
        rowKey="id"
        :columns="columns"
        :loading="loading"
        :dataSource="dataSource"
        :pagination="false"
      >
        <!-- 循环展示每一个列表 -->
        <div slot-scope="text,record,index" :slot="v.dataIndex" v-for="(v,i) in columns" :key="i">
          <!-- 输入框 -->
          <!-- 给第一个输入框添加一个父盒子，装载点击区域，相对定位（子绝父相） -->
          <div class="father">
              <a-input
                v-if="v.type == 'input'"
                :style="{width: v.width}"
                :disabled="v.isDisabled"
                v-model="record[v.dataIndex]"
                :ref="'ref'+ index+''+ v.dataIndex"
              >
                <a-icon v-if="v.icon" slot="prefix" :type="v.icon" />
                <!-- 可点击的输入框区域，绝对定位 -->
              </a-input>
              <div v-if="v.icon" class="clickBox" @click="showList(v, index)"></div>
          </div>
          <!-- 时间选择 -->
          <a-date-picker
            v-if="v.type == 'date'"
            v-model="record[v.dataIndex]"
            :disabled="v.isDisabled"
            :style="{width: v.width}"
          />
          <!-- 下拉框 -->
          <j-search-select-tag
            v-if="v.type == 'select'"
            v-model="record[v.dataIndex]"
            :style="{width: v.width}"
            type="list"
            :dictOptions="record['dictOptions']?record['dictOptions']:[]"
            :disabled="v.isDisabled"
            :ref="'ref'+ index+''+ v.dataIndex"
          />
          <!-- :disabled="v.isDisabled || model.orderTypeCode != 12" -->
          <a-radio-group
            :defaultValue="v.default"
            v-model="record[v.dataIndex]"
            v-if="v.type == 'radio'"
          >
            <a-radio
              :value="item.value"
              v-for="(item,index) in v.options"
              :key="index"
            >{{item.text}}</a-radio>
          </a-radio-group>
        </div>
        <span slot="action" slot-scope="text, record">
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handDelete(record.num)">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
        </span>
      </a-table>
      <j-modal
        title="商品列表"
        :width="1100"
        :visible="visible2"
        :maskClosable="false"
        @ok="handleOk2"
        @cancel="handleCancel2"
      >
        <a-form-model layout="inline" @keyup.enter.native="getGoods">
          <a-form-model-item
             :label="$t('商品编码')"
            :labelCol="{xs: { span: 24 }, sm: { span: 7 },}"
            :wrapperCol="{xs: { span: 24 },  sm: { span: 14 },}"
          >
            <a-input v-model="select.shpBianMa"></a-input>
          </a-form-model-item>
          <a-form-model-item
             :label="$t('商品名称')"
            :labelCol="{xs: { span: 24 }, sm: { span: 7 },}"
            :wrapperCol="{xs: { span: 24 },  sm: { span: 14 },}"
          >
            <a-input v-model="select.shpMingCheng"></a-input>
          </a-form-model-item>
          <span class="table-page-search-submitButtons">
            <a-button type="primary" @click="getGoods" icon="search">{{$t('查询')}}</a-button>
            <a-button
              type="primary"
              @click="searchReset"
              icon="reload"
              style="margin-left: 8px"
            >{{$t('重置')}}</a-button>
          </span>
        </a-form-model>
        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          class="j-table-force-nowrap"
          :scroll="{x:true}"
          :columns="columns2"
          :dataSource="dataSource2"
          :pagination="ipagination2"
          :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type:'radio'}"
          :customRow="clickThenSelect"
          @change="handleTableChange"
        ></a-table>
      </j-modal>

      <j-modal
        :title="$t('入库单号')"
        :width="1100"
        :visible="visible3"
        :maskClosable="false"
        @ok="handleOk3"
        @cancel="handleCancel3"
      >
        <a-form-model  @keyup.enter.native="getBills" >
          <a-form-model-item
            :label="$t('入库单号')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input v-model="select2.noticeId"></a-input>
          </a-form-model-item>
          <a-form-model-item
            :label="$t('销售单号')"
           :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input v-model="select2.u8ReturnCode"></a-input>
          </a-form-model-item>
          <a-form-model-item
            :label="$t('客户')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
             <j-search-select-tag
                  v-model="select2.cusCode"
                  dict="md_cus,zhong_wen_qch,ke_hu_bian_ma"
                  :placeholder="$t('请选择')"
                />	
          </a-form-model-item>
          <a-form-model-item
            :label="$t('供应商')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <j-search-select-tag
              type="list"
              v-model="select2.supCode"
              dict="md_sup,zhong_wen_qch,gys_bian_ma"
            />
          </a-form-model-item>
           <a-form-model-item
            :label="$t('主PO号')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input v-model="select2.imBeizhu"></a-input>
          </a-form-model-item>
          <span class="table-page-search-submitButtons" style="margin-left: 100px;margin-top:5px;display: inline-block;">
            <a-button type="primary" @click="getBills" icon="search">{{$t('查询')}}</a-button>
            <a-button
              type="primary"
              @click="searchReset"
              icon="reload"
              style="margin-left: 8px"
            >{{$t('重置')}}</a-button>
          </span>
        </a-form-model>
        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          class="j-table-force-nowrap"
          :scroll="{x:true}"
          :columns="columns3"
          :dataSource="dataSource3"
          :pagination="ipagination3"
          :rowSelection="{selectedRowKeys: selectedRowKeys3, onChange: onSelectChange3}"
          :customRow="clickThenSelect3"
          @change="handleTableChange3"
        ></a-table>
      </j-modal>
    </a-spin>
  </j-modal>
</template>

<script>
import pick from 'lodash.pick'
import { FormTypes } from '@/utils/JEditableTableUtil'
import { SpecificJEditableTableMixin } from '@/mixins/SpecificJEditableTableMixin'
import { validateDuplicateValue } from '@/utils/util'
import JDate from '@/components/jeecg/JDate'
import JUpload from '@/components/jeecg/JUpload'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import { VALIDATE_NO_PASSED, getRefPromise, validateFormAndTables } from '@/utils/JEditableTableUtil'
import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
import { getAction, httpAction } from '@/api/manage'
import { mapActions, mapGetters, mapState } from 'vuex'

export default {
  name: 'WmOmNoticeHModal',
  mixins: [SpecificJEditableTableMixin],
  components: {
    JDate,
    JUpload,
    JDictSelectTag,
    JSearchSelectTag,
    JSelectDepart
  },
  
  data() {
    return {
      select2: {},
      selectedRowKeys3: [],
      selectionRows3: [],
      dataSource3: [],
      columns3: [
        {
          title: this.$t('主PO号'),
          align: 'left',
          dataIndex: 'imBeizhu'
        },
        {
          title: this.$t('客户'),
          align: 'left',
          dataIndex: 'cusCode_dictText'
        },
        // {
        //   title: '客户'this.$t('客户'),
        //   align: 'left',
        //   dataIndex: 'imclientcode_dictText'
        // },
        {
          title: this.$t('供应商'),
          align: 'left',
          dataIndex: 'supCode_dictText'
        },
        {
          title: this.$t('创建人'),
          align: 'left',
          dataIndex: 'createBy'
        },
        {
          title: this.$t('创建日期'),
          align: 'left',
          dataIndex: 'createTime',
          customRender: text => {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: this.$t('销售单号'),
          align: 'left',
          dataIndex: 'u8ReturnCode'
        },
        {
          title:this.$t('入库单号'),
          align: 'left',
          dataIndex: 'noticeId'
        }
      ],
      visible3: false,
      ipagination3: {
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
      select: {},
      handleIndex: 0,
      columns2: [
        {
          title: this.$t('商品编码'),
          align: 'left',
          dataIndex: 'shpBianMa'
        },
        {
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'shpMingCheng'
        },
        {
          title: this.$t('商品中文名称'),
          align: 'left',
          dataIndex: 'ywMingCheng'
        },
        {
          title: this.$t('产品属性'),
          align: 'left',
          dataIndex: 'chpShuXing'
        },
        {
          title: this.$t('单位'),
          align: 'left',
          dataIndex: 'shlDanWei'
        }
      ],
      dataSource2: [],
      selectedRowKeys: [],
      selectionRows: [],
      selectedMainId: '',
      visible2: false,
      ipagination2: {
        current: 1,
        pageSize: 5,
        pageSizeOptions: ['5', '10', '50'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' ' + this.$t('共') + ' '  + total + ' ' + this.$t('项目')
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      sysOrgCode: '',
      sysOrgCodes: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      labelCol2: {
        xs: { span: 24 },
        sm: { span: 3 }
      },
      wrapperCol2: {
        xs: { span: 24 },
        sm: { span: 20 }
      },
      // 新增时子表默认添加几行空数据
      addDefaultRowNum: 1,
      paramData: {
        type: '',
        param: ''
      },
      validatorRules: {
        cusCode: [{ required: true, message: this.$t('请选择') }],
        delvData: [{ required: true, message: this.$t('请选择') }],
        omNoticeId: [{ required: true, message: this.$t('请输入') }],
        orderTypes: [{ required: true, message: this.$t('请选择') }],
        u8ReturnCode: [{ required: true, message: this.$t('请选择') }],
        remarks: [{ required: true, message: this.$t('请输入') }],
      },
      validatorRules2: ['goodsId', 'goodsUnit', 'goodsQua'],
      refKeys: ['wmOmNoticeI'],
      tableKeys: ['wmOmNoticeI'],
      activeKey: 'wmOmNoticeI',
      // 出货详情
      loading: false,
      dataSource: [],
      columns: [
        {
          title: this.$t('商品编码'),
          align: 'left',
          dataIndex: 'goodsId',
          scopedSlots: { customRender: 'goodsId' },
          width: '200px',
          type: 'input',
          icon: 'funnel-plot',
          isDisabled: true
        },
        {
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'goodsName',
          scopedSlots: { customRender: 'goodsName' },
          width: '200px',
          type: 'input',
          isDisabled: true
        },
        {
          title: this.$t('商品中文名称'),
          align: 'left',
          dataIndex: 'ywMingCheng',
          scopedSlots: { customRender: 'ywMingCheng' },
          width: '200px',
          type: 'input',
          isDisabled: true
        },
        {
          title: this.$t('单位'),
          align: 'left',
          dataIndex: 'goodsUnit',
          scopedSlots: { customRender: 'goodsUnit' },
          width: '100px',
          type: 'input',
          isDisabled: true
        },

        {
          title:this.$t('出货数量'),
          align: 'left',
          dataIndex: 'goodsQua',
          scopedSlots: { customRender: 'goodsQua' },
          width: '100px',
          type: 'input'
        },
        //   {
        //     title: '生产日期'this.$t('操作'),
        //   align: 'left',
        //   dataIndex: 'goodsProData',
        //   scopedSlots: { customRender: 'goodsProData' },
        //   width:"200px",
        //   type:'input'
        //   },
        {
          title: this.$t('批次'),
          align: 'left',
          dataIndex: 'goodsBatch',
          scopedSlots: { customRender: 'goodsBatch' },
          width:"200px",
          type:'input'
        },
        {
          title: this.$t('下架任务是否已生成'),
          align: 'left',
          dataIndex: 'planSta',
          scopedSlots: { customRender: 'planSta' },
          width: '200px',
          type: 'radio',
          default: 'N',
          isDisabled: true,
          options: [
            {
              value: 'Y',
              text:  this.$t('是')
            },
            {
              value: 'N',
              text: this.$t('否')
            }
          ]
        },
        {
          title: this.$t('是否报关'),
          align: 'left',
          dataIndex: 'checkname',
          scopedSlots: { customRender: 'checkname' },
          width: '200px',
          type: 'radio',
          default: '0',
          options: [
            {
              value: '1',
              text: this.$t('是')
            },
            {
              value: '0',
              text:this.$t('否')
            }
          ]
        },
        {
          title:this.$t('备注'),
          align: 'left',
          dataIndex: 'omBeiZhu',
          scopedSlots: { customRender: 'omBeiZhu' },
          width: '200px',
          type: 'input'
        },
        // {
        //   title: '货主编码'this.$t('操作'),
        //   align: 'left',
        //   dataIndex: 'cusCode'
        // },
        // {
        //   title: '货主名称'this.$t('操作'),
        //   align: 'left',
        //   dataIndex: 'cusCode_dictText'
        // },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        add: '/jeewms/wmOmNoticeH/add',
        edit: '/jeewms/wmOmNoticeH/edit',
        wmOmNoticeI: {
          list: '/jeewms/wmOmNoticeH/queryWmOmNoticeIByMainId',
          importExcelUrl: '/jeewms/wmOmNoticeH/getBankListByExcel'
        }
      },
      isShow: 0,
      stopDate: [],
      stockIn:[],// 选中的采购子表
    }
  },
  watch: {
    visible(v) {
      this.closeData()
      if (this.model.stopDate && v) {
        this.stopDate = this.model.stopDate.split('~')
      }
    }
  },
  methods: {
    ...mapGetters(['nickname', 'avatar', 'userInfo']),
    delvMemberChange(e){
      console.log(e);
      if(e){
        let arr =JSON.parse(JSON.stringify(this.dataSource)) 
        arr.map(item =>{
          item.checkname = "1"
        })
        this.dataSource = []
        this.$nextTick(()=>{
        this.dataSource = arr
        })
      }
    },
    // 点击弹出输入框的方法
    showList(v, index) {
      // this.v.isDisabled = false
      if (v.icon) {
        this.goodsCodeClick(index)
      }
    },
    onDateChange: function(value, dateString) {
      this.model.stopDate = dateString[0] + '~' + dateString[1]
    },
    BillsShow() {
      this.visible3 = true
      this.getBills()
    },
    handleOk3() {
      getAction('/jeewms/wmImNoticeH/getQueryData',{orderids:this.selectionRows3.join(',')}).then(res=>{
        this.stockIn = res.result
        this.setGoodsBatch()
      })
      this.model.u8ReturnCode = this.selectionRows3.join(',')
      this.handleCancel3()
    },
    setGoodsBatch(){
        this.dataSource.map((v,index)=>{
          this.stockIn.map(item=>{
            if(item.goodsCode == v.goodsId){
              this.$set(this.dataSource[index],'goodsBatch',item.goodsBatch)
            }
          })
      })
    },
    handleCancel3() {
      this.dataSource3 = []
      this.visible3 = false
      this.onClearSelected3()
    },
    getBills() {
      let obj = {
        ...this.select2,
        pageNo: this.ipagination3.current,
        pageSize: this.ipagination3.pageSize,
        // orderTypeCode: '06'
      }
      getAction('/jeewms/wmImNoticeH/cusList', obj).then(res => {
        this.dataSource3 = res.result.records
        this.ipagination3.total = res.result.total
      })
    },
    onClearSelected3() {
      this.selectedRowKeys3 = []
    },
    onSelectChange3(selectedRowKeys, selectionRows) {
      let arr = []
      selectionRows.map((item, index) => {
        arr.push(item.noticeId)
      })

      this.selectedRowKeys3 = selectedRowKeys
      this.selectionRows3 = arr
    },
    clickThenSelect3(record) {
      return {
        on: {
          click: () => {
            this.onSelectChange3(record.id.split(','), [record])
          }
        }
      }
    },
    handleTableChange3(res) {
      this.ipagination3 = res
      this.getBills()
    },
    searchReset() {
      this.select = {}
      this.select2 = {}
      this.getGoods()
      this.getBills()
    },
    handleTableChange(res) {
      this.ipagination2 = res
      this.getGoods()
    },
    // 选择商品后确定
    handleOk2() {
      let obj = {
        goodsId: this.selectionRows[0].shpBianMa,
        goodsName: this.selectionRows[0].shpMingCheng,
        goodsUnit: this.selectionRows[0].shlDanWei,
        ywMingCheng: this.selectionRows[0].ywMingCheng
      }
      this.dataSource.splice(this.handleIndex, 1, { num: this.dataSource[this.handleIndex].num, ...obj })
      this.setGoodsBatch()
      this.handleCancel2()
      this.getSelectList(this.handleIndex)
    },
    getSelectList(index) {
      getAction('/jeewms/mdGoods/listpageDetail', { shpBianMa: this.dataSource[index].goodsCode }).then(res => {
        let arr = []
        res.result.map((item, index) => {
          arr.push({
            text: item.unit2,
            value: item.unit2
          })
        })
        // this.$set(this.dataSource2[index], 'dictOptions', arr)
        this.dataSource.splice(this.handleIndex, 1, { ...this.dataSource[index], dictOptions: arr })
      })
    },
    // 关闭商品弹窗
    handleCancel2() {
      this.visible2 = false
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
      this.selectedMainId = selectedRowKeys[0]
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
    },
    goodsCodeClick(index) {
      this.handleIndex = index
      this.visible2 = true
      this.getGoods()
    },
    // 获取商品列表
    getGoods() {
      let obj = {
        ...this.select,
        pageNo: this.ipagination2.current,
        pageSize: this.ipagination2.pageSize
      }
      getAction('/jeewms/mdGoods/list', obj).then(res => {
        this.dataSource2 = res.result.records
        this.ipagination2.total = res.result.total
      })
    },
    // 添加
    addTable() {
      if(this.model.delvMember){
        this.dataSource.push({checkname:"1"})
      }else{
        this.dataSource.push({})
      }
      this.getTableNum()
    },
    getTableNum() {
      this.dataSource.map((item, index) => {
        item.num = index
      })
    },
    // 删除
    handDelete(num) {
      this.dataSource.map((item, index) => {
        if (item.num == num) {
          this.dataSource.splice(index, 1)
        }
      })
      this.getTableNum()
    },
    getFormFieldValue(field) {
      return this.form.getFieldValue(field)
    },
    openadd(record) {
      this.closeData()
      this.visible = true
      this.title = '新增'
      this.model = {
        orderTypeCode: record
      }
      this.isShow = record

      // this.title =  "新增"
      // this.form.resetFields() // 清空表单
      // this.visible = true;
      // // 默认新增空数据
      // let rowNum = this.addDefaultRowNum
      // if (typeof rowNum !== 'number') {
      //   rowNum = 1
      //   console.warn('由于你没有在 data 中定义 addDefaultRowNum 或 addDefaultRowNum 不是数字，所以默认添加一条空数据，如果不想默认添加空数据，请将定义 addDefaultRowNum 为 0')
      // }
      // this.eachAllTable((item) => {
      //   item.add(rowNum)
      // })
      // this.$nextTick(()=>{
      //   this.isShow = record
      //   this.popupCallback({orderTypeCode:record}) // 赋值进去
      // })
    },
    cusChange(val) {
      this.paramData.param = val
    },
    getAllTable() {
      let values = this.tableKeys.map(key => getRefPromise(this, key))
      return Promise.all(values)
    },
    /** 调用完edit()方法之后会自动调用此方法 */
    editAfter() {
      let fieldval = pick(
        this.model,
          'shiporgcode',
          'shipdepcode',
          'shipdepname',
        'createName',
        'createTime',
        'sysOrgCode',
        'sysCompanyCode',
        'cusCode',
        'delvData',
        'delvMember',
        'delvMobile',
        'delvAddr',
        'reMember',
        'reMobile',
        'reCarno',
        'omPlatNo',
        'omBeizhu',
        'planSta',
        'omNoticeId',
        'fuJian',
        'orderTypeCode',
        'ocusCode',
        'ocusName',
        'imCusCode',
        'printStatus',
        'piClass',
        'piMaster'
      )
      this.$nextTick(() => {
        this.form.setFieldsValue(fieldval)
      })
      // 加载子表数据
      if (this.model.omNoticeId) {
        let params = { id: this.model.omNoticeId }
        // this.requestSubTableData(this.url.wmOmNoticeI.list, params, this.wmOmNoticeITable)
        this.loadData(params)
      }
    },
    /** 整理成formData */
    classifyIntoFormData(allValues) {
      let main = Object.assign(this.model, allValues.formValue)
      return {
        ...main, // 展开
        wmOmNoticeIList: allValues.tablesValue[0].values
      }
    },
    validateError(msg) {
      this.$message.error(msg)
    },
    popupCallback(row) {
      this.form.setFieldsValue(
        pick(
          row,
          'shiporgcode',
          'shipdepcode',
          'shipdepname',
          'orderTypeCode',
          'createName',
          'createTime',
          'sysOrgCode',
          'sysCompanyCode',
          'cusCode',
          'delvData',
          'delvMember',
          'delvMobile',
          'delvAddr',
          'reMember',
          'reMobile',
          'reCarno',
          'omPlatNo',
          'omBeizhu',
          'planSta',
          'omNoticeId',
          'fuJian',
          'orderTypeCode',
          'ocusCode',
          'ocusName',
          'imCusCode',
          'printStatus',
          'piClass',
          'piMaster'
        )
      )
    },
    // /** 确定按钮点击事件 */
    // handleOk() {
    //   /** 触发表单验证 */
    //   if(this.title == this.$t('查看')) {
    //     this.close();
    //     return
    //   }
    //   this.getAllTable().then(tables => {
    //     /** 一次性验证主表和所有的次表 */
    //     return validateFormAndTables(this.form, tables)
    //   }).then(allValues => {
    //     if (typeof this.classifyIntoFormData !== 'function') {
    //       throw this.throwNotFunction('classifyIntoFormData')
    //     }
    //     let formData = this.classifyIntoFormData(allValues)
    //     // 发起请求
    //     return this.request(formData)
    //   }).catch(e => {
    //     if (e.error === VALIDATE_NO_PASSED) {
    //       // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
    //       this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
    //     } else {
    //       console.error(e)
    //     }
    //   })
    // },

    verification() {
      let index = 0
      if (this.dataSource.length == 0) {
        this.$message.error('商品列表为空')
        return false
      }
      this.dataSource.map((item, y) => {
        this.validatorRules2.map((v, i) => {
          if (!item[v]) {
            index = 1
            if (v == 'goodsId') {
              this.$refs['ref' + y + '' + v][0].$refs.input.style.borderColor = '#f00'
            } else {
              this.$refs['ref' + y + '' + v][0].$el.style.borderColor = '#f00'
            }
          } else {
            if (v == 'goodsId') {
              this.$refs['ref' + y + '' + v][0].$refs.input.style.borderColor = '#D9D9D9'
            } else {
              this.$refs['ref' + y + '' + v][0].$el.style.borderColor = '#D9D9D9'
            }
          }
        })
      })
      if (index == 1) {
        return false
      } else {
        return true
      }
    },
    handleOk(e) {
      console.log(this.title)
      if(!this.dataSource.length) {
        return this.$message.warning('请先添加商品')
        
      }
      if (this.title == this.$t('查看')) {
        this.close()
        return
      }
      this.$refs.formName.validate(valid => {
        if (valid) {
          // if (!this.verification()) return
          this.confirmLoading = true
          let formData = Object.assign({ ...this.model, wmOmNoticeIList: this.dataSource })
          let url = this.url.add
          let get = 'post'
          if (this.title == this.$t('编辑')) {
            url = this.url.edit
            get = 'put'
          }else{
            formData.delvAddr = this.userInfo().username
          }
          httpAction(url, formData, get).then(res => {
            this.confirmLoading = false
            if (res.success) {
              this.close()
              this.closeData()
              this.$emit('ok')
              this.$message.success(this.$t('操作成功'))
            } else {
              this.$message.error(this.$t('操作失败'))
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    closeData() {
      this.dataSource = []
      this.dataSource2 = []
      this.selectedRowKeys = []
      this.selectionRows = []
      this.selectedMainId = ''
      this.stopDate = []
    },
    loadData(params) {
      this.loading = true
      getAction(this.url.wmOmNoticeI.list, params).then(res => {
        if (res.success) {
          this.dataSource = res.result.records
          this.getTableNum()
        }
        if (res.code === 510) {
          this.$message.warning(this.$t('操作失败'))
        }
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>
/* 可点击的输入框样式 */
>>> .father {
  position: relative;
}

>>> .clickBox {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  cursor: pointer;
  background-color: transparent;
  z-index: 100;
}
>>> .ant-modal-content .ant-modal-body .ant-form-item {
  display: inline-block !important;
  width: 33%;
}
>>> .ant-modal-content .ant-modal-body {
  max-height: 700px !important;
  overflow-y: auto;
}
>>> .ant-input-affix-wrapper .ant-input-suffix {
  display: none !important;
}

>>> .ant-select .ant-select-selection {
  background-color: transparent;
  border: none;
  border-radius: 0px;
  height: 31px;
}
>>> .ant-select {
  background-color: #fff;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  height: 32px;
}
/deep/ .ant-table-tbody > tr > td {
  white-space: nowrap;
}
</style>