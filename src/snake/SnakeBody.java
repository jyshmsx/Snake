package snake;

public class SnakeBody {
	private Cell headCell;
	private int[] xDirec = new int[] { -1, 1, 0, 0 }; // 分别是左右上下
	private int[] yDirec = new int[] { 0, 0, -1, 1 };

	public SnakeBody() {
		headCell = new Cell(1, (int) (Snake.BOARD_HEIGHT / 2), 1);
		Cell nextCell = new Cell(headCell.getxPos() - 1, headCell.getyPos(), headCell.getDirection());
		headCell.setNextCell(nextCell);
	}

	public void move(int i) { // 0,1,2,3分别为左右上下
		int remeDire = i;
		headCell.setBeforeDirec(headCell.getDirection());
		headCell.setDirection(i);
		headCell.setXYPos(headCell.getxPos() + xDirec[headCell.getDirection()],
				headCell.getyPos() + yDirec[headCell.getDirection()]);
		Cell cell = headCell;
		while ((cell = cell.getNextCell()) != null) {
			int nowDire = cell.getDirection();
			cell.setXYPos(cell.getxPos() + xDirec[cell.getDirection()], cell.getyPos() + yDirec[cell.getDirection()]);
			cell.setBeforeDirec(cell.getDirection());
			cell.setDirection(remeDire);
			remeDire = nowDire;
		}
	}

	public Cell checkMove(int i)
	{
		Cell cell = new Cell(headCell.getxPos() + xDirec[i], headCell.getyPos() + yDirec[i], headCell.getDirection());
		return cell;
	}

	public void addCell() {
		Cell cell = headCell;
		while ((cell = cell.getNextCell()) != null) {
			if (cell.getNextCell() == null) {
				break;
			}
		}
		Cell nextCell = new Cell(cell.getxPos() - xDirec[cell.getBeforeDirec()],
				cell.getyPos() - yDirec[cell.getBeforeDirec()], cell.getBeforeDirec());
		cell.setNextCell(nextCell);
	}

	public Cell getHeadCell() {
		return this.headCell;
	}

	@Override
	public String toString() {
		return "SnakeBody [headCell=" + headCell + "]";
	}

}
