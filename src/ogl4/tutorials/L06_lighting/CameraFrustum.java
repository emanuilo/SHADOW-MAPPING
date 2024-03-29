/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import ogl4.tutorials.L06_lighting.Camera;
import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.jogamp.opengl.GL4;
import ogl4.tutorials.L06_lighting.Camera;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/**
 *
 * @author djordje
 */
public class CameraFrustum extends GraphicsObject
{
    private int vertexArrayID;
    private int vertexBufferID;
    private int vertexIndexBufferID;
    
    public CameraFrustum()
    {
    }

    @Override
    public void init(GL4 gl) 
    {
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGenVertexArrays(1, intBuffer );
        vertexArrayID = intBuffer.get(0);
       
        gl.glBindVertexArray(vertexArrayID);
       
       float []vertexData = 
       {
           -1.0f, -1.0f, -1.0f,
           -1.0f, -1.0f, 1.0f,
           1.0f, -1.0f, 1.0f,
           1.0f, -1.0f, -1.0f,
           -1.0f, 1.0f, -1.0f,
           -1.0f, 1.0f, 1.0f,
           1.0f, 1.0f, 1.0f,
           1.0f, 1.0f, -1.0f
       };
              
       int []indices = new int[] { 0, 1, 1, 2, 2, 3, 3, 0, 4, 5, 5, 6, 6, 7, 7, 4, 0, 4, 1, 5, 2, 6, 3, 7 };
       
       
       FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertexData, 0);
       IntBuffer vertexIndexBuffer = Buffers.newDirectIntBuffer(indices, 0);
       
       intBuffer.rewind();
       gl.glGenBuffers(1, intBuffer);
       vertexBufferID = intBuffer.get(0);
       gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexBufferID);
       gl.glBufferData(GL4.GL_ARRAY_BUFFER, vertexData.length * Float.BYTES, vertexBuffer, GL4.GL_STATIC_DRAW);
       gl.glEnableVertexAttribArray(0);
       gl.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, 0, 0);   
                     
       intBuffer.rewind();
       gl.glGenBuffers(1, intBuffer);
       vertexIndexBufferID = intBuffer.get(0);
       gl.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, vertexIndexBufferID);
       gl.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, indices.length * Integer.BYTES, vertexIndexBuffer, GL4.GL_STATIC_DRAW);
    }

    @Override
    public void render(GL4 gl, Camera camera) 
    {
        if( shaderProgram == null )
            return;
       
        gl.glBindVertexArray(vertexArrayID);      
        
        shaderProgram.activate(gl);
       
        gl.glDrawElements(GL4.GL_LINES, 24, GL4.GL_UNSIGNED_INT, 0);

        gl.glBindVertexArray(0);
    }

    @Override
    public void destroy(GL4 gl) 
    {
       IntBuffer buffer = Buffers.newDirectIntBuffer(2);
       buffer.put(vertexBufferID);
       buffer.put(vertexIndexBufferID);
       buffer.rewind();
       gl.glDeleteBuffers(2, buffer);
       
       buffer.rewind();
       buffer.put(vertexArrayID);
       buffer.rewind();
       gl.glDeleteVertexArrays(1, buffer);       
    }
    
    public void update() 
    {
    }
}
