/** A point in the plane using double precision. We also think of this as a
 * vector on in the plane.
 *
 * Remark: This data structure can be used both for something that is
 * conceptually a vector (e.g. a displacement vector) and for something that is
 * conceptually a point on the plane (because a point can be identified with the
 * vector from the origin to the point). */
public class PointD {
    public double x; // x-component of the vector.
    public double y; // y-component of the vector.

    /** Constructor: A copy of p. */
    public PointD(PointD p) {
        x= p.x;
        y= p.y;
    }

    /** Constructor: an instance for point (x, y). */
    public PointD(double x, double y) {
        this.x= x;
        this.y= y;
    }

    /** Return a representation (x, y) of this instance. */
    public @Override String toString() {
        return "(" + x + ", " + y + ")";
    }

    /** Return the result of adding this vector to p.
     *  Do not change p or this vector */
    public PointD add(PointD p) {
        return add(this, p);
    }

    /** Return the result of adding p and q. */
    public static PointD add(PointD p, PointD q) {
        return new PointD(p.x + q.x, p.y + q.y);
    }

    /** Return the Euclidean distance between points p and q. */
    public static double dist(PointD p, PointD q) {
        double dx= p.x - q.x;
        double dy= p.y - q.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /** Add p to this vector. */
    public void addOn(PointD p) {
        x= x + p.x;
        y= y + p.y;
    }

    /** Return the result of subtracting p from this vector.
     * (don't change b; don't change this Vector). */
    public PointD minus(PointD p) {
        return new PointD(x - p.x, y - p.y);
    }

    /** Return the result of multiplying this vector by scalar s.
     * (don't change this Vector). */
    public PointD scale(double s) {
        return new PointD(s * x, s * y);
    }
}
