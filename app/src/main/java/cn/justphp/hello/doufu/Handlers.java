package cn.justphp.hello.doufu;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YangLong on 2017/4/4.
 */

public class Handlers {

    static Handler mHandler;

    public static void doGet() {
        Utils.get("https://api.doufu.la/post/view/1694184?with_ids=1&type=read");
    }

    public static void mHandler(final AppCompatActivity activity) {
        mHandler = new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                super.handleMessage(msg);

                if (msg.what < 0) {
                    switch (msg.what) {
                        case -1:
                            // 网络错误
                            Toast.makeText(activity.getApplicationContext(),
                                    msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            break;
                        case -2:
                            // 参数错误
                            Toast.makeText(activity.getApplicationContext(),
                                    msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        case -3:
                            // Json解析错误
                            Toast.makeText(activity.getApplicationContext(),
                                    msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(activity.getApplicationContext(),
                                    "未知错误：" + Integer.toString(msg.what) + ", "
                                            + msg.obj.toString(),
                                    Toast.LENGTH_SHORT).show();
                    }
                } else {
                    switch (msg.what) {
                        case 1: {
                            Toast.makeText(activity.getApplicationContext(),
                                    "未定义操作：" + msg.obj.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 2: {
                        }
                        break;
                        default:
                            Toast.makeText(activity.getApplicationContext(),
                                    "未定义操作：" + Integer.toString(msg.what),
                                    Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        doGet();
    }

    public static void handlerOnFailure(Call call, IOException e) {
        Message msg = new Message();
        msg.what = -1;
        msg.obj = e.getMessage();
        mHandler.sendMessage(msg);
    }

    public static void handlerOnResponse(Call call, Response response) {
        Message msg = new Message();
        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            msg.what = 1;
            msg.obj = jsonObject.toString();
            mHandler.sendMessage(msg);
        } catch (JSONException e) {
            msg.what = -3;
            msg.obj = e.getMessage();
            mHandler.sendMessage(msg);
        } catch (IOException e) {
            msg.what = -4;
            msg.obj = e.getMessage();
            mHandler.sendMessage(msg);
        }
    }

    public static void handlerIllegalArgumentException(IllegalArgumentException e) {
        Message msg = new Message();
        msg.what = -2;
        msg.obj = e.getMessage();
        mHandler.sendMessage(msg);
    }
}
