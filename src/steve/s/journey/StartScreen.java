package steve.s.journey;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;

/**
 *
 * @author jonat
 */
public class StartScreen implements Updatable, KeyInputProcessable, Paintable {

    private final Display DISPLAY;

    private long runTimer = 1000000000L;

    private boolean toGoAway;

    private final String GAME_TITLE = "STEVE'S ADVENTURE", START_DESCRIPTION = "Press Any Button To Start";
    private int gameTitleSize = 3000;
    private final int GAME_TITLE_TARGET_SIZE = 70;
    private int startDescAlpha, startDescAlphaChange = 5;

    public StartScreen(Display display) {
        DISPLAY = display;
    }

    @Override
    public void update() {
        if (runTimerComplete()) {
            gameTitleSize = (int) StevesJourney.lerp(gameTitleSize, GAME_TITLE_TARGET_SIZE, .1);
            startDescAlpha += startDescAlphaChange;
            if (startDescAlpha > 255) {
                startDescAlpha = 255;
                startDescAlphaChange *= -1;
            } else if (startDescAlpha < 0) {
                startDescAlpha = 0;
                startDescAlphaChange *= -1;
            }
        } else {
            runTimer -= NS_PER_UPDATE;
            if (runTimerComplete()) {
                DISPLAY.AUDIO_PLAYER_STORAGE.TITLE_SWOSH_AUDIO_PLAYER.playAudio();
            }
        }
    }

    private boolean runTimerComplete() {
        return runTimer < 0;
    }

    @Override
    public void processKeyPress(int keyCode) {
        if (runTimerComplete()) {
            DISPLAY.AUDIO_PLAYER_STORAGE.START_AUDIO_PLAYER.playAudio();
            Updatable.removeMember(this, 0);
            KeyInputProcessable.MEMBERS_TO_BE_REMOVED.add(this);
            Paintable.removeMember(this, 0);
            DISPLAY.MENU_SCREEN_STORAGE.HOME_MENU_SCREEN.switchTo();
        }
    }

    @Override
    public void processKeyRelease(int keyCode) {
    }

    @Override
    public boolean whitinPaintingDist() {
        return true;
    }

    @Override
    public void paint(Graphics2D sourceGraphics) {
        if (runTimerComplete()) {
            gameTitleShadowGraphics(sourceGraphics).drawString(GAME_TITLE, 0, 0);
            gameTitleGraphics(sourceGraphics).drawString(GAME_TITLE, 0, 0);
            startDescriptionGraphics(sourceGraphics).drawString(START_DESCRIPTION, 0, 0);
        }
    }

    @Override
    public Graphics2D mainPaintingGraphics(Graphics2D sourceGraphics) {
        return null;
    }

    private Graphics2D gameTitleGraphics(Graphics2D sourceGraphics) {
        Graphics2D gameTitleGraphics = Paintable.stringGraphics(
                sourceGraphics, new Vector2D(0, -300), GAME_TITLE, new Font(Font.DIALOG, Font.BOLD, gameTitleSize)
        );
        gameTitleGraphics.setColor(Color.RED);
        return gameTitleGraphics;
    }

    private Graphics2D gameTitleShadowGraphics(Graphics2D sourceGraphics) {
        Graphics2D gameTitleShadowGraphics = Paintable.stringGraphics(
                sourceGraphics, new Vector2D(0, -293), GAME_TITLE, new Font(Font.DIALOG, Font.BOLD, gameTitleSize)
        );
        gameTitleShadowGraphics.setColor(new Color(0, 0, 0, 150));
        return gameTitleShadowGraphics;
    }

    private Graphics2D startDescriptionGraphics(Graphics2D sourceGraphics) {
        Graphics2D startDescGraphics = Paintable.stringGraphics(
                sourceGraphics, new Vector2D(0, 400), START_DESCRIPTION, new Font(Font.MONOSPACED, Font.BOLD, 30)
        );
        startDescGraphics.setColor(new Color(0, 0, 0, startDescAlpha));
        return startDescGraphics;
    }

    @Override
    public Shape mainPaintingShape() {
        return null;
    }

}
