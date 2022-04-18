<template>
  <div class="table-page-search-wrapper">
    <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
      <a-row :gutter="24">
        <a-col :xl="6" :lg="7" :md="8" :sm="24">
          <a-form-item label="数据集内容">
            <a-input
              v-decorator="[
                'dataset', // 给表单赋值或拉取表单时，该input对应的key
                {rules: [{ required: true, message: '请输入数据集内容!' }]}
                ]"
              placeholder="请输入数据集内容"
              v-model="dataset">
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :xl="3" :lg="7" :md="8" :sm="24">
          <a-form-item label="是否展示词性">
            <a-switch  :checked="checked" @change="onChange"></a-switch>
          </a-form-item>
        </a-col>
        <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="PartOfSpeech" icon="question-circle ">词性说明</a-button>
              <a-button type="primary" @click="searchQuery" icon="search" style="margin-left: 8px">进行分词</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
        </a-col>
      </a-row>
    </a-form>
    <a-modal v-model:visible="visible" title="词性说明" @ok="handleOk">
      <p>Some contents...</p>
      <p>Some contents...</p>
      <p>Some contents...</p>
    </a-modal>
    <a-card :bordered="false">
      <a-tabs defaultActiveKey="1" @change="callback">
        <a-tab-pane tab="柱状图" key="1">
          <a-row>
            <a-col :span="10">
              <a-radio-group :value="barType" @change="statisticst">
                <a-radio-button value="WordSegmentation">分词统计</a-radio-button>
                <a-radio-button value="WordSegmentation_n">名词统计</a-radio-button>
              </a-radio-group>
            </a-col>
            <bar class="statistic" title="分词结果" :dataSource="countSource" :height="400"/>
          </a-row>
        </a-tab-pane>
        <a-tab-pane tab="饼状图" key="2">
          <a-row :gutter="24">
            <a-col :span="8">
              <a-radio-group :value="pieType" @change="statisticst">
                <a-radio-button value="WordSegmentation">分词统计</a-radio-button>
                <a-radio-button value="WordSegmentation_n">名词统计</a-radio-button>
              </a-radio-group>
            </a-col>
            <pie class="statistic" title="分词结果" :dataSource="countSource" :height="450"/>
          </a-row>
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </div>
</template>

