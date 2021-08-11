package techno3d.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class End {
    boolean playerWin;
    boolean reset;
    BitmapFont font;

    public boolean getReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public End(boolean playerWin) {
        this.playerWin = playerWin;
        font = new BitmapFont();
        font.setColor(0, 0, 0, 1);
        reset = false;
    }

    public void render() {
		ScreenUtils.clear(1, 1, 1, 1);
        
        if(playerWin) {
            font.draw(Pong.batch, "First Player Won", Pong.cam.viewportWidth/2, Pong.cam.viewportHeight-25);
        } else {
            font.draw(Pong.batch, "2nd Player Won", Pong.cam.viewportWidth/2, Pong.cam.viewportHeight-25);
        }
        
        if(Gdx.input.isKeyJustPressed(Keys.R)) {
            reset = true;
        }
    }

    public void dispose() {
        font.dispose();
    }
}
