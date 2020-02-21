/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorDifuso;

import MultiAgente.*;
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

            //String estado = "";
            Double solucion = null;
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

                //estado = Double.toString(PotencialFoco.Fusifucador(comp[0], comp[1]));
                solucion = PotencialFoco.Defusifucador(comp[0], comp[1]);
            }

            if (nticks == 4) {
                if (solucion != null) {
                    System.out.println("----------------------------------------");
                    System.out.println(
                            "--*Luz exterior: " + comp[0] + " lm es " + Funciones.LuzExterior(comp[0])
                            + "\n--*Fusión temperatura: " + comp[1] + "ºC es " + Funciones.Temperatura(comp[1])
                    );
                    System.out.printf("--/El potencial del foco: %.2f l esta %s\n", solucion, Funciones.Potencial(solucion));
                    //estado = "";
                    solucion = null;
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
