<template>
  <a-table :dataSource="dataSource" :columns="columns" />
</template>

<script>
  import { getAction } from '@/api/manage' //调用main.js中的通用请求方法
  import {message} from "ant-design-vue";
    export default {
      tags: "PartOfSpeech",
      //接收父组件传来的属性
      props: {
        ModelPosId: ""
      },
      data() {
        return {
          dataSource: [],
          url:{
            queryTbNlpPosDetailListByMainId:"/jeecg-demo/mynlp/tbNlpPos/queryTbNlpPosDetailByMainId", //通过自然语言词性标注集明细主表ID查询
          },
          columns: [
            {
              title: '标签',
              dataIndex: 'tags',
              key: 'tags',
            },
            {
              title: '含义',
              dataIndex: 'meaning',
              key: 'meaning',
            },
            {
              title: '描述',
              dataIndex: 'description',
              key: 'description',
            },
          ],
        };
      },
      watch:{
        //将ModelPosId作为监视属性，注意如果子组件没有初次加载显示过就不会进行渲染，即就不会触发监视属性
        //只有子组件在页面被初次渲染显示后，watch才开始工作，查看是否有监视属性的值被改变，如果有这时才会触发监视方法！
        ModelPosId:{
          immediate:true, //初始化时让handler调用一下，即第一次加载显示页面时。
          deep:true,//深度监视
          handler(newValue,oldValue){
            let requestUrl = this.url.queryTbNlpPosDetailListByMainId;
            let param = {
              "id" : newValue
            }
            getAction(requestUrl,param).then((res) => {
              console.info("res.success = " + res.success);
              if (res.success) {
                let posData = res.result;
                let dataPosSource = [];
                for (let i = 0; i < posData.length; i++) {
                  let jsonPosObject = {};
                  jsonPosObject.key = i + 1;
                  jsonPosObject.tags = posData[i].posTags;
                  jsonPosObject.meaning = posData[i].posMeaning;
                  jsonPosObject.description = posData[i].posDescription;
                  dataPosSource[i] = jsonPosObject;
                }
                this.dataSource = dataPosSource;
              }else{
                message.warning('请求超时，请重试！',2)
              }
            })
          }
        }
      }
    }
</script>

<style scoped>

</style>