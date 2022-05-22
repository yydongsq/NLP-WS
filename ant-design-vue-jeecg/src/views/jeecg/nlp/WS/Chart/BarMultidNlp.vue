<template>
  <div :style="{ padding: '0 0 32px 32px' }">
    <h3 style="marginBottom: '20px';text-align:center">{{ title }}</h3>
    <v-chart :data="data" :height="height" :force-fit="true" :scale="scale" :onClick="handleClick">
      <v-tooltip/>
      <v-axis/>
      <v-legend/>
      <v-bar position="x*y" color="type" :adjust="adjust"/>
    </v-chart>
  </div>
</template>

<script>
  import { DataSet } from '@antv/data-set'
  import { ChartEventMixins } from '@/components/chart/mixins/ChartMixins'

  export default {
    name: 'BarMultid',
    mixins: [ChartEventMixins],
    props: {
      title: {
        type: String,
        default: ''
      },
      dataSource: {
        type: Array,
        default: () => [
          { type: 'Thulac', '我爱': 18.9,  '北京': 39.3, '天安门': 81.4, '祝福': 47, '伟大': 20.3, '祖国': 24, '母亲': 35.6 },
          { type: 'HanLP', '我': 18.9, '爱北京': 39.3, '天安门': 81.4, '祝福': 47, '伟大': 20.3, '祖国': 24, '母亲': 35.6 },
          { type: 'Jieba', '我': 18.9, '爱': 28.8, '北京天安门': 81.4, '祝福': 47, '伟大': 20.3, '祖国': 24, '母亲': 35.6 },
          { type: 'Ltp', '我': 18.9, '爱': 28.8, '北京': 39.3, '天安门': 81.4, '祝福伟大': 47,  '祖国': 24, '母亲': 35.6 }
        ]
      },
      fields: {
        type: Array,
        default: () => ['我', '爱', '我爱','北京','爱北京', '天安门','北京天安门', '祝福', '伟大','祝福伟大', '祖国', '母亲',]
      },
      // 别名，需要的格式：[{field:'name',alias:'姓名'}, {field:'sex',alias:'性别'}]
      aliases: {
        type: Array,
        default: () => []
      },
      height: {
        type: Number,
        default: 254
      }
    },
    data() {
      return {
        adjust: [{
          type: 'dodge',
          marginRatio: 1 / 32
        }]
      }
    },
    computed: {
      data() {
        const dv = new DataSet.View().source(this.dataSource)
        dv.transform({
          type: 'fold',
          fields: this.fields,
          key: 'x',
          value: 'y'
        })

        // bar 使用不了 - 和 / 所以替换下
        let rows = dv.rows.map(row => {
          if (typeof row.x === 'string') {
            row.x = row.x.replace(/[-/]/g, '_')
          }
          return row
        })
        // 替换别名
        rows.forEach(row => {
          for (let item of this.aliases) {
            if (item.field === row.type) {
              row.type = item.alias
              break
            }
          }
        })
        return rows
      },
      scale() {
        return [
          {
            type: 'cat',
            dataKey: 'x'
          }
        ]
      }
    }
  }
</script>

<style scoped>

</style>