package pixelbattle.heroes;

import pixelbattle.core.Fighter;
import pixelbattle.core.Hero;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.core.Ultimate;
import pixelbattle.engine.Sprite;

/** Лучниця — швидка й спритна. */
public class Archer extends Hero {

    public Archer() {
        super("Лучниця", 80, new Sprite("elf_f_idle_anim", 4, 4));

        addSkill(new Skill("Постріл", Target.ENEMY, 0, (user, target) ->
                user.getName() + " стріляє в " + target.getName() + ": "
                        + target.takeDamage(random(10, 16)) + " шкоди"));

        // TODO 3: вміння «Подвійний постріл» (Target.ENEMY, перезарядка 1)
        addSkill(new Skill("Подвійний постріл", Target.ENEMY, 1, (user, target) -> {
            int dmg1 = target.takeDamage(random(7, 11));
            int dmg2 = target.takeDamage(random(7, 11));
            int totalDmg = dmg1 + dmg2;
            return user.getName() + " робить подвійний постріл у " + target.getName() + ": " + totalDmg + " шкоди";
        }));

        // TODO 9: ультимейт «Злива стріл»
        setUltimate(new Ultimate() {
            private int arrowsCount = 3;

            @Override
            public String getName() {
                return "Злива стріл";
            }

            @Override
            public String getDescription() {
                return "Випускає по кілька стріл (4-8 шкоди) у кожного ворога";
            }

            @Override
            public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                int currentArrows = arrowsCount;
                arrowsCount++;
                int totalDmg = 0;

                for (Fighter e : enemies) {
                    if (e.isAlive()) {
                        for (int i = 0; i < currentArrows; i++) {
                            if (e.isAlive()) {
                                totalDmg += e.takeDamage(random(4, 8));
                            }
                        }
                    }
                }
                return user.getName() + " випускає Зливу стріл (по " + currentArrows + " шт.): " + totalDmg + " шкоди";
            }
        });
    }

    // TODO 5: пасивка «Ухилення»
    @Override
    protected int modifyIncomingDamage(int damage) {
        if (chance(25)) {
            return 0;
        }
        return damage;
    }
}
