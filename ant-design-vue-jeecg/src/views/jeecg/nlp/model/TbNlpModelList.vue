<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="模型类型名称">
              <a-input placeholder="请输入模型类型名称" v-model="queryParam.modelTypeName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="模型名称">
              <a-input placeholder="请输入模型名称" v-model="queryParam.modelName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="模型创建时间">
                <j-date :show-time="true" date-format="YYYY-MM-DD" placeholder="请选择模型创建时间" v-model="queryParam.modelCreatTime"></j-date>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('自然语言分词模型')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item v-if="record.modelStatus === '已启用'">
                <a-popconfirm title="确认禁用吗?" @confirm="() => handleStatus(record,'已禁用')">
                  <a>禁用</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item v-else>
                <a-popconfirm title="确认启用吗?" @confirm="() => handleStatus(record,'已启用')">
                  <a>启用</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <tb-nlp-model-modal ref="modalForm" @ok="modalFormOk"></tb-nlp-model-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import TbNlpModelModal from './modules/TbNlpModelModal'
  import { httpAction, getAction } from '@/api/manage'

  /*
    * 在你已经写好了构造器后，需要增加方法或者临时的活动时使用的方法，这时用混入会减少源代码的污染。
    * 很多地方都会用到的公用方法，用混入的方法可以减少代码量，实现代码重用。
    * 在和组件的数据或方法发生冲突时以组件数据优先（组件的data中变量会覆盖混入对象的data中变量）
    * 如果在引用mixins的同时，在组件中重复定义相同的方法，则mixins中的方法会被覆盖。
    * mixins是在引入组件之后，则是将组件内部的内容如data等方法、method等属性与父组件相应内容进行合并。相当于在引入后，父组件的各种属性方法都被扩充了。
    * 实例对象即组件或者Vue实例对象中，仍然可以定义相同函数名的方法进行覆盖，有点像子类和父类的感觉。
    * 可以定义共用的变量，在每个组件中使用，引入组件中之后，各个变量是相互独立的，值的修改在组件中不会相互影响。
  * */
  export default {
    name: 'TbNlpModelList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      TbNlpModelModal
    },
    data () {
      return {
        description: '自然语言分词模型管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'模型类型编码',
            align:"center",
            dataIndex: 'modelTypeCode'
          },
          {
            title:'模型类型名称',
            align:"center",
            dataIndex: 'modelTypeName'
          },
          {
            title:'模型编号',
            align:"center",
            dataIndex: 'modelNo'
          },
          {
            title:'模型名称',
            align:"center",
            dataIndex: 'modelName'
          },
          {
            title:'模型来源',
            align:"center",
            dataIndex: 'modelSource'
          },
          {
            title:'模型创建人',
            align:"center",
            dataIndex: 'modelCreatBy'
          },
          {
            title:'模型创建时间',
            align:"center",
            dataIndex: 'modelCreatTime'
          },
          {
            title:'模型更新人',
            align:"center",
            dataIndex: 'modelUpdateBy'
          },
          {
            title:'模型更新时间',
            align:"center",
            dataIndex: 'modelUpdateTime'
          },
          {
            title:'模型状态',
            align:"center",
            dataIndex: 'modelStatus'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'memo'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/jeecg-demo/mynlp/tbNlpModel/list",
          delete: "/jeecg-demo/mynlp/tbNlpModel/delete",
          deleteBatch: "/jeecg-demo/mynlp/tbNlpModel/deleteBatch",
          exportXlsUrl: "/jeecg-demo/mynlp/tbNlpModel/exportXls",
          importExcelUrl: "jeecg-demo/mynlp/tbNlpModel/importExcel",
          modelDisable: "/jeecg-demo/mynlp/tbNlpModel/edit",
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'modelTypeCode',text:'模型类型编码',dictCode:''})
        fieldList.push({type:'string',value:'modelTypeName',text:'模型类型名称',dictCode:''})
        fieldList.push({type:'string',value:'modelNo',text:'模型编号',dictCode:''})
        fieldList.push({type:'string',value:'modelName',text:'模型名称',dictCode:''})
        fieldList.push({type:'string',value:'modelSource',text:'模型来源',dictCode:''})
        fieldList.push({type:'string',value:'modelCreatBy',text:'模型创建人',dictCode:''})
        fieldList.push({type:'datetime',value:'modelCreatTime',text:'模型创建时间'})
        fieldList.push({type:'string',value:'modelUpdateBy',text:'模型更新人',dictCode:''})
        fieldList.push({type:'datetime',value:'modelUpdateTime',text:'模型更新时间'})
        fieldList.push({type:'string',value:'modelStatus',text:'模型状态',dictCode:''})
        fieldList.push({type:'string',value:'memo',text:'备注',dictCode:''})
        this.superFieldList = fieldList
      },
      handleStatus(record,status){
        record.modelStatus = status;
        let httpUrl = this.url.modelDisable;
        httpAction(httpUrl,record,"put").then((res)=>{
          if(res.success){
            this.$message.success(res.message);
            this.modalFormOk('ok');
          }else{
            this.$message.warning(res.message);
          }
        }).finally(() => {
          this.confirmLoading = false;
        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>