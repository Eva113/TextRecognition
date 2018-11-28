package com.example.macbook.myapplication.camera

import android.graphics.SurfaceTexture
import io.reactivex.Observable

interface CameraHelper {

    fun attachSurfaceTexture(surfaceTexture: SurfaceTexture, width: Int, height: Int)

    fun startCamera()

    fun stopCamera()

    fun cameraFrames(): Observable<ByteArray>

}