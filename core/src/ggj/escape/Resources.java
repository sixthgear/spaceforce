package ggj.escape;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ggj.escape.components.BaddieComponent;

public class Resources {

    public static class tex {
        public static Texture placeholder = new Texture("sprites/placeholders.png");
        public static Texture characters = new Texture("sprites/sprite-sheet.png");
        public static Texture baddies = new Texture("sprites/enemy-sprites.png");
        public static Texture map = new Texture("sprites/map-sprites.png");
    }

    public static class sprites {
        public static TextureRegion bullet = new TextureRegion(tex.placeholder, 128, 0, 32, 32);
    }

    public static class sfx {
        public static Sound pistol_1 = Gdx.audio.newSound(Gdx.files.internal("sfx/Escape_From_the_Lab_SFX_Pistol_Shot_01.wav"));
        public static Sound pistol_2 = Gdx.audio.newSound(Gdx.files.internal("sfx/Escape_From_the_Lab_SFX_Pistol_Shot_02.wav"));
        public static Sound pistol_3 = Gdx.audio.newSound(Gdx.files.internal("sfx/Escape_From_the_Lab_SFX_Pistol_Shot_03.wav"));
    }

    public static class animations {
        public static class spider {
            public static Animation idle = BaddieComponent.buildAnim(0, 1, 32, 32);
            public static Animation walk = BaddieComponent.buildAnim(0, 5, 32, 32);
            public static Animation attack = BaddieComponent.buildAnim(0, 1, 32, 32);
            public static Animation die = BaddieComponent.buildAnim(0, 1, 32, 32);
        }
        public static class robot {
            public static Animation idle = BaddieComponent.buildAnim(1, 1, 32, 64);
            public static Animation walk = BaddieComponent.buildAnim(1, 4, 32, 64);
            public static Animation attack = BaddieComponent.buildAnim(1, 1, 32, 64);
            public static Animation die = BaddieComponent.buildAnim(1, 1, 32, 64);
        }
        public static class slime{
            public static Animation idle = BaddieComponent.buildAnim(3, 1, 32, 32);
            public static Animation walk = BaddieComponent.buildAnim(3, 4, 32, 32);
            public static Animation attack = BaddieComponent.buildAnim(3, 1, 32, 32);
            public static Animation die = BaddieComponent.buildAnim(3, 1, 32, 32);
        }

    }

}
