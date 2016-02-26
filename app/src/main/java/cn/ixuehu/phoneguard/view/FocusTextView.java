package cn.ixuehu.phoneguard.view;

import android.content.Context;
import android.widget.TextView;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.view
 * Created by daimaren on 2016/2/26.
 */
public class FocusTextView extends TextView{
    public FocusTextView(Context context) {
        super(context);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
