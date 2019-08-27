package www.app.ypy.com.mvpandroid.network;

/**
 * User: Axl_Jacobs(Axl.Jacobs@gmail.com)
 * Date: 2016-05-06
 * Time: 15:37
 * FIXME
 */
public class ApiException extends Exception {
    String resultMessage;
    String resultCode;
    Object extraData;

    public ApiException(String resultCode, String resultMessage , Object extraData) {
        this.resultMessage = resultMessage;
        this.resultCode = resultCode;
        this.extraData = extraData;
    }

    @Override
    public String getMessage() {
        return resultMessage;
    }

    public String getCode() {
        return resultCode;
    }

    public Object getExtraData(){
        return extraData;
    }

}
