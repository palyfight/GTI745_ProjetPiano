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
	
	public ColoredPoint(int xPosition, int yPosition, int current){
		this.setxPosition(xPosition);
		this.setyPosition(yPosition);
		this.setCurrentColor(current);
		this.setCurrentIndex(current);
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
		pointColors.add(Color.BLACK);
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
