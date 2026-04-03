package com.wattOpen.businessTO;

/**
 *
 * 计费模板
 *
 * @author author wangdaigang
 * @create 2024 - 06 - 01 10:49
 */
public class PriceTemplateTO {

    /**
     * sharp(尖价格),
     */
    private String sharp;
    /**
     * peak(峰价格)
     */
    private String peak;
    /**
     * flat(平价格)
     */
    private String flat;
    /**
     * valley(谷价格)
     */
    private String valley;
    /**
     * 单位为元
     */
    private String price;
}
