package cn.com.lz.entrepreneur.restaurant.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description：String工具
 * author：CodingHornet
 * date：2015/9/1 0001 16:23
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        return str == null || (str.trim()).length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    private static final String PATTERN_PORT = "(:(\\d|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5]))?$";

    private static final String PATTERN_IP = "^(([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.){3}([0-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])" + PATTERN_PORT;


    private static final String PATTERN_DOMAIN = "^([a-zA-Z0-9]+(-[a-zA-Z0-9]+)*\\.)+[a-zA-Z]{2,}" + PATTERN_PORT;

    /**
     * 生成文件名
     *
     * @return 文件名
     */
    private static String createFileName() {
        java.util.Date dt = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        return fmt.format(dt);
    }

    /**
     * 生成带扩展名的文件名
     *
     * @param extension 文件扩展名
     * @return 带扩展名的文件名
     */
    public synchronized static String createFileNameWithExtension(String extension) {
        return createFileName() + "." + extension;
    }

    /**
     * 解析URL取得数据
     **/
    public static String analyUrl(String urlStr, String key) {
        //创建数组
        String[] urlArray = urlStr.split("&");
        for (int i = 0; i < urlArray.length; i++) {
            String urlArraySingle = urlArray[i];
            String[] tempKv = urlArraySingle.split("=");
            if (tempKv[0].equals(key)) {
                if (tempKv.length == 2) {
                    return tempKv[1];
                } else {
                    return "";
                }
            }
        }
        return "";

    }

    /**
     * 获取文件名
     */
    public static String getName(String name) {
        if (name == null)
            return "";
        int index = name.indexOf("(");
        if (index > 0) {
            return name.substring(0, index);
        } else
            return name;
    }

    /**
     * Description: 判断服务器地址是否有效
     * author: CodingHornet
     * Date: 2016/6/17 14:51
     */
    public static boolean checkServerAddress(String serverAddress) {
        return validateIP(serverAddress) || validateDomain(serverAddress);
    }

    public static boolean validateIP(final String ip) {
        Pattern pattern = Pattern.compile(PATTERN_IP);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public static boolean validateDomain(final String domain) {
        Pattern pattern = Pattern.compile(PATTERN_DOMAIN);
        Matcher matcher = pattern.matcher(domain);
        return matcher.matches();
    }

    /**
     * Description: 截取字符串
     * author: lhd
     * Date: 2017-03-30 10:57:20
     **/
    public static String splitStr(String str, String splitSymbol) {
        String[] strArray = str.split(splitSymbol);
        int arrLength = strArray.length;
        if (arrLength < 2) {
            return "";
        }
        return strArray[1];
    }


    /**
     * 更具原图url获取缩略图url
     *
     * @param path
     * @return
     */
    public static String getThumbPath(String path) {
        if (path != null && path.contains(".")) {
            StringBuilder sb = new StringBuilder(path.substring(0, path.lastIndexOf(".")));
            sb.append("_t.").append(path.substring(path.lastIndexOf(".") + 1));
            return sb.toString();
        } else {
            return "";
        }
    }


    /**
     * Description:生成UUID
     *
     * @return UUID
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        // 去掉"-"符号
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
        return temp;
    }

    /**
     * Description:百分数转float
     *
     * @return flost
     */
    public static float percentToFloat(String percent) {
        if (percent.contains("%")) {
            percent = percent.replaceAll("%", "");
            Float f = Float.valueOf(percent);
            return f;
        }
        return 0;
    }

    /**
     * Description: 匹配搜索关键字并高亮显示
     * author: CodingHornet
     * Date: 2017/12/22 10:51
     */
    public static SpannableString matcherSearchText(int color, String text, String keyword) {
        String string = text.toLowerCase();
        String key = keyword.toLowerCase();

        Pattern pattern = Pattern.compile(key);
        Matcher matcher = pattern.matcher(string);

        SpannableString ss = new SpannableString(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }
}
