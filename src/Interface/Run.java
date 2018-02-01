/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Classes.Control;
import Classes.Evento;
import Classes.Factura;
import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Urbano
 */
public final class Run extends javax.swing.JFrame {

    /**
     * Creates new form Run
     */
    public Run() {
        //Ocultamos los datos avanzados de factura
        this.ocultarDatosFactura();
        try {
            initComponents();
            GregorianCalendar fechaInicio = new GregorianCalendar(); //Guardamos instante de inicio de aplicación
            String lhip = InetAddress.getLocalHost().toString(); //ahora guardamos un string que contiene "nombre_Equipo/IP_local"
            StringTokenizer tkn = new StringTokenizer(lhip, "/"); //Separamos en Tokens para dividir según "/" la cadena anterior
            String[] strTknArr = new String[2]; //Creamos un vector de dos posicioines para guardar el resultado de los Tkns
            int c = 0; //Iniciamos contador a cero para guardar las cadenas resultantes de los tokens
            while (tkn.hasMoreElements()) {
                strTknArr[c] = tkn.nextToken(); //Guardamos cada token en su posición y aumentamos posteriormente el contador en una unidad
                c++;
            }
            //Ya tenemos todo lo necesario guardado. Ahora hay que mostralo en pantalla
            this.escribirHistorial("Aplicación iniciada "
                    + fechaInicio.get(GregorianCalendar.DAY_OF_MONTH) + "/" //Escribimos el día del mes
                    + (fechaInicio.get(GregorianCalendar.MONTH) + 1) + "/" //Escribimos el mes (se suma uno porque empieza en cero)
                    + fechaInicio.get(GregorianCalendar.YEAR) + " " //Escribimos el año actual
                    + fechaInicio.get(GregorianCalendar.HOUR_OF_DAY) + ":" //Escribimos la hora (formato 24 horas)
                    + fechaInicio.get(GregorianCalendar.MINUTE) + ":" //Escribimos el minuto
                    + fechaInicio.get(GregorianCalendar.SECOND) + "\n" //Escribimos el segundo
                    + "Nombre del equipo: " + strTknArr[0] + "\n" //Escribimos la primera posición del vector, el nombre de la máquina
                    + "Dirección IPv4: " + strTknArr[1] + "\n"
                    + "Usuario: "//Escribimos la segunda posición del vector, la dirección IPv4
                    + System.getProperties().getProperty("user.name") + "\n" //Obtenemos e imprimimos el nombre de usuario de sesión
                    + "Sistema Operativo: "
                    + System.getProperty("os.name", "error") //Escribimos el sistema operativo
                    + "\n" + Control.cargarDatos()); //Cargamos datos y escribimos la cadena devuelta
            this.actualizarEstadisticas(); //Actualizamos los labels de estadísticas generales
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        /* Hemos iniciado los componentes, escrito en el historial de eventos
        y se han cargado los datos.
         */
        this.escribirTabla(Control.facturas); //Ahora podemos escribir en la tabla
    }

    public void ocultarDatosFactura(){;
        this.setSize(800, 700);
    }
    
    public void actualizarEstadisticas() {
        int f = Control.facturas.size();
        int p = 0;
        for (int i = 0; i < Control.facturas.size(); i++) {
            p = p + Control.facturas.get(i).getProductos().size();
        }
        double im = 0;
        for (int i = 0; i < Control.facturas.size(); i++) {
            for (int j = 0; j < Control.facturas.get(i).getProductos().size(); j++) {
                im = im + Control.facturas.get(i).getProductos().get(j).getPrecioConIVA();
            }
        }
        this.estadisticasLabel.setText("Facturas totales: " + f + " // Productos totales: " + p + " // Importe total: " + im);
    }

    public void escribirTabla(ArrayList<Factura> v) {
        //Primero tenemos que borrar todo lo que hay en la tabla
        if (this.tabla.getRowCount() != 0) {
            DefaultTableModel m = (DefaultTableModel) this.tabla.getModel();
            m.setRowCount(0);
            this.tabla.setModel(m);
        }
        //Ahora sabemos que la tabla está vacía. Reescribimos todo.
        try {
            if (!v.isEmpty()) {
                for (int i = 0; i < v.size(); i++) {
                    this.añadirFilaTabla();
                    this.tabla.setValueAt(v.get(i).getNumPedido(), i, 0);
                    String f = Control.fechaToString(v.get(i).getFecha());
                    this.tabla.setValueAt(f, i, 1);
                    String nombreCompleto = v.get(i).getComprador().getNombre()
                            + " " + v.get(i).getComprador().getApellidos();
                    this.tabla.setValueAt(nombreCompleto, i, 2);
                    this.tabla.setValueAt(v.get(i).getTienda().getNombre(), i, 3);
                    this.tabla.setValueAt(v.get(i).getFormaPago(), i, 4);
                    this.tabla.setValueAt(v.get(i).getProductos().size(), i, 5);
                    this.tabla.setValueAt(v.get(i).getTotalFinal(), i, 6);
                }
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, error.getMessage() + "\n"
                    + error.getCause() + "\n" + error.getSuppressed(),
                    "Error escribiendo en tabla", JOptionPane.ERROR_MESSAGE);
            Evento evt = new Evento(new GregorianCalendar(), Evento.ERROR, "Error escribiendo tabla facturas.\n" + error.getMessage(), null);
            Control.registro.add(evt);
            this.escribirHistorial(evt.toString());
        }
    }

    public void escribirHistorial(String txt) {
        if (this.historialTxt.getText().isEmpty()) {
            this.historialTxt.setText(txt);
        } else {
            this.historialTxt.setText(this.historialTxt.getText() + "\n" + txt);
        }
    }

    public void añadirFilaTabla() {
        DefaultTableModel modelo = (DefaultTableModel) this.tabla.getModel();
        modelo.setRowCount(modelo.getRowCount() + 1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenu1 = new javax.swing.JPopupMenu();
        menuItemUno = new javax.swing.JMenuItem();
        nuevaFacturaButon1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        estadisticasLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        buscarTxt = new javax.swing.JTextField();
        buscarButon = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        historialTxt = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        panelDatosFactura = new javax.swing.JPanel();

        menuItemUno.setText("jMenuItem1");
        popMenu1.add(menuItemUno);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        nuevaFacturaButon1.setText("NUEVA FACTURA");
        nuevaFacturaButon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaFacturaButon1ActionPerformed(evt);
            }
        });

