package shaders;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/vShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fShader.txt";
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttributes(0, "position");
	}

}
