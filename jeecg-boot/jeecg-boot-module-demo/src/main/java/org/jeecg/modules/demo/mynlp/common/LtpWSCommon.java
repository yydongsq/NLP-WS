package org.jeecg.modules.demo.mynlp.common;/**
 * @author sq
 * @create 2022-04-06-20:37
 */

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.demo.mynlp.entity.LtpWSData;
import org.jeecg.modules.demo.mynlp.entity.LtpWSModel;
import org.jeecg.modules.demo.mynlp.util.WsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 20:37
 */
@Slf4j
public class LtpWSCommon {
    /**
     * 调用API接口进行分词
     * @param text
     * @return
     */
    public String getLtpWS(String text){
        //封装Ltp模型的Flask RestFull API接口地址
        String url="http://127.0.0.1:5000/ltp/sentence";
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("text", text);
        //执行api
        String ltp_data = WsUtils.doWsApi(url,params);
        Gson gson = new Gson();
        //创建map对象，用于接收json字符串的转换
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        //将json字符串转换为map集合
        HashMap map_result = gson.fromJson(ltp_data, map.getClass());
        ArrayList<LtpWSModel> list = new ArrayList<LtpWSModel>();
        //获取分词数组和词性数组
        ArrayList<String> list_word = (ArrayList<String>)map_result.get("0");
        ArrayList<String> list_nature = (ArrayList<String>)map_result.get("1");
        //将map集合中的分词数据封装在对应的实体类中
        for (int i = 0; i < list_word.size(); i++) {
            LtpWSModel ltpWSModel = new LtpWSModel();
            ltpWSModel.setWord(list_word.get(i));
            ltpWSModel.setNature(list_nature.get(i));
            list.add(ltpWSModel);
        }
        LtpWSData ltpWSData = new LtpWSData();
        //设置响应json数据中键名key为code的值
        ltpWSData.setCode("0");
        //将list转换为数组
        LtpWSModel[] ltpWSModels = list.toArray(new LtpWSModel[list.size()]);
        //设置响应json数据中键名key为data的值
        ltpWSData.setData(ltpWSModels);
        //将实体类对象转换为json字符串
        String resultJsonString = gson.toJson(ltpWSData);
        return resultJsonString;
    }
}
