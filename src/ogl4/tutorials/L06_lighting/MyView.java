/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import java.nio.IntBuffer;
import ogl4.shader.ShaderProgramWrapper;

/**
 *
 * @author emanu
 */
public class MyView extends GLView {
    public static final int SHADOW_WIDTH = 1024;
    public static final int SHADOW_HEIGHT = 1024;
    
    protected int depthMapFBOID;
    protected int depthMapID;
    
    private MyActivation myActivation;
    private MyShader myShader;
    private ShadowMappingShader shadowMappingShader;
    private ShadowMappingDebugShader shadowMappingDebugShader;

    
    private ShadowMapQuad shadowMapQuad; //delete
    
    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4bc();

        scene = new Scene();
        scene.SetCamera(LightingShaderDemo.mainCamera);
        scene.SetLight(LightingShaderDemo.mainLight);

        //creation of default shader object for rendering the scene
        myActivation = new MyActivation(scene.GetCamera(), scene.GetLight());
        myShader = new MyShader();
        myShader.buildShader(gl);
        myShader.GetShaderProgram().setActivation(myActivation);

        //creation of shadow mapping shader
        ShadowMappingActivation shadowMappingActivation = new ShadowMappingActivation(scene.GetCamera(), scene.GetLight());
        shadowMappingShader = new ShadowMappingShader();
        shadowMappingShader.buildShader(gl);
        shadowMappingShader.GetShaderProgram().setActivation(shadowMappingActivation);
        
        //creation of debug shader for depth buffer rendering
        ShadowMappingDebugActivation shadowMappingDebugActivation = new ShadowMappingDebugActivation(scene.GetCamera(), scene.GetLight());
        shadowMappingDebugShader = new ShadowMappingDebugShader();
        shadowMappingDebugShader.buildShader(gl);
        shadowMappingDebugShader.GetShaderProgram().setActivation(shadowMappingDebugActivation);
        
        //delete
        shadowMapQuad = new ShadowMapQuad();
        shadowMapQuad.setShaderProgram(shadowMappingDebugShader.GetShaderProgram());
        shadowMapQuad.init(gl);
        
        
        Floor floor = new Floor();
        floor.setShaderProgram(myShader.GetShaderProgram());
        floor.init(gl);
        scene.Add(floor);

        Cube cube1 = new Cube();
        cube1.setShaderProgram(myShader.GetShaderProgram());
        cube1.init(gl);
        cube1.setTranslate(0, 1.5f, 0);
        cube1.setScale(1, 1.5f, 1);
//        cube1.setRotate(180, 0, 1, 0);
        scene.Add(cube1);

        initDepthTexture(gl);
        
        gl.glEnable(GL4.GL_DEPTH_TEST);
        
        int error = gl.glGetError();
        System.out.println("Init: error = " + error);
    }

    public void initDepthTexture(GL4 gl) {
        //creation of secondary frame buffer object in which depth map will be written
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGenFramebuffers(1, intBuffer);
        depthMapFBOID = intBuffer.get(0);
        
        //creation of a texture in which depth buffer will be stored
        intBuffer.rewind();
        gl.glGenTextures(1, intBuffer);
        depthMapID = intBuffer.get(0);
        gl.glBindTexture(GL4.GL_TEXTURE_2D, depthMapID);
        gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, GL4.GL_DEPTH_COMPONENT, 1024, 1024, 0, GL4.GL_DEPTH_COMPONENT, GL4.GL_FLOAT, null);
        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);
        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_REPEAT);
        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_T, GL4.GL_REPEAT);
        
        //attaching the texture to the framebuffer
        gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, depthMapFBOID);
        gl.glFramebufferTexture2D(GL4.GL_FRAMEBUFFER, GL4.GL_DEPTH_ATTACHMENT, GL4.GL_TEXTURE_2D, depthMapID, 0);
        gl.glDrawBuffer(GL4.GL_NONE);
        gl.glReadBuffer(GL4.GL_NONE);
        gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
    }
    
    @Override
    public void render(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4bc();

        //first pass - rendering the shadow map from light point of view
        gl.glViewport(0, 0, MyView.SHADOW_WIDTH, MyView.SHADOW_HEIGHT);
//        gl.glViewport(0, 0, 600, 600);
        gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, depthMapFBOID);
        gl.glClear(GL4.GL_DEPTH_BUFFER_BIT);
        //configure shaders and matrices
        myActivation.setLightMVP();
        changeShaders(shadowMappingShader);
        //render the scene
        scene.display(drawable);
        gl.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0); //return to the default frame buffer
        
//        //second pass
//        gl.glViewport(0, 0, 600, 400);
//        //configure shaders and matrices
//        myActivation.setCameraMVP();
//        changeShaders(myShader);
////        changeShaders(shadowMappingDebugShader); //debug
//        //render the scene
//        gl.glActiveTexture(GL4.GL_TEXTURE0); //?
//        gl.glBindTexture(GL4.GL_TEXTURE_2D, depthMapID);
//        super.render(drawable);
        
       
        //rendering depth buffer quad
        changeShaders(shadowMappingDebugShader); //debug
        gl.glActiveTexture(GL4.GL_TEXTURE0); 
        gl.glBindTexture(GL4.GL_TEXTURE_2D, depthMapID);
        gl.glViewport(0, 0, 600, 400);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_STENCIL_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
        shadowMapQuad.render(gl, LightingShaderDemo.mainCamera);
        

        // rendering basic scene - delete
//        gl.glViewport(0, 0, 600, 400);
//        myActivation.setLightMVP();
////        myActivation.setCameraMVP();
//        changeShaders(myShader);
//        super.render(drawable);
    }
    
    public void changeShaders(ShaderProgramWrapper shader){
        scene.changeShaders(shader);
    }
    
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) { }
    
}
