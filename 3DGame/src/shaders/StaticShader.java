package shaders;

import javax.vecmath.Matrix4f;

import entities.Camera;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/vShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fShader.txt";
	
	private int location_transformationMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	
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
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.LoadMatrix(location_transformationMatrix, matrix);
	}
	
    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.LoadMatrix(location_viewMatrix, viewMatrix);
    }
	
	public void loadProjeciontMatrix(Matrix4f matrix) {
		super.LoadMatrix(location_projectionMatrix, matrix);
	}
}
