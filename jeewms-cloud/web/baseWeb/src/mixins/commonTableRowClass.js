import Vue from 'vue'

export const commonTableRowClass = {
  data(){
    return {

    }
  },
  methods: {
    setRowClsaa(record, index) {
      let className = '';
      className = index % 2 === 0 ? 'oddRow' : 'evenRow';
      return className;
    }
  }
}