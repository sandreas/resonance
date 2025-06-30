import androidx.compose.ui.graphics.vector.ImageVector
import com.pilabor.resonance.icons.createIcon

val BootstrapDisc: ImageVector
    get() {
        if (BootstrapDisc_ != null) return BootstrapDisc_!!
        BootstrapDisc_ = createIcon(name="BootstrapDisc", path="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16 M10 8a2 2 0 1 1-4 0 2 2 0 0 1 4 0M8 4a4 4 0 0 0-4 4 .5.5 0 0 1-1 0 5 5 0 0 1 5-5 .5.5 0 0 1 0 1m4.5 3.5a.5.5 0 0 1 .5.5 5 5 0 0 1-5 5 .5.5 0 0 1 0-1 4 4 0 0 0 4-4 .5.5 0 0 1 .5-.5")
        return BootstrapDisc_!!
    }

private var BootstrapDisc_: ImageVector? = null