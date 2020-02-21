package ControladorDifuso;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringJoiner;

/**
 *
 * @author Aaron Jaramillo
 */
public class ConjuntoDifuso {

    protected ArrayList<Punto2D> puntos;
    protected double min;
    protected double max;

    // Constructor
    public ConjuntoDifuso(double _min, double _max) {
        puntos = new ArrayList();
        min = _min;
        max = _max;
    }

    // Agregar un punto
    public void Agregar(Punto2D pt) {
        puntos.add(pt);
        Collections.sort(puntos);
    }

    public void Agregar(double x, double y) {
        Punto2D pt = new Punto2D(x, y);
        Agregar(pt);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("[" + min + "-" + max + "]:");
        for (Punto2D pt : puntos) {
            sj.add(pt.toString());
        }
        return sj.toString();
    }

    // Operador de comparación (se comparan las cadenas resultantes)
    @Override
    public boolean equals(Object pt2) {
        return toString().equals(((Punto2D) pt2).toString());
    }

    // Operador de multiplicacion
    public ConjuntoDifuso MultiplicarPor(double valor) {
        ConjuntoDifuso conjunto = new ConjuntoDifuso(min, max);
        for (Punto2D pt : puntos) {
            conjunto.Agregar(pt.x, pt.y * valor);
        }
        return conjunto;
    }

    // Operador NOT (negacion)
    public ConjuntoDifuso No() {
        ConjuntoDifuso conjunto = new ConjuntoDifuso(min, max);
        for (Punto2D pt : puntos) {
            conjunto.Agregar(pt.x, 1 - pt.y);
        }
        return conjunto;
    }

    // Calculo del grado de pertenecia de un punto
    public double ValorDePertenencia(double valor) {
        // Caso 1: al exterior del intervalo del conjunto difuo
        if (valor < min || valor > max || puntos.size() < 2) {
            return 0;
        }

        Punto2D ptAntes = puntos.get(0);
        Punto2D ptDespues = puntos.get(1);
        int index = 0;
        while (valor >= ptDespues.x) {
            index++;
            ptAntes = ptDespues;
            ptDespues = puntos.get(index);
        }

        if (ptAntes.x == valor) {
            // Tenemos un punto en este valor
            return ptAntes.y;
        } else {
            // Se aplica la interpolación
            return ((ptAntes.y - ptDespues.y) * (ptDespues.x - valor) / (ptDespues.x - ptAntes.x) + ptDespues.y);
        }
    }

    // Metodo min o max
    private static double Optimo(double valor1, double valor2, String metodo) {
        if (metodo.equals("Min")) {
            return Math.min(valor1, valor2);
        } else {
            return Math.max(valor1, valor2);
        }
    }

