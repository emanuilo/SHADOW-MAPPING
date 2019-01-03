/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import ogl4.shader.FragmentShader;
import ogl4.shader.ShaderProgram;
import ogl4.shader.ShaderProgramWrapper;
import ogl4.shader.VertexShader;

/**
 *
 * @author emanu
 */
public class MyShader extends ShaderProgramWrapper{

    @Override
    protected void setupSources() {
        String shadersPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/shaders/";
        
        vertexShader = new VertexShader("LightingGouraud VS");
        fragmentShader = new FragmentShader("LightingGouraud FS");
        shaderProgram = new ShaderProgram("LightingGouraud");
     
        try
        {
            vertexShader.readSource( Paths.get(shadersPath+"MyShader_VS.shader") );
            fragmentShader.readSource( Paths.get(shadersPath+"MyShader_FS.shader") );
        }
        catch(IOException e)
        {
            System.out.println(e);
            return;
        }

        
        uniforms = new ArrayList<>();
        uniforms.add("MVPTransform");
        uniforms.add("LightSpaceTransform");
        uniforms.add("NormalTransform");
        uniforms.add("ModelTransform");
        uniforms.add("LightPosition");
    }
    
}
