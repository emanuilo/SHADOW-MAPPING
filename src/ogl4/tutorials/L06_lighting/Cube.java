/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

//import ogl4.tutorials.L03_transform.*;
import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 *
 * @author djordje
 */
public class Cube extends GraphicsObject {

    private int vertexArrayID;
    private int vertexBufferID;
    private int colorBufferID;
    private int normalBufferID;
    private int vertexIndexBufferID;
    private float[] transformArray = new float[16];
    private Vector3f rotate = new Vector3f(0, 0, 0);
    private float rotateAngle = 0;
    private Vector3f scale = new Vector3f(1, 1, 1);
    private Vector3f translate = new Vector3f(0, 0, 0);
    
    public Cube() { }

    public void setRotate(float angle, float x, float y, float z) {
        this.rotateAngle = angle;
        this.rotate = new Vector3f(x, y, z);
    }

    public void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z);;
    }

    public void setTranslate(float x, float y, float z) {
        this.translate = new Vector3f(x, y, z);;
    }

    @Override
    public void render(GL4 gl, Camera camera) {
        if (shaderProgram == null) {
            return;
        }

        gl.glBindVertexArray(vertexArrayID);
        shaderProgram.activate(gl);
//        int transformLoc = shaderProgram.getUniformLocation("transform");
//        int lightPositionLoc = shaderProgram.getUniformLocation("lightPos");
//
//        Matrix4f parentTransform = camera.GetViewProjection();
//        Matrix4f finalTransform = parentTransform.scale(1f, 1.2f, 1f);
//        finalTransform.get(transformArray);
//        gl.glUseProgram(shaderProgram.getID());

//        gl.glUniformMatrix4fv(transformLoc, 1, false, transformArray, 0);
//        float[] lightPos = {6.0f, 5.0f, 3.0f};
//        gl.glUniform3fv(lightPositionLoc, 1, lightPos, 0);
        
        Matrix4f transform = new Matrix4f();
        transform.translate(translate);
        transform.scale(scale);
        transform.rotate((float)Math.toRadians(rotateAngle), rotate);

        int modelTransformLoc = shaderProgram.getUniformLocation("ModelTransform");
        gl.glUniformMatrix4fv(modelTransformLoc, 1, false, transform.get(transformArray), 0);
        
        gl.glDrawElements(GL4.GL_QUADS, 24, GL4.GL_UNSIGNED_INT, 0);

        gl.glBindVertexArray(0);
    }

    @Override
    public void init(GL4 gl) {
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGenVertexArrays(1, intBuffer);
        vertexArrayID = intBuffer.get(0);

        gl.glBindVertexArray(vertexArrayID);

        float[] vertexData
                = {
                    -0.5f, -0.5f, -0.5f, //0
                    +0.5f, -0.5f, -0.5f, //1
                    +0.5f, +0.5f, -0.5f, //3
                    -0.5f, +0.5f, -0.5f, //2
                    
                    -0.5f, +0.5f, -0.5f, //2
                    +0.5f, +0.5f, -0.5f, //3
                    +0.5f, +0.5f, +0.5f, //5
                    -0.5f, +0.5f, +0.5f, //4
                    
                    +0.5f, -0.5f, -0.5f, //1
                    +0.5f, +0.5f, -0.5f, //3
                    +0.5f, +0.5f, +0.5f, //5
                    +0.5f, -0.5f, +0.5f,  //7

                    -0.5f, +0.5f, +0.5f, //4
                    +0.5f, +0.5f, +0.5f, //5
                    +0.5f, -0.5f, +0.5f,  //7
                    -0.5f, -0.5f, +0.5f, //6

                    -0.5f, -0.5f, -0.5f, //0
                    -0.5f, +0.5f, -0.5f, //2
                    -0.5f, +0.5f, +0.5f, //4
                    -0.5f, -0.5f, +0.5f, //6

                    -0.5f, -0.5f, -0.5f, //0
                    +0.5f, -0.5f, -0.5f, //1
                    +0.5f, -0.5f, +0.5f,  //7
                    -0.5f, -0.5f, +0.5f //6
                };

        float[] colorData
                = {
                    1.0f, 0.0f, 0.0f, // 0 red
                    0.0f, 1.0f, 0.0f, // 1 green
                    0.0f, 0.0f, 1.0f, // 2 blue
                    0.0f, 1.0f, 0.0f, // 1 green
                    1.0f, 1.0f, 0.0f, // 3 yellow
                    0.0f, 0.0f, 1.0f, // 2 blue
                    1.0f, 1.0f, 0.0f, // 3 yellow
                    1.0f, 0.0f, 0.0f, // 0 red
                    1.0f, 0.0f, 0.0f, // 0 red
                    0.0f, 1.0f, 0.0f, // 1 green
                    0.0f, 0.0f, 1.0f, // 2 blue
                    0.0f, 1.0f, 0.0f, // 1 green
                    1.0f, 1.0f, 0.0f, // 3 yellow
                    0.0f, 0.0f, 1.0f, // 2 blue
                    1.0f, 1.0f, 0.0f, // 3 yellow
                    1.0f, 0.0f, 0.0f, // 0 red
                    1.0f, 0.0f, 0.0f, // 0 red
                    0.0f, 1.0f, 0.0f, // 1 green
                    0.0f, 0.0f, 1.0f, // 2 blue
                    0.0f, 1.0f, 0.0f, // 1 green
                    1.0f, 1.0f, 0.0f, // 3 yellow
                    0.0f, 0.0f, 1.0f, // 2 blue
                    1.0f, 1.0f, 0.0f, // 3 yellow
                    1.0f, 0.0f, 0.0f // 0 red
                    
//                    0.7f, 0.7f, 0.7f, // 0 red
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    
//                    0.7f, 0.7f, 0.7f, // 0 red
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    
//                    0.7f, 0.7f, 0.7f, // 0 red
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f,
//                    0.7f, 0.7f, 0.7f
                    
                };

        float[] normals = {
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f
        };
        
//        int[] indices = new int[]{0, 1, 3, 2,  2, 3, 5, 4,  1, 3, 5, 7,  4, 5, 7, 6,  0, 2, 4, 6,  0, 1, 7, 6};
        int[] indices = new int[]{0, 1, 2, 3,  4, 5, 6, 7,  8, 9, 10, 11,  12, 13, 14, 15,  16, 17, 18, 19,  20, 21, 22, 23};

        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertexData, 0);
        FloatBuffer vertexColorBuffer = Buffers.newDirectFloatBuffer(colorData, 0);
        FloatBuffer vertexNormalBuffer = Buffers.newDirectFloatBuffer(normals, 0);
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
        colorBufferID = intBuffer.get(0);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, colorBufferID);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, colorData.length * Float.BYTES, vertexColorBuffer, GL4.GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL4.GL_FLOAT, false, 0, 0);
        
        intBuffer.rewind();
        gl.glGenBuffers(1, intBuffer);
        normalBufferID = intBuffer.get(0);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, normalBufferID);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, normals.length * Float.BYTES, vertexNormalBuffer, GL4.GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(2);
        gl.glVertexAttribPointer(2, 3, GL4.GL_FLOAT, false, 0, 0);

        intBuffer.rewind();
        gl.glGenBuffers(1, intBuffer);
        vertexIndexBufferID = intBuffer.get(0);
        gl.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, vertexIndexBufferID);
        gl.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, indices.length * Integer.BYTES, vertexIndexBuffer, GL4.GL_STATIC_DRAW);
    }

    @Override
    public void destroy(GL4 gl) {
        IntBuffer buffer = Buffers.newDirectIntBuffer(3);
        buffer.put(vertexBufferID);
        buffer.put(colorBufferID);
        buffer.put(vertexIndexBufferID);
        buffer.rewind();
        gl.glDeleteBuffers(3, buffer);

        buffer.rewind();
        buffer.put(vertexArrayID);
        buffer.rewind();
        gl.glDeleteVertexArrays(1, buffer);
    }


}
