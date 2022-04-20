<template>
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
        <a-row :gutter="24">
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="数据集名称">
              <a-select
                v-decorator="[
                'DataSetName', // 给表单赋值或拉取表单时，该input对应的key
                {rules: [{ required: true, message: '请选择数据集名称!' }]}
                ]"
                placeholder="请选择数据集名称"
                :value="DataSetName"
                @change="handleDataSetChange">
                <a-select-option v-for="d in dataSets" :key="d.id" :value="d.id">{{d.dtName}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="模型名称">
              <a-select
                v-decorator="[
                'ModelName', // 给表单赋值或拉取表单时，该input对应的key
                {rules: [{ required: true, message: '请选择模型名称!' }]}
                ]"
                placeholder="请选择模型名称"
                :value="ModelName"
                @change='handleModelChange'>
                <a-select-option v-for="m in models" :key="m.id" :value="m.modelName">{{m.modelName}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="3" :lg="7" :md="8" :sm="24">
            <a-form-item label="是否展示词性">
              <a-switch  :checked="checked" @change="onChange"></a-switch>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchPartOfSpeech" icon="question-circle">词性说明</a-button>
              <a-button type="primary" @click="searchDataSet" icon="file-text" style="margin-left: 8px">查看数据集</a-button>
              <a-button type="primary" @click="searchQuery" icon="search" style="margin-left: 8px">进行分词</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
      <a-modal v-model:visible="visible" :title="modalTitle" @ok="handleOk">
        <PartOfSpeech v-show="PSVisible"></PartOfSpeech>
        <div v-show="DataSetVisible">{{dataSetContent}}</div>
      </a-modal>
      <WSIndex :ShowModel = "ShowModel" :DataSet = "DataSet" :dataSetId = "dataSetId" :checked = "checked"></WSIndex>
    </div>
    <!-- 查询区域-END -->
</template>
<script>
  import WSIndex from '@/views/jeecg/nlp/WS/WSIndex.vue'
  import PartOfSpeech from '@/views/jeecg/nlp/WS/PartOfSpeech/PartOfSpeech.vue'
  import { getAction } from '@/api/manage'  //调用main.js中的通用请求方法

    export default {
        name: "DataSetWS",
        components: {
          WSIndex,
          PartOfSpeech
        },
      data(){
        return{
          checked:false,  //词性展示开启关闭
          visible:false,  //对话框开启关闭
          DataSetName: undefined,
          ModelName: undefined,
          DataSet: '',  //双向绑定传递给子组件
          ShowModel:'', //双向绑定传递给子组件
          models:[],  //模型下拉列表数组
          dataSets:[],  //数据集名称下拉列表数组
          dataSetContent:"",  //数据集内容
          dataSetId:"", //选中的数据集id
          modalTitle:"",  //对话框标题
          PSVisible:false,
          DataSetVisible:false,
          url: {
            getModelData:"/jeecg-demo/mynlp/tbNlpModel/list", //请求后台模型数据API接口
            getDataSetData:"/jeecg-demo/mynlp/tbNlpDataset/list", //请求后台数据集数据API接口
            getDataSetDataById:"/jeecg-demo/mynlp/tbNlpDataset/queryById", //请求后台数据集数据API接口
          },
          form: this.$form.createForm(this), // 只有这样注册后，才能通过表单拉取数据
        }
      },
      //在页面创建成功后执行加载下拉列表的数据
      created() {
        let modelUrl = this.url.getModelData;
        let dataSetUrl = this.url.getDataSetData;
        this.loadData(modelUrl,"model",null);
        this.loadData(dataSetUrl,"dataSet",null);
      },
      methods: {
        //进行分词
        searchQuery(){
          //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！
          this.form.validateFields((err, values) => {
            console.log("err = " + err);
            //判断必填项校验是否通过
            if (!err) {
              //通过双向绑定改变监视属性调用模型
              this.DataSet = this.dataSetContent;
              this.ShowModel = this.ModelName;
              console.log("values = " + values);
            }
          })
        },
        //查看数据集
        searchDataSet(){
          //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！(对指定字段进行校验，第一个参数是一个数组，数组里是指定字段的名称)
          this.form.validateFields(['DataSetName'],(err) => {
            console.log("err = " + err);
            //判断必填项校验是否通过
            if (!err) {
              this.modalTitle = this.DataSetName;
              this.visible = true;
              this.PSVisible = false;
              this.DataSetVisible = true;
            }
          })
        },
        //是否开启展示词性功能
        onChange(checked) {
          this.checked = checked;
        },
        //是否弹出词性说明对话框
        searchPartOfSpeech(){
          this.modalTitle = "863词性标注集";
          this.visible = true;
          this.PSVisible = true;
          this.DataSetVisible = false;
        },
        //关闭词性说明对话框窗口
        handleOk(){
          this.visible = false;
        },
        //将输入框置为空
        searchReset(){
          this.DataSetName = undefined;
          this.ModelName = undefined;
          this.checked = false;
          //加入v-decorator后无法使用v-model进行双向数据绑定，通过 setFieldsValue() 方法进行数据改变
          this.form.setFieldsValue({
            DataSetName: undefined,
            ModelName: undefined,
            checked: false
          })
        },
        //发送axios异步请求加载数据
        loadData(url,type,param) {
          getAction(url,param).then((res) => {
            console.info("res.success = " + res.success);
            if (res.success) {
              if(type === "model"){
                this.models = res.result.records;
              }
              if(type === "dataSet"){
                this.dataSets = res.result.records;
              }
            }else{
              var that = this;
              that.$message.warning(res.message);
            }
          })
        },
        //绑定选中的模型名称
        handleModelChange(value){
          this.ModelName = value;
        },
        //绑定选中的数据集名称(传进来的value是select组件选中的value值)
        handleDataSetChange(value){
          let obj = {};
          //从当前dataSets数组中寻找
          obj = this.dataSets.find(function (item) {
            //判断id相等
            return item.id === value;
          });
          console.info("obj.dtName = " + obj.dtName);
          console.info("obj.id = " + obj.id);
          //obj就是被选中的那个对象
          this.dataSetId = value;
          this.DataSetName = obj.dtName;
          this.dataSetContent = obj.dtText;
        }
      }
    }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>