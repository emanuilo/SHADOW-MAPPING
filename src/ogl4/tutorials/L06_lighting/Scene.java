/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import com.jogamp.opengl.GL;
import ogl4.tutorials.L06_lighting.Camera;
import java.util.ArrayList;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import ogl4.shader.ShaderProgramWrapper;
import ogl4.tutorials.L06_lighting.Camera;

/**
 *
 * @author djordje
 */
public class Scene {

    protected Camera camera;
    protected Light light;
    protected ArrayList<GraphicsObject> graphicsObjects = new ArrayList<>();

    public void SetLight(Light l) {
        light = l;
    }

    public Light GetLight() {
        return light;
    }

    public void SetCamera(Camera c) {
        camera = c;
    }

    public Camera GetCamera() {
        return camera;
    }

    public void Add(GraphicsObject go) {
        graphicsObjects.add(go);
    }

    public void update() {
        camera.update();
        light.update();
        for (GraphicsObject go : graphicsObjects) {
            go.update();
        }
    }

    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4bc();

        for (GraphicsObject go : graphicsObjects) {
            go.render(gl, camera);
        }
    }

    public void destroy(GL4 gl4) {
        for (GraphicsObject go : graphicsObjects) {
            go.destroy(gl4);
        }
    }

    public void changeShaders(ShaderProgramWrapper shader) {
        for (GraphicsObject go : graphicsObjects) {
            go.setShaderProgram(shader.GetShaderProgram());
        }
    }
}
