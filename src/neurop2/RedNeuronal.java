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

    public RedNeuronal(int numEntradas, int numSalidas) {
    	Capa capaAux = null;
    	ArrayList<Neurona> neuronas = new ArrayList<>();
    	for(int i =0; i<numEntradas; i++){
    		Neurona neurona = new Neurona();
    		neuronas.add(neurona);
    	}
    	capaAux = new Capa(neuronas);
    	capas.add(capaAux);
    	
    	capaAux = null;
    	neuronas = new ArrayList<>();
    	for(int i =0; i<numEntradas*2; i++){
    		Neurona neurona = new Neurona();
    		neuronas.add(neurona);
    	}
    	capaAux = new Capa(neuronas);
    	capas.add(capaAux);
    	
    	capaAux = null;
    	neuronas = new ArrayList<>();
    	for(int i =0; i<numSalidas; i++){
    		Neurona neurona = new Neurona();
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
    
    public void impulsoFinal(String linea){
    	this.impulso(linea);
    	System.out.println(this.getSalidaFinal());
    }
    
    private void actualizarEntradas(String entradas){
    	String entrada[] = entradas.split(" ");
    	ArrayList<Integer> entradasAux = new ArrayList<>();
    	ArrayList<Integer> nuevasEntradas = null;
    	for(String aux : entrada){
    		entradasAux.add(Integer.parseInt(aux));
    	}
    	
    	//Actualizamos la primera capa
    	for(int index = 0; index < entradasAux.size(); index++){
    		nuevasEntradas = new ArrayList<Integer>();
    		nuevasEntradas.add(entradasAux.get(index));
    		capas.get(0).setEntradasNeurona(nuevasEntradas, index);
    	}
    	
    	//Actualizamos la segunda capa
    	ArrayList<Integer> salidasAux = capas.get(0).getSalidas();
    	nuevasEntradas = new ArrayList<Integer>();
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
