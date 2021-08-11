package techno3d.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class End {
    boolean playerWin;
    BitmapFont font;

    public End(boolean playerWin) {
        this.playerWin = playerWin;
        font = new BitmapFont();
        font.setColor(0, 0, 0, 1);
    }

    public void render() {
		ScreenUtils.clear(1, 1, 1, 1);
        
        if(playerWin) {
            font.draw(Pong.batch, "First Player Won", Pong.cam.viewportWidth/2, Pong.cam.viewportHeight-25);
        } else {
            font.draw(Pong.batch, "2nd Player Won", Pong.cam.viewportWidth/2, Pong.cam.viewportHeight-25);
        }
        
        if(Gdx.input.isKeyJustPressed(Keys.R)) {
            
        }
    }

    public void dispose() {
        font.dispose();
    }
}
