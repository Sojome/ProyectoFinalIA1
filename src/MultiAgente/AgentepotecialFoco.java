/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiAgente;

import Funciones.*;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author user
 */
public class AgentepotecialFoco extends Agent {
    
    long tini;
    int[] comp = new int[2];
    
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
        
        public void reset() {
            super.reset();
            //minticks = 0;
            //System.out.println("reseteo!");
        }
        
        protected void onTick() {
            long tfin = System.currentTimeMillis() - tini;
            int nticks = getTickCount(); // obtenemos el numero de ticks desde el último reset
            minticks++;
            
            String estado = "";
            block();
            ACLMessage msm = receive();
            //System.out.println(getLocalName() + " esta esperando recibir un mensaje");
            if (msm != null) {
                //System.out.println("LLego mensaje de " + msm.getSender().getLocalName());
                if (msm.getSender().getLocalName().equals("luz")) {
                    comp[0] = Integer.parseInt(msm.getContent());
                }
                if (msm.getSender().getLocalName().equals("fusi")) {
                    comp[1] = Integer.parseInt(msm.getContent());
                }

                //Luz exterior baja
                // Y Temperatura fria
                if ((comp[0] >= 0 && comp[0] < 30) && (comp[1] >= 0 && comp[1] < 20)) {
                    estado = "prendida";
                // Y Temperatura ambiente
                } else if ((comp[0] >= 0 && comp[0] < 30) && (comp[1] >= 20 && comp[1] < 35)) {
                    estado = "medio prendida";
                // Y Temperatura caliente   
                } else if ((comp[0] >= 0 && comp[0] < 30) && (comp[1] >= 35 && comp[1] <= 100)) {
                    estado = "medio prendida";
                }
                
                //Luz exterior ambiente
                // Y Temperatura fria
                else if ((comp[0] >= 30 && comp[0] < 70) && (comp[1] >= 0 && comp[1] < 20)) {
                    estado = "medio prendida";
                // Y Temperatura ambiente
                } else if ((comp[0] >= 30 && comp[0] < 70) && (comp[1] >= 20 && comp[1] < 35)) {
                    estado = "medio prendida";
                // Y Temperatura caliente   
                } else if ((comp[0] >= 30 && comp[0] < 70) && (comp[1] >= 35 && comp[1] <= 100)) {
                    estado = "apagada";
                }
                
                //Luz exterior alta
                // Y Temperatura fria
                else if ((comp[0] >= 70 && comp[0] <= 100) && (comp[1] >= 0 && comp[1] < 20)) {
                    estado = "medio prendida";
                // Y Temperatura ambiente
                } else if ((comp[0] >= 70 && comp[0] <= 100) && (comp[1] >= 20 && comp[1] < 35)) {
                    estado = "medio prendida";
                // Y Temperatura caliente   
                } else if ((comp[0] >= 70 && comp[0] <= 100) && (comp[1] >= 35 && comp[1] <= 100)) {
                    estado = "apagada";
                }
            }
            
            if (nticks == 4) {
                if (!estado.equals("")) {
                    System.out.println("----------------------------------------");
                    System.out.println(
                            "--*Luz exterior: " + comp[0] + " lm es "+Funciones.LuzExterior(comp[0])
                            + "\n--*Fusión temperatura: " + comp[1] + "ºC es "+Funciones.Temperatura(comp[1])
                    );
                    System.out.println("--/El potencial del foco esta " + estado);
                    estado = "";
                }
                reset();
            } /*else {
                System.out.println(nticks + "    " + minticks);
            }*/
            
            /*
             //Matar al agente
             if(minticks == 40){
             myAgent.doDelete();
             }*/
        }
    }
}
