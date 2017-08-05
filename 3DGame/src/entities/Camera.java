package entities;

import org.lwjgl.glfw.GLFWKeyCallback;

import engine.KeyHandler;

import static org.lwjgl.glfw.GLFW.*;
import javax.vecmath.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0,0,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {}
	
	public Camera(Vector3f initPos, float initPitch, float initYaw, float initRoll) {
		position = initPos;
		pitch = initPitch;
		yaw = initYaw;
		roll = initRoll;
	}
	public void move(){
		if (KeyHandler.isKeyDown(GLFW_KEY_UP))
			increasePitch(0.01f);
		else if (KeyHandler.isKeyDown(GLFW_KEY_DOWN))
			increasePitch(-0.01f);
		else if (KeyHandler.isKeyDown(GLFW_KEY_LEFT))
			yaw+= 0.01f;
		else if (KeyHandler.isKeyDown(GLFW_KEY_RIGHT))
			yaw += -0.01f;
		else if (KeyHandler.isKeyDown(GLFW_KEY_A))
			increasePosition(-0.001f,0,0);
		else if (KeyHandler.isKeyDown(GLFW_KEY_D))
			increasePosition(0.001f,0,0);
		else if (KeyHandler.isKeyDown(GLFW_KEY_W))
			increasePosition(0,0,-0.001f);
		else if (KeyHandler.isKeyDown(GLFW_KEY_S))
			increasePosition(0,0,0.001f);
	}
	
	public void increasePitch(float dpitch) {
		pitch += dpitch;
	}
	public void increasePosition(float dx, float dy, float dz) {
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}
	public Vector3f getPosition() {
		return position;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}
}
