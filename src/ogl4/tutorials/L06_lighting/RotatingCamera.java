/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import ogl4.tutorials.L06_lighting.Camera;
import ogl4.tutorials.L06_lighting.Camera;
import org.joml.Matrix4f;

/**
 *
 * @author djordje
 */
public class RotatingCamera extends Camera 
{
    private double currentAngle;
    private double angleChange;
    private int angle;
    
    public RotatingCamera()
    {
        projection.perspective((float)Math.toRadians(45.0), 1.0f, 0.1f, 20.0f);
        view.identity()
           .translate(0, -2, -10.0f).rotate((float)Math.toRadians(40), 1, 0, 0).rotate((float)Math.toRadians(-30), 0, 1, 0); 
    }
    
    @Override
    public synchronized void update()
    {
//       currentAngle += angleChange;
//       view.identity()
//           .translate(0, 0, -4.0f)
//           .rotate((float)Math.toRadians(currentAngle), 0, 1, 0);
//        view.identity()
//           .translate(0, 0, -10.0f)
//           .rotate((float)Math.toRadians(30), 1, 0, 0)    
//           .rotate((float)Math.toRadians(angle++), 0, 1, 0);
    }
    
    public synchronized void advance(float amount)
    {
        angleChange = amount;    
    }
    
}
