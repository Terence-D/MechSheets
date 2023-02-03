package ca.coffeeshopstudio.meksheets.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ca.coffeeshopstudio.meksheets.ui.composables.AboutCard
import ca.coffeeshopstudio.meksheets.ui.composables.SettingsCard
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState
import ca.coffeeshopstudio.meksheets.ui.utils.ImageLibrary
import ca.coffeeshopstudio.meksheets.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MechSettings(uiState: MechUiState,
                 onUpdateDarkMode: (Int) -> Unit,
                 onUpdateDynamicColors: (Boolean) -> Unit,
                 modifier: Modifier = Modifier,
) {
    val imageLibrary = ImageLibrary()
    val context = LocalContext.current
    Scaffold (
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = {
                    Icon(
                        painter = painterResource(id = imageLibrary.IconContactMe),
                        contentDescription = stringResource(R.string.contact_me),
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                },
                onClick = {
                    sendEmail(context)
                },
                text = {Text(stringResource(R.string.contact_me))}
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = modifier
                    .padding(paddingValues),
            ) {
                item {
                    SettingsCard(modifier = modifier,
                        uiState = uiState,
                        onUpdateDarkMode = onUpdateDarkMode,
                        onUpdateDynamicColors = onUpdateDynamicColors
                    )
                }
                item {
                    AboutCard(modifier = modifier)
                }
            }
        }
    )
}

fun sendEmail(context: Context) {
    val email = Intent(Intent.ACTION_SEND)
    email.type = "text/email"
    email.putExtra(
        Intent.EXTRA_EMAIL,
        arrayOf(context.getString(R.string.email_address))
    ) //developer 's email
    email.putExtra(
        Intent.EXTRA_SUBJECT,
        context.getString(R.string.subject_line)
    ) // Email 's Subject
    context.startActivity(Intent.createChooser(email, context.getString(R.string.email_chooser)))
}
