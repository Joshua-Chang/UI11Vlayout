package com.wangyi.ui05.splash;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.wangyi.ui05.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ParallaxLayoutInflater extends LayoutInflater {
    private ParallaxFragment fragment;

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext,ParallaxFragment fragment) {
        super(original, newContext);
        this.fragment=fragment;
        //如何在系统控件中加入自定义属性？
        //自定义inflater 填充解析时回调factory方法
        setFactory2(new ParallaxFactory(this));
    }


    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(this,newContext,fragment);
    }
    //拦截自定义属性
    class ParallaxFactory implements Factory2{
        private final String[] sClassPrefix={//所有的系统view包名（还有webview此处不常用）
                "android.widget",
                "android.view",
        };
        //系统控件自定义的属性
        int[] attrIds = {
                R.attr.a_in,
                R.attr.a_out,
                R.attr.x_in,
                R.attr.x_out,
                R.attr.y_in,
                R.attr.y_out};

        private LayoutInflater inflater;

        public ParallaxFactory(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Nullable
        @Override
        public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//            Log.e("xxx",parent.toString()+"\t"+name+"\t"+attrs.getAttributeCount());
            View view=null;
            view=createMyView(name,context,attrs);
            if (view != null) {

                TypedArray array = context.obtainStyledAttributes(attrs,attrIds);
                if (array != null&&array.length()>0) {
                    ParallaxViewTag viewTag = new ParallaxViewTag();
                    viewTag.alphaIn = array.getFloat(0, 0f);
                    viewTag.alphaOut = array.getFloat(array.getIndex(1), 0f);
                    viewTag.xIn = array.getFloat(array.getIndex(2), 0f);
                    viewTag.xOut = array.getFloat(array.getIndex(3), 0f);
                    viewTag.yIn = array.getFloat(array.getIndex(4), 0f);
                    viewTag.yOut = array.getFloat(array.getIndex(5), 0f);
                    view.setTag(R.id.parallax_view_tag, viewTag);
                }
                fragment.getParallaxViews().add(view);//将需要变化的view保存
                array.recycle();
            }
            return null;
        }

        private View createMyView(String name, Context context, AttributeSet attrs) {
            if (name.contains(".")) {//自定义View
                return reflectView(name,null,context,attrs);
            }else {//系统控件
                for (String prefix : sClassPrefix) {
                    View view = reflectView(name, prefix,context, attrs);//attrs 系统控件的所有属性（也包括自定义属性）

                    if (view != null) {
                        return view;
                    }
                }
            }
            return null;
        }
        private View reflectView(String name, String prefix,Context context, AttributeSet attrs){
            try {
                //通过系统的inflater创建视图，读取系统属性
//                也可以反射获取(系统就是这样做的)
//                Class clazz=Class.forName(name);
//                Constructor<View> constructor=Constructor.class.newInstance(context,)
                return inflater.createView(name, prefix, attrs);
            } catch (ClassNotFoundException e) {
                return null;
            }

        }


        @Nullable
        @Override
        public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
            return null;
        }
    }
}
