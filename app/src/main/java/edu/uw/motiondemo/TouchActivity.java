package edu.uw.motiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class TouchActivity extends Activity {

    private static final String TAG = "Touch";

    private DrawingSurfaceView view;

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (DrawingSurfaceView)findViewById(R.id.drawingView);

        detector = new GestureDetector(this, new MyGestureListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean gestured = detector.onTouchEvent(event);
        //if(gestured){ return true; }

        float x = event.getX();
        float y = event.getY();

        int pointerIndex = MotionEventCompat.getActionIndex(event);
        int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);

        //handle action
        int action = MotionEventCompat.getActionMasked(event);
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG,"Touch!");

                view.addTouch(pointerId, event.getX(pointerIndex), event.getY(pointerIndex));

                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.v(TAG, "Another finger!");

                view.addTouch(pointerId, event.getX(pointerIndex), event.getY(pointerIndex));

                return true;
            case MotionEvent.ACTION_POINTER_UP:
                Log.v(TAG, "A finger left");

                view.removeTouch(pointerId);

                return true;
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "Last finger left");

                view.removeTouch(pointerId);

                return true;
            case (MotionEvent.ACTION_MOVE) : //move

                int pointerCount = event.getPointerCount();
                for(int index=0; index<pointerCount; index++){
                    int pId = event.getPointerId(index);

                    view.moveTouch(pId, event.getX(index), event.getY(index));
                }

                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return true; //we got this
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //what do we want to do on fling

            Log.v(TAG, "Fling!!");
            view.ball.dx = .03f*velocityX;
            view.ball.dy = .03f*velocityY;

            return true; //we handled this
        }
    }
}
