package steve.s.journey;

import java.awt.geom.Point2D;
import static java.lang.Math.*;

/**
 *
 * @author Jan Jonatan Larsson
 */
public class Vector2D {

    public double x, y;

    public Vector2D(double x0, double y0) {
        x = x0;
        y = y0;
    }

    public Vector2D(Vector2D v) {
        x = v.x;
        y = v.y;
    }

    public Vector2D(Point2D p) {
        x = p.getX();
        y = p.getY();
    }

    public Vector2D() {
    }

    // void methods:
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2D v) {
        set(v.x, v.y);
    }

    public void set() {
        set(0, 0);
    }

    public void setAngle(double angle) {
        Vector2D v = Vector2D.fromAngle(angle);
        v.setMag(mag());
        set(v);
    }

    public void abs() {
        x = Math.abs(x);
        y = Math.abs(y);
    }

    public void add(Vector2D v) {
        x += v.x;
        y += v.y;
    }

    public void sub(Vector2D v) {
        x -= v.x;
        y -= v.y;
    }

    public void mult(double n) {
        x *= n;
        y *= n;
    }

    public void mult(Vector2D v) {
        x *= v.x;
        y *= v.y;
    }

    public void div(double n) {
        x /= n;
        y /= n;
    }

    public void div(Vector2D v) {
        x /= v.x;
        y /= v.y;
    }

    public void normalize() {
        double a = angle();
        x = cos(a);
        y = sin(a);
    }

    public void setMag(double n) {
        normalize();
        mult(n);
    }

    public void limit(double n) {
        if (mag() > n) {
            setMag(n);
        }
    }

    public void limit(double min, double max) {
        double mag = mag();
        if (mag < min) {
            setMag(min);
        } else if (mag > max) {
            setMag(max);
        }
    }

    public void limitComponents(double xMin, double xMax, double yMin, double yMax) {
        if (x < xMin) {
            x = xMin;
        } else if (x > xMax) {
            x = xMax;
        }
        if (y < yMin) {
            y = yMin;
        } else if (y > yMax) {
            y = yMax;
        }
    }

    public void limitComponents(Vector2D minV, Vector2D maxV) {
        limitComponents(minV.x, maxV.x, minV.y, maxV.y);
    }

    public void rotate(double angleDiff) {
        double newAngle = angle() + angleDiff;
        double mag = mag();
        x = mag * cos(newAngle);
        y = mag * sin(newAngle);
    }

    public void lerp(Vector2D targetedV, double attraction) {
        Vector2D attractionVector = Vector2D.sub(targetedV, this);
        attractionVector.mult(attraction);
        add(attractionVector);
    }

    // non-static return methods
    public double angle(boolean positiveOnly) {
        double angle = Math.atan2(y, x);
        if (positiveOnly && angle < 0) {
            angle += 2 * Math.PI;
        }
        return angle;
    }
    
    public double angle() {
        return angle(false);
    }

    public double biggestComponent() {
        return (x > y) ? x : y;
    }

    public double smallestComponent() {
        return (x < y) ? x : y;
    }

    public double mag() {
        return Math.sqrt(x * x + y * y);
    }

    public double xFromAngle(double componentAngle) {
        double relativeAngle = angle() - componentAngle;
        return mag() * cos(relativeAngle);
    }

    public double yFromAngle(double componentAngle) {
        return xFromAngle(componentAngle + PI / 2);
    }

    public double dist(Vector2D v) {
        return Vector2D.dist(this, v);
    }

    public boolean equals(Vector2D v, double precisionDist) {
        return v != null && dist(v) <= precisionDist;
    }

    public boolean equals(Vector2D v) {
        return equals(v, 0);
    }

    // static return methods
    public static Vector2D fromAngle(double angle, double mag) {
        double xLocal = cos(angle);
        double yLocal = sin(angle);
        Vector2D v = new Vector2D(xLocal, yLocal);
        v.mult(mag);
        return v;
    }

    public static Vector2D fromAngle(double angle) {
        return fromAngle(angle, 1);
    }

    public static Vector2D random2D(double mag) {
        return fromAngle(2 * PI * random(), mag);
    }

    public static Vector2D random2D() {
        return random2D(1);
    }

    public static Vector2D fromPoint(Point2D p) {
        return new Vector2D(p.getX(), p.getY());
    }

    public static Vector2D add(Vector2D v1, Vector2D v2) {
        Vector2D v = new Vector2D(v1);
        v.add(v2);
        return v;
    }

    public static Vector2D add(Vector2D v1, double x2, double y2) {
        Vector2D v = new Vector2D(v1);
        v.x += x2;
        v.y += y2;
        return v;
    }

    public static Vector2D sub(Vector2D v1, Vector2D v2) {
        Vector2D v = new Vector2D(v1);;
        v.sub(v2);
        return v;
    }

    public static Vector2D sub(Vector2D v1, double x2, double y2) {
        Vector2D v = new Vector2D(v1);
        v.x -= x2;
        v.y -= y2;
        return v;
    }

    public static Vector2D mult(Vector2D v, double n) {
        Vector2D newV = new Vector2D(v);
        newV.mult(n);
        return newV;
    }

    public static Vector2D mult(Vector2D v0, Vector2D v1) {
        Vector2D newV = new Vector2D(v0);
        newV.mult(v1);
        return newV;
    }

    public static Vector2D div(Vector2D v, double n) {
        Vector2D newV = new Vector2D(v);
        newV.div(n);
        return newV;
    }

    public static Vector2D div(Vector2D v0, Vector2D v1) {
        Vector2D newV = new Vector2D(v0);
        newV.div(v1);
        return newV;
    }

    public static Vector2D setMag(Vector2D v, double n) {
        Vector2D newV = new Vector2D(v);
        newV.setMag(n);
        return newV;
    }

    public static Vector2D limit(Vector2D v, double n) {
        Vector2D newV = new Vector2D(v);
        newV.limit(n);
        return newV;
    }

    public static Vector2D rotate(Vector2D v, double a) {
        Vector2D newV = new Vector2D(v);
        newV.rotate(a);
        return newV;
    }

    public static double dist(Vector2D v1, Vector2D v2) {
        return Point2D.distance(v1.x, v1.y, v2.x, v2.y);
    }

    public static Vector2D abs(Vector2D v) {
        return new Vector2D(Math.abs(v.x), Math.abs(v.y));
    }

}
