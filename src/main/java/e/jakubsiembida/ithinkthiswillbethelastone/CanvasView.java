package e.jakubsiembida.ithinkthiswillbethelastone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Class defining a drawable by finger element.
 */
public class CanvasView extends View {

    /**
     * Width of the element.
     */
    public int width;

    /**
     * Height of the element.
     */
    public int height;

    /**
     * Bitmap object in which drawing on canvas is saved.
     */
    private Bitmap mBitmap;

    /**
     * Canvas object used for drawing.
     */
    private Canvas mCanvas;

    /**
     * Path object specifying the trajectory of the finger on the canvas.
     */
    private Path mPath;

    /**
     * The Context the view is running in, through which it can access the current theme, resources, etc.
     */
    Context context;

    /**
     * Paint used in the canvas.
     */
    private Paint mPaint;

    /**
     * Paint used for drawing on bitmap with.
     */
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    /**
     * Vertical position on the canvas.
     */
    private float mX;

    /**
     * Horizontal position on the canvas.
     */
    private float mY;

    /**
     * The touch tolerance of the canvas.
     */
    private static final float TOLERANCE=5;

    /**
     * Constructor setting the context and the attribute set.
     * @param c The Context the view is running in, through which it can access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the view. This value may be null.
     */
    public CanvasView(Context c, AttributeSet attrs ){
        super(c,attrs);
        context=c;

        mPath= new Path();
        mPaint= new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(80);
    }

    /**
     * Method sued for gaining access to the bitmap.
     * @return <code>Bitmap</code> object.
     */
    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * Method launched on size change.
     * @param w new width of the element.
     * @param h new height of the element.
     * @param oldw old width of the element.
     * @param oldh old height of the element.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ALPHA_8);
        mCanvas= new Canvas(mBitmap);
    }

    /**
     * Method launched when the canvas is intended to be drawn into.
     * @param canvas <code>Canvas</code> object.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas.drawPath(mPath,mPaint);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

        canvas.save();

    }

    /**
     * Method launched after starting touching the screen.
     * @param x vertical position on the canvas.
     * @param y horizontal position on the canvas.
     */
    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    /**
     * Method launched after changing position on screen while touching the screen.
     * @param x vertical position on the canvas.
     * @param y horizontal position on the canvas.
     */
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    /**
     * Method used for clearing the canvas.
     */
    public void clearCanvas() {
        mPath.reset();
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    /**
     * Method launched when the screen is no longer being touched.
     */
    private void upTouch() {
        mPath.lineTo(mX, mY);
    }

    /**
     * Method used for touch event handling.
     * @param event The motion event.
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}

