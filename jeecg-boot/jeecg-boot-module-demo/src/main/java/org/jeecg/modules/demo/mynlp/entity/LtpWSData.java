package org.jeecg.modules.demo.mynlp.entity;/**
 * @author sq
 * @create 2022-04-06-20:35
 */

import lombok.Data;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 20:35
 */
@Data
public class LtpWSData {
    private String code;
    private LtpWSModel[] data;
}
