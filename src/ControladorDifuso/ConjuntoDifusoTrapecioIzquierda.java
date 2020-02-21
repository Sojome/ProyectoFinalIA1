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
public class ConjuntoDifusoTrapecioIzquierda extends ConjuntoDifuso {
    // Constructor
    public ConjuntoDifusoTrapecioIzquierda(double min, double max, double finPlanoElevado, double inicioPlanoBajo) {
        super(min, max);
        Agregar(new Punto2D(min, 1));
        Agregar(new Punto2D(finPlanoElevado, 1));
        Agregar(new Punto2D(inicioPlanoBajo, 0));
        Agregar(new Punto2D(max, 0));
    }
}
