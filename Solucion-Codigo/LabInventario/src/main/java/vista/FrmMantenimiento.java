/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author ASUS
 */
public class FrmMantenimiento extends javax.swing.JFrame {

    private Modelo.Persona usuarioActual;
    private Controlador.ActivoDAO activoDAO;
    private Controlador.ControladorHardware controladorHW;
    private Controlador.ControladorSoftware controladorSW;
    private Modelo.Activo activoSeleccionado; // Guarda en memoria el activo que encontremos

    public FrmMantenimiento(Modelo.Persona usuario) {
        this.usuarioActual = usuario;
        initComponents();
        inicializarSistema();
    }

    private void inicializarSistema() {
        try {
            Class.forName("org.sqlite.JDBC");
            java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:sqlite:lab_inventario.db");
            this.activoDAO = new Controlador.ActivoDAO(conn);

            Controlador.Servicios.ServicioAutorizacion auth = new Controlador.Servicios.ServicioAutorizacion();
            Controlador.Servicios.ServicioHardware servHW = new Controlador.Servicios.ServicioHardware();
            Controlador.Servicios.ServicioSoftware servSW = new Controlador.Servicios.ServicioSoftware();

            this.controladorHW = new Controlador.ControladorHardware(auth, servHW, activoDAO);
            this.controladorSW = new Controlador.ControladorSoftware(auth, servSW, activoDAO);

            // AQUÍ ESTABA EL ERROR: Ahora sí bloqueamos txtInfo y no txtCodigo
            txtInfo.setEditable(false);
            deshabilitarTodo();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error base de datos: " + e.getMessage());
        }
    }

    private void deshabilitarTodo() {
        txtEncargado.setEnabled(false);
        txtObservaciones.setEnabled(false);
        txtTiempoRenovar.setEnabled(false);
        txtCosto.setEnabled(false);
        btnCalcularCosto.setEnabled(false);
        btnProcesar.setEnabled(false);
    }

    private void habilitarParaHardware() {
        deshabilitarTodo(); // Apagamos todo primero
        txtEncargado.setEnabled(true);
        txtObservaciones.setEnabled(true);
        txtCosto.setEnabled(true);
        btnCalcularCosto.setEnabled(true);
        btnProcesar.setEnabled(true);
    }

