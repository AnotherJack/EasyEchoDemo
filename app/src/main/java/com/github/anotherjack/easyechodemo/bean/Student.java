package com.github.anotherjack.easyechodemo.bean;

import java.util.List;

/**
 * Created by ASD on 2017/4/24.
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
     * friends : [{"friendName":"小红"},{"friendName":"小刚"},{"friendName":"小军"}]
     */

    private String name;
    private int age = 24;
    private String sex;
    private String home;
    private String hobby;
    private String extra = "其他";
    private GradesBean grades;
    private List<FriendsBean> friends;

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

    public GradesBean getGrades() {
        return grades;
    }

    public void setGrades(GradesBean grades) {
        this.grades = grades;
    }

    public List<FriendsBean> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendsBean> friends) {
        this.friends = friends;
    }

    public static class GradesBean {
        /**
         * chinese : 95
         * math : 100
         * english : 90
         * level : 优秀
         */

        private int chinese = 100;
        private int math;
        private int english;
        private String level = null;

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

    public static class FriendsBean {
        /**
         * friendName : 小红
         */

        private String friendName;

        public String getFriendName() {
            return friendName;
        }

        public void setFriendName(String friendName) {
            this.friendName = friendName;
        }
    }
}
