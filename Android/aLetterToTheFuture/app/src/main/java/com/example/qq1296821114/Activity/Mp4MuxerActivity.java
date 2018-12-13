/*
 *
 *  *
 *  *  * Copyright (C) 2017 ChillingVan
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package com.example.qq1296821114.Activity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glcanvas.BasicTexture;
import com.chillingvan.canvasgl.glcanvas.RawTexture;
import com.chillingvan.canvasgl.glview.texture.GLTexture;
import com.chillingvan.canvasgl.textureFilter.BasicTextureFilter;
import com.chillingvan.canvasgl.textureFilter.TextureFilter;
import com.chillingvan.canvasgl.util.Loggers;
import com.example.qq1296821114.R;
import com.example.qq1296821114.Utils.Helper.MediaPlayerHelper;
import com.example.qq1296821114.View.view.CameraPreviewTextureView;
import com.example.qq1296821114.applib.camera.InstantVideoCamera;
import com.example.qq1296821114.applib.encoder.video.H264Encoder;
import com.example.qq1296821114.applib.muxer.MP4Muxer;
import com.example.qq1296821114.applib.publisher.CameraStreamPublisher;
import com.example.qq1296821114.applib.publisher.StreamPublisher;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Mp4MuxerActivity extends BaseActivity {

    @BindView(R.id.test_camera_button2)
    Button pause;
    @BindView(R.id.test_camera_button)
    Button cameraButton;
    private CameraStreamPublisher streamPublisher;
    private CameraPreviewTextureView cameraPreviewTextureView;
    private InstantVideoCamera instantVideoCamera;
    private Handler handler;
    private HandlerThread handlerThread;
    private TextView outDirTxt;
    private String outputDir;

    private MediaPlayerHelper mediaPlayer = new MediaPlayerHelper();
    private Surface mediaSurface;
    private String TAG = "mytag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outputDir = getExternalFilesDir(null) + "/test_mp4_encode.mp4";
        setContentView(R.layout.activity_mp4_muxer);
        ButterKnife.bind(this);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pause.getText().toString().equals("暂停")) {
                    streamPublisher.pauseCamera();
//                if (streamPublisher.isStart()) {
//                    streamPublisher.closeAll();
//                }
                    pause.setText("继续");
                }
                else
                {
                    streamPublisher.resumeCamera();
                    pause.setText("暂停");
                }
            }
        });
        cameraPreviewTextureView = findViewById(R.id.camera_produce_view);
        cameraPreviewTextureView.setOnDrawListener(new H264Encoder.OnDrawListener() {
            @Override
            public void onGLDraw(ICanvasGL canvasGL, List<GLTexture> producedTextures, List<GLTexture> consumedTextures) {
                GLTexture texture = producedTextures.get(0);
                GLTexture mediaTexture = producedTextures.get(1);
                drawVideoFrame(canvasGL, texture.getSurfaceTexture(), texture.getRawTexture(), mediaTexture);
            }

        });
        outDirTxt = findViewById(R.id.output_dir_txt);
        outDirTxt.setText(outputDir);


        instantVideoCamera = new InstantVideoCamera(Camera.CameraInfo.CAMERA_FACING_FRONT, 1280, 720);
//        instantVideoCamera = new InstantVideoCamera(Camera.CameraInfo.CAMERA_FACING_FRONT, 1280, 720);

        handlerThread = new HandlerThread("StreamPublisherOpen");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//              playMedia();
//              StreamPublisher.StreamPublisherParam streamPublisherParam = new StreamPublisher.StreamPublisherParam(1080, 640, 9500 * 1000, 30, 1, 44100, 19200);
                StreamPublisher.StreamPublisherParam streamPublisherParam = new StreamPublisher.StreamPublisherParam(
                        720, 1280,
                        4800 * 1000, 24, 1, 22050, 32*1000);
                streamPublisherParam.outputFilePath = outputDir;
                streamPublisherParam.setInitialTextureCount(2);
                streamPublisher.prepareEncoder(streamPublisherParam, new H264Encoder.OnDrawListener() {
                    @Override
                    public void onGLDraw(ICanvasGL canvasGL, List<GLTexture> producedTextures, List<GLTexture> consumedTextures) {
                        GLTexture texture = consumedTextures.get(1);
                        GLTexture mediaTexture = consumedTextures.get(0);
                        drawVideoFrame(canvasGL, texture.getSurfaceTexture(), texture.getRawTexture(), mediaTexture);
                        Loggers.i("DEBUG", "gl draw");
                    }

                });
                try {
                    streamPublisher.startPublish();
                } catch (IOException e) {
                    e.printStackTrace();
                    ((TextView) findViewById(R.id.test_camera_button)).setText("START");
                }
            }
        };

        streamPublisher = new CameraStreamPublisher(new MP4Muxer(), cameraPreviewTextureView, instantVideoCamera);
        streamPublisher.setOnSurfacesCreatedListener(new CameraStreamPublisher.OnSurfacesCreatedListener() {
            @Override
            public void onCreated(List<GLTexture> producedTextureList, StreamPublisher streamPublisher) {
                GLTexture texture = producedTextureList.get(1);
                GLTexture mediaTexture = producedTextureList.get(1);
                streamPublisher.addSharedTexture(new GLTexture(mediaTexture.getRawTexture(), mediaTexture.getSurfaceTexture()));
                mediaSurface = new Surface(texture.getSurfaceTexture());
            }
        });
    }

    private void drawVideoFrame(ICanvasGL canvasGL, @Nullable SurfaceTexture outsideSurfaceTexture, @Nullable BasicTexture outsideTexture, GLTexture mediaTexture) {
        // Here you can do video process
        // 此处可以视频处理，例如加水印等等
        TextureFilter textureFilterLT = new BasicTextureFilter();
//        TextureFilter textureFilterRT = new HueFilter(180);
        int width = outsideTexture.getWidth();
        int height = outsideTexture.getHeight();
        canvasGL.drawSurfaceTexture(outsideTexture, outsideSurfaceTexture, 0, 0, width, height, textureFilterLT);
//        canvasGL.drawSurfaceTexture(outsideTexture, outsideSurfaceTexture, 0, height / 2, width / 2, height, textureFilterRT);

//        SurfaceTexture mediaSurfaceTexture = mediaTexture.getSurfaceTexture();
//        RawTexture mediaRawTexture = mediaTexture.getRawTexture();
//        mediaRawTexture.setIsFlippedVertically(true);
//        canvasGL.drawSurfaceTexture(mediaRawTexture, mediaSurfaceTexture, width / 2, height / 2, width, height);
    }

    @Override
    protected void onResume() {
        super.onResume();
        streamPublisher.resumeCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        streamPublisher.pauseCamera();
        if (streamPublisher.isStart()) {
            streamPublisher.closeAll();
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    private void playMedia() {
        if ((mediaPlayer.isPlaying() || mediaPlayer.isLooping())) {
            return;
        }

        mediaPlayer.playMedia(this, mediaSurface);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.release();
        }
        handlerThread.quitSafely();
    }

    public void clickStartTest(View view) {
        TextView textView = (TextView) view;
        if (streamPublisher.isStart()) {
            streamPublisher.closeAll();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            textView.setText("START");
            pause.setVisibility(View.GONE);
        } else {
            streamPublisher.resumeCamera();
            handler.sendEmptyMessage(1);
            textView.setText("STOP");
            pause.setVisibility(View.VISIBLE);
        }
    }
}
