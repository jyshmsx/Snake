package snake;

public class Cell {
    private Cell nextCell = null;
    private int xPos;
    private int yPos;
    private int direction;
    private int beforeDirec;

    public Cell(int xPos, int yPos, int direction) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        this.beforeDirec = direction;
    }

    public Cell(Cell cell)
    {
        this.xPos = cell.getxPos();
        this.yPos = cell.getyPos();
        this.direction = cell.getDirection();
        this.beforeDirec = this.direction;
    }

    public Cell getNextCell() {
        return nextCell;
    }

    public void setNextCell(Cell nextCell) {
        this.nextCell = nextCell;
    }

    public int getxPos() {
        return xPos;
    }

    public void setXYPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getBeforeDirec() {
        return beforeDirec;
    }

    public void setBeforeDirec(int beforeDirec) {
        this.beforeDirec = beforeDirec;
    }

    @Override
    public String toString() {
        return "Cell [beforeDirec=" + beforeDirec + ", direction=" + direction + ", xPos=" + xPos + ", yPos=" + yPos
                + "]";
    }

}