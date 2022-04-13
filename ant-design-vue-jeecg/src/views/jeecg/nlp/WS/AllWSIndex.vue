<template>
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="数据集内容">
              <a-input
                v-decorator="[
                'DataContent', // 给表单赋值或拉取表单时，该input对应的key
                {rules: [{ required: true, message: '请输入数据集内容!' }]}
                ]"
                placeholder="请输入数据集内容"
                v-model="DataContent">
              </a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="模型名称">
              <a-select
                v-decorator="[
                'ModelName', // 给表单赋值或拉取表单时，该input对应的key
                {rules: [{ required: true, message: '请选择模型名称!' }]}
                ]"
                placeholder="请选择模型名称"
                v-model="ModelName">
                <a-select-option value="HanLP">HanLP</a-select-option>
                <a-select-option value="Jieba">Jieba</a-select-option>
                <a-select-option value="LTP">LTP</a-select-option>
                <a-select-option value="Thulac">Thulac</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">进行分词</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
      <AllWS :ShowModel = "ShowModel" :DataSet = "DataSet"></AllWS>
    </div>
    <!-- 查询区域-END -->
</template>
<script>
  import AllWS from './AllWS.vue'
    export default {
        name: "AllWSIndex",
        components: {
          AllWS
        },
      data(){
        return{
          DataContent: undefined,
          ModelName: undefined,
          DataSet: '',
          ShowModel:'',
          form: this.$form.createForm(this), // 只有这样注册后，才能通过表单拉取数据
        }
      },
      methods: {
        searchQuery(){
          //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！
          this.form.validateFields((err, values) => {
            //判断必填项校验是否通过
            if (!err) {
              this.ShowModel = this.ModelName;
              this.DataSet = this.DataContent;
              console.log(values)
            }
          })
        },
        //将输入框置为空
        searchReset(){
          this.DataContent = undefined;
          this.ModelName = undefined;
          //加入v-decorator后无法使用v-model进行双向数据绑定，通过 setFieldsValue() 方法进行数据改变
          this.form.setFieldsValue({
            DataContent: undefined,
            ModelName: undefined
          })
        }
      }
    }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>