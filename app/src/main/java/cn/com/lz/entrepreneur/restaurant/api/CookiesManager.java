package cn.com.lz.entrepreneur.restaurant.api;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Description:
 * author: lhd
 * Date: 2018/1/23
 */

public class CookiesManager implements CookieJar{

    private final PersistentCookieStore mCookieStore;

    public CookiesManager(Context context) {
        mCookieStore = new PersistentCookieStore(context);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                mCookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return mCookieStore.get(url);
    }
}
