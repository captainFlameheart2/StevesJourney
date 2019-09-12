package steve.s.journey;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author jonat
 */
public class Map implements Updatable {

    private final Display DISPLAY;

    public static Map currentMap;

    public final String NAME;

    private final Background BACKGROUND = new Background();

    private long steveSpawnTimer;
    private final Steve STEVE;
    
    private final ArrayList<Platform> NON_BODY_PLATFORMS = new ArrayList<>();
    private final ArrayList<Restriction> RESTRICTIONS = new ArrayList<>();
    private final ArrayList<TriggerZone> TRIGGER_ZONES = new ArrayList<>();
    private final ArrayList<Cable> CABLES = new ArrayList<>();
    private final ArrayList<MovementActivator> MOVEMENT_ACTIVATORS = new ArrayList<>();
    private final ArrayList<CircleBody> CIRCLE_BODIES = new ArrayList<>(), NON_STEVE_CIRCLE_BODIES;

    private final MapCameraTarget CAMERA_TARGET;

    public Map(Display display, String name) throws IOException {
        DISPLAY = display;
        NAME = name;

//        BACKGROUND.TEXTURES.add(new Texture(DISPLAY, ImageIO.read(new File("images\\background.PNG")), new Vector2D(), .1));
//        BACKGROUND.TEXTURES.add(new Texture(DISPLAY, ImageIO.read(new File("images\\house.PNG")), new Vector2D(), .5));

        STEVE = new Steve(DISPLAY, CIRCLE_BODIES, new Vector2D(-210, 60));
        CAMERA_TARGET = new MapCameraTarget(STEVE, new Vector2D(500 / 2, 300 / 2));
        
        RectanglePlatform leftDirtPlatform = new RectanglePlatform(DISPLAY, CIRCLE_BODIES, new Vector2D(-150, 500), 0, new Vector2D(500, 200));
        leftDirtPlatform.setTextureFromBufferedImage(ImageIO.read(new File("images\\grass.PNG")));
        NON_BODY_PLATFORMS.add(leftDirtPlatform);
        
        SignalButton b = new SignalButton(DISPLAY, CIRCLE_BODIES, new Vector2D(), 0, new Vector2D(200, 50));
        NON_BODY_PLATFORMS.add(b);
        
        
        LineRestriction r = new LineRestriction(DISPLAY, new Vector2D(0, 100), Math.PI / 2, 350);
        r.AFFECTABLE_PLATFORMS.add(b);
        RESTRICTIONS.add(r);
        
        
        CustomPlatform lift = new CustomPlatform(DISPLAY, CIRCLE_BODIES, new Vector2D(350, 425), 0);//new Vector2D(500, 50));
        lift.addVertex(new Vector2D(250, 25));
        lift.addVertex(new Vector2D(-250, 25));
        lift.addVertex(new Vector2D(-250, -50));
        lift.addVertex(new Vector2D(-225, -50));
        lift.addVertex(new Vector2D(-225, -25));
        lift.addVertex(new Vector2D(225, -25));
        lift.addVertex(new Vector2D(225, -50));
        lift.addVertex(new Vector2D(250, -50));
        lift.addVertex(new Vector2D(250, 25));
        WaypointSet ws = new WaypointSet(WaypointSet.INFINITE_LOOP_AMOUNT);
        ws.addWaypoint(new Waypoint(new Vector2D(2000, 425)));
        ws.addWaypoint(new Waypoint(new Vector2D(350, 425)));
        lift.SET_WAYPOINT_MOVEMENT_TREATER(ws);
        lift.setTextureFromBufferedImage(ImageIO.read(new File("images\\lift.PNG")));
        NON_BODY_PLATFORMS.add(lift);
        
        RectangleTriggerZone rtz = new RectangleTriggerZone(DISPLAY, new Vector2D(0, 0), new Vector2D(200, 200));
        rtz.ACTIVATION_POINTS.add(STEVE.POS);
        MovementActivator ma = new MovementActivator(DISPLAY, new Vector2D(500, 0));
        ma.MOVEMENT_TREATERS.add(lift.movementTreater);
//        CableExitPort cexp = new CableExitPort(new Vector2D(500, 0));
        CABLES.add(new Cable(rtz.PORT, ma.PORT));
        TRIGGER_ZONES.add(rtz);
        MOVEMENT_ACTIVATORS.add(ma);
        
        RectanglePlatform rightDirtPlatform = new RectanglePlatform(DISPLAY, CIRCLE_BODIES, new Vector2D(2750, 500), 0, new Vector2D(1000, 200));
        rightDirtPlatform.setTextureFromBufferedImage(ImageIO.read(new File("images\\grass2.PNG")));
        NON_BODY_PLATFORMS.add(rightDirtPlatform);
        
        NON_BODY_PLATFORMS.add(LinePlatform.fromEndPoints(new Vector2D(3250, 500), new Vector2D(3250 + 5000, 500), DISPLAY, CIRCLE_BODIES, null));
        
        NON_BODY_PLATFORMS.add(new CirclePlatform(DISPLAY, CIRCLE_BODIES, new Vector2D(3250 + 5000, 500), 0, 700));
        
//        RectanglePlatform rp = new RectanglePlatform(DISPLAY, CIRCLE_BODIES, new Vector2D(0, 300), 0, new Vector2D(800, 300));
//        rp.setTextureFromBufferedImage(ImageIO.read(new File("images\\box.PNG")));
//        WaypointSet wsA = new WaypointSet(new Waypoint(new Vector2D(0, 400)));
//        WaypointSet wsB = new WaypointSet(new Waypoint(new Vector2D(0, -400)));
//        WaypointSet ws = new WaypointSet(WaypointSet.INFINITE_LOOP_AMOUNT);
//        ws.WAYPOINT_SETS.add(wsA);
//        ws.WAYPOINT_SETS.add(wsB);
////        rp.setWaypointSet(ws);
//        NON_BODY_PLATFORMS.add(rp);
//        NON_BODY_PLATFORMS.add(new RectanglePlatform(DISPLAY, CIRCLE_BODIES, new Vector2D(1050, 300), 0, new Vector2D(1000, 100)));
//        NON_BODY_PLATFORMS.add(new LinePlatform(DISPLAY, CIRCLE_BODIES, new Vector2D(1500, 300), Math.PI / 4, 1000));
//
//        CustomPlatform cp = new CustomPlatform(DISPLAY, CIRCLE_BODIES, new Vector2D(1000, 0), 0);
//        cp.addVertex(new Vector2D(100, 200));
//        cp.addVertex(new Vector2D(-300, 100));
//        cp.addVertex(new Vector2D(-100, -200));
//        cp.addVertex(new Vector2D(100, -100));
//        NON_BODY_PLATFORMS.add(cp);
//
//        NON_BODY_PLATFORMS.add(new CirclePlatform(DISPLAY, CIRCLE_BODIES, new Vector2D(2000, 300), 0, 500));

//        CIRCLE_BODIES.add(new CircleBody(DISPLAY, CIRCLE_BODIES, new Vector2D(), 0, 50));
        CIRCLE_BODIES.add(STEVE);
        CIRCLE_BODIES.add(new CircleBody(DISPLAY, CIRCLE_BODIES, new Vector2D(2500, -500), 0, 300));
        
        CIRCLE_BODIES.add(new CircleBody(DISPLAY, CIRCLE_BODIES, new Vector2D(3000, -500), 0, 50));
        CIRCLE_BODIES.add(new CircleBody(DISPLAY, CIRCLE_BODIES, new Vector2D(3200, -500), 0, 50));
        CIRCLE_BODIES.add(new CircleBody(DISPLAY, CIRCLE_BODIES, new Vector2D(3400, -500), 0, 50));
        CIRCLE_BODIES.add(new CircleBody(DISPLAY, CIRCLE_BODIES, new Vector2D(4000, -500), 0, 70));

        NON_STEVE_CIRCLE_BODIES = (ArrayList<CircleBody>) CIRCLE_BODIES.clone();
        NON_STEVE_CIRCLE_BODIES.remove(STEVE);
    }

