package techno3d.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pong extends ApplicationAdapter {
	Game gameScreen;
	End endScreen;
	Screen screen;
	boolean playerWin;
	public static OrthographicCamera cam;
    public static SpriteBatch batch;

	
	@Override
	public void create () {
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1280, 720);
		batch = new SpriteBatch();
		
		screen = Screen.GAME;
		gameScreen = new Game();
		endScreen = new End(playerWin);
		playerWin = false;
	}



	@Override
	public void render() {
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		if (screen.equals(Screen.GAME)) {
			gameScreen.render();
			
			if(gameScreen.playerScore > 6) {
				screen = Screen.END;
				playerWin = true;
			} else if(gameScreen.enemyScore > 6) {
				screen = Screen.END;
				playerWin = false;
			}
		} else if(screen.equals(Screen.END)) {
			endScreen.render();
			
			if(endScreen.getReset()) {
				screen = Screen.GAME;
				endScreen.setReset(false);
				gameScreen.reset();
			}

		}
		batch.end();
	}

	
	@Override
	public void dispose () {
		gameScreen.dispose();
		endScreen.dispose();
		batch.dispose();
	}


}
