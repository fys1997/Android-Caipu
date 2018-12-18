package com.example.ado.cookbookuser.view.OwnView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.example.ado.cookbookuser.R;

public class SearchTextView extends View {
    private String text;//文本内容
    private int textColor;//文本颜色
    private int textSize;//文本字体大小
    /**
     * background
     * @param context
     * @param attrs
     */
    private int background;
    private int cornerSize;//圆角大小
    /*绘制时控制文本绘制的范围*/
    private Rect textBound;
    private Paint textPaint;

    public SearchTextView(Context context, AttributeSet attrs){
        super(context,attrs,0);
    }
    public SearchTextView(Context context){
        super(context,null);
    }
    /**
     * 获得自我定义的样式属性
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SearchTextView(Context context,AttributeSet attrs,int defStyle){
        super(context, attrs,defStyle);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs,R.styleable.SearchTextView,defStyle,0);
        int n=a.getIndexCount();
        for(int i=0;i<n;i++){
            int attr=a.getIndex(i);
            switch (attr) {
                case R.styleable.SearchTextView_text:
                    text = a.getString(attr);
                    break;
                case R.styleable.SearchTextView_textColor:
                    //默认颜色设置为黑色
                    textColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.SearchTextView_textSize:
                    //默认设置为20sp，TypeValue也可以把sp转化为px
                    textSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.SearchTextView_background:
                    //默认为灰色
                    background = a.getColor(attr, Color.GRAY);
                    break;
                case R.styleable.SearchTextView_cornerSize:
                    //默认圆角为3
                    cornerSize = a.getInteger(attr, 0);
                    break;
            }
        }
        a.recycle();
        /**
         * 获得绘制文本的宽和高
         */
        textPaint=new Paint();
        textPaint.setTextSize(textSize);
        textBound=new Rect();
        textPaint.getTextBounds(text,0,text.length(),textBound);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }
        else{
            textPaint.setTextSize(textSize);
            textPaint.getTextBounds(text,0,text.length(),textBound);

            int desired=getPaddingLeft()+textBound.width()+getPaddingRight();
            width=desired<=widthSize?desired:widthSize;
        }

        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }
        else{
            textPaint.setTextSize(textSize);
            textPaint.getTextBounds(text,0,text.length(),textBound);
            int desired=getPaddingTop()+textBound.height()+getPaddingBottom();
            height=desired<=heightSize?desired:heightSize;
        }
        setMeasuredDimension(width,height);
    }
    @Override
    protected void onDraw(Canvas canvas){
        Paint paint=new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(background);
        RectF rec=new RectF(0,0,getMeasuredWidth(),getMeasuredHeight());
        canvas.drawRoundRect(rec,cornerSize,cornerSize,paint);

        textPaint.setColor(textColor);
        Paint.FontMetricsInt fontMetrics=textPaint.getFontMetricsInt();
        int baseline=(getMeasuredHeight()-fontMetrics.bottom+fontMetrics.top)/2-fontMetrics.top;
        canvas.drawText(text,getPaddingLeft(),baseline,textPaint);
    }
}
