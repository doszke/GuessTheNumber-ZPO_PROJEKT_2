package e.jakubsiembida.ithinkthiswillbethelastone;

import android.graphics.Bitmap;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Class used for guessing a digit drawn into a bitmap by the use of multilayer perceptron.
 */
public class DigitGuesser {

    /** @hidden */
    private static final int BITMAP_SIDE_LENGTH = 28;

    /**
     * Neural network used for handwritten digit recognition.
     */
    private DigitGuessNeuralNetwork neuralNetwork = new DigitGuessNeuralNetwork();


    /**
     * Method used for processing the bitmap through the neural network and getting the guess.
     * @param bitmap <code>Bitmap</code> object containing an image of handwritten digit.
     * @return an integer representing the digit which the neural network guesses that is drawn on the bitmap. .
     */
    public int guessDigit(Bitmap bitmap){
        Bitmap resized_bitmap = resizeBitmap(bitmap);


        double[] output = neuralNetwork.processSignal(getNeuralNetInput(resized_bitmap));
        int guess = -1;
        double max = -1;

        if(output != null){

            for (int i = 0; i < output.length; i++) {
                if(output[i] > max){
                    max = output[i];
                    guess = i;
                }
            }
            return guess;
        } else {
            throw new IllegalArgumentException("The input is not appropriate");
        }
    }

    /**
     * Method used for resizing the bitmap into an acceptable format for the neural network.
     * @param bitmap <code>Bitmap</code> object intended to be resized.
     * @return resized bitmap.
     */
    private Bitmap resizeBitmap(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap, BITMAP_SIDE_LENGTH, BITMAP_SIDE_LENGTH, false);
    }

    /**
     * Method transforming the bitmap values into an array.
     * @param b bitmap
     * @return an array containing each pixel value from bitmap
     */
    private double[] getNeuralNetInput(Bitmap b) {
        int bytes = b.getRowBytes() * b.getHeight();
        ByteBuffer buffer = ByteBuffer.allocate(bytes);
        b.copyPixelsToBuffer(buffer);

        byte[] values = buffer.array();
        buffer.clear();

        double[] output = new double[values.length];
        for(int i = 0; i < values.length; i++){
            if(values[i] != 0) {
                output[i] = 1;
            } else {
                output[i] = 0;
            }
        }
        return output;
    }



}

