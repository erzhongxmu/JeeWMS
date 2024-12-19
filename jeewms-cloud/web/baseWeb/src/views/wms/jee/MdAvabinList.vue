<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="仓位">
              <a-input placeholder="请输入仓位" v-model="queryParam.storeCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="库位">
              <a-input placeholder="请输入库位" v-model="queryParam.kwCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="层数">
              <a-input placeholder="请输入层数" v-model="queryParam.znode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="行数">
              <a-input placeholder="请输入行数" v-model="queryParam.rowNum"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="列数">
              <a-input placeholder="请输入列数" v-model="queryParam.colNum"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="方向">
              <a-select default-value="0" v-model="queryParam.type">
                <a-select-option value="0">
                  正向
                </a-select-option>
                <a-select-option value="1">
                  反向
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="其他">
              <a-input placeholder="请输入其他信息" v-model="queryParam.des"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">

          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button  @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
<!--              <a @click="handleToggleSearch" style="margin-left: 8px">-->
<!--                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}-->
<!--                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
<!--              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    <div id="bottom" :style="{width: width+'px'}">
      <div v-for="(item,key) in list" class='all' href='javascript:void(0);' :style="{background:''+item.colour+''}" @click="addtab(item.des)">{{item.tincount}}</div>
    </div>

    <j-modal
      title="信息"
      :visible="visible"
      :footer="null"
      @cancel="handleCancel"
    >
      <p>{{ des?des:'无' }}</p>
    </j-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import { getAction } from '@/api/manage'

  export default {
    name: 'MdAvabinList',
    data() {
      return {
        toggleSearchStatus: false,
        queryParam: {
          storeCode: '',
          kwCode: '',
          des: '',
          znode: '01',
          type: '0',
          rowNum: '42',//行数
          colNum: '34',//列数
        },
        url: {
          list: "/jeewms/baKw/getKwPicData",
        },
        // 创建一个空数字 用于填充空格子(因为空格子后台不返回 前端循环渲染需要填充数据)
        dataSource: [],
        // 创建数组填充所有格子
        list: [],
        width: 17.5,
        des: '',
        visible: false,
      }
    },
    created() {

    },
    methods: {
      handleToggleSearch() {
        this.toggleSearchStatus = !this.toggleSearchStatus
      },

      //重置查询条件
      searchReset() {
        this.queryParam = {
          storeCode: '',
          kwCode: '',
          des: '',
          znode: '',
          type: '0',
          rowNum: '',
          colNum: '',
        }
      },

      //查询
      searchQuery() {
        this.list = [];
        var that = this;
        var cangku;
        var chuwei;
        var des;
        cangku = that.queryParam.storeCode;
        chuwei = that.queryParam.kwCode;
        var hangshu = that.queryParam.rowNum * 1;
        var lieshu = that.queryParam.colNum * 1;
        var cengshu = that.queryParam.znode * 1;
        des = that.queryParam.des;
        var params = that.queryParam;
        getAction(that.url.list, params).then((res) => {
          if (res.success) {
            that.dataSource = res.result.dataList;
            if(that.dataSource.length > 0) {
              that.width = 17.5 * hangshu*1
              var num = hangshu * lieshu +1;  // 行数乘以列数  计算一共有多少格子
              let obj ={
                bin_store: "",
                binid: "",
                colour: "white",
                des: "",
                tincount: "",
                xnode: "",
                ynode: "",
                znode: "",
              } ;// 创建一个空数字 用于填充空格子(因为空格子后台不返回 前端循环渲染需要填充数据)
              //循环填充数组
              for(let s = 0 ; s < num ; s ++){
                that.list.push(obj);
              }
              for(let o = 0; o < this.dataSource.length; o++){
                if(that.dataSource[o].ynode*1 == 1){
                  console.log(that.dataSource[o].ynode*1 * that.dataSource[o].xnode*1 )
                  that.list[that.dataSource[o].ynode*1 * that.dataSource[o].xnode*1-1 ] = that.dataSource[o]
                }
                else{
                  console.log((that.dataSource[o].ynode*1 - 1) * hangshu*1 + that.dataSource[o].xnode*1)
                  that.list[(that.dataSource[o].ynode*1 - 1) * hangshu*1 + that.dataSource[o].xnode*1 -1] = that.dataSource[o]
                }
              }
            }
          }
          if(res.code===510){
            this.$message.warning(this.$t('操作失败'))
          }
          this.loading = false;
        })
      },
      addtab(des) {
        this.des =des;
        this.visible = true
      },
      handleCancel() {
        this.visible = false
      }
    }
  }
</script>

<style scoped>
  @import '~@assets/less/common.less';
  a.a01:link, a.a01:visited {
    font-size: 12px;
    font-family: verdana;
    /*width: 90px;*/
    margin: 1px;
    color: #1A1A1A;
    display: inline-table;
    background-color: #FF4040;
  }

  a.a01:active, a.a01:hover {
    font-size: 12px;
    font-family: verdana;
    /*width: 90px;*/
    margin: 1px;
    color: #1A1A1A;
    display: inline-table;
  }

  a.a02:link, a.a01:visited {
    font-size: 12px;
    font-family: verdana;
    /*width: 90px;*/
    margin: 1px;
    color: #1A1A1A;
    display: inline-table;
    background-color: #66CD00;
  }

  a.a02:active, a.a01:hover {
    font-size: 12px;
    font-family: verdana;
    /*width: 90px;*/
    margin: 1px;
    color: #1A1A1A;
    display: inline-table;
  }
  .all{
    /*display: inline-block;*/
    width: 16.5px;
    height:16.5px;
    line-height: 16.5px;
    text-align: center;
    margin-right: 1px;
    margin-bottom: 1px;
  }
  #bottom{
    display: flex;
    flex-wrap: wrap;
    margin: auto;
  }
</style>