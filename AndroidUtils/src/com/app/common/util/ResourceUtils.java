package com.app.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * ResourceUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-5-26
 */
public class ResourceUtils {

    /**
     * get an asset using ACCESS_STREAMING mode. This provides access to files that have been bundled with an
     * application as assets -- that is, files placed in to the "assets" directory.
     * 
     * @param context
     * @param fileName The name of the asset to open. This name can be hierarchical.
     * @return
     */
    public static String geFileFromAssets(Context context, String fileName) {
        if (context == null || StringUtils.isEmpty(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder("");
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get content from a raw resource. This can only be used with resources whose value is the name of an asset files
     * -- that is, it can be used to open drawable, sound, and raw resources; it will fail on string and color
     * resources.
     * 
     * @param context
     * @param resId The resource identifier to open, as generated by the appt tool.
     * @return
     */
    public static String geFileFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * same to {@link ResourceUtils#geFileFromAssets(Context, String)}, but return type is List<String>
     * 
     * @param context
     * @param fileName
     * @return
     */
    public static List<String> geFileToListFromAssets(Context context, String fileName) {
        if (context == null || StringUtils.isEmpty(fileName)) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * same to {@link ResourceUtils#geFileFromRaw(Context, int)}, but return type is List<String>
     * 
     * @param context
     * @param resId
     * @return
     */
    public static List<String> geFileToListFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            reader = new BufferedReader(in);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Object loadRes(ResType type, Context context, int id) {
        if (context == null || id < 1) return null;
        switch (type) {
            case Animation:
                return getAnimation(context, id);
            case Boolean:
                return getBoolean(context, id);
            case Color:
                return getColor(context, id);
            case ColorStateList:
                return getColorStateList(context, id);
            case Dimension:
                return getDimension(context, id);
            case DimensionPixelOffset:
                return getDimensionPixelOffset(context, id);
            case DimensionPixelSize:
                return getDimensionPixelSize(context, id);
            case Drawable:
                return getDrawable(context, id);
            case Integer:
                return getInteger(context, id);
            case IntArray:
                return getIntArray(context, id);
            case Movie:
                return getMovie(context, id);
            case String:
                return getString(context, id);
            case StringArray:
                return getStringArray(context, id);
            case Text:
                return getText(context, id);
            case TextArray:
                return getTextArray(context, id);
            case Xml:
                return getXml(context, id);
            default:
                break;
        }

        return null;
    }

    public static Animation getAnimation(Context context, int id) {
        return AnimationUtils.loadAnimation(context, id);
    }

    public static boolean getBoolean(Context context, int id) {
        return context.getResources().getBoolean(id);
    }

    public static int getColor(Context context, int id) {
        return context.getResources().getColor(id);
    }

    public static ColorStateList getColorStateList(Context context, int id) {
        return context.getResources().getColorStateList(id);
    }

    public static float getDimension(Context context, int id) {
        return context.getResources().getDimension(id);
    }

    public static int getDimensionPixelOffset(Context context, int id) {
        return context.getResources().getDimensionPixelOffset(id);
    }

    public static int getDimensionPixelSize(Context context, int id) {
        return context.getResources().getDimensionPixelSize(id);
    }

    public static Drawable getDrawable(Context context, int id) {
        return context.getResources().getDrawable(id);
    }

    public static int getInteger(Context context, int id) {
        return context.getResources().getInteger(id);
    }

    public static int[] getIntArray(Context context, int id) {
        return context.getResources().getIntArray(id);
    }

    public static Movie getMovie(Context context, int id) {
        return context.getResources().getMovie(id);
    }

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String[] getStringArray(Context context, int id) {
        return context.getResources().getStringArray(id);
    }

    public static CharSequence getText(Context context, int id) {
        return context.getResources().getText(id);
    }

    public static CharSequence[] getTextArray(Context context, int id) {
        return context.getResources().getTextArray(id);
    }

    public static XmlResourceParser getXml(Context context, int id) {
        return context.getResources().getXml(id);
    }
    
    public enum ResType {
        Animation,
        Boolean,
        Color,
        ColorStateList,
        Dimension,
        DimensionPixelOffset,
        DimensionPixelSize,
        Drawable,
        Integer,
        IntArray,
        Movie,
        String,
        StringArray,
        Text,
        TextArray,
        Xml
    }
}
