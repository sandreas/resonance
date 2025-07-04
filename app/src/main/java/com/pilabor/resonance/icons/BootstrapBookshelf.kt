import androidx.compose.ui.graphics.vector.ImageVector
import com.pilabor.resonance.icons.createIcon

val BootstrapBookshelf: ImageVector
    get() {
        if (BootstrapBookshelf_ != null) return BootstrapBookshelf_!!
        BootstrapBookshelf_ = createIcon(name="BootstrapBookshelf", path="M2.5 0a.5.5 0 0 1 .5.5V2h10V.5a.5.5 0 0 1 1 0v15a.5.5 0 0 1-1 0V15H3v.5a.5.5 0 0 1-1 0V.5a.5.5 0 0 1 .5-.5M3 14h10v-3H3zm0-4h10V7H3zm0-4h10V3H3z")
        return BootstrapBookshelf_!!
    }

private var BootstrapBookshelf_: ImageVector? = null