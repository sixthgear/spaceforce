package ggj.escape;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import ggj.escape.components.BaddieComponent;
import ggj.escape.components.ExplosionComponent;
import ggj.escape.world.Level;

public class Resources {

    public static Animation buildAnim(Texture tex, float interval, int index, int frames, int width, int height) {
        return buildAnim(tex, interval, index, frames, width, height, Animation.PlayMode.LOOP);
    }

    public static Animation buildAnim(Texture tex, float interval, int index, int frames, int width, int height, Animation.PlayMode playmode) {
        TextureRegion slice = new TextureRegion(tex, 0, Level.TILESIZE * index, Level.TILESIZE * frames, height);
        TextureRegion[][] tmpFrames = slice.split(width, height);
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();

        for (TextureRegion[] ty : tmpFrames)
            for (TextureRegion t : ty)
                keyFrames.add(t);

        return new Animation(interval, keyFrames, playmode);
    }

    public static class tex {
        public static Texture placeholder = new Texture("sprites/placeholders.png");
        public static Texture characters = new Texture("sprites/sprite-sheet.png");
        public static Texture baddies = new Texture("sprites/enemy-sprites.png");
        public static Texture map = new Texture("sprites/map-sprites.png");
        public static Texture ui = new Texture("sprites/ui-sprites-01.png");
        public static Texture fx = new Texture("sprites/explosion-sprites.png");
    }

    public static class fonts {
        public static BitmapFont roboto_white = new BitmapFont(Gdx.files.internal("fnt/roboto-white-32.fnt"));
        public static BitmapFontCache roboto_white_cache = new BitmapFontCache(roboto_white);
    }

    public static class sprites {
        public static TextureRegion bullet = new TextureRegion(tex.placeholder, 128, 0, 32, 32);
        public static Sprite[] crosshair = new Sprite[4];
        public static TextureRegion[] health = new TextureRegion[4];


        static {
            crosshair[0] = new Sprite(new TextureRegion(tex.ui, 0, 128, 128, 32));
            crosshair[2] = new Sprite(new TextureRegion(tex.ui, 0, 160, 128, 32));
            crosshair[3] = new Sprite(new TextureRegion(tex.ui, 0, 192, 128, 32));
            crosshair[1] = new Sprite(new TextureRegion(tex.ui, 0, 224, 128, 32));
            for (Sprite c : crosshair) {
                c.setSize(4, 1);
                c.setOrigin(0.5f, 0.5f);
            }
            health[0] = new TextureRegion(tex.ui, 0, 0, 256, 32);
            health[1] = new TextureRegion(tex.ui, 0, 32, 256, 32);
            health[2] = new TextureRegion(tex.ui, 0, 64, 256, 32);
            health[3] = new TextureRegion(tex.ui, 0, 96, 256, 32);
        }
    }

    public static class sfx {
        public static Sound pistol_1 = Gdx.audio.newSound(Gdx.files.internal("sfx/Escape_From_the_Lab_SFX_Pistol_Shot_01.wav"));
        public static Sound pistol_2 = Gdx.audio.newSound(Gdx.files.internal("sfx/Escape_From_the_Lab_SFX_Pistol_Shot_02.wav"));
        public static Sound pistol_3 = Gdx.audio.newSound(Gdx.files.internal("sfx/Escape_From_the_Lab_SFX_Pistol_Shot_03.wav"));
        public static Sound snipe_1 = Gdx.audio.newSound(Gdx.files.internal("sfx/Escape_From_the_Lab_SFX_Captain_Shot_01.wav"));
        public static Sound shotgun_1 = Gdx.audio.newSound(Gdx.files.internal("sfx/Escape_From_the_Lab_SFX_Soldier_Shot_01.wav"));
    }

    public static class animations {
        public static class spider {
            public static Animation idle = buildAnim(Resources.tex.baddies, 0.1f, 0, 1, 32, 32);
            public static Animation walk = buildAnim(Resources.tex.baddies, 0.1f, 0, 5, 32, 32);
            public static Animation attack = buildAnim(Resources.tex.baddies, 0.1f, 0, 1, 32, 32);
            public static Animation die = buildAnim(Resources.tex.baddies, 0.1f,0, 1, 32, 32);
            public static Animation explode = buildAnim(Resources.tex.fx, 0.05f, 3, 14, 32, 32, Animation.PlayMode.NORMAL);
        }
        public static class robot {
            public static Animation idle = buildAnim(Resources.tex.baddies, 0.1f, 1, 1, 32, 64);
            public static Animation walk = buildAnim(Resources.tex.baddies, 0.1f, 1, 4, 32, 64);
            public static Animation attack = buildAnim(Resources.tex.baddies, 0.1f, 1, 1, 32, 64);
            public static Animation die = buildAnim(Resources.tex.baddies, 0.1f, 1, 1, 32, 64);
            public static Animation explode = buildAnim(Resources.tex.fx, 0.05f, 0, 14, 64, 64, Animation.PlayMode.NORMAL);
        }
        public static class slime{
            public static Animation idle = buildAnim(Resources.tex.baddies, 0.1f, 3, 1, 32, 32);
            public static Animation walk = buildAnim(Resources.tex.baddies, 0.1f, 3, 4, 32, 32);
            public static Animation attack = buildAnim(Resources.tex.baddies, 0.1f, 3, 1, 32, 32);
            public static Animation die = buildAnim(Resources.tex.baddies, 0.1f, 3, 1, 32, 32);
            public static Animation explode = buildAnim(Resources.tex.fx, 0.05f, 2, 14, 32, 32, Animation.PlayMode.NORMAL);
        }
    }

}
