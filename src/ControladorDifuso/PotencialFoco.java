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
public class PotencialFoco {

    public static double Defusifucador(double a, double b) {

        //Creacion del sistema
        ControladorDifuso controlador = new ControladorDifuso("Control del potencial de un foco");

        // Variable linguistica de entrada:
        // luz exterior (en lm, de 0 a 100)
        VariableLinguistica luz = new VariableLinguistica("Luz", 0, 101);
        luz.AgregarValorLinguistico(new ValorLinguistico("Baja", new ConjuntoDifusoTrapecioIzquierda(0, 101, 0, 30)));
        //luz.AgregarValorLinguistico(new ValorLinguistico("Baja", new ConjuntoDifusoTriangulo(0, 100, -30, 0, 30)));
        //luz.AgregarValorLinguistico(new ValorLinguistico("Ambiente", new ConjuntoDifusoTriangulo(0, 100, 30, 50, 70)));
        luz.AgregarValorLinguistico(new ValorLinguistico("Ambiente", new ConjuntoDifusoTrapecio(0, 101, 20, 30, 70, 80)));
        luz.AgregarValorLinguistico(new ValorLinguistico("Alta", new ConjuntoDifusoTrapecioDerecha(0, 101, 70, 100)));
        //luz.AgregarValorLinguistico(new ValorLinguistico("Alta", new ConjuntoDifusoTriangulo(0, 100, 70, 100, 130)));
        controlador.AgregarVariableEntrada(luz);

        // Variable linguistica de entrada:
        // temperatura (en ºC, de 0 a 100)
        VariableLinguistica temp = new VariableLinguistica("Temperatura", 0, 101);
        temp.AgregarValorLinguistico(new ValorLinguistico("Frio", new ConjuntoDifusoTrapecioIzquierda(0, 101, 0, 20)));
        //temp.AgregarValorLinguistico(new ValorLinguistico("Frio", new ConjuntoDifusoTriangulo(0, 100, -20, 0, 20)));
        //temp.AgregarValorLinguistico(new ValorLinguistico("Ambiente", new ConjuntoDifusoTriangulo(0, 100, 20, 27.5, 35)));
        temp.AgregarValorLinguistico(new ValorLinguistico("Ambiente", new ConjuntoDifusoTrapecio(0, 101, 10, 20, 35, 70)));
        temp.AgregarValorLinguistico(new ValorLinguistico("Caliente", new ConjuntoDifusoTrapecioDerecha(0, 101, 35, 100)));
        //temp.AgregarValorLinguistico(new ValorLinguistico("Caliente", new ConjuntoDifusoTriangulo(0, 100, 35, 100, 165)));
        controlador.AgregarVariableEntrada(temp);

        //Agregar variables de salida del tipo potencial del foco
        VariableLinguistica foco = new VariableLinguistica("Foco", 0, 250);
        foco.AgregarValorLinguistico(new ValorLinguistico("Apagado", new ConjuntoDifusoTrapecioIzquierda(0, 250, 0, 50)));
        foco.AgregarValorLinguistico(new ValorLinguistico("Medio", new ConjuntoDifusoTriangulo(0, 250, 50, 125, 200)));
        foco.AgregarValorLinguistico(new ValorLinguistico("Encendido", new ConjuntoDifusoTrapecioDerecha(0, 250, 200, 250)));
        controlador.AgregarVariableSalida(foco);
        //Añadir Reglas
        //Se agregan las distintas reglas (9 para cubrir los 9 casos)
        //Luz exterior baja
        // Y Temperatura fria
        controlador.AgregarRegla("IF Luz IS Baja AND Temperatura IS Frio THEN Foco IS Encendido");
        // Y Temperatura ambiente
        controlador.AgregarRegla("IF Luz IS Baja AND Temperatura IS Ambiente THEN Foco IS Medio");
        // Y Temperatura caliente  
        controlador.AgregarRegla("IF Luz IS Baja AND Temperatura IS Caliente THEN Foco IS Medio");

        //Luz exterior ambiente
        // Y Temperatura fria
        controlador.AgregarRegla("IF Luz IS Ambiente AND Temperatura IS Frio THEN Foco IS Medio");
        // Y Temperatura ambiente
        controlador.AgregarRegla("IF Luz IS Ambiente AND Temperatura IS Ambiente THEN Foco IS Medio");
        // Y Temperatura caliente  
        controlador.AgregarRegla("IF Luz IS Ambiente AND Temperatura IS Caliente THEN Foco IS Apagado");

        //Luz exterior alta
        // Y Temperatura fria
        controlador.AgregarRegla("IF Luz IS Alta AND Temperatura IS Frio THEN Foco IS Medio");
        // Y Temperatura ambiente
        controlador.AgregarRegla("IF Luz IS Alta AND Temperatura IS Ambiente THEN Foco IS Medio");
        // Y Temperatura caliente 
        controlador.AgregarRegla("IF Luz IS Alta AND Temperatura IS Caliente THEN Foco IS Apagado");

        //Realizar consulta
        controlador.BorrarValorNumericos();
        controlador.AgregarValorNumerico(luz, a);
        controlador.AgregarValorNumerico(temp, b);

        /*
         a = b = 0;
         for (int i = 0; i <= 10; i++) {
         controlador.BorrarValorNumericos();
         a = 10 * i;
         b = 10 * i;
         System.out.printf("%.2f y %.2f \n", a, b);
         controlador.AgregarValorNumerico(luz, a);
         controlador.AgregarValorNumerico(temp, b);
         System.out.println("Resultado: " + controlador.Resolver());
         }*/
        return controlador.Resolver();
    }//Fin de la clase main
}
