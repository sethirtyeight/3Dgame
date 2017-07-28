package shaders;

import javax.vecmath.Matrix4f;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/vShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fShader.txt";
	
	private int location_transformationMatrix;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttributes(0, "position");
		super.bindAttributes(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.LoadMatrix(location_transformationMatrix, matrix);
	}

}
