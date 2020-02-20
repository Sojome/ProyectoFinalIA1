/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

/**
 *
 * @author Aaron Jaramillo
 */
public class Funciones {

    //Funcion para generar numeros random en un rango
    public static int generarRR(int min, int max) {
        //Random rand = new Random();
        int ii = -min + (int) (Math.random() * ((max - (-min)) + 1));
        return ii;
    }

    //Darle nombre al estado
    public static String Temperatura(int valor) {
        String mensaje = "";
        if (valor >= 0 && valor < 20) {
            mensaje = "frio";
        } else if (valor >= 20 && valor < 35) {
            mensaje = "temperatura ambiente";
        } else if (valor >= 35 && valor <= 100) {
            mensaje = "caliente";
        }
        
        return mensaje;
    }
    
    public static String LuzExterior(int valor) {
        String mensaje = "";
        if (valor >= 0 && valor < 30) {
            mensaje = "baja";
        } else if (valor >= 30 && valor < 70) {
            mensaje = "luz ambiente";
        } else if (valor >= 70 && valor <= 100) {
            mensaje = "alta";
        }
        
        return mensaje;
    }
    
    public static String Potencial(double valor) {
        String mensaje = "";
        if (valor >= 0 && valor < 50) {
            mensaje = "apagado";
        } else if (valor >= 50 && valor < 200) {
            mensaje = "medio encendido";
        } else if (valor >= 200 && valor <= 250) {
            mensaje = "encendido";
        }
        
        return mensaje;
    }
}
