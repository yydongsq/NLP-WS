package org.jeecg.modules.demo.mynlp.common;/**
 * @author sq
 * @create 2022-04-06-20:37
 */

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jeecg.modules.demo.mynlp.entity.JiebaParticipleData;
import org.jeecg.modules.demo.mynlp.entity.JiebaParticipleModel;
import org.jeecg.modules.demo.mynlp.entity.LtpParticipleData;
import org.jeecg.modules.demo.mynlp.entity.LtpParticipleModel;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 20:37
 */
@Slf4j
public class LtpParticipleCommon {
    public String getLtpParticiple(String text,String type){
        //封装Ltp模型的Flask RestFull API接口地址
        String url="http://127.0.0.1:5000/ltp/sentence";
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("text", text);
        //执行api
        String ltp_data = doLtpApi(url,params);
        Gson gson = new Gson();
        //创建map对象，用于接收json字符串的转换
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        //将json字符串转换为map集合
        HashMap map_result = gson.fromJson(ltp_data, map.getClass());
        ArrayList<LtpParticipleModel> list = new ArrayList<LtpParticipleModel>();
        //获取分词数组和词性数组
        ArrayList<String> list_word = (ArrayList<String>)map_result.get("0");
        ArrayList<String> list_nature = (ArrayList<String>)map_result.get("1");
        //将map集合中的分词数据封装在对应的实体类中
        for (int i = 0; i < list_word.size(); i++) {
            LtpParticipleModel ltpParticipleModel = new LtpParticipleModel();
            ltpParticipleModel.setWord(list_word.get(i));
            ltpParticipleModel.setNature(list_nature.get(i));
            list.add(ltpParticipleModel);
        }
        LtpParticipleData ltpParticipleData = new LtpParticipleData();
        //设置响应json数据中键名key为code的值
        ltpParticipleData.setCode("0");
        //将list转换为数组
        LtpParticipleModel[] ltpParticipleModels = list.toArray(new LtpParticipleModel[list.size()]);
        //设置响应json数据中键名key为data的值
        ltpParticipleData.setData(ltpParticipleModels);
        //将实体类对象转换为json字符串
        String resultJsonString = gson.toJson(ltpParticipleData);
        return resultJsonString;
    }

    public String doLtpApi(String url, Map<String,Object> params) {
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
            Header contentType = response.getEntity().getContentType();//application/json
            Header contentEncoding = response.getEntity().getContentEncoding();//null
            resultString = EntityUtils.toString(response.getEntity(),"utf-8");
            return resultString;
        } catch (Exception e) {
            log.info("-----------------------调用Ltp模型失败---------------------");
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
}