    private void habilitarParaSoftware() {
        deshabilitarTodo(); // Apagamos todo primero
        txtTiempoRenovar.setEnabled(true);
        txtCosto.setEnabled(true);
        btnCalcularCosto.setEnabled(true);
        btnProcesar.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCodigo = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtEncargado = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtObservaciones = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTiempoRenovar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        btnCalcularCosto = new javax.swing.JButton();
        btnProcesar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        txtCodigo = new javax.swing.JTextField();
        txtInfo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblCodigo.setText("Código del Activo:");

        btnBuscar.setText("Buscar Activo");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel1.setText("Encargado (Técnico):");

        txtEncargado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEncargadoActionPerformed(evt);
            }
        });

        jLabel2.setText("Observaciones:");

        jLabel3.setText("Tiempo a Renovar (Meses/Años):");

        txtTiempoRenovar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTiempoRenovarActionPerformed(evt);
            }
        });

        jLabel4.setText("Costo ($):");

        btnCalcularCosto.setText("Calcular Costo Sugerido");
        btnCalcularCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularCostoActionPerformed(evt);
            }
        });

        btnProcesar.setText("Procesar Mantenimiento / Renovación");
        btnProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver al Menú");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo)
                            .addComponent(btnBuscar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCalcularCosto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProcesar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVolver))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(83, 83, 83)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEncargado, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(txtObservaciones)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(142, 142, 142)
                            .addComponent(txtCosto))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(txtTiempoRenovar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTiempoRenovar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(txtEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCalcularCosto)
                    .addComponent(btnProcesar)
                    .addComponent(btnVolver))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese un código para buscar.");
            return;
        }

        activoSeleccionado = activoDAO.buscarPorCodigo(codigo);

        if (activoSeleccionado == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Activo no encontrado.");
            txtInfo.setText("");
            deshabilitarTodo();
            return;
        }
        
        // Y AQUÍ EL ÚLTIMO: Mostramos la información en txtInfo, no en txtCodigo
        txtInfo.setText(activoSeleccionado.getNombre() + " - " + activoSeleccionado.getTipoActivo() + " - " + activoSeleccionado.getEstado());

        // Adaptar la interfaz usando polimorfismo
        if (activoSeleccionado instanceof Modelo.Hardware) {
            habilitarParaHardware();
        } else if (activoSeleccionado instanceof Modelo.Software) {
            habilitarParaSoftware();
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtEncargadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEncargadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEncargadoActionPerformed

    private void txtTiempoRenovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTiempoRenovarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTiempoRenovarActionPerformed

    private void btnCalcularCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularCostoActionPerformed
        try {
            if (activoSeleccionado instanceof Modelo.Hardware) {
                Controlador.Calculadora.CalculadoraHardware calcHW = new Controlador.Calculadora.CalculadoraHardware();
                double costoSugerido = calcHW.calcular((Modelo.Hardware) activoSeleccionado);
                txtCosto.setText(String.format(java.util.Locale.US, "%.2f", costoSugerido));

            } else if (activoSeleccionado instanceof Modelo.Software) {
                if (txtTiempoRenovar.getText().trim().isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Ingrese primero los meses a renovar.");
                    return;
                }
                int meses = Integer.parseInt(txtTiempoRenovar.getText().trim());
                Controlador.Calculadora.CalculadoraSoftware calcSW = new Controlador.Calculadora.CalculadoraSoftware();
                double costoMensual = calcSW.calcularMensual((Modelo.Software) activoSeleccionado);

                double costoTotal = costoMensual * meses;
                txtCosto.setText(String.format(java.util.Locale.US, "%.2f", costoTotal));
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al calcular: " + e.getMessage());
        }
    }//GEN-LAST:event_btnCalcularCostoActionPerformed

    private void btnProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarActionPerformed
        try {
            if (txtCosto.getText().trim().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "El campo de costo no puede estar vacío.");
                return;
            }
            double costo = Double.parseDouble(txtCosto.getText().trim());

            if (activoSeleccionado instanceof Modelo.Hardware) {
                String encargado = txtEncargado.getText().trim();
                String obs = txtObservaciones.getText().trim();

                if (encargado.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Debe especificar el encargado del mantenimiento.");
                    return;
                }
                String msj = controladorHW.registrarMantenimiento(usuarioActual, (Modelo.Hardware) activoSeleccionado, encargado, costo, obs);
                javax.swing.JOptionPane.showMessageDialog(this, msj);

            } else if (activoSeleccionado instanceof Modelo.Software) {
                int tiempo = Integer.parseInt(txtTiempoRenovar.getText().trim());

                String msj = controladorSW.renovarSoftware(usuarioActual, (Modelo.Software) activoSeleccionado, costo, tiempo);
                javax.swing.JOptionPane.showMessageDialog(this, msj);
            }
            // Limpiar ventana tras el éxito
            activoSeleccionado = null;
            lblCodigo.setText("");
            txtCodigo.setText("");
            txtEncargado.setText("");
            txtObservaciones.setText("");
            txtTiempoRenovar.setText("");
            txtCosto.setText("");
            deshabilitarTodo();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.");
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al procesar: " + e.getMessage());
        }

    }//GEN-LAST:event_btnProcesarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        new FrmMenuPrincipal().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCalcularCosto;
    private javax.swing.JButton btnProcesar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtEncargado;
    private javax.swing.JTextField txtInfo;
    private javax.swing.JTextField txtObservaciones;
    private javax.swing.JTextField txtTiempoRenovar;
    // End of variables declaration//GEN-END:variables
}
