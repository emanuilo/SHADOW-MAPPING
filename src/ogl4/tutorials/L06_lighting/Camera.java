/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import org.joml.Matrix4f;

/**
 *
 * @author djordje
 */
public class Camera
{
    protected Matrix4f view = new Matrix4f();
    protected Matrix4f projection = new Matrix4f();    
    
    public Camera()
    {
    }
    
    public Matrix4f GetProjection()
    {
        return projection;
    }

    public Matrix4f GetView()
    {
        return view;
    }
    
    public synchronized Matrix4f GetViewProjection()
    {
        return new Matrix4f().mul(projection).mul(view);
    }
    
    public void update()
    {
    }
    
    public void advance(float amount)
    {
    }
}
