package org.jeecg.modules.demo.mynlp.common;/**
 * @author sq
 * @create 2022-04-06-16:15
 */

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.demo.mynlp.entity.*;
import org.jeecg.modules.demo.mynlp.util.WsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 16:15
 */
@Slf4j
public class JiebaWSCommon {
    /**
     * 调用API接口进行分词
     * @param text
     * @return
     */
    public String getJiebaWS(String text){
        //封装Thulac模型的Flask RestFull API接口地址
        String url="http://127.0.0.1:5000/jieba/sentence";
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("text", text);
        //执行api
        String jieba_data = WsUtils.doWsApi(url,params);
        Gson gson = new Gson();
        //创建map对象，用于接收json字符串的转换
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        //将json字符串转换为map集合
        HashMap map_result = gson.fromJson(jieba_data, map.getClass());
        ArrayList<JiebaWSModel> list = new ArrayList<>();
        //将map集合中的分词数据封装在对应的实体类中
        for (int i = 0; i < map_result.size(); i++) {
            String key = String.valueOf(i);
            ArrayList<String> list_data = (ArrayList<String>)map_result.get(key);
            JiebaWSModel jiebaWSModel = new JiebaWSModel();
            jiebaWSModel.setWord(list_data.get(0));
            jiebaWSModel.setNature(list_data.get(1));
            list.add(jiebaWSModel);
        }
        JiebaWSData jiebaParticipleData = new JiebaWSData();
        //设置响应json数据中键名key为code的值
        jiebaParticipleData.setCode("0");
        //将list转换为数组
        JiebaWSModel[] jiebaWSModels = list.toArray(new JiebaWSModel[list.size()]);
        //设置响应json数据中键名key为data的值
        jiebaParticipleData.setData(jiebaWSModels);
        //将实体类对象转换为json字符串
        String resultJsonString = gson.toJson(jiebaParticipleData);
        return resultJsonString;
    }
}
