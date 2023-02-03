package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import ca.coffeeshopstudio.meksheets.ui.state.RecordSheetTab

data class MechNavBarItemContent(
    val tabType: RecordSheetTab,
    val icon: Int,
    val text: String
)

/**
 * Component that displays Bottom Navigation Bar
 * for mech sheets
 */
@Composable
fun MechNavBar(
    currentTab: RecordSheetTab,
    onTabPressed: ((RecordSheetTab) -> Unit),
    recordSheetTabList: List<MechNavBarItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier.fillMaxWidth()) {
        for (navItem in recordSheetTabList) {
            NavigationBarItem(
                selected = currentTab == navItem.tabType,
                alwaysShowLabel = true,
                onClick = { onTabPressed(navItem.tabType) },
                label = { Text(navItem.text) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(navItem.icon),
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }
}
