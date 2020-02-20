/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiAgente;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Aaron Jaramillo
 */
public class AgenteFusion extends Agent {

    long tini;
    int[] fus = new int[2];
    int mitad;

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
            block();
            ACLMessage msm = receive();
            //System.out.println(getLocalName() + " esta esperando recibir un mensaje");
            if (msm != null) {
                //System.out.println("LLego");
                if (msm.getSender().getLocalName().equals("temp1")) {
                    fus[0] = Integer.parseInt(msm.getContent());
                }
                if (msm.getSender().getLocalName().equals("temp2")) {
                    fus[1] = Integer.parseInt(msm.getContent());
                }
                
                mitad = (fus[0] + fus[1]) / 2;
                
                mensaje = Integer.toString(mitad);
            }
            
            if (nticks == 3 && !mensaje.equals("")) {
                //------------------ENVIAR
                //System.out.println(getLocalName()+" preparando para enviar mensaje");
                AID id = new AID();
                id.setLocalName("poten");

                ACLMessage msm2 = new ACLMessage(ACLMessage.INFORM);
                msm2.setSender(getAID());
                msm2.setLanguage("Español");
                msm2.addReceiver(id);
                msm2.setContent(mensaje);
                send(msm2);

                mensaje = "";
                reset();
                //System.out.println("Enviando mensaje a "+id.getLocalName());
            }/*else{
                System.out.println(nticks +"    "+minticks);
            }*/
        }
    }
}
