package alien;

import java.awt.Image;



public class Sprite implements ISprite {

        private boolean visible;
        private Image image;
        protected int x;
        protected int y;
        protected boolean dying;
        protected int dx;

        public Sprite() {
            visible = true;
        }

        /* (non-Javadoc)
		 * @see alien.ISprite#die()
		 */
        @Override
		public void die() {
            visible = false;
        }

        /* (non-Javadoc)
		 * @see alien.ISprite#isVisible()
		 */
        @Override
		public boolean isVisible() {
            return visible;
        }

        protected void setVisible(boolean visible) {
            this.visible = visible;
        }

        /* (non-Javadoc)
		 * @see alien.ISprite#setImage(java.awt.Image)
		 */
        @Override
		public void setImage(Image image) {
            this.image = image;
        }

        /* (non-Javadoc)
		 * @see alien.ISprite#getImage()
		 */
        @Override
		public Image getImage() {
            return image;
        }

        /* (non-Javadoc)
		 * @see alien.ISprite#setX(int)
		 */
        @Override
		public void setX(int x) {
            this.x = x;
        }

        /* (non-Javadoc)
		 * @see alien.ISprite#setY(int)
		 */
        @Override
		public void setY(int y) {
            this.y = y;
        }
        /* (non-Javadoc)
		 * @see alien.ISprite#getY()
		 */
        @Override
		public int getY() {
            return y;
        }

        /* (non-Javadoc)
		 * @see alien.ISprite#getX()
		 */
        @Override
		public int getX() {
            return x;
        }

        /* (non-Javadoc)
		 * @see alien.ISprite#setDying(boolean)
		 */
        @Override
		public void setDying(boolean dying) {
            this.dying = dying;
        }

        /* (non-Javadoc)
		 * @see alien.ISprite#isDying()
		 */
        @Override
		public boolean isDying() {
            return this.dying;
        }
}