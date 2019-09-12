/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steve.s.journey;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author jonat
 */
public class AudioPlayerStorage {

    public final AudioPlayer TITLE_SWOSH_AUDIO_PLAYER, START_AUDIO_PLAYER,
            BUTTON_OPENING_AUDIO_PLAYER, HOVERING_AUDIO_PLAYER, SELECTION_AUDIO_PLAYER,
            OLD_TV_AUDIO_PLAYER,
            SPAWN_AUDIO_PLAYER, STEVE_MOVE_AUDIO_PLAYER, HUB_STOPPING_AUDIO_PLAYER,
            STEVE_JUMP_AUDIO_PLAYER,
            TYPE_WRITING_AUDIO_PLAYER;

    public AudioPlayerStorage() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        TITLE_SWOSH_AUDIO_PLAYER = audioPlayerFromFileName("titleswosh");
        START_AUDIO_PLAYER = audioPlayerFromFileName("startup");
        BUTTON_OPENING_AUDIO_PLAYER = audioPlayerFromFileName("buttonopening");
        HOVERING_AUDIO_PLAYER = audioPlayerFromFileName("hovering");
        SELECTION_AUDIO_PLAYER = audioPlayerFromFileName("selection");
        OLD_TV_AUDIO_PLAYER = audioPlayerFromFileName("oldTV");
        SPAWN_AUDIO_PLAYER = audioPlayerFromFileName("spawn");
        STEVE_MOVE_AUDIO_PLAYER = audioPlayerFromFileName("stevemove");
        HUB_STOPPING_AUDIO_PLAYER = audioPlayerFromFileName("hubstopping2");
        STEVE_JUMP_AUDIO_PLAYER = audioPlayerFromFileName("stevejump");
        TYPE_WRITING_AUDIO_PLAYER = audioPlayerFromFileName("typewriting");
    }

    private AudioPlayer audioPlayerFromFileName(String name) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        return new AudioPlayer(new File("sound effects\\" + name + ".WAV"));
    }

}
