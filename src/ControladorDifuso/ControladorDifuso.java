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
// Clase que gestiona todo el sistema difuso
public class ControladorDifuso {

    protected String nombre;
    protected ArrayList<VariableLinguistica> entradas;
    protected VariableLinguistica salida;
    protected ArrayList<ReglaDifusa> reglas;
    protected ArrayList<ValorNumerico> problema;

    // Constructor
    public ControladorDifuso(String _nombre) {
        nombre = _nombre;
        entradas = new ArrayList();
        reglas = new ArrayList();
        problema = new ArrayList();
    }

    // Agregar una variable linguistica como entrada
    public void AgregarVariableEntrada(VariableLinguistica v1) {
        entradas.add(v1);
    }

    // Agregar una variable linguistica como salida
    // Una unica posibilidad: remplaza la existente si es necesario
    public void AgregarVariableSalida(VariableLinguistica v1) {
        salida = v1;
    }

    // Agregar una regla
    public void AgregarRegla(ReglaDifusa regla) {
        reglas.add(regla);
    }

    // Agregar un valor numerico como entrada
    public void AgregarValorNumerico(VariableLinguistica var, double valor) {
        problema.add(new ValorNumerico(var, valor));
    }

    // Poner a cero el problema (para pasar al siguiente caso)
    public void BorrarValorNumericos() {
        problema.clear();
    }

    // Encontrar una variable linguistica a partir de su nombre
    public VariableLinguistica VariableLinguisticaPorNombre(String nombre) {
        for (VariableLinguistica var : entradas) {
            if (var.nombre.equalsIgnoreCase(nombre)) {
                return var;
            }
        }
        if (salida.nombre.equalsIgnoreCase(nombre)) {
            return salida;
        }
        return null;
    }

    public double Resolver() {
        // Inicializacion del conjunto difuso resultante
        ConjuntoDifuso resultado = new ConjuntoDifuso(salida.valorMin, salida.valorMax);
        resultado.Agregar(salida.valorMin, 0);
        resultado.Agregar(salida.valorMax, 0);

        // Aplicacion de las reglas
        // y modificacion del conjunto difuso resultannte
        for (ReglaDifusa regla : reglas) {
            resultado = resultado.O(regla.Aplicar(problema));
        }

        // Defuzzificacion
        return resultado.Baricentro();
    }
    
    // Agregar una regla (formato texto)
    public void AgregarRegla(String reglastr) {
        ReglaDifusa regla = new ReglaDifusa(reglastr, this);
        reglas.add(regla);
    }
}
