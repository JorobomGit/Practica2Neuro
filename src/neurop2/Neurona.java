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
public class Neurona {
    private ArrayList<Integer> entrada = null;
    private int salida;
    private int umbral;
    private int peso;
    
    public Neurona(ArrayList<Integer> entrada, int umbral, int peso){
    	this.entrada = entrada;
    	this.peso = peso;
    	this.salida = 0;
    	this.umbral = umbral;
    }

    public void calcularSalida(){
    	int acumulado = 0;
    	for(int entrada : this.entrada){
    		acumulado += entrada * peso;
    	}
    	if(acumulado<umbral){
    		salida = 0;
    	}else{
    		salida = 1;
    	}
    }
    
    public int getSalida(){
    	return this.salida;
    }
    
    public void setEntrada(ArrayList<Integer> entrada){
    	this.entrada = entrada;
    }
}
