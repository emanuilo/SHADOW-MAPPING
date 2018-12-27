/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 *
 * @author djordje
 */
public class Light extends SceneObject
{
    private Matrix4f projection = new Matrix4f();
    private Matrix4f view = new Matrix4f();
    
    private float nearPlane = 0.1f;
    private float farPlane = 20.0f;
    
    public Light() {
        projection.ortho(-10.0f, 10.0f, -10.0f, 10.0f, nearPlane, farPlane);
        view.lookAt(new Vector3f(position.x, position.y, position.z), 
                    new Vector3f(5, 5, 5), 
                    new Vector3f(0, 1, 0));
    }
    
    public Light(float x, float y, float z)
    {
        position.set(x, y, z);
        
//        projection.ortho(-10.0f, 10.0f, -10.0f, 10.0f, nearPlane, farPlane);
//        projection.perspective((float)Math.toRadians(60.0), 1.0f, 0.1f, 20.0f);
//        view.lookAt(new Vector3f(position.x, position.y, position.z), 
//                    new Vector3f(-1, 3, 1), 
//                    new Vector3f(0, 1, 0));
        
        
//        projection.perspective((float)Math.toRadians(45.0), 1.0f, 0.1f, 20.0f);
        projection.ortho(-10.0f, 10.0f, -10.0f, 10.0f, nearPlane, farPlane);
        view.identity()
           .translate(0, -2, -10.0f).rotate((float)Math.toRadians(40), 1, 0, 0).rotate((float)Math.toRadians(60), 0, 1, 0); 
    }
    
    @Override
    public void update() 
    {
//       double angle = (System.currentTimeMillis() % 5000)*360.0/5000.0;
//       position.x = (float)(5 * Math.cos( Math.toRadians(angle) ));
//       position.z = (float)(5 * Math.sin( Math.toRadians(angle) ));   
    }   
    
    public synchronized Matrix4f GetViewProjection()
    {
        return new Matrix4f().mul(projection).mul(view);
    }
}
