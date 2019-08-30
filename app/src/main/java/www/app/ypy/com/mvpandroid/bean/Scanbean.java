package www.app.ypy.com.mvpandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ypu
 * on 2019/5/14 0014
 */
public class Scanbean implements Parcelable {

    private String deviceModelCode;//设备类型
    private String deviceId;//设备Id
    private boolean mBoolean=true;

    public boolean isBoolean() {
        return mBoolean;
    }

    public void setBoolean(boolean aBoolean) {
        mBoolean = aBoolean;
    }

    public String getDeviceModelCode() {
        return deviceModelCode;
    }

    public void setDeviceModelCode(String deviceModelCode) {
        this.deviceModelCode = deviceModelCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deviceModelCode);
        dest.writeString(this.deviceId);
        dest.writeByte(this.mBoolean ? (byte) 1 : (byte) 0);
    }

    public Scanbean() {
    }

    protected Scanbean(Parcel in) {
        this.deviceModelCode = in.readString();
        this.deviceId = in.readString();
        this.mBoolean = in.readByte() != 0;
    }

    public static final Creator<Scanbean> CREATOR = new Creator<Scanbean>() {
        @Override
        public Scanbean createFromParcel(Parcel source) {
            return new Scanbean(source);
        }

        @Override
        public Scanbean[] newArray(int size) {
            return new Scanbean[size];
        }
    };
}
