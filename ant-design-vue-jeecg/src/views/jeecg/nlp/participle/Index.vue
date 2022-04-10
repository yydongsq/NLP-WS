<template>
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="数据集内容">
              <a-input placeholder="请输入数据集内容" v-model="TypeName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="模型名称">
              <a-select placeholder="请选择模型名称" v-model="ShowModel">
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

<!--      <IndexHanLP v-show="ShowModel == 'HanLP'"></IndexHanLP>
      <IndexJieba v-show="ShowModel == 'Jieba'"></IndexJieba>
      <IndexLtp v-show="ShowModel == 'Ltp'"></IndexLtp>
      <IndexThulac v-show="ShowModel == 'Thulac'"></IndexThulac>-->
      <CommonParticiple v-bind:ShowModel = "ShowModel"></CommonParticiple>
    </div>
    <!-- 查询区域-END -->


</template>

<script>

  import CommonParticiple from './CommonParticiple.vue'
    export default {
        name: "Index",
        components: {
          CommonParticiple
        },
      data(){
        return{
          TypeName: '',
          ModelName: undefined,
          ShowModel:''
          }
      },
      methods: {
        searchQuery(){
          let modelName = this.ModelName;
          this.ShowModel = modelName;
        },
        searchReset(){

        }
      }
    }
</script>

<style scoped>

</style>