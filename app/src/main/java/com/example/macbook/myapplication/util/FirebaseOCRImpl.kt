package com.example.macbook.myapplication.util

import android.view.Surface
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

class FirebaseOCRImpl() : Consumer<ByteArray> {
    override fun accept(byteArray: ByteArray) {

        val image = FirebaseVisionImage.fromByteArray(byteArray, metadata)

        val result = detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                resultSubject.onNext(firebaseVisionText.text)
            }
            .addOnFailureListener {
                // Task failed with an exception
                // ...
            }

    }

    val resultSubject:PublishSubject<String> = PublishSubject.create()

    val metadata = FirebaseVisionImageMetadata.Builder()
        .setWidth(1280)   // 480x360 is typically sufficient for
        .setHeight(720)  // image recognition
        .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
        .setRotation(Surface.ROTATION_90)
        .build()

    val detector = FirebaseVision.getInstance()
        .onDeviceTextRecognizer


}
