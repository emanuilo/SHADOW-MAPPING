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
public class ShadowMapQuad extends GraphicsObject {

    private int dimension = 10;
    private int vertexArrayID;
    private int vertexBufferID;
    private int textureCoordsBufferID;
    private int normalBufferID;
    private int vertexIndexBufferID;
    private float[] transformArray = new float[16];

    public ShadowMapQuad() {
    }

    public ShadowMapQuad(int dimension) {
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

        gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
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
                    -1.0f, 1.0f, 0.0f,
                    -1.0f, -1.0f, 0.0f,
                    1.0f, 1.0f, 0.0f,
                    1.0f, -1.0f, 0.0f
                };

        float[] textureCoords
                = {
                    0.0f, 1.0f,
                    0.0f, 0.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f
                };

        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertexData, 0);
        FloatBuffer textureCoordsBuffer = Buffers.newDirectFloatBuffer(textureCoords, 0);

        intBuffer.rewind();
        gl.glGenBuffers(1, intBuffer);
        vertexBufferID = intBuffer.get(0);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexBufferID);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, vertexData.length * Float.BYTES, vertexBuffer, GL4.GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, 0, 0);

        intBuffer.rewind();
        gl.glGenBuffers(1, intBuffer);
        textureCoordsBufferID = intBuffer.get(0);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, textureCoordsBufferID);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, textureCoords.length * Float.BYTES, textureCoordsBuffer, GL4.GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, 2, GL4.GL_FLOAT, false, 0, 0);
    }

    @Override
    public void destroy(GL4 gl) {
        IntBuffer buffer = Buffers.newDirectIntBuffer(3);
        buffer.put(vertexBufferID);
        buffer.put(textureCoordsBufferID);
        buffer.rewind();
        gl.glDeleteBuffers(3, buffer);

        buffer.rewind();
        buffer.put(vertexArrayID);
        buffer.rewind();
        gl.glDeleteVertexArrays(1, buffer);
    }

}
