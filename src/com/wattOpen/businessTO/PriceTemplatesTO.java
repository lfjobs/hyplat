package com.wattOpen.businessTO;

/**
 *
 * 计费模板
 *
 * @author author wangdaigang
 * @create 2024 - 06 - 01 10:49
 */
public class PriceTemplatesTO {

    /**
     * type:
     * 电(SCENE_ELEC) ,水(SCENE_WATER) ,空调(SCENE_INTELLIGENT_THERMOSTAT)；
     */
    private String type;
    private String sn;
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
    /**
     * 起始读数,读数>=0；如果不传，则系统将设定为设备最近上报的读数；
     */
    private String initReading;
    /**
     * 价格类型:
     * ①.不传则修改平均价格
     * ②.priceType=1//表示修改尖、峰、平、谷电价
     */
    private String priceType;
}
