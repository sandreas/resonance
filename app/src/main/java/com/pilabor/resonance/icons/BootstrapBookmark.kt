import androidx.compose.ui.graphics.vector.ImageVector
import com.pilabor.resonance.icons.createIcon

val BootstrapBookmark: ImageVector
    get() {
        if (BootstrapBookmark_ != null) return BootstrapBookmark_!!
        BootstrapBookmark_ = createIcon(name="BootstrapBookmark", path="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1z")
        return BootstrapBookmark_!!
    }

private var BootstrapBookmark_: ImageVector? = null