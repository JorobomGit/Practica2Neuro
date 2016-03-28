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
public class RedNeuronal {
    private ArrayList<Capa> capas = new ArrayList<>();
    private int numEntradas, numCapaOculta, numSalidas;

    public RedNeuronal(int numEntradas, int numCapaOculta, int numSalidas) {
    	this.numEntradas = numEntradas;
    	this.numCapaOculta = numCapaOculta;
    	this.numSalidas = numSalidas;
    	Capa capaAux = null;
    	ArrayList<Neurona> neuronas = new ArrayList<>();
    	for(int i =0; i<numEntradas; i++){
    		double peso = Math.random() * 0.5;
        	double sesgo = Math.random() * 0.5;
        	ArrayList<Double> entradas = new ArrayList<>();
        	entradas.add(0.0);
    		Neurona neurona = new Neurona(entradas,peso, sesgo);
    		neuronas.add(neurona);
    	}
    	capaAux = new Capa(neuronas);
    	capas.add(capaAux);
    	
    	capaAux = null;
    	neuronas = new ArrayList<>();
    	for(int i =0; i<numCapaOculta; i++){
    		double peso = Math.random() * 0.5;
        	double sesgo = Math.random() * 0.5;
        	ArrayList<Double> entradas = new ArrayList<>();
        	for(int j =0; j<numEntradas; j++){
        		entradas.add(0.0);
        	}
    		Neurona neurona = new Neurona(entradas,peso, sesgo);
    		neuronas.add(neurona);
    	}
    	capaAux = new Capa(neuronas);
    	capas.add(capaAux);
    	
    	capaAux = null;
    	neuronas = new ArrayList<>();
    	for(int i =0; i<numSalidas; i++){
    		double peso = Math.random() * 0.5;
        	double sesgo = Math.random() * 0.5;
        	ArrayList<Double> entradas = new ArrayList<>();
        	for(int j =0; j<numCapaOculta; j++){
        		entradas.add(0.0);
        	}
    		Neurona neurona = new Neurona(entradas,peso, sesgo);
    		neuronas.add(neurona);
    	}
    	capaAux = new Capa(neuronas);
    	capas.add(capaAux);
    }
    
    //Metodo que actualiza todos los valores de las neuronas para un nuevo impulso
    //sumatorio de peso*valor
    public void impulso(String entrada){
        //Empezamos recorriendo por la ultima capa para que actualice correctamente
    	actualizarEntradas(entrada);
    	
    	for(Capa capa : capas){
    		capa.actualizarCapa();
    	}
    }
    
    private void actualizarEntradas(String entradas){
    	String entrada[] = entradas.split(" ");
    	ArrayList<Double> entradasAux = new ArrayList<>();
    	ArrayList<Double> nuevasEntradas = null;
    	for(String aux : entrada){
    		entradasAux.add(Double.parseDouble(aux));
    	}
    	
    	//Actualizamos la primera capa
    	for(int index = 0; index < numEntradas; index++){
    		nuevasEntradas = new ArrayList<Double>();
    		nuevasEntradas.add(entradasAux.get(index));
    		capas.get(0).setEntradasNeurona(nuevasEntradas, index);
    	}
    	/*
    	 * 
    	 * Calculamos las nuevas salidas para la capa 1 con el algoritmo
    	 * 
    	 */
    	//Actualizamos la segunda capa
    	ArrayList<Double> salidasAux = capas.get(0).getSalidas();
    	for(int index = 0; index < numCapaOculta; index ++){
    		nuevasEntradas = new ArrayList<Double>();
    		for(double salida : salidasAux){
    			nuevasEntradas.add(salida);
    		}
    		capas.get(1).setEntradasNeurona(nuevasEntradas, index);
    	}
    	/*
    	 * 
    	 * Calculamos las nuevas salidas para la capa 2 con el algoritmo
    	 * 
    	 */
    	salidasAux = capas.get(1).getSalidas();
    	
    	for(int index = 0; index < numSalidas; index++){
    		nuevasEntradas = new ArrayList<>();
    		for(Double salida : salidasAux){
    			nuevasEntradas.add(salida);
    		}
    		capas.get(2).setEntradasNeurona(nuevasEntradas, index);
    	}
    	/*
    	 * 
    	 * Calculamos las nuevas salidas para la capa 1 con el algoritmo
    	 * Y a partir de aqui retropropagacion
    	 * 
    	 */
    	
    }
    
    public String getSalidaFinal(){
    	return capas.get(2).salidasToString();
    }
}
