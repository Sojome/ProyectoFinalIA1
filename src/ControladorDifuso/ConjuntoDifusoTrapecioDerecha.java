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
public class ConjuntoDifusoTrapecioDerecha extends ConjuntoDifuso {
    // Constructor
    public ConjuntoDifusoTrapecioDerecha(double min, double max, double finPlanoBajo, double inicioPlanoElevado) {
        super(min, max);
        Agregar(new Punto2D(min, 0));
        Agregar(new Punto2D(finPlanoBajo, 0));
        Agregar(new Punto2D(inicioPlanoElevado, 1));
        Agregar(new Punto2D(max, 1));
    }
}
