package com.example.macbook.myapplication

import android.graphics.SurfaceTexture
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.TextureView
import com.example.macbook.myapplication.camera.CameraHelper
import com.example.macbook.myapplication.camera.CameraV1HelperImpl
import com.example.macbook.myapplication.util.FirebaseOCRImpl
import com.example.macbook.myapplication.util.applyWindowInsets
import com.example.macbook.myapplication.util.bottomMargin
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    private lateinit var cameraHelper:CameraHelper
    private lateinit var firebaseOCRImpl: FirebaseOCRImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraHelper = CameraV1HelperImpl(this)
        firebaseOCRImpl = FirebaseOCRImpl()
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
            }

        }

        contentView?.applyWindowInsets {
            btn_record_text.bottomMargin += it.systemWindowInsetBottom
        }

        firebaseOCRImpl.resultSubject.subscribe {
            toast(it)
        }
        btn_record_text.setOnClickListener {
            cameraHelper.cameraFrames().firstElement().subscribe {
                firebaseOCRImpl.accept(it)
            }
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
