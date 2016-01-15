/** An instance is a 2D bounding box. */
public class BoundingBox {
    /** The corner of the bounding box with smaller x,y coordinates. */
    public final PointD low; // (minX, minY)

    /** The corner of the bounding box with larger x,y coordinates. */
    public final PointD high; // (maxX, maxY)

    /** Constructor: an instance is a copy of bounding box b.*/
    public BoundingBox(BoundingBox b) {
        low= new PointD(b.low);
        high= new PointD(b.high);
    }

    /** Constructor: An instance with low as smaller coordinates and
     * high as larger coordinates.
     * Precondition low.x <= high.x and low.y <= high.y */
    public BoundingBox(PointD low, PointD high) {
        assert low.x <= high.x  && low.y <= high.y;

        this.low= low;
        this.high= high;
    }

    /** Return the width of this bounding box (along x-dimension). */
    public double getWidth() {
        return high.x - low.x;
    }

    /** Return the height of this bounding box (along y-dimension). */
    public double getHeight() {
        return high.y - low.y;
    }

    /** Return the larger of the width and height of this bounding box. */
    public double getLength() {
        return Math.max(getHeight(),getWidth());
    }

    /** Return the area of this bounding box. */
    public double getArea() {
        return getWidth() * getHeight();
    }

    /** Return the center of this bounding box. */
    public PointD getCenter() {
        return new PointD((high.x + low.x) / 2, (high.y + low.y) / 2);
    }

    /** Return the result of displacing this bounding box by d. */
    public BoundingBox displaced(PointD d) {
        PointD l= PointD.add(low, d);
        PointD u= PointD.add(high, d);
        return new BoundingBox(l,u);
    }

    /** Return true iff this bounding box contains p. */
    public boolean contains(PointD p) {
        boolean inX= low.x <= p.x && p.x <= high.x;
        boolean inY= low.y <= p.y && p.y <= high.y;
        return inX && inY;
    }

    /** Return true iff this bounding box overlaps with b. */
    public boolean overlaps(BoundingBox b) {
        if (high.x < b.low.x) return false;
        if (low.x > b.high.x) return false;
        if (high.y < b.low.y) return false;
        if (low.y > b.high.y) return false;
        return true;
    }

    /** Return a representation of this bounding box. */
    public @Override String toString() {
        return low + "--" + high;
    }
}
