/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neurop2;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class NeuroEj21 {

    public static void main(String[] args) {
        // TODO code application logic here
        //Creamos un determinado numero de neuronas.
        String entrada = "problema-real-3clases.txt";
        int training = 80;
        int tasa = 1;
        String ocultas = "5";

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
                String linea = "";
                linea = b.readLine();
                String[] params = linea.split(" ");
                List<String> datos = new ArrayList<>();
                int numEntradas = Integer.parseInt(params[0]);
                int numSalidas = Integer.parseInt(params[1]);
                int numCapaOculta = Integer.parseInt(ocultas);
                System.out.println("Numero de neuronas entrada: " + numEntradas);
                System.out.println("Numero de neuronas oculta: " + numSalidas);
                System.out.println("Numero de neuronas salida: " + numCapaOculta);
                RedNeuronal red = new RedNeuronal(numEntradas, numCapaOculta, numSalidas, tasa);
                linea = b.readLine();
                while ((linea = b.readLine()) != null) {
                	datos.add(linea);
                }
                double porcentaje = training/100.0;
                int numTrain = (int)(datos.size() * porcentaje);
                int numTest = datos.size() - numTrain;
                Collections.shuffle(datos);
                String datosTrain[] = new String[numTrain];
                String datosTest[] = new String[numTest];
                for(int index = 0; index < numTrain; index++){
                	datosTrain[index] = datos.get(index);
                }
                for(int contador = 0, index = numTrain; contador < numTest; contador++, index++){
                	datosTest[contador] = datos.get(index);
                }
                /*Condicion de parada*/
                for(int i = 0; i < datosTrain.length; i++) {
                	
                    /*En este punto tenemos la red neuronal creada*/
                    /*****FEEDFORWARD*****/
                    /*Comienza el algoritmo con la primera tupla del fichero*/
                    /*Paso 3: Establecer activaciones a neuronas de entrada*/
                    red.activarCapaEntrada(datosTrain[i]);
                    red.actualizarEntradasCapaOculta();
                    /*Paso 4: Calcular respuesta de neuronas capa oculta*/
                    red.respuestasCapaOculta();
                    red.actualizarEntradasCapaSalida();
                    /*Paso 5: Calcular respuesta de neuronas de salida*/
                    red.respuestasCapaSalida();
                    /*****FIN FEEDFORWARD*****/
                    /*****RETROPROPAGACION DEL ERROR*****/
                    /*Paso 6: Calculamos error, necesitamos obtener la clase de entrenamiento*/
                    ArrayList<Double> errores = red.calcularErroresSalida();
                    System.out.println(datosTrain[i]);
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
                    /******FIN ERRORES********/
                    /*****ACTUALIZAR PESOS Y SESGOS****/
                    /*Paso 8: Actualizamos pesos y sesgos de ambas capas*/
                    red.actualizarSalida(correcciones1, correccionesSesgo1);
                    red.actualizarOculta(correcciones2, correccionesSesgo2);
                    
                    /*Paso 9: Condicion de parada*/
                }
                
                // Fase de clasificacion
                int errores = 0;
                for(int i = 0; i < numTest; i++){
                	String aux[] = datosTest[i].split(" ");
                	String entradas = "";
                	String claseReal = "";
                	for(int j = 0; j < numEntradas; j++){
                		entradas += aux[j] + " ";
                	}
                	for(int index = numEntradas, j = 0; j < numSalidas; j++, index++){
                		if(j == (numSalidas - 1)){
                			claseReal += aux[index];
                		}else{
                    		claseReal += aux[index] + " ";
                		}
                	}
                	red.explotacionEntrada(entradas);
                	red.explotacionCapaOculta();
                	red.explotacionCapaSalida();
                	String clasePred = red.getSalidaFinal();
                	if(!clasePred.equals(claseReal)){
                		errores++;
                	}
                }
                double error = errores / numTrain;
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
