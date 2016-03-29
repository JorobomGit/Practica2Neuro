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
    private ArrayList<Double> entrada = null;
    private double salida;
    private ArrayList<Double> peso = null;
    private double sesgo;
    
    public Neurona(ArrayList<Double> entrada, ArrayList<Double> peso, double sesgo){
    	this.entrada = entrada;
    	this.peso = peso;
    	this.salida = 0;
    	this.sesgo = sesgo;
    }

    public void calcularSalida(){
    	
    }
    
    public double getSalida(){
    	return this.salida;
    }
    
    public void setEntrada(ArrayList<Double> entrada){
    	this.entrada = entrada;
    }
}