        jButton3.setText("Configuracion");

        estadisticasLabel.setText("Facturas totales: // Productos totales: // Importe total:");

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Factura", "Fecha", "Comprador", "Vendedor", "Forma de pago", "Artículos", "Importe"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setComponentPopupMenu(popMenu1);
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(5).setResizable(false);
        }

        buscarTxt.setText("Escriba para buscar...");
        buscarTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                buscarTxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                buscarTxtFocusLost(evt);
            }
        });

        buscarButon.setText("Buscar");
        buscarButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarButonActionPerformed(evt);
            }
        });

        historialTxt.setEditable(false);
        historialTxt.setColumns(20);
        historialTxt.setLineWrap(true);
        historialTxt.setRows(5);
        jScrollPane2.setViewportView(historialTxt);

        jButton4.setText("Todo al escritorio");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Label Estado");

        panelDatosFactura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelDatosFacturaLayout = new javax.swing.GroupLayout(panelDatosFactura);
        panelDatosFactura.setLayout(panelDatosFacturaLayout);
        panelDatosFacturaLayout.setHorizontalGroup(
            panelDatosFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );
        panelDatosFacturaLayout.setVerticalGroup(
            panelDatosFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(estadisticasLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nuevaFacturaButon1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(buscarButon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buscarTxt, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelDatosFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nuevaFacturaButon1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(buscarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buscarButon, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
                    .addComponent(panelDatosFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(estadisticasLabel)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1316, 739));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void nuevaFacturaButon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaFacturaButon1ActionPerformed
        // TODO add your handling code here:
        CrearFactura c = new CrearFactura(this);
        c.setVisible(true);
    }//GEN-LAST:event_nuevaFacturaButon1ActionPerformed

    private void buscarTxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_buscarTxtFocusLost
        // TODO add your handling code here:
        if (this.buscarTxt.getText().isEmpty()) {
            this.buscarTxt.setText("Escriba para buscar...");
        }
    }//GEN-LAST:event_buscarTxtFocusLost

    private void buscarTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_buscarTxtFocusGained
        // TODO add your handling code here:
        if (this.buscarTxt.getText().equals("Escriba para buscar...")) {
            this.buscarTxt.setText(null);
        }
    }//GEN-LAST:event_buscarTxtFocusGained

    private void buscarButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarButonActionPerformed
        // TODO add your handling code here:
        ArrayList<Factura> resultado = new ArrayList<>();
        String t = this.buscarTxt.getText();
        StringTokenizer tkn = new StringTokenizer(t, " ");
        for (int i = 0; i < Control.facturas.size(); i++) {
            for (int j = 0; j < Control.facturas.get(i).getProductos().size(); j++) {
                while (tkn.hasMoreTokens()) {
                    if(Control.getFacturas().get(i).getProductos().get(j).getNombre().contains((CharSequence) tkn.nextElement().toString().toUpperCase())){
                        resultado.add(Control.facturas.get(i));
                        this.escribirTabla(resultado);
                    }
                }
            }
        }
    }//GEN-LAST:event_buscarButonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    //javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Run.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Run().setVisible(true);
            }
        });
        /*
        //Clase principal, todo arranca desde aquí
        
        //COMPROBAMOS SI LA APLIACCIÓN YA HA SIDO EJECUTADA Y CONFIGURADA
        File carpetaDatosLocales = new File("LocalData");
        if(!carpetaDatosLocales.exists()){
            //JOptionPane.showMessageDialog(null, null, "La aplicación creará los directoriosnecesarios para poder ejecutarse", JOptionPane.INFORMATION_MESSAGE);
            //JOptionPane.showMessageDialog("Mensaje", null, "Título", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "El programa creará ahora los directorios necesarios. Pulse aceptar para continuar", "Aplicación no configurada", JOptionPane.INFORMATION_MESSAGE);
            carpetaDatosLocales = new File("LocalData");
            carpetaDatosLocales.mkdir();
            File archivoUsuarios = new File("LocalData/users.dat");
            File archivoTiendas = new File("LocalData/shops.dat");
            File archivoFacturas = new File("LocalData/bills.dat");
            File archivoProductos = new File("LocalData/products.dat");
            File archivoConfig = new File("LocalData/config.dat");
        }
         */

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscarButon;
    private javax.swing.JTextField buscarTxt;
    private javax.swing.JLabel estadisticasLabel;
    private javax.swing.JTextArea historialTxt;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem menuItemUno;
    private javax.swing.JButton nuevaFacturaButon1;
    private javax.swing.JPanel panelDatosFactura;
    private javax.swing.JPopupMenu popMenu1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
