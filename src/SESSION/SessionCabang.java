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
public class SessionCabang {
    private static String cabang;

    public static void setSessionCabang(String cabang) {
        SessionCabang.cabang = cabang;
    }
    
    
    
    
    
    
    public static String getSessionCabang() {
        return cabang;
    }
}
