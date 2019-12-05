package com.wangyi.ui10_svg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

public class MapView extends View {
    private List<ProviceItem> itemList;
    private int[]colorArray=new int[]{0xff239bd7,0xff30a9e5,0xff80cbf1,0xffffffff};
    private Context context;
    private Paint paint;
    private ProviceItem selected;
    private float scale=1.0f;
    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        try {
            init(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(Context context) {
        this.context = context;
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        itemList=new ArrayList<>();
        localThread.start();
    }

    private RectF totalRectF;
    private Thread localThread = new Thread() {
        @Override
        public void run() {
            InputStream inputStream = context.getResources().openRawResource(R.raw.china);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(inputStream);
                Element rootElement = doc.getDocumentElement();
                NodeList items = rootElement.getElementsByTagName("path");
                float l=-1;
                float t=-1;
                float r=-1;
                float b=-1;
                List<ProviceItem> list =new ArrayList<>();
                for (int i = 0; i < items.getLength(); i++) {
                    Element element = (Element) items.item(i);
                    String pathData = element.getAttribute("android:pathData");
                    String name = element.getAttribute("android:name");
                    @SuppressLint("RestrictedApi") Path path = PathParser.createPathFromPathData(pathData);
                    ProviceItem proviceItem=new ProviceItem(path);
                    proviceItem.setDrawColor(colorArray[i%4]);
                    proviceItem.setName(name);





                    RectF rect=new RectF();
                    path.computeBounds(rect,true);
                    l=l==-1?rect.left: Math.min(l,rect.left);
                    t=t==-1?rect.top: Math.min(t,rect.top);
                    r=r==-1?rect.right: Math.max(r,rect.right);
                    b=b==-1?rect.bottom: Math.max(b,rect.bottom);





                    list.add(proviceItem);
                }
                itemList=list;
                totalRectF = new RectF(l, t, r, b);
                postInvalidate();
                Handler handler=new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestLayout();
//                        invalidate();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (itemList != null) {
            canvas.save();
            canvas.scale(scale,scale);
            for (ProviceItem proviceItem : itemList) {
                if (proviceItem!=selected) {
                    proviceItem.drawItem(canvas,paint,false);
                }else {
                    proviceItem.drawItem(canvas,paint,true);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event.getX()/scale,event.getY()/scale);
        return super.onTouchEvent(event);
    }

    private void handleTouch(float x, float y) {
        if (itemList == null) {
            return;
        }
        ProviceItem selectedProviceItem=null;
        for (ProviceItem item : itemList) {
            if (item.isTouch(x,y)){
                selectedProviceItem=item;
            }
        }
        if (selectedProviceItem != null) {
            selected=selectedProviceItem;
            postInvalidate();
            Toast.makeText(context, selected.getName()+"", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (totalRectF != null) {
            float mapWidth = totalRectF.width();
            scale=(float) (width/mapWidth);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
