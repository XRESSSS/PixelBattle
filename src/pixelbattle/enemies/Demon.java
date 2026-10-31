package pixelbattle.enemies;

import pixelbattle.core.Enemy;
import pixelbattle.core.Fighter;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.core.Ultimate;
import pixelbattle.engine.Sprite;

/** Демон — володар полум'я з вампіризмом та пекельним ультимейтом. */
public class Demon extends Enemy {

    public Demon() {
        super("Демон", 120, new Sprite("big_demon_idle_anim", 4, 4));

        addSkill(new Skill("Удар кігтями", Target.ENEMY, 0, (user, target) ->
                user.getName() + " дряпає " + target.getName() + ": "
                        + target.takeDamage(random(10, 15)) + " шкоди"));

        // Унікальне вміння: викрадення життя
        addSkill(new Skill("Викрадення душі", Target.ENEMY, 2, (user, target) -> {
            int dmg = target.takeDamage(random(14, 20));
            int healed = user.heal(dmg / 2);
            return user.getName() + " витягує душу з " + target.getName() + ": "
                    + dmg + " шкоди та +" + healed + " HP";
        }));

        // Ультимейт Демона
        setUltimate(new Ultimate() {
            @Override
            public String getName() {
                return "Пекельне полум'я";
            }

            @Override
            public String getDescription() {
                return "Спалює всіх героїв на 10-16 шкоди";
            }

            @Override
            public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                int totalDmg = 0;
                for (Fighter hero : enemies) {
                    if (hero.isAlive()) {
                        totalDmg += hero.takeDamage(random(10, 16));
                    }
                }
                return user.getName() + " викликає Пекельне полум'я! Загальна шкода: " + totalDmg;
            }
        });
    }
}
