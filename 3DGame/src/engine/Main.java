package engine;
import util.ShaderProgram;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL.*;

public class Main implements Runnable {

	public boolean running = true;
	
	private Thread thread;
	private long windowID;
	private int width = 1200, height = 800;
	private ShaderProgram shaderProgram;
	
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

		shaderProgram = new ShaderProgram();
		shaderProgram.attachVertexShader("engine/vertex.vs");
		shaderProgram.attachFragmentShader("engine/fragment.vs"); 
		shaderProgram.link(); 
		
		 // Generate and bind a Vertex Array
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // The vertices of our Triangle
        float[] vertices = new float[]
        {
            +0.0f, +0.8f,    // Top coordinate
            -0.8f, -0.8f,    // Bottom-left coordinate
            +0.8f, -0.8f     // Bottom-right coordinate
        };

        // Create a FloatBuffer of vertices
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        // Create a Buffer Object and upload the vertices buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        // Point the buffer at location 0, the location we set
        // inside the vertex shader. You can use any location
        // but the locations should match
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glBindVertexArray(0);


		//glfwSetWindowPos(windowID, 100, 100);
		//glfwShowWindow(windowID);
	}
	
	public void update() {
		glfwPollEvents();
	}
	
	public void close() {
        // Destroy the window 
        glfwDestroyWindow(windowID); 
        glfwTerminate(); 	
	}
	
	public void render() {
	 
		glfwSwapBuffers(windowID);
	}
	
	@Override
	public void run() {
		init();
		
		while(running) {
			update();
			render();
			
			if(glfwWindowShouldClose(windowID)) {
				running = false;
			}
		}
		
		close();
	}
	
}
