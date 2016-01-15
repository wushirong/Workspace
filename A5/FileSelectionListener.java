import java.io.File;

/** Require procedure selectionChanged(File) to handle file selection events. */
public interface FileSelectionListener {
    /** Called when the selection changes to path, which may be null. */
    public void selectionChanged(File path);
}
