
/** An object of this class has (almost) no methods */
public class C extends javax.swing.JFrame {
    
    /** return the area of the window. */
    public int area() {
        return getWidth() * getHeight();
    }
    
    /** set the width of the window to its height. */
    public void makeSquare() {
        setSize(getHeight(), getHeight());
    }
    
    /** Put the date as the title of the window. */
    public void putDate() {
        setTitle( (new java.util.Date()).toString()  );
    }
}