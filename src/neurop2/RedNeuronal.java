/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neurop2;

import java.util.ArrayList;
import java.util.Arrays;

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
    	for(int i =0; i<numCapaOculta; i++){
    		double peso = Math.random() * 0.5;
        	double sesgo = Math.random() * 0.5;
        	ArrayList<Double> entradas = new ArrayList<>();
        	for(int j =0; i<numEntradas; i++){
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
        	for(int j =0; i<numCapaOculta; i++){
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
    	for(int index = 0; index < entradasAux.size(); index++){
    		nuevasEntradas = new ArrayList<Double>();
    		nuevasEntradas.add(entradasAux.get(index));
    		capas.get(0).setEntradasNeurona(nuevasEntradas, index);
    	}
    	
    	//Actualizamos la segunda capa
    	ArrayList<Double> salidasAux = capas.get(0).getSalidas();
    	for(int index = 0; index < numCapaOculta; index ++){
    		nuevasEntradas = new ArrayList<Double>();
    		
    	}
    	
    	nuevasEntradas.add(entradasAux.get(2));
    	nuevasEntradas.add(salidasAux.get(0));
    	capas.get(1).setEntradasNeurona(nuevasEntradas, 0);
    	nuevasEntradas = new ArrayList<Integer>();
    	nuevasEntradas.add(entradasAux.get(0));
    	nuevasEntradas.add(salidasAux.get(1));
    	capas.get(1).setEntradasNeurona(nuevasEntradas, 1);
    	nuevasEntradas = new ArrayList<Integer>();
    	nuevasEntradas.add(entradasAux.get(1));
    	nuevasEntradas.add(salidasAux.get(2));
    	capas.get(1).setEntradasNeurona(nuevasEntradas, 2);
    	    	
    	nuevasEntradas = new ArrayList<Integer>();
    	nuevasEntradas.add(entradasAux.get(1));
    	nuevasEntradas.add(salidasAux.get(0));
    	capas.get(1).setEntradasNeurona(nuevasEntradas, 3);
    	nuevasEntradas = new ArrayList<Integer>();
    	nuevasEntradas.add(entradasAux.get(2));
    	nuevasEntradas.add(salidasAux.get(1));
    	capas.get(1).setEntradasNeurona(nuevasEntradas, 4);
    	nuevasEntradas = new ArrayList<Integer>();
    	nuevasEntradas.add(entradasAux.get(0));
    	nuevasEntradas.add(salidasAux.get(2));
    	capas.get(1).setEntradasNeurona(nuevasEntradas, 5);
    	
    	//Actualizamos la tercera capa
    	
    	nuevasEntradas = new ArrayList<>();
    	salidasAux = new ArrayList<>();
    	salidasAux = capas.get(1).getSalidas();
    	nuevasEntradas.add(salidasAux.get(0));
    	nuevasEntradas.add(salidasAux.get(1));
    	nuevasEntradas.add(salidasAux.get(2));
    	capas.get(2).setEntradasNeurona(nuevasEntradas, 0);
    	
    	nuevasEntradas = new ArrayList<>();
    	nuevasEntradas.add(salidasAux.get(3));
    	nuevasEntradas.add(salidasAux.get(4));
    	nuevasEntradas.add(salidasAux.get(5));
    	capas.get(2).setEntradasNeurona(nuevasEntradas, 1);   	
    }
    
    public String getSalidaFinal(){
    	return capas.get(2).salidasToString();
    }
}
