package com.d954mas.game.indicator2019.speech.model.effects;

public class Effects {
    public static DebuffEffect FireAttackEffect = new DebuffEffect(2,"Огненая Атака",
            "Горение 3 хп");
    public static DebuffEffect FireDefenceEffect = new DebuffEffect(1,"Огненая Защита",
            "Ответный урон 4хп");

    public static DebuffEffect StormAttackEffect = new DebuffEffect(1,"Штормовая Атака",
            "Игнорирование щита");

    public static BuffEffect StormDefenceEffect = new BuffEffect(2,"Штормовая защита",
            "Доп защита");

    public static DebuffEffect LoveAttackEffect = new DebuffEffect(1,"Любовная атака",
            "");
    public static BuffEffect LoveDefenceEffect = new BuffEffect(1,"Любовная защита",
            "");

    public static BuffEffect IceAttackEffect = new BuffEffect(1,"Ледяная атака","оглушение");
    public static BuffEffect IceDefenceEffect = new BuffEffect(1,"Ледяная защита","оглушение");

    public static DebuffEffect LieAttackEffect = new DebuffEffect(1,"Обманная атака","Уменьшение защиты врага");
    public static DebuffEffect LieDefenceEffect = new DebuffEffect(1,"Обманная защита","Уменьшение атаки врага");

}
