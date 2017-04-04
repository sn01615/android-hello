package cn.justphp.hello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.justphp.hello.doufu.Handlers;
import cn.justphp.hello.doufu.Init;

public class MainActivity extends AppCompatActivity {

    private String Tag = "iTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init.init(this);
        Handlers.mHandler(this);
        Log.i(Tag, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Tag, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Tag, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(Tag, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(Tag, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(Tag, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Tag, "onDestroy");
    }
}
