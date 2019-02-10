package e.jakubsiembida.ithinkthiswillbethelastone;

import org.neuroph.core.NeuralNetwork;

import java.util.Arrays;

/**
 * Class containing a multilayer perceptron.
 */
public class DigitGuessNeuralNetwork {

    /**
     * A multilayer perceptron with 784 input neurons, 20 hidden neurons and 10 output neurons.
     */
    private NeuralNetwork neuralNetwork;

    /**
     * Default constructor initializing the neural network from the input stream from <code>MainActivity</code> class.
     */
    public DigitGuessNeuralNetwork(){
            neuralNetwork=NeuralNetwork.load(MainActivity.neuralNetInputStream);
    }


    /**
     * Method used for processing the given signal through tne NN.
     * @param input vector of values of length 784.
     * @return vector of length 10, containing activations of the output layer.
     */
    public double[] processSignal(double[] input){
        if(input.length == 784){
            neuralNetwork.setInput(input);
            neuralNetwork.calculate();
            double[] output = neuralNetwork.getOutput();
            String s = "";
            for(double d : output){
                s += String.valueOf(d) + ",";
            }
            return output;
        } else {
            return null;
        }
    }

}
