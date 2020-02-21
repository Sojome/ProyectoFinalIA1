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
// Clase que representa una regla difusa, con variables premisas
// y una conclusion
public class ReglaDifusa {
    protected ArrayList<ExpresionDifusa> premisas;
    protected ExpresionDifusa conclusion;
    
    // Constructor
    public ReglaDifusa(ArrayList<ExpresionDifusa> _premisas, ExpresionDifusa _conclusion) {
        premisas = _premisas;
        conclusion = _conclusion;
    }
    
    // Aplicar la regla a un problema numerico concreto
    // Esto produce un conjunto difuso
    ConjuntoDifuso Aplicar(ArrayList<ValorNumerico> problema) {
        double grado = 1;
        // Se busca el grado de cada premisa
        for(ExpresionDifusa premisa : premisas) {
            double gradoLocal = 0;
            ValorLinguistico v1 = null;
            // Se recupera el valor para esta premisa
            // en el problema a resolver
            for(ValorNumerico pb : problema) {
                if(premisa.varL.equals(pb.v1)) {
                    // Encontramos el dato correcto del problema,
                    // se recupera su pertenencia
                    v1 = premisa.varL.ValorLinguisticoPorNombre(premisa.nombreValorLinguistico);
                    if(v1 != null) {
                        gradoLocal = v1.ValorLinguistico(pb.valor);
                        break;
                    }
                }
            }
            if(v1 == null) {
                // Nos falta al menos un dato,
                // de modo que no podemos resolver
                return null;
            }
            // Se guarda el grado min
            grado = Math.min(grado, gradoLocal);
        }
        return conclusion.varL.ValorLinguisticoPorNombre(conclusion.nombreValorLinguistico).conjuntoDifuso.MultiplicarPor(grado);
    }
    
    // Constructor a partir de una cadena de caracteres
    public ReglaDifusa(String reglastr, ControladorDifuso controlador) {
        // Paso de la regla en mayusculas
        reglastr = reglastr.toUpperCase();
        
        // Separacion premisas / conclusion (palabra clave THEN)
        String[] regla = reglastr.split(" THEN ");
        if(regla.length == 2) {
            // Se tratan las premisas quitando el IF
            // y luego separando por los AND
            regla[0] = regla[0].replaceFirst("IF ", "").trim();
            String[] premisasStr = regla[0].split(" AND ");
            premisas = new ArrayList();
            for(String exp : premisasStr) {
                // Se divide por la palabra clave IS,
                // y se crea una expresion difusa
                String[] partes = exp.trim().split(" IS ");
                if(partes.length == 2) {
                    ExpresionDifusa expDifusa = new ExpresionDifusa(controlador.VariableLinguisticaPorNombre(partes[0]), partes[1]);
                    premisas.add(expDifusa);
                }
            }
            // Se trata la conclusion
            String[] concluStr = regla[1].trim().split(" IS ");
            if(concluStr.length == 2) {
                conclusion = new ExpresionDifusa(controlador.VariableLinguisticaPorNombre(concluStr[0]), concluStr[1]);
            }
        }
    }
}
