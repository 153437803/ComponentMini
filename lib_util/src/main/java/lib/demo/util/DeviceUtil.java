package lib.demo.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description: 手机设备工具类
 * created by kalu on 2016/11/19 11:57
 */
public final class DeviceUtil {


    private static final String TAG = DeviceUtil.class.getSimpleName();

    /**
     * 适配设备
     */
    public static boolean isAdaptDevice(int height) {

        return ("nexus 4".equals(getPhoneModel().toLowerCase()) && height <= 96);
    }

    /**
     * 获得屏幕密度
     */
    public static float getScreenDensity(Context context) {
        return null == context ? -1 : context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获得屏幕高度
     */
    public static int getScreenWidth(Context context) {
        return null == context ? -1 : context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕宽度
     */
    public static int getScreenHeight(Context context) {
        return null == context ? -1 : context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * SD卡判断
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取内网IP地址
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否有网
     */
//    public static boolean isNetworkConnected() {
//
//        Context context = BaseApp.getContext();
//
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mNetworkInfo = mConnectivityManager
//                    .getActiveNetworkInfo();
//            if (mNetworkInfo != null) {
//                return mNetworkInfo.isAvailable();
//            }
//        }
//        return false;
//    }

    /**
     * 返回版本名字，对应build.gradle中的versionName
     */
    public static String getVersionName(Context context) {

        String versionName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
        return versionName;
    }

    /**
     * 返回版本号，对应build.gradle中的versionCode
     */
    public static String getVersionCode(Context context) {
        String versionCode = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(packInfo.versionCode);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
        return versionCode;
    }

    /**
     * 获取设备的唯一标识，deviceId
     */
//    public static String getDeviceId() {
//
//        Context context = BaseApp.getContext();
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceId = tm.getDeviceId();
//        if (deviceId == null) {
//            return "not found";
//        } else {
//            return deviceId;
//        }
//    }

    /**
     * 获取手机品牌
     */
    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 是否是小米手机
     */
    public static boolean isXiaoMi() {
        return "xiaomi".equalsIgnoreCase(DeviceUtil.getPhoneBrand());
    }

    /**
     * 是否是华为手机
     */
    public static boolean isHuaWei() {
        return "huawei".equalsIgnoreCase(DeviceUtil.getPhoneBrand());
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     */
    public static int getBuildLevel() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return
     */
    public static String getBuildVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取当前App进程的id
     */
    public static int getAppProcessId() {
        return android.os.Process.myPid();
    }

    /**
     * 获取当前App进程的Name
     */
    public static String getAppProcessName(Context context, int processId) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取所有运行App的进程集合
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == processId) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));

                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                LogUtil.e("tag", e.getMessage());
            }
        }
        return processName;
    }

    /**
     * 创建App文件夹
     */
    public static String createAPPFolder(Context context, String appName) {

        return createAPPFolder(context, appName, null);
    }

    /**
     * 创建App文件夹
     */
    public static String createAPPFolder(Context context, String appName, String folderName) {
        File root = Environment.getExternalStorageDirectory();
        File folder;
        /**
         * 如果存在SD卡
         */
        if (isSDCardAvailable() && root != null) {
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        } else {
            /**
             * 不存在SD卡，就放到缓存文件夹内
             */
            root = context.getCacheDir();
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        if (folderName != null) {
            folder = new File(folder, folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        return folder.getAbsolutePath();
    }

    /**
     * 通过Uri找到File
     */
    public static File uri2File(Context context, Uri uri) {
        File file;
        String[] project = {MediaStore.Images.Media.DATA};

        Cursor actualImageCursor = context.getContentResolver().query(uri, project, null, null, null);

        if (actualImageCursor != null) {
            int actual_image_column_index = actualImageCursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualImageCursor.moveToFirst();
            String img_path = actualImageCursor
                    .getString(actual_image_column_index);
            file = new File(img_path);
        } else {
            file = new File(uri.getPath());
        }
        if (actualImageCursor != null) actualImageCursor.close();
        return file;
    }

    /**
     * 获取AndroidManifest.xml里 的值
     */
    public static String getMetaData(Context context, String name) {
        String value = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * app 是否在前台显示运行
     */
    public static boolean isRunForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return true;
        String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo app : appProcesses) {
            if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && app.processName.equals(packageName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * app 是否在后台显示运行
     */
    public static boolean isRunBackground(Context context) {
        String processName = "match.android.activity";
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        if (activityManager == null) return false;
        List<ActivityManager.RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processList) {
            if (process.processName.startsWith(processName)) {
                boolean isBackground = process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                boolean isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
                if (isBackground || isLockedState) return true;
                else return false;
            }
        }
        return false;
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight(Context context) {

//        int statusHeight = -1;
//        try {
//            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
//            Object object = clazz.newInstance();
//            int height = Integer.parseInt(clazz.getField("status_bar_height")
//                    .get(object).toString());
//            statusHeight = context.getResources().getDimensionPixelSize(height);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return statusHeight;

        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 判断设备是否有返回键、菜单键来确定是否有 NavigationBar
     */
    public static boolean hasNavigationBar(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            return true;
        }
        return false;
    }

    /**
     * 获取 NavigationBar 的高度
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap getShotWithStatusBar(Context context, Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(context);
        int height = getScreenHeight(context);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap getShotWithoutStatusBar(Context context, Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth(context);
        int height = getScreenHeight(context);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    public static boolean getKeybordStatus(Activity activity) {
        return !(activity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * 更新安卓媒体库(全部文件)
     */
    public static void updataMediaScanner(Context context) {
        MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, null, null);
    }

    /**
     * 更新安卓媒体库(指定文件)
     */
    public static void updataMediaScanner(Context context, String path) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    /**
     * 更新安卓媒体库(指定文件)
     */
    public static void updataMediaScanner2(Context context, String path) {
        MediaScannerConnection.scanFile(context,
                new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        LogUtil.e("tag", "file " + path + " was scanned seccessfully: " + uri);
                    }
                });
    }

    /**
     * 图片保存到指定的文件夹，同时图片出现在图库里
     */
    public static void savePhotoAndDcim(Context context, String filePath, String fileName) {
        // 其次把文件插入到系统图库
        try {
            FileUtil.copyFile(filePath, fileName);
            MediaStore.Images.Media.insertImage(context.getContentResolver(), filePath, fileName, null);

            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 判断是否是十一位手机号码
     */
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(14[0-9])|(17[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    /**
     * 判断是否是大于等于十一位手机号码
     */
    public static boolean isMobileNOLowLevel(String mobiles) {
        boolean flag = false;
        int length = mobiles.length();
        if (length >= 11) {
            flag = true;
        }
        return flag;
    }

    /**
     * 16位随机数[a-zA-Z0-9]的方法
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);//[0,62)

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 小数点后取几位
     *
     * @param key 要转换的数字
     * @return
     */
    public static String NumFormat(Double key) {
        DecimalFormat df = new DecimalFormat("##0.00");
        String format1 = df.format(key);
        return format1;
    }


    /**
     * 读取缓存
     *
     * @param context
     * @param defaultPath
     * @return
     */
//    public static File getCachePath(Context context, String defaultPath) {
//        File cacheDir;
//        // 预防这个手机没有SD卡，或者SD正好被移除了的情况
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            // new File(File dir, String name);在dir的目录下建一个name文件夹并返回
//            // getExternalCacheDir的路径是/sdcard/Android/data/<application
//            // package>/cache
//            cacheDir = new File(CardUtil.getSDCardPath() + File.separator + "caibei" + File.separator + defaultPath);
//            if (!cacheDir.exists()) {
//                cacheDir.mkdirs();
//            }
//            LogUtil.e("Cache", "getCachePath   " + cacheDir.getPath());
//        } else {
//            // getCacheDir的路径是/data/data/<application package>/cache
//
//            cacheDir = new File(context.getCacheDir(), defaultPath);
//            LogUtil.e("Cache", "getCachePath  " + cacheDir.getPath());
//        }
//
//        return cacheDir;
//
//    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    public static String getCacheSize(File file) {

        String size = null;

        try {
            size = getFormatSize(getFolderSize(file));
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 跳转系统浏览器
     */
    public static void goBrower(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /**
     * 生成漂亮的颜色
     */
    public static int generateBeautifulColor() {
        Random random = new Random();
        //为了让生成的颜色不至于太黑或者太白，所以对3个颜色的值进行限定
        int red = random.nextInt(150) + 50;//50-200
        int green = random.nextInt(150) + 50;//50-200
        int blue = random.nextInt(150) + 50;//50-200
        return Color.rgb(red, green, blue);//使用r,g,b混合生成一种新的颜色
    }

    //判断当前系统版本是否支持通知，4.4以下均返回true.
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        }
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");

            int value = (Integer) opPostNotificationValue.get(Integer.class);

            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
        return false;
    }

    public static Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return new Point(display.getWidth(), display.getHeight());
        } else {
            Point point = new Point();
            display.getSize(point);
            return point;
        }
    }

    public static String getJsonFromAssets(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            LogUtil.e("", e.getMessage(), e);
        }
        return stringBuilder.toString();
    }
}