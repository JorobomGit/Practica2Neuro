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
    private ArrayList<Double> patron = new ArrayList<>();
    private ArrayList<Double> y_in_aux = new ArrayList<>();
    private ArrayList<Double> z_in_aux = new ArrayList<>();
    private double tasa;

    public RedNeuronal(int numEntradas, int numCapaOculta, int numSalidas, double tasa) {
        this.numEntradas = numEntradas;
        this.numCapaOculta = numCapaOculta;
        this.numSalidas = numSalidas;
        this.tasa = tasa;
        Capa capaAux = null;
        ArrayList<Neurona> neuronas = new ArrayList<>();
        /*Capa de entrada*/
        for (int i = 0; i < numEntradas; i++) {
            /*Inicializamos pesos y sesgo a valores pequenos*/
            ArrayList<Double> pesos = new ArrayList<>();
            pesos.add(Math.random() * 0.5);

            ArrayList<Double> entradas = new ArrayList<>();
            entradas.add(0.0);
            Neurona neurona = new Neurona(entradas, pesos);
            neuronas.add(neurona);
        }
        double sesgo = Math.random() * 0.5;
        capaAux = new Capa(neuronas, sesgo);
        capas.add(capaAux);

        capaAux = null;
        neuronas = new ArrayList<>();
        /*Capa oculta*/
        for (int i = 0; i < numCapaOculta; i++) {
            ArrayList<Double> pesos = new ArrayList<>();
            ArrayList<Double> entradas = new ArrayList<>();
            for (int j = 0; j < numEntradas; j++) {
                pesos.add(Math.random() * 0.5);
                entradas.add(0.0);
            }
            Neurona neurona = new Neurona(entradas, pesos);
            neuronas.add(neurona);
        }
        sesgo = Math.random() * 0.5;
        capaAux = new Capa(neuronas, sesgo);
        capas.add(capaAux);

        capaAux = null;
        neuronas = new ArrayList<>();
        /*Capa salida*/
        for (int i = 0; i < numSalidas; i++) {
            ArrayList<Double> pesos = new ArrayList<>();
            ArrayList<Double> entradas = new ArrayList<>();
            for (int j = 0; j < numCapaOculta; j++) {
                pesos.add(Math.random() * 0.5);
                entradas.add(0.0);
            }
            Neurona neurona = new Neurona(entradas, pesos);
            neuronas.add(neurona);
        }
        sesgo = Math.random() * 0.5;
        capaAux = new Capa(neuronas, sesgo);
        capas.add(capaAux);
    }

    //Metodo que actualiza todos los valores de las neuronas para un nuevo impulso
    //sumatorio de peso*valor
    public void impulso(String entrada) {
        //Empezamos recorriendo por la ultima capa para que actualice correctamente
        actualizarEntradas(entrada);

        for (Capa capa : capas) {
            capa.actualizarCapa();
        }
    }

    private void actualizarEntradas(String entradas) {
        String entrada[] = entradas.split(" ");
        ArrayList<Double> entradasAux = new ArrayList<>();
        ArrayList<Double> nuevasEntradas = null;
        for (String aux : entrada) {
            entradasAux.add(Double.parseDouble(aux));
        }

        //Actualizamos la primera capa
        for (int index = 0; index < numEntradas; index++) {
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
        for (int index = 0; index < numCapaOculta; index++) {
            nuevasEntradas = new ArrayList<Double>();
            for (double salida : salidasAux) {
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

        for (int index = 0; index < numSalidas; index++) {
            nuevasEntradas = new ArrayList<>();
            for (Double salida : salidasAux) {
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

    public String getSalidaFinal() {
        return capas.get(2).salidasToString();
    }

    public void activarCapaEntrada(String activaciones) {
        /*Paso 3 del algoritmo*/
 /*Establecemos valores de entrada*/
        String[] valores = activaciones.split(" ");
        for (int i = 0; i < this.numEntradas; i++) {
            this.capas.get(0).setSalidas(valores);
        }
        /*Aprovechamos y obtenemos el patron*/
        for (int i = this.numEntradas; i < (this.numEntradas + this.numSalidas); i++) {
            patron.add(Double.parseDouble(valores[i]));
        }
    }

    /*Metodo para actualizar las entradas de la capa oculta*/
    public void actualizarEntradasCapaOculta() {
        ArrayList<Double> entradas = new ArrayList<>();
        entradas = this.capas.get(0).getSalidas();
        for (int i = 0; i < numCapaOculta; i++) {
            this.capas.get(1).setEntradasNeurona(entradas, i);
        }
    }

    public void respuestasCapaOculta() {
        ArrayList<Double> respuestas = new ArrayList<>();
        /*z_in = sesgo + sumatorio(pesos*entradas)*/
        this.capas.get(1).actualizarCapa();
        /*Guardamos de forma auxiliar y_in*/
        this.z_in_aux = this.capas.get(1).getSalidas();
        /*z = f(z_in)*/
 /*En este punto ya tenemos z_in actualizado, falta pasarlo por la funcion para obtener las respuestas*/
        for (int i = 0; i < numCapaOculta; i++) {
            /*Para cada neurona obtenemos respuesta*/
 /*La funcion de transferencia es la sigmoide bipolar*/
            respuestas.add(sigmoideBipolar(this.capas.get(1).getSalidas().get(i)));
        }
        this.capas.get(1).setSalidas(respuestas);
    }

    /*Metodo para actualizar las entradas de la capa oculta*/
    public void actualizarEntradasCapaSalida() {
        ArrayList<Double> entradas;
        entradas = this.capas.get(1).getSalidas();
        for (int i = 0; i < numSalidas; i++) {
            this.capas.get(2).setEntradasNeurona(entradas, i);
        }
    }

    public void respuestasCapaSalida() {
        ArrayList<Double> respuestas = new ArrayList<>();
        /*z_in = sesgo + sumatorio(pesos*entradas)*/
        this.capas.get(2).actualizarCapa();
        /*Guardamos de forma auxiliar y_in*/
        this.y_in_aux = this.capas.get(2).getSalidas();
        /*z = f(z_in)*/
 /*En este punto ya tenemos z_in actualizado, falta pasarlo por la funcion para obtener las respuestas*/
        for (int i = 0; i < numSalidas; i++) {
            /*Para cada neurona obtenemos respuesta*/
 /*La funcion de transferencia es la sigmoide bipolar*/
            respuestas.add(sigmoideBipolar(this.capas.get(2).getSalidas().get(i)));
        }
        this.capas.get(2).setSalidas(respuestas);
    }

    public Double sigmoideBipolar(Double x) {
        return (2 / (1 + Math.exp(-x))) - 1;
    }

    public ArrayList<Double> calcularErroresSalida() {
        ArrayList<Double> errores = new ArrayList<>();
        ArrayList<Double> y = this.capas.get(2).getSalidas();
        ArrayList<Double> t = this.patron;
        /*Tenemos el patron t almacenado, y la salida Y actualizada de cada neurona*/
        for (int i = 0; i < numSalidas; i++) {
            errores.add((t.get(i) - y.get(i)) * derivadaSigmoideBipolar(this.y_in_aux.get(i)));
        }
        return errores;
    }

    public Double derivadaSigmoideBipolar(Double x) {
        return 0.5 * (1 + sigmoideBipolar(x)) * (1 - sigmoideBipolar(x));
    }

    public ArrayList<Double> correccionPesosSalida(ArrayList<Double> errores) {
        ArrayList<Double> correcciones = new ArrayList<>();
        for (int i = 0; i < numSalidas; i++) {
            for (int j = 0; j < numCapaOculta; j++) {
                correcciones.add(errores.get(i) * this.tasa * this.capas.get(1).getSalidas().get(j));
            }
        }
        return correcciones;
    }

    public ArrayList<Double> correccionSesgoSalida(ArrayList<Double> errores) {
        ArrayList<Double> correccionSesgoSalida = new ArrayList<>();
        for (int i = 0; i < numSalidas; i++) {
            correccionSesgoSalida.add(errores.get(i) * this.tasa);
        }
        return correccionSesgoSalida;
    }

    public ArrayList<Double> calcularErroresOculta(ArrayList<Double> errores) {
        ArrayList<Double> erroresOculta = new ArrayList<>();
        double sumatorio;
        for (int i = 0; i < numCapaOculta; i++) {
            sumatorio = 0;
            for (int j = 0; j < numSalidas; j++) {
                sumatorio += errores.get(j) * this.capas.get(2).getNeuronas().get(j).getPesos().get(i);
            }
            erroresOculta.add(sumatorio * this.derivadaSigmoideBipolar(this.z_in_aux.get(i)));
        }
        return erroresOculta;
    }

    public ArrayList<Double> correccionPesosOculta(ArrayList<Double> errores) {
        ArrayList<Double> correcciones = new ArrayList<>();
        for (int i = 0; i < numCapaOculta; i++) {
            for (int j = 0; j < numEntradas; j++) {
                correcciones.add(errores.get(i) * this.tasa * this.capas.get(0).getSalidas().get(j));
            }
        }
        return correcciones;
    }

    public ArrayList<Double> correccionSesgoOculta(ArrayList<Double> errores) {
        ArrayList<Double> correccionSesgoOculta = new ArrayList<>();
        for (int i = 0; i < numCapaOculta; i++) {
            correccionSesgoOculta.add(errores.get(i) * this.tasa);
        }
        return correccionSesgoOculta;
    }

    public void actualizarSalida(ArrayList<Double> correccionPesos, ArrayList<Double> correccionSesgo) {

        for (int i = 0; i < numSalidas; i++) {
            ArrayList<Double> pesosNuevos = new ArrayList<>();
            ArrayList<Double> pesos = this.capas.get(2).getNeuronas().get(i).getPesos();
            Double sesgo = this.capas.get(2).getNeuronas().get(i).getSesgo();
            for (int j = 0; j < numCapaOculta; j++) {
                pesosNuevos.add(pesos.get(j) + correccionPesos.get(j));
            }
            this.capas.get(2).getNeuronas().get(i).setPesos(pesosNuevos);
            this.capas.get(2).getNeuronas().get(i).setSesgo(sesgo + correccionSesgo.get(i));
        }
    }

    public void actualizarOculta(ArrayList<Double> correccionPesos, ArrayList<Double> correccionSesgo) {

        for (int i = 0; i < numCapaOculta; i++) {
            ArrayList<Double> pesosNuevos = new ArrayList<>();
            ArrayList<Double> pesos = this.capas.get(1).getNeuronas().get(i).getPesos();
            Double sesgo = this.capas.get(1).getNeuronas().get(i).getSesgo();
            for (int j = 0; j < numEntradas; j++) {
                pesosNuevos.add(pesos.get(j) + correccionPesos.get(j));
            }
            this.capas.get(1).getNeuronas().get(i).setPesos(pesosNuevos);
            this.capas.get(1).getNeuronas().get(i).setSesgo(sesgo + correccionSesgo.get(i));
        }
    }
}
