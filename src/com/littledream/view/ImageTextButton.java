package com.littledream.view;

import com.littledream.yoblock.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.widget.Button;

public class ImageTextButton extends Button {

	public static final int GRAVITY_LEFT = 0x03;
	private int resourceId;
	private Bitmap bitmap;
	private boolean block = false;

	public ImageTextButton(Context context) {
	    super(context, null);
	    // TODO Auto-generated constructor stub
    }

    public ImageTextButton(Context context,AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setClickable(true);
        this.setGravity(GRAVITY_LEFT);
        this.setHeight(72);
        resourceId = R.drawable.icon;
        bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
    }
    
    public void setIcon(int resourceId) 
    {
        this.bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        invalidate();
    }
    
    public void setIcon(Drawable drawable)
    {
    	BitmapDrawable bd = (BitmapDrawable) drawable;
    	bitmap =  bd.getBitmap();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        // 图片顶部居中显示
        int x = 4;
        int y = 4;
        if (block)
        	canvas.drawBitmap(ThumbnailUtils.extractThumbnail(bitmap,64,64), x, y, null);
        else
        	canvas.drawBitmap(ThumbnailUtils.extractThumbnail(bitmap,128,128), x, y, null);
        // 坐标需要转换，因为默认情况下Button中的文字居中显示
        // 这里需要让文字在底部显示
        canvas.translate(132, 0);
        super.onDraw(canvas);
    }

	public void setBlock(boolean block) {
		this.block = block;
		invalidate();
	}
	
	public boolean isBlock() {
		return block;
	}
	
}
