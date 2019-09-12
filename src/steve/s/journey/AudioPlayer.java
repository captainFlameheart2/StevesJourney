package steve.s.journey;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author jonat
 */
public class AudioPlayer {

    public final Clip CLIP;

    private static final ArrayList<AudioPlayer> MEMBERS = new ArrayList<>();

    public AudioPlayer(File audioFile) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        CLIP = AudioSystem.getClip();
        CLIP.open(AudioSystem.getAudioInputStream(audioFile));
        MEMBERS.add(this);
    }

    public void playAudio() {
        if (CLIP.isRunning()) {
            CLIP.stop();
        }
        CLIP.setFramePosition(0);
        CLIP.start();
    }

    public static void stopAllSystemResources() {
        for (AudioPlayer member : MEMBERS) {
            member.CLIP.stop();
            member.CLIP.close();
        }
    }

}
