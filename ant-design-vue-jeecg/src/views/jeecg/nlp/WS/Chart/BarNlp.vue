<template>
  <div>
    <h3 style="text-align:center">{{ title }}</h3>
    <v-chart :forceFit="true" :height="height" :data="dataSource" :scale="scale" :padding="padding">
      <v-tooltip/>
      <v-axis/>
      <v-bar position="x*y"/>
    </v-chart>
    <div :style="{ marginBottom: '20px',padding: '0 20px 0 12px'}">{{DataSetResult}}</div>
  </div>
</template>

<script>
  import {triggerWindowResizeEvent} from '@/utils/util'

  export default {
    name: 'Bar',
    props: {
      DataSetResult: {
        type: String,
        default: ''
      },
      dataSource: {
        type: Array,
        required: true
      },
      yaxisText: {
        type: String,
        default: 'y'
      },
      title: {
        type: String,
        default: ''
      },
      height: {
        type: Number,
        default: 254
      }
    },
    data() {
      return {
        padding: ['auto', 'auto', '40', '50'],
      }
    },
    methods: {

    },
    computed: {
      scale() {
        return [{
          dataKey: 'y',
          alias: this.yaxisText
        }]
      }
    },
    mounted() {
      triggerWindowResizeEvent()
    }
  }
</script>
