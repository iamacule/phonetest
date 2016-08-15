package phamhungan.com.phonetestv3.ui.canvas;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.ScreenUtil;

@SuppressLint("ClickableViewAccessibility")
public class DrawTouch extends View{

	private ArrayList<Point> point = new ArrayList<Point>();
	private Paint p = new Paint();
	private Paint p2 = new Paint();
	private Paint pMessage = new Paint();
	private Paint pClear = new Paint();
	private int width,height;
	private int Xcham,Ycham;

	public DrawTouch(Context context) {
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);
		p.setColor(Color.RED);
		p.setAntiAlias(true);
		p2.setColor(Color.BLUE);
		p2.setTextSize(36);
		pMessage.setColor(Color.BLUE);
		pMessage.setTextSize(44);
		pMessage.setTextAlign(Paint.Align.CENTER);
		pMessage.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		pClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		setBackgroundResource(R.drawable.background_caro);
	}

	public DrawTouch(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setFocusableInTouchMode(true);
		p.setColor(Color.RED);
		p.setAntiAlias(true);
		p2.setColor(Color.BLUE);
		p2.setTextSize(36);
		pMessage.setColor(Color.BLUE);
		pMessage.setTextSize(44);
		pMessage.setTextAlign(Paint.Align.CENTER);
		pMessage.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		pClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		setBackgroundResource(R.drawable.background_caro);
	}

	//sự kiện khi ta chạm vào màn hình
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Point po = new Point();
		po.x = (int) event.getX();
		po.y = (int) event.getY();
		point.add(po);
		
		invalidate();	
		return true;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;	
	}
	
	//vẽ lên nền canvas
	@Override
	protected void onDraw(Canvas canvas) {
		if(point.size()>0)
		{
			canvas.drawRect(0, 0, 0, 0, pClear);
			for(int i = 0; i<point.size(); i++)
			{
				canvas.drawCircle(point.get(i).x, point.get(i).y, DataUtil.radius, p);
				Xcham = point.get(i).x;
				Ycham = point.get(i).y;
			}
			canvas.drawText(Xcham+" x "+Ycham, Xcham-30, Ycham-100, p2);
		}
		else {
			canvas.drawText(getResources().getString(R.string.touch_guide),width/2,height/2,pMessage);
		}
	}
}
