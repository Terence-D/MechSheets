package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.BuildConfig
import ca.coffeeshopstudio.meksheets.R

@Composable
fun AboutCard(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    val urlGsonGit: String = stringResource(id = R.string.website_gson)
    val urlApache2: String = stringResource(id = R.string.website_apache)
    val urlLicense: String = stringResource(id = R.string.license_url)
    val urlMsUrl: String = stringResource(id = R.string.website_source_code)
    val urlCatalyst: String = stringResource(id = R.string.website_catalyst)
    Card(
        modifier = modifier.padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            SettingsTitle(stringResource(id = R.string.about))
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                stringResource(
                    id = R.string.version,
                    BuildConfig.VERSION_NAME,
                    BuildConfig.VERSION_CODE
                )
            )
            SettingsSubTitle(stringResource(id = R.string.legal))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    uriHandler.openUri(urlMsUrl)
                }) {
                    Text(stringResource(id = R.string.source_code), modifier = Modifier)
                }
                Button(onClick = {
                    uriHandler.openUri(urlLicense)
                }) {
                    Text(stringResource(id = R.string.cc_by_nc_4), modifier = Modifier)
                }
            }
            Text(stringResource(id = R.string.legal_contents_a), modifier = Modifier)
            Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        uriHandler.openUri(urlCatalyst)
                    },
                ) {
                    Text(stringResource(id = R.string.catalyst), modifier = Modifier)
                }
            }
            Text(stringResource(id = R.string.legal_contents_b), modifier = Modifier)
            Text(stringResource(id = R.string.legal_contents_a), modifier = Modifier)
            SettingsSubTitle(stringResource(id = R.string.libraries))
            Text(stringResource(id = R.string.legal_gson), modifier = Modifier)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    uriHandler.openUri(urlGsonGit)
                }) {
                    Text(stringResource(id = R.string.gson_title), modifier = Modifier)
                }
                Button(onClick = {
                    uriHandler.openUri(urlApache2)
                }) {
                    Text(stringResource(id = R.string.apache_title), modifier = Modifier)
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}