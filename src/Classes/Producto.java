/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;

/**
 *
 * @author Urbano
 */
public class Producto implements Serializable{
    String nombre;
    String tipo;
    int codigo;
    double precioSinIVA;
    double precioConIVA;

    public Producto(String nombre, String tipo, int codigo, double precio, boolean precioIncluyeIVA) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.codigo = codigo;
        if(precioIncluyeIVA){
            this.precioSinIVA = precio/1.21;
            this.precioConIVA = precio;
        } else {
            this.precioSinIVA = precio;
            this.precioConIVA = precio + (precio*0.21);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getPrecioSinIVA() {
        return precioSinIVA;
    }

    public void setPrecioSinIVA(double precioSinIVA) {
        this.precioSinIVA = precioSinIVA;
    }

    public double getPrecioConIVA() {
        return precioConIVA;
    }

    public void setPrecioConIVA(double precioConIVA) {
        this.precioConIVA = precioConIVA;
    }
    
    
    
}
