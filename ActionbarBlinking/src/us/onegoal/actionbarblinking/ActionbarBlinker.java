package us.onegoal.actionbarblinking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.animation.AnimationUtils;

public class ActionbarBlinker extends Drawable implements Animatable {

	private static int BLINKING_DURATION = 10;
	public static int BLINK_SLOW = 3;
	public static int BLINK_MEDIUM = 5;
	public static int BLINK_FAST = 10;
	private static int BLINK_INCREMENTER = 7;
	private boolean isRunning = false;
	private long startTime;
	private Context context;
	private int alpha = 0;
	private int color = Color.BLACK;
	private boolean isAlphaHigh = false;
	private Paint paint = new Paint();
	
	public ActionbarBlinker(Context context, int color, int blinkingSpeed) {
		this.context = context;
		this.BLINK_INCREMENTER = blinkingSpeed;
		this.color = color;
	}
	
	public void setActionBarColor(int color) {
		this.color = color;
	}
	
	@Override
	public void draw(Canvas canvas) {
		Rect bounds = getBounds();
		paint.setColor(color);
		paint.setAlpha(alpha);
		canvas.drawRect(bounds, paint);
	}

	@Override
	public int getOpacity() {
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void start() {
		if(!isRunning()) {
			isRunning = true;
			startTime = AnimationUtils.currentAnimationTimeMillis();
			scheduleSelf(refresher, SystemClock.uptimeMillis() + BLINKING_DURATION);
		}
	}

	@Override
	public void stop() {
		if(isRunning()){
			unscheduleSelf(refresher);
			isRunning = false;
		}
	}
	
	private final Runnable refresher = new Runnable() {

		@Override
		public void run() {
			long totalDuration = AnimationUtils.currentAnimationTimeMillis() - startTime;
		
			if (totalDuration >= BLINKING_DURATION) {
				if(!isAlphaHigh) {
					alpha += BLINK_INCREMENTER;
					if (alpha >= 245) {
						isAlphaHigh = true;
					} 
				} else {
					alpha -= BLINK_INCREMENTER;
					if(alpha <= 25) {
						isAlphaHigh = false;
					}
				}
			}
			scheduleSelf(refresher, SystemClock.uptimeMillis() + BLINKING_DURATION);
			invalidateSelf();
		}
		
	};

}
