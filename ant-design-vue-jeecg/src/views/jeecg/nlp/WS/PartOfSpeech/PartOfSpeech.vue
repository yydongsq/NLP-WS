<template>
  <a-table :dataSource="dataSource" :columns="columns" />
</template>

<script>
    export default {
      tags: "PartOfSpeech",
      data() {
        return {
          dataSource: [
            {
              key: '1',
              tags: 'a',
              description: "adjective",
              meaning: '形容词',
            },
            {
              key: '2',
              tags: 'b',
              description: "other noun-modifier",
              meaning: '其他名词修饰词',
            },
            {
              key: '3',
              tags: 'c',
              description: "conjunction",
              meaning: '连词',
            },
            {
              key: '4',
              tags: 'd',
              description: "adverb",
              meaning: '副词',
            },
            {
              key: '5',
              tags: 'e',
              description: "exclamation",
              meaning: '叹词',
            },
            {
              key: '6',
              tags: 'g',
              description: "morpheme",
              meaning: '语素',
            },
            {
              key: '7',
              tags: 'h',
              description: "prefix",
              meaning: '前缀',
            },
            {
              key: '8',
              tags: 'i',
              description: "idiom",
              meaning: '成语、习语',
            },
            {
              key: '9',
              tags: 'j',
              description: "abbreviation",
              meaning: '缩写',
            },
            {
              key: '10',
              tags: 'k',
              description: "suffix",
              meaning: '后缀',
            },
            {
              key: '11',
              tags: 'm',
              description: "number",
              meaning: '数字',
            },
            {
              key: '12',
              tags: 'n',
              description: "general noun",
              meaning: '一般名词',
            },
            {
              key: '13',
              tags: 'nd',
              description: "direction noun",
              meaning: '方位名词',
            },
            {
              key: '14',
              tags: 'nh',
              description: "person name",
              meaning: '人名',
            },
            {
              key: '15',
              tags: 'ni',
              description: "organization name",
              meaning: '机构名',
            },
            {
              key: '16',
              tags: 'nl',
              description: "lacation name",
              meaning: '位置名词',
            },
            {
              key: '17',
              tags: 'ns',
              description: "geographical name",
              meaning: '地点名词',
            },
            {
              key: '18',
              tags: 'nt',
              description: "temporal noun",
              meaning: '时间名词',
            },
            {
              key: '19',
              tags: 'nz',
              description: "other proper noun",
              meaning: '其他专有名词',
            },
            {
              key: '20',
              tags: 'o',
              description: "onomatopoeia",
              meaning: '拟声词',
            },
            {
              key: '21',
              tags: 'p',
              description: "preposition",
              meaning: '介词、前置词',
            },
            {
              key: '22',
              tags: 'q',
              description: "quantity",
              meaning: '量词',
            },
            {
              key: '23',
              tags: 'r',
              description: "pronoun",
              meaning: '代词',
            },
            {
              key: '24',
              tags: 'u',
              description: "auxiliary",
              meaning: '助词',
            },
            {
              key: '25',
              tags: 'v',
              description: "verb",
              meaning: '动词',
            },
            {
              key: '26',
              tags: 'wp',
              description: "punctuation",
              meaning: '标点',
            },
            {
              key: '27',
              tags: 'ws',
              description: "foreign words",
              meaning: '外文词',
            },
            {
              key: '28',
              tags: 'x',
              description: "non-lexeme",
              meaning: '非词根',
            },
          ],

          columns: [
            {
              title: '标签',
              dataIndex: 'tags',
              key: 'tags',
            },
            {
              title: '描述',
              dataIndex: 'description',
              key: 'description',
            },
            {
              title: '含义',
              dataIndex: 'meaning',
              key: 'meaning',
            },
          ],
        };
      },
    }
</script>

<style scoped>

</style>