/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.examen2p2_hector_21911312;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author eucm2
 */
public class HiloJuego implements Runnable {

    JTextArea taJuego;
    JTextField txtLetras;
    public String ultima = "d";
    public String movimiento = "adelante";
    public String[][] lienzo = new String[33][13];
    public String[][] serpiente = new String[33][13];
    public int serpienteTam = 4;
    public int serpientePosX = 20;
    public int serpientePosY = 6;
    public List<Integer> posX;
    public List<Integer> posY;
    //POSICION DE LA COMIDA QUE VAMOS A COMER
    public String[][] comida = new String[33][13];
    public int comidaPosX = 0;
    public int comidaPosY = 0;
    //COLOCAMOS LOS VALORES DE LA SERPIENTE EN ""
    private void limpiSerpiente() {

        for (int y = 0; y < 13; y++) {
            for (int x = 0; x < 33; x++) {
                serpiente[x][y] = "";
            }
        }
    }
    //COLOCAMOS LOS VALORES DE LA SERPIENTE EN ""
    private void limpiaComida() {

        for (int y = 0; y < 13; y++) {
            for (int x = 0; x < 33; x++) {
                comida[x][y] = "";
            }
        }
    }
    private void posisionAleatoriaComida(){
        comidaPosX = 0;
        Random rd = new Random();
        comidaPosX = rd.nextInt(32);
        comidaPosY = 0;
        comidaPosY = rd.nextInt(12);
        for (int y = 0; y < 13; y++) {
            for (int x = 0; x < 33; x++) {
                if(comidaPosX==x && comidaPosY==y)
                comida[x][y] = "O";
            }
        }
    }
    HiloJuego(JTextArea taJuego, JTextField txtLetras) {
        this.posX = new ArrayList<Integer>();
        this.posY = new ArrayList<Integer>();
        this.taJuego = taJuego;
        this.txtLetras = txtLetras;
        //INICIAMOS LA SERPIENTE PARA QUE NO DE ERROR DE NULL POINT
        limpiSerpiente();
        //INICIAMOS LA POSICION D EL SERPIENTE EN ESTAS CORDENADAS X y Y
        posX.add(10);
        posY.add(6);

        posX.add(9);
        posY.add(6);

        posX.add(8);
        posY.add(6);

        posX.add(7);
        posY.add(6);
        //COLOCA LAS CORDENADAS DE LA SERPIENTE
        posisionMatrizSerpiente();
        //INICIAMOS LA POSICION DE LA COMIDA
        limpiaComida();
        posisionAleatoriaComida();
    }

    //COLOCA LAS CORDENADAS DE LA SERPIENTE
    public void posisionMatrizSerpiente() {
        List<Integer> posX2 = new ArrayList<Integer>();
        List<Integer> posY2 = new ArrayList<Integer>();
        for (int i = 0; i < serpienteTam; i++) {
            posX2.add(posX.get(i));
            posY2.add(posY.get(i));
        }
        if (movimiento.equals("adelante")) {
            for (int i = 0; i < serpienteTam; i++) {
                if (i == 0) {
                    posX.set(i, posX.get(i) + 1);
                }
                if (i > 0) {
                    posX.set(i, posX2.get(i - 1));
                    posY.set(i, posY2.get(i - 1));
                }
            }

        }
        if (movimiento.equals("atras")) {
            for (int i = 0; i < serpienteTam; i++) {
                if (i == 0) {
                    posX.set(i, posX.get(i) - 1);
                }
                if (i > 0) {
                    posX.set(i, posX2.get(i - 1));
                    posY.set(i, posY2.get(i - 1));
                }
            }
        }
        if (movimiento.equals("arriba")) {
            for (int i = 0; i < serpienteTam; i++) {
                if (i == 0) {
                    posY.set(i, posY.get(i) - 1);
                }
                if (i > 0) {
                    posX.set(i, posX2.get(i - 1));
                    posY.set(i, posY2.get(i - 1));
                }
            }

        }
        if (movimiento.equals("abajo")) {
            for (int i = 0; i < serpienteTam; i++) {
                if (i == 0) {
                    posY.set(i, posY.get(i) + 1);
                }
                if (i > 0) {
                    posX.set(i, posX2.get(i - 1));
                    posY.set(i, posY2.get(i - 1));
                }
            }
        }
        //COLOCAMOS LOS VALORES DE LA SERPIENTE EN ""
        limpiSerpiente();
        //COLOCA @ SI ES CABEZA X SI ES COLA
        for (int i = 0; i < serpienteTam; i++) {
            if (i == 0) {
                serpiente[posX.get(i)][posY.get(i)] = "@";
            } else {
                serpiente[posX.get(i)][posY.get(i)] = "X";
            }
        }
        int a = 0;

    }

    public void run() {
        try {

            while (true) {
                System.out.println(ultima);
                movimiento = seMueve(ultima);
                System.out.println(movimiento);
                //DIBUJA MARCO EN AL MATRIZ
                for (int y = 0; y < 13; y++) {
                    for (int x = 0; x < 33; x++) {
                        //DIBUJAMOS EL LIENZO CON ESPACIO VACIO
                        lienzo[x][y] = " ";
                        //SI ES MARCO DIBUJAMOS ASTERICO
                        if (y == 0 || y == 12) {
                            lienzo[x][y] = "*";
                        }
                        if (x == 0 || x == 32) {
                            lienzo[x][y] = "*";
                        }
                        //SI ES SERPIENTE DIBUJAMOS EL VALOR DE LA SERPIENTE
                        if (serpiente[x][y].length() > 0) {
                            lienzo[x][y] = serpiente[x][y];
                        }
                        //SI ES COMIDA DIBUJAMOS EL VALOR DE LA COMIDA
                        if (comida[x][y].length() > 0) {
                            lienzo[x][y] = comida[x][y];
                        }
                        if(serpiente[x][y].equals("@") && comida[x][y].equals("O")){
                            serpienteTam++;
                            posX.add(x);
                            posY.add(y);
                            limpiaComida();
                            posisionAleatoriaComida();
                        }
                    }
                }

                String aDibujar = "";
                //DIBUJA MARCO EN EL TEXTAREA CON STRING
                for (int y = 0; y < 13; y++) {
                    for (int x = 0; x < 33; x++) {

                        aDibujar = aDibujar + lienzo[x][y];
                    }
                    aDibujar = aDibujar + "\n";
                }
                //COLOCA LAS CORDENADAS DE LA SERPIENTE
                posisionMatrizSerpiente();
                //ESCRIBIMOS EL LIENZO Y LA SEPIENTE EN EL TEXTAREA
                taJuego.setText(aDibujar);
                pausa(500);
                //taJuego.setText("");
                ultima = ultimaLetra(this.txtLetras.getText());
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            taJuego.setText(taJuego.getText()+"PERDISTE");
        }
    }

    public String seMueve(String ultima) {
        if (ultima.equals("w") || ultima.equals("W") || ultima.equals("8")) {
            return "arriba";
        }
        if (ultima.equals("s") || ultima.equals("S") || ultima.equals("5")) {
            return "abajo";
        }
        if (ultima.equals("a") || ultima.equals("A") || ultima.equals("4")) {
            return "atras";
        }
        if (ultima.equals("d") || ultima.equals("D") || ultima.equals("6")) {
            return "adelante";
        }
        return "";
    }

    public String ultimaLetra(String texto) {
        try {
            return texto.substring(texto.length() - 1);
        } catch (Exception e) {
            return "";
        }
    }

    public void pausa(long sleeptime) {
        try {
            Thread.sleep(sleeptime);
        } catch (InterruptedException ex) {
        }
    }
}
