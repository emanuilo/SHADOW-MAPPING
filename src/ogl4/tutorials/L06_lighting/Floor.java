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
import java.util.Random;
import org.joml.Matrix4f;

/**
 *
 * @author djordje
 */
public class Floor extends GraphicsObject {

    private int dimension = 10;
    private int vertexArrayID;
    private int vertexBufferID;
    private int colorBufferID;
    private int normalBufferID;
    private int vertexIndexBufferID;
    private float[] transformArray = new float[16];

    public Floor() {
    }

    public Floor(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public void render(GL4 gl, Camera camera) {
        if (shaderProgram == null) {
            return;
        }

        gl.glBindVertexArray(vertexArrayID);
        shaderProgram.activate(gl);
//        int transformLoc = shaderProgram.getUniformLocation("transform");
//        int lightPositionLoc = shaderProgram.getUniformLocation("lightPosition");
//        
//        parentTransform.get(transformArray);
//        gl.glUseProgram(shaderProgram.getID());
//        gl.glUniformMatrix4fv(transformLoc, 1, false, transformArray, 0);
//        float[] lightPos = {6.0f, 5.0f, 3.0f};
//        gl.glUniform3fv(lightPositionLoc, 1, lightPos, 0);
        
        Matrix4f transform = new Matrix4f();
//        transform.rotate((float)Math.toRadians(90), 0, 1, 0);

        int modelTransformLoc = shaderProgram.getUniformLocation("ModelTransform");
        gl.glUniformMatrix4fv(modelTransformLoc, 1, false, transform.get(transformArray), 0);

        gl.glDrawElements(GL4.GL_TRIANGLES, (dimension - 1) * (dimension - 1) * 2 * 3, GL4.GL_UNSIGNED_INT, 0);
        gl.glBindVertexArray(0);
    }

    @Override
    public void init(GL4 gl) {
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGenVertexArrays(1, intBuffer);
        vertexArrayID = intBuffer.get(0);

        gl.glBindVertexArray(vertexArrayID);

//       float []vertexData = 
//       {
//          -0.8f, -0.8f, -0.8f,
//          +0.8f, -0.8f, -0.8f,
//          -0.8f, -0.8f, +0.8f,
//          +0.8f, -0.8f, +0.8f
//       };
        float[] vertexData = new float[dimension * dimension * 3];
//       float []colorData =
//       {
//           0.8f, 0.8f, 0.8f, //grey
//           1.0f, 1.0f, 1.0f,
//           0.9f, 0.9f, 0.9f, 
//           0.85f, 0.85f, 0.85f
//       };

//       int []indices = new int[] { 0, 1, 3, 2 };
        int numberOfElements = dimension * dimension * 3;
        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(numberOfElements);
        FloatBuffer vertexColorBuffer = Buffers.newDirectFloatBuffer(numberOfElements);
        FloatBuffer vertexNormalBuffer = Buffers.newDirectFloatBuffer(numberOfElements);
        IntBuffer vertexIndexBuffer = Buffers.newDirectIntBuffer((dimension - 1) * (dimension - 1) * 2 * 3); //2 triangles per square, 3 indices per triangle

        int half = dimension / 2;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                float x = j - half;
                float y = -0.7f;
                float z = i - half;
                vertexBuffer.put(x).put(y).put(z);
//                float randomColor = (float) (Math.random() * 0.2 + 0.7);
                vertexColorBuffer.put(0.8f).put(0.8f).put(0.8f);
//                vertexColorBuffer.put(randomColor).put(randomColor).put(randomColor);
                vertexNormalBuffer.put(0).put(1).put(0);
            }
        }

        for (int row = 0; row < dimension - 1; row++) {
            for (int col = 0; col < dimension - 1; col++) {
                //first half of the square
                int index1 = dimension * row + col;
                int index2 = dimension * row + col + dimension;
                int index3 = dimension * row + col + dimension + 1;
                int index4 = dimension * row + col + 1;

                vertexIndexBuffer.put(index1).put(index2).put(index3);
                vertexIndexBuffer.put(index1).put(index3).put(index4);
            }
        }

        vertexBuffer.rewind();
        vertexIndexBuffer.rewind();
        vertexColorBuffer.rewind();
        vertexNormalBuffer.rewind();
        
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
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, vertexColorBuffer.capacity() * Float.BYTES, vertexColorBuffer, GL4.GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 3, GL4.GL_FLOAT, false, 0, 0);

        intBuffer.rewind();
        gl.glGenBuffers(1, intBuffer);
        normalBufferID = intBuffer.get(0);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, normalBufferID);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, vertexNormalBuffer.capacity() * Float.BYTES, vertexNormalBuffer, GL4.GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(2);
        gl.glVertexAttribPointer(2, 3, GL4.GL_FLOAT, false, 0, 0);

        intBuffer.rewind();
        gl.glGenBuffers(1, intBuffer);
        vertexIndexBufferID = intBuffer.get(0);
        gl.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, vertexIndexBufferID);
        gl.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, vertexIndexBuffer.capacity() * Integer.BYTES, vertexIndexBuffer, GL4.GL_STATIC_DRAW);

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
