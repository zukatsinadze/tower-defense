package hu.elte.inf.szofttech.nameless.model;

import java.util.Optional;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hu.elte.inf.szofttech.nameless.model.tower.Tower;


public final class Square {
    private Optional<Tower> tower;
    private GDSprite sprite;

    public Square() {
        this.tower = Optional.empty();
    }

    public Optional<Tower> getTower() {
        return this.tower;
    }

    public void buildTower(Tower tower) {
        this.tower = Optional.of(tower);
    }

    public void destroyTower() {
        this.tower = Optional.empty();
    }

    public GDSprite getSprite() {
        return sprite;
    }

    public void setSprite(GDSprite sprite) {
        this.sprite = sprite;
    }

    public void draw(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }
}
