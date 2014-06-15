package com.mingy.android.list;

import java.util.ArrayList;

public class ArrayListUtils {
    private ArrayListUtils( ){
        
    }
    
    public static <V> boolean isEmpty(ArrayList<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }
}
