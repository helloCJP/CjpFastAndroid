package com.cjp.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.cjp.R;


/**
 * Created by 蔡金品 on 2017/4/11.
 * email : caijinpin@zhexinit.com
 */

public class PhoneInfoActivity extends Activity {

    private TextView text;
    private String phone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_info);

        text = ( TextView ) findViewById ( R.id.textView );
        TelephonyManager tm = ( TelephonyManager ) this.getSystemService( Context.TELEPHONY_SERVICE );

        phone = tm.getLine1Number();
        if( phone != null){
            text.setText( text.getText() + "\n" + "number: " + phone.toString() + "\\");
        }
        text.setText( text.getText() + "\n" + "imei: " + tm.getDeviceId());
        text.setText( text.getText() + "\n" + "NetOperatorName: " + tm.getNetworkOperatorName());
        text.setText( text.getText() + "\n" + "Sim: " + tm.getSimSerialNumber());
        text.setText( text.getText() + "\n" + "imsi: " + tm.getSubscriberId());
        text.setText( text.getText() + "\n" + "NetCountrylso: " + tm.getNetworkCountryIso());
        text.setText( text.getText() + "\n" + "NetOperator: " + tm.getNetworkOperator());
        text.setText( text.getText() + "\n" + "NetType: " + tm.getNetworkType());
        text.setText( text.getText() + "\n" + "PhoneType: " + tm.getPhoneType());
        text.setText( text.getText() + "\n" + "Roaming: " + tm.isNetworkRoaming());
        text.setText( text.getText() + "\n" + "cpuModel: "+ getCPUModel());
        text.setText( text.getText() + "\n" + "cpuNum: " + getNumCores());
        //
        text.setText( text.getText() + "\n" + "Hsman: " + android.os.Build.MANUFACTURER);
        //锟秸讹拷锟矫伙拷锟缴硷拷锟斤拷锟斤拷为锟斤拷锟秸诧拷品
        text.setText( text.getText() + "\n" + "Hstype:" + android.os.Build.MODEL);
        //锟斤拷锟矫伙拷锟缴硷拷锟侥版本锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟界，锟斤拷1锟斤拷锟斤拷3.4b5锟斤拷 (锟斤拷前android 锟侥版本锟脚ｏ拷
        text.setText( text.getText() + "\n" + "OsVer: " + "android_" +android.os.Build.VERSION.RELEASE);
        //锟斤拷取锟斤拷前锟秸伙拷锟斤拷幕锟侥分憋拷锟斤拷   锟斤拷锟斤拷DisplayMetrics 锟斤拷锟结供锟斤拷一锟街癸拷锟斤拷锟斤拷示锟斤拷通锟斤拷锟斤拷息锟斤拷锟斤拷锟斤拷示锟斤拷小锟斤拷锟街憋拷锟绞猴拷锟斤拷锟斤拷
        DisplayMetrics dm = new DisplayMetrics();
        dm = this.getResources().getDisplayMetrics();
        text.setText( text.getText() + "\n" + "ScreenWidth: " + (short) dm.widthPixels );
        text.setText( text.getText() + "\n" + "ScreenHeight: " + (short) dm.heightPixels );
        //锟斤拷取rom
        text.setText( text.getText() + "\n" + "Rom: " + getTotalRom() + " M" );
        //锟斤拷取ram
        text.setText( text.getText() + "\n" + "Ram: " + getTotalRam() + " M" );
        //锟叫讹拷锟角凤拷root
        text.setText( text.getText() + "\n" + "root: " + isRoot());
        //锟斤拷取mac 锟斤拷址.为锟斤拷锟斤拷未锟斤拷锟�
        String mac = getLocalMacAddress( this );
