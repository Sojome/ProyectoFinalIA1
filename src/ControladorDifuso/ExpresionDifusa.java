package ControladorDifuso;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aaron Jaramillo
 */
public class ExpresionDifusa {
    protected VariableLinguistica varL;
    protected String nombreValorLinguistico;
    
    // Constructor
    public ExpresionDifusa(VariableLinguistica _v1, String _valor) {
        varL = _v1;
        nombreValorLinguistico = _valor;
    }
}
