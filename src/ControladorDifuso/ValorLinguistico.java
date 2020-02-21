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
public class ValorLinguistico {
    // Conjunto difuso vinculado
    protected ConjuntoDifuso conjuntoDifuso;
    
    // Nombre del valor
    protected String nombre;
    
    // Constructor
    public ValorLinguistico(String _nombre, ConjuntoDifuso _cd) {
        conjuntoDifuso = _cd;
        nombre = _nombre;
    }
    
    double ValorLinguistico(double valor) {
        return conjuntoDifuso.ValorDePertenencia(valor);
    }
}
