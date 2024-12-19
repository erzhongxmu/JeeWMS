<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    
  >
    <a-spin :spinning="confirmLoading">
      <a-form ref="form" :form="form">
        <a-row>
          <template #expandIcon="props">
            <a-icon type="caret-right" :rotate="props.isActive ? 90 : 0" />
          </template>
          <!-- <a-col :span="6">
                <a-form-item label="所属货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-search-select-tag
                    type="list"
                    v-decorator="['suoShuKeHu', validatorRules.suoShuKeHu]"
                    :trigger-change="true"
                    dict="md_cus,zhong_wen_qch,ke_hu_bian_ma"
                    :placeholder="title==$t('查看')?'':'请选择货主名称'"
                    :disabled="title==$t('查看')?true:false"
                  />
                </a-form-item>
          </a-col>-->
          <!-- <a-col :span="6">
                <a-form-item label="存放温层" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-search-select-tag
                    type="list"
                    v-decorator="['cfWenCeng', validatorRules.cfWenCeng]"
                    :trigger-change="true"
                    dict="ba_deg_type,deg_type_code,deg_type_name"
                    :placeholder="title==$t('查看')?'':'请输入存放温层'"
                    :disabled="title==$t('查看')?true:false"
                  />
                </a-form-item>
          </a-col>-->
          <a-col :span="6" v-if="title==$t('新增')">
            <a-form-item :label="$t('客户')" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-popup
                  v-decorator="['khJC', validatorRules.khJC]"
                  field="khJC"
                  org-fields="ke_hu_jian_cheng"
                  dest-fields="khJC"
                  code="cus_jiancheng"
                  :triggerChange="true"
                  :multi="false"
                  :placeholder="$t('请选择')"
                  @callback="popupCallback"
                  />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :label="$t('商品编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['shpBianMa', validatorRules.shpBianMa]"
                :placeholder="$t('请输入')"
                :disabled="title!=$t('新增')?true:false"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :label="$t('商品名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['shpMingCheng', validatorRules.shpMingCheng]"
                :placeholder="$t('请输入')"
                :disabled="title==$t('查看')?true:false"
            ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :label="$t('商品简称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['shpJianCheng', validatorRules.shpJianCheng]"
                :placeholder="$t('请输入')"
                :disabled="title==$t('查看')?true:false"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :label="$t('贴纸类型')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['stickertype']"
                :placeholder="$t('请输入')"
                :disabled="title==$t('查看')?true:false"
              ></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col :span="6">
            <a-form-item :label="$t('海关编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['cusNo']"
                :placeholder="$t('请输入')"
                :disabled="title==$t('查看')?true:false"
              ></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :span="6">
            <a-form-item :label="$t('中文名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['ywMingCheng']"
                :placeholder="$t('请输入')"
                :disabled="title==$t('查看')?true:false"
              ></a-input>
            </a-form-item>
          </a-col>
          
          <a-col :span="6">
            <a-form-item :label="$t('颜色')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!-- <j-search-select-tag
                  @change="shpYanSeCodeChange"
                  type="list"
                  v-decorator="['shpYanSeCode', validatorRules.shpYanSeCode]"
                  :trigger-change="true"
                  dict="wms_mdgood_color,color_name,color_code"
                  :placeholder="$t('请输入')"
                  :disabled="title==$t('查看')?true:false"
                /> -->
                <j-popup
                  v-decorator="['shpYanSeCode', validatorRules.shpYanSeCode]"
                  field="shpYanSeCode"
                  org-fields="color_code,color_name"
                  dest-fields="shpYanSeCode,shpYanSe"
                  code="wms_mdgood_color"
                  :triggerChange="true"
                  :multi="false"
                  :placeholder="$t('请选择')"
                  @callback="popupCallback"
                  />
            </a-form-item>
          </a-col>
          <!-- <a-col :span="6">
            <a-form-item :label="$t('颜色英文')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['shpYanEnse', validatorRules.shpYanEnse]"
                :placeholder="$t('请输入')"
                :disabled="title==$t('查看')?true:false"
              ></a-input>
            </a-form-item>
          </a-col>-->
          <!-- <a-col :span="6">
            <a-form-item :label="$t('颜色编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag
                  @change="shpYanSeCodeChange"
                  type="list"
                  v-decorator="['shpYanSeCode', validatorRules.shpYanSeCode]"
                  :trigger-change="true"
                  dict="wms_mdgood_color,color_code,color_name"
                  :placeholder="$t('请输入')"
                  :disabled="title==$t('查看')?true:false"
                />
            </a-form-item>
          </a-col>  -->
          <a-col :span="6">
              <a-form-item :label="$t('商品分类')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-popup
                  v-decorator="['classification', validatorRules.classification]"
                  field="classification"
                  org-fields="attr3,attr4"
                  dest-fields="classification,model"
                  code="part_type"
                  :triggerChange="true"
                  :multi="false"
                  :placeholder="$t('请选择')"
                  @callback="popupCallback"
                  />
              </a-form-item>
          </a-col>
          <a-col :span="6">
              <a-form-item :label="$t('商品分类编码')" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                v-decorator="['model']"
                :placeholder="$t('请输入')"
                :disabled="true"
              ></a-input>
              </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :label="$t('产品属性')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-popup
                  v-decorator="['chpShuXing', validatorRules.chpShuXing]"
                  field="chpShuXing"
                  org-fields="goods_type_name"
                  dest-fields="chpShuXing"
                  code="goods_type"
                  :triggerChange="true"
                  :multi="false"
                  :placeholder="$t('请选择')"
                  @callback="popupCallback"
                  />
            </a-form-item>
          </a-col>

          <a-col :span="6">
            <a-form-item :label="$t('商品规格')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['shpGuiGe']"
                :placeholder="$t('请输入')"
                :disabled="title==$t('查看')?true:false"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :label="$t('单位')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag
                type="list"
                v-decorator="['shlDanWei', validatorRules.shlDanWei]"
                :trigger-change="true"
                dict="ba_unit,unit_zh_name,unit_code"
                :placeholder="$t('请选择')"
                :disabled="title==$t('查看')?true:false"
              />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :label="$t('商品类型')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag
                type="list"
                v-decorator="['factoryType', validatorRules.factoryType]"
                :trigger-change="true"
                dict="commodity_type"
                :placeholder="$t('请选择')"
                :disabled="title==$t('查看')?true:false"
              />
            </a-form-item>
          </a-col>
          <a-col :span="6">
              <a-form-item :label="$t('logo名称')" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <j-search-select-tag
                  type="list"
                  v-decorator="['shplogo']"
                  :trigger-change="true"
                  dict="wms_logo_config,logo_name,logo_name"
                  :placeholder="$t('请输入')"
                  :disabled="title==$t('查看')?true:false"
                />
              </a-form-item>
          </a-col>
          <a-col :span="6">
              <a-form-item :label="$t('Note')" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  v-decorator="['assembleSingle']"
                  :placeholder="$t('请输入')"
                  :disabled="title==$t('查看')?true:false"
                ></a-input>
              </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :label="$t('每箱数量')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['boxQty']"
                :placeholder="$t('请输入')"
                :disabled="title==$t('查看')?true:false"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :label="$t('商品描述')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea
                v-decorator="['shpMiaoShu']"
                :placeholder="$t('请输入')"
                :disabled="title==$t('查看')?true:false"
                allow-clear
              />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :label="$t('附件')" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-upload v-decorator="['ppTuPian']" :number="1"></j-upload>
            </a-form-item>
          </a-col>




          
          <!-- <a-col :span="6">
                <a-form-item label="系统料号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['dn', validatorRules.dn]"
                    :placeholder="title==$t('查看')?'':'请输入系统料号'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
          </a-col>-->

          <!-- <a-col :span="6">
                <a-form-item label="商品ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['goodsId']"
                    :placeholder="title==$t('查看')?'':'请输入商品ID'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
          </a-col>-->
          <!-- <a-col :span="6">
                <a-form-item label="货主商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['shpBianMakh']"
                    :placeholder="title==$t('查看')?'':'请输入货主编码'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
          </a-col>-->
          <!-- <a-col :span="6">
                <a-form-item label="商品条码" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['shpTiaoMa', validatorRules.shpTiaoMa]"
                    :placeholder="title==$t('查看')?'':'请输入商品条码'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
          </a-col>-->
          <!-- <a-col :span="6">
                <a-form-item label="长" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['chDanPin']"
                    :placeholder="title==$t('查看')?'':'请输入长'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="宽" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['kuDanPin']"
                    :placeholder="title==$t('查看')?'':'请输入宽'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="高" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['gaoDanPin']"
                    :placeholder="title==$t('查看')?'':'请输入高'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="箱长" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['chZhXiang']"
                    :placeholder="title==$t('查看')?'':'请输入长'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="箱宽" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['kuZhXiang']"
                    :placeholder="title==$t('查看')?'':'请输入宽'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="箱高" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['gaoZhXiang']"
                    :placeholder="title==$t('查看')?'':'请输入高'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['zhlKg']"
                    :placeholder="title==$t('查看')?'':'请输入重量'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="体积" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['tiJiCm']"
                    :placeholder="title==$t('查看')?'':'请输入体积'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="码盘单层数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['mpDanCeng']"
                    :placeholder="title==$t('查看')?'':'请输入码盘单层数量'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="码盘层高" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['mpCengGao']"
                    :placeholder="title==$t('查看')?'':'请输入码盘层高'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
          </a-col>-->
          <!-- <a-col :span="6">
                <a-form-item label="拆前单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['unit1']"
                    :placeholder="title==$t('查看')?'':'请输入码盘层高'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="拆后单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['unit2']"
                    :placeholder="title==$t('查看')?'':'请输入码盘层高'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="拆前数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['num1']"
                    :placeholder="title==$t('查看')?'':'请输入码盘层高'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="拆后数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['num2']"
                    :placeholder="title==$t('查看')?'':'请输入码盘层高'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
          </a-col>-->
          <!-- <a-col :span="6">
                <a-form-item label="拆零控制" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-search-select-tag
                    v-decorator="['chlKongZhi']"
                    :placeholder="title==$t('查看')?'':'请输入拆零控制'"
                    :disabled="title==$t('查看')?true:false"
                    :dictOptions="[{value:'是',text:'是'},{value:'否',text:'否'}]"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="拆零数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['chlShl']"
                    :placeholder="title==$t('查看')?'':'请输入拆零数量'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
          </a-col>-->
          <!-- <a-col :span="6">
                <a-form-item label="价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['gaoDanPin', validatorRules.gaoDanPin]"
                    :placeholder="title==$t('查看')?'':'请输入价格'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
          </a-col>-->

          <!-- <a-col :span="6">
                <a-form-item label="商品品牌" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['shpPinPai']"
                    :placeholder="title==$t('查看')?'':'请输入商品品牌'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
          </a-col>-->

          <!-- <a-col :span="6">
                <a-form-item label="日文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['rwMingCheng']"
                    :placeholder="title==$t('查看')?'':'请输入日文名称'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
          </a-col>-->

          <!-- <a-col :span="6">
                <a-form-item label="保质期" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['bzhiQi']"
                    :placeholder="title==$t('查看')?'':'请输入保质期'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="允收天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input
                    v-decorator="['zhlKgm']"
                    :placeholder="title==$t('查看')?'':'请输入允收天数'"
                    :disabled="title==$t('查看')?true:false"
                  ></a-input>
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item label="计费商品类" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-search-select-tag
                    v-decorator="['jfShpLei']"
                    :placeholder="title==$t('查看')?'':'请输入计费商品类'"
                    :disabled="title==$t('查看')?true:false"
                    dict="ba_goods_class,goods_class_name,goods_class_code"
                  />
                </a-form-item>
              </a-col>

              <a-col :span="6">
                <a-form-item label="拆零单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-search-select-tag
                    type="list"
                    v-decorator="['jshDanWei']"
                    :trigger-change="true"
                    dict="ba_unit,unit_zh_name,unit_code"
                    :placeholder="title==$t('查看')?'':'请输入拆零单位'"
                    :disabled="title==$t('查看')?true:false"
                  />
                </a-form-item>
          </a-col>-->

          <!--        <a-form-item label="商品分类" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag type="list" v-decorator="['model']" :trigger-change="true" dictCode="ba_model,model_name,model_code" :placeholder="title==$t('查看')?'':'请选择商品分类'" :disabled="title==$t('查看')?true:false"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="车间" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag type="list" v-decorator="['workshop']" :trigger-change="true" dictCode="chejian" :placeholder="title==$t('查看')?'':'请选择车间'" :disabled="title==$t('查看')?true:false"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="BOM中文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <a-input v-decorator="['bomZw', validatorRules.bomZw]" :placeholder="title==$t('查看')?'':'请输入BOM中文名称'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-search-select-tag @change="e => change(e)" v-decorator="['cusName', validatorRules.cusName]" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')?'':'请选择货主名称'" :disabled="title==$t('查看')?true:false"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->

          <!--&lt;!&ndash;        <a-form-item label="存放库位" :labelCol="labelCol" :wrapperCol="wrapperCol">&ndash;&gt;-->
          <!--&lt;!&ndash;          <a-input v-decorator="['storageArea']" :placeholder="title==$t('查看')?'':'请输入存放库区'" :disabled="title==$t('查看')?true:false"></a-input>&ndash;&gt;-->
          <!--&lt;!&ndash;          <j-multi-select-tag type="list_multi" v-decorator="['storeAreaCode']" :trigger-change="true" dictCode="ba_store_area,area_name,area_code" placeholder="请选择库位"/>&ndash;&gt;-->
          <!--&lt;!&ndash;          <a-select&ndash;&gt;-->
          <!--&lt;!&ndash;            show-search&ndash;&gt;-->
          <!--&lt;!&ndash;            mode="multiple"&ndash;&gt;-->
          <!--&lt;!&ndash;            option-filter-prop="children"&ndash;&gt;-->
          <!--&lt;!&ndash;            :filter-option="filterOption"&ndash;&gt;-->
          <!--&lt;!&ndash;            @focus="handleFocus"&ndash;&gt;-->
          <!--&lt;!&ndash;            @blur="handleBlur"&ndash;&gt;-->
          <!--&lt;!&ndash;            @change="handleChange"&ndash;&gt;-->
          <!--&lt;!&ndash;            :maxTagCount="1"&ndash;&gt;-->
          <!--&lt;!&ndash;            v-decorator="['storageArea']"&ndash;&gt;-->
          <!--&lt;!&ndash;            :placeholder="title==$t('查看')?'':'请选择存放库位'"&ndash;&gt;-->
          <!--&lt;!&ndash;          >&ndash;&gt;-->
          <!--&lt;!&ndash;            <a-select-option v-for="(item,key) in kwList" :value="item.kwCode">&ndash;&gt;-->
          <!--&lt;!&ndash;              {{item.kwName}}&ndash;&gt;-->
          <!--&lt;!&ndash;            </a-select-option>&ndash;&gt;-->
          <!--&lt;!&ndash;          </a-select>&ndash;&gt;-->
          <!--&lt;!&ndash;        </a-form-item>
            </a-col>
          <a-col :span="6">&ndash;&gt;-->
          <!--        <a-form-item label="最大库存量（件）" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <a-input v-decorator="['maxStock']" :placeholder="title==$t('查看')?'':'请输入最大库存量（件）'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="最小库存量（件）" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <a-input v-decorator="['minStock']" :placeholder="title==$t('查看')?'':'请输入最小库存量（件）'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="计量单位" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag v-decorator="['snpTray']" :trigger-change="true" dictCode="snp_tray" :disabled="true"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="上线包装-是否翻包" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag type="radio" v-decorator="['ifBackpacking', validatorRules.ifBackpacking]" :trigger-change="true" dictCode="is_or_not" :placeholder="title==$t('查看')?'':'请选择上线包装-是否翻包'" :disabled="title==$t('查看')?true:false" @change="e => changeFb(e)"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <div class="title-item">出厂包装属性：</div>-->
          <!--        <a-form-item label="出厂包装-类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag type="list" v-decorator="['factoryType', validatorRules.factoryType]" :trigger-change="true" dictCode="packing_type" :placeholder="title==$t('查看')?'':'请选择出厂包装-类型'" :disabled="title==$t('查看')?true:false"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="出厂包装-SNP" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <a-input v-decorator="['beforeSnp', validatorRules.beforeSnp]" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="出厂包装-箱数" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <a-input v-decorator="['factorySnpCaseNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="出厂包装-SNP-箱" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag type="list" v-decorator="['factorySnpCase']" :trigger-change="true" dictCode="snp_case" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="出厂包装-包数" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <a-input v-decorator="['factorySnpPackageNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="出厂包装-SNP-包" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag type="list" v-decorator="['factorySnpPackage']" :trigger-change="true" dictCode="snp_package" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="出厂包装-件数" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <a-input v-decorator="['factorySnpPieceNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="出厂包装-SNP-件" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag type="list" v-decorator="['factorySnpPiece']" :trigger-change="true" dictCode="snp_piece" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->

          <!--        <a-form-item label="出厂包装-堆码极限" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <a-input v-decorator="['factoryStackingLimit', validatorRules.factoryStackingLimit]" :placeholder="title==$t('查看')?'':'请输入出厂包装-堆码极限'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->

          <!--        <div v-if="fbFlag">
                  <div class="title-item">上线包装属性：</div>
                  <a-form-item label="上线包装-类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <j-dict-select-tag type="list" v-decorator="['onlineType', validatorRules.onlineType]" :trigger-change="true" dictCode="packing_type" :placeholder="title==$t('查看')?'':'请选择上架包装-类型'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="上线包装-SNP" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <a-input v-decorator="['afterSnp', validatorRules.afterSnp, validatorRules.afterSnp]" :placeholder="title==$t('查看')?'':'请输入上线包装-SNP'" :disabled="title==$t('查看')?true:false"></a-input>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--          <a-form-item label="上线包装-箱数" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['onlineSnpCaseNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="上线包装-SNP-箱" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag type="list" v-decorator="['onlineSnpCase']" :trigger-change="true" dictCode="snp_case" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--          <a-form-item label="上线包装-包数" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['onlineSnpPackageNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="上线包装-SNP-包" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag type="list" v-decorator="['onlineSnpPackage']" :trigger-change="true" dictCode="snp_package" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--          <a-form-item label="上线包装-件数" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['onlineSnpPieceNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--        <a-form-item label="上线包装-SNP-件" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--          <j-dict-select-tag type="list" v-decorator="['onlineSnpPiece']" :trigger-change="true" dictCode="snp_piece" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
          <!--        </a-form-item>
            </a-col>
          <a-col :span="6">-->
          <!--          <a-form-item label="上线包装-长" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['chDanPin']" :placeholder="title==$t('查看')?'':'请输入上线包装-长'" :disabled="title==$t('查看')?true:false"></a-input>
                  </a-form-item>
            </a-col>
            <a-col :span="6">
                  <a-form-item label="上线包装-宽" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['kuDanPin']" :placeholder="title==$t('查看')?'':'请输入上线包装-宽'" :disabled="title==$t('查看')?true:false"></a-input>
                  </a-form-item>
            </a-col>
            <a-col :span="6">
                  <a-form-item label="上线包装-高" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['gaoDanPin']" :placeholder="title==$t('查看')?'':'请输入上线包装-高'" :disabled="title==$t('查看')?true:false"></a-input>
                  </a-form-item>
            </a-col>
            <a-col :span="6">
                  <a-form-item label="上线包装-上线方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <j-dict-select-tag type="list" v-decorator="['onlineMode', validatorRules.onlineMode]" :trigger-change="true" dictCode="online_mode" :placeholder="title==$t('查看')?'':'请选择上线包装-上线方式'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>
          </a-col>-->
          <!--        </div>-->
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="采购" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="purchaseTable.loading"
            :columns="purchaseTable.columns"
            :dataSource="purchaseTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        <a-tab-pane tab="销售" :key="refKeys[1]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[1]"
            :loading="saleTable.loading"
            :columns="saleTable.columns"
            :dataSource="saleTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        <a-tab-pane tab="加工" :key="refKeys[2]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[2]"
            :loading="processTable.loading"
            :columns="processTable.columns"
            :dataSource="processTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        <a-tab-pane tab="质检" :key="refKeys[3]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[3]"
            :loading="qualityTestTable.loading"
            :columns="qualityTestTable.columns"
            :dataSource="qualityTestTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
      </a-tabs>

    </a-spin>
  </j-modal>
</template>

<script>
import { getAction, httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import JMultiSelectTag from '@/components/dict/JMultiSelectTag'
import JEditableTable from '../../../../components/jeecg/JEditableTable.vue'
import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
import { FormTypes,getRefPromise,VALIDATE_NO_PASSED, validateFormModelAndTables,validateTables } from '@/utils/JEditableTableUtil'

export default {
  name: 'MdGoodsModal',
  mixins: [JEditableTableModelMixin],
  components: {
    JDictSelectTag,
    JSearchSelectTag,
    JMultiSelectTag,
    JEditableTable
  },
  data() {
    return {
      defaultkey: ['1', '2', '3', '4', '5'],
      customStyle: 'background: #f7f7f7;border-radius: 4px;border: 0;',
      toggleSearchStatus: false,
      form: this.$form.createForm(this),
      title: this.$t('操作'),
      width: 1600,
      visible: false,
      model: {},
      fbFlag: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 9 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 - 9 }
      },
      confirmLoading: false,
      validatorRules: {
        // khJC: {
        //   rules: [{ required: true, message: this.$t('请选择') }]
        // },
        // suoShuKeHu: {
        //   rules: [{ required: true, message: this.$t('请输入') }]
        // },
        // dn: {
        //   rules: [{ required: true, message: this.$t('请输入')}]
        // },
        // cfWenCeng: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // chpShuXing: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // gaoDanPin: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // shpBianMa: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // shpMingCheng: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // shlDanWei: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // zhlKg: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // shpTiaoMa: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // bomZw: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // classification: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // shpBianMakh: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // cusName: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // factoryType: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // beforeSnp: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // factoryStackingLimit: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // ifBackpacking: {
        //   rules: [{ required: true, message:this.$t('请选择') }]
        // },
        // onlineType: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // afterSnp: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // onlineMode: {
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // cusNo:{
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // shpYanSeCode:{
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // shlDanWei:{
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // },
        // factoryType:{
        //   rules: [{ required: true, message:this.$t('请输入')}]
        // }
      },
      url: {
        add: '/jeewms/mdGoods/add',
        edit: '/jeewms/mdGoods/edit',
        getKwList: '/jeewms/baKw/getKwList',
        findByKeHuBianMa: '/jeewms/mdCus/findByKeHuBianMa',
        getKwListByGoodsType: '/jeewms/baKw/getKwListByGoodsType'
      },
      kwList: [],
      refKeys: ['purchaseTable', 'saleTable','processTable','qualityTestTable' ],
      tableKeys:['purchaseTable', 'saleTable','processTable','qualityTestTable' ],
      activeKey: 'purchaseTable',
      purchaseTable: {
        dataSource:[],
        columns: [
          {
            title: '采购提前期',
            key: 'query07',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
            validateRules: [{ required: true, message: '${title}不能为空' }],
          },
          {
            title: '公司',
            key: 'query01',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '工厂',
            key: 'query02',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '库存地点',
            key: 'query03',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '商品编码',
            key: 'query04',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '商品名称',
            key: 'query05',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          }
        ],
        loading: false
      },
      saleTable: {
        dataSource:[],
        columns: [
          {
            title: '销售运输方式',
            key: 'query06',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '公司',
            key: 'query01',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '工厂',
            key: 'query02',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '库存地点',
            key: 'query03',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '商品编码',
            key: 'query04',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '商品名称',
            key: 'query05',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          }
        ],
        loading: false
      },
      processTable: {
        dataSource:[],
        columns: [
          {
            title: '生产周期',
            key: 'query08',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '公司',
            key: 'query01',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '工厂',
            key: 'query02',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '库存地点',
            key: 'query03',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '商品编码',
            key: 'query04',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '商品名称',
            key: 'query05',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          }
        ],
        loading: false
      },
      qualityTestTable: {
        dataSource:[],
        columns: [
          {
            title: '质检方式',
            key: 'query09',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '质检比例',
            key: 'query10',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '公司',
            key: 'query01',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '工厂',
            key: 'query02',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '库存地点',
            key: 'query03',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '商品编码',
            key: 'query04',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          },
          {
            title: '商品名称',
            key: 'query05',
            type: FormTypes.input,
            width:"200px",
            placeholder: '请输入${title}',
            defaultValue:'',
          }
        ],
        loading: false
      }
    }
  },
  props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
  mounted() {
    //this.getKwList()
  },
  created() {},
  methods: {
    shpYanSeCodeChange(e,l){
      this.$nextTick(() => {
        this.form.setFieldsValue({
          shpYanSe: l,
        })
      })
    },
    shpYanSeCodeChange2(e,l){
      this.$nextTick(() => {
        this.form.setFieldsValue({
          shpYanSeCode: l,
        })
      })
    },
    handleChange(value) {
      console.log(`selected ${value}`)
    },
    handleBlur() {
      console.log('blur')
    },
    handleFocus() {
      console.log('focus')
    },
    filterOption(input, option) {
      return option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
    },

    // changePartType(val) {
    //   console.log(val)
    //   this.kwList = []
    //   postAction(this.url.getKwListByGoodsType, [val]).then((res) => {
    //     if (res.success) {
    //       for(let key in res.result) {
    //         this.kwList.push({'kwCode':key,'kwName':res.result[key]})
    //       }
    //       console.log(this.kwList)
    //       this.showModal = true
    //     } else {
    //       this.$message.warning(this.$t('操作失败'))
    //     }
    //   })
    // },

    changeFb(val) {
      if (val == 'Y') {
        this.fbFlag = true
      } else {
        this.fbFlag = false
      }
    },
    // getKwList() {
    //   getAction(this.url.getKwList).then((res) => {
    //     if (res.success) {
    //       this.kwList = res.result
    //     }
    //   })
    // },

    change(val) {
      console.log(val)
      getAction(this.url.findByKeHuBianMa + '?keHuBianMa=' + val).then(res => {
        if (res.success) {
          this.$nextTick(() => {
            this.form.setFieldsValue({
              cusName: res.result.zhongWenQch,
              shpBianMakh: val
            })
          })
        }
      })
    },
    add() {
      this.edit({})
      this.$nextTick(() => {
        this.form.setFieldsValue({
          ifBackpacking: 'N',
          snpTray: '托'
        })
      })
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      if (this.model.storageArea && this.model.storageArea.length > 0) {
        this.model.storageArea = this.model.storageArea.split(',')
      }
      console.log( this.model);
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            "khJC",
            "stickertype",
            "assembleSingle",
            "shplogo",
            "shpYanSeCode",
            'dn',
            'shpMingCheng',
            'shpJianCheng',
            'shpBianMa',
            'shpXingHao',
            'shpGuiGe',
            'mpDanCeng',
            'mpCengGao',
            'shpTiaoMa',
            'ppTuPian',
            'bzhiQi',
            'shlDanWei',
            'jshDanWei',
            'tiJiCm',
            'zhlKg',
            'chlShl',
            'jtiJiBi',
            'jmZhongBi',
            'jjZhongBi',
            'chcDanWei',
            'chDanPin',
            'kuDanPin',
            'gaoDanPin',
            'chZhXiang',
            'kuZhXiang',
            'gaoZhXiang',
            'zhuangTai',
            'shpBianMakh',
            'cusName',
            'recommend',
            'ifBackpacking',
            'beforeSnp',
            'afterSnp',
            'routing',
            'dock',
            'kuWeiShuXing',
            'packingTag',
            'cusId',
            'partName',
            'orgId',
            'weight',
            'volume',
            'model',
            'workshop',
            'bomZw',
            'classification',
            'factoryType',
            'onlineType',
            'storageArea',
            'maxStock',
            'minStock',
            'onlineMode',
            'factoryStackingLimit',
            'factorySnpCaseNum',
            'factorySnpPackageNum',
            'factorySnpPieceNum',
            'onlineSnpCaseNum',
            'onlineSnpPackageNum',
            'onlineSnpPieceNum',
            'mpDanCeng',
            'mpCengGao',
            'snpTray',
            'cfWenCeng',
            'chpShuXing',
            'shpPinPai',
            'ywMingCheng',
            'rwMingCheng',
            'zhlKgm',
            'jfShpLei',
            'chlKongZhi',
            'suoShuKeHu',
            'goodsId',
            'goodsStyle',
            'goodsGranary',
            'status',
            'warnDays',
            'isPackUse',
            'isUstime',
            'isQccodeMgn',
            'isPreSales',
            'isAdvent',
            'isHighValue',
            'isLabel',
            'effectiveDistinguish',
            'goodsAttr',
            'capacityReplenishmentMin',
            'companyNo',
            'cusNo',
            'goodsUnit',
            'dateBzDay',
            'dateBz',
            'boxQty',
            'boxVolume',
            'boxWeight',
            'fillingWeight',
            'airEmbargo',
            'dnExternal',
            'goodsProductNo',
            'countryOrigin',
            'matDes',
            'matUrls',
            'matNames',
            'adventdays',
            'goodsgrade',
            'title',
            'words',
            'packageUseMaterial',
            'offShelfDays',
            'rejectionDays',
            'capacityReplenishmentSafety',
            'extend4',
            'extend5',
            'extend6',
            'extend7',
            'categoryId',
            'goodsPrice',
            'styleCode',
            'supplierStyleCode',
            'year',
            'implStandards',
            'category',
            'level',
            'sectionNumber',
            'season',
            'fabric',
            'operator',
            'hsCode',
            'curr',
            'packageSp',
            'fillWeight',
            'ecommerceCode',
            'color',
            'price',
            'salesPrice',
            'shpYanSe',
            'shpYanEnse',
            'shpJianCheng',
            'shpMiaoShu',
            'ppTuPian'
          )
        )
      })
      
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.fbFlag = false
    },
    /** 获取所有的editableTable实例 */
    getAllTable() {
      if (!(this.refKeys instanceof Array)) {
        throw this.throwNotArray('refKeys')
      }
      let values = this.refKeys.map(key => getRefPromise(this, key))
      return Promise.all(values)
    },
    /** 整理成formData */
    classifyIntoFormData(allValues) {
      let main = Object.assign(this.model, allValues.formValue)
      let list = allValues
      let dateFormat = []
      list.map((item) => {
        dateFormat = [...dateFormat,...item.values]
      })
      return {
        ...main,
        baseGoodsItemList:dateFormat
      }
    },
    handleOk() {
      const that = this
      if (that.title == that.$t('查看')) {
        that.close()
        return
      }
      // 触发表单验证
      that.form.validateFields((err, values) => {
        Object.assign(this.model, values)
        if (!err) {
          that.getAllTable().then(tables => {
            return validateTables(tables)
          }).then(allValues => {
          let formData = that.classifyIntoFormData(allValues)
          if (!that.model.id) {
            formData.shpBianMa = formData.khJC + '#' + formData.shpBianMa
          }
          if (formData.storageArea) {
            formData.storageArea = formData.storageArea.join(',')
          }
          that.request(formData)
        }).catch(e => {})
        }
      })
    },

    handleCancel() {
      this.close()
    },
    popupCallback(row) {
      console.log(row)
      this.form.setFieldsValue(
        pick(
          row,
          "khJC",
            "shpYanSeCode",
            "stickertype",
          "shplogo",
            "assembleSingle",
          'shpMingCheng',
          'shpJianCheng',
          'shpBianMa',
          'shpXingHao',
          'shpGuiGe',
          'mpDanCeng',
          'mpCengGao',
          'shpTiaoMa',
          'ppTuPian',
          'bzhiQi',
          'shlDanWei',
          'jshDanWei',
          'tiJiCm',
          'zhlKg',
          'chlShl',
          'jtiJiBi',
          'jmZhongBi',
          'jjZhongBi',
          'chcDanWei',
          'chDanPin',
          'kuDanPin',
          'gaoDanPin',
          'chZhXiang',
          'kuZhXiang',
          'gaoZhXiang',
          'zhuangTai',
          'shpBianMakh',
          'cusName',
          'recommend',
          'ifBackpacking',
          'beforeSnp',
          'afterSnp',
          'routing',
          'dock',
          'kuWeiShuXing',
          'packingTag',
          'cusId',
          'partName',
          'orgId',
          'weight',
          'volume',
          'model',
          'workshop',
          'bomZw',
          'classification',
          'factoryType',
          'onlineType',
          'storageArea',
          'maxStock',
          'maxStock',
          'onlineMode',
          'factoryStackingLimit',
          'factorySnpCaseNum',
          'factorySnpPackageNum',
          'factorySnpPieceNum',
          'onlineSnpCaseNum',
          'onlineSnpPackageNum',
          'onlineSnpPieceNum',
          'mpDanCeng',
          'mpCengGao',
          'snpTray',
          'cfWenCeng',
          'chpShuXing',
          'shpPinPai',
          'ywMingCheng',
          'rwMingCheng',
          'zhlKgm',
          'jfShpLei',
          'chlKongZhi',
          'suoShuKeHu',
          'goodsId',
          'goodsStyle',
          'goodsGranary',
          'status',
          'warnDays',
          'isPackUse',
          'isUstime',
          'isQccodeMgn',
          'isPreSales',
          'isAdvent',
          'isHighValue',
          'isLabel',
          'effectiveDistinguish',
          'goodsAttr',
          'capacityReplenishmentMin',
          'companyNo',
          'cusNo',
          'goodsUnit',
          'dateBzDay',
          'dateBz',
          'boxQty',
          'boxVolume',
          'boxWeight',
          'fillingWeight',
          'airEmbargo',
          'dnExternal',
          'goodsProductNo',
          'countryOrigin',
          'matDes',
          'matUrls',
          'matNames',
          'adventdays',
          'goodsgrade',
          'title',
          'words',
          'packageUseMaterial',
          'offShelfDays',
          'rejectionDays',
          'capacityReplenishmentSafety',
          'extend4',
          'extend5',
          'extend6',
          'extend7',
          'categoryId',
          'goodsPrice',
          'styleCode',
          'supplierStyleCode',
          'year',
          'implStandards',
          'category',
          'level',
          'sectionNumber',
          'season',
          'fabric',
          'operator',
          'hsCode',
          'curr',
          'packageSp',
          'fillWeight',
          'ecommerceCode',
          'color',
          'price',
          'salesPrice',
          'shpYanSe',
          'shpYanEnse',
          'shpJianCheng',
          'shpMiaoShu',
          'ppTuPian'
        )
      )
    }
  }
}
</script>
<style scoped>
/* >>> .ant-modal-content .ant-modal-body .ant-form-item {
  display: inline-block !important;
  width: 100%;
}
>>> .ant-collapse-content-active .ant-col-6{
  display: inline-block;
  float:none !important;
}
>>> .ant-modal-content .ant-modal-body {
  max-height: 700px !important;
  overflow-y: auto;
}
>>> .ant-collapse-header {
  color: #50bb79 !important;
  font-weight: 600;
}
>>> .ant-collapse-content {
  overflow: initial;
} */
</style>