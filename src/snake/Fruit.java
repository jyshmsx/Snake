package snake;

import java.awt.Color;

public class Fruit {
    private int xPos;
    private int yPos;
    private Color fruitColor;

    public void setFruit(int xPos, int yPOs,Color fruitColor)
    {
        this.xPos = xPos;
        this.yPos = yPOs;
        this.fruitColor = fruitColor;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public Color getFruitColor() {
        return fruitColor;
    }
}
