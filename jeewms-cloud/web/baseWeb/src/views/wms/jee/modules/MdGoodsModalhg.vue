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
      <a-form :form="form">
        <div class="title-item">基础属性：</div>
        <a-form-item label="所属货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-search-select-tag type="list" v-decorator="['suoShuKeHu', validatorRules.suoShuKeHu]"
                               :trigger-change="true" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma"
                               :placeholder="title==$t('查看')?'':'请选择货主名称'" :disabled="title==$t('查看')?true:false"/>
        </a-form-item>

        <a-form-item label="系统料号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['dn', validatorRules.dn]" :placeholder="title==$t('查看')?'':'请输入系统料号'"
                   :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>

        <a-form-item label="商品编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shpBianMa', validatorRules.shpBianMa]" :placeholder="title==$t('查看')?'':'请输入商品编码'"
                   :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>
        <a-form-item label="商品ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['goodsId']" :placeholder="title==$t('查看')?'':'请输入商品ID'"
                   :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>

        <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['shpMingCheng', validatorRules.shpMingCheng]" :placeholder="title==$t('查看')?'':'请输入商品名称'"
                   :disabled="title==$t('查看')?true:false"></a-input>
        </a-form-item>


           <a-form-item label="仓库" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsGranary']" :placeholder="title==$t('查看')?'':'请输入仓库'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="款式" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number v-decorator="['goodsStyle']" :placeholder="title==$t('查看')?'':'请输入警戒补货库存'"
                            :disabled="title==$t('查看')?true:false"></a-input-number>
          </a-form-item>
          <a-form-item label="警戒补货库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number v-decorator="['capacityReplenishmentMin']" :placeholder="title==$t('查看')?'':'请输入款式'"
                            :disabled="title==$t('查看')?true:false"></a-input-number>
          </a-form-item>
          <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-search-select-tag type="list" v-decorator="['status']" :trigger-change="true" dict="snp_status"
                                :placeholder="title==$t('查看')?'':'请选择状态'" :disabled="title==$t('查看')?true:false"/>
          </a-form-item>
          <a-form-item label="预警天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number v-decorator="['warnDays']" :placeholder="title==$t('查看')?'':'请输入预警天数'"
                            :disabled="title==$t('查看')?true:false"></a-input-number>
          </a-form-item>
          <a-form-item label="是否包材管理" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['isPackUse']" :trigger-change="true" dictCode="is_not"
                              placeholder="请选择是否包材管理"/>
          </a-form-item>
          <a-form-item label="是否包材管理" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['isUstime']" :trigger-change="true" dictCode="is_not"
                              placeholder="请选择是否存在有效期"/>
          </a-form-item>
          <a-form-item label="是否二维码管理" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['isQccodeMgn']" :trigger-change="true" dictCode="is_not"
                              placeholder="请选择是否二维码管理"/>
          </a-form-item>
          <a-form-item label="是否预售" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['isPreSales']" :trigger-change="true" dictCode="is_not"
                              placeholder="请选择是否预售"/>
          </a-form-item>
          <a-form-item label="是否支持临期品售卖" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['isAdvent']" :trigger-change="true" dictCode="is_not"
                              placeholder="请选择是否支持临期品售卖"/>
          </a-form-item>
          <a-form-item label="是否高值品" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['isHighValue']" :trigger-change="true" dictCode="is_not"
                              placeholder="请选择是否高值品"/>
          </a-form-item>
          <a-form-item label="是否贴标" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['isLabel']" :trigger-change="true" dictCode="is_label"
                              placeholder="请选择是否贴标"/>
          </a-form-item>
          <a-form-item label="效期区分" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['effectiveDistinguish']" :trigger-change="true" dictCode="effec"
                              placeholder="请选择效期区分"/>
          </a-form-item>
          <a-form-item label="商品属性" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="list" v-decorator="['goodsAttr']" :trigger-change="true" dictCode="goods_attr"
                              placeholder="请选择商品属性"/>
          </a-form-item>
          <a-form-item label="成本价" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['price']" :placeholder="title==$t('查看')?'':'请输入成本价'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="售价" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['salesPrice']" :placeholder="title==$t('查看')?'':'请输入售价'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="企业编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['companyNo']" :placeholder="title==$t('查看')?'':'请输入企业编号'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="海关料号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['cusNo']" :placeholder="title==$t('查看')?'':'请输入海关料号'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="售卖单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsUnit']" :placeholder="title==$t('查看')?'':'请输入售卖单位'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="保质期（单位:日)" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['dateBzDay']" :placeholder="title==$t('查看')?'':'请输入保质期（单位:日)'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="保质期（单位:月）" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['dateBz']" :placeholder="title==$t('查看')?'':'请输入保质期（单位:月）'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="箱规数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['boxQty']" :placeholder="title==$t('查看')?'':'请输入箱规数量'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="箱规体积" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['boxVolume']" :placeholder="title==$t('查看')?'':'请输入箱规体积'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="箱子重量（kg）" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['boxWeight']" :placeholder="title==$t('查看')?'':'请输入箱子重量（kg）'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="填充物重量（g）" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['fillingWeight']" :placeholder="title==$t('查看')?'':'请输入填充物重量（g）'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="航空禁运标记" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['airEmbargo']" :placeholder="title==$t('查看')?'':'请输入航空禁运标记'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>

          <a-form-item label="外部系统料号（sku）" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['dnExternal']" :placeholder="title==$t('查看')?'':'请输入外部系统料号（sku）'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>

          <a-form-item label="商品货号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsProductNo']" :placeholder="title==$t('查看')?'':'请输入外部系统料号（sku）'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>

          <a-form-item label="商品生产国" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['countryOrigin']" :placeholder="title==$t('查看')?'':'请输入外部系统料号（sku）'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>

          <a-form-item label="内包装简要描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['matDes']" :placeholder="title==$t('查看')?'':'请输入内包装简要描述'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="内包装使用介绍url" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['matUrls']" :placeholder="title==$t('查看')?'':'请输入内包装使用介绍url'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="内包装名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['matNames']" :placeholder="title==$t('查看')?'':'请输入内包装名称'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="临期品售卖期 单位/天" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['adventdays']" :placeholder="title==$t('查看')?'':'请输入临期品售卖期'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="质量等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsgrade']" :placeholder="title==$t('查看')?'':'请输入质量等级'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="贴标标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['title']" :placeholder="title==$t('查看')?'':'请输入贴标标题'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="贴标文案" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['words']" :placeholder="title==$t('查看')?'':'请输入贴标文案'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="耗材" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['packageUseMaterial']" :placeholder="title==$t('查看')?'':'请输入耗材'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>

          <a-form-item label="下架天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['offShelfDays']" :placeholder="title==$t('查看')?'':'请输入下架天数'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="拒收天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['rejectionDays']" :placeholder="title==$t('查看')?'':'请输入拒收天数'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="安全补货库存" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['capacityReplenishmentSafety']" :placeholder="title==$t('查看')?'':'请输入安全补货库存'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="法定计量第二单位代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['extend4']" :placeholder="title==$t('查看')?'':'请输入法定计量第二单位代码'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="法定数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['extend5']" :placeholder="title==$t('查看')?'':'请输入法定数量'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="第二法定数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['extend6']" :placeholder="title==$t('查看')?'':'请输入第二法定数量'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="法定计量单位代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['extend7']" :placeholder="title==$t('查看')?'':'请输入法定计量单位代码'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="商品类目ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['categoryId']" :placeholder="title==$t('查看')?'':'请输入商品类目ID'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="商品价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['goodsPrice']" :placeholder="title==$t('查看')?'':'请输入商品价格'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="款式编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['styleCode']" :placeholder="title==$t('查看')?'':'请输入款式编码'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="供应商商品款号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['supplierStyleCode']" :placeholder="title==$t('查看')?'':'请输入供应商商品款号'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['year']" :placeholder="title==$t('查看')?'':'请输入年份'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="执行标准" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['implStandards']" :placeholder="title==$t('查看')?'':'请输入执行标准'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['category']" :placeholder="title==$t('查看')?'':'请输入类别'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['level']" :placeholder="title==$t('查看')?'':'请输入等级'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="款号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['sectionNumber']" :placeholder="title==$t('查看')?'':'请输入款号'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="季节" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['season']" :placeholder="title==$t('查看')?'':'请输入季节'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="面料" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['fabric']" :placeholder="title==$t('查看')?'':'请输入面料'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="操作员" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['operator']" :placeholder="title==$t('查看')?'':'请输入操作员'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>

          <a-form-item label="海关十位数编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['hsCode']" :placeholder="title==$t('查看')?'':'请输入海关十位数编码'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="币制" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['curr']" :placeholder="title==$t('查看')?'':'请输入币制'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="包材" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['packageSp']" :placeholder="title==$t('查看')?'':'请输入包材'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="填充物重量（g）" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['fillWeight']" :placeholder="title==$t('查看')?'':'请输入填充物重量'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="电商企业编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['ecommerceCode']" :placeholder="title==$t('查看')?'':'请输入电商企业编码'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>
          <a-form-item label="保税类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['color']" :placeholder="title==$t('查看')?'':'请输入保税类型'"
                    :disabled="title==$t('查看')?true:false"></a-input>
          </a-form-item>


        <!--        <a-form-item label="商品分类" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag type="list" v-decorator="['model']" :trigger-change="true" dictCode="ba_model,model_name,model_code" :placeholder="title==$t('查看')?'':'请选择商品分类'" :disabled="title==$t('查看')?true:false"/>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="车间" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag type="list" v-decorator="['workshop']" :trigger-change="true" dictCode="chejian" :placeholder="title==$t('查看')?'':'请选择车间'" :disabled="title==$t('查看')?true:false"/>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="BOM中文名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="['bomZw', validatorRules.bomZw]" :placeholder="title==$t('查看')?'':'请输入BOM中文名称'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="货主名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-search-select-tag @change="e => change(e)" v-decorator="['cusName', validatorRules.cusName]" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="title==$t('查看')?'':'请选择货主名称'" :disabled="title==$t('查看')?true:false"/>-->
        <!--        </a-form-item>-->

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
        <!--&lt;!&ndash;        </a-form-item>&ndash;&gt;-->
        <!--        <a-form-item label="最大库存量（件）" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="['maxStock']" :placeholder="title==$t('查看')?'':'请输入最大库存量（件）'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="最小库存量（件）" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="['minStock']" :placeholder="title==$t('查看')?'':'请输入最小库存量（件）'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="计量单位" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag v-decorator="['snpTray']" :trigger-change="true" dictCode="snp_tray" :disabled="true"/>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="上线包装-是否翻包" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag type="radio" v-decorator="['ifBackpacking', validatorRules.ifBackpacking]" :trigger-change="true" dictCode="is_or_not" :placeholder="title==$t('查看')?'':'请选择上线包装-是否翻包'" :disabled="title==$t('查看')?true:false" @change="e => changeFb(e)"/>-->
        <!--        </a-form-item>-->
        <!--        <div class="title-item">出厂包装属性：</div>-->
        <!--        <a-form-item label="出厂包装-类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag type="list" v-decorator="['factoryType', validatorRules.factoryType]" :trigger-change="true" dictCode="packing_type" :placeholder="title==$t('查看')?'':'请选择出厂包装-类型'" :disabled="title==$t('查看')?true:false"/>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="出厂包装-SNP" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="['beforeSnp', validatorRules.beforeSnp]" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="出厂包装-箱数" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="['factorySnpCaseNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="出厂包装-SNP-箱" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag type="list" v-decorator="['factorySnpCase']" :trigger-change="true" dictCode="snp_case" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="出厂包装-包数" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="['factorySnpPackageNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="出厂包装-SNP-包" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag type="list" v-decorator="['factorySnpPackage']" :trigger-change="true" dictCode="snp_package" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="出厂包装-件数" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="['factorySnpPieceNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="出厂包装-SNP-件" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag type="list" v-decorator="['factorySnpPiece']" :trigger-change="true" dictCode="snp_piece" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
        <!--        </a-form-item>-->

        <!--        <a-form-item label="出厂包装-堆码极限" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="['factoryStackingLimit', validatorRules.factoryStackingLimit]" :placeholder="title==$t('查看')?'':'请输入出厂包装-堆码极限'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--        </a-form-item>-->

        <!--        <div v-if="fbFlag">
                  <div class="title-item">上线包装属性：</div>
                  <a-form-item label="上线包装-类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <j-dict-select-tag type="list" v-decorator="['onlineType', validatorRules.onlineType]" :trigger-change="true" dictCode="packing_type" :placeholder="title==$t('查看')?'':'请选择上架包装-类型'" :disabled="title==$t('查看')?true:false"/>
                  </a-form-item>-->
        <!--        <a-form-item label="上线包装-SNP" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="['afterSnp', validatorRules.afterSnp, validatorRules.afterSnp]" :placeholder="title==$t('查看')?'':'请输入上线包装-SNP'" :disabled="title==$t('查看')?true:false"></a-input>-->
        <!--        </a-form-item>-->
        <!--          <a-form-item label="上线包装-箱数" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['onlineSnpCaseNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>
                  </a-form-item>-->
        <!--        <a-form-item label="上线包装-SNP-箱" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag type="list" v-decorator="['onlineSnpCase']" :trigger-change="true" dictCode="snp_case" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
        <!--        </a-form-item>-->
        <!--          <a-form-item label="上线包装-包数" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['onlineSnpPackageNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>
                  </a-form-item>-->
        <!--        <a-form-item label="上线包装-SNP-包" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag type="list" v-decorator="['onlineSnpPackage']" :trigger-change="true" dictCode="snp_package" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
        <!--        </a-form-item>-->
        <!--          <a-form-item label="上线包装-件数" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['onlineSnpPieceNum']" :placeholder="title==$t('查看')?'':'请输入出厂包装-SNP-箱数'" :disabled="title==$t('查看')?true:false"></a-input>
                  </a-form-item>-->
        <!--        <a-form-item label="上线包装-SNP-件" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <j-dict-select-tag type="list" v-decorator="['onlineSnpPiece']" :trigger-change="true" dictCode="snp_piece" :placeholder="title==$t('查看')?'':'请选择'" :disabled="title==$t('查看')?true:false"/>-->
        <!--        </a-form-item>-->
        <!--          <a-form-item label="上线包装-长" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['chDanPin']" :placeholder="title==$t('查看')?'':'请输入上线包装-长'" :disabled="title==$t('查看')?true:false"></a-input>
                  </a-form-item>
                  <a-form-item label="上线包装-宽" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['kuDanPin']" :placeholder="title==$t('查看')?'':'请输入上线包装-宽'" :disabled="title==$t('查看')?true:false"></a-input>
                  </a-form-item>
                  <a-form-item label="上线包装-高" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <a-input v-decorator="['gaoDanPin']" :placeholder="title==$t('查看')?'':'请输入上线包装-高'" :disabled="title==$t('查看')?true:false"></a-input>
                  </a-form-item>
                  <a-form-item label="上线包装-上线方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
                    <j-dict-select-tag type="list" v-decorator="['onlineMode', validatorRules.onlineMode]" :trigger-change="true" dictCode="online_mode" :placeholder="title==$t('查看')?'':'请选择上线包装-上线方式'" :disabled="title==$t('查看')?true:false"/>
                  </a-form-item>-->
        <!--        </div>-->

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

import {getAction, httpAction} from '@/api/manage'
import pick from 'lodash.pick'
import JDictSelectTag from "@/components/dict/JDictSelectTag"
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import JMultiSelectTag from "@/components/dict/JMultiSelectTag"


export default {
  name: "MdGoodsModalhg",
  components: {
    JDictSelectTag,
    JSearchSelectTag,
    JMultiSelectTag
  },
  data() {
    return {
      toggleSearchStatus:false,
      form: this.$form.createForm(this),
      title: "操作",
      width: 1400,
      visible: false,
      model: {},
      fbFlag: false,
      labelCol: {
        xs: {span: 24},
        sm: {span: 8},
      },
      wrapperCol: {
        xs: {span: 24},
        sm: {span: 13},
      },
      confirmLoading: false,
      validatorRules: {
        suoShuKeHu: {
          rules: [
            {required: true, message: '请输入所属货主!'},
          ]
        },
        dn: {
          rules: [
            {required: true, message: '请输入系统料号!'},
          ]
        },
        cfWenCeng: {
          rules: [
            {required: true, message: '请输入存放温层!'},
          ]
        },
        chpShuXing: {
          rules: [
            {required: true, message: '请输入产品属性!'},
          ]
        },
        gaoDanPin: {
          rules: [
            {required: true, message: '请输入价格!'},
          ]
        },
        shpBianMa: {
          rules: [
            {required: true, message: '请输入商品编码!'},
          ]
        },
        shpMingCheng: {
          rules: [
            {required: true, message: '请输入商品名称!'},
          ]
        },
        shlDanWei: {
          rules: [
            {required: true, message: '请输入单位!'},
          ]
        },
        zhlKg: {
          rules: [
            {required: true, message: '请输入重量!'},
          ]
        },
        shpTiaoMa: {
          rules: [
            {required: true, message: '请输入商品条码!'},
          ]
        },
        bomZw: {
          rules: [
            {required: true, message: '请输入BOM中文名称!'},
          ]
        },
        classification: {
          rules: [
            {required: true, message: '请选择商品分类!'},
          ]
        },
        shpBianMakh: {
          rules: [
            {required: true, message: '请输入货主编码!'},
          ]
        },
        cusName: {
          rules: [
            {required: true, message: '请输入货主名称!'},
          ]
        },
        factoryType: {
          rules: [
            {required: true, message: '请选择出厂包装类型!'},
          ]
        },
        beforeSnp: {
          rules: [
            {required: true, message: '请输入出厂包装SNP!'},
          ]
        },
        factoryStackingLimit: {
          rules: [
            {required: true, message: '请输入出厂堆码极限!'},
          ]
        },
        ifBackpacking: {
          rules: [
            {required: true, message: '请选择上线是否翻包!'},
          ]
        },
        onlineType: {
          rules: [
            {required: true, message: '请选择上线包装类型!'},
          ]
        },
        afterSnp: {
          rules: [
            {required: true, message: '请输入上线包装SNP!'},
          ]
        },
        onlineMode: {
          rules: [
            {required: true, message: '请输入上线方式!'},
          ]
        },
      },
      url: {
        add: "/jeewms/mdGoods/add",
        edit: "/jeewms/mdGoods/edit",
        getKwList: "/jeewms/baKw/getKwList",
        findByKeHuBianMa: "/jeewms/mdCus/findByKeHuBianMa",
        getKwListByGoodsType: "/jeewms/baKw/getKwListByGoodsType",
      },
      kwList: []
    }
  },
  mounted() {
    //this.getKwList()
  },
  created() {

  },
  methods: {
    handleChange(value) {
      console.log(`selected ${value}`);
    },
    handleBlur() {
      console.log('blur');
    },
    handleFocus() {
      console.log('focus');
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      );
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
      console.log(val);
      getAction(this.url.findByKeHuBianMa + "?keHuBianMa=" + val).then((res) => {
        if (res.success) {
          this.$nextTick(() => {
            this.form.setFieldsValue({
              'cusName': res.result.zhongWenQch,
              'shpBianMakh': val
            })
          })
        }
      })

    },
    add() {
      this.edit({});
      this.$nextTick(() => {
        this.form.setFieldsValue({
          'ifBackpacking': 'N',
          'snpTray': '托'
        })
      })
    },
    edit(record) {
      this.form.resetFields();
      this.model = Object.assign({}, record);
      if (this.model.storageArea && this.model.storageArea.length > 0) {
        this.model.storageArea = this.model.storageArea.split(',')
      }
      this.visible = true;
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'dn', 'shpMingCheng', 'shpJianCheng', 'shpBianMa', 'shpXingHao', 'shpGuiGe', 'mpDanCeng', 'mpCengGao', 'shpTiaoMa', 'ppTuPian', 'bzhiQi', 'shlDanWei', 'jshDanWei', 'tiJiCm', 'zhlKg', 'chlShl', 'jtiJiBi', 'jmZhongBi', 'jjZhongBi', 'chcDanWei', 'chDanPin', 'kuDanPin', 'gaoDanPin', 'chZhXiang', 'kuZhXiang', 'gaoZhXiang', 'zhuangTai', 'shpBianMakh', 'cusName', 'recommend', 'ifBackpacking', 'beforeSnp', 'afterSnp', 'routing', 'dock', 'kuWeiShuXing', 'packingTag', 'cusId', 'partName', 'orgId', 'weight', 'volume', 'model', 'workshop', 'bomZw', 'classification', 'factoryType', 'onlineType', 'storageArea', 'maxStock', 'minStock', 'onlineMode', 'factoryStackingLimit', 'factorySnpCaseNum', 'factorySnpPackageNum', 'factorySnpPieceNum', 'onlineSnpCaseNum', 'onlineSnpPackageNum', 'onlineSnpPieceNum', 'mpDanCeng', 'mpCengGao', 'snpTray', 'cfWenCeng', 'chpShuXing', 'shpPinPai', 'ywMingCheng', 'rwMingCheng', 'zhlKgm', 'jfShpLei', 'chlKongZhi', 'suoShuKeHu', 'goodsId', 'goodsStyle', 'goodsGranary', 'status', 'warnDays', 'isPackUse', 'isUstime', 'isQccodeMgn', 'isPreSales', 'isAdvent', 'isHighValue', 'isLabel', 'effectiveDistinguish', 'goodsAttr', 'capacityReplenishmentMin','companyNo','cusNo','goodsUnit','dateBzDay','dateBz','boxQty','boxVolume','boxWeight','fillingWeight','airEmbargo','dnExternal','goodsProductNo','countryOrigin','matDes','matUrls','matNames','adventdays','goodsgrade','title','words','packageUseMaterial','offShelfDays','rejectionDays','capacityReplenishmentSafety','extend4','extend5','extend6','extend7','categoryId','goodsPrice','styleCode','supplierStyleCode','year','implStandards','category','level','sectionNumber','season','fabric','operator','hsCode','curr','packageSp','fillWeight','ecommerceCode','color','price','salesPrice'))
      })
    },
    close() {
      this.$emit('close');
      this.visible = false;
      this.fbFlag = false;
    },
    handleOk() {
      const that = this;
      if (that.title == that.$t('查看')) {
        that.close();
        return
      }
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true;
          let httpurl = '';
          let method = '';
          if (!this.model.id) {
            httpurl += this.url.add;
            method = 'post';
          } else {
            httpurl += this.url.edit;
            method = 'put';
          }
          let formData = Object.assign(this.model, values);
          console.log("表单提交数据", formData)
          if (formData.storageArea) {
            formData.storageArea = formData.storageArea.join(',')
          }
          httpAction(httpurl, formData, method).then((res) => {
            if (res.success) {
              that.$message.success(this.$t('操作成功'));
              that.$emit('ok');
            } else {
              that.$message.warning(this.$t('操作失败'));
            }
          }).finally(() => {
            that.confirmLoading = false;
            that.close();
          })
        }

      })
    },
    handleCancel() {
      this.close()
    },
    popupCallback(row) {
      this.form.setFieldsValue(pick(row, 'shpMingCheng', 'shpJianCheng', 'shpBianMa', 'shpXingHao', 'shpGuiGe', 'mpDanCeng', 'mpCengGao', 'shpTiaoMa', 'ppTuPian', 'bzhiQi', 'shlDanWei', 'jshDanWei', 'tiJiCm', 'zhlKg', 'chlShl', 'jtiJiBi', 'jmZhongBi', 'jjZhongBi', 'chcDanWei', 'chDanPin', 'kuDanPin', 'gaoDanPin', 'chZhXiang', 'kuZhXiang', 'gaoZhXiang', 'zhuangTai', 'shpBianMakh', 'cusName', 'recommend', 'ifBackpacking', 'beforeSnp', 'afterSnp', 'routing', 'dock', 'kuWeiShuXing', 'packingTag', 'cusId', 'partName', 'orgId', 'weight', 'volume', 'model', 'workshop', 'bomZw', 'classification', 'factoryType', 'onlineType', 'storageArea', 'maxStock', 'maxStock', 'onlineMode', 'factoryStackingLimit', 'factorySnpCaseNum', 'factorySnpPackageNum', 'factorySnpPieceNum', 'onlineSnpCaseNum', 'onlineSnpPackageNum', 'onlineSnpPieceNum', 'mpDanCeng', 'mpCengGao', 'snpTray', 'cfWenCeng', 'chpShuXing', 'shpPinPai', 'ywMingCheng', 'rwMingCheng', 'zhlKgm', 'jfShpLei', 'chlKongZhi', 'suoShuKeHu', 'goodsId', 'goodsStyle', 'goodsGranary', 'status', 'warnDays', 'isPackUse', 'isUstime', 'isQccodeMgn', 'isPreSales', 'isAdvent', 'isHighValue', 'isLabel', 'effectiveDistinguish', 'goodsAttr', 'capacityReplenishmentMin','companyNo','cusNo','goodsUnit','dateBzDay','dateBz','boxQty','boxVolume','boxWeight','fillingWeight','airEmbargo','dnExternal','goodsProductNo','countryOrigin','matDes','matUrls','matNames','adventdays','goodsgrade','title','words','packageUseMaterial','offShelfDays','rejectionDays','capacityReplenishmentSafety','extend4','extend5','extend6','extend7','categoryId','goodsPrice','styleCode','supplierStyleCode','year','implStandards','category','level','sectionNumber','season','fabric','operator','hsCode','curr','packageSp','fillWeight','ecommerceCode','color','price','salesPrice'))
    },


  }
}
</script>
<style scoped>
>>> .ant-modal-content .ant-modal-body .ant-form-item {
  display: inline-block !important;
  width: 33%;
}

>>> .ant-modal-content .ant-modal-body {
  max-height: 460px !important;
  overflow-y: auto;
}
</style>