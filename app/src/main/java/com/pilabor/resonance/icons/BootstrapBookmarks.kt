import androidx.compose.ui.graphics.vector.ImageVector
import com.pilabor.resonance.icons.createIcon

val BootstrapBookmarks: ImageVector
    get() {
        if (BootstrapBookmarks_ != null) return BootstrapBookmarks_!!
        BootstrapBookmarks_ = createIcon(name="BootstrapBookmarks", path="M2 4a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v11.5a.5.5 0 0 1-.777.416L7 13.101l-4.223 2.815A.5.5 0 0 1 2 15.5zm2-1a1 1 0 0 0-1 1v10.566l3.723-2.482a.5.5 0 0 1 .554 0L11 14.566V4a1 1 0 0 0-1-1z M4.268 1H12a1 1 0 0 1 1 1v11.768l.223.148A.5.5 0 0 0 14 13.5V2a2 2 0 0 0-2-2H6a2 2 0 0 0-1.732 1")
        return BootstrapBookmarks_!!
    }

private var BootstrapBookmarks_: ImageVector? = null