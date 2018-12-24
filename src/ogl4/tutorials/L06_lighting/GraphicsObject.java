/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import ogl4.tutorials.L06_lighting.Camera;
import com.jogamp.opengl.GL4;
import ogl4.tutorials.L06_lighting.Camera;
import org.joml.Matrix4f;
import ogl4.shader.ShaderProgram;

/**
 *
 * @author djordje
 */
public abstract class GraphicsObject 
{
    protected ShaderProgram shaderProgram;
    protected Matrix4f transform = new Matrix4f();
    public abstract void init(GL4 gl);
    public abstract void render(GL4 gl, Camera c);
    public abstract void destroy(GL4 gl);
    public void update() { }
    
    public Matrix4f GetTransform()
    {
        return transform;
    }
    
    public void setShaderProgram(ShaderProgram sp)
    {
        shaderProgram = sp;
    }
    
    public ShaderProgram getShaderProgram()
    {
        return shaderProgram;
    }
}
