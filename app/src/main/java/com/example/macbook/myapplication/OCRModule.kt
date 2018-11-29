package com.example.macbook.myapplication

import android.speech.tts.TextToSpeech
import com.example.macbook.myapplication.camera.CameraHelper
import com.example.macbook.myapplication.camera.CameraV1HelperImpl
import com.example.macbook.myapplication.ocr.FirebaseOCRImpl
import com.example.macbook.myapplication.ocr.OCR
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import java.util.*

val ocrModule = module {

    single {
        TextToSpeech(androidContext()) {
            get<TextToSpeech>().language = Locale.US
        }
    }

    single<OCR> { FirebaseOCRImpl(get()) }

    single<CameraHelper> { CameraV1HelperImpl(androidContext()) }

}