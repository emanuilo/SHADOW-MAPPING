/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;

/**
 *
 * @author emanu
 */
public class MyView extends GLView {

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4bc();

        scene = new Scene();
        scene.SetCamera(LightingShaderDemo.mainCamera);
        scene.SetLight(LightingShaderDemo.mainLight);

        MyShader myShader = new MyShader();
        myShader.buildShader(gl);

        MyActivation myActivation = new MyActivation(scene.GetCamera(), scene.GetLight());
        myShader.GetShaderProgram().setActivation(myActivation);

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

        gl.glEnable(GL4.GL_DEPTH_TEST);
        
        int error = gl.glGetError();
        System.out.println("Init: error = " + error);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

}