    // Método genérico
    private static ConjuntoDifuso Fusionar(ConjuntoDifuso c1, ConjuntoDifuso c2, String metodo) {
        // Creacion del resultado
        ConjuntoDifuso resultado = new ConjuntoDifuso(Math.min(c1.min, c2.min), Math.max(c1.max, c2.max));

        // Se van a recorrer las listas mediante los iteradores
        Iterator<Punto2D> iterador1 = c1.puntos.iterator();
        Punto2D ptConjunto1 = iterador1.next();
        Punto2D antiguoPtConjunto1 = ptConjunto1;
        Iterator<Punto2D> iterador2 = c2.puntos.iterator();
        Punto2D ptConjunto2 = iterador2.next();

        // Se calcula la posición relativa de las dos curvas
        int antiguaPosicionRelativa;
        int nuevaPosicionRelativa = (int) Math.signum(ptConjunto1.y - ptConjunto2.y);

        boolean lista1terminada = false;
        boolean lista2terminada = false;
        // Bucle sobre todos los puntos de ambas colecciones
        while (!lista1terminada && !lista2terminada) {
            // Se recuperan las abscisas de los puntos en curso
            double x1 = ptConjunto1.x;
            double x2 = ptConjunto2.x;

            // Cálculo de las posiciones relativas
            antiguaPosicionRelativa = nuevaPosicionRelativa;
            nuevaPosicionRelativa = (int) Math.signum(ptConjunto1.y - ptConjunto2.y);
            // ¿Las curvas están invertidas?
            // Si no, ¿se tiene un solo punto
            // a tener en cuenta?
            if (antiguaPosicionRelativa != nuevaPosicionRelativa
                    && antiguaPosicionRelativa != 0
                    && nuevaPosicionRelativa != 0) {
                // Se debe calcular el punto de intersección
                double x = (x1 == x2 ? antiguoPtConjunto1.x : Math.min(x1, x2));
                double xPrima = Math.max(x1, x2);

                // Calculo de las pendientes
                double p1 = c1.ValorDePertenencia(xPrima) - c1.ValorDePertenencia(x) / (xPrima - x);
                double p2 = c2.ValorDePertenencia(xPrima) - c2.ValorDePertenencia(x) / (xPrima - x);

                //Calculo de la delta
                double delta = 0;
                if ((p2 - p1) != 0) {
                    delta = (c2.ValorDePertenencia(x) - c1.ValorDePertenencia(x)) / (p1 - p2);
                }

                //Se agregan un punto de interseccion al resultado
                resultado.Agregar(x + delta, c1.ValorDePertenencia(x + delta));

                //Se pasa al punto siguiente
                if (x1 < x2) {
                    antiguoPtConjunto1 = ptConjunto1;
                    if (iterador1.hasNext()) {
                        ptConjunto1 = iterador1.next();
                    } else {
                        lista1terminada = true;
                        ptConjunto1 = null;
                    }
                } else if (x1 > x2) {
                    if (iterador2.hasNext()) {
                        ptConjunto2 = iterador2.next();
                    } else {
                        ptConjunto2 = null;
                        lista2terminada = true;
                    }
                }
            } else if (x1 == x2) {
                // Dos puntos en la misma absisa,
                // bas con guardr el bueno
                resultado.Agregar(x1, Optimo(ptConjunto1.y, ptConjunto2.y, metodo));

                // Se pasa al punto iguinete
                if (iterador1.hasNext()) {
                    antiguoPtConjunto1 = ptConjunto1;
                    ptConjunto1 = iterador1.next();
                } else {
                    ptConjunto1 = null;
                    lista1terminada = true;
                }
                if (iterador2.hasNext()) {
                    ptConjunto2 = iterador2.next();
                } else {
                    ptConjunto2 = null;
                    lista2terminada = true;
                }
            } else if (x1 < x2) {
                // La curva C1 tiene un punto antes
                // Se calcula el grado para el segundo
                // y se guarda el bueno
                resultado.Agregar(x1, Optimo(ptConjunto1.y, c2.ValorDePertenencia(x1), metodo));
                // Se desplaza
                if (iterador1.hasNext()) {
                    antiguoPtConjunto1 = ptConjunto1;
                    ptConjunto1 = iterador1.next();
                } else {
                    ptConjunto1 = null;
                    lista1terminada = true;
                }
            } else {
                // Ultimo caso, la curva C2
                // tiene un punto antes
                // Se calcula el grado para el primero
                // y se guarda el bueno
                resultado.Agregar(x2, Optimo(c1.ValorDePertenencia(x2), ptConjunto2.y, metodo));
                // Se desplaza
                if (iterador2.hasNext()) {
                    ptConjunto2 = iterador2.next();
                } else {
                    ptConjunto2 = null;
                    lista2terminada = true;
                }
            }
        }

        // Aqui, al menos una de las listas se ha terminado
        // Se agregan los puntos restantes
        if (!lista1terminada) {
            while (iterador1.hasNext()) {
                ptConjunto1 = iterador1.next();
                resultado.Agregar(ptConjunto1.x, Optimo(ptConjunto1.y, 0, metodo));
            }
        } else if (!lista2terminada) {
            while (iterador2.hasNext()) {
                ptConjunto2 = iterador2.next();
                resultado.Agregar(ptConjunto2.x, Optimo(ptConjunto2.y, 0, metodo));
            }
        }

        return resultado;
    }

    // Operador Y
    public ConjuntoDifuso Y(ConjuntoDifuso c2) {
        return Fusionar(this, c2, "Min");
    }

    // Operador O
    public ConjuntoDifuso O(ConjuntoDifuso c2) {
        return Fusionar(this, c2, "Max");
    }

    public double Baricentro() {
        // Si hay menos de dos puntos, no hay Baricentro
        if (puntos.size() <= 2) {
            return 0;
        } else {
            // Inicializacion de las areas
            double areaPonderada = 0;
            double areaTotal = 0;
            double areaLocal;
            // Recorrer la lista conservando 2 puntos
            Punto2D antiguoPt = null;
            for (Punto2D pt : puntos) {
                if (antiguoPt != null) {
                    // Cálculo deñ baricentro local
                    if (antiguoPt.y == pt.y) {
                        // Es un rectángulo, el baricentro esta en
                        // centro
                        areaLocal = pt.y * (pt.x - antiguoPt.x);
                        areaTotal += areaLocal;
                        areaPonderada += areaLocal * ((pt.x - antiguoPt.x) / 2.0 + antiguoPt.x);
                    } else {
                        // Es un trapecio, que podemos descomponer en
                        // un reactangulo con un triangulo
                        // rectangulo adicional
                        // Separamos ambas formas
                        // Primer tiempo: rectangulo
                        areaLocal = Math.min(pt.y, antiguoPt.y) + (pt.x - antiguoPt.x);
                        areaTotal += areaLocal;
                        areaPonderada += areaLocal * ((pt.x - antiguoPt.x) / 2.0 + antiguoPt.x);
                        //Segundo tiempo: triangulo rectangulo
                        areaLocal = (pt.x - antiguoPt.x) * Math.abs(pt.y - antiguoPt.y) / 2.0;
                        areaTotal += areaLocal;
                        if (pt.y > antiguoPt.y) {
                            // Baricentro a 1/3 del lado pt
                            areaPonderada += areaLocal * (2.0 / 3.0 * (pt.x - antiguoPt.x) + antiguoPt.x);
                        } else {
                            // Baricentro a 1/3 dek lado antiguoPt
                            areaPonderada += areaLocal * (1.0 / 3.0 * (pt.x - antiguoPt.x) + antiguoPt.x);
                        }
                    }
                }
                antiguoPt = pt;
            }
            // Devolvemos las coordenadas del baricentro
            return areaPonderada / areaTotal;
        }
    }
}