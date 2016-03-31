package PianoRoll;

import java.awt.Color;
import java.util.ArrayList;

public class ColoredPoint {
	
	private int xPosition;
	private int yPosition;
	private final Color basicColor = Color.BLACK;
	private int currentColor;
	private ArrayList<Color> pointColors;
	private int currentIndex;
	private float opacity;
	
	public ColoredPoint(int xPosition, int yPosition, int current, float opacity){
		this.setxPosition(xPosition);
		this.setyPosition(yPosition);
		this.setCurrentColor(current);
		this.setCurrentIndex(current);
		this.setOpacity(opacity);
		pointColors = new ArrayList<Color>();
		pointColors.add(Color.RED);
		pointColors.add(Color.RED);
		pointColors.add(Color.MAGENTA);
		pointColors.add(Color.MAGENTA);
		pointColors.add(Color.MAGENTA);
		pointColors.add(Color.MAGENTA);
		pointColors.add(Color.MAGENTA);
		pointColors.add(Color.MAGENTA);
		pointColors.add(Color.MAGENTA);
		pointColors.add(Color.ORANGE);
		pointColors.add(Color.ORANGE);
		pointColors.add(Color.ORANGE);
		pointColors.add(Color.ORANGE);
		pointColors.add(Color.ORANGE);
		pointColors.add(Color.ORANGE);
		pointColors.add(Color.ORANGE);
		pointColors.add(Color.ORANGE);
		pointColors.add(Color.YELLOW);
		pointColors.add(Color.YELLOW);
		pointColors.add(Color.YELLOW);
		pointColors.add(Color.YELLOW);
		pointColors.add(Color.YELLOW);
		pointColors.add(Color.YELLOW);
		pointColors.add(Color.YELLOW);
		pointColors.add(Color.YELLOW);
		pointColors.add(Color.YELLOW);
		pointColors.add(Color.YELLOW);
		pointColors.add(Color.GREEN);
		pointColors.add(Color.GREEN);
		pointColors.add(Color.GREEN);
		pointColors.add(Color.GREEN);
		pointColors.add(Color.GREEN);
		pointColors.add(Color.GREEN);
		pointColors.add(Color.BLACK);
	}
	
	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public int getxPosition() {
		return xPosition;
	}
	
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	
	public int getyPosition() {
		return yPosition;
	}
	
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	public Color getBasicColor() {
		return basicColor;
	}
	
	public void changeColor() {
		Color now = pointColors.get(currentColor);
		if(currentColor < pointColors.size() -1){
			currentColor++;
			currentIndex++;
			if(opacity > 0)
				opacity -= 0.03;
		}
	}
	
	public Color getCurrentColor() {
		return pointColors.get(currentColor);	
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public void setCurrentColor(int currentColor) {
		this.currentColor = currentColor;
	}

	public ArrayList<Color> getPointColors() {
		return pointColors;
	}

}
