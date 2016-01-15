package com.magc.jni;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Tank {
	

	int x, y;
	public static final int width = 20;
	public static final int height = 20;
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	TankClient tc = null;
	
	private boolean bL = false, bU = false, bR = false, bD = false;
	private boolean good;
	
	public boolean isGood() {
		return good;
	}


	private boolean live = true ;
	private Direction dt;
	private int steps = r.nextInt(12) + 3;
	
	enum Direction {L, R, U, D, UL, UR, DL, DR, STOP};
	
	
	private static Random r = new Random();
	private Direction dir= Direction.STOP;
	private Direction barrel = Direction.D;
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			if(! good) tc.enemy.remove(this);
			else return;
		}
		
		 
		Color c = g.getColor();
		if(good) g.setColor(Color.RED); 
		if(! good) g.setColor(Color.PINK);
		g.fillOval(x, y , Tank.width, Tank.height);
		g.setColor(c);
		
		switch(barrel) {
		case L:
			 g.drawLine(x + Tank.width/2, y + Tank.height/2, x, y + Tank.height/2);
			 break;
		 case R:
			 g.drawLine(x + Tank.width/2, y + Tank.height/2, x + Tank.width, y + Tank.height/2);
			 break;
		 case U:
			 g.drawLine(x + Tank.width/2, y + Tank.height/2, x + Tank.width/2, y);
			 break;
		 case D:
			 g.drawLine(x + Tank.width/2, y + Tank.height/2, x + Tank.width/2, y + Tank.width);
			 break;
		 case UL:
			 g.drawLine(x + Tank.width/2, y + Tank.height/2, x, y);
			 break;
		 case UR:
			 g.drawLine(x + Tank.width/2, y + Tank.height/2, x + Tank.width, y);
			 break;
		 case DL:
			 g.drawLine(x + Tank.width/2, y + Tank.height/2, x, y + Tank.height);
			 break;
		 case DR:
			 g.drawLine(x + Tank.width/2, y + Tank.height/2, x + Tank.width, y + Tank.height);
			 break;
		 case STOP:
			 break;
		}
		move();
		if(this.dir != Direction.STOP) barrel = this.dir;
	}
	
	protected void move() {
		 switch(dir) {
		 case L:
			 x -= XSPEED;
			 break;
		 case R:
			 x += XSPEED;
			 break;
		 case U:
			 y -= YSPEED;
			 break;
		 case D:
			 y += YSPEED;
			 break;
		 case UL:
			 y -= YSPEED;
			 x -= XSPEED;
			 break;
		 case UR:
			 y -= YSPEED;
			 x += XSPEED;
			 break;
		 case DL:
			 y += YSPEED;
			 x -= XSPEED;
 			 break;
		 case DR:
			 y += YSPEED;
			 x += XSPEED;
			 break;
		 case STOP:
			 break;
		 }
		 if(x < 0) x = 0;
		 if(y < 20) y = 20;
		 if(x + Tank.width > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.width;
		 if(y + Tank.height > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.height;
	
		 if(!good) {
			 if(steps == 0) {
				 steps = r.nextInt(12) + 3;
				 Direction[] dirs = Direction.values();
				 int rn = r.nextInt(dirs.length);
				 dir = dirs[rn];
			 }
			 steps--;
			 if(r.nextInt(40) > 38) {
				 this.fire();
			 }
		 }
	}
	
	public void KeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_LEFT: 
			bL = true;
			break;
		case KeyEvent.VK_UP: 
			bU = true;
			break;
		case KeyEvent.VK_DOWN: 
			bD = true;
			break;
		}
		locateDirection();
	}
	
	protected void locateDirection() {
		if(bR && !bL && !bU && !bD) dir = Direction.R;
		else if(!bR && bL && !bU && !bD) dir = Direction.L;
		else if(!bR && !bL && bU && !bD) dir = Direction.U;
		else if(!bR && !bL && !bU && bD) dir = Direction.D;
		else if(bR && !bL && bU && !bD) dir = Direction.UR;
		else if(bR && !bL && !bU && bD) dir = Direction.DR;
		else if(!bR && bL && bU && !bD) dir = Direction.UL;
		else if(!bR && bL && !bU && bD) dir = Direction.DL;
		else if(!bR && !bL && !bU && !bD) dir = Direction.STOP;
	}

	public void KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_CONTROL:
			tc.m = fire();
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_LEFT: 
			bL = false;
			break;
		case KeyEvent.VK_UP: 
			bU = false;
			break; 
		case KeyEvent.VK_DOWN: 
			bD = false;
			break;
		}
		locateDirection();
	}
	
	public Missile fire() {
		if(!live) return null;
		Missile m = new Missile(x + Tank.width/2, y + Tank.height/2, barrel, this.isGood(), tc);
		tc.missile.add(m);
		return m;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, Tank.width, Tank.height);
	}
	
	public boolean isLive() {
		return live;
	}
	
	public void setLive(boolean live) {
		this.live = live;
	}
}
