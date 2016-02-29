package cn.ixuehu.phoneguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.utils.ShowToast;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/2/27.
 */
public abstract class BaseSetupActivity extends Activity{
    private GestureDetector gs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gs = new GestureDetector(BaseSetupActivity.this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float vx, float vy) {
                if (Math.abs(vx) > 300){
                    float v = motionEvent.getX() - motionEvent1.getX();
                    if (v > 100){
                        //从右往左
                        next(null);
                    }
                    else if (v < -100){
                        pre(null);
                    }
                }else {
                    ShowToast.show(BaseSetupActivity.this, "速度太 慢");
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gs.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void next(View view){
        next();
        //动画效果
        overridePendingTransition(R.drawable.next_in,R.drawable.next_out);
    }
    public abstract void next();
    public abstract void pre();
    public void pre(View view){
        pre();
        //动画效果
        overridePendingTransition(R.drawable.pre_in,R.drawable.pre_out);
    }

    /**
     * 启动Activity
     * @param type
     */
    public void startActivityAndFinish(Class type)
    {
        Intent intent = new Intent(BaseSetupActivity.this,type);
        startActivity(intent);
        finish();
    }

}
