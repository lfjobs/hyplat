package hy.ea.util;

public class PhoneUtils {
    /**
     * 手机号脱敏：中间4位替换为****
     * @param phone 原始手机号
     * @return 脱敏后的手机号
     */
    public static String hidePhone(String phone) {
        if (phone == null || phone.trim().length() == 0) {
            return "";
        }
        // 校验11位手机号格式
        if (phone.matches("^1[3-9]\\d{9}$")) {
            return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        // 适配固定电话（如010-12345678）
        else if (phone.matches("^\\d{3,4}-\\d{7,8}$")) {
            return phone.replaceAll("(\\d{3,4}-)\\d{4}(\\d{0,4})", "$1****$2");
        }
        return phone; // 非手机号返回原值
    }
}
