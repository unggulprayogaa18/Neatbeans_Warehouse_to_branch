package SESSION;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */
public class SessionStatus {
     private static String status;

    public static void setSessionStatus(String status) {
        SessionStatus.status = status;
    }
    
    
    
    
    
    
    public static String getSessionStatus() {
        return status;
    }
}
