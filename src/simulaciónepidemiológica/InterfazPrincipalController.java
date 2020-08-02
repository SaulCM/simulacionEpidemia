/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciónepidemiológica;

import clases.reporteEpidemiologico;
import dba.LoadDriver;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Master
 */
public class InterfazPrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    LoadDriver dbConnector;
    Connection conn;
    Statement sent;

    private String region="Bolivia";
    private String opcion="Simulación";
    private String tipoCuarentena="sincuarentena";
    
    private String fechaEstadisticas;
    private int diasSimulacion;
    private double poblacionInicial;
    private double probabilidadContagio=0.14;
    private double duracionEnfermedad=7;
    private double tasaDiariaInteraccion=7;
    private double tasaRecuperacion=0.95;
    private double tasaLetalidad=0.05;
    private double contagiadosIniciales=10;
    private double medidasGobierno=0.0;
    
    private double circulacionPoblDias=0;
    private double circulacionPoblHoras=0;
    private double circulacionTransporteDias=0;
    private double diasMercado=0;
            
    private Boolean cBTransportePublicoRestriccionPlaca=false;
    private Boolean cBTransportePublicoBioseguridad=false;
    private Boolean conPeriodos=false;
    
    private double contagios=0;
    private double recuperaciones=0;
    private double fallecimientos=0;


    private double susceptibles;
    private double infectados=1;
    private double recuperados=0;
    private double fallecidos=0;
    
            double mayorContagios=0;// 1
        double mayorInfectados=0; //5
        double mayorRecuperaciones=0;// 2
        double mayorFallecimientos=0;// 7
        double totalInfectados=0;
        double totalRecuperados=0;
        double totalFallecidos=0;
    
    private String[]fechas={"10-03-2020",
"11-03-2020","12-03-2020","13-03-2020","14-03-2020","15-03-2020","16-03-2020","17-03-2020","18-03-2020","19-03-2020","20-03-2020","21-03-2020","22-03-2020","23-03-2020","24-03-2020","25-03-2020","26-03-2020","27-03-2020","28-03-2020","29-03-2020","30-03-2020","31-03-2020","01-04-2020","02-04-2020","03-04-2020","04-04-2020","05-04-2020","06-04-2020","07-04-2020","08-04-2020","09-04-2020","10-04-2020","11-04-2020","12-04-2020","13-04-2020","15-04-2020","16-04-2020",
"17-04-2020","18-04-2020","19-04-2020","20-04-2020","22-04-2020","23-04-2020","24-04-2020","25-04-2020","26-04-2020","27-04-2020","28-04-2020","29-04-2020","30-04-2020","01-05-2020","02-05-2020","03-05-2020","04-05-2020","05-05-2020","06-05-2020","07-05-2020","08-05-2020","09-05-2020","10-05-2020","11-05-2020","12-05-2020","13-05-2020","14-05-2020","15-05-2020","16-05-2020","17-05-2020","18-05-2020","19-05-2020","20-05-2020","21-05-2020","22-05-2020","23-05-2020",
"24-05-2020","25-05-2020","26-05-2020","27-05-2020","28-05-2020","29-05-2020","30-05-2020","31-05-2020","01-06-2020","02-06-2020","03-06-2020","04-06-2020","05-06-2020","06-06-2020","07-06-2020","08-06-2020","09-06-2020","10-06-2020","11-06-2020","12-06-2020","13-06-2020","14-06-2020","15-06-2020","16-06-2020","17-06-2020","18-06-2020","19-06-2020","20-06-2020","21-06-2020","22-06-2020","23-06-2020","24-06-2020","25-06-2020","26-06-2020","27-06-2020","28-06-2020",
"29-06-2020","30-06-2020","01-07-2020","02-07-2020","03-07-2020","04-07-2020","05-07-2020","06-07-2020","07-07-2020","08-07-2020","09-07-2020","10-07-2020","11-07-2020","12-07-2020","13-07-2020","14-07-2020","15-07-2020","16-07-2020","17-07-2020","18-07-2020","19-07-2020","20-07-2020","21-07-2020","22-07-2020","23-07-2020","24-07-2020","25-07-2020","26-07-2020","27-07-2020","28-07-2020","29-07-2020","30-07-2020","31-07-2020","01-08-2020","02-08-2020","03-08-2020",
"04-08-2020","05-08-2020","06-08-2020","07-08-2020","08-08-2020","09-08-2020","10-08-2020","11-08-2020","12-08-2020","13-08-2020","14-08-2020","15-08-2020","16-08-2020","17-08-2020","18-08-2020","19-08-2020","20-08-2020","21-08-2020","22-08-2020","23-08-2020","24-08-2020","25-08-2020","26-08-2020","27-08-2020","28-08-2020","29-08-2020","30-08-2020","31-08-2020","01-09-2020","02-09-2020","03-09-2020","04-09-2020","05-09-2020","06-09-2020","07-09-2020","08-09-2020",
"09-09-2020","10-09-2020","11-09-2020","12-09-2020","13-09-2020","14-09-2020","15-09-2020","16-09-2020","17-09-2020","18-09-2020","19-09-2020","20-09-2020","21-09-2020","22-09-2020","23-09-2020","24-09-2020","25-09-2020","26-09-2020","27-09-2020","28-09-2020","29-09-2020","30-09-2020","01-10-2020","02-10-2020","03-10-2020","04-10-2020","05-10-2020","06-10-2020","07-10-2020","08-10-2020","09-10-2020","10-10-2020","11-10-2020","12-10-2020","13-10-2020","14-10-2020",
"15-10-2020","16-10-2020","17-10-2020","18-10-2020","19-10-2020","20-10-2020","21-10-2020","22-10-2020","23-10-2020","24-10-2020","25-10-2020","26-10-2020","27-10-2020","28-10-2020","29-10-2020","30-10-2020","31-10-2020","01-11-2020","02-11-2020","03-11-2020","04-11-2020","05-11-2020","06-11-2020","07-11-2020","08-11-2020","09-11-2020","10-11-2020","11-11-2020","12-11-2020","13-11-2020","14-11-2020","15-11-2020","16-11-2020","17-11-2020","18-11-2020","19-11-2020",
"20-11-2020","21-11-2020","22-11-2020","23-11-2020","24-11-2020","25-11-2020","26-11-2020","27-11-2020","28-11-2020","29-11-2020","30-11-2020","01-12-2020","02-12-2020","03-12-2020","04-12-2020","05-12-2020","06-12-2020","07-12-2020","08-12-2020","09-12-2020","10-12-2020","11-12-2020","12-12-2020","13-12-2020","14-12-2020","15-12-2020","16-12-2020","17-12-2020","18-12-2020","19-12-2020","20-12-2020","21-12-2020","22-12-2020","23-12-2020","24-12-2020","25-12-2020",
"26-12-2020","27-12-2020","28-12-2020","29-12-2020","30-12-2020","31-12-2020","01-01-2021","02-01-2021","03-01-2021","04-01-2021","05-01-2021","06-01-2021","07-01-2021","08-01-2021","09-01-2021","10-01-2021","11-01-2021","12-01-2021","13-01-2021","14-01-2021","15-01-2021","16-01-2021","17-01-2021","18-01-2021","19-01-2021","20-01-2021","21-01-2021","22-01-2021","23-01-2021","24-01-2021","25-01-2021","26-01-2021","27-01-2021","28-01-2021","29-01-2021","30-01-2021",
"31-01-2021","01-02-2021","02-02-2021","03-02-2021","04-02-2021","05-02-2021","06-02-2021","07-02-2021","08-02-2021","09-02-2021","10-02-2021","11-02-2021","12-02-2021","13-02-2021","14-02-2021","15-02-2021","16-02-2021","17-02-2021","18-02-2021","19-02-2021","20-02-2021","21-02-2021","22-02-2021","23-02-2021","24-02-2021","25-02-2021","26-02-2021","27-02-2021","28-02-2021","01-03-2021","02-03-2021","03-03-2021","04-03-2021","05-03-2021","06-03-2021","07-03-2021",
"08-03-2021","09-03-2021","10-03-2021","11-03-2021","12-03-2021","13-03-2021","14-03-2021","15-03-2021","16-03-2021","17-03-2021","18-03-2021","19-03-2021","20-03-2021","21-03-2021","22-03-2021","23-03-2021","24-03-2021","25-03-2021","26-03-2021","27-03-2021","28-03-2021","29-03-2021","30-03-2021","31-03-2021","01-04-2021","02-04-2021","03-04-2021","04-04-2021","05-04-2021","06-04-2021","07-04-2021","08-04-2021","09-04-2021","10-04-2021","11-04-2021","12-04-2021",
"13-04-2021","14-04-2021","15-04-2021","16-04-2021","17-04-2021","18-04-2021","19-04-2021","20-04-2021","21-04-2021","22-04-2021","23-04-2021","24-04-2021","25-04-2021","26-04-2021","27-04-2021","28-04-2021","29-04-2021","30-04-2021","01-05-2021","02-05-2021","03-05-2021","04-05-2021","05-05-2021","06-05-2021","07-05-2021","08-05-2021","09-05-2021","10-05-2021","11-05-2021","12-05-2021","13-05-2021","14-05-2021","15-05-2021","16-05-2021","17-05-2021","18-05-2021",
"19-05-2021","20-05-2021","21-05-2021","22-05-2021","23-05-2021","24-05-2021","25-05-2021","26-05-2021","27-05-2021","28-05-2021","29-05-2021","30-05-2021","31-05-2021","01-06-2021","02-06-2021","03-06-2021","04-06-2021","05-06-2021","06-06-2021","07-06-2021","08-06-2021","09-06-2021","10-06-2021","11-06-2021","12-06-2021","13-06-2021","14-06-2021","15-06-2021","16-06-2021","17-06-2021","18-06-2021","19-06-2021","20-06-2021","21-06-2021","22-06-2021","23-06-2021",
"24-06-2021","25-06-2021","26-06-2021","27-06-2021","28-06-2021","29-06-2021","30-06-2021"};
    
    private double[][]datosCalculados;
    //private ArrayList<ArrayList<Double>> datosCalculados;
        public InterfazPrincipalController() {
    }
    @FXML private ComboBox<?> Region;
    @FXML private TextField PoblacionInicial;
    @FXML private CheckBox CheckBoxCuarentenaR;
    @FXML private CheckBox CheckBoxCuarentenaD;
    @FXML private CheckBox CheckBoxSinCuarentena;
    @FXML private AnchorPane VistaMapa;
    @FXML private Label tituloSeleccion;
    
    @FXML private LineChart<?, ?> grafica;
    
    @FXML private TableView<reporteEpidemiologico> tablaReporteEpidemiologico;     
        @FXML private TableColumn<reporteEpidemiologico, String> columnFecha; 
        @FXML private TableColumn<reporteEpidemiologico, Integer> columnNuevosCasos;
        @FXML private TableColumn<reporteEpidemiologico, Integer> columnTotalContagiados;
        @FXML private TableColumn<reporteEpidemiologico, Integer> columnRecuperados;
        @FXML private TableColumn<reporteEpidemiologico, Integer> columnTotalRecuperados;
        @FXML private TableColumn<reporteEpidemiologico, Integer> columnFallecidos;
        @FXML private TableColumn<reporteEpidemiologico, Integer> columnTotalFallecidos;
        
    @FXML    private Label contagiosPando;
    @FXML    private Label contagiosLapaz;
    @FXML    private Label contagiosOruro;
    @FXML    private Label contagiosCochabamba;
    @FXML    private Label contagiosPotosi;
    @FXML    private Label contagiosBeni;
    @FXML    private Label contagiosSantacruz;
    @FXML    private Label contagiosChuquisaca;
    @FXML    private Label contagiosTarija;

    @FXML private TextField TotalInfectados;
    @FXML private TextField TotalRecuperados;
    @FXML private TextField TotalFallecidos;
    
    @FXML private VBox ResultadosSimulacion;
    @FXML private VBox Estadisticas;
    @FXML private VBox ParametrosSimulacion;
    
    @FXML private TextField CirculacionPoblDias;
    @FXML private TextField CirculacionPoblHoras;
    @FXML private TextField CirculacionTransporteDias;
    @FXML private TextField DiasMercado;
            
    @FXML private CheckBox CBTransportePublicoRestriccionPlaca;
    @FXML private CheckBox CBTransportePublicoBioseguridad;
    
    @FXML private TextField DiasSimulacion;
    @FXML private TextField ResMaxNumContagios;
    @FXML private TextField ResMaxNumRecuperados;
    @FXML private TextField ResMaxNumFallecidos;
    @FXML private TextField resTasaLetalidad;
    
    @FXML private TextField estaNuevosCasos;
    @FXML private TextField estaContagiados;
    @FXML private TextField estaRecuperados;
    @FXML private TextField estaFallecidos;
    
    @FXML private DatePicker datepickerFechaInicial;
    @FXML private DatePicker datepickerFechaFinal;
    @FXML private CheckBox checkBoxPeriodo;
    @FXML private Button switchGraficoTabla;
    @FXML private Button buttonReportes;

    
    
    @FXML
    void generarReporte(MouseEvent event) {
        try {
            conn=dbConnector.getConnection();
           //Class.forName("com.mysql.jdbc.Driver");
            //conn=DriverManager.getConnection("jdbc:mysql://localhost/epidemiologia","root","");
            JasperDesign jdesign=JRXmlLoader.load("src\\reportes\\newReport.jrxml");
            String query="SELECT\n" +
"	reporte_epidemiologico.`id` AS reporte_epidemiologico_id,\n" +
"     reporte_epidemiologico.`fecha` AS reporte_epidemiologico_fecha,\n" +
"     reporte_epidemiologico.`totalContagiados` AS reporte_epidemiologico_totalContagiados,\n" +
"     reporte_epidemiologico.`totalRecuperados` AS reporte_epidemiologico_totalRecuperados,\n" +
"     reporte_epidemiologico.`totalFallecidos` AS reporte_epidemiologico_totalFallecidos,\n" +
"     reporte_epidemiologico.`nuevos_casos` AS reporte_epidemiologico_nuevos_casos,\n" +
"     reporte_epidemiologico.`recuperados` AS reporte_epidemiologico_recuperados,\n" +
"     reporte_epidemiologico.`fallecidos` AS reporte_epidemiologico_fallecidos,\n" +
"     region.`region` AS region_region\n" +
"FROM\n" +
"     `region` region INNER JOIN `reporte_epidemiologico` reporte_epidemiologico\n" +
"ON region.region_id=reporte_epidemiologico.region_id where region.region="+"'"+region+"'";
            JRDesignQuery updateQuery= new JRDesignQuery();
            updateQuery.setText(query);
            
            jdesign.setQuery(updateQuery);
            
            JasperReport jreport=JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint=JasperFillManager.fillReport(jreport,null,conn);
            JasperViewer jv = new JasperViewer( jprint, false );
            jv.viewReport(jprint,false);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void Simular(MouseEvent event) {

        simulacion();

    }
    public void simulacion(){
        
        //Se obtiene la población inicial 
        poblacionInicial=Double.parseDouble(PoblacionInicial.getText());
        //De acuerdo al tipo de medidas tomadas se altera la variable Tasa diaria de interacción
        if (tipoCuarentena.equals("dinamica")) {
        tasaDiariaInteraccion=4.6;
        circulacionPoblDias=Double.parseDouble(CirculacionPoblDias.getText());
        circulacionPoblHoras=Double.parseDouble(CirculacionPoblHoras.getText());
        circulacionTransporteDias=Double.parseDouble(CirculacionTransporteDias.getText());
        diasMercado=Double.parseDouble(DiasMercado.getText());

                    if (cBTransportePublicoBioseguridad==true) {
                tasaDiariaInteraccion-=0.02;
            }
            if (cBTransportePublicoRestriccionPlaca==true) {
                tasaDiariaInteraccion-=0.02;
            }
        
        tasaDiariaInteraccion-=(1-(circulacionPoblDias/10));
        tasaDiariaInteraccion-=(1-(circulacionPoblHoras/100));
        tasaDiariaInteraccion-=(0.3-(circulacionTransporteDias/30));
        tasaDiariaInteraccion-=(0.3-(diasMercado/30));
        System.out.println(tasaDiariaInteraccion);
        }
        //Se obtiene los días que se quieren simular
        diasSimulacion=Integer.parseInt(DiasSimulacion.getText());
        //Se crea un array para almacenar los datos calculados
        datosCalculados=new double[diasSimulacion][7];
        
        //Se establece la cantidad de susceptibles inicial
        susceptibles=poblacionInicial-infectados;
        //Ciclo que calcula las variables de contagios recuperados fallecidos en base a la anterior iteración
        for (int i = 0; i < datosCalculados.length; i++) {
            contagios=infectados*tasaDiariaInteraccion*(1-0.4)*probabilidadContagio*susceptibles/(susceptibles+infectados+recuperados);
            recuperaciones=infectados*tasaRecuperacion/duracionEnfermedad;
            fallecimientos=infectados*tasaLetalidad/12;
                datosCalculados[i][0]=contagios;
                datosCalculados[i][1]=recuperaciones;
                datosCalculados[i][2]=fallecimientos;
                
                susceptibles=susceptibles-contagios;
                fallecidos=fallecidos+fallecimientos;
                recuperados=recuperados+recuperaciones;
                infectados=infectados+contagios-recuperaciones-fallecimientos;
                
                datosCalculados[i][3]=susceptibles;
                datosCalculados[i][4]=infectados;
                datosCalculados[i][5]=recuperados;
                datosCalculados[i][6]=fallecidos;
                
                DecimalFormat df = new DecimalFormat("#");
        }
        graficar();
        mostrarResultados();
    }
    public void graficar(){
        grafica.getData().clear();// Borrar datos anteriores
         XYChart.Series series1=new XYChart.Series();
         XYChart.Series series2=new XYChart.Series();
         XYChart.Series series3=new XYChart.Series();
         XYChart.Series series4=new XYChart.Series();
         //Nombrar las series de datos
         series1.setName("Susceptibles");
         series2.setName("Infectados");
         series3.setName("Recuperados");
         series4.setName("Fallecidos");
        
         boolean flagEsLaFecha=false;
         for (int i = 0; i < datosCalculados.length; i++) {
             if(conPeriodos==true){
                 LocalDate ld=datepickerFechaInicial.getValue();
                DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String fechaIni=ld.format(formatter);
                 if (fechas[i].equals(fechaIni)) {
                     flagEsLaFecha=true;
                     
                     }
                     LocalDate ld2=datepickerFechaFinal.getValue();
                     String fechaFin=ld2.format(formatter);
                 if (fechas[i].equals(fechaFin)) {
                        series1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][3]));
                        series2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                        series3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                        series4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6])); 
                        flagEsLaFecha=false;
                   }
                 if (flagEsLaFecha==true) {
                        series1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][3]));
                        series2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                        series3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                        series4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6])); 
                 }
             }
             else{
             series1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][3]));
             series2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
             series3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
             series4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));             
             }
        }
         //series1.getData().add(new XYChart.Data("",2));
         grafica.setCreateSymbols(false);
        // grafica.getYAxis().setTickLabelsVisible(false);
         //grafica.getYAxis().setOpacity(0);
         grafica.getData().addAll(series2,series3,series4);
        limpiarDatos();
    }
    public void mostrarResultados(){
        double nuevosCasos=0;
        mayorContagios=0;// 1
        mayorInfectados=0; //5
        mayorRecuperaciones=0;// 2
        mayorFallecimientos=0;// 7
        totalInfectados=0;
        totalRecuperados=0;
        totalFallecidos=0;
        double tasaLetalidad=0;
        for (int i = 0; i < datosCalculados.length; i++) {
            if (mayorContagios<datosCalculados[i][0]) {
                
            mayorContagios=datosCalculados[i][0];
            }
            if (mayorInfectados<datosCalculados[i][4]) {
                
            mayorInfectados=datosCalculados[i][4];
            }
            if (mayorRecuperaciones<datosCalculados[i][1]) {
                
            mayorRecuperaciones=datosCalculados[i][1];
            }
            if (mayorFallecimientos<datosCalculados[i][2]) {
                
            mayorFallecimientos=datosCalculados[i][2];
            }
            totalInfectados+=datosCalculados[i][0];
            totalRecuperados+=datosCalculados[i][1];
            totalFallecidos+=datosCalculados[i][2];
            nuevosCasos=datosCalculados[i][0];
        }
        DecimalFormat df = new DecimalFormat("#");
        DecimalFormat df2=new DecimalFormat("#.##");
         tasaLetalidad=(totalFallecidos*100)/totalInfectados;
        ResMaxNumContagios.setText(String.valueOf(df.format(mayorContagios))+" Personas");
        ResMaxNumRecuperados.setText(String.valueOf(df.format(mayorRecuperaciones))+" Personas");
        ResMaxNumFallecidos.setText(String.valueOf(df.format(mayorFallecimientos))+" Personas");
        resTasaLetalidad.setText(String.valueOf(df2.format(tasaLetalidad))+" %");
        estaNuevosCasos.setText(String.valueOf(df.format(nuevosCasos))+" Personas");
        estaContagiados.setText(String.valueOf(df.format(totalInfectados))+" Personas");
        estaRecuperados.setText(String.valueOf(df.format(totalRecuperados))+" Personas");
        estaFallecidos.setText(String.valueOf(df.format(totalFallecidos))+" Personas");
        
                
        
    }
    public void clearData(){
        grafica.getData().clear();
    }
    public void limpiarDatos(){
    
    contagios=0;
    recuperaciones=0;
    fallecimientos=0;

    infectados=10;
    recuperados=0;
    fallecidos=0;
            totalInfectados=0;
            totalRecuperados=0;
            totalFallecidos=0;
                    mayorContagios=0;// 1
        mayorInfectados=0; //5
        mayorRecuperaciones=0;// 2
        mayorFallecimientos=0;// 7

    }
    public void graficarDatosNacionalesReales(){
        clearData();
        
        for ( int i = 0; i<tablaReporteEpidemiologico.getItems().size(); i++) {
            tablaReporteEpidemiologico.getItems().clear(); 
        } 
         
         XYChart.Series series1=new XYChart.Series();
         XYChart.Series series2=new XYChart.Series();
         XYChart.Series series3=new XYChart.Series();
         XYChart.Series series4=new XYChart.Series();
         
         series1.setName("Contagiados");
         series2.setName("Recuperados");
         series3.setName("Fallecidos");
         series4.setName("Nuevos Casos");
         
         int nuevosCasos=0;
         int contagiados=0;
         int recuperados=0;
         int fallecidos=0;
         
         int maxNumContagios=0;
         int maxNumRecuperados=0;
         int maxNumFallecidos=0;
         
         int region_id=10;
         switch(region){
             case "Cochabamba": region_id=1; break;
             case "La Paz": region_id=2; break;
             case "Santa Cruz": region_id=3; break;
             case "Oruro": region_id=4; break;
             case "Potosi": region_id=5; break;
             case "Pando": region_id=6; break;
             case "Beni": region_id=7; break;
             case "Chuquisaca": region_id=8; break;
             case "Tarija": region_id=9; break;
             case "Bolivia": region_id=10; break;
             case "Simulación": region_id=11; break;
         }
                            try {
            conn = dbConnector.getConnection();
            String sql = "SELECT * FROM reporte_epidemiologico r WHERE r.region_id="+region_id+";";
            sent= conn.createStatement();
            ResultSet rs=sent.executeQuery(sql);
            boolean flagEsLaFecha=false;// boolean para controlar que se grafique los datos entre las fechas establecidas
            //Mientras hayan registros recuperados de la base de datos, se ejecuta el codigo para cada registro
            while(rs.next()){
                //Recuperar los campos de cada registro
                nuevosCasos=Integer.parseInt(rs.getString("nuevos_casos"));
                contagiados=Integer.parseInt(rs.getString("totalContagiados"));
                recuperados=Integer.parseInt(rs.getString("totalRecuperados"));
                fallecidos=Integer.parseInt(rs.getString("totalFallecidos"));
                
                maxNumContagios=0;
                maxNumRecuperados=0;
                maxNumFallecidos=0;
                //Obtener el campo fecha y formatear a un formato igual al utilizado    
                String fecha=rs.getString("fecha");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                //convert String to LocalDate
                LocalDate localDate = LocalDate.parse(fecha, formatter);
                
                DateTimeFormatter formatter2=DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String fechaFormateada=localDate.format(formatter2);
                
                if(conPeriodos==true){
                 LocalDate ld=datepickerFechaInicial.getValue();
                String fechaIni=ld.format(formatter2);
                 if (fechaFormateada.equals(fechaIni)) {
                     flagEsLaFecha=true;
                     
                     }
                     LocalDate ld2=datepickerFechaFinal.getValue();
                     String fechaFin=ld2.format(formatter2);
                 if (fechaFormateada.equals(fechaFin)) {
                     System.out.println("1");
                        series1.getData().add(new XYChart.Data(fechaFormateada,contagiados));
                        series2.getData().add(new XYChart.Data(fechaFormateada,recuperados));
                        series3.getData().add(new XYChart.Data(fechaFormateada,fallecidos));
                        series4.getData().add(new XYChart.Data(fechaFormateada,nuevosCasos));  
                        flagEsLaFecha=false;
                   }
                 if (flagEsLaFecha==true) {
                     System.out.println("2");
                        series1.getData().add(new XYChart.Data(fechaFormateada,contagiados));
                        series2.getData().add(new XYChart.Data(fechaFormateada,recuperados));
                        series3.getData().add(new XYChart.Data(fechaFormateada,fallecidos));
                        series4.getData().add(new XYChart.Data(fechaFormateada,nuevosCasos)); 
                 }
             }
             else{
                series1.getData().add(new XYChart.Data(fechaFormateada,contagiados));
                series2.getData().add(new XYChart.Data(fechaFormateada,recuperados));
                series3.getData().add(new XYChart.Data(fechaFormateada,fallecidos));
                series4.getData().add(new XYChart.Data(fechaFormateada,nuevosCasos));            
             }
                
             // Mostrar resultado de ultima fecha en Labels
                if (Integer.parseInt(rs.getString("nuevos_casos"))>maxNumContagios) {
                    maxNumContagios=Integer.parseInt(rs.getString("nuevos_casos"));
                   ResMaxNumContagios.setText(String.valueOf(maxNumContagios));
                }
                if (Integer.parseInt(rs.getString("recuperados"))>maxNumRecuperados) {
                    maxNumRecuperados=Integer.parseInt(rs.getString("recuperados"));
                   ResMaxNumRecuperados.setText(String.valueOf(maxNumRecuperados));
                }
                if (Integer.parseInt(rs.getString("fallecidos"))>maxNumFallecidos) {
                    maxNumFallecidos=Integer.parseInt(rs.getString("fallecidos"));
                   ResMaxNumFallecidos.setText(String.valueOf(maxNumFallecidos));
                }
                estaNuevosCasos.setText(rs.getString("nuevos_casos"));
                estaContagiados.setText(rs.getString("totalContagiados"));
                estaRecuperados.setText(rs.getString("totalRecuperados"));
                estaFallecidos.setText(rs.getString("totalFallecidos"));
                double tasaLetalidad=(Double.parseDouble(rs.getString("totalFallecidos"))*100)/Double.parseDouble(rs.getString("totalContagiados"));
                DecimalFormat df2=new DecimalFormat("#.##");
                resTasaLetalidad.setText(String.valueOf(df2.format(tasaLetalidad))+" %");
             //Llenar tabla de reportes
             reporteEpidemiologico r=new reporteEpidemiologico(rs.getString("fecha"),Integer.parseInt(rs.getString("nuevos_casos")),
             Integer.parseInt(rs.getString("totalContagiados")),Integer.parseInt(rs.getString("recuperados")),
             Integer.parseInt(rs.getString("totalRecuperados")),
             Integer.parseInt(rs.getString("fallecidos")),
             Integer.parseInt(rs.getString("totalFallecidos")));
            tablaReporteEpidemiologico.getItems().add(r);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        grafica.setCreateSymbols(false);
        grafica.getData().addAll(series1,series2,series3,series4);
    }

        @FXML
    void ButtonGraficaNacionalReal(MouseEvent event) {
        this.grafica.setVisible(true);
        this.grafica.toFront();
        this.VistaMapa.setVisible(false);
    }

    @FXML
    void ButtonMapaNacionalReal(MouseEvent event) {
        this.VistaMapa.toFront();
        this.VistaMapa.setVisible(true);
                this.grafica.setVisible(false);
    }
    @FXML
    void CheckCuarentenaDinamica(MouseEvent event) {
        if (CheckBoxCuarentenaD.isSelected()) {
        CheckBoxCuarentenaR.setSelected(false);
        CheckBoxSinCuarentena.setSelected(false);
                tipoCuarentena="dinamica";
                tasaDiariaInteraccion=5;
        
        }
    }
    @FXML
    void CheckCuarentenaRigida(MouseEvent event) {
        if (CheckBoxCuarentenaR.isSelected()) {
            CheckBoxCuarentenaD.setSelected(false);
        CheckBoxSinCuarentena.setSelected(false);
        tipoCuarentena="rigida";
        tasaDiariaInteraccion=2.5;
        }
    }
    @FXML
    void CheckSinCuarentena(MouseEvent event) {
        if (CheckBoxSinCuarentena.isSelected()) {
            CheckBoxCuarentenaD.setSelected(false);
        CheckBoxCuarentenaR.setSelected(false);
                tipoCuarentena="sincuarentena";
                tasaDiariaInteraccion=7;
        }
    }
        @FXML
    void checkBoxPeriodoClicked(MouseEvent event) {
            if (conPeriodos==false) {
                checkBoxPeriodo.setSelected(true);
                conPeriodos=true;
            }
            else{
                    checkBoxPeriodo.setSelected(false);
                    conPeriodos=false;
            }
    }
        @FXML
    void SelectedEstadisticas(MouseEvent event) {
         limpiarDatos();
        graficarDatosNacionalesReales();
        this.opcion="Estadísticas";
        tituloSeleccion.setText(opcion+" "+region);
        this.ParametrosSimulacion.setVisible(false);
        this.VistaMapa.setVisible(false);
        this.grafica.setVisible(true);
        this.Estadisticas.setVisible(true);
        this.buttonReportes.setVisible(true);
    }
        @FXML
    void SelectedSimulacion(MouseEvent event) {
            simulacion();
        this.opcion="Simulación";
        tituloSeleccion.setText(opcion+" "+region);
        this.ParametrosSimulacion.setVisible(true);
        this.VistaMapa.setVisible(false);
        this.grafica.setVisible(true);
        this.Estadisticas.setVisible(true);
        this.buttonReportes.setVisible(false);
        
    }
            @FXML
    void SelectedMapaDeRiesgo(MouseEvent event) {
        this.opcion="Mapa de Riesgo";
        tituloSeleccion.setText(opcion+" "+region+" a la fecha: "+fechaEstadisticas);
        this.ParametrosSimulacion.setVisible(false);
        this.VistaMapa.setVisible(true);
        this.grafica.setVisible(false);
        this.Estadisticas.setVisible(false);
        buttonReportes.setVisible(true);
    }
       @FXML
    void TransporteBioseguridadClicked(MouseEvent event) {
            this.cBTransportePublicoBioseguridad=true;
    }
 
    @FXML
    void RestriccionPlacaClicked(MouseEvent event) {
            this.cBTransportePublicoRestriccionPlaca=true;
    }
            @FXML
    void selectedGraficoTabla(MouseEvent event) {
            if (switchGraficoTabla.getText().equals("Tabla de Datos")) {
                grafica.setVisible(false);
                tablaReporteEpidemiologico.setVisible(true);
                VistaMapa.setVisible(false);
                switchGraficoTabla.setText("Grafica");
            }else{
                grafica.setVisible(true);
                tablaReporteEpidemiologico.setVisible(false);
                VistaMapa.setVisible(false);
                switchGraficoTabla.setText("Tabla de Datos");
            }
    }
        @FXML
    void SelectedNacional(MouseEvent event) {
                        simulacion();
        this.opcion="Estadísticas";
        this.region="Bolivia";
        PoblacionInicial.setText("11633371");
        graficarDatosNacionalesReales();
        this.ParametrosSimulacion.setVisible(false);
        this.VistaMapa.setVisible(false);
        this.grafica.setVisible(true);
        this.Estadisticas.setVisible(true);
        tituloSeleccion.setText(opcion+" "+region);
    }
    @FXML void SelectedBeni(MouseEvent event) {   opcion="Estadísticas";   this.region="Beni"; PoblacionInicial.setText("480308"); graficarDatosNacionalesReales();  
        tituloSeleccion.setText(opcion+" "+region);ParametrosSimulacion.setVisible(false);}
    @FXML void SelectedChuquisaca(MouseEvent event) {opcion="Estadísticas";   this.region="Chuquisaca"; PoblacionInicial.setText("637013");graficarDatosNacionalesReales(); 
        tituloSeleccion.setText(opcion+" "+region);ParametrosSimulacion.setVisible(false);}
    @FXML void SelectedCochabamba(MouseEvent event) {opcion="Estadísticas";  this.region="Cochabamba";PoblacionInicial.setText("2028639");graficarDatosNacionalesReales();    
        tituloSeleccion.setText(opcion+" "+region);ParametrosSimulacion.setVisible(false);}   
    @FXML void SelectedLaPaz(MouseEvent event) {opcion="Estadísticas";  this.region="La Paz";PoblacionInicial.setText("2926996");graficarDatosNacionalesReales();    
        tituloSeleccion.setText(opcion+" "+region);ParametrosSimulacion.setVisible(false);}
    @FXML void SelectedOruro(MouseEvent event) {opcion="Estadísticas";   this.region="Oruro";PoblacionInicial.setText("551116");graficarDatosNacionalesReales();    
        tituloSeleccion.setText(opcion+" "+region);ParametrosSimulacion.setVisible(false);}
    @FXML void SelectedPando(MouseEvent event) {opcion="Estadísticas";   this.region="Pando";PoblacionInicial.setText("154355");graficarDatosNacionalesReales();    
        tituloSeleccion.setText(opcion+" "+region);ParametrosSimulacion.setVisible(false);}
    @FXML void SelectedPotosi(MouseEvent event) {opcion="Estadísticas";   this.region="Potosí";PoblacionInicial.setText("901555");graficarDatosNacionalesReales();   
        tituloSeleccion.setText(opcion+" "+region);ParametrosSimulacion.setVisible(false);}
    @FXML void SelectedSantaCruz(MouseEvent event) {opcion="Estadísticas";   this.region="Santa Cruz";PoblacionInicial.setText("3370059");graficarDatosNacionalesReales();    
        tituloSeleccion.setText(opcion+" "+region);ParametrosSimulacion.setVisible(false);}
    @FXML void SelectedTarija(MouseEvent event) {opcion="Estadísticas";   this.region="Tarija";PoblacionInicial.setText("383330");graficarDatosNacionalesReales();    
        tituloSeleccion.setText(opcion+" "+region);ParametrosSimulacion.setVisible(false);}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConnector=new LoadDriver();
        //graficarDatosNacionalesReales();
        CheckBoxSinCuarentena.setSelected(true);
        conn=dbConnector.getConnection();
        //Declaración de columnas de tableview
        columnFecha.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,String>("Fecha"));
        columnNuevosCasos.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("NuevosCasos"));
        columnTotalContagiados.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("TotalContagiados"));
        columnRecuperados.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("Recuperados"));
        columnTotalRecuperados.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("TotalRecuperados"));
        columnFallecidos.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("Fallecidos"));
        columnTotalFallecidos.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("TotalFallecidos"));
        // Llenar datos Mapa de riesgo
        for (int i = 1; i < 10; i++) {
            try {
                conn = LoadDriver.getConnection();
                String sql = "SELECT * FROM reporte_epidemiologico r WHERE r.region_id="+i+";";
                sent= conn.createStatement();
                ResultSet rs=sent.executeQuery(sql);
                String contagiados="";
                while (rs.next()) {                    
                    contagiados=rs.getString("totalContagiados");
                    fechaEstadisticas=rs.getString("fecha");
                }
                switch(i){
                    case 1: contagiosCochabamba.setText(contagiados+" casos"); break;
                    case 2: contagiosLapaz.setText(contagiados+" casos"); break;
                    case 3: contagiosSantacruz.setText(contagiados+" casos"); break;
                    case 4: contagiosOruro.setText(contagiados+" casos"); break;
                    case 5: contagiosPotosi.setText(contagiados+" casos"); break;
                    case 6: contagiosPando.setText(contagiados+" casos"); break;
                    case 7: contagiosBeni.setText(contagiados+" casos"); break;
                    case 8: contagiosChuquisaca.setText(contagiados+" casos"); break;
                    case 9: contagiosTarija.setText(contagiados+" casos"); break;
                    
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(InterfazPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
