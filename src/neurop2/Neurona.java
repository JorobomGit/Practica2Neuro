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
    
    public Neurona(ArrayList<Double> entrada, ArrayList<Double> peso){
    	this.entrada = entrada;
    	this.peso = peso;
    	this.salida = 0;
        this.sesgo = 1;
    }

    public double getSesgo() {
        return sesgo;
    }

    public void setSesgo(double sesgo) {
        this.sesgo = sesgo;
    }

    public ArrayList<Double> getPesos() {
        return peso;
    }

    public void setPesos(ArrayList<Double> peso) {
        this.peso = peso;
    }

    public void calcularSalida(double sesgo){
        double sumatorio=0;
        for(int i=0; i<peso.size();i++){
            sumatorio+=peso.get(i)*entrada.get(i);
        }
    	salida = sesgo + sumatorio;
    }
    
    public void calcularSalidasExplotacion(){
    	double sumatorio=0;
        for(int i=0; i<peso.size();i++){
            sumatorio+=peso.get(i)*entrada.get(i);
        }
    	double z_in = sesgo + sumatorio;
    	double divisor = 1 + Math.exp(-z_in);
    	salida = (2/divisor) - 1;
    }
    
    public double getSalida(){
    	return this.salida;
    }

    public void setSalida(double salida) {
        this.salida = salida;
    }

    public void setEntrada(ArrayList<Double> entrada){
    	this.entrada = entrada;
    }
    
    
}