    public void transition() {
        currentMap = this;
        new TransitionWall(DISPLAY, TransitionWall.ID_TO_MAP);
    }

    public void open() {
        Updatable.addMember(this, 0);

        BACKGROUND.addTexturesToScene();

        Updatable.addMembers(RESTRICTIONS, 2);
        Paintable.addMembers(RESTRICTIONS, 0);
        
        Updatable.addMembers(NON_BODY_PLATFORMS, 2);
        Paintable.addMembers(NON_BODY_PLATFORMS, 0);
        
        Updatable.addMembers(TRIGGER_ZONES, 2);
        Paintable.addMembers(TRIGGER_ZONES, 0);
        
        Paintable.addMembers(CABLES, 0);
        
        Updatable.addMembers(MOVEMENT_ACTIVATORS, 0);
        Paintable.addMembers(MOVEMENT_ACTIVATORS, 0);

        Updatable.addMembers(NON_STEVE_CIRCLE_BODIES, 1);
        Paintable.addMembers(NON_STEVE_CIRCLE_BODIES, 0);

        Updatable.addMember(CAMERA_TARGET, 0);

        DISPLAY.setBackground(new Color(150, 255, 255));
        DISPLAY.CAMERA.targetPos = CAMERA_TARGET.POS;

        steveSpawnTimer = 2000000000L;
    }

    public void close() {
        Updatable.removeMember(this, 0);

        BACKGROUND.removeTexturesFromScene();

        Updatable.removeMembers(NON_BODY_PLATFORMS, 2);
        Paintable.removeMembers(NON_BODY_PLATFORMS, 0);
        
        Updatable.removeMembers(NON_STEVE_CIRCLE_BODIES, 1);
        Paintable.removeMembers(CIRCLE_BODIES, 0);
        
        Updatable.removeMember(STEVE, 0);

        Updatable.removeMember(CAMERA_TARGET, 0);

        currentMap = null;
    }

    @Override
    public void update() {
        if (!steveSpawnTimerComplete()) {
            steveSpawnTimer -= NS_PER_UPDATE;
            if (steveSpawnTimerComplete()) {
                DISPLAY.AUDIO_PLAYER_STORAGE.SPAWN_AUDIO_PLAYER.playAudio();
                STEVE.addToScene();
//                Updatable.addMember(STEVE, 0);
//                Paintable.addMember(STEVE, 0);
                for (int i = 0; i < 20; i++) {
                    SpawnParticle p = SpawnParticle.particleWithRandomDirection(DISPLAY, STEVE.POS);
                    Updatable.addMember(p, 0);
                    Paintable.addMember(p, 0);
                }
                Updatable.removeMember(this, 0);
            }
        }
    }

    private boolean steveSpawnTimerComplete() {
        return steveSpawnTimer < 0;
    }

}
