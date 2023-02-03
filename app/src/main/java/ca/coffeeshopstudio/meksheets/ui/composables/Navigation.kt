package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.ui.state.NavigationTabDestination

data class NavigationItemContent(
    val tabType: NavigationTabDestination,
    val icon: Int,
    val text: String
)

/**
 * Component that displays Navigation Drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MechNavigationDrawerContent(
    selectedDestination: NavigationTabDestination,
    onTabPressed: ((NavigationTabDestination) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(12.dp)
    ) {
        NavigationDrawerHeader(modifier)
        for (navItem in navigationItemContentList) {
            NavigationDrawerItem(
                selected = selectedDestination == navItem.tabType,
                label = {
                    Text(
                        text = navItem.text,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(navItem.icon),
                        contentDescription = navItem.text
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { onTabPressed(navItem.tabType) }
            )
        }
    }
}

/**
 * Component that displays the App logo
 */
@Composable
fun MechAppLogo(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    Row (modifier = modifier.background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(20.dp))){
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = stringResource(R.string.logo),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(color),
            modifier = modifier.clip(CircleShape)
        )
    }}

/**
 * Component that displays Navigation Rail
 */
@Composable
fun MechNavigationRail(
    currentTab: NavigationTabDestination,
    onTabPressed: ((NavigationTabDestination) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.tabType,
                onClick = { onTabPressed(navItem.tabType) },
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

/**
 * Component that displays Top Toolbar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MechTopToolbar(
    onTabPressed: ((NavigationTabDestination) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text= stringResource(R.string.app_name)) },
        actions = {
            for (navItem in navigationItemContentList) {
                IconButton( onClick = { onTabPressed(navItem.tabType) } ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(navItem.icon),
                        contentDescription = navItem.text
                    )
                }
            }
        }
    )
}

@Composable
private fun NavigationDrawerHeader(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MechAppLogo(modifier = Modifier.size(64.dp))
    }
}

