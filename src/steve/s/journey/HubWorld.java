package steve.s.journey;

import java.awt.Color;
import java.awt.RenderingHints;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class HubWorld implements Updatable {

    private final Display DISPLAY;

    final ArrayList<HubSpot> SPOTS = new ArrayList<>();
    final ArrayList<HubPath> PATHS = new ArrayList<>();

    private final HubSteve STEVE;
    private long steveSpawnTimer;

    private final double SHADOW_ANGLE = Math.PI / 3;

    public HubWorld(Display display) {
        DISPLAY = display;

        HubSpot hubSpotA = new HubSpot(display, new Vector2D(), Color.PINK, SHADOW_ANGLE);
        HubSpot hubSpotB = new HubSpot(display, new Vector2D(200, 0), Color.RED, SHADOW_ANGLE);
        HubSpot hubSpotC = new HubSpot(display, new Vector2D(600, 400), Color.YELLOW, SHADOW_ANGLE);
        HubSpot hubSpotD = new HubSpot(display, new Vector2D(600, -400), Color.GREEN, SHADOW_ANGLE);
        SPOTS.add(hubSpotA);
        SPOTS.add(hubSpotB);
        SPOTS.add(hubSpotC);
        SPOTS.add(hubSpotD);

        PATHS.add(new HubPath(DISPLAY, hubSpotA, hubSpotB));
        PATHS.add(new HubPath(DISPLAY, hubSpotB, hubSpotC));
        PATHS.add(new HubPath(DISPLAY, hubSpotB, hubSpotD));

        STEVE = new HubSteve(display, hubSpotA);
    }

    public void open() {
        Updatable.addMember(this, 0);

        Updatable.addMembers(PATHS, 0);
        Paintable.addMembers(PATHS, 0);
        Updatable.addMembers(SPOTS, 0);

        MouseInputProcessable.MEMBERS_TO_BE_ADDED.addAll(SPOTS);
        Paintable.addMembers(SPOTS, 0);

        steveSpawnTimer = 2000000000L;

        DISPLAY.RENDERING_HINTS.clear();
        DISPLAY.RENDERING_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        DISPLAY.pausable = true;
        DISPLAY.CAMERA.targetPos = STEVE.POS;
        DISPLAY.setBackground(Color.GREEN);
    }

    public void close() {
        Updatable.removeMember(this, 0);

        Updatable.removeMembers(PATHS, 0);
        Paintable.removeMembers(PATHS, 0);
        Updatable.removeMembers(SPOTS, 0);
        MouseInputProcessable.MEMBERS_TO_BE_ADDED.addAll(SPOTS);
        Paintable.removeMembers(SPOTS, 0);

        Updatable.removeMember(STEVE, 0);
        KeyInputProcessable.MEMBERS_TO_BE_REMOVED.add(STEVE);
        Paintable.removeMember(STEVE, 0);
    }

    @Override
    public void update() {
        if (!steveSpawnTimerComplete()) {
            steveSpawnTimer -= NS_PER_UPDATE;
            if (steveSpawnTimer < 1000000000L) {
                DISPLAY.CAMERA.targetScaling = 3;
            }
            if (steveSpawnTimerComplete()) {
                DISPLAY.AUDIO_PLAYER_STORAGE.SPAWN_AUDIO_PLAYER.playAudio();
                Updatable.addMember(STEVE, 0);
                KeyInputProcessable.MEMBERS_TO_BE_ADDED.add(STEVE);
                Paintable.addMember(STEVE, 0);
                for (int i = 0; i < 20; i++) {
                    SpawnParticle p = SpawnParticle.particleWithRandomDirection(DISPLAY, STEVE.POS);
                    Updatable.addMember(p, 0);
                    Paintable.addMember(p, 0);
                }
                Updatable.removeMember(this, 0);

                DISPLAY.CAMERA.resetTargetScaling(500000000L);
            }
        }
    }

    private boolean steveSpawnTimerComplete() {
        return steveSpawnTimer < 0;
    }

}
