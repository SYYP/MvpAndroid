package www.app.ypy.com.mvpandroid.network;



/**
 * Created by Administrator on 2017/3/21.
 */

public class NetConfig {
    public static boolean isRelase = false;

    public static String SERVER_HOST;
    public static String ENDPOINT;
    public static String ANSWER_PIC_HOST;
    public static String BUCKET_NAME;
    //云信app_key
    public static String YUNXING_APP_KEY;

    private static int testCase = 1;

    static {
        if (isRelase) {
            ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
            ANSWER_PIC_HOST = ".oss-cn-beijing.aliyuncs.com/";
            BUCKET_NAME = "bj-b00k";
            SERVER_HOST = "http://api.edu-pad.com.cn/";
            YUNXING_APP_KEY = "6ba4e97ff40a1720bb4c193bfd6580ba";
        } else {
            switch (testCase) {
                case 1://测试环境
                    ENDPOINT = "https://oss-cn-shanghai.aliyuncs.com";
                    ANSWER_PIC_HOST = ".oss-cn-shanghai.aliyuncs.com/";
                    BUCKET_NAME = "b00k";
                    SERVER_HOST = "https://www.learningpad.cn/";
                    YUNXING_APP_KEY = "88d269fbc1d49219764c826de8e54d91";
                    break;
                case 2://预发布环境
                    ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
                    ANSWER_PIC_HOST = ".oss-cn-beijing.aliyuncs.com/";
                    BUCKET_NAME = "pre-b00k";
                    SERVER_HOST = "https://api.schoolpad.cn/";
                    YUNXING_APP_KEY = "dfc509a65dca2b658b7cf8e825df9cd6";
                    break;
                default:
                    //默认走测试 环境
                    ENDPOINT = "https://oss-cn-shanghai.aliyuncs.com";
                    ANSWER_PIC_HOST = ".oss-cn-shanghai.aliyuncs.com/";
                    BUCKET_NAME = "b00k";
                    SERVER_HOST = "https://www.learningpad.cn/";
                    YUNXING_APP_KEY = "88d269fbc1d49219764c826de8e54d91";
                    break;
            }
        }
    }

}
