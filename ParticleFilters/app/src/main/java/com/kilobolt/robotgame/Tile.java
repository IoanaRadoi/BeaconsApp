package com.kilobolt.robotgame;

import android.graphics.Rect;

import com.kilobolt.framework.Image;

import algo.Robot;

public class Tile {

	private int tileX, tileY, speedX;
	public int type;
	public Image tileImage;

	private Robot robot = GameScreen.getRobot();
	private Background bg = GameScreen.getBg1();

	private Rect r;

	public Tile(int x, int y, int typeInt) {
		tileX = x * 40;
		tileY = y * 40;

		type = typeInt;

		r = new Rect();

		if (type == 5) {
			tileImage = Assets.tiledirt;
		} else if (type == 8) {
			tileImage = Assets.tilegrassTop;
		} else if (type == 4) {
			tileImage = Assets.tilegrassLeft;

		} else if (type == 6) {
			tileImage = Assets.tilegrassRight;

		} else if (type == 2) {
			tileImage = Assets.tilegrassBot;
		} else {
			type = 0;
		}
	}

		public void update() {
			tileX += speedX;
			r.set(tileX, tileY, tileX+40, tileY+40);
	
		}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

	public void checkVerticalCollision(Rect rtop, Rect rbot) {
		if (Rect.intersects(rtop, r)) {
		}
	}

	public void checkSideCollision(Rect rleft, Rect rright, Rect leftfoot, Rect rightfoot) {
		if (type != 5 && type != 2 && type != 0){
			if (Rect.intersects(rleft, r)) {
				robot.setCenterX(tileX + 102);
	
			}else if (Rect.intersects(leftfoot, r)) {
				robot.setCenterX(tileX + 85);
			}
			
			if (Rect.intersects(rright, r)) {
				robot.setCenterX(tileX - 62);
			}
			
			else if (Rect.intersects(rightfoot, r)) {
				robot.setCenterX(tileX - 45);
			}
		}
	}

}