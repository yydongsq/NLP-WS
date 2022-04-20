<template>
  <a-card :bordered="false">
    <a-tabs defaultActiveKey="1" @change="callback">
      <a-tab-pane tab="柱状图" key="1">
        <a-row>
          <a-col :span="10">
            <a-radio-group :value="barType" @change="statisticst">
              <a-radio-button value="WordSegmentation">{{ShowReloadModel}}分词统计</a-radio-button>
              <a-radio-button value="WordSegmentation_n">{{ShowReloadModel}}名词统计</a-radio-button>
            </a-radio-group>
          </a-col>
          <bar class="statistic" title="分词结果" :dataSource="countSource" :height="400"/>
        </a-row>
      </a-tab-pane>

      <a-tab-pane tab="饼状图" key="2">
        <a-row :gutter="24">
          <a-col :span="8">
            <a-radio-group :value="pieType" @change="statisticst">
              <a-radio-button value="WordSegmentation">{{ShowReloadModel}}分词统计</a-radio-button>
              <a-radio-button value="WordSegmentation_n">{{ShowReloadModel}}名词统计</a-radio-button>
            </a-radio-group>
          </a-col>
          <pie class="statistic" title="分词结果" :dataSource="countSource" :height="450"/>
        </a-row>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script>
  import Bar from '@/components/chart/Bar'
  import Pie from '@/components/chart/Pie'
  import ACol from 'ant-design-vue/es/grid/Col'
  import { putNlpAction } from '@/api/manage'
  import { message, Button } from 'ant-design-vue';

  export default {
    name: 'WSIndex',
    //接收父组件传来的属性
    //props:['ShowModel'],
    props: {
      ShowModel: Object,
      DataSet: Object,
      checked: Object,
      dataSetId: Object
    },
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
        ShowReloadModel: '',
        ShowModelUpdate: false,
        url: {
          getYearCountInfo: "/mock/api/report/getYearCountInfo",
          getMonthCountInfo:"/mock/api/report/getMonthCountInfo",
          getCntrNoCountInfo:"/mock/api/report/getCntrNoCountInfo",
          getCabinetCountInfo:"/mock/api/report/getCabinetCountInfo",
          getHanLPWS:"/jeecg-demo/mynlp/hanlp/hanLPWS",
          getJiebaWS:"/jeecg-demo/mynlp/jieba/jiebaWS",
          getLtpWS:"/jeecg-demo/mynlp/ltp/ltpWS",
          getThulacWS:"/jeecg-demo/mynlp/thulac/thulacWS",
        },
        initShow:"",
      }
    },
    methods: {
      loadDate(url,type,param,dsJsonString) {
        const loding = message.loading('模型加载中，请稍后...',0);
        putNlpAction(url,param,dsJsonString).then((res) => {
          loding();
          message.success('模型加载成功！');
          this.ShowReloadModel = this.ShowModel;
          console.info("res.success = " + res.success);
          if (res.success) {
            console.info(res);
            this.countSource = [];
            if(type === 'WordSegmentation'){
              let json_Data = JSON.parse(res.result);
              this.getWSData(json_Data.data);
            }
            if(type === 'WordSegmentation_n'){
              let json_Data = JSON.parse(res.result);
              this.getWSData(json_Data.data);
            }
          }else{
            var that = this;
            that.$message.warning(res.message);
          }
        })
      },
      getWSData(data){
        let wordOrNature = "";
        console.info("checked=" + this.checked);
        for (let i = 0; i < data.length; i++) {
          wordOrNature = this.checked === true ? data[i].word + "/" + data[i].nature : data[i].word;
          if(this.tabStatus === "bar"){
            this.countSource.push({
              x: wordOrNature,
              y: 650
            })
          }else{
            this.countSource.push({
              item: wordOrNature,
              count:2
            })
          }
        }
      },
      // 选择统计图类别
      callback(key) {
        if(key === "1"){
          this.tabStatus = "bar";
          this.queryDatebar();
        }else{
          this.tabStatus = "pie";
          this.queryDatepie();
        }
      },
      // 选择统计类别
      statisticst(e) {
        if(this.tabStatus === "pie"){
          this.pieType = e.target.value;
          this.queryDatepie();
        }else{
          this.barType = e.target.value;
          this.queryDatebar();
        }
      },
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
        let showModel = this.ShowModel;
        let dataSetId = this.dataSetId;
        let dsJsonString = "";
        console.info("dataSetId = " + dataSetId);
        if(this.dataSetId === undefined){
          let dataSetModel = {
            dtText:this.DataSet
          };
          dsJsonString = JSON.stringify(dataSetModel);
        }
        let url = "";
        //type为分词类型
        if(type === 'WordSegmentation' && showModel === "HanLP"){
          param = {
            dataSetId:dataSetId,
            type:"get_all"
          };
          url = this.url.getHanLPWS;
        }
        if(type === 'WordSegmentation' && showModel === "Thulac"){
          param = {
            dataSetId:dataSetId,
            type:"get_all"
          };
          url = this.url.getThulacWS;
        }
        if(type === 'WordSegmentation' && showModel === "Jieba"){
          param = {
            dataSetId:dataSetId,
            type:"get_all"
          };
          url = this.url.getJiebaWS;
        }
        if(type === 'WordSegmentation' && showModel === "Ltp"){
          param = {
            dataSetId:dataSetId,
            type:"get_all"
          };
          url = this.url.getLtpWS;
        }
        if(type === 'WordSegmentation_n' && showModel === "HanLP"){
          param = {
            dataSetId:dataSetId,
            type:"get_n"
          };
          url = this.url.getHanLPWS;
        }
        if(url !== ""){
          console.info("dsJsonString = " + dsJsonString);
          this.loadDate(url,type,param,dsJsonString);
        }
      },
    },
    created() {
      //let url = this.url.getParticiple;
      //this.loadDate(url,'participle',{type:"get_all"});
    },
    watch:{
      //将ShowModel作为监事属性
      ShowModel:{
        //immediate:true, //初始化时让handler调用一下
        deep:true,//深度监视
        handler(newValue,oldValue){
          this.showModelUpdate = true;
          this.getUrl("WordSegmentation",{});
        }
      },
      //将DataSet作为监事属性
      DataSet:{
        //immediate:true, //初始化时让handler调用一下
        deep:true,//深度监视
        handler(newValue,oldValue){
          //解决数据集和模型同时改变时重复调用gatUrl方法
          if(this.showModelUpdate === false){
            this.getUrl("WordSegmentation",{});
          }else{
            //解决数据集改变而模型不改变时不调用gatUrl方法
            this.showModelUpdate = false;
          }
        }
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