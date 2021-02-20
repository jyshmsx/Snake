package snake;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;

import java.util.Random;

public class Snake {
    public final static int BOARD_WIDTH = 15;
    public final static int BOARD_HEIGHT = 15;
    public final static int CELL_SIZE = 20;
    private final int snakeSpeed = 1000;

    private SnakeBody snakeBody = new SnakeBody();
    private Fruit fruit = new Fruit();

    private JFrame jf = new JFrame("snake");
    private myJPanel mjp = new myJPanel();
    private final Color[] allColors = new Color[] { Color.gray, Color.blue, Color.red, Color.green, Color.PINK,
            Color.yellow, Color.orange, Color.MAGENTA }; // 鎬婚鑹�
    private Color snakeColor;
    private Color snakeHeadColor;

    private Random rd = new Random();
    private Timer updateUITimer;
    private Timer actionTimer;

    private boolean isLose;

    public void checkFruit() {
        Cell cell = snakeBody.getHeadCell();
        if (cell.getxPos() == fruit.getxPos() && cell.getyPos() == fruit.getyPos()) {
            snakeBody.addCell();
            setFruit();
        }
    }

    public boolean checkHitBoard(int xPos, int yPos) {
        boolean flag = false;
        if ((xPos < 0 || xPos == BOARD_WIDTH) || (yPos < 0 || yPos == BOARD_HEIGHT)) {
            flag = true;
        }
        return flag;
    }

    public boolean checkHitBody(int xPos, int yPos) {
        boolean flag = false;
        Cell cell = snakeBody.getHeadCell();
        do {
            if (xPos == cell.getxPos() && yPos == cell.getyPos()) {
                flag = true;
            }
        } while ((cell = cell.getNextCell()) != null);
        return flag;
    }

    public boolean checkSnake(int i, Cell cell) {
        boolean flag = false;
        if (checkHitBoard(cell.getxPos(), cell.getyPos()) || checkHitBody(cell.getxPos(), cell.getyPos())) {
            flag = true;
        }
        return flag;
    }

    public void snakeMove(int i) {
        Cell cell = snakeBody.checkMove(i);

        if (cell.getxPos() != snakeBody.getHeadCell().getNextCell().getxPos()
                || cell.getyPos() != snakeBody.getHeadCell().getNextCell().getyPos()) {
            if (checkSnake(i, cell)) {
                isLose = true;
            } else {
                snakeBody.move(i);
                checkFruit();
            }
        }
    }

    public boolean checkPlace(int xPos, int yPos) {
        boolean flag = false;
        Cell cell = snakeBody.getHeadCell();
        do {
            if (xPos == cell.getxPos() && yPos == cell.getyPos()) {
                flag = true;
                break;
            }
        } while ((cell = cell.getNextCell()) != null);
        return flag;
    }

    public void setFruit() {
        int xPos = rd.nextInt(BOARD_WIDTH);
        int yPos = rd.nextInt(BOARD_HEIGHT);
        do {
            xPos = rd.nextInt(BOARD_WIDTH);
            yPos = rd.nextInt(BOARD_HEIGHT);
        } while (checkPlace(xPos, yPos));
        fruit.setFruit(xPos, yPos, allColors[rd.nextInt(8)]);
    }

    public void reStartGame() {
        snakeColor = allColors[rd.nextInt(8)];
        snakeBody = new SnakeBody();
        setFruit();
        isLose = false;
    }

    public void setSnakeColor() {
        int sc = rd.nextInt(8);
        snakeColor = allColors[sc];
        int hc;
        do {
            hc = rd.nextInt(8);
            snakeHeadColor = allColors[hc];
        } while (hc == sc);
    }

    public void startGame() {
        setSnakeColor();
        snakeBody = new SnakeBody();
        setFruit();
        actionTimer.restart();
        isLose = false;
    }

    public void init() {
        mjp.setPreferredSize(new Dimension(BOARD_WIDTH * 2 * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE));
        jf.addKeyListener(new MyKeyAdapter());
        updateUITimer = new Timer(10, new UpdateUIListener());
        updateUITimer.start();
        actionTimer = new Timer(snakeSpeed, new actionTimerListener());
        actionTimer.start();
        jf.add(mjp);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
        
        startGame();
    }

    public static void main(String[] args) {
        new Snake().init();
    }

    class myJPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        public void paintComponent(Graphics g) {

            if (isLose) {
                g.setColor(Color.BLACK);
                g.drawString("you are lose, press R replay", 16 * CELL_SIZE, 1 * CELL_SIZE);
            } else {
                g.setColor(Color.white);
                g.fillRect(0, 0, BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);

                Cell cell = snakeBody.getHeadCell();
                g.setColor(snakeHeadColor);
                g.fillRect(cell.getxPos() * CELL_SIZE, cell.getyPos() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(snakeColor);
                while ((cell = cell.getNextCell()) != null) {
                    g.fillRect(cell.getxPos() * CELL_SIZE, cell.getyPos() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }

                g.setColor(fruit.getFruitColor());
                g.fillRect(fruit.getxPos() * CELL_SIZE, fruit.getyPos() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }

        }
    }

    class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent ke) {
            if (!isLose) {
                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    snakeMove(0);
                }
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    snakeMove(1);
                }
                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    snakeMove(2);
                }
                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                    snakeMove(3);
                }
                if (ke.getKeyCode() == KeyEvent.VK_A && ke.getKeyCode() == KeyEvent.VK_B) {
                    snakeBody.addCell();
                }
            } else {
                if (ke.getKeyCode() == KeyEvent.VK_R) {
                    reStartGame();
                }
            }

        }
    }

    class UpdateUIListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mjp.updateUI();
        }
    }

    class actionTimerListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            snakeMove(snakeBody.getHeadCell().getDirection());
        }
    }
}
