export const listCheckMixin = {
  data(){
    return {

    }
  },
  methods: {
    handleCheck: function (record) {
      this.$refs.modalForm.edit(record);
      this.$refs.modalForm.title = this.$t('查看');
      this.$refs.modalForm.disableSubmit = false;
    },
  }
}