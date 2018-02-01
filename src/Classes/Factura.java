/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author Urbano
 */
public class Factura implements Serializable{
    Tienda tienda;
    Comprador comprador;
    GregorianCalendar fecha;
    int numPedido;
    String formaPago;
    ArrayList<Producto> productos;
    double gastosEnvioSinIVA;
    //double gastosEnvioConIVA;
    double subtotalSinIVA; //Suma del precio sin IVA de todos los productos INCLUYENDO gastos de envío
    double totalConIVA; //subtotalSinIVA + 21% del total
    double descuentos; //Descuentos aplicados en la factura (cantidad final con IVA)
    double totalFinal; //Precio final con IVA y todos los descuentos aplicados

    public Factura(Tienda tienda, Comprador comprador, GregorianCalendar fecha, int numPedido, String formaPago, ArrayList<Producto> productos, double gastosEnvioSinIVA, double descuentoConIVA) {
        this.tienda = tienda;
        this.fecha = fecha;
        this.comprador = comprador;
        this.numPedido = numPedido;
        this.formaPago = formaPago;
        this.productos = productos;
        this.gastosEnvioSinIVA = gastosEnvioSinIVA;
        this.subtotalSinIVA = this.calcularSubtotalSinIVA(productos);
        this.totalConIVA = subtotalSinIVA + (subtotalSinIVA*0.21);
        //Comprobar que totalConIVA = suma de todos los precios con IVA de los productos?
        this.descuentos = descuentoConIVA;
        this.totalFinal = totalConIVA - descuentos;
    }

    public Tienda getTienda() {
        return this.tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }
    
    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public double getGastosEnvioSinIVA() {
        return gastosEnvioSinIVA;
    }

    public void setGastosEnvioSinIVA(double gastosEnvioSinIVA) {
        this.gastosEnvioSinIVA = gastosEnvioSinIVA;
    }

    public double getSubtotalSinIVA() {
        return subtotalSinIVA;
    }

    public void setSubtotalSinIVA(double subtotalSinIVA) {
        this.subtotalSinIVA = subtotalSinIVA;
    }

    public double getTotalConIVA() {
        return totalConIVA;
    }

    public void setTotalConIVA(double totalConIVA) {
        this.totalConIVA = totalConIVA;
    }

    public double getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(double descuentos) {
        this.descuentos = descuentos;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(double totalFinal) {
        this.totalFinal = totalFinal;
    }
    
    private double calcularSubtotalSinIVA(ArrayList<Producto> productos){
        double subtotal = 0;
        for(int i = 0; i < productos.size(); i++){
            subtotal = subtotal + productos.get(i).getPrecioSinIVA();
        }
        subtotal = subtotal + this.gastosEnvioSinIVA;
        return subtotal;
    }
    
}
