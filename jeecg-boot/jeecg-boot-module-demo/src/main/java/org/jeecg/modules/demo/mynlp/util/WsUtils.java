package org.jeecg.modules.demo.mynlp.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.mynlp.common.WSCommon;
import org.jeecg.modules.demo.mynlp.entity.TbNlpDataset;
import org.jeecg.modules.demo.mynlp.service.impl.TbNlpDatasetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月28日 8:40
 */
@Slf4j
@Component
public class WsUtils {

    @Autowired
    TbNlpDatasetServiceImpl tbNlpDatasetService;
    public static WsUtils wsUtils;

    /**
     * @PostConstruct注解在完成依赖注入之后执行
     * 该注解的方法在整个Bean初始化中的执行顺序：
     * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     */
    @PostConstruct
    public void init() {
        wsUtils = this;
    }

    /**
     * 调用指定模型进行分词通用方法
     * @param wsModel
     * @param dataSetId
     * @param tbNlpDataset
     * @return
     */
    public static Result<String> getWSResult(String wsModel, String dataSetId, TbNlpDataset tbNlpDataset) {
        String dataSetText = getRequestDataSet(dataSetId, tbNlpDataset);
        if(dataSetText == ""){
            return Result.error("未找到对应数据",null);
        }
        WSCommon ws = new WSCommon();
        String wsResult = "";
        switch (wsModel){
            case "hanLPWS": {
                wsResult = ws.getHanLPWSByMyAPI(dataSetText);
                log.info("HanLP调用成功");
            }
            break;
            case "jiebaWS": {
                wsResult = ws.getJiebaWS(dataSetText);
                log.info("Jieba调用成功");
            }
            break;
            case "ltpWS": {
                wsResult = ws.getLtpWS(dataSetText);
                log.info("Ltp调用成功");
            }
            break;
            case "thulacWS": {
                wsResult = ws.getThulacWS(dataSetText);
                log.info("Thulac调用成功");
            }
            break;
            default:{
                //
            }
        }
        Result<String> result = new Result<String>();
        result.setResult(wsResult);
        result.setSuccess(true);
        return result;
    }

    /**
     * 执行自定义封装的API
     * @param url
     * @param params
     * @return
     */
    public static String doWsApi(String url, Map<String,Object> params) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //添加header请求头
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            Gson gson = new Gson();
            //将Map集合转换成json字符串
            String personMapJsonString = gson.toJson(params);
            // 构建消息实体
            StringEntity entity = new StringEntity(personMapJsonString, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            //Header contentType = response.getEntity().getContentType();//application/json
            //Header contentEncoding = response.getEntity().getContentEncoding();//null
            resultString = EntityUtils.toString(response.getEntity(),"utf-8");
            return resultString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(response!=null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 判断请求分词的数据是数据集ID还是自输入文本内容
     * @param dataSetId
     * @param tbNlpDataset
     * @return
     */
    public static String getRequestDataSet(String dataSetId,TbNlpDataset tbNlpDataset){
        String dataSetText = "";
        if(dataSetId == null && tbNlpDataset.getDtText() != null){
            dataSetText = tbNlpDataset.getDtText();
        }else{
            /**
             * Spring中的Service不是你想new就能new的，因为通过new实例化的对象脱离了Spring容器的管理，获取不到注解的属性值，所以会是null
             * 就算调用service的Utils类中有@Component注解加入了Spring容器管理，也还是null
             *
             * static TbNlpDatasetServiceImpl tbNlpDatasetService;
             * Spring不支持依赖注入static静态变量，静态变量/类变量不是对象的属性而是一个类的属性，spring则是基于对象层面上的依赖注入。
             *
             * 解决方案：在工具类上加一个@Component注解，把它交给spring去管理
             * 使用@PostConstruct注解修饰的init方法
             * Utils工具类中使用service和mapper接口的方法例子，用"utils.xxx.方法" 就可以了
             */
            TbNlpDataset dataset = wsUtils.tbNlpDatasetService.getById(dataSetId);
            if(dataset == null) {
                return dataSetText;
            }else{
                dataSetText = dataset.getDtText();
            }
        }
        return dataSetText;
    }
}
