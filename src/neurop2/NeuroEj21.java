/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neurop2;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class NeuroEj21 {

    public static void main(String[] args) {
        // TODO code application logic here
        //Creamos un determinado numero de neuronas.
        String entrada = "problema-real4.txt";
        int training = 100;
        double tasa = 0.02;
        String ocultas = "4";
        int maxEpocas = 3000;
        String salida = "salida.txt";
        

        System.out.println(args.length);
        if (args.length == 0) {
            /*
        	 * 
        	 * 
        	 * El nombre del fichero con los datos del problema
                - El % del conjunto de training
                - La tasa de aprendizaje
                - El n�mero de neuronas de la capa oculta.
                - El nombre de un fichero en el que el programa escribir� en cada l�nea el
                error cuadr�tico medio de la red para cada �poca, as� como los valores
                de los pesos de la red. Entenderemos que ha transcurrido una �poca
                cuando todos los patrones de entrenamiento ha
             */

            //Ejemplo de ejecucion: problema-real-3clases.txt 80 1 3 salida.txt
            try {
                FileReader f = new FileReader(entrada); //Abrimos fichero
                BufferedReader b = new BufferedReader(f);
                PrintWriter writer = new PrintWriter(salida, "UTF-8");
                String linea = "";
                linea = b.readLine();
                String[] params = linea.split(" ");
                List<String> datos = new ArrayList<>();
                int numEntradas = Integer.parseInt(params[0]);
                int numSalidas = Integer.parseInt(params[1]);
                int numCapaOculta = Integer.parseInt(ocultas);
                System.out.println("Numero de neuronas entrada: " + numEntradas);
                System.out.println("Numero de neuronas oculta: " + numCapaOculta);
                System.out.println("Numero de neuronas salida: " + numSalidas);
                RedNeuronal red = new RedNeuronal(numEntradas, numCapaOculta, numSalidas, tasa);
                while ((linea = b.readLine()) != null) {
                    datos.add(linea);
                }
                double porcentaje = training / 100.0;
                int numTrain = (int) (datos.size() * porcentaje);
                int numTest;
                if (training < 100) {
                    numTest = datos.size() - numTrain;
                } else {
                    numTest = numTrain;
                }

                Collections.shuffle(datos);
                String datosTrain[] = new String[numTrain];

                /*Obtenemos datos de train y datos de test*/
                for (int index = 0; index < numTrain; index++) {
                    datosTrain[index] = datos.get(index);
                }
                String datosTest[] = new String[numTest];
                /*Explotacion datos*/
                if (training < 100) {
                    for (int contador = 0, index = numTrain; contador < numTest; contador++, index++) {
                        datosTest[contador] = datos.get(index);
                    }
                } else {
                    for (int index = 0; index < numTrain; index++) {
                        datosTest[index] = datos.get(index);
                    }
                }

                /**
                 * **TRAINING***
                 */
                /*Condicion de parada comprobamos las epocas*/
 /*Error cuadratico minimizado*/
                double epocas = 0;
                double errorCuadratico;
                do {
                    ArrayList<Double> cuadraticos = new ArrayList<>();

                    for (int i = 0; i < datosTrain.length; i++) {
                        //System.out.println("*******************");
                        /*En este punto tenemos la red neuronal creada*/
                        /**
                         * ***FEEDFORWARD****
                         */
                        /*Comienza el algoritmo con la primera tupla del fichero*/
 /*Paso 3: Establecer activaciones a neuronas de entrada*/
                        red.activarCapaEntrada(datosTrain[i]);
                        /*Paso 4: Calcular respuesta de neuronas capa oculta*/
                        red.respuestasCapaOculta();
                        /*Paso 5: Calcular respuesta de neuronas de salida*/
                        red.respuestasCapaSalida();

                        /**
                         * ***RETROPROPAGACION DEL ERROR****
                         */
                        /*Paso 6: Calculamos error, necesitamos obtener la clase de entrenamiento*/
                        ArrayList<Double> errores = red.calcularErroresSalida();
                        /*Calcular correcciones de peso*/
                        ArrayList<Double> correcciones1 = red.correccionPesosSalida(errores);
                        /*Calcular correcciones sesgo*/
                        ArrayList<Double> correccionesSesgo1 = red.correccionSesgoSalida(errores);

                        /*Paso 7: Cada neurona de la oculta suma sus entradas delta*/
                        errores = red.calcularErroresOculta(errores);
                        /*Calcular correcciones de peso*/
                        ArrayList<Double> correcciones2 = red.correccionPesosOculta(errores);
                        /*Calcular correcciones sesgo*/
                        ArrayList<Double> correccionesSesgo2 = red.correccionSesgoOculta(errores);

                        /**
                         * ***ACTUALIZAR PESOS Y SESGOS***
                         */
                        /*Paso 8: Actualizamos pesos y sesgos de ambas capas*/
                        red.actualizarSalida(correcciones1, correccionesSesgo1);
                        red.actualizarOculta(correcciones2, correccionesSesgo2);

                        /*Paso 9: Condicion de parada (epocas)*/
                        cuadraticos.add(red.errorCuadratico());
                    }
                    epocas++;
                    writer.println(red.errorCuadratico() + " " + red.getCapas().get(1).getPesos() + " " + red.getCapas().get(2).getPesos());

                    errorCuadratico =0;
                    for(int cua=0;cua<cuadraticos.size();cua++){
                        errorCuadratico += cuadraticos.get(cua);
                    }
                    //System.out.println(errorCuadratico);
                    errorCuadratico = errorCuadratico/cuadraticos.size();
                    System.out.println(errorCuadratico);
                } while (errorCuadratico>0.05 && epocas < 3000);
                
                System.out.println("Epocas realizadas: " + epocas);
                writer.close();
                /**
                 * **CLASIFICACION***
                 */
                /*Tenemos nuestra red con los pesos actualizados en este momento*/
                int errores = 0;
                /*Paso1 Para todos los datos de test*/
                for (int i = 0; i < numTest; i++) {
                    String aux[] = datosTest[i].split(" ");
                    String entradas = "";
                    String claseReal = "";
                    for (int j = 0; j < numEntradas; j++) {
                        entradas += aux[j] + " ";
                    }
                    for (int index = numEntradas, j = 0; j < numSalidas; j++, index++) {
                        if (j == (numSalidas - 1)) {
                            claseReal += aux[index];
                        } else {
                            claseReal += aux[index] + " ";
                        }
                    }
                    /*Paso 3: Establecer activaciones a neuronas de entrada*/
                    //System.out.println("-----------------------------");
                    // System.out.println("Datos test: " + datosTest[i]);
                    red.activarCapaEntrada(datosTest[i]);
                    /*Paso 4: Calcular respuesta de neuronas capa oculta*/
                    red.respuestasCapaOculta();
                    /*Paso 5: Calcular respuesta de neuronas de salida*/
                    red.respuestasCapaSalida();

                    String clasePred = red.getSalidaFinal();
                    //System.out.println("Clase Predicha: " + clasePred);
                    // System.out.println("Clase Real: " + claseReal);
                    if (!clasePred.equals(claseReal)) {
                        errores++;
                    }
                }
                double error = (double) errores / numTest;
                System.out.println("Error: " + error);
                b.close();
                f.close();

            } catch (Exception e) {
                System.out.println("Error leyendo el fichero.");
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR con los ARGUMENTOS del programa.");
        }
    }
}
