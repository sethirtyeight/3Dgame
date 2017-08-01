package engine;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

import javax.vecmath.Vector3f;

import static org.lwjgl.opengl.GL.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;

public class Main implements Runnable {

	public boolean running = true;
	
	private Thread thread;
	private long windowID;
	private int width = 1200, height = 800;
	private Loader loader;
	private Renderer renderer;
	private RawModel model;
	private StaticShader shader;
	private ModelTexture texture;
	private TexturedModel texturedModel;
	private Entity entity, entity1, entity2, entity3, entity4;
	private Camera camera;
	
	public static void main(String args[]) {
		new Main().start();
	}
	
	public void start() {
		running = true;
		thread = new Thread(this, "EndlessRunner");
		thread.start();
	}
	
	public void init() {
		
		if(!glfwInit()) {
			System.err.println("GLFW initialisation failed");
		}		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		windowID = glfwCreateWindow(width, height, "Game", NULL, NULL);
		
		if (windowID == 0) {
			System.err.println("Couldn't create a window for the game");
		}

		glfwMakeContextCurrent(windowID);   
		GL.createCapabilities();

        float[] vertices1 = 
        {
            -0.5f,0.5f,0,   //front face 0
            -0.5f,-0.5f,0,  //1
            0.5f,-0.5f,0,   //2   
            0.5f,0.5f,0,    //3 
             
            -0.5f,0.5f,1,   //back face 4
            -0.5f,-0.5f,1,  //5
            0.5f,-0.5f,1,   //6
            0.5f,0.5f,1,    //7
             
            0.5f,0.5f,0,    //right face 3
            0.5f,-0.5f,0,   //2
            0.5f,-0.5f,1,   //6
            0.5f,0.5f,1,    //7
             
            -0.5f,0.5f,0,   //left face 0
            -0.5f,-0.5f,0,  //1
            -0.5f,-0.5f,1,  //4
            -0.5f,0.5f,1,   //4
             
            -0.5f,0.5f,1,   //top face 4
            -0.5f,0.5f,0,   //0
            0.5f,0.5f,0,    //3
            0.5f,0.5f,1,	//7
             
            -0.5f,-0.5f,1,  //bottom face 5
            -0.5f,-0.5f,0,  //1
            0.5f,-0.5f,0,   //2
            0.5f,-0.5f,1    //6    		
        }; 
        
        int[] indices1 = {
        	0, 1, 3, //front face
        	3, 1, 2,
        	
        	4, 5, 7, //right face
        	7, 5, 6,
        	
        	4, 6, 5, //back face
        	5, 6, 7,
        	
        	8, 9, 11, //left face
        	11, 9, 10,
        	
        	12, 13, 15, //top face
        	15, 13, 14,
        	
        	16, 17, 19, //bottom face
        	19, 17, 18,
        	
        	20, 21, 23,
        	23, 21, 22
        };
        
        float[] textureCoords1 = {
        		0, 0, 0, 1, 1, 1, 1, 0,
        		0, 0, 0, 1, 1, 1, 1, 0,
        		0, 0, 0, 1, 1, 1, 1, 0,
        		0, 0, 0, 1, 1, 1, 1, 0,
        		0, 0, 0, 1, 1, 1, 1, 0,
        		0, 0, 0, 1, 1, 1, 1, 0,
        		0, 0, 0, 1, 1, 1, 1, 0,
        		0, 0, 0, 1, 1, 1, 1, 0
        };
        float[] vertices = {
        		-0.5f, 0.5f, 0,
        		-0.5f,-0.5f,0,
        		0.5f, -0.5f, 0,
        		0.5f, 0.5f,0
        };
        float[] vertices2 = {
        		-1f, 0.5f, 0,
        		-1f,-0.5f,0,
        		0f, -0.5f, 0,
        		0f, 0.5f,0
        };
        float[] textureCoords = {
        		0,0,0,1,1,1,1,0
        };
        int[] indices = {
        	0,1,3,
        	3,1,2
        };
        
		loader = new Loader();
		shader = new StaticShader();
		renderer = new Renderer(windowID, shader);

        model = loader.loadToVAO(vertices1, textureCoords1, indices1);
        texture = new ModelTexture(loader.loadTexture("C:\\Users\\Wojciech Haase\\Pictures\\bricks.png"));
        texturedModel = new TexturedModel(model, texture);

        entity = new Entity(texturedModel, new Vector3f(2,2,0),0,0,0,0.2f);
        entity1 = new Entity(texturedModel, new Vector3f(-1,-1,0),0,0,0,0.2f);
        entity2 = new Entity(texturedModel, new Vector3f(-3,0,0),0,0,0,0.2f);
        entity3 = new Entity(texturedModel, new Vector3f(1,1,0),0,0,0,0.2f);
        entity4 = new Entity(texturedModel, new Vector3f(-2,-2,0),0,0,0,0.2f);
        
        camera = new Camera();
        
		glfwSetWindowPos(windowID, 250, 100);
		//glfwShowWindow(windowID);
	}
	
	public void update() {
		glfwPollEvents();
	}
	
	public void close() {
		shader.cleanUp();
		loader.cleanUp();
        // Destroy the window 
        glfwDestroyWindow(windowID); 
        glfwTerminate(); 	
	}
	
	@Override
	public void run() {
		init();
		
		while(running) {
			update();
			renderer.prepare();
			
			shader.start();
			//shader.loadViewMatrix(camera);
			renderer.render(entity,shader);
			renderer.render(entity1,shader);
			renderer.render(entity2,shader);
			renderer.render(entity3,shader);
			renderer.render(entity4,shader);
			shader.stop();
			//entity.increasePosition(-0.001f, 0, 0);
			entity.increaseRotation(0.1f, 0.2f, 0);
			entity1.increaseRotation(0.01f, 0.2f, 0.1f);
			entity2.increaseRotation(0.1f, 0.6f, 0);
			entity3.increaseRotation(0.1f, 0.01f, 0.3f);
			entity4.increaseRotation(0.1f, 0.2f, -0.4f);
			
			
			glfwSwapBuffers(windowID);
			
			if(glfwWindowShouldClose(windowID)) {
				running = false;
			}
		}
		
		close();
	}
	
	public long getWindowID() {
		return windowID;
	}
	
}
