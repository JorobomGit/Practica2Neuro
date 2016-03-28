/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neurop2;

import java.io.BufferedReader;
import java.io.FileReader;

public class NeuroEj21 {

    public static void main(String[] args) {
        // TODO code application logic here
        //Creamos un determinado numero de neuronas.
        if(args.length == 5){
        	/*
        	 * 
        	 * 
        	 * El nombre del fichero con los datos del problema
- El % del conjunto de training
- La tasa de aprendizaje
- El número de neuronas de la capa oculta.
- El nombre de un fichero en el que el programa escribirá en cada línea el
error cuadrático medio de la red para cada época, así como los valores
de los pesos de la red. Entenderemos que ha transcurrido una época
cuando todos los patrones de entrenamiento ha
        	 */
            try{ 
	        	FileReader f = new FileReader(args[0]);
	            BufferedReader b = new BufferedReader(f);           
	            String linea = "", lineaFinal="";
	            linea = b.readLine();
	            String[] params = linea.split(" ");
	            int numEntradas = Integer.parseInt(params[0]);
	            int numSalidas = Integer.parseInt(params[1]);
	            int numCapaOculta = Integer.parseInt(args[1]);
	            RedNeuronal red = new RedNeuronal(numEntradas,numCapaOculta, numSalidas);
	            while((linea = b.readLine()) != null){
	                red.impulso(linea);
	                System.out.println(red.getSalidaFinal());
	                lineaFinal = linea;
	            }
	            b.close();
            }catch(Exception e){
                System.out.println("Error leyendo el fichero.");
                e.printStackTrace();
            }
            
            
        }else{
            System.out.println("ERROR. Falta pasar el fichero por argumento.");
        }  
        
    }
    
}
