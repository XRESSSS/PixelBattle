package pixelbattle.enemies;

import pixelbattle.core.Enemy;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.engine.Sprite;

/** Скелет — нежить, що переживає перший смертельний удар. */
public class Skeleton extends Enemy {

    private boolean resurrected = false;

    public Skeleton() {
        super("Скелет", 50, new Sprite("skelet_idle_anim", 4, 6));

        addSkill(new Skill("Кістяний удар", Target.ENEMY, 0, (user, target) -> {
            int dmg = target.takeDamage(random(8, 12));
            return user.getName() + " завдає кістяного удару " + target.getName() + ": " + dmg + " шкоди";
        }));
    }

    // Пасивка «Воскресіння»: перший удар, який мав би вбити скелета, залишає йому 1 HP
    @Override
    protected int modifyIncomingDamage(int damage) {
        if (!resurrected && damage >= getHp()) {
            resurrected = true;
            return getHp() - 1;
        }
        return damage;
    }
}