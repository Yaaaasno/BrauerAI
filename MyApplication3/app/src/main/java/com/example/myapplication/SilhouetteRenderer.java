package com.example.myapplication;

import static androidx.camera.core.processing.util.GLUtils.loadShader;

import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SilhouetteRenderer implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    private SurfaceTexture surfaceTexture;
    private int textureId;
    private int program;
    private final float[] textureMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        textureId = createTexture();
        surfaceTexture = new SurfaceTexture(textureId);
        surfaceTexture.setOnFrameAvailableListener(this);
        setupShader();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Viewport bei Änderung der Oberflächengröße anpassen
        GLES30.glViewport(0, 0, width, height);
    }

    private int createTexture() {
        int[] textures = new int[1];
        GLES30.glGenTextures(1, textures, 0);
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0]);
        // Texture-Parameter setzen
        return textures[0];
    }

    private void setupShader() {
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADER_CODE);
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE);
        program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program, vertexShader);
        GLES30.glAttachShader(program, fragmentShader);
        GLES30.glLinkProgram(program);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        surfaceTexture.updateTexImage();
        surfaceTexture.getTransformMatrix(textureMatrix);
        // Shader-Logik hier fortsetzen
    }

    // Shader-Code für Silhouetten-Effekt
    private static final String FRAGMENT_SHADER_CODE =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTexCoord;\n" +
                    "uniform samplerExternalOES uTexture;\n" +
                    "void main() {\n" +
                    "    vec4 color = texture2D(uTexture, vTexCoord);\n" +
                    "    float luminance = dot(color.rgb, vec3(0.299, 0.587, 0.114));\n" +
                    "    gl_FragColor = (luminance < 0.5) ? vec4(0.0, 0.0, 0.0, 1.0) : vec4(1.0, 1.0, 1.0, 1.0);\n" +
                    "}";
    private static final String VERTEX_SHADER_CODE =
            "#version 300 es\n" +
                    "layout(location = 0) in vec4 aPosition;\n" +
                    "layout(location = 1) in vec2 aTexCoord;\n" +
                    "out vec2 vTexCoord;\n" +
                    "void main() {\n" +
                    "    gl_Position = aPosition;\n" +
                    "    vTexCoord = aTexCoord;\n" +
                    "}";

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {

    }
}