package steve.s.journey;

/**
 *
 * @author jonat
 */
public class TrailJunction implements Updatable {

    private long lifespan = 500000000L;

    private final Trail OWNER;

    private final Vector2D POS;
    private Vector2D perpendicularVectorA = new Vector2D(), perpendicularVectorB = new Vector2D();

    private TrailJunction(Trail owner) {
        OWNER = owner;
        POS = OWNER.pos();
    }

    public static void addToTrail(Trail owner) {
        TrailJunction created = new TrailJunction(owner);
        owner.JUNCTIONS.add(created);
        int index = owner.JUNCTIONS.indexOf(created);
        if (index != 0) {
            created.perpendicularVectorA = Vector2D.sub(created.POS, owner.JUNCTIONS.get(index - 1).POS);
            created.perpendicularVectorA.rotate(Math.PI / 2);
            created.perpendicularVectorA.normalize();

            created.perpendicularVectorB = Vector2D.mult(created.perpendicularVectorA, -1);
        }
        
        Updatable.addMember(created, 0);
    }

    public Vector2D trailShapeContribution(boolean firstContribution) {
        double index = OWNER.JUNCTIONS.indexOf(this);
        if (index == 0) {
            return new Vector2D(POS);
        }
        double maxIndex = OWNER.JUNCTIONS.size() - 1;
        double ownersHalfThickness = index / maxIndex * OWNER.HALF_SOURCE_THICKNESS;
        Vector2D contribution = Vector2D.mult(
                (firstContribution) ? perpendicularVectorA : perpendicularVectorB,
                 ownersHalfThickness
        );
        contribution.add(POS);
        return contribution;
    }

    @Override
    public void update() {
        lifespan -= NS_PER_UPDATE;
        if (lifespan < 0) {
            OWNER.JUNCTIONS.remove(this);
            Updatable.removeMember(this, 0);
        }
    }

}
