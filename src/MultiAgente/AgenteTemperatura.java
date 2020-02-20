/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiAgente;

import Funciones.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Aaron Jaramillo
 */
public class AgenteTemperatura extends Agent {

    private long tini;

    protected void setup() {
        tini = System.currentTimeMillis();
        addBehaviour(new miTicker(this, 1000));
    }

    private class miTicker extends TickerBehaviour {

        int minticks;

        public miTicker(Agent a, long intervalo) {
            super(a, intervalo);
            minticks = 0;
        }

        protected void onTick() {
            long tfin = System.currentTimeMillis() - tini;
            int nticks = getTickCount(); // obtenemos el numero de ticks desde el último reset
            minticks++;
            
            String mensaje = "";
            int valor = Funciones.generarRR(0, 100);
            
            //System.out.println(valor+"ºC es "+fc.Temperatura(valor));
            mensaje = Integer.toString(valor);
            
            //------------------ENVIAR
            //System.out.println(getLocalName()+" preparando para enviar mensaje");
            AID id = new AID();
            id.setLocalName("fusi");
            
            ACLMessage msm = new ACLMessage(ACLMessage.INFORM);
            msm.setSender(getAID());
            msm.setLanguage("Español");
            msm.addReceiver(id);
            msm.setContent(mensaje);
            send(msm);
            //System.out.println("Enviando mensaje a "+id.getLocalName());
        }
    }
}
