package steve.s.journey;

import java.awt.Color;
import java.awt.HeadlessException;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/**
 *
 * @author jonat
 */
public class StevesJourney extends JFrame {

    private final Display DISPLAY;

    public StevesJourney() throws HeadlessException, LineUnavailableException, UnsupportedAudioFileException, IOException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Steve's Journey");
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        DISPLAY = new Display();
        add(DISPLAY);
        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws HeadlessException, LineUnavailableException, UnsupportedAudioFileException, IOException {
        StevesJourney sj = new StevesJourney();
        sj.DISPLAY.start();
    }

    public static void quitProcedure() {
        AudioPlayer.stopAllSystemResources();
        System.exit(0);
    }

    public static Color transparencyMod(Color sourceColor, int transparency) {
        return new Color(sourceColor.getRed(), sourceColor.getGreen(), sourceColor.getBlue(), transparency);
    }

    public static double random(double min, double max) {
        double diff = max - min;
        return min + Math.random() * diff;
    }

    public static double random(double max) {
        return random(0, max);
    }

    public static Color randomColor() {
        return new Color((int) random(256), (int) random(256), (int) random(256));
    }

    public static double lerp(double n, double targetN, double targetingMultiplier) {
        double diff = targetN - n;
        return n + targetingMultiplier * diff;
    }

}
