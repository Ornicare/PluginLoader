package spaceinvaders;

import javax.swing.JFrame;

public class SpaceInvaders extends JFrame implements Commons {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2696774479366547077L;

	public SpaceInvaders(MainClass mainClass)
    {
        add(new Board(mainClass));
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGTH);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

}