package me.quickscythe.dragonforge.utils.sessions;


public class SessionManager {
    private static Session session = null;

    public static void init(){
        session = new Session();
    }

    public static Session session(){
        return session;
    }
}
