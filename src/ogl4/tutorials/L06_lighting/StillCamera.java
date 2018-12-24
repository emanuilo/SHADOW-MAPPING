/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogl4.tutorials.L06_lighting;

import ogl4.tutorials.L06_lighting.Camera;
import ogl4.tutorials.L06_lighting.Camera;

/**
 *
 * @author djordje
 */
public class StillCamera extends Camera
{
    public StillCamera()
    {
        projection.perspective((float)Math.toRadians(45.0), 1.0f, 0.1f, 100.0f);
        
        view.translate(0, 0, -20.0f)
           .rotate((float)Math.toRadians(30), 1, 0, 0)
           .rotate((float)Math.toRadians(45), 0, 1, 0);
    }    
}
