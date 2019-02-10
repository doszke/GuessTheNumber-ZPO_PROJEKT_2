package e.jakubsiembida.ithinkthiswillbethelastone;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Class describing main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Input stream used for neural network load.
     */
    static InputStream neuralNetInputStream;

    /**
     * <code>CanvasView</code> element used for drawing on screen by finger.
     */
    private CanvasView canvasView;

    /**
     * Instance of class <code>DigitGuesser</code>.
     */
    private DigitGuesser digitGuesser;

    /**
     * Listener for handling selection events on bottom navigation items. The first one, when clicked, clears the canvas. The last one- guesses the number written on screen.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.clear:
                    canvasView.clearCanvas();
                    Toast.makeText(getApplicationContext(), "Canvas cleared", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.guess:
                    Toast.makeText(getApplicationContext(), String.valueOf(digitGuesser.guessDigit(canvasView.getBitmap())), Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };

    /**
     * Method launched after app launch. It creates the activity.
     * @param savedInstanceState Object holding saved values.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        neuralNetInputStream = getResources().openRawResource(R.raw.my_mlp_sth);
        digitGuesser = new DigitGuesser();
        canvasView = (CanvasView) findViewById(R.id.signature_canvas);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


}
