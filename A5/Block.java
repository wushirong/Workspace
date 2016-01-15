import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/** An instance is a rectangular primitive for representing a node in a treemap. */
public class Block extends BoundingBox {
    // Random generator for random colors.
    private static Random gen= new Random(System.currentTimeMillis());

    // Color of selected blocks.
    private static final Color SELECTED_COLOR= new Color(255, 127, 0);

    private Color color;       // Block color

    /** Constructor: a block with minimum corner (in image coordinate system)
     * lo, maximum corner hi, and random color. */
    Block(PointD lo, PointD hi) {
        super(lo, hi);
        // Generate random color
        int red= gen.nextInt(256);
        int green= gen.nextInt(256);
        int blue= gen.nextInt(256);
        color= new Color(red, green, blue);
    }

    /** Constructor: a block with bounding box b and random color. */
    Block(BoundingBox b) {
        this(b.low, b.high);
    }

    /** Constructor: a block with minimum corner
     * (in image coordinate system) lo, maximum corner hi, color c. */
    Block(PointD lo, PointD hi, Color c) {
        super(lo, hi);
        color= c;  // colors are immutable so this is safe
    }

    /** Constructor: a block with bounding box b and color c. */
    Block(BoundingBox b, Color c) {
        this(b.low, b.high, c);
    }

    /** Draw this block using g, which must be a Graphics2D object.
     * @param g  A Graphics2D object.
     * @param w  Width of canvas in pixels
     * @param h  Height of canvas in pixels
     * @param filled  If false, draw only the outline of the block.
     * @param depth  Depth of the block in the tree.
     * @param selected  Is the block selected or not?
     */
    public void paint(Graphics g, int w, int h, boolean filled, int depth,
                      boolean selected) {
        Graphics2D g2d= (Graphics2D) g;

        Color prevColor= g2d.getColor();
        Paint prevPaint= g2d.getPaint();
        Stroke prevStroke= g2d.getStroke();

        // Draw with double precision.
        double topLeftX= w * low.x;
        double topLeftY= h * low.y;
        double width= w * getWidth();
        double height= h * getHeight();

        Rectangle2D rect= new Rectangle2D.Double(topLeftX, topLeftY,
                                                  width, height);
        if (filled) {
            Color fillColor= (selected ? SELECTED_COLOR : color);
            int leftColorRed= Math.min(fillColor.getRed()   + 64, 255);
            int leftColorGreen= Math.min(fillColor.getGreen() + 64, 255);
            int leftColorBlue= Math.min(fillColor.getBlue()  + 64, 255);
            Color leftColor= new Color(leftColorRed, leftColorGreen,
                                       leftColorBlue);
            g2d.setPaint(new GradientPaint((float)topLeftX,
                                           (float)topLeftY,
                                           leftColor,
                                           (float)(topLeftX + width),
                                           (float)(topLeftY + height),
                                           fillColor));
            g2d.fill(rect);
        }

        // Draw the block outline only if the block is big enough
        if (width >= 3  &&  height >= 3) {
            int effectiveDepth= Math.min(Math.max(depth - 1, 0), 30);
            int intensity= (int)Math.round(255.0 / (1 << effectiveDepth));
            Color strokeColor= new Color(intensity, intensity, intensity);
            g2d.setColor(strokeColor);
            g2d.setStroke(new BasicStroke(1));
            g2d.draw(rect);
        }

        g2d.setStroke(prevStroke);  // restore previous stroke
        g2d.setPaint(prevPaint);    // restore previous paint
        g2d.setColor(prevColor);    // restore previous color
    }

    /** Set the color of the block. */
    public void setColor(Color c) {
        color= c;  // colors are immutable so this is safe
    }

    /** Return a description of this block. */
    public @Override String toString() {
        return "bbox = " + super.toString() + ", c = " + color;
    }
}
