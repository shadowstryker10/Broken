package ld.math;

import org.lwjgl.util.vector.Vector2f;

public class MathUtil
{
	public static float getAngle(float x1, float y1, float x2, float y2)
	{
		float angle;

		float deltaX = x2 - x1;
		float deltaY = y2 - y1;
		
		angle = (float) (Math.atan2(deltaY, deltaX));
		return angle;
	}
}
