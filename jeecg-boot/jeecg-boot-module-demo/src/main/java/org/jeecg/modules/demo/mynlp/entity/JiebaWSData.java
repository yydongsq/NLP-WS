package org.jeecg.modules.demo.mynlp.entity;/**
 * @author sq
 * @create 2022-04-06-16:17
 */

import lombok.Data;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 16:17
 */
@Data
public class JiebaWSData {
    private String code;
    private JiebaWSModel[] data;
}
