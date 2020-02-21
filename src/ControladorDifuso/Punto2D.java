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
public class Punto2D implements Comparable{
    // Coordenadas
    public double x;
    public double y;
    
    // 

    public Punto2D(double _x, double _y) {
        x = _x;
        y = _y;
    }
    
    @Override
    public int compareTo(Object t) {
        return (int) (x - ((Punto2D) t).x);
    }
    
    @Override
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}