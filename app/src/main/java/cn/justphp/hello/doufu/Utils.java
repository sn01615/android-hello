package cn.justphp.hello.doufu;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YangLong on 2017/4/4.
 */

public class Utils {
    static public void get(final String url) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Handlers.handlerOnFailure(call, e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Handlers.handlerOnResponse(call, response);
                        }
                    });
                } catch (IllegalArgumentException e) {
                    Handlers.handlerIllegalArgumentException(e);
                }
            }
        }.start();
    }
}