<script>
  import Bar from '@/components/chart/Bar'
  import Pie from '@/components/chart/Pie'
  import ACol from 'ant-design-vue/es/grid/Col'
  import { getAction } from '@/api/manage'
  import { message, Button } from 'ant-design-vue'

  export default {
    name: 'HanLPWS',
    components: {
      ACol,
      Bar,
      Pie
    },
    data() {
      return {
        description: '档案统计页面',
        // 查询条件
        queryParam: {},
        // 数据集
        countSource: [],
        // 柱状图
        barType: 'WordSegmentation',
        barDate: ['month', 'month'],
        barValue: [],
        // 饼状图
        pieType: 'WordSegmentation',
        pieDate: ['month', 'month'],
        pieValue: [],
        // 统计图类型
        tabStatus:"bar",
        dataset: "",
        checked:false,
        visible:false,
        form: this.$form.createForm(this), // 只有这样注册后，才能通过表单拉取数据
        url: {
          getYearCountInfo: "/mock/api/report/getYearCountInfo",
          getMonthCountInfo:"/mock/api/report/getMonthCountInfo",
          getCntrNoCountInfo:"/mock/api/report/getCntrNoCountInfo",
          getCabinetCountInfo:"/mock/api/report/getCabinetCountInfo",
          getParticiple:"/jeecg-demo/mynlp/hanlp/hanLPWS",
        },
      }
    },
    created() {
      //let url = this.url.getParticiple;
      //this.loadDate(url,'participle',{type:"get_all",dataSet: this.dataSet,});
    },
    methods: {
      loadDate(url,type,param) {
        const loding = message.loading('模型加载中，请稍后...',0);
        getAction(url,param,'get').then((res) => {
          loding();
          message.success('模型加载成功！');
          console.info("res.success = " + res.success);
          if (res.success) {
            console.info(res);
            this.countSource = [];
            if(type === 'year'){
              this.getYearCountSource(res.result);
            }
            if(type === 'month'){
              console.info("month= "+ res);
              this.getMonthCountSource(res.result);
            }
            if(type === 'category'){
              this.getCategoryCountSource(res.result);
            }
            if(type === 'cabinet'){
              this.getCabinetCountSource(res.result);
            }
            if(type === 'WordSegmentation'){
              let json_Data = JSON.parse(res.result);
              this.getParticipleCountSource(json_Data.data);
            }
            if(type === 'WordSegmentation_n'){
              let json_Data = JSON.parse(res.result);
              this.getParticipleCountSource(json_Data.data);
            }
          }else{
            var that=this;
            that.$message.warning(res.message);
          }
        })
      },
      getCategoryCountSource(data){
        for (let i = 0; i < data.length; i++) {
          if(this.tabStatus ==="bar"){
            this.countSource.push({
              x: data[i].classifyname,
              y: data[i].cntrnocount
            })
          }else{
            this.countSource.push({
              item: data[i].classifyname,
              count:data[i].cntrnocount
            })
          }
        }
      },
      getCabinetCountSource(data){
        for (let i = 0; i < data.length; i++) {
          if(this.tabStatus === "bar"){
            this.countSource.push({
              x: data[i].cabinetname,
              y: data[i].cabinetcocunt
            })
          }else{
            this.countSource.push({
              item: data[i].cabinetname,
              count:data[i].cabinetcocunt
            })
          }
        }
      },
      getParticipleCountSource(data){
        for (let i = 0; i < data.length; i++) {
          if(this.tabStatus === "bar"){
            let nature = data[i].nature;
            let word = data[i].word;
            let word_nature = word + "/" + nature;
            this.countSource.push({
              x: data[i].nature,
              y: 650
            })
          }else{
            this.countSource.push({
              item: data[i].nature,
              count:2
            })
          }
        }
      },
      // 选择统计图类别
      callback(key) {
        //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！
        this.form.validateFields((err, values) => {
          //判断必填项校验是否通过
          if (!err) {
            if(key === "1"){
              this.tabStatus = "bar";
              this.queryDatebar();
            }else{
              this.tabStatus = "pie";
              this.queryDatepie();
            }
            console.log(values)
          }
        })
      },
      // 选择统计类别
      statisticst(e) {
        //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！
        this.form.validateFields((err, values) => {
          //判断必填项校验是否通过
          if (!err) {
            if(this.tabStatus === "pie"){
              this.pieType = e.target.value;
              this.queryDatepie();
            }else{
              this.barType = e.target.value;
              this.queryDatebar();
            }
            console.log(values)
          }
        })
      },
      // 按月份查询
      queryDatebar(){
        if(this.barValue.length>0){
          this.getUrl(this.barType,{startTime:this.barValue[0]._d,endTime:this.barValue[1]._d});
        }else{
          this.getUrl(this.barType,{});
        }
      },
      queryDatepie(){
        if(this.pieValue.length>0){
          this.getUrl(this.pieType,{startTime:this.pieValue[0]._d,endTime:this.pieValue[1]._d});
        }else{
          this.getUrl(this.pieType,{});
        }
      },
      searchReset(){
        console.log(this.tabStatus);
        if(this.tabStatus === "pie"){
          this.pieValue = [];
        }else{
          this.barValue = [];
        }
        this.getUrl(this.barType,{});
      },
      // 选择请求url
      getUrl(type,param){
        let url = "";
        if(type === 'year'){
          url = this.url.getYearCountInfo;
        }
        if(type === 'month'){
          url = this.url.getMonthCountInfo;
        }
        if(type === 'category'){
          url = this.url.getCntrNoCountInfo;
        }
        if(type === 'cabinet'){
          url = this.url.getCabinetCountInfo;
        }
        if(type === 'WordSegmentation'){
          param = {
            type:"get_all",
            dataSet: this.dataset
          };
          url = this.url.getParticiple;
        }
        if(type === 'WordSegmentation_n'){
          param = {
            type:"get_n",
            dataSet: this.dataset
          };
          url = this.url.getParticiple;
        }
        this.loadDate(url,type,param);
      },
      // 选择月份日期
      handleBarDate(value, mode) {
        this.barValue = value
        this.barDate = [
          mode[0] === 'date' ? 'month' : mode[0],
          mode[1] === 'date' ? 'month' : mode[1]
        ]
      },
      handlePieDate(value, mode) {
        this.pieValue = value
        this.pieDate = [
          mode[0] === 'date' ? 'month' : mode[0],
          mode[1] === 'date' ? 'month' : mode[1]
        ]
      },
      //是否开启展示词性功能
      onChange(checked) {
        this.checked = checked;
        console.log(`切换 to ${checked}`);
      },
      //将输入框置为空
      searchReset(){
        this.dataset = undefined;
        this.checked = false;
        //加入v-decorator后无法使用v-model进行双向数据绑定，通过 setFieldsValue() 方法进行数据改变
        this.form.setFieldsValue({
          dataset: undefined,
          checked: false
        })
      },
      //是否弹出词性说明对话框
      PartOfSpeech(){
        this.visible = true;
      },
      //关闭词性说明对话框窗口
      handleOk(){
        this.visible = false;
      },
      //进行分词
      searchQuery(){
        //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！
        this.form.validateFields((err, values) => {
          //判断必填项校验是否通过
          if (!err) {
            this.getUrl("WordSegmentation",{});
            console.log(values)
          }
        })
      }
    }
  }
</script>
<style scoped>
  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }

  .statistic {
    padding: 0px !important;
    margin-top: 50px;
  }

  .statistic h4 {
    margin-bottom: 20px;
    text-align: center !important;
    font-size: 24px !important;;
  }

  .statistic #canvas_1 {
    width: 100% !important;
  }
</style>