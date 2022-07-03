package edu.wm.cs.cs301.EliSvoboda.gui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import edu.wm.cs.cs301.EliSvoboda.R;
import edu.wm.cs.cs301.EliSvoboda.gui.ColorTheme.MazeColors;

//StateAnimation and StateManual store a Canvas with a stored bitmap

public class MazePanel extends View implements P5Panel {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paintbrush;

    public MazePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MazePanel,
                0, 0);

        paintbrush = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;

        bitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, conf);
        canvas = new Canvas(bitmap);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public void commit() {
        invalidate();
        requestLayout();
    }

    @Override
    public boolean isOperational() {
        return (paintbrush != null && canvas != null);
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
        setColor(ColorTheme.getColor(MazeColors.BACKGROUND_TOP,percentToExit).toArgb());
        addFilledRectangle(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT/2);
        setColor(ColorTheme.getColor(MazeColors.BACKGROUND_BOTTOM,percentToExit).toArgb());
        addFilledRectangle(0, Constants.VIEW_HEIGHT/2, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT/2);
    }

    @Override
    public void addFilledRectangle(int x, int y, int width, int height) {
        Rect r = new Rect(x, y, x + width, y + height);
        paintbrush.setStyle(Paint.Style.FILL);
        canvas.drawRect(r, paintbrush);
    }

    @Override
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        paintbrush.setStyle(Paint.Style.FILL);

        Path path = new Path();
        path.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i < nPoints; i++) {
            path.lineTo(xPoints[i], yPoints[i]);
        }
        path.lineTo(xPoints[0], yPoints[0]);

        canvas.drawPath(path, paintbrush);
    }

    @Override
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        paintbrush.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i < nPoints; i++) {
            path.lineTo(xPoints[i], yPoints[i]);
        }
        path.lineTo(xPoints[0], yPoints[0]);

        canvas.drawPath(path, paintbrush);
    }

    @Override
    public void addLine(int startX, int startY, int endX, int endY) {
        canvas.drawLine((float) startX, (float) startY,(float) endX, (float) endY, paintbrush);
    }

    @Override
    public void addFilledOval(int x, int y, int width, int height) {
        paintbrush.setStyle(Paint.Style.FILL);
        canvas.drawOval((float)x, (float)y, (float)x + width, (float)y + height, paintbrush);
    }

    @Override
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        paintbrush.setStyle(Paint.Style.STROKE);
        canvas.drawArc((float)x, (float)y, (float)x+width, (float)y + height, (float)startAngle, (float)arcAngle, false, paintbrush);
    }

    @Override
    public void addMarker(float x, float y, String str) {
        canvas.drawText(str, x, y, paintbrush);
    }

    @Override
    public void setRenderingHint(P5RenderingHints hintKey, P5RenderingHints hintValue) {
    }
}
