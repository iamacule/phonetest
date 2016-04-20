package phamhungan.com.phonetestv3.ui.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import phamhungan.com.phonetestv3.R;

public class DrawMultiTouch extends View{
	
	private SparseArray<PointF> mActivePointers;
	private Paint mPaint;
	private Paint pMessage = new Paint();
	private Paint pClear = new Paint();
	private int[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA,Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,Color.LTGRAY, Color.YELLOW };
	private Paint textPaint;
	int width,height;
	
	public DrawMultiTouch(Context context) {
		super(context);
		//setFocusable(true);
		//setFocusableInTouchMode(true);
		setBackgroundResource(R.drawable.background_caro);
		mActivePointers = new SparseArray<PointF>();
	    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    mPaint.setColor(Color.RED);
	    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		pMessage.setColor(Color.BLUE);
		pMessage.setTextSize(50);
		pMessage.setTextAlign(Paint.Align.CENTER);
		pMessage.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		pClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
	    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.BLUE);
	    textPaint.setTextSize(50);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// lấ vị trí điểm chạm vào
	    int pointerIndex = event.getActionIndex();

	    // lấy id điểm chạm
	    int pointerId = event.getPointerId(pointerIndex);

	    // get masked (not specific to a pointer) action
	    int maskedAction = event.getActionMasked();

	    switch (maskedAction) {
		    case MotionEvent.ACTION_DOWN:
		    case MotionEvent.ACTION_POINTER_DOWN: {
		      // We have a new pointer. Lets add it to the list of pointers
	
			      PointF f = new PointF();
			      f.x = event.getX(pointerIndex);
			      f.y = event.getY(pointerIndex);
			      mActivePointers.put(pointerId, f);
			      break;
		    }
		    case MotionEvent.ACTION_MOVE: { // a pointer was moved
		      for (int size = event.getPointerCount(), i = 0; i < size; i++) {
		    	  PointF point = mActivePointers.get(event.getPointerId(i));
		    	  if (point != null) {
			          point.x = event.getX(i);
			          point.y = event.getY(i);
		        }
		      }
		      break;
		    }
		    case MotionEvent.ACTION_UP:
		    case MotionEvent.ACTION_POINTER_UP:
		    case MotionEvent.ACTION_CANCEL: {
		    	mActivePointers.remove(pointerId);
		    	break;
		    }
	    }
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

	@Override
	protected void onDraw(Canvas canvas) {
		if(mActivePointers.size()>0){
			canvas.drawRect(0, 0, 0, 0, pClear);
			for (int size = mActivePointers.size(), i = 0; i < size; i++) {
				PointF point = mActivePointers.valueAt(i);
				if (point != null)
					mPaint.setColor(colors[i % 9]);
				canvas.drawCircle(point.x, point.y, width/6, mPaint);
				canvas.drawLine(point.x, point.y, point.x, 0, mPaint);
				canvas.drawLine(point.x, point.y, point.x, height, mPaint);
				canvas.drawLine(point.x, point.y, 0, point.y, mPaint);
				canvas.drawLine(point.x, point.y, width, point.y, mPaint);
			}
			canvas.drawText("Total pointers: " + mActivePointers.size(), 50, 50 , textPaint);
		}else {
			canvas.drawText(getResources().getString(R.string.multitouch_guide),width/2,height/2,pMessage);
		}

	}	
}
