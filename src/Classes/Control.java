/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Urbano
 */
public class Control {

    //Clase de control. Contiene todos los datos y operaciones que afectan a la lógica de nivel del sistema
    //public static ArrayList<Usuario> usuarios; //Contiene todos los usuarios de la plataforma
    public static ArrayList<Tienda> tiendas; //Contiene todas las tiendas de la plataforma
    public static ArrayList<Factura> facturas;//Contiene todas las facturas de la plataforma
    public static ArrayList<Producto> productos;  //Contiene todos los productos comprados. Si un producto ha sido comprado dos veces o aparece en varias facturas no se repite en este HashMap
    public static ArrayList<Evento> registro; //Almacena todos los eventos del registro para poder consultarlos

    public Control() {
    }

    public static String cargarDatos() {
        String resultado = "Carga de datos correcta";
        File carpetaDatosLocales = new File("LocalData");
        if (carpetaDatosLocales.exists()) {
            try {
                FileInputStream fis = new FileInputStream("LocalData/shops.dat");
                ObjectInputStream in = new ObjectInputStream(fis);
                tiendas = (ArrayList<Tienda>) in.readObject();
                fis = new FileInputStream("LocalData/bills.dat");
                in = new ObjectInputStream(fis);
                facturas = (ArrayList<Factura>) in.readObject();
                fis = new FileInputStream("LocalData/products.dat");
                in = new ObjectInputStream(fis);
                productos = (ArrayList<Producto>) in.readObject();
                fis = new FileInputStream("LocalData/registry.dat");
                in = new ObjectInputStream(fis);
                registro = (ArrayList<Evento>) in.readObject();
            } catch (IOException | ClassNotFoundException error) {
                JOptionPane.showMessageDialog(null, error.getMessage(), "Error +al cargar datos", JOptionPane.ERROR_MESSAGE);
                resultado = error.getMessage();
                //Añadir evento de error al cargar los datos
                Control.registro.add(new Evento(new GregorianCalendar(), Evento.ERROR, error.getMessage(), null));
            }
        } else{
            carpetaDatosLocales = new File("LocalData");
            carpetaDatosLocales.mkdir();
            Control.tiendas = new ArrayList<>();
            Control.facturas = new ArrayList<>();
            Control.productos = new ArrayList<>();
            Control.registro = new ArrayList<>();
            Control.guardarDatos();
            JOptionPane.showMessageDialog(null, "Se ha creado el sistema de ficheros +"
                    + "para guardar los datos. \nPuede verlo en el directorio de instalación +"
                    + " y podrá crear copias de seguridad", "Información de primera instancia",
                    JOptionPane.INFORMATION_MESSAGE);
            resultado = "No había datos. Sistema de ficheros creado";
        }
        return resultado;
    }

    public static void guardarDatos() {
        try {
            FileOutputStream fos = new FileOutputStream("LocalData/shops.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(Control.tiendas);
            fos = new FileOutputStream("LocalData/bills.dat");
            out = new ObjectOutputStream(fos);
            out.writeObject(Control.facturas);
            fos = new FileOutputStream("LocalData/products.dat");
            out = new ObjectOutputStream(fos);
            out.writeObject(Control.productos);
            fos = new FileOutputStream("LocalData/registry.dat");
            out = new ObjectOutputStream(fos);
            out.writeObject(Control.registro);
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error.getMessage(), "Error al guardar datos", JOptionPane.ERROR_MESSAGE);
            //Añadir evento de error al guardar los datos
           
            
        }
    }

    public static void añadirFactura(Factura f){
        if(Control.facturas.contains(f)){
            JOptionPane.showMessageDialog(null, "Ya existe esta factura que desea añadir", "Error al añadir la factura", JOptionPane.ERROR_MESSAGE);
        } else{
            Control.facturas.add(f);
            Control.guardarDatos();
        }
    }
    
    public static String fechaToString(GregorianCalendar f){
     String t = null;
     SimpleDateFormat formato = new SimpleDateFormat("dd / MM / yyyy");
     t = formato.format(f.getTime());
     return t;
    }
    
    public static ArrayList<Tienda> getTiendas() {
        return tiendas;
    }

    public static void setTiendas(ArrayList<Tienda> tiendas) {
        Control.tiendas = tiendas;
    }

    public static ArrayList<Factura> getFacturas() {
        return facturas;
    }

    public static void setFacturas(ArrayList<Factura> facturas) {
        Control.facturas = facturas;
    }

    public static ArrayList<Producto> getProductos() {
        return productos;
    }

    public static void setProductos(ArrayList<Producto> productos) {
        Control.productos = productos;
    }

    public static ArrayList<Evento> getRegistro() {
        return registro;
    }

    public static void setRegistro(ArrayList<Evento> registro) {
        Control.registro = registro;
    }

}
