/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 *
 * @author Urbano
 */
public class Evento implements Serializable {
    GregorianCalendar fecha;
    int tipo;
    String descripcion;
    Factura facturaImplicada;
    
    //constantes de tipologia
    public static final int GENERAL = 0;
    public static final int FACTURA_CREADA = 1;
    public static final int FACTURA_ELIMINADA = 2;
    public static final int ERROR = 3;

    public Evento(GregorianCalendar fecha, int tipo, String descripcion, Factura facturaImplicada) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.facturaImplicada = facturaImplicada;
    }

    public String tipoToString(int t){
        switch(t){
            case 0:
                return "Genérico";
            case 1:
                return "Factura creada";
            case 2:
                return "Factura eliminada";
            case 3:
                return "Error";
            default:
                return "Valor no aceptado";
        }
    }
    
    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Factura getFacturaImplicada() {
        return facturaImplicada;
    }

    public void setFacturaImplicada(Factura facturaImplicada) {
        this.facturaImplicada = facturaImplicada;
    }

    @Override
    public String toString() {
        return "Evento{" + "fecha=" + fecha + ", tipo=" + tipo + ", descripcion=" + descripcion + ", facturaImplicada=" + facturaImplicada + '}';
    }
    
}
