package com.asgard.smartnotes;
import android.support.annotation.IntDef;

@IntDef({Importance.noMatter, Importance.green, Importance.yellow, Importance.red})
public @interface Importance {

    public static int noMatter = 0;
    public static int green = 1;
    public static int yellow = 2;
    public static int red = 3;
}
