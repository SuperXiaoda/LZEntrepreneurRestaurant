package cn.com.lz.entrepreneur.restaurant.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Description: 键盘工具
 * author: CodingHornet
 * Date: 2016/8/31 10:19
 */
public class KeyBoardUtil {

    /**
     * @param @param mEditText
     * @param @param mContext
     * @return void
     * @throws
     * @Description: 打开软键盘
     * @author guoyf
     * @date 2015-7-9 下午4:08:49
     */
    public static void openKeyboard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * @param @param mEditText
     * @param @param mContext
     * @return void
     * @throws
     * @Description: 关闭软键盘
     * @author guoyf
     * @date 2015-7-9 下午4:09:03
     */
    public static void closeKeyboard(EditText mEditText, Context mContext) {
        if (mEditText != null && mContext != null) {
            InputMethodManager imm = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
    }
}