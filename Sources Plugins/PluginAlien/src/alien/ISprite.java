package alien;

import java.awt.Image;

public interface ISprite {

	public abstract void die();

	public abstract boolean isVisible();

	public abstract void setImage(Image image);

	public abstract Image getImage();

	public abstract void setX(int x);

	public abstract void setY(int y);

	public abstract int getY();

	public abstract int getX();

	public abstract void setDying(boolean dying);

	public abstract boolean isDying();

}