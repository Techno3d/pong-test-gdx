package techno3d.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game {
	Texture ballTexture;
	Texture pongTexture;
	Sprite player;
	Sprite enemy;
	Sprite ball;
	Vector2 ballPos;
	Vector2 playerPos;
	Vector2 enemyPos;
	boolean isStart;
	boolean isPaused;
	int direction = 1;
	float angle = 0;
	BitmapFont font;
	float playerSpeed;
	float enemySpeed;
	int maxSpeed;
	float ballSpeed;

	public int playerScore;
	public int enemyScore;

	long pTime;

	//Stupid Solution
	boolean wallRight;
	boolean wallUp;

	public void reset() {
		isStart = true;
		isPaused = false;

		playerScore = 0;
		enemyScore = 0;

		resetLocations();
	}
    
    private void resetLocations() {
		ballPos = new Vector2(Pong.cam.viewportWidth/2, Pong.cam.viewportHeight/2);
		playerPos = new Vector2(12, Pong.cam.viewportHeight/2);
		enemyPos = new Vector2(Pong.cam.viewportWidth-16-12, Pong.cam.viewportHeight/2);
		updatePos();

		wallRight = false;
		wallUp = false;
	}
    
    public Game() {
		ballTexture = new Texture(Gdx.files.internal("Pong.png"));
		ball = new Sprite(ballTexture);
		pongTexture = new Texture(Gdx.files.internal("PongWall.png"));
		player = new Sprite(pongTexture);
		enemy = new Sprite(pongTexture);
		
		player.setSize(16, 64);
		player.setOriginCenter();
		enemy.setSize(16, 64);
		enemy.setOriginCenter();
		
		resetLocations();

		isStart = true;
		isPaused = false;

		playerScore = 0;
		enemyScore = 0;

		font = new BitmapFont();
		font.setColor(0, 0, 0, 1);

		playerSpeed = 0;
		enemyScore = 0;
		maxSpeed = 500;
		ballSpeed = 400;
	}
    
    public void dispose() {
		ballTexture.dispose();
		pongTexture.dispose();
		font.dispose();
	}

    public void render() {
		ScreenUtils.clear(1, 1, 1, 1);

		if(!isPaused) {
			//Paddle Logic
			paddleLogic();

			//Ball Logic
			ballLogic();

			updatePos();
		}
		ball.draw(Pong.batch);
		player.draw(Pong.batch);
		enemy.draw(Pong.batch);
		font.draw(Pong.batch, playerScore + "    |    " + enemyScore, Pong.cam.viewportWidth/2, Pong.cam.viewportHeight-font.getCapHeight());

		if(isPaused && System.currentTimeMillis() - pTime >= 1000) {
			isPaused = false;
		}
	}
    
    private void updatePos() {
		ball.setX(ballPos.x);
		ball.setY(ballPos.y);

		player.setX(playerPos.x);
		player.setY(playerPos.y);
		
		enemy.setX(enemyPos.x);
		enemy.setY(enemyPos.y);
	}

	
	private void paddleLogic() {
		//Player Logic
		if(Gdx.input.isKeyPressed(Keys.W)) {
			if(playerSpeed <= maxSpeed) playerSpeed += 50;
			playerPos.y += playerSpeed * Gdx.graphics.getDeltaTime();
		} else if(Gdx.input.isKeyPressed(Keys.S)) {
			if(playerSpeed >= -maxSpeed) playerSpeed -= 50;
			playerPos.y += playerSpeed * Gdx.graphics.getDeltaTime();
		} else {
			playerSpeed = 0;
		}
		
		if(playerPos.y > Pong.cam.viewportHeight-player.getHeight()) {
			playerPos.y = Pong.cam.viewportHeight-player.getHeight();
		}

		if(playerPos.y < 0) {
			playerPos.y = 0;
		}

		//Enemy Player Logic
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			if(enemySpeed <= maxSpeed) enemySpeed += 50;
			enemyPos.y += enemySpeed * Gdx.graphics.getDeltaTime();
		} else if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			if(enemySpeed >= -maxSpeed) enemySpeed -= 50;
			enemyPos.y += enemySpeed * Gdx.graphics.getDeltaTime();
		} else {
			enemySpeed = 0;
		}
		
		if(enemyPos.y > Pong.cam.viewportHeight-enemy.getHeight()) {
			enemyPos.y = Pong.cam.viewportHeight-enemy.getHeight();
		}

		if(enemyPos.y < 0) {
			enemyPos.y = 0;
		}
	}
	
	private void ballLogic() {
		if(isStart) {
			direction = Math.round(Math.random()) == 1 ? 1 : -1;
			angle = MathUtils.random(-150f, 150f);
			isStart = false;
		}

		if(direction == -1) wallRight = true;
		if(angle >= 0) wallUp = true;

		ballPos.x += ballSpeed * direction * Gdx.graphics.getDeltaTime();
		ballPos.y += angle * Gdx.graphics.getDeltaTime();

		ballSpeed += 50 * Gdx.graphics.getDeltaTime();

		if(ball.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
			if(wallRight) {
				direction *= -1;
				if(angle <= 300) angle += playerSpeed/3;
				wallRight = !wallRight;
				ballSpeed -= 40;
			}
		}
		
		if(ball.getBoundingRectangle().overlaps(enemy.getBoundingRectangle())) {
			if(!wallRight) {
				direction *= -1;
				if(angle <= 300) angle += enemySpeed/3;
				wallRight = !wallRight;
				ballSpeed -= 40;
			}
		}

		if(ballPos.y + ball.getHeight() > Pong.cam.viewportHeight) {
			if(wallUp) {
				angle *= -1;
				wallUp = !wallUp;
				ballSpeed -= 40;
			}
		}

		if(ballPos.y < 0) {
			if(!wallUp) {
				angle *= -1;
				wallUp = !wallUp;
				ballSpeed -= 40;
			}
		}

		//Score
		if(ballPos.x < 0) {
			enemyScore += 1;
			resetLocations();
			
			isPaused = true;
			pTime = System.currentTimeMillis();

			isStart = true;
		}

		if(ballPos.x + ball.getWidth() > Pong.cam.viewportWidth) {
			playerScore += 1;
			resetLocations();
			
			isPaused = true;
			pTime = System.currentTimeMillis();

			isStart = true;
		}
	}
}
