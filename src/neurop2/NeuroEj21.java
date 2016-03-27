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
        if(args.length == 1){
            try{ 
	        	FileReader f = new FileReader(args[0]);
	            BufferedReader b = new BufferedReader(f);           
	            String linea = "", lineaFinal="";
	            RedNeuronal red = new RedNeuronal();
	            while((linea = b.readLine()) != null){
	                red.impulso(linea);
	                System.out.println(red.getSalidaFinal());
	                lineaFinal = linea;
	            }
	            red.impulsoFinal(lineaFinal);
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
