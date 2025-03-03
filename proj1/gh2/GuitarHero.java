package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static final int NUM_NOTES = 37;

    public static void main(String[] args) {
        GuitarString[] strings = new GuitarString[NUM_NOTES];
        for (int i = 0; i < NUM_NOTES; i++) {
            double frequency = 440 * Math.pow(2, (i - 24.0) / 12);
            strings[i] = new GuitarString(frequency);
        }

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index != -1) {
                    strings[index].pluck();
                }
            }

            double sample = 0;
            for (GuitarString string : strings) {
                sample += string.sample();
            }

            StdAudio.play(sample);

            for (GuitarString string : strings) {
                string.tic();
            }
        }
    }
}
