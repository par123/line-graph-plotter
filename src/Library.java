/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author partho
 */
public class Library {
    public void Library(){
        
    }

    boolean isNum(String s){
        boolean retVal=true;
        try {
            Float.parseFloat(s);
        } catch (Exception e) {
            retVal=false;
        }
        return retVal;
    }
}
