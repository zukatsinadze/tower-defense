package hu.elte.inf.szofttech.nameless.model.tower;

import hu.elte.inf.szofttech.nameless.model.Balloon;
import hu.elte.inf.szofttech.nameless.model.GDSprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tower {
    private SpecialAbility specialAbility;
    private int XP;
    private int price;
    private int range;
    private int attack_speed;
    private Boolean upgraded = false;
    protected Vector2 position;
    protected GDSprite sprite;

    public void setPosition(Vector2 position){
        this.position = position;
        sprite.setX(position.x);
        sprite.setY(position.y);
    }
    public Vector2 getPosition() {
        return position;
    }

    public GDSprite getSprite() {
        return sprite;
    }
    public void setSprite(GDSprite sprite) {
        this.sprite = sprite;
    }




    public void specialAttack(Balloon b){
        if (upgraded)
            specialAbility.specialAttack(b);
    }

    public void attackBaloon(Balloon b){
        //
    }

    public void upgradeTower(){
        //
    }
}
