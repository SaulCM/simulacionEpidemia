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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
//import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private String opcion="Simulación Mixta";
    private String tipoCuarentena="sincuarentena";
    
    private String fechaEstadisticas;
    private int diasSimulacion;
    private double poblacionInicial;
    private double probabilidadContagio=0.14;
    private double duracionEnfermedad=7;
    private double tasaDiariaInteraccion=7;
    private double tasaDiariaInteraccionCuarentenaR=3;
    private double tasaDiariaInteraccionCuarentenaD=3.9;//4.6
    private double tasaDiariaInteraccionSinCuarentena=7;
    private double tasaRecuperacion=0.95;
    private double tasaLetalidad=0.05;
    
    private double circulacionPoblDias=0;
    private double circulacionPoblHoras=0;
    private double circulacionTransporteDias=0;
    private double diasMercado=0;
            
    private Boolean cBTransportePublicoRestriccionPlaca=false;
    private Boolean cBTransportePublicoBioseguridad=false;
    private Boolean conPeriodos=false;
    private Boolean conValidacion=false;
    private String datoDeComparacion="";
    
    private double contagios=0;
    private double recuperaciones=0;
    private double fallecimientos=0;


    private double susceptibles;
    private double infectados=2;
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
    @FXML private Label labelFechaHastaLaQueSimular;
    @FXML private TextField PoblacionInicial;
    @FXML private CheckBox CheckBoxCuarentenaR;
    @FXML private CheckBox CheckBoxCuarentenaD;
    @FXML private CheckBox CheckBoxSinCuarentena;
    @FXML private AnchorPane VistaMapa;
    @FXML private Label tituloSeleccion;
    @FXML private Label LabelSimulacionBasica;
    @FXML private Label LabelDefinirCuarentenaDinamica;
    @FXML private Button ButtonSimular;
    @FXML private VBox VistaSimulacionMixta;
    
    @FXML private DatePicker DatePickerFechaMixta1;
    @FXML private ComboBox<String> ComboBoxTipoMixta1;
    @FXML private DatePicker DatePickerFechaMixta2;
    @FXML private ComboBox<String> ComboBoxTipoMixta2;
    @FXML private DatePicker DatePickerFechaMixta3;
    @FXML private ComboBox<String> ComboBoxTipoMixta3;
    @FXML private DatePicker DatePickerFechaMixta4;
    @FXML private ComboBox<String> ComboBoxTipoMixta4;

    
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
    
    @FXML private DatePicker DiasSimulacion;
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
    @FXML private CheckBox checkBoxCompararInfectadosReal;
    @FXML private CheckBox checkBoxCompararRecuperadosReal;
    @FXML private CheckBox checkBoxCompararFallecidosReal;
    @FXML private CheckBox checkBoxCompararCasosDiariosReal;
    
    @FXML private CheckBox checkBoxPeriodo;
    @FXML private Button switchGraficoTabla;
    @FXML private Button buttonReportes;
    
    //@FXML private Slider sliderTasaInteraccion;

    
    
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
        DecimalFormat df = new DecimalFormat("#");
        //Se obtiene la población inicial 
        poblacionInicial=Double.parseDouble(PoblacionInicial.getText())*0.2;
        //De acuerdo al tipo de medidas tomadas se altera la variable Tasa diaria de interacción
        if (tipoCuarentena.equals("dinamica")) {
        tasaDiariaInteraccion=tasaDiariaInteraccionCuarentenaD;
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
        }
        
        
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //Calcular los dias de simulacion a partir de la fecha ingresada
                LocalDate fechaSimulacion=DiasSimulacion.getValue();
                for (int j = 0; j < fechas.length; j++) {
                    LocalDate fechaCont=LocalDate.parse(fechas[j],formatter);
                    if (fechaCont.isBefore(fechaSimulacion)||fechaCont.isEqual(fechaSimulacion)) {
                        diasSimulacion++;
                    }
                }
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
                
               // System.out.print("Fecha: "+fechas[i]);System.out.print("       Infectados: "+df.format(infectados)); System.out.println(       "Contagios: "+df.format(contagios));
        }
        graficar();
        mostrarResultados();
        limpiarDatos();
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
         
         DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate fechaSimulacion = DiasSimulacion.getValue();//Obtener la fecha hasta la que se debe simular
         
         boolean flagEsLaFecha=false;
         for (int i = 0; i < datosCalculados.length; i++) {
                if(conPeriodos==true){
                    LocalDate ld=datepickerFechaInicial.getValue();
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
    
     @FXML    void SimularMixta(MouseEvent event) {
         simulacionMixta();
     }
     
    public void simulacionMixta(){
        DecimalFormat df = new DecimalFormat("#");
        //Se borran los datos de anterior simulacion
        //Se obtiene la población inicial 
        poblacionInicial=Double.parseDouble(PoblacionInicial.getText())*0.2;
        //Se obtiene la tasa de interaccion con cuarentena dinamica
        double tasaInteraaccionDinamica=0;
        tasaDiariaInteraccion=tasaDiariaInteraccionCuarentenaD;
        try {
            
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
        tasaDiariaInteraccion-=(0.24-(circulacionTransporteDias/30));
        tasaDiariaInteraccion-=(0.24-(diasMercado/30));
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText(null);
            alert.setContentText("Llene los campos para una cuarentena dinamica");

            alert.showAndWait();
        }
        tasaInteraaccionDinamica=tasaDiariaInteraccion;
        
        double tasaInteraccionPeriodo1=0;
        double tasaInteraccionPeriodo2=0;
        double tasaInteraccionPeriodo3=0;
        double tasaInteraccionPeriodo4=0;
        
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        LocalDate ultimaFecha=LocalDate.now();
        LocalDate fechaPeriodo1=DatePickerFechaMixta1.getValue();
        LocalDate fechaPeriodo2=DatePickerFechaMixta2.getValue();
        LocalDate fechaPeriodo3=DatePickerFechaMixta3.getValue();
        LocalDate fechaPeriodo4=DatePickerFechaMixta4.getValue();
        
        
                if(fechaPeriodo1!=null){
                    ultimaFecha=fechaPeriodo1;
                }
                if(fechaPeriodo2!=null){
                    ultimaFecha=fechaPeriodo2;
                }
                if(fechaPeriodo3!=null){
                    ultimaFecha=fechaPeriodo3;
                }
                if(fechaPeriodo4!=null){
                    ultimaFecha=fechaPeriodo4;
                }
        //Calcular los dias de simulacion a partir del ultimo periodo ingresado
                for (int j = 0; j < fechas.length; j++) {
                    LocalDate fechaCont=LocalDate.parse(fechas[j],formatter);
                    if (fechaCont.isBefore(ultimaFecha)||fechaCont.isEqual(ultimaFecha)) {
                        diasSimulacion++;
                    }
                }
        //Se crea un array para almacenar los datos calculados
        datosCalculados=new double[diasSimulacion][7];
        //Se establece la cantidad de susceptibles inicial
        susceptibles=poblacionInicial-infectados;
        //Obtener las fechas de los periodos 1-4
                LocalDate fechaMixta1 = DatePickerFechaMixta1.getValue();
                LocalDate fechaMixta2 = DatePickerFechaMixta2.getValue();
                LocalDate fechaMixta3 = DatePickerFechaMixta3.getValue();
                LocalDate fechaMixta4 = DatePickerFechaMixta4.getValue();        
                
        int esPeriodo=1; //Flag para reconocer el periodo al que pertenece la fecha dentro de las iteraciones del ciclo
        //Ciclo que calcula las variables de contagios recuperados fallecidos en base a la anterior iteración
        for (int i = 0; i < datosCalculados.length; i++) {
                //Formatear fechas del selector de fechas al formato dd-MM-YYYY

                String fechaFormateada1="";
                String fechaFormateada2="";
                String fechaFormateada3="";
                String fechaFormateada4="";


                //Si se establecen fechas para cuarentena mixta, y se guardan los valores de tasa de interaccion para los periodos ingresados
                    switch(ComboBoxTipoMixta1.getValue()){
                        case "Rígida": tasaInteraccionPeriodo1=tasaDiariaInteraccionCuarentenaR; break;
                        case "Dinámica": tasaInteraccionPeriodo1=tasaInteraaccionDinamica; break;
                        case "Sin Cuarentena": tasaInteraccionPeriodo1=tasaDiariaInteraccionSinCuarentena; break;
                    }

                if (fechaMixta2!=null) {

                    fechaFormateada1=fechaMixta1.format(formatter);
                        if(fechas[i].equals(fechaFormateada1)){
                            switch(ComboBoxTipoMixta2.getValue()){
                                case "Rígida": tasaInteraccionPeriodo2=tasaDiariaInteraccionCuarentenaR; break;
                                case "Dinámica": tasaInteraccionPeriodo2=tasaInteraaccionDinamica; break;
                                case "Sin Cuarentena": tasaInteraccionPeriodo2=tasaDiariaInteraccionSinCuarentena; break;
                            }
                            esPeriodo=2;
                        }
                }
                if (fechaMixta3!=null) {
                    fechaFormateada2=fechaMixta2.format(formatter);
                        if(fechas[i].equals(fechaFormateada2)){
                            switch(ComboBoxTipoMixta3.getValue()){
                                case "Rígida": tasaInteraccionPeriodo3=tasaDiariaInteraccionCuarentenaR; break;
                                case "Dinámica": tasaInteraccionPeriodo3=tasaInteraaccionDinamica; break;
                                case "Sin Cuarentena": tasaInteraccionPeriodo3=tasaDiariaInteraccionSinCuarentena; break;
                            }
                            esPeriodo=3;
                        }
                }
                if (fechaMixta4!=null) {
                    fechaFormateada3=fechaMixta3.format(formatter);
                        if(fechas[i].equals(fechaFormateada3)){
                            switch(ComboBoxTipoMixta4.getValue()){
                                case "Rígida": tasaInteraccionPeriodo4=tasaDiariaInteraccionCuarentenaR; break;
                                case "Dinámica": tasaInteraccionPeriodo4=tasaInteraaccionDinamica; break;
                                case "Sin Cuarentena": tasaInteraccionPeriodo4=tasaDiariaInteraccionSinCuarentena; break;
                            }
                            esPeriodo=4;
                        }       

               }
                //Se modifica la tasa de interaccion de acuerdo al flag de periodo
                switch(esPeriodo){
                    case 1: tasaDiariaInteraccion=tasaInteraccionPeriodo1; break;
                    case 2: tasaDiariaInteraccion=tasaInteraccionPeriodo2; break;
                    case 3: tasaDiariaInteraccion=tasaInteraccionPeriodo3; break;
                    case 4: tasaDiariaInteraccion=tasaInteraccionPeriodo4; break;

                }

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
                    /*String sql = "insert into simulacion (casos_nuevos,infectados, recuperaciones, recuperados, fallecimientos, fallecidos) values('1','2','3','4','5','6')";
                    PreparedStatement a=null;
                    try {
                        conn = dbConnector.getConnection();
                            a = conn.prepareStatement(sql); 
                        sent.execute(sql);
                        System.out.println("EXito: ");                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                                
                    }   */
        /*DecimalFormat df = new DecimalFormat("#");
        System.out.print("   TasaContagio:"+df.format(tasaDiariaInteraccion));
        System.out.print("   Conagios:"+df.format(contagios));
        System.out.print("   Recuperaciones:"+df.format(recuperaciones));
        System.out.print("   Fallecimientos:"+df.format(fallecimientos));
        System.out.print("   Susceptibles"+df.format(susceptibles));
        System.out.print("   Fallecidos:"+df.format(fallecidos));
        System.out.print("   Recuperados:"+df.format(recuperados));
        System.out.print("   Infectados:"+df.format(infectados));
        System.out.println();*/
        
        }   
        System.out.println(tasaDiariaInteraccion);
        mostrarResultados();
        graficarMixta();
        habilitarDatepickers();
    }
    public void habilitarDatepickers(){
            //habilitar datepickers
       if (DatePickerFechaMixta3.isDisabled()==false&&DatePickerFechaMixta4.isDisabled()==true&&DatePickerFechaMixta3.getValue()!=null) {
            DatePickerFechaMixta4.setDisable(false);
            DatePickerFechaMixta4.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate limite = DatePickerFechaMixta3.getValue();
                    setDisable(empty || date.compareTo(limite) <= 0 );
                }
            });
        }
               if (DatePickerFechaMixta2.isDisabled()==false&&DatePickerFechaMixta3.isDisabled()==true&&DatePickerFechaMixta2.getValue()!=null) {
            DatePickerFechaMixta3.setDisable(false);
            DatePickerFechaMixta3.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate limite = DatePickerFechaMixta2.getValue();
                    setDisable(empty || date.compareTo(limite) <= 0 );
                }
            });
        }
               if (DatePickerFechaMixta2.isDisabled()==true&&DatePickerFechaMixta1.getValue()!=null) {
            DatePickerFechaMixta2.setDisable(false);
            DatePickerFechaMixta2.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate limite = DatePickerFechaMixta1.getValue();
                    setDisable(empty || date.compareTo(limite) <= 0 );
                }
            });
        }
    }
    @FXML 
    void reiniciarPeriodos(MouseEvent event){
        ComboBoxTipoMixta2.setValue(null);
        ComboBoxTipoMixta3.setValue(null);
        ComboBoxTipoMixta4.setValue(null);
        DatePickerFechaMixta2.setDisable(true);
        DatePickerFechaMixta3.setDisable(true);
        DatePickerFechaMixta4.setDisable(true);
        
    }
    public void graficarMixta(){
        conValidacion=false;
    grafica.getData().clear();// Borrar datos anteriores
         XYChart.Series seriesInf1=new XYChart.Series();
         XYChart.Series seriesInf2=new XYChart.Series();
         XYChart.Series seriesInf3=new XYChart.Series();
         XYChart.Series seriesInf4=new XYChart.Series();
         XYChart.Series seriesRecu1=new XYChart.Series();
         XYChart.Series seriesRecu2=new XYChart.Series();
         XYChart.Series seriesRecu3=new XYChart.Series();
         XYChart.Series seriesRecu4=new XYChart.Series();
         XYChart.Series seriesFalle1=new XYChart.Series();
         XYChart.Series seriesFalle2=new XYChart.Series();
         XYChart.Series seriesFalle3=new XYChart.Series();
         XYChart.Series seriesFalle4=new XYChart.Series();
         XYChart.Series seriesCont1=new XYChart.Series();
         XYChart.Series seriesCont2=new XYChart.Series();
         XYChart.Series seriesCont3=new XYChart.Series();
         XYChart.Series seriesCont4=new XYChart.Series();
         
         //Nombrar las series de datos
         seriesInf1.setName("Infectados P1");
         seriesInf2.setName("Infectados P2");
         seriesInf3.setName("Infectados P3");
         seriesInf4.setName("Infectados P4");
         seriesRecu1.setName("Recuperados P1");
         seriesRecu2.setName("Recuperados P2");
         seriesRecu3.setName("Recuperados P3");
         seriesRecu4.setName("Recuperados P4");
         seriesFalle1.setName("Fallecidos P1");
         seriesFalle2.setName("Fallecidos P2");
         seriesFalle3.setName("Fallecidos P3");
         seriesFalle4.setName("Fallecidos P4");
         seriesCont1.setName("Contagios P1");
         seriesCont2.setName("Contagios P2");
         seriesCont3.setName("Contagios P3");
         seriesCont4.setName("Contagios P4");
         
         DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
            LocalDate fechaMixta1 = DatePickerFechaMixta1.getValue();
            LocalDate fechaMixta2 = DatePickerFechaMixta2.getValue();
            LocalDate fechaMixta3 = DatePickerFechaMixta3.getValue();
            LocalDate fechaMixta4 = DatePickerFechaMixta4.getValue();
            
        
        //Se establece la cantidad de susceptibles inicial
        susceptibles=poblacionInicial-infectados;
        
               LocalDate fechaSimulacion = DiasSimulacion.getValue();//Obtener la fecha hasta la que se debe simular
        //Ciclo que calcula las variables de contagios recuperados fallecidos en base a la anterior iteración
         boolean flagEsLaFecha=false;
         for (int i = 0; i < datosCalculados.length; i++) {
              //LocalDate fechaCont=LocalDate.parse(fechas[i],formatter);
            //if(fechaCont.isBefore(fechaSimulacion)||fechaCont.isEqual(fechaSimulacion)){       
                LocalDate fechaIteracion= LocalDate.parse(fechas[i], formatter);
                if(conPeriodos==true){
                    LocalDate ld=datepickerFechaInicial.getValue();
                   String fechaIni=ld.format(formatter);
                    if (fechas[i].equals(fechaIni)) {
                        flagEsLaFecha=true;

                        }

                        LocalDate ld2=datepickerFechaFinal.getValue();
                        String fechaFin=ld2.format(formatter);
                    if (fechas[i].equals(fechaFin)) {

                           if(fechaMixta1!=null&&(fechaIteracion.isBefore(fechaMixta1)||fechaIteracion.isEqual(fechaMixta1))){
                               seriesInf1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta2!=null&&(fechaIteracion.isBefore(fechaMixta2)||fechaIteracion.isEqual(fechaMixta2))&&(fechaIteracion.isAfter(fechaMixta1))){
                               seriesInf2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta3!=null&&(fechaIteracion.isBefore(fechaMixta3)||fechaIteracion.isEqual(fechaMixta3))&&(fechaIteracion.isAfter(fechaMixta2))){
                               seriesInf3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta4!=null&&(fechaIteracion.isBefore(fechaMixta4)||fechaIteracion.isEqual(fechaMixta4))&&(fechaIteracion.isAfter(fechaMixta3))){
                               seriesInf4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0])); 
                           }
                           flagEsLaFecha=false;
                      }
                    if (flagEsLaFecha==true) {

                           if(fechaMixta1!=null&&(fechaIteracion.isBefore(fechaMixta1)||fechaIteracion.isEqual(fechaMixta1))){
                               seriesInf1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta2!=null&&(fechaIteracion.isBefore(fechaMixta2)||fechaIteracion.isEqual(fechaMixta2))&&(fechaIteracion.isAfter(fechaMixta1))){
                               seriesInf2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta3!=null&&(fechaIteracion.isBefore(fechaMixta3)||fechaIteracion.isEqual(fechaMixta3))&&(fechaIteracion.isAfter(fechaMixta2))){
                               seriesInf3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta4!=null&&(fechaIteracion.isBefore(fechaMixta4)||fechaIteracion.isEqual(fechaMixta4))&&(fechaIteracion.isAfter(fechaMixta3))){
                               seriesInf4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0])); 
                           }
                    }
                }
                else{
                           if(fechaMixta1!=null&&(fechaIteracion.isBefore(fechaMixta1)||fechaIteracion.isEqual(fechaMixta1))){
                               seriesInf1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta2!=null&&((fechaIteracion.isBefore(fechaMixta2)&&(fechaIteracion.isAfter(fechaMixta1)))||fechaIteracion.isEqual(fechaMixta2))){
                               seriesInf2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta3!=null&&(fechaIteracion.isBefore(fechaMixta3)||fechaIteracion.isEqual(fechaMixta3))&&(fechaIteracion.isAfter(fechaMixta2))){
                               seriesInf3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta4!=null&&(fechaIteracion.isBefore(fechaMixta4)||fechaIteracion.isEqual(fechaMixta4))&&(fechaIteracion.isAfter(fechaMixta3))){
                               seriesInf4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }          
                }
            //}
        }
         grafica.setCreateSymbols(false);
         if(fechaMixta1!=null){
             grafica.getData().addAll(seriesInf1,seriesRecu1, seriesFalle1, seriesCont1);
         }
         if(fechaMixta2!=null){
             grafica.getData().addAll(seriesInf2,seriesRecu2, seriesFalle2, seriesCont2);
         }
         if(fechaMixta3!=null){
             grafica.getData().addAll(seriesInf3,seriesRecu3, seriesFalle3, seriesCont3);
         }
         if(fechaMixta4!=null){
             grafica.getData().addAll(seriesInf4,seriesRecu4, seriesFalle4, seriesCont4);
         }
         checkBoxCompararInfectadosReal.setSelected(false);
         checkBoxCompararRecuperadosReal.setSelected(false);
         checkBoxCompararFallecidosReal.setSelected(false);
         checkBoxCompararCasosDiariosReal.setSelected(false);
        limpiarDatos();
    }
    public void graficarMixtaConValidacion(){
            grafica.getData().clear();// Borrar datos anteriores
         XYChart.Series seriesInf1=new XYChart.Series();
         XYChart.Series seriesInf2=new XYChart.Series();
         XYChart.Series seriesInf3=new XYChart.Series();
         XYChart.Series seriesInf4=new XYChart.Series();
         XYChart.Series seriesRecu1=new XYChart.Series();
         XYChart.Series seriesRecu2=new XYChart.Series();
         XYChart.Series seriesRecu3=new XYChart.Series();
         XYChart.Series seriesRecu4=new XYChart.Series();
         XYChart.Series seriesFalle1=new XYChart.Series();
         XYChart.Series seriesFalle2=new XYChart.Series();
         XYChart.Series seriesFalle3=new XYChart.Series();
         XYChart.Series seriesFalle4=new XYChart.Series();
         XYChart.Series seriesCont1=new XYChart.Series();
         XYChart.Series seriesCont2=new XYChart.Series();
         XYChart.Series seriesCont3=new XYChart.Series();
         XYChart.Series seriesCont4=new XYChart.Series();
         //series para datos reales
         XYChart.Series seriesRealInf=new XYChart.Series();
         XYChart.Series seriesRealCont=new XYChart.Series();
         XYChart.Series seriesRealRecu=new XYChart.Series();
         XYChart.Series seriesRealFalle=new XYChart.Series();

         //Nombrar las series de datos
         seriesInf1.setName("Infectados P1");
         seriesInf2.setName("Infectados P2");
         seriesInf3.setName("Infectados P3");
         seriesInf4.setName("Infectados P4");
         seriesRecu1.setName("Recuperados P1");
         seriesRecu2.setName("Recuperados P2");
         seriesRecu3.setName("Recuperados P3");
         seriesRecu4.setName("Recuperados P4");
         seriesFalle1.setName("Fallecidos P1");
         seriesFalle2.setName("Fallecidos P2");
         seriesFalle3.setName("Fallecidos P3");
         seriesFalle4.setName("Fallecidos P4");
         seriesCont1.setName("Contagios P1");
         seriesCont2.setName("Contagios P2");
         seriesCont3.setName("Contagios P3");
         seriesCont4.setName("Contagios P4");
         //nombre series reales
         seriesRealInf.setName("Infectados REAL");
         seriesRealCont.setName("Contagios REAL");
         seriesRealRecu.setName("Recuperados REAL");
         seriesRealFalle.setName("Fallecidos REAL");

        
         DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
            LocalDate fechaMixta1 = DatePickerFechaMixta1.getValue();
            LocalDate fechaMixta2 = DatePickerFechaMixta2.getValue();
            LocalDate fechaMixta3 = DatePickerFechaMixta3.getValue();
            LocalDate fechaMixta4 = DatePickerFechaMixta4.getValue();
            
        
        //Se establece la cantidad de susceptibles inicial
        susceptibles=poblacionInicial-infectados;
        
               LocalDate fechaSimulacion = DiasSimulacion.getValue();//Obtener la fecha hasta la que se debe simular
        //Ciclo que calcula las variables de contagios recuperados fallecidos en base a la anterior iteración
         boolean flagEsLaFecha=false;
         for (int i = 0; i < datosCalculados.length; i++) {
              //LocalDate fechaCont=LocalDate.parse(fechas[i],formatter);
            //if(fechaCont.isBefore(fechaSimulacion)||fechaCont.isEqual(fechaSimulacion)){       
                LocalDate fechaIteracion= LocalDate.parse(fechas[i], formatter);
                if(conPeriodos==true){
                    LocalDate ld=datepickerFechaInicial.getValue();
                   String fechaIni=ld.format(formatter);
                    if (fechas[i].equals(fechaIni)) {
                        flagEsLaFecha=true;

                        }

                        LocalDate ld2=datepickerFechaFinal.getValue();
                        String fechaFin=ld2.format(formatter);
                    if (fechas[i].equals(fechaFin)) {

                           if(fechaMixta1!=null&&(fechaIteracion.isBefore(fechaMixta1)||fechaIteracion.isEqual(fechaMixta1))){
                               seriesInf1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta2!=null&&(fechaIteracion.isBefore(fechaMixta2)||fechaIteracion.isEqual(fechaMixta2))&&(fechaIteracion.isAfter(fechaMixta1))){
                               seriesInf2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta3!=null&&(fechaIteracion.isBefore(fechaMixta3)||fechaIteracion.isEqual(fechaMixta3))&&(fechaIteracion.isAfter(fechaMixta2))){
                               seriesInf3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta4!=null&&(fechaIteracion.isBefore(fechaMixta4)||fechaIteracion.isEqual(fechaMixta4))&&(fechaIteracion.isAfter(fechaMixta3))){
                               seriesInf4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0])); 
                           }
                           flagEsLaFecha=false;
                      }
                    if (flagEsLaFecha==true) {

                           if(fechaMixta1!=null&&(fechaIteracion.isBefore(fechaMixta1)||fechaIteracion.isEqual(fechaMixta1))){
                               seriesInf1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta2!=null&&(fechaIteracion.isBefore(fechaMixta2)||fechaIteracion.isEqual(fechaMixta2))&&(fechaIteracion.isAfter(fechaMixta1))){
                               seriesInf2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta3!=null&&(fechaIteracion.isBefore(fechaMixta3)||fechaIteracion.isEqual(fechaMixta3))&&(fechaIteracion.isAfter(fechaMixta2))){
                               seriesInf3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta4!=null&&(fechaIteracion.isBefore(fechaMixta4)||fechaIteracion.isEqual(fechaMixta4))&&(fechaIteracion.isAfter(fechaMixta3))){
                               seriesInf4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                    }
                }
                else{
                           if(fechaMixta1!=null&&(fechaIteracion.isBefore(fechaMixta1)||fechaIteracion.isEqual(fechaMixta1))){
                               seriesInf1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont1.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta2!=null&&((fechaIteracion.isBefore(fechaMixta2)&&(fechaIteracion.isAfter(fechaMixta1)))||fechaIteracion.isEqual(fechaMixta2))){
                               seriesInf2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont2.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta3!=null&&(fechaIteracion.isBefore(fechaMixta3)||fechaIteracion.isEqual(fechaMixta3))&&(fechaIteracion.isAfter(fechaMixta2))){
                               seriesInf3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont3.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0]));
                           }
                           if(fechaMixta4!=null&&(fechaIteracion.isBefore(fechaMixta4)||fechaIteracion.isEqual(fechaMixta4))&&(fechaIteracion.isAfter(fechaMixta3))){
                               seriesInf4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][4]));
                               seriesRecu4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][5]));
                               seriesFalle4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][6]));
                               seriesCont4.getData().add(new XYChart.Data(fechas[i],datosCalculados[i][0])); 
                           }          
                }
            //}
        }
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
            String sql = "SELECT * FROM reporte_epidemiologico r WHERE r.region_id="+region_id+"";
            sent= conn.createStatement();
            ResultSet rs=sent.executeQuery(sql);
            int cont=0;
            //Mientras hayan registros recuperados de la base de datos, se ejecuta el codigo para cada registro
            while(rs.next()){
                //Recuperar los campos de cada registro
                contagios=Integer.parseInt(rs.getString("nuevos_casos"));
                infectados=Integer.parseInt(rs.getString("totalContagiados"));
                recuperados=Integer.parseInt(rs.getString("totalRecuperados")); 
                fallecidos=Integer.parseInt(rs.getString("totalFallecidos"));
                //recuperados=Integer.parseInt(rs.getString("totalRecuperados"));
                //fallecidos=Integer.parseInt(rs.getString("totalFallecidos"));
                seriesRealCont.getData().add(new XYChart.Data(fechas[cont],contagios));
                seriesRealInf.getData().add(new XYChart.Data(fechas[cont],infectados));
                seriesRealRecu.getData().add(new XYChart.Data(fechas[cont],recuperados));
                seriesRealFalle.getData().add(new XYChart.Data(fechas[cont],fallecidos));
                cont++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //caso de datos simulados a mostrar en la comparacion
         XYChart.Series casoSerie=new XYChart.Series();
         XYChart.Series casoSerieReal=new XYChart.Series();
            //Por cada periodo la serie de datos correspondiente
         if(fechaMixta1!=null){
             switch(datoDeComparacion){
                case "Infectados": casoSerie=seriesInf1; break;
                case "Recuperados": casoSerie=seriesRecu1; break;
                case "Fallecidos": casoSerie=seriesFalle1; break;
                case "CasosDiarios": casoSerie=seriesCont1; break;
            }  
            grafica.getData().addAll(casoSerie);
         }
         if(fechaMixta2!=null){
             switch(datoDeComparacion){
                case "Infectados": casoSerie=seriesInf2; break;
                case "Recuperados": casoSerie=seriesRecu2; break;
                case "Fallecidos": casoSerie=seriesFalle2; break;
                case "CasosDiarios": casoSerie=seriesCont2; break;
            }  
             grafica.getData().addAll(casoSerie);
         }
         if(fechaMixta3!=null){
             switch(datoDeComparacion){
                case "Infectados": casoSerie=seriesInf3; break;
                case "Recuperados": casoSerie=seriesRecu3; break;
                case "Fallecidos": casoSerie=seriesFalle3; break;
                case "CasosDiarios": casoSerie=seriesCont3; break;
            }  
             grafica.getData().addAll(casoSerie);
         }
         if(fechaMixta4!=null){
             switch(datoDeComparacion){
                case "Infectados": casoSerie=seriesInf4; break;
                case "Recuperados": casoSerie=seriesRecu4; break;
                case "Fallecidos": casoSerie=seriesFalle4; break;
                case "CasosDiarios": casoSerie=seriesCont4; break;
            }  
             grafica.setCreateSymbols(false);
             grafica.getData().addAll(casoSerie);
         }
         //caso de dato real a graficar
        switch(datoDeComparacion){
                case "Infectados": casoSerieReal=seriesRealInf; break;
                case "Recuperados": casoSerieReal=seriesRealRecu; break;
                case "Fallecidos": casoSerieReal=seriesRealFalle; break;
                case "CasosDiarios": casoSerieReal=seriesRealCont; break;
        }  
         grafica.getData().addAll(casoSerieReal);
        limpiarDatos();
    }
    
        @FXML
    void selectedGraficaCompararInfectadosReal(MouseEvent event) {
        datoDeComparacion="Infectados";    
        checkBoxCompararRecuperadosReal.setSelected(false);
        checkBoxCompararFallecidosReal.setSelected(false);
        checkBoxCompararCasosDiariosReal.setSelected(false);
            graficarMixtaConValidacion();
            
    }
        @FXML
    void selectedGraficaCompararRecuperadosReal(MouseEvent event) {
        datoDeComparacion="Recuperados";    
        System.out.println(checkBoxCompararInfectadosReal.isSelected());
        checkBoxCompararInfectadosReal.setSelected(false);
        checkBoxCompararFallecidosReal.setSelected(false);
        checkBoxCompararCasosDiariosReal.setSelected(false);
        graficarMixtaConValidacion();
    }
        @FXML
    void selectedGraficaCompararFallecidosReal(MouseEvent event) {
        datoDeComparacion="Fallecidos";    
        checkBoxCompararInfectadosReal.setSelected(false);
        checkBoxCompararRecuperadosReal.setSelected(false);
        checkBoxCompararCasosDiariosReal.setSelected(false);
        graficarMixtaConValidacion();
    }
        @FXML
    void selectedGraficaCompararCasosDiariosReal(MouseEvent event) {
        datoDeComparacion="CasosDiarios";    
        checkBoxCompararInfectadosReal.setSelected(false);
        checkBoxCompararRecuperadosReal.setSelected(false);
        checkBoxCompararFallecidosReal.setSelected(false);
        graficarMixtaConValidacion();
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

    infectados=2;
    recuperados=0;
    fallecidos=0;
            totalInfectados=0;
            totalRecuperados=0;
            totalFallecidos=0;
                    mayorContagios=0;
        mayorInfectados=0; 
        mayorRecuperaciones=0;
        mayorFallecimientos=0;
    diasSimulacion=0;
    tasaDiariaInteraccion=0;

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
                        series1.getData().add(new XYChart.Data(fechaFormateada,contagiados));
                        series2.getData().add(new XYChart.Data(fechaFormateada,recuperados));
                        series3.getData().add(new XYChart.Data(fechaFormateada,fallecidos));
                        series4.getData().add(new XYChart.Data(fechaFormateada,nuevosCasos));  
                        flagEsLaFecha=false;
                   }
                 if (flagEsLaFecha==true) {
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
                datepickerFechaInicial.setVisible(true);
                datepickerFechaFinal.setVisible(true);
            }
            else{
                    checkBoxPeriodo.setSelected(false);
                    conPeriodos=false;
                    datepickerFechaInicial.setVisible(false);
                datepickerFechaFinal.setVisible(false);
            }
    }
        @FXML
    void SelectedEstadisticas(MouseEvent event) {
         limpiarDatos();
         vistaEnEstadisticas();
    }
        @FXML
    void SelectedSimulacion(MouseEvent event) {
        this.opcion="Simulación";
        tituloSeleccion.setText(opcion+" "+region);
        this.ParametrosSimulacion.setVisible(true);
        this.VistaMapa.setVisible(false);
        this.grafica.setVisible(true);
        this.Estadisticas.setVisible(true);
        this.buttonReportes.setVisible(false);
        this.VistaSimulacionMixta.setVisible(false);
        this.LabelSimulacionBasica.setVisible(true);
        this.ButtonSimular.setVisible(true);
        this.CheckBoxCuarentenaR.setVisible(true);
        this.CheckBoxSinCuarentena.setVisible(true);
        this.switchGraficoTabla.setVisible(false);
        this.tablaReporteEpidemiologico.setVisible(false);
        this.CheckBoxCuarentenaD.setVisible(true);
        this.LabelDefinirCuarentenaDinamica.setVisible(false);
        this.labelFechaHastaLaQueSimular.setVisible(true);
        this.DiasSimulacion.setVisible(true);
                    simulacion();
    }
        @FXML
    void SelectedSimulacionMixta(MouseEvent event) {
        this.opcion="Simulación Mixta";
        tituloSeleccion.setText(opcion+" "+region);
        this.ParametrosSimulacion.setVisible(true);
        this.VistaMapa.setVisible(false);
        this.grafica.setVisible(true);
        this.Estadisticas.setVisible(true);
        this.buttonReportes.setVisible(false);
        this.VistaSimulacionMixta.setVisible(true);
        this.LabelSimulacionBasica.setVisible(false);
        this.ButtonSimular.setVisible(false);
        this.CheckBoxCuarentenaR.setVisible(false);
        this.CheckBoxSinCuarentena.setVisible(false);
        this.switchGraficoTabla.setVisible(false);
        this.tablaReporteEpidemiologico.setVisible(false);
        this.CheckBoxCuarentenaD.setVisible(false);
        this.LabelDefinirCuarentenaDinamica.setVisible(true);
        this.labelFechaHastaLaQueSimular.setVisible(false);
        this.DiasSimulacion.setVisible(false);
    }
            @FXML
    void SelectedMapaDeRiesgo(MouseEvent event) {
        this.opcion="Mapa de Riesgo";
        tituloSeleccion.setText("Mapa de riesgo"+ " a la fecha: "+fechaEstadisticas);
        this.ParametrosSimulacion.setVisible(false);
        this.VistaMapa.setVisible(true);
        this.grafica.setVisible(false);
        this.Estadisticas.setVisible(false);
        buttonReportes.setVisible(true);
        this.tablaReporteEpidemiologico.setVisible(false);
        
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
            if (switchGraficoTabla.getText().equals("Cambiar a Tabla de Datos")) {
                grafica.setVisible(false);
                tablaReporteEpidemiologico.setVisible(true);
                VistaMapa.setVisible(false);
                switchGraficoTabla.setText("Cambiar a Grafica");
            }else{
                grafica.setVisible(true);
                tablaReporteEpidemiologico.setVisible(false);
                VistaMapa.setVisible(false);
                switchGraficoTabla.setText("Cambiar a Tabla de Datos");
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
        this.tablaReporteEpidemiologico.setVisible(false);
    }
    public void vistaEnEstadisticas(){
        graficarDatosNacionalesReales();
        this.opcion="Estadísticas";
        tituloSeleccion.setText(opcion+" "+region);
        this.ParametrosSimulacion.setVisible(false);
        this.VistaMapa.setVisible(false);
        this.grafica.setVisible(true);
        this.Estadisticas.setVisible(true);
        this.buttonReportes.setVisible(true);
        this.switchGraficoTabla.setVisible(true);
        this.tablaReporteEpidemiologico.setVisible(false);
        switchGraficoTabla.setText("Cambiar a Tabla de Datos");
    }
    @FXML void SelectedBeni(MouseEvent event) {     this.region="Beni"; PoblacionInicial.setText("480308"); vistaEnEstadisticas(); }
    @FXML void SelectedChuquisaca(MouseEvent event) { this.region="Chuquisaca"; PoblacionInicial.setText("637013");vistaEnEstadisticas();}
    @FXML void SelectedCochabamba(MouseEvent event) {  this.region="Cochabamba";PoblacionInicial.setText("2028639");vistaEnEstadisticas();}  
    @FXML void SelectedLaPaz(MouseEvent event) { this.region="La Paz";PoblacionInicial.setText("2926996");  vistaEnEstadisticas(); }
    @FXML void SelectedOruro(MouseEvent event) { this.region="Oruro";PoblacionInicial.setText("551116"); vistaEnEstadisticas(); }
    @FXML void SelectedPando(MouseEvent event) { this.region="Pando";PoblacionInicial.setText("154355");vistaEnEstadisticas(); }
    @FXML void SelectedPotosi(MouseEvent event) {  this.region="Potosí";PoblacionInicial.setText("901555"); vistaEnEstadisticas(); }
    @FXML void SelectedSantaCruz(MouseEvent event) {  this.region="Santa Cruz";PoblacionInicial.setText("3370059");graficarDatosNacionalesReales();   vistaEnEstadisticas(); }
    @FXML void SelectedTarija(MouseEvent event) { this.region="Tarija";PoblacionInicial.setText("383330");graficarDatosNacionalesReales();  vistaEnEstadisticas();  }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConnector=new LoadDriver();
        //sliderTasaInteraccion.setMin(1.5);
        //sliderTasaInteraccion.setMax(3.0);

        //sliderTasaInteraccion.setShowTickLabels(true);
        //sliderTasaInteraccion.setShowTickMarks(true);
        //Vista predefinida en simulacion mixta 
                this.opcion="Simulación Mixta";
                tituloSeleccion.setText(opcion+" "+region);
                this.ParametrosSimulacion.setVisible(true);
                this.VistaMapa.setVisible(false);
                this.grafica.setVisible(true);
                this.Estadisticas.setVisible(true);
                this.buttonReportes.setVisible(false);
                this.VistaSimulacionMixta.setVisible(true);
                this.LabelSimulacionBasica.setVisible(false);
                this.ButtonSimular.setVisible(false);
                this.CheckBoxCuarentenaR.setVisible(false);
                this.CheckBoxSinCuarentena.setVisible(false);
                this.switchGraficoTabla.setVisible(false);
                this.tablaReporteEpidemiologico.setVisible(false);
                this.CheckBoxCuarentenaD.setVisible(false);
                this.LabelDefinirCuarentenaDinamica.setVisible(true);
                this.labelFechaHastaLaQueSimular.setVisible(false);
                this.DiasSimulacion.setVisible(false);
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
        //Llenar los items de tipo de cuarentena para simulacion mixta
        ComboBoxTipoMixta1.getItems().addAll("Rígida","Dinámica","Sin Cuarentena");
        ComboBoxTipoMixta2.getItems().addAll("Rígida","Dinámica","Sin Cuarentena");
        ComboBoxTipoMixta3.getItems().addAll("Rígida","Dinámica","Sin Cuarentena");
        ComboBoxTipoMixta4.getItems().addAll("Rígida","Dinámica","Sin Cuarentena");
        //Poner Fecha actual al selector de fechas de simualacion normal
        DiasSimulacion.setValue(LocalDate.now());
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
        //validadores
        CirculacionPoblDias.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        if (!newValue) { 
           // Cuando no es igual al patron definido, se pone vacio
            if(!CirculacionPoblDias.getText().matches("[1-7]")){
                CirculacionPoblDias.setText("");
            }
        }});
        CirculacionPoblHoras.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        if (!newValue) { 
            // Cuando no es igual al patron definido, se pone vacio
            if(!CirculacionPoblHoras.getText().matches("[1-9]|1[0-9]|2[0-4]")){
                CirculacionPoblHoras.setText("");
            }
        }});
        CirculacionTransporteDias.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        if (!newValue) { 
            // Cuando no es igual al patron definido, se pone vacio
            if(!CirculacionTransporteDias.getText().matches("[1-7]")){
                CirculacionTransporteDias.setText("");
            }
        }});
        DiasMercado.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        if (!newValue) { 
            // Cuando no es igual al patron definido, se pone vacio
            if(!DiasMercado.getText().matches("[1-7]")){
                DiasMercado.setText("");
            }
        }});
        //definir fecha actual para el primer periodo de simumlacion mixta
        DatePickerFechaMixta1.setValue(LocalDate.now());
    }
    
}
