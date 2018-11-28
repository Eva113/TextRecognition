package com.example.macbook.myapplication.camera

import android.graphics.SurfaceTexture
import io.reactivex.Observable
import java.nio.ByteBuffer

interface CameraHelper {

    fun attachSurfaceTexture(surfaceTexture: SurfaceTexture, width: Int, height: Int)

    fun startCamera()

    fun stopCamera()

    fun cameraFrames(): Observable<ByteArray>

}