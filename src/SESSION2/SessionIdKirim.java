/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SESSION2;

/**
 *
 * @author PC
 */
public class SessionIdKirim {
     private static String idKirim;

    public static void setSessionIdKirim(String idKirim) {
        SessionIdKirim.idKirim = idKirim;
    }
    
    
    
    
    
    
    public static String getSessionIdKirim() {
        return idKirim;
    }
}
