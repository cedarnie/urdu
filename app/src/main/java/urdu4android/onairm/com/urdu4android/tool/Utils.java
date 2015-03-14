package urdu4android.onairm.com.urdu4android.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 6a209 on 9/6/13.
 */
public class Utils {

    public static final int MIN_STORAGE_SIZE = 2 * 1024 * 1024; //2M

    public static int getScreenWith(Context ctx) {
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context ctx) {
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    /*解决Toast重复出现问题, 快速显示Toast*/
    public static Toast toast;

    static public void myToast(Context cot, CharSequence str) {
        if (toast == null) {
            toast = Toast.makeText(cot, str, 0);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, context.getResources().getDisplayMetrics());
    }

    public static String getSimpleDateFormat(long lTime) {
        String dastring;
        Calendar calendar = Calendar.getInstance();
        if (lTime > 0) {
            calendar.setTimeInMillis(lTime * 1000);
            Date date = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            dastring = format.format(date);
        } else {
            dastring = new String("");
        }
        return dastring;
    }

    public static SimpleDateFormat SimpleDatFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");


    public static String getTimeFormat(long time_sec) {
        long time_mill = time_sec * 1000;
        Calendar calendar = Calendar.getInstance();
        // calendar.setTimeInMillis(System.currentTimeMillis());
        long ms = 1000 * (calendar.get(Calendar.HOUR_OF_DAY) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND));// 毫秒数
        long ms_now = System.currentTimeMillis();// +
        Date date = new Date(time_mill);

        String preDesc = "";
        if (ms_now - time_mill < ms) {
            preDesc = "今天";
        } else if (ms_now - time_mill < (ms + 24 * 3600 * 1000)) {
            preDesc = "昨天";
        } else if (ms_now - time_mill < (ms + 24 * 3600 * 1000 * 2)) {
            preDesc = "前天";
        } else {
            SimpleDateFormat formatter = null;
            Date nowDate = new Date(ms_now);
            if (nowDate.getYear() == date.getYear()) {
                formatter = new SimpleDateFormat("MM/dd HH:mm");
            } else {
                formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            }
            return formatter.format(date);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String endDesc = formatter.format(date);

        return preDesc + " " + endDesc;
    }

    public static String getTimeTextFormat(long time_sec) {
        long time_mill = time_sec * 1000;
        Calendar calendar = Calendar.getInstance();
        // calendar.setTimeInMillis(System.currentTimeMillis());
        long ms = 1000 * (calendar.get(Calendar.HOUR_OF_DAY) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND));// 毫秒数
        long ms_now = System.currentTimeMillis();// +
        Date date = new Date(time_mill);

        String preDesc = "";
        if (ms_now - time_mill < ms) {
            preDesc = "今天";
            long sub = ms_now - time_mill;
            SimpleDateFormat formatter = null;
            Date subDate = new Date(sub);
            if (sub < 60 * 60 * 1000) {
                if (sub < 60 * 1000) {
                    return "刚刚";
                } else {
                    formatter = new SimpleDateFormat("m分钟前");
                    return formatter.format(subDate);
                }
            } else {
                Date nowDate = new Date(ms_now);
                int createHour = date.getHours();
                int nowHour = nowDate.getHours();
                int createMin = date.getMinutes();
                int nowMin = nowDate.getMinutes();
                int subMin = nowMin - createMin;
                int subHour = nowHour - createHour;
                if (subMin < -30) {
                    subHour = subHour - 1;
                } else if (subMin > 30) {
                    subHour = subHour + 1;
                }

                return subHour + "小时前";
            }

        } else if (ms_now - time_mill < (ms + 24 * 3600 * 1000)) {
            preDesc = "昨天";
        } else if (ms_now - time_mill < (ms + 24 * 3600 * 1000 * 2)) {
            preDesc = "前天";
        } else {
            SimpleDateFormat formatter = null;
            formatter = new SimpleDateFormat("MM月dd日 HH:mm");
            return formatter.format(date);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String endDesc = formatter.format(date);

        return preDesc + " " + endDesc;
    }


    public static String getDateTextFormat(long time_sec) {
        long time_mill = time_sec * 1000;
        Calendar calendar = Calendar.getInstance();
        // calendar.setTimeInMillis(System.currentTimeMillis());
        long ms = 1000 * (calendar.get(Calendar.HOUR_OF_DAY) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND));// 毫秒数
        long ms_now = System.currentTimeMillis();// +
        Date date = new Date(time_mill);

        SimpleDateFormat formatter = null;
        formatter = new SimpleDateFormat("yyyy年M月d日");
        return formatter.format(date);
    }

    public static String getLongDayTime(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(Long.valueOf(strTime) * 1000));
        System.out.println(date);
        return date;
    }


    public static String getShowOrderTime(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String date = sdf.format(new Date(Long.valueOf(strTime) * 1000));
        System.out.println(date);
        return date;
    }

    public static String getShowOrderTime(Long strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String date = sdf.format(new Date(strTime * 1000));
        System.out.println(date);
        return date;
    }

    public static String getLiveShareTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(new Date(time * 1000));
        System.out.println(date);
        return date;
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    //判断email格式是否正确
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
//        System.out.println(m.matches() + "---");
        return m.matches();
    }


    public static boolean isAppRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        String MY_PKG_NAME = "com.taoshijie.top";
        if (list != null && !list.isEmpty()) {
            RunningTaskInfo info = list.get(0);
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                return true;
            }
        }
//		for (RunningTaskInfo info : list) {
//
//			if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
//				return true;
//			}
//		}
        return false;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static boolean copyToClipboard(Context context, String text) {
        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("CopyToClipBoard", text);
                clipboard.setPrimaryClip(clip);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static String generateFileNameByCurTime() {
        long lTime = getCurUnixTime();
        String jsSavePicName = null;
        Calendar calendar = Calendar.getInstance();
        if (lTime > 0) {
            calendar.setTimeInMillis(lTime);
            Date date = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            jsSavePicName = format.format(date) + ".jpg";
        } else {
            return null;
        }

        return jsSavePicName;
    }

    public static long getCurUnixTime() {
        Calendar calendar = Calendar.getInstance();
        long unixTime = calendar.getTimeInMillis();
        return unixTime;
    }


    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (null != pi) {
                return pi.versionName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (null != pi) {
                return pi.versionCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getMediaPath(Activity activity, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = activity.managedQuery(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static void hiddenKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //得到InputMethodManager的实例
        if (imm.isActive()) {
            //如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static void toCs(Context context) {
//        intent.setAction("android.intent.action.CALL");
//        intent.setData(Uri.parse("tel:"+phoneNo));
        Uri telUri = Uri.parse("tel:4008766388");
        Intent intent = new Intent(Intent.ACTION_DIAL, telUri);
        context.startActivity(intent);
    }


    private static long lastClickTime;
    /**
     * @Fields INTERVAL_TIME : TODO(点击按钮响应间隔时间－毫秒)
     */
    private static int  INTERVAL_TIME = 500;

    /**
     * @return 判断两次响应按钮间隔
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < INTERVAL_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    /**
     * @return 判断两次响应按钮间隔
     */
    public static boolean isFastDoubleClick(int TIME) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }



}
