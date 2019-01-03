/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import com.jogamp.opengl.GL4;
import ogl4.shader.ShaderProgramActivation;

/**
 *
 * @author emanu
 */
public class MyActivation extends ShaderProgramActivation {

    private final Camera camera;
    private final Light light;
    private int MVPtransformLoc = -1;
    private int LightSpaceTransformLoc = -1;
    private int LightPositionLoc = -1;
    private float[] transformArray = new float[16];
    private float[] lightPosArray = new float[3];
    
    private boolean isLightMVP = false;
    
    public MyActivation(Camera c, Light l) {
        camera = c;
        light = l;
    }

    @Override
    protected void initialize(GL4 gl) {
        MVPtransformLoc = program.getUniformLocation("MVPTransform");
        LightSpaceTransformLoc = program.getUniformLocation("LightSpaceTransform");
        LightPositionLoc = program.getUniformLocation("LightPosition");
    }

    @Override
    protected void activateInternal(GL4 gl) {
        if(isLightMVP)
            light.GetViewProjection().get(transformArray);
        else
            camera.GetViewProjection().get(transformArray);
        
        gl.glUniformMatrix4fv(MVPtransformLoc, 1, false, transformArray, 0);

        light.GetViewProjection().get(transformArray);
        gl.glUniformMatrix4fv(LightSpaceTransformLoc, 1, false, transformArray, 0);

        lightPosArray[0] = light.getPosition().x;
        lightPosArray[1] = light.getPosition().y;
        lightPosArray[2] = light.getPosition().z;
        gl.glUniform3fv(LightPositionLoc, 1, lightPosArray, 0);
    }
    
    public void setLightMVP(){
        isLightMVP = true;
    }
    
    public void setCameraMVP(){
        isLightMVP = false;
    }
    
}
