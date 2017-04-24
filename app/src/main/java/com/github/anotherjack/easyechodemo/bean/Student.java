package com.github.anotherjack.easyechodemo.bean;

/**
 * Created by zhengj on 2017/4/24.
 */
public class Student {
    /**
     * name : 小明
     * age : 15
     * sex : 男
     * home : 北京
     * hobby : 看电影
     * extra : 其他
     * grades : {"chinese":95,"math":100,"english":90,"level":"优秀"}
     */

    private String name;
    private int age;
    private String sex;
    private String home;
    private String hobby;
    private String extra;
    private Grades grades;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Grades getGrades() {
        return grades;
    }

    public void setGrades(Grades grades) {
        this.grades = grades;
    }

    public static class Grades {
        /**
         * chinese : 95
         * math : 100
         * english : 90
         * level : 优秀
         */

        private int chinese;
        private int math;
        private int english;
        private String level;

        public int getChinese() {
            return chinese;
        }

        public void setChinese(int chinese) {
            this.chinese = chinese;
        }

        public int getMath() {
            return math;
        }

        public void setMath(int math) {
            this.math = math;
        }

        public int getEnglish() {
            return english;
        }

        public void setEnglish(int english) {
            this.english = english;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}
