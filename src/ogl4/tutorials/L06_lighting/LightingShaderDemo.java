

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ogl4.tutorials.L06_lighting;

import ogl4.tutorials.L06_lighting.Camera;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;
import ogl4.tutorials.L06_lighting.Camera;
 
public class LightingShaderDemo 
{
    private static final int WINDOW_WIDTH = 600;  // width of the drawable
    private static final int WINDOW_HEIGHT = 400; // height of the drawable
    private static final int FPS = 60; // animator's target frames per second
    
    private static String MainViewTitle = "Lighting, MainView";  // window's title
    private static String ThirdPersonViewTitle = "Lighting, ThirdPersonView, ";  // window's title

    public static Camera mainCamera = new RotatingCamera();
    public static Light mainLight = new Light(10, 15, 5);
    
    /** Constructor */
   public LightingShaderDemo() {}
 
   
   private static void createWindow(GLCapabilities caps, String title, GLView view, int width, int height, int x, int y)
   {
       // Create the OpenGL rendering canvas
       GLWindow mainWindow = GLWindow.create(caps);
 

 
       // Create a animator that drives canvas' display() at the specified FPS.
       final FPSAnimator animator = new FPSAnimator(mainWindow, FPS, true);
 
       mainWindow.addWindowListener(new WindowAdapter() {
          @Override
          public void windowDestroyNotify(WindowEvent arg0) {
             // Use a dedicate thread to run the stop() to ensure that the
             // animator stops before program exits.
             new Thread() {
                @Override
                public void run() {
                   animator.stop(); // stop the animator loop
                   System.exit(0);
                }
             }.start();
          };
       });
 
       mainWindow.addGLEventListener(view);
       mainWindow.addKeyListener(view);
       mainWindow.setSize(width, height);
       mainWindow.setPosition(x, y);
              
       mainWindow.setTitle(title);
       mainWindow.setVisible(true);
       animator.start();   
   }   
   
   /** The entry main() method */
   public static void main(String[] args) 
   {
       // Get the default OpenGL profile, reflecting the best for your running platform
       GLProfile glp = GLProfile.getDefault();
       
       System.out.println(glp.getGLImplBaseClassName());
       System.out.println(glp.getImplName());
       System.out.println(glp.getName());
       System.out.println(glp.hasGLSL());
       
       // Specifies a set of OpenGL capabilities, based on your profile.
       GLCapabilities caps = new GLCapabilities(glp);
       
       caps.setAlphaBits(8);
       caps.setDepthBits(24);
       System.out.println(caps);
       
//       LightingShaderGouraud shaderGouraud1 = new LightingShaderGouraud();
//       LightingShaderGouraud shaderGouraud2 = new LightingShaderGouraud();
//       LightingShaderPhong shaderPhong = new LightingShaderPhong();
//       LightingShaderPhongSpecular shaderPhongSpecular = new LightingShaderPhongSpecular();
       
       createWindow(caps, MainViewTitle, new MyView(), WINDOW_WIDTH, WINDOW_HEIGHT, 30, 100);
//       createWindow(caps, ThirdPersonViewTitle + "Gouraud (2,2)", new ThirdPersonView(shaderGouraud1, 2, 2, false), WINDOW_WIDTH, WINDOW_HEIGHT, 50+WINDOW_WIDTH, 100);
//       createWindow(caps, ThirdPersonViewTitle + "Gouraud (4,4)", new ThirdPersonView(shaderGouraud2, 4, 4, false), WINDOW_WIDTH, WINDOW_HEIGHT, 70+2*WINDOW_WIDTH, 100);
//
//       createWindow(caps, ThirdPersonViewTitle + "Phong", new ThirdPersonView(shaderPhong, 2, 2, false), WINDOW_WIDTH, WINDOW_HEIGHT, 30, 150 + WINDOW_HEIGHT);
//       createWindow(caps, ThirdPersonViewTitle + "Phong Specular", new ThirdPersonView(shaderPhongSpecular, 2, 2, true), WINDOW_WIDTH, WINDOW_HEIGHT, 50+WINDOW_WIDTH, 150 + WINDOW_HEIGHT);
   }
}