package engine;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

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

		loader = new Loader();
		renderer = new Renderer();
		shader = new StaticShader();

        float[] vertices = 
        {
        		-0.5f, 0.5f,  0f,
        		-0.5f, -0.5f, 0f,
        		0.5f, -0.5f, 0f,
        		0.5f, 0.5f, 0f,
        }; 
        int[] indices = {
        	0, 1, 3,
        	3, 1, 2
        };
        float[] textureCoords = {
        		0, 0,
        		0, 1,
        		1, 1,
        		1, 0
        };
        
        model = loader.loadToVAO(vertices, textureCoords, indices);
        texture = new ModelTexture(loader.loadTexture("C:\\Users\\Wojciech Haase\\Pictures\\bricks.png"));
        texturedModel = new TexturedModel(model, texture);
        
		//glfwSetWindowPos(windowID, 100, 100);
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
			renderer.render(texturedModel);
			shader.stop();
			glfwSwapBuffers(windowID);
			
			if(glfwWindowShouldClose(windowID)) {
				running = false;
			}
		}
		
		close();
	}
	
}
