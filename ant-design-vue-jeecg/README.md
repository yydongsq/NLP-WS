Ant Design Jeecg Vue
====

当前最新版本： 3.1.0（发布日期：20220301）

## 前端技术架构

基于 [Ant Design of Vue](https://vuecomponent.github.io/ant-design-vue/docs/vue/introduce-cn/) 实现的 Ant Design Pro  Vue 版Jeecg-boot 的前端UI框架，采用前后端分离方案，提供强大代码生成器的低代码平台。前端页面代码和后端功能代码一键生成，不需要写任何代码！

- 基础框架：[ant-design-vue](https://github.com/vueComponent/ant-design-vue) - Ant Design Of Vue 实现
- JavaScript框架：[Vue 2.6.10](https://cn.vuejs.org/),[Vuex](https://vuex.vuejs.org/zh/),[Vue Router](https://router.vuejs.org/zh/)
- Webpack
- eslint
- @vue/cli 3.2.1
- [vue-cropper](https://github.com/xyxiao001/vue-cropper) - 头像裁剪组件
- [@antv/g2](https://antv.alipay.com/zh-cn/index.html) - Alipay AntV 数据可视化图表
- [Viser-vue](https://viserjs.github.io/docs.html#/viser/guide/installation)  - antv/g2 封装实现

## 开发环境

- node 10.0+ （推荐使用 v12.22.12）
- npm 5.6.0+ （推荐使用 6.14.16）
- yarn 1.21.1+ （推荐使用 1.22.22）

项目启动
----

- 安装依赖
```
yarn install
```

- 开发模式运行
```
yarn run serve
```

- 编译项目
```
yarn run build
```

- Lints and fixes files
```
yarn run lint
```

**下载整理好的node依赖：** 百度网盘链接 https://pan.baidu.com/s/1ZKS7kl_WS_8cwz1ZtJPToQ 提取码: yyds

<img src="assets/Readme/image-20250520161529267.png" alt="image-20250520161529267" style="zoom:33%;" />

**配置NLP菜单路由：** **参考官方文档** https://doc.jeecg.com/2043884

<img src="assets/Readme/image-20250520160411587.png" alt="image-20250520160411587" style="zoom: 33%;" />

<img src="assets/Readme/image-20250520170823303.png" alt="image-20250520170823303" style="zoom:33%;" />

其他说明
----

- 项目使用的 [vue-cli3](https://cli.vuejs.org/guide/), 请更新您的 cli

- 关闭 Eslint (不推荐) 移除 `package.json` 中 `eslintConfig` 整个节点代码

- 修改 Ant Design 配色，在文件 `vue.config.js` 中，其他 less 变量覆盖参考 [ant design](https://ant.design/docs/react/customize-theme-cn) 官方说明
```ecmascript 6
  css: {
    loaderOptions: {
      less: {
        modifyVars: {
          /* less 变量覆盖，用于自定义 ant design 主题 */

          'primary-color': '#F5222D',
          'link-color': '#F5222D',
          'border-radius-base': '4px',
        },
        javascriptEnabled: true,
      }
    }
  }
```

附属文档
----
- [Ant Design Vue](https://vuecomponent.github.io/ant-design-vue/docs/vue/introduce-cn)

- [报表 viser-vue](https://viserjs.github.io/demo.html#/viser/bar/basic-bar)

- [Vue](https://cn.vuejs.org/v2/guide)

- [路由/菜单说明](https://github.com/zhangdaiscott/jeecg-boot/tree/master/ant-design-vue-jeecg/src/router/Readme.md)

- [ANTD 默认配置项](https://github.com/zhangdaiscott/jeecg-boot/tree/master/ant-design-vue-jeecg/src/defaultSettings.js)

- 其他待补充...

