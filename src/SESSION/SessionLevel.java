/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SESSION;

/**
 *
 * @author PC
 */
public class SessionLevel {
     private static String level;

    public static void setSessionLevel(String level) {
        SessionLevel.level = level;
    }
    
    
    
    
    
    
    public static String getSessionLevel() {
        return level;
    }
}
