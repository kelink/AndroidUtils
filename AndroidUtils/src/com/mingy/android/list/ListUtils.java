package com.mingy.android.list;

import java.util.List;

public class ListUtils {
    private ListUtils( ){
        
    }
    
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }
}
