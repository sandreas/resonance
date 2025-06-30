import androidx.compose.ui.graphics.vector.ImageVector
import com.pilabor.resonance.icons.createIcon

val BootstrapRecordCircle: ImageVector
    get() {
        if (BootstrapRecordCircle_ != null) return BootstrapRecordCircle_!!
        BootstrapRecordCircle_ = createIcon(name="BootstrapRecordCircle", path="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16 M11 8a3 3 0 1 1-6 0 3 3 0 0 1 6 0")
        return BootstrapRecordCircle_!!
    }

private var BootstrapRecordCircle_: ImageVector? = null