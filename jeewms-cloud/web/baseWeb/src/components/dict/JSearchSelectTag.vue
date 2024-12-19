<template>
  <a-select
    v-if="async"
    showSearch
    labelInValue
    :disabled="disabled"
    :getPopupContainer="getParentContainer"
    @search="loadData"
    :placeholder="placeholder"
    v-model="selectedAsyncValue"
    style="width: 100%"
    :filterOption="false"
    @change="handleAsyncChange"
    @popupScroll="handlePopupScroll"
    @blur="handleOnBlur"
    allowClear
    :notFoundContent="loading ? undefined : null"
  >
    <a-spin v-if="loading" slot="notFoundContent" size="small" />
    <a-select-option v-for="d in frontDataZ" :key="d.value" :value="d.value">{{ d.text }}</a-select-option>
  </a-select>

  <a-select
    v-else
    :getPopupContainer="getParentContainer"
    showSearch
    :disabled="disabled"
    :placeholder="placeholder"
    optionFilterProp="children"
    style="width: 100%"
    @change="handleChange"
    :filterOption="filterOption"
    @popupScroll="handlePopupScroll"
    v-model="selectedValue"
    @blur="handleOnBlur"
    allowClear
    @search="handleSearch"
    :notFoundContent="loading ? undefined : null"
  >
    <a-spin v-if="loading" slot="notFoundContent" size="small" />
    <a-select-option v-for="(d,index) in frontDataZ" :key="index+d.value" :value="d.value">{{ d.text }}</a-select-option>
  </a-select>
</template>

<script>
  import { ajaxGetDictItems, getDictItemsFromCache } from '@/api/api'
  import debounce from 'lodash/debounce'
  import { getAction } from '../../api/manage'

  export default {
    name: 'JSearchSelectTag',
    props: {
      disabled: Boolean,
      value: [String, Number],
      dict: String,
      dictOptions: Array,
      async: Boolean,
      placeholder: String,
      popContainer: {
        type: String,
        default: '',
        required: false
      },
      pageSize: {
        type: Number,
        default: 10,
        required: false
      }
    },
    data() {
      this.loadData = debounce(this.loadData, 800) //消抖
      this.lastLoad = 0
      return {
        loading: false,
        selectedValue: [],
        selectedAsyncValue: [],
        options: [],
        frontDataZ: [],
        sourceOwnerSystems: [],
        valueData: '',
        treePageSize: 200,
        scrollPage: 1,
        allDataZ:[]
      }
    },
    created() {
      this.initDictData()
    },
    watch: {
      value: {
        immediate: true,
        handler(val) {
          if (!val) {
            if (val == 0) {
              this.initSelectValue()
            } else {
              this.selectedValue = []
              this.selectedAsyncValue = []
            }
          } else {
            this.initSelectValue()
          }
        }
      },
      dict: {
        handler() {
          this.initDictData()
        }
      },
      dictOptions: {
        deep: true,
        handler(val) {
          if (val && val.length > 0) {
            this.options = [...val]
            this.frontDataZ = [...val].slice(0, this.treePageSize)
          }
        }
      }
    },
    methods: {
      handleOnBlur(e) {
        this.frontDataZ = this.options.slice(0, this.treePageSize)
      },
      handlePopupScroll(e) {
        const { target } = e
        const scrollHeight = target.scrollHeight - target.scrollTop
        const clientHeight = target.clientHeight
        // 下拉框不下拉的时候
        if (scrollHeight === 0 && clientHeight === 0) {
          this.scrollPage = 1
          console.log(this.scrollPage)
        } else {
          // 当下拉框滚动条到达底部的时候
          if (scrollHeight < clientHeight + 5) {
            this.scrollPage = this.scrollPage + 1
            const scrollPage = this.scrollPage // 获取当前页
            const treePageSize = this.treePageSize * (scrollPage || 1) // 新增数据量
            const newData = [] // 存储新增数据
            let max = '' // max 为能展示的数据的最大条数
            if (this.options.length > treePageSize) {
              // 如果总数据的条数大于需要展示的数据
              max = treePageSize
            } else {
              // 否则
              max = this.options.length
            }
            // 判断是否有搜索
            if (this.valueData) {
              this.allDataZ.forEach((item, index) => {
                if (index < max) {
                  // 当data数组的下标小于max时
                  newData.push(item)
                }
              })
            } else {
              this.options.forEach((item, index) => {
                if (index < max) {
                  // 当data数组的下标小于max时
                  newData.push(item)
                }
              })
            }

            this.frontDataZ = newData // 将新增的数据赋值到要显示的数组中
          }
        }
      },
      handleSearch(val) {
        this.valueData = val
        if (!val) {
          // this.showTabelCi List()
        } else {
          this.frontDataZ = []
          this.scrollPage = 1
          this.options.forEach(item => {
            // if (item.text.indexOf(val) >= 0) {添加了区分大小写功能
            if (item.text.toLowerCase().indexOf(val.toLowerCase()) >= 0) {
              this.frontDataZ.push(item)
            }
          })
          this.allDataZ = this.frontDataZ
          this.frontDataZ = this.frontDataZ.slice(0, this.treePageSize)
        }
      },

      initSelectValue() {
        if (this.async) {
          if (!this.selectedAsyncValue || !this.selectedAsyncValue.key || this.selectedAsyncValue.key != this.value) {
            console.log('这才请求后台')
            getAction(`/sys/dict/loadDictItem/${this.dict}`, { key: this.value }).then(res => {
              if (res.success) {
                let obj = {
                  key: this.value,
                  label: res.result
                }
                this.selectedAsyncValue = { ...obj }
              }
            })
          }
        } else {
          this.selectedValue = this.value.toString()
        }
      },
      loadData(value) {
        console.log('数据加载', value)
        this.lastLoad += 1
        const currentLoad = this.lastLoad
        this.options = []
        this.loading = true
        // 字典code格式：table,text,code
        getAction(`/sys/dict/loadDict/${this.dict}`, { keyword: value, pageSize: this.pageSize }).then(res => {
          this.loading = false
          if (res.success) {
            if (currentLoad != this.lastLoad) {
              return
            }
            this.options = res.result
            console.log('我是第一个', res)
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
        })
      },
      initDictData() {
        if (!this.async) {
          //如果字典项集合有数据
          if (this.dictOptions && this.dictOptions.length > 0) {
            this.options = [...this.dictOptions]
            this.frontDataZ = this.options.slice(0, this.treePageSize)
          } else {
            //根据字典Code, 初始化字典数组
            let dictStr = ''
            if (this.dict) {
              let arr = this.dict.split(',')
              if (arr[0].indexOf('where') > 0) {
                let tbInfo = arr[0].split('where')
                dictStr = tbInfo[0].trim() + ',' + arr[1] + ',' + arr[2] + ',' + encodeURIComponent(tbInfo[1])
              } else {
                dictStr = this.dict
              }
              if (this.dict.indexOf(',') == -1) {
                //优先从缓存中读取字典配置
                if (getDictItemsFromCache(this.dictCode)) {
                  this.options = getDictItemsFromCache(this.dictCode)
                  this.frontDataZ = this.options.slice(0, this.treePageSize)
                  return
                }
              }
              ajaxGetDictItems(dictStr, null).then(res => {
                if (res.success) {
                  let arr3 = res.result.filter(item =>{
                    if(item){
                      return item
                    }
                  })
                  this.options = this.deWeight(arr3)
                  this.frontDataZ = this.options.slice(0, this.treePageSize)
                }
              })
            }
          }
        } else {
          //异步一开始也加载一点数据
          this.loading = true
          getAction(`/sys/dict/loadDict/${this.dict}`, { pageSize: this.pageSize, keyword: '' }).then(res => {
            this.loading = false
            if (res.success) {
              this.options = res.result
              this.frontDataZ = this.options.slice(0, this.treePageSize)
            } else {
              this.$message.warning(this.$t('操作失败'))
            }
          })
        }
      },
      deWeight(arr){
        let newsArr = [];
        for (let i = 0; i < arr.length; i++) {
          if(newsArr.indexOf(arr[i]) === -1){
          newsArr.push(arr[i]);
          }
        }
        return newsArr;
        },
      filterOption(input, option) {
        return option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      },
      handleChange(selectedValue) {
        this.selectedValue = selectedValue

        this.callback()
      },
      handleAsyncChange(selectedObj) {
        //update-begin-author:scott date:20201222 for:【搜索】搜索查询组件，删除条件，默认下拉还是上次的缓存数据，不好 JT-191
        if (selectedObj) {
          this.selectedAsyncValue = selectedObj
          this.selectedValue = selectedObj.key
        } else {
          this.selectedAsyncValue = null
          this.selectedValue = null
          this.options = null
          this.loadData('')
        }
        this.callback()
        //update-end-author:scott date:20201222 for:【搜索】搜索查询组件，删除条件，默认下拉还是上次的缓存数据，不好 JT-191
      },
      callback() {
        let str = ''
        this.options.map(item => {
          if (item.value == this.selectedValue) {
            str = item.text
          }
        })
        this.$emit('change', this.selectedValue, str)
      },
      setCurrentDictOptions(dictOptions) {
        this.options = dictOptions
      },
      getCurrentDictOptions() {
        return this.options
      },
      getParentContainer(node) {
        if (!this.popContainer) {
          return node.parentNode
        } else {
          return document.querySelector(this.popContainer)
        }
      }
    },
    model: {
      prop: 'value',
      event: 'change'
    }
  }
</script>

<style scoped>
</style>