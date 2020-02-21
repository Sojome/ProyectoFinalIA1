package ControladorDifuso;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aaron Jaramillo
 */
// Clase que representa una variabe linguistica
public class VariableLinguistica {
    protected String nombre;
    protected ArrayList<ValorLinguistico> valores;
    protected double valorMin;
    protected double valorMax;
    
    // Constructor
    public VariableLinguistica(String _nombre, double _min, double _max) {
        nombre = _nombre;
        valorMin = _min;
        valorMax = _max;
        valores = new ArrayList();
    }
    
    public void AgregarValorLinguistico(ValorLinguistico v1) {
       valores.add(v1);
    }
    
    public void AgregarValorLinguistico(String nombre, ConjuntoDifuso conjunto) {
        valores.add(new ValorLinguistico(nombre, conjunto));
    }
    
    public void BorrarValores() {
        valores.clear();
    }
    
    ValorLinguistico ValorLinguisticoPorNombre(String nombre) {
        for(ValorLinguistico v1: valores) {
            if(v1.nombre.equalsIgnoreCase(nombre)) {
                return v1;
            }
        }
        return null;
    }
}
