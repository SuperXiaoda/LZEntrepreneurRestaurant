package cn.com.lz.entrepreneur.restaurant.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import cn.com.lz.entrepreneur.restaurant.R;


/**
 * description：文件管理工具
 * author：CodingHornet
 * date：2015/8/20 0020 16:06
 */
public class FileUtil {

    /**
     * Description: 获取外部储存卡根目录
     * author: CodingHornet
     * Date: 2017/10/28 17:29
     */
    public static String getDiskDir(String pathOrName) {
        return Environment.getExternalStorageDirectory() + File.separator + pathOrName;
    }

    /**
     * Description: 获取外部存储卡缓存目录
     * author: CodingHornet
     * Date: 2017/10/28 17:32
     *
     * @param context 上下文
     * @return 缓存目录地址
     */
    public static String getDiskCacheDir(Context context) {
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) && context.getExternalCacheDir() != null) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    /**
     * Description: 获取外部存储卡缓存目录
     * author: CodingHornet
     * Date: 2017/10/28 17:33
     *
     * @param context 上下文
     * @param newPath 目录活文件名
     * @return 缓存目录下文件或文件夹文件对象
     */
    public static String getDiskCacheDir(Context context, String newPath) {
        String cachePath = getDiskCacheDir(context);
        File path = new File(cachePath);
        if (!path.exists()) {
            path.exists();
        }
        return cachePath + File.separator + newPath;
    }

    /**
     * Description:  获取或创建缓存目录文件
     * author: CodingHornet
     * Date: 2017/11/20 17:15
     *
     * @param context    上下文
     * @param createPath 新建目录名
     * @return 缓存目录下文件或文件夹文件对象
     */
    public static String getOrCreateDiskCacheDir(Context context, String createPath) {
        String newPath = getDiskCacheDir(context, createPath);
        File path = new File(newPath);
        if (!path.exists()) {
            path.exists();
        }
        return newPath;
    }

    /**
     * Description: 获取外部存储卡数据目录
     * author: CodingHornet
     * Date: 2017/10/28 17:33
     *
     * @param context 上下文
     * @param newPath 目录活文件名，根目录传null
     * @return 缓存目录下文件或文件夹文件对象
     */
    public String getDiskFileDir(Context context, String newPath) {
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) && context.getExternalCacheDir() != null) {
            return context.getExternalFilesDir(newPath).getPath();
        } else {
            return context.getFilesDir().getPath();
        }
    }

    /**
     * 检验SDcard状态
     *
     * @return boolean
     */
    public static boolean checkSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * Description: 检查外部储存卡是否可用
     * author: CodingHornet
     * Date: 2017/10/28 17:43
     */
    public static boolean checkSDCardWithFailedToast(Context context) {
        if (checkSDCard())
            return true;
        Toast.makeText(context, R.string.invalid_sdcard, Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 根据文件路径删除文件
     */

    public static boolean deleteFile(String path) {
        File file = new File(path);
        //判断文件是否存在
        if (file.exists()) {
            //判断是不是文件
            if (file.isFile()) {
                file.delete();
                return true;
            }
        }
        return false;
    }

    /**
     * Description: 清空目录或删除文件
     * author: CodingHornet
     * Date: 2016/7/7 16:55
     */
    public static void deleteDirectory(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (int i = 0; i < childFiles.length; i++) {
                deleteDirectory(childFiles[i]);
            }
            file.delete();
        }
    }

    /**
     * 复制文件
     *
     * @param src      原文件
     * @param dstPath  保存路径
     * @param fileName 文件名
     * @return
     */
    public static boolean copy(File src, String dstPath, String fileName) {
        try {
            if (!src.exists()) {
                return false;
            }

            File path = new File(dstPath);
            File saveFile = new File(dstPath + File.separator + fileName);
            if (!path.exists()) {
                path.mkdirs();
            }

            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(saveFile);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据URL路径获取文件名
     *
     * @param path 路径
     * @return 文件名
     */
    public static String getFileName(String path) {
        if ((path != null) && (path.length() > 0)) {
            int dot = path.lastIndexOf('/');
            if ((dot > -1) && (dot < (path.length() - 1))) {
                return path.substring(dot + 1);
            }
        }
        return path;
    }

    // TBS支持的文件列表
    private final static ArrayList<String> TBS_SUPPORT_LIST = new ArrayList<String>() {{
        add("doc");
        add("docx");
        add("xls");
        add("xlsx");
        add("ppt");
        add("pptx");
        add("txt");
        add("pdf");
        add("epub");
    }};

    /**
     * Description: 是否是TBS支持的文件
     * author: CodingHornet
     * Date: 2017/12/11 15:15
     */
    public static boolean isTbsSupport(String path) {
        return TBS_SUPPORT_LIST.contains(getExtensionName(path));
    }

    /**
     * Description: 获取扩展名
     * author: CodingHornet
     * Date: 2017/12/11 15:10
     */
    public static String getExtensionName(String path) {
        if ((path != null) && (path.length() > 0)) {
            int dot = path.lastIndexOf('.');
            if ((dot > -1) && (dot < (path.length() - 1))) {
                return path.substring(dot + 1);
            }
        }
        return path;
    }

    /**
     * 打开文件
     *
     * @param file
     */
    public static void openFile(Context context, File file) {
        if (file == null || !file.exists()) {
            Toast.makeText(context, R.string.file_not_exists, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            //获取文件file的MIME类型
            String type = getMIMEType(file);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, "cn.com.thit.tiintegrity.fileprovider", file);
                intent.setDataAndType(contentUri, type);
            } else {
                intent.setDataAndType(Uri.fromFile(file), type);
            }
            //跳转
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.showLong(R.string.no_support_app);
            e.printStackTrace();
        }
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        // 获取文件的后缀名
        String fileType = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (fileType.equals("")) return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (String[] strArr : MIME_MapTable) {
            if (fileType.equals(strArr[0]))
                return strArr[1];
        }
        return type;
    }

    //建立一个MIME类型与文件后缀名的匹配表
    private final static String[][] MIME_MapTable = {
            //{后缀名，    MIME类型}
            {".323", "text/h323"},
            {".3gp", "video/3gpp"},
            {".aab", "application/x-authoware-bin"},
            {".aam", "application/x-authoware-map"},
            {".aas", "application/x-authoware-seg"},
            {".acx", "application/internet-property-stream"},
            {".ai", "application/postscript"},
            {".aif", "audio/x-aiff"},
            {".aifc", "audio/x-aiff"},
            {".aiff", "audio/x-aiff"},
            {".als", "audio/X-Alpha5"},
            {".amc", "application/x-mpeg"},
            {".ani", "application/octet-stream"},
            {".apk", "application/vnd.android.package-archive"},
            {".asc", "text/plain"},
            {".asd", "application/astound"},
            {".asf", "video/x-ms-asf"},
            {".asn", "application/astound"},
            {".asp", "application/x-asap"},
            {".asr", "video/x-ms-asf"},
            {".asx", "video/x-ms-asf"},
            {".au", "audio/basic"},
            {".avb", "application/octet-stream"},
            {".avi", "video/x-msvideo"},
            {".awb", "audio/amr-wb"},
            {".axs", "application/olescript"},
            {".bas", "text/plain"},
            {".bcpio", "application/x-bcpio"},
            {".bin ", "application/octet-stream"},
            {".bld", "application/bld"},
            {".bld2", "application/bld2"},
            {".bmp", "image/bmp"},
            {".bpk", "application/octet-stream"},
            {".bz2", "application/x-bzip2"},
            {".c", "text/plain"},
            {".cal", "image/x-cals"},
            {".cat", "application/vnd.ms-pkiseccat"},
            {".ccn", "application/x-cnc"},
            {".cco", "application/x-cocoa"},
            {".cdf", "application/x-cdf"},
            {".cer", "application/x-x509-ca-cert"},
            {".cgi", "magnus-internal/cgi"},
            {".chat", "application/x-chat"},
            {".class", "application/octet-stream"},
            {".clp", "application/x-msclip"},
            {".cmx", "image/x-cmx"},
            {".co", "application/x-cult3d-object"},
            {".cod", "image/cis-cod"},
            {".conf", "text/plain"},
            {".cpio", "application/x-cpio"},
            {".cpp", "text/plain"},
            {".cpt", "application/mac-compactpro"},
            {".crd", "application/x-mscardfile"},
            {".crl", "application/pkix-crl"},
            {".crt", "application/x-x509-ca-cert"},
            {".csh", "application/x-csh"},
            {".csm", "chemical/x-csml"},
            {".csml", "chemical/x-csml"},
            {".css", "text/css"},
            {".cur", "application/octet-stream"},
            {".dcm", "x-lml/x-evm"},
            {".dcr", "application/x-director"},
            {".dcx", "image/x-dcx"},
            {".der", "application/x-x509-ca-cert"},
            {".dhtml", "text/html"},
            {".dir", "application/x-director"},
            {".dll", "application/x-msdownload"},
            {".dmg", "application/octet-stream"},
            {".dms", "application/octet-stream"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".dot", "application/msword"},
            {".dvi", "application/x-dvi"},
            {".dwf", "drawing/x-dwf"},
            {".dwg", "application/x-autocad"},
            {".dxf", "application/x-autocad"},
            {".dxr", "application/x-director"},
            {".ebk", "application/x-expandedbook"},
            {".emb", "chemical/x-embl-dl-nucleotide"},
            {".embl", "chemical/x-embl-dl-nucleotide"},
            {".eps", "application/postscript"},
            {".epub", "application/epub+zip"},
            {".eri", "image/x-eri"},
            {".es", "audio/echospeech"},
            {".esl", "audio/echospeech"},
            {".etc", "application/x-earthtime"},
            {".etx", "text/x-setext"},
            {".evm", "x-lml/x-evm"},
            {".evy", "application/envoy"},
            {".exe", "application/octet-stream"},
            {".fh4", "image/x-freehand"},
            {".fh5", "image/x-freehand"},
            {".fhc", "image/x-freehand"},
            {".fif", "application/fractals"},
            {".flr", "x-world/x-vrml"},
            {".flv", "flv-application/octet-stream"},
            {".fm", "application/x-maker"},
            {".fpx", "image/x-fpx"},
            {".fvi", "video/isivideo"},
            {".gau", "chemical/x-gaussian-input"},
            {".gca", "application/x-gca-compressed"},
            {".gdb", "x-lml/x-gdb"},
            {".gif", "image/gif"},
            {".gps", "application/x-gps"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".hdf", "application/x-hdf"},
            {".hdm", "text/x-hdml"},
            {".hdml", "text/x-hdml"},
            {".hlp", "application/winhlp"},
            {".hqx", "application/mac-binhex40"},
            {".hta", "application/hta"},
            {".htc", "text/x-component"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".hts", "text/html"},
            {".htt", "text/webviewhtml"},
            {".ice", "x-conference/x-cooltalk"},
            {".ico", "image/x-icon"},
            {".ief", "image/ief"},
            {".ifm", "image/gif"},
            {".ifs", "image/ifs"},
            {".iii", "application/x-iphone"},
            {".imy", "audio/melody"},
            {".ins", "application/x-internet-signup"},
            {".ips", "application/x-ipscript"},
            {".ipx", "application/x-ipix"},
            {".isp", "application/x-internet-signup"},
            {".it", "audio/x-mod"},
            {".itz", "audio/x-mod"},
            {".ivr", "i-world/i-vrml"},
            {".j2k", "image/j2k"},
            {".jad", "text/vnd.sun.j2me.app-descriptor"},
            {".jam", "application/x-jam"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jfif", "image/pipeg"},
            {".jnlp", "application/x-java-jnlp-file"},
            {".jpe", "image/jpeg"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".jpz", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".jwc", "application/jwc"},
            {".kjx", "application/x-kjx"},
            {".lak", "x-lml/x-lak"},
            {".latex", "application/x-latex"},
            {".lcc", "application/fastman"},
            {".lcl", "application/x-digitalloca"},
            {".lcr", "application/x-digitalloca"},
            {".lgh", "application/lgh"},
            {".lha", "application/octet-stream"},
            {".lml", "x-lml/x-lml"},
            {".lmlpack", "x-lml/x-lmlpack"},
            {".log", "text/plain"},
            {".lsf", "video/x-la-asf"},
            {".lsx", "video/x-la-asf"},
            {".lzh", "application/octet-stream"},
            {".m13", "application/x-msmediaview"},
            {".m14", "application/x-msmediaview"},
            {".m15", "audio/x-mod"},
            {".m3u", "audio/x-mpegurl"},
            {".m3url", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".ma1", "audio/ma1"},
            {".ma2", "audio/ma2"},
            {".ma3", "audio/ma3"},
            {".ma5", "audio/ma5"},
            {".man", "application/x-troff-man"},
            {".map", "magnus-internal/imagemap"},
            {".mbd", "application/mbedlet"},
            {".mct", "application/x-mascot"},
            {".mdb", "application/x-msaccess"},
            {".mdz", "audio/x-mod"},
            {".me", "application/x-troff-me"},
            {".mel", "text/x-vmel"},
            {".mht", "message/rfc822"},
            {".mhtml", "message/rfc822"},
            {".mi", "application/x-mif"},
            {".mid", "audio/mid"},
            {".midi", "audio/midi"},
            {".mif", "application/x-mif"},
            {".mil", "image/x-cals"},
            {".mio", "audio/x-mio"},
            {".mmf", "application/x-skt-lbs"},
            {".mng", "video/x-mng"},
            {".mny", "application/x-msmoney"},
            {".moc", "application/x-mocha"},
            {".mocha", "application/x-mocha"},
            {".mod", "audio/x-mod"},
            {".mof", "application/x-yumekara"},
            {".mol", "chemical/x-mdl-molfile"},
            {".mop", "chemical/x-mopac-input"},
            {".mov", "video/quicktime"},
            {".movie", "video/x-sgi-movie"},
            {".mp2", "video/mpeg"},
            {".mp3", "audio/mpeg"},
            {".mp4", "video/mp4"},
            {".mpa", "video/mpeg"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".mpn", "application/vnd.mophun.application"},
            {".mpp", "application/vnd.ms-project"},
            {".mps", "application/x-mapserver"},
            {".mpv2", "video/mpeg"},
            {".mrl", "text/x-mrml"},
            {".mrm", "application/x-mrm"},
            {".ms", "application/x-troff-ms"},
            {".msg", "application/vnd.ms-outlook"},
            {".mts", "application/metastream"},
            {".mtx", "application/metastream"},
            {".mtz", "application/metastream"},
            {".mvb", "application/x-msmediaview"},
            {".mzv", "application/metastream"},
            {".nar", "application/zip"},
            {".nbmp", "image/nbmp"},
            {".nc", "application/x-netcdf"},
            {".ndb", "x-lml/x-ndb"},
            {".ndwn", "application/ndwn"},
            {".nif", "application/x-nif"},
            {".nmz", "application/x-scream"},
            {".nokia-op-logo", "image/vnd.nok-oplogo-color"},
            {".npx", "application/x-netfpx"},
            {".nsnd", "audio/nsnd"},
            {".nva", "application/x-neva1"},
            {".nws", "message/rfc822"},
            {".oda", "application/oda"},
            {".ogg", "audio/ogg"},
            {".oom", "application/x-AtlasMate-Plugin"},
            {".p10", "application/pkcs10"},
            {".p12", "application/x-pkcs12"},
            {".p7b", "application/x-pkcs7-certificates"},
            {".p7c", "application/x-pkcs7-mime"},
            {".p7m", "application/x-pkcs7-mime"},
            {".p7r", "application/x-pkcs7-certreqresp"},
            {".p7s", "application/x-pkcs7-signature"},
            {".pac", "audio/x-pac"},
            {".pae", "audio/x-epac"},
            {".pan", "application/x-pan"},
            {".pbm", "image/x-portable-bitmap"},
            {".pcx", "image/x-pcx"},
            {".pda", "image/x-pda"},
            {".pdb", "chemical/x-pdb"},
            {".pdf", "application/pdf"},
            {".pfr", "application/font-tdpfr"},
            {".pfx", "application/x-pkcs12"},
            {".pgm", "image/x-portable-graymap"},
            {".pict", "image/x-pict"},
            {".pko", "application/ynd.ms-pkipko"},
            {".pm", "application/x-perl"},
            {".pma", "application/x-perfmon"},
            {".pmc", "application/x-perfmon"},
            {".pmd", "application/x-pmd"},
            {".pml", "application/x-perfmon"},
            {".pmr", "application/x-perfmon"},
            {".pmw", "application/x-perfmon"},
            {".png", "image/png"},
            {".pnm", "image/x-portable-anymap"},
            {".pnz", "image/png"},
            {".pot,", "application/vnd.ms-powerpoint"},
            {".ppm", "image/x-portable-pixmap"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".pqf", "application/x-cprplayer"},
            {".pqi", "application/cprplayer"},
            {".prc", "application/x-prc"},
            {".prf", "application/pics-rules"},
            {".prop", "text/plain"},
            {".proxy", "application/x-ns-proxy-autoconfig"},
            {".ps", "application/postscript"},
            {".ptlk", "application/listenup"},
            {".pub", "application/x-mspublisher"},
            {".pvx", "video/x-pv-pvx"},
            {".qcp", "audio/vnd.qcelp"},
            {".qt", "video/quicktime"},
            {".qti", "image/x-quicktime"},
            {".qtif", "image/x-quicktime"},
            {".r3t", "text/vnd.rn-realtext3d"},
            {".ra", "audio/x-pn-realaudio"},
            {".ram", "audio/x-pn-realaudio"},
            {".rar", "application/octet-stream"},
            {".ras", "image/x-cmu-raster"},
            {".rc", "text/plain"},
            {".rdf", "application/rdf+xml"},
            {".rf", "image/vnd.rn-realflash"},
            {".rgb", "image/x-rgb"},
            {".rlf", "application/x-richlink"},
            {".rm", "audio/x-pn-realaudio"},
            {".rmf", "audio/x-rmf"},
            {".rmi", "audio/mid"},
            {".rmm", "audio/x-pn-realaudio"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rnx", "application/vnd.rn-realplayer"},
            {".roff", "application/x-troff"},
            {".rp", "image/vnd.rn-realpix"},
            {".rpm", "audio/x-pn-realaudio-plugin"},
            {".rt", "text/vnd.rn-realtext"},
            {".rte", "x-lml/x-gps"},
            {".rtf", "application/rtf"},
            {".rtg", "application/metastream"},
            {".rtx", "text/richtext"},
            {".rv", "video/vnd.rn-realvideo"},
            {".rwc", "application/x-rogerwilco"},
            {".s3m", "audio/x-mod"},
            {".s3z", "audio/x-mod"},
            {".sca", "application/x-supercard"},
            {".scd", "application/x-msschedule"},
            {".sct", "text/scriptlet"},
            {".sdf", "application/e-score"},
            {".sea", "application/x-stuffit"},
            {".setpay", "application/set-payment-initiation"},
            {".setreg", "application/set-registration-initiation"},
            {".sgm", "text/x-sgml"},
            {".sgml", "text/x-sgml"},
            {".sh", "application/x-sh"},
            {".shar", "application/x-shar"},
            {".shtml", "magnus-internal/parsed-html"},
            {".shw", "application/presentations"},
            {".si6", "image/si6"},
            {".si7", "image/vnd.stiwap.sis"},
            {".si9", "image/vnd.lgtwap.sis"},
            {".sis", "application/vnd.symbian.install"},
            {".sit", "application/x-stuffit"},
            {".skd", "application/x-Koan"},
            {".skm", "application/x-Koan"},
            {".skp", "application/x-Koan"},
            {".skt", "application/x-Koan"},
            {".slc", "application/x-salsa"},
            {".smd", "audio/x-smd"},
            {".smi", "application/smil"},
            {".smil", "application/smil"},
            {".smp", "application/studiom"},
            {".smz", "audio/x-smd"},
            {".snd", "audio/basic"},
            {".spc", "application/x-pkcs7-certificates"},
            {".spl", "application/futuresplash"},
            {".spr", "application/x-sprite"},
            {".sprite", "application/x-sprite"},
            {".sdp", "application/sdp"},
            {".spt", "application/x-spt"},
            {".src", "application/x-wais-source"},
            {".sst", "application/vnd.ms-pkicertstore"},
            {".stk", "application/hyperstudio"},
            {".stl", "application/vnd.ms-pkistl"},
            {".stm", "text/html"},
            {".svg", "image/svg+xml"},
            {".sv4cpio", "application/x-sv4cpio"},
            {".sv4crc", "application/x-sv4crc"},
            {".svf", "image/vnd"},
            {".svg", "image/svg+xml"},
            {".svh", "image/svh"},
            {".svr", "x-world/x-svr"},
            {".swf", "application/x-shockwave-flash"},
            {".swfl", "application/x-shockwave-flash"},
            {".t", "application/x-troff"},
            {".tad", "application/octet-stream"},
            {".talk", "text/x-speech"},
            {".tar", "application/x-tar"},
            {".taz", "application/x-tar"},
            {".tbp", "application/x-timbuktu"},
            {".tbt", "application/x-timbuktu"},
            {".tcl", "application/x-tcl"},
            {".tex", "application/x-tex"},
            {".texi", "application/x-texinfo"},
            {".texinfo", "application/x-texinfo"},
            {".tgz", "application/x-compressed"},
            {".thm", "application/vnd.eri.thm"},
            {".tif", "image/tiff"},
            {".tiff", "image/tiff"},
            {".tki", "application/x-tkined"},
            {".tkined", "application/x-tkined"},
            {".toc", "application/toc"},
            {".toy", "image/toy"},
            {".tr", "application/x-troff"},
            {".trk", "x-lml/x-gps"},
            {".trm", "application/x-msterminal"},
            {".tsi", "audio/tsplayer"},
            {".tsp", "application/dsptype"},
            {".tsv", "text/tab-separated-values"},
            {".ttf", "application/octet-stream"},
            {".ttz", "application/t-time"},
            {".txt", "text/plain"},
            {".uls", "text/iuls"},
            {".ult", "audio/x-mod"},
            {".ustar", "application/x-ustar"},
            {".uu", "application/x-uuencode"},
            {".uue", "application/x-uuencode"},
            {".vcd", "application/x-cdlink"},
            {".vcf", "text/x-vcard"},
            {".vdo", "video/vdo"},
            {".vib", "audio/vib"},
            {".viv", "video/vivo"},
            {".vivo", "video/vivo"},
            {".vmd", "application/vocaltec-media-desc"},
            {".vmf", "application/vocaltec-media-file"},
            {".vmi", "application/x-dreamcast-vms-info"},
            {".vms", "application/x-dreamcast-vms"},
            {".vox", "audio/voxware"},
            {".vqe", "audio/x-twinvq-plugin"},
            {".vqf", "audio/x-twinvq"},
            {".vql", "audio/x-twinvq"},
            {".vre", "x-world/x-vream"},
            {".vrml", "x-world/x-vrml"},
            {".vrt", "x-world/x-vrt"},
            {".vrw", "x-world/x-vream"},
            {".vts", "workbook/formulaone"},
            {".wav", "audio/x-wav"},
            {".wax", "audio/x-ms-wax"},
            {".wbmp", "image/vnd.wap.wbmp"},
            {".wcm", "application/vnd.ms-works"},
            {".wdb", "application/vnd.ms-works"},
            {".web", "application/vnd.xara"},
            {".wi", "image/wavelet"},
            {".wis", "application/x-InstallShield"},
            {".wks", "application/vnd.ms-works"},
            {".wm", "video/x-ms-wm"},
            {".wma", "audio/x-ms-wma"},
            {".wmd", "application/x-ms-wmd"},
            {".wmf", "application/x-msmetafile"},
            {".wml", "text/vnd.wap.wml"},
            {".wmlc", "application/vnd.wap.wmlc"},
            {".wmls", "text/vnd.wap.wmlscript"},
            {".wmlsc", "application/vnd.wap.wmlscriptc"},
            {".wmlscript", "text/vnd.wap.wmlscript"},
            {".wmv", "audio/x-ms-wmv"},
            {".wmx", "video/x-ms-wmx"},
            {".wmz", "application/x-ms-wmz"},
            {".wpng", "image/x-up-wpng"},
            {".wps", "application/vnd.ms-works"},
            {".wpt", "x-lml/x-gps"},
            {".wri", "application/x-mswrite"},
            {".wrl", "x-world/x-vrml"},
            {".wrz", "x-world/x-vrml"},
            {".ws", "text/vnd.wap.wmlscript"},
            {".wsc", "application/vnd.wap.wmlscriptc"},
            {".wv", "video/wavelet"},
            {".wvx", "video/x-ms-wvx"},
            {".wxl", "application/x-wxl"},
            {".x-gzip", "application/x-gzip"},
            {".xaf", "x-world/x-vrml"},
            {".xar", "application/vnd.xara"},
            {".xbm", "image/x-xbitmap"},
            {".xdm", "application/x-xdma"},
            {".xdma", "application/x-xdma"},
            {".xdw", "application/vnd.fujixerox.docuworks"},
            {".xht", "application/xhtml+xml"},
            {".xhtm", "application/xhtml+xml"},
            {".xhtml", "application/xhtml+xml"},
            {".xla", "application/vnd.ms-excel"},
            {".xlc", "application/vnd.ms-excel"},
            {".xll", "application/x-excel"},
            {".xlm", "application/vnd.ms-excel"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".xlt", "application/vnd.ms-excel"},
            {".xlw", "application/vnd.ms-excel"}, {".xm", "audio/x-mod"},
            {".xml", "text/plain"}, {".xml", "application/xml"},
            {".xmz", "audio/x-mod"}, {".xof", "x-world/x-vrml"},
            {".xpi", "application/x-xpinstall"},
            {".xpm", "image/x-xpixmap"}, {".xsit", "text/xml"},
            {".xsl", "text/xml"}, {".xul", "text/xul"},
            {".xwd", "image/x-xwindowdump"}, {".xyz", "chemical/x-pdb"},
            {".yz1", "application/x-yz1"},
            {".z", "application/x-compress"},
            {".zac", "application/x-zaurus-zac"},
            {".zip", "application/zip"},
            {".json", "application/json"},
            {"", "*/*"}
    };
}
