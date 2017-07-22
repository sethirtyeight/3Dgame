package util;

import static org.lwjgl.opengl.GL11.*; 
import static org.lwjgl.opengl.GL20.*; 

public class ShaderProgram {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public ShaderProgram() {
		programID = glCreateProgram();
	}
	
	public void attachVertexShader(String iv_name) {
		String vertexShaderSource = FileUtil.getFileAsString(iv_name);
		
		vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderID, vertexShaderSource);
		
		glCompileShader(vertexShaderID);
		
		if (glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Error creating vertex shader\n"
					+ glGetShaderInfoLog(vertexShaderID, glGetShaderi(vertexShaderID, GL_INFO_LOG_LENGTH)));
		
		glAttachShader(programID, vertexShaderID);
		
	}
	
	public void attachFragmentShader(String iv_name) {
		String fragmentShaderSource = FileUtil.getFileAsString(iv_name);
		
		fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderID, fragmentShaderSource);
		
		glCompileShader(fragmentShaderID);
		
		if (glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Error creating fragment shader\n"
					+ glGetShaderInfoLog(fragmentShaderID, glGetShaderi(fragmentShaderID, GL_INFO_LOG_LENGTH)));
		
		glAttachShader(programID, fragmentShaderID);
		
	}
	
	public void link() {
		
		glLinkProgram(programID);
		
		if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE)
			throw new RuntimeException("Unable to link shader program");
	}
	
	public void bind() {
		glUseProgram(programID);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void close() {
		unbind();
		
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		
		glDeleteProgram(programID);
	}
	
	public int getID() {
		return programID;
	}
}

	