package com.example.macbook.myapplication

import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import com.example.macbook.myapplication.camera.CameraHelper
import com.example.macbook.myapplication.camera.CameraV1HelperImpl
import com.example.macbook.myapplication.util.applyWindowInsets
import com.example.macbook.myapplication.util.bottomMargin
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.contentView

class MainActivity : AppCompatActivity() {
    private lateinit var cameraHelper:CameraHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cameraHelper = CameraV1HelperImpl()
        textureView.surfaceTextureListener = object: TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {

            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
                return true
            }

            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                cameraHelper.attachSurfaceTexture(surface, width, height)
                cameraHelper.startCamera()
            }

        }

        contentView?.applyWindowInsets {
            btn_record_text.bottomMargin += it.systemWindowInsetBottom
        }
    }


    override fun onStart() {
        super.onStart()
        cameraHelper.startCamera()
    }

    override fun onStop() {
        super.onStop()
        cameraHelper.stopCamera()
    }

}
