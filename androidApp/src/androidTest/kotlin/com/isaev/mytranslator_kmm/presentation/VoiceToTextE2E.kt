package com.isaev.mytranslator_kmm.presentation

import android.Manifest
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.isaev.mytranslator_kmm.android.MainActivity
import com.isaev.mytranslator_kmm.android.R
import com.isaev.mytranslator_kmm.android.di.AppModule
import com.isaev.mytranslator_kmm.android.text_to_speech.di.VoiceToTextModule
import com.isaev.mytranslator_kmm.translate.data.remote.FakeTranslateClient
import com.isaev.mytranslator_kmm.translate.domain.translate.TranslateClient
import com.isaev.mytranslator_kmm.voice_to_text.data.data.FakeVoiceToTextParser
import com.isaev.mytranslator_kmm.voice_to_text.domain.VoiceToTextParser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class, VoiceToTextModule::class)
class VoiceToTextE2E {

    @get:Rule
    val composableRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val permissionRule = GrantPermissionRule.grant(
        Manifest.permission.RECORD_AUDIO
    )

    @Inject
    lateinit var fakeVoiceToTextParser: VoiceToTextParser

    @Inject
    lateinit var fakeClient: TranslateClient

    @Before
    fun before() {
        hiltRule.inject()
    }

    @Test
    fun recordAndTranslate() = runBlocking<Unit> {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val parser = fakeVoiceToTextParser as FakeVoiceToTextParser
        val client = fakeClient as FakeTranslateClient

        composableRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composableRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composableRule
            .onNodeWithContentDescription(context.getString(R.string.stop_recording))
            .performClick()

        composableRule
            .onNodeWithText(parser.voiceToTextResult)
            .assertIsDisplayed()

        composableRule
            .onNodeWithContentDescription(context.getString(R.string.apply))
            .performClick()

        composableRule
            .onNodeWithText(parser.voiceToTextResult)
            .assertIsDisplayed()

        composableRule
            .onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
            .performClick()

        composableRule
            .onNodeWithText(client.text)
            .assertIsDisplayed()
    }

}