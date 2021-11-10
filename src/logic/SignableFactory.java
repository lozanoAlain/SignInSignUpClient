/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import dataModel.Signable;

/**
 * Class for the Signable Factory
 * @author Alain Lozano, Ilia Consuegra
 */
public class SignableFactory {

    /**
     * 
     * @return signable
     * @throws Exception 
     */
    public Signable getSignable() throws Exception{
        Signable signable;
        signable = new SignableImplementation();
        return signable;
    }

}
