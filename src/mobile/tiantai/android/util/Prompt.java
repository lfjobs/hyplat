package mobile.tiantai.android.util;

public class Prompt {
    public static String CompanyPrompt="请帮我在爱企查查询%s的详细信息，按json格式返回。模板格式{" +
            "  result:{" +
            "    market:行业市场," +
            "    brand:品牌信息," +
            "    companyPhone:企业电话," +
            "    legal:法人," +
            "    legalPhone:法人电话" +
            "  }" +
            "  " +
            "}" +
            "电话号码不要用星号，要完整的。行业信息根据《国民经济行业分类》返回。品牌信息缩减到8个字以内";
}
