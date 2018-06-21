package model;

public enum Mood {
    NORMAL,
    HAPPY,
    SAD,
    FURY;


    public static Mood setMood(String s) {
        switch (s){
            case "Mood.NORMAL" : {}
            case "NORMAL" : {
                return Mood.NORMAL;
            }
            case "Mood.FURY" : {}
            case "FURY" : {
                return Mood.FURY;
            }
            case "Mood.HAPPY" : {}
            case "HAPPY" : {
                return Mood.HAPPY;
            }
            case "Mood.SAD" : {}
            case "SAD" : {
                return Mood.SAD;
            }
            default: {
                return null;
            }
        }
    }
}
