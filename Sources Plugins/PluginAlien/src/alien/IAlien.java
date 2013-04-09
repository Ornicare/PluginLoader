package alien;

import alien.Alien.Bomb;

public interface IAlien extends ISprite{

	public abstract void act(int direction);

	public abstract Bomb getBomb();
	public void create(int x, int y);

}