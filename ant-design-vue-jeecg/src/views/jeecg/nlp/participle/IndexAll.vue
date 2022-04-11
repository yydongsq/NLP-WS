<template>
  <a-card :bordered="false">
    <a-tabs defaultActiveKey="1" @change="callback">
      <a-tab-pane tab="柱状图" key="1">
        <a-row>
          <a-col :span="10">
            <a-radio-group :value="barType" @change="statisticst">
              <a-radio-button value="participle">{{ShowModel}}分词结果</a-radio-button>
              <a-radio-button value="participle_n">关键字统计</a-radio-button>
            </a-radio-group>
          </a-col>
          <bar class="statistic" title="档案统计" :dataSource="countSource" :height="400"/>
        </a-row>
      </a-tab-pane>

      <a-tab-pane tab="饼状图" key="2">
        <a-row :gutter="24">
          <a-col :span="8">
            <a-radio-group :value="pieType" @change="statisticst">
              <a-radio-button value="participle">{{ShowModel}}分词结果</a-radio-button>
              <a-radio-button value="participle_n">关键字统计</a-radio-button>
            </a-radio-group>
          </a-col>
          <pie class="statistic" title="档案统计" :dataSource="countSource" :height="450"/>
        </a-row>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script>
  import Bar from '@/components/chart/Bar'
  import Pie from '@/components/chart/Pie'
  import ACol from 'ant-design-vue/es/grid/Col'
  import { getAction } from '@/api/manage'

  export default {
    name: 'IndexAll',
    //接收父组件传来的属性
    //props:['ShowModel'],
    props: {
      ShowModel: Object
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
        barType: 'participle',
        barDate: ['month', 'month'],
        barValue: [],
        // 饼状图
        pieType: 'participle',
        pieDate: ['month', 'month'],
        pieValue: [],
        // 统计图类型
        tabStatus:"bar",
        url: {
          getYearCountInfo: "/mock/api/report/getYearCountInfo",
          getMonthCountInfo:"/mock/api/report/getMonthCountInfo",
          getCntrNoCountInfo:"/mock/api/report/getCntrNoCountInfo",
          getCabinetCountInfo:"/mock/api/report/getCabinetCountInfo",
          getHanLPParticiple:"/jeecg-demo/mynlp/hanlp/hanLPParticiple",
          getJiebaParticiple:"/jeecg-demo/mynlp/jieba/jiebaParticiple",
          getLtpParticiple:"/jeecg-demo/mynlp/ltp/ltpParticiple",
          getThulacParticiple:"/jeecg-demo/mynlp/thulac/thulacParticiple",
        },
        initShow:"",
      }
    },
    created() {
      //let url = this.url.getParticiple;
      //this.loadDate(url,'participle',{type:"get_all"});
    },
    methods: {
      loadDate(url,type,param) {
        getAction(url,param,'get').then((res) => {
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
            if(type === 'participle'){
              let json_Data = JSON.parse(res.result);
              this.getParticipleCountSource(json_Data.data);
            }
            if(type === 'participle_n'){
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
            this.countSource.push({
              x: data[i].word,
              y: 650
            })
          }else{
            this.countSource.push({
              item: data[i].word,
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
        let url = "";
        //type为分词类型
        if(type === 'participle' && showModel === "HanLP"){
          param = {type:"get_all"};
          url = this.url.getHanLPParticiple;
        }
        if(type === 'participle' && showModel === "Thulac"){
          param = {type:"get_all"};
          url = this.url.getThulacParticiple;
        }
        if(type === 'participle' && showModel === "Jieba"){
          param = {type:"get_all"};
          url = this.url.getJiebaParticiple;
        }
        if(type === 'participle' && showModel === "LTP"){
          param = {type:"get_all"};
          url = this.url.getLtpParticiple;
        }
        if(type === 'participle_n'){
          param = {type:"get_n"};
          url = this.url.getParticiple;
        }
        this.loadDate(url,type,param);
      },
    },
    watch:{
      //正常写法
      ShowModel:{
        immediate:true, //初始化时让handler调用一下
        deep:true,//深度监视
        handler(newValue,oldValue){
          if(newValue === "reload"){
            this.getUrl("",{});
          }else{
            this.getUrl("participle",{});
          }
          console.log('isHot被修改了',newValue,oldValue)
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