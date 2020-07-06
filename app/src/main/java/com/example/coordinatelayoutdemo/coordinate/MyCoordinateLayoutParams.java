package com.example.coordinatelayoutdemo.coordinate;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.example.coordinatelayoutdemo.R;
import com.example.coordinatelayoutdemo.base.BaseBehavior;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MyCoordinateLayoutParams extends ViewGroup.LayoutParams {

    public static final String TAG = MyCoordinateLayoutParams.class.getSimpleName();
    public BaseBehavior behavior;

    public MyCoordinateLayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
        final TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.MyCoordinateLayout);
        String full_class_name = typedArray.getString(R.styleable.MyCoordinateLayout_layout_behavior);
        behavior = parseBehavior(c, attrs, full_class_name);
    }

    private BaseBehavior parseBehavior(Context c, AttributeSet attrs, String full_class_name) {

        //获取class的实例
        if (TextUtils.isEmpty(full_class_name)) {
            return null;
        }

        try {
            final Class<?> behaviorClazz = Class.forName(full_class_name, true, c.getClassLoader());
            final Constructor<?> behavior_constructor = behaviorClazz.getConstructor(Context.class,
                    AttributeSet.class);
            behavior_constructor.setAccessible(true);
            return (BaseBehavior) behavior_constructor.newInstance(c, attrs);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("could not inflate Behavior sub class " + full_class_name + "," + e);
        }
    }

    public MyCoordinateLayoutParams(int width, int height) {
        super(width, height);
    }

    public MyCoordinateLayoutParams(ViewGroup.LayoutParams source) {
        super(source);
    }

}
