package com.example.macbook.myapplication.camera

import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.util.Log
import com.example.macbook.myapplication.util.setOptimalPreviewSize
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.io.IOException
import java.lang.Exception
import java.util.logging.Logger

@Suppress("DEPRECATION")
class CameraV1HelperImpl: CameraHelper {

    private lateinit var camera: Camera
    private lateinit var backCameraInfo: Camera.CameraInfo
    private lateinit var surfaceTexture: SurfaceTexture
    private var width: Int = 0
    private var height: Int = 0

    private val cameraPreviewSubject = PublishSubject.create<ByteArray>()


    private var istextureReady = false

    private val backCamera: Pair<Camera.CameraInfo, Int> = getBackCamera()


    private fun getBackCamera(): Pair<Camera.CameraInfo, Int> {
        val cameraInfo = Camera.CameraInfo()
        val numberOfCameras = Camera.getNumberOfCameras()

        for (i in 0 until numberOfCameras) {
            Camera.getCameraInfo(i, cameraInfo)
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return Pair<Camera.CameraInfo, Int>(
                    cameraInfo,
                    Integer.valueOf(i)
                )
            }

        }
        throw Exception("Not back camera found")
    }

    private fun cameraDisplayRotation() {
        val displayOrientation = (backCameraInfo.orientation - 0 + 360) % 360
        camera.setDisplayOrientation(displayOrientation)
    }

    override fun attachSurfaceTexture(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
        this.surfaceTexture = surfaceTexture
        this.width = width
        this.height = height
        istextureReady = true
    }

    override fun startCamera() {
        if (!istextureReady) {
            return
        }
        backCameraInfo = backCamera.first
        camera = Camera.open(backCamera.second)
        cameraDisplayRotation()
        camera.setPreviewCallback { data, camera ->
            cameraPreviewSubject.onNext(
                data
            )
        }

        val params = camera.parameters
        params.setOptimalPreviewSize(width, height)
        params.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
        camera.parameters = params
        Log.e("DAS", "WW" + width + "HH " + height)

        try {
            camera.setPreviewTexture(surfaceTexture)
            camera.startPreview()
        } catch (ioe: IOException) {

        }
    }

    override fun stopCamera() {
        camera.stopPreview()
        camera.release()
    }

    override fun cameraFrames(): Observable<ByteArray>{
        return cameraPreviewSubject
    }

}