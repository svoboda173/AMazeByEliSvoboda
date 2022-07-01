package edu.wm.cs.cs301.EliSvoboda.gui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import edu.wm.cs.cs301.EliSvoboda.R;

//StateAnimation and StateManual store a Canvas with a stored bitmap

class MazePanel extends View implements P5Panel {
    private Bitmap bitmap;
    private Canvas canvas = new Canvas(bitmap);
    private Paint paintbrush;

    public MazePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MazePanel,
                0, 0);

        paintbrush = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setBitmap(bitmap);
    }

    @Override
    public void commit() {
        invalidate();
        requestLayout();
    }

    @Override
    public boolean isOperational() {
        //TODO
        return false;
    }

    @Override
    public void setColor(int argb) {
        paintbrush.setColor(argb);
    }

    @Override
    public int getColor() {
        return paintbrush.getColor();
    }

    @Override
    public void addBackground(float percentToExit) {
        //TODO
    }

    @Override
    public void addFilledRectangle(int x, int y, int width, int height) {
        Rect r = new Rect(x, y, x + width, y + height);
        paintbrush.setStyle(Paint.Style.FILL);
        canvas.drawRect(r, paintbrush);
    }

    @Override
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {

    }

    @Override
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {

    }

    @Override
    public void addLine(int startX, int startY, int endX, int endY) {

    }

    @Override
    public void addFilledOval(int x, int y, int width, int height) {

    }

    @Override
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    @Override
    public void addMarker(float x, float y, String str) {

    }

    @Override
    public void setRenderingHint(P5RenderingHints hintKey, P5RenderingHints hintValue) {

    }
}
