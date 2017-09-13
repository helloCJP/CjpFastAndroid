package com.cjp.video.ijkplayerview;

import android.net.Uri;
import android.text.TextUtils;

import com.danikula.videocache.ProxyCacheUtils;
import com.danikula.videocache.file.FileNameGenerator;

/**
 * Created by 蔡金品 on 2017/8/24.
 * email : caijinpin@zhexinit.com
 */
public class MyFileNameGenerator implements FileNameGenerator{
    private static final int MAX_EXTENSION_LENGTH = 4;


    /**
     * 返回保存在内存的文件名
     * @param url
     * @return  有 orid  以orid 命名  没有 以 MD5值命名
     */
    @Override
    public String generate(String url) {
        String extension = getExtension(url);
        String name = getOrid(url);
        return TextUtils.isEmpty(extension) ? name : name + "." + extension;
    }

    private String getExtension(String url) {
        int dotIndex = url.lastIndexOf('.');
        int slashIndex = url.lastIndexOf('/');
        int doubtIndex = url.indexOf('?');
        if (doubtIndex == -1){
            doubtIndex = url.length();
        }
        return dotIndex != -1 && dotIndex > slashIndex && dotIndex + 2 + MAX_EXTENSION_LENGTH > doubtIndex ?
                url.substring(dotIndex + 1, doubtIndex) : "";
    }

    private String getOrid(String url){
        String orid = Uri.parse(url).getQueryParameter("orid");
        if (orid != null && !orid.isEmpty()){
            return orid;
        }
        return ProxyCacheUtils.computeMD5(url);
    }
}
