package engine;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.opengl.GL;

import shaders.StaticShader;

import static org.lwjgl.opengl.GL.*;

public class Main implements Runnable {

	public boolean running = true;
	
	private Thread thread;
	private long windowID;
	private int width = 1200, height = 800;
	private Loader loader;
	private Renderer renderer;
	private RawModel model;
	private StaticShader shader;
	
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
        
        model = loader.loadToVAO(vertices, indices);

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
			renderer.render(model);
			shader.stop();
			glfwSwapBuffers(windowID);
			
			if(glfwWindowShouldClose(windowID)) {
				running = false;
			}
		}
		
		close();
	}
	
}
