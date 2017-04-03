package cn.justphp.hello;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                super.handleMessage(msg);
                if (msg.what < 0) {
                    switch (msg.what) {
                        case -1:
                            // 网络错误
                            Toast.makeText(getApplicationContext(),
                                    msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            break;
                        case -2:
                            // 参数错误
                            Toast.makeText(getApplicationContext(),
                                    msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        case -3:
                            // Json解析错误
                            Toast.makeText(getApplicationContext(),
                                    msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(),
                                    "未知错误：" + Integer.toString(msg.what) + ", "
                                            + msg.obj.toString(),
                                    Toast.LENGTH_SHORT).show();
                    }
                } else {
                    switch (msg.what) {
                        case 1: {
                            TextView textView = (TextView) findViewById(R.id.fuck123);
                            textView.setText(msg.obj.toString());
                        }
                        break;
                        case 2: {
                            TextView textView = (TextView) findViewById(R.id.fuck123);
                            textView.setText(msg.obj.toString());
                        }
                        break;
                        default:
                            Toast.makeText(getApplicationContext(),
                                    "未定义操作：" + Integer.toString(msg.what),
                                    Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        doGet();
    }

    private void doGet() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                final Message msg = new Message();
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://ip-api.com/json/8.8.8.8").build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            msg.what = -1;
                            msg.obj = e.getMessage();
                            mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                msg.what = 1;
                                msg.obj = jsonObject.toString();
                                mHandler.sendMessage(msg);
                            } catch (JSONException e) {
                                msg.what = -3;
                                msg.obj = e.getMessage();
                                mHandler.sendMessage(msg);
                            }
                        }
                    });
                } catch (IllegalArgumentException e) {
                    msg.what = -2;
                    msg.obj = e.getMessage();
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }
}