/*		if (TextUtils.isEmpty(mac)) {
			mac = DBUtils.getInstance(this).queryCfgValueByKey( "mac" );
		} else {
			DBUtils.getInstance(this).addCfg( "mac" , mac);
		}*/
        text.setText( text.getText() + "\n" + "mac: " + getLocalMacAddress( this ));

        try {
            //md5
            text.setText( text.getText() + "\n" + "md5: " + getMd5FromFile("/system/lib/libandroid_runtime.so") );
            text.setText( text.getText() + "\n" + "md5_lib: " + getMd5FromFile("/system/lib/libbinder.so"));
            //硬锟斤拷锟芥本
            text.setText( text.getText() + "\n" + "BUILD_ID: " + android.os.Build.ID);
            text.setText( text.getText() + "\n" + "BRAND: " + android.os.Build.BRAND);//锟斤拷锟斤拷系统锟斤拷
            text.setText( text.getText() + "\n" + "DENSITY: " + getDensity( this )+"");//锟斤拷幕锟杰讹拷
            text.setText( text.getText() + "\n" + "COUNTRY: " + getCountry( this ));//锟斤拷锟斤拷
            text.setText( text.getText() + "\n" + "LANGUAGE: " + getLanguage( this ));//锟斤拷前使锟矫碉拷锟斤拷锟斤拷
            text.setText( text.getText() + "\n" + "TARGET_SDKVERSION: " + gettargetSdkVersion( this ));//sdk锟芥本锟斤拷息
//			text.setText( text.getText() + "\n" + "SUBTYPE_NAME: " + getSubtypeName( this ));
            text.setText( text.getText() + "\n" + "KERNELVERSION: " + getKernelVersion());//锟节核版本
//			text.setText( text.getText() + "\n" + "SERIALNO: " + SystemPropertiesUtil.getSerialno());
//			text.setText( text.getText() + "\n" + "BUILD_VERSION: " + SystemPropertiesUtil.getSdkVersion()+"");
            text.setText( text.getText() + "\n" + "SD_ID: " +  getSdcardid());//锟街伙拷sdcard
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    //锟斤拷取SD锟斤拷id
    public static String getSdcardid() {
        String memBlk = "";
        String sd_cid = "";
        try {
            File file = new File("/sys/block/mmcblk1");
            if (file.exists() && file.isDirectory()) {
                memBlk = "mmcblk1";
            } else {
                memBlk = "mmcblk0";
            }
            Process cmd = Runtime.getRuntime().exec("cat/sys/block/" + memBlk + "/device/cid");
            BufferedReader br = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
            sd_cid = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sd_cid;
    }

    // 锟斤拷取锟节核版本
    public static String getKernelVersion() {
        String kernelVersion = "";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/proc/version");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return kernelVersion;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8 * 1024);
        String info = "";
        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                info += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (info != "") {
                final String keyword = "version ";
                int index = info.indexOf(keyword);
                line = info.substring(index + keyword.length());
                index = line.indexOf(" ");
                kernelVersion = line.substring(0, index);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return kernelVersion;
    }

    public static String getSubtypeName(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info2 = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return info2.getSubtypeName();
    }
    // 锟斤拷取targetSdkVersion
    public static int gettargetSdkVersion(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        int targetSdkVersion = applicationInfo.targetSdkVersion;
        return targetSdkVersion;
    }
    //锟斤拷取锟斤拷锟斤拷
    public static String getLanguage(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage();
    }
    //锟斤拷取锟斤拷锟斤拷/锟斤拷锟斤拷
    public static String getCountry(Context context) {
        return context.getResources().getConfiguration().locale.getCountry();
    }

    // 锟斤拷取锟斤拷幕Density
    public static float getDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 锟斤拷取锟侥硷拷锟斤拷MD5值
     *
     * @param filepath
     * @return
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     */
    public static String getMd5FromFile(String filepath) throws NoSuchAlgorithmException, FileNotFoundException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        File f = new File(filepath);
        String output = "";
        InputStream is = new FileInputStream(f);
        byte[] buffer = new byte[8192];
        int read = 0;
        try {
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            output = bigInt.toString(16);
            for (; output.length() < 32;) {
                output = "0" + output;
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to process file for MD5", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException("Unable to close input stream for MD5 calculation", e);
            }
        }
        return output;
    }
    /**
     * 锟斤拷取MAC锟斤拷址
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        String mac = "";
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();

            if (null == info) {
                return "";
            }
            mac = info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }

    //root
    public boolean isRoot() {

        boolean root = false;

        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }

        } catch (Exception e) {
        }

        return root;
    }
    /**
     * 锟斤拷取锟斤拷ram锟斤拷锟斤拷位锟斤拷M
     * @return
     */
    private static int getTotalRam() {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        int initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() / 1024;
            localBufferedReader.close();
        } catch (Exception e) {
        }
        return initial_memory;
    }
    /**
     * 锟斤拷取Rom锟斤拷锟斤拷位M
     */
    private static int getTotalRom() {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        int initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() / 1024;
            localBufferedReader.close();
        } catch (Exception e) {
        }
        return initial_memory;
    }

    public static String getCPUModel() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String str3 = "";
        String ret = "";
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                ret = ret + arrayOfString[i] + " ";
            }

            str3 = localBufferedReader.readLine();
            if (str3.contains("model name")) {
                arrayOfString = str3.split("\\s+");
                for (int i = 2; i < arrayOfString.length; i++) {
                    ret += arrayOfString[i] + " ";
                }
            }

            localBufferedReader.close();
        } catch (Exception e) {
        }
        return ret;
    }

    //CPU 核心数
    public static int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if(Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch(Exception e) {
            //Print exception
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }
}
