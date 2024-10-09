// Nathnael Yonas
// 4/13/22
// CSE143
// TA: Sophie Lin Robertson
// Assignment #2
// The program models the frequency of a vibrating guitar string.

import java.util.*;
public class GuitarString {
	private Queue<Double> ringBuffer;
	public static final int SAMPLING_RATE = StdAudio.SAMPLE_RATE;
	public static final double ENERGY_DECAY = 0.996;
	
	// pre: If 0 is greater than or equal to the frequency or if N is less than 2,
	//then throw Illegal Argument Exception.
	// post: Uses the given frequency to make a guitar string. Makes a ring buffer of
	// the capacity.
	public GuitarString(double frequency) {
		int number = (int) Math.round(SAMPLING_RATE / frequency);
		if (frequency <= 0 || number < 2) {
			throw new IllegalArgumentException();
		}
		ringBuffer = new LinkedList<Double>();
		for (int i = 0; i < number; i++) {
			ringBuffer.add(0.0);
		}
	}
	
	// pre: If the array thats taken in has less than two elements, then throw IllegalArgumentException.
	// post: It initializes elements in the ringBuffer to the array values.
	public GuitarString(double[] init) {
		int integer = init.length;
		if (init.length < 2) {
			throw new IllegalArgumentException();
		}
		ringBuffer = new LinkedList<Double>();
		for(double i = 0; i < integer; i++){
			ringBuffer.add(i);
		}
	}
	
	// pre:
	// post: Takes the elements in the ringBuffer and replaces with random values
	public void pluck() {
		for (int i = 0; i < ringBuffer.size(); i++) {
			ringBuffer.remove();
			ringBuffer.add(Math.random() - 0.5);
		}
	}
	
	// pre:
	// post: Deletes sample at the front and adds to the end of the ring buffer.
	public void tic() {
		double delete = ringBuffer.remove();
		double peek = ringBuffer.peek();
		ringBuffer.add(((delete + peek) / 2) * ENERGY_DECAY);
	}
	
	// pre:
	// post: returns value at the front of ring buffer.
	public double sample() {
		return ringBuffer.peek();
	}
}