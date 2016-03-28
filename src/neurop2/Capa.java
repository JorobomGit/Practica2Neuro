/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neurop2;

import java.util.ArrayList;

/**
 *
 * @author Jose
 */
public class Capa {
    private ArrayList<Neurona> neuronas = new ArrayList<>();

    public Capa(ArrayList<Neurona> neuronas) {
        this.neuronas = neuronas;
    }

    public void actualizarCapa(){
    	for(Neurona neurona : neuronas){
    		neurona.calcularSalida();
    	}
    }
    
    public ArrayList<Double> getSalidas(){
    	ArrayList<Double> salidas = new ArrayList<>();
    	for(Neurona neurona : neuronas){
    		salidas.add(neurona.getSalida());
    	}
    	return salidas;
    }
    
    public String salidasToString(){
    	String respuesta="";
    	for(int i = 0; i<neuronas.size();i++){
    		respuesta += Double.toString(neuronas.get(i).getSalida());
    		if(i != (neuronas.size()-1)){
    			respuesta += " ";
    		}
    	}
    	return respuesta;
    }
    
    public void setEntradasNeurona(ArrayList<Double> entradas, int index){
    	neuronas.get(index).setEntrada(entradas);
    }
    
}
