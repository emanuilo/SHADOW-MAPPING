/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;


import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

/**
 *
 * @author djordje
 */
public abstract class GLView implements GLEventListener, KeyListener
{
    protected Scene scene;
    
    /** Render the shape (triangle) */
    public void render(GLAutoDrawable drawable) 
    {
        GL4 gl = drawable.getGL().getGL4bc();

        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_STENCIL_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);

        scene.display(drawable);
    }
   
    /** Update the rotation angle after each frame refresh */
    private void update() 
    {
        scene.update();
    }
   
   
    @Override
    public void dispose(GLAutoDrawable drawable) 
    {
        scene.destroy(drawable.getGL().getGL4bc());
    }

    @Override
    public void display(GLAutoDrawable drawable) 
    {
       render(drawable);
       update();
    }    
    
    
    
    @Override
    public void keyPressed(com.jogamp.newt.event.KeyEvent e) {
    }

    @Override
    public void keyReleased(com.jogamp.newt.event.KeyEvent e) {
    }
     
}
