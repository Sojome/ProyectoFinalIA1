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
public class ConjuntoDifusoTrapecio extends ConjuntoDifuso {
    // Constructor
    public ConjuntoDifusoTrapecio(double min, double max, double inicioBase, double inicioMeseta, double finMeseta, double finBase) {
        super(min, max);
        Agregar(new Punto2D(min, 0));
        Agregar(new Punto2D(inicioBase, 0));
        Agregar(new Punto2D(inicioMeseta, 1));
        Agregar(new Punto2D(finMeseta, 1));
        Agregar(new Punto2D(finBase, 0));
        Agregar(new Punto2D(max, 0));
    }
}
