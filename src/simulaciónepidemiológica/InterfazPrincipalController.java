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
import java.util.ArrayList;
import java.util.Arrays;
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

/**
 * FXML Controller class
 *
 * @author Master
 */
public class InterfazPrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    private ArrayList<Integer> datosNalRealCasosTotales=new ArrayList<Integer>(Arrays.asList(2,3,3,10,10,11,11,12,12,17,19,20,27,28,32,39,61,74,81,96,107,115,
            123,132,139,157,183,194,210,264,268,275,300,330,354,397,441,465,493,520,564,598,609,672,703,807,866,950,1014,1053,1110,1167,
            1229,1470,1594,1681,1802,1886,2081,2266,2437,2556,2831,2964,3148,3372,3577,3826,4088,4263,4481,4919,5187,5579,5915,6263,6660,7136,7768,8387,8731,9592,9982,
            10531,10991,11638,12245,12728,13358,13643,13949,14644,15281,16165,16929,17642,18468,19073,19883,20685,21499,22476,23512,24388,25493,26389,27487,28503,29423,30676,31524,32125,33219,
            34227,35528,36818,38071,39297,40509,41545,42984,44113,45565,47200,48187,49250,50867,
            52218,54156,56102,58138,59582,60991,62357));
    private ArrayList<Integer> datosNalRealCasosActivos=new ArrayList<Integer>(Arrays.asList(2,3,3,10,10,11,11,12,12,17,19,20,27,28,32,39,61,74,81,93,101,108,
                114,121,127,145,170,177,192,243,245,251,272,297,320,362,398,408,431,457,500,517,525,582,597,700,745,819,861,888,934,973,
                1029,1240,1352,1425,1529,1597,1760,1923,2065,2166,2421,2523,2667,2864,2979,3188,3426,3586,3759,4167,4411,4774,5066,5384,5752,6185,6799,7356,7682,8393,8683,
                9051,9317,9731,10172,10562,11002,11092,11315,11967,12508,13260,13939,14489,14735,15011,15472,16004,16482,17091,17686,18161,18816,19243,19816,20252,20753,21548,21993,22126,22756,
                23262,23899,24732,25421,26196,27104,27617,28524,29121,29945,31113,31537,32090,33150,
                33919,35193,36171,37832,38878,39898,40794));
    private ArrayList<Integer> datosNalRealRecuperados=new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                1,2,2,2,2,3,3,3,4,4,4,6,6,7,14,26,31,31,31,47,47,50,63,63,75,81,100,110,117,132,
                134,159,166,174,187,198,219,237,258,275,289,313,339,356,434,473,493,503,533,553,561,575,609,629,647,677,689,738,749,889,986,
                137,1298,1507,1658,1739,1902,2086,2159,2190,2261,2372,2431,2768,3113,3430,3752,4002,4320,4670,5086,5454,5857,6300,6795,7338,7736,8158,8517,8928,9340,
                9764,10358,10766,11272,11667,11929,12398,12883,13354,13918,14333,14843,15294,15819,
                16357,16979,17882,18200,18553,18875,19290));
    private ArrayList<Integer> datosNalRealFallecidos=new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,6,7,
                8,9,10,10,11,14,15,18,19,20,24,27,28,28,29,31,31,32,33,34,37,40,43,44,46,50,53,55,59,62,
                66,71,76,82,86,91,102,106,114,117,121,128,142,152,164,165,169,174,189,199,215,230,240,250,261,274,280,293,300,310,313,
                343,376,400,415,427,454,465,475,487,512,533,559,585,611,632,659,679,697,715,740,773,820,846,876,913,934,970,1014,1071,1123,
                1201,1271,1320,1378,1434,1476,1530,1577,1638,1702,1754,1807,1866,1898,
                1942,1984,2049,2106,2151,2218,2273));
    
    
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
    
    @FXML private Button switchGraficoTabla;



    @FXML
    void Simular(MouseEvent event) {

        simulacion();

    }
    public void simulacion(){
        poblacionInicial=Double.parseDouble(PoblacionInicial.getText());
        
        if (tipoCuarentena.equals("dinamica")) {
        tasaDiariaInteraccion=4.0;
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
        System.out.println(cBTransportePublicoBioseguridad);
        
        tasaDiariaInteraccion-=(1-(circulacionPoblDias/10));
        tasaDiariaInteraccion-=(1-(circulacionPoblHoras/100));
        tasaDiariaInteraccion-=(circulacionTransporteDias/100);
        tasaDiariaInteraccion-=(diasMercado/100);

        }
        
        diasSimulacion=Integer.parseInt(DiasSimulacion.getText());
        datosCalculados=new double[diasSimulacion][7];
        susceptibles=poblacionInicial-infectados;
        
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

                System.out.print(String.valueOf(df.format(datosCalculados[i][0]))+" ");
                System.out.print(String.valueOf(df.format(datosCalculados[i][1]))+" ");
                System.out.print(String.valueOf(df.format(datosCalculados[i][2]))+" ");
                System.out.print(String.valueOf(df.format(datosCalculados[i][3]))+" ");
                System.out.print(String.valueOf(df.format(datosCalculados[i][4]))+" ");
                System.out.print(String.valueOf(df.format(datosCalculados[i][5]))+" ");
                System.out.print(String.valueOf(df.format(datosCalculados[i][6]))+" ");
                System.out.println();
        }
        graficar();
        mostrarResultados();
    }
    public void graficar(){
        grafica.getData().clear();
         XYChart.Series series1=new XYChart.Series();
         XYChart.Series series2=new XYChart.Series();
         XYChart.Series series3=new XYChart.Series();
         XYChart.Series series4=new XYChart.Series();
         
         series1.setName("Susceptibles");
         series2.setName("Infectados");
         series3.setName("Recuperados");
         series4.setName("Fallecidos");
        
         for (int i = 0; i < datosCalculados.length; i++) {

             series1.getData().add(new XYChart.Data(Integer.toString(i+1),datosCalculados[i][3]));
             series2.getData().add(new XYChart.Data(Integer.toString(i+1),datosCalculados[i][4]));
             series3.getData().add(new XYChart.Data(Integer.toString(i+1),datosCalculados[i][5]));
             series4.getData().add(new XYChart.Data(Integer.toString(i+1),datosCalculados[i][6]));
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
            System.out.println(tasaLetalidad);
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
        
        for ( int i = 0; i<tablaReporteEpidemiologico.getItems().size(); i++) {
            tablaReporteEpidemiologico.getItems().clear(); 
        } 
         clearData();
         XYChart.Series series1=new XYChart.Series();
         XYChart.Series series2=new XYChart.Series();
         XYChart.Series series3=new XYChart.Series();
         XYChart.Series series4=new XYChart.Series();
         
         series1.setName("Nuevos Casos");
         series2.setName("Contagiados");
         series3.setName("Recuperados");
         series4.setName("Fallecidos");
         
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
            conn = LoadDriver.getConnection();
            String sql = "SELECT * FROM reporteepidemiologico r WHERE r.region_id="+region_id+";";
            sent= conn.createStatement();
            ResultSet rs=sent.executeQuery(sql);
            
            while(rs.next()){
                nuevosCasos=Integer.parseInt(rs.getString("nuevos_casos"));
                contagiados=Integer.parseInt(rs.getString("totalContagiados"));
                recuperados=Integer.parseInt(rs.getString("totalRecuperados"));
                fallecidos=Integer.parseInt(rs.getString("totalFallecidos"));
                
                maxNumContagios=0;
                maxNumRecuperados=0;
                maxNumFallecidos=0;
                
                series1.getData().add(new XYChart.Data(rs.getString("fecha"),nuevosCasos));
             series2.getData().add(new XYChart.Data(rs.getString("fecha"),contagiados));
             series3.getData().add(new XYChart.Data(rs.getString("fecha"),recuperados));
             series4.getData().add(new XYChart.Data(rs.getString("fecha"),fallecidos));
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
             //XYChart.data data=new XYChart.Data(Double.toString(i+1),datosCalculados[i][4]);
            // Rectangle rect = new Rectangle(0, 0);
            //rect.setVisible(false);
            //data.setNode(rect);
            // series1.getData().add(xychart,datosCalculados[i][3]));
             
        
         //series1.getData().add(new XYChart.Data("",2));
         grafica.setCreateSymbols(false);
        // grafica.getYAxis().setTickLabelsVisible(false);
         //grafica.getYAxis().setOpacity(0);
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
                tasaDiariaInteraccion=4;
        
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
    void SelectedEstadisticas(MouseEvent event) {
         limpiarDatos();
        graficarDatosNacionalesReales();
        this.opcion="Estadísticas";
        tituloSeleccion.setText(opcion+" "+region);
        this.ParametrosSimulacion.setVisible(false);
        this.VistaMapa.setVisible(false);
        this.grafica.setVisible(true);
        this.Estadisticas.setVisible(true);
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
        
    }
            @FXML
    void SelectedMapaDeRiesgo(MouseEvent event) {
        this.opcion="Mapa de Riesgo";
        tituloSeleccion.setText(opcion+" "+region+" a la fecha: "+fechaEstadisticas);
        this.ParametrosSimulacion.setVisible(false);
        this.VistaMapa.setVisible(true);
        this.grafica.setVisible(false);
        this.Estadisticas.setVisible(false);
        
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
        this.opcion="Simulación";
        this.region="Bolivia";
        PoblacionInicial.setText("11633371");
        graficarDatosNacionalesReales();
        this.ParametrosSimulacion.setVisible(true);
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
        //graficarDatosNacionalesReales();
        CheckBoxSinCuarentena.setSelected(true);
        conn=LoadDriver.getConnection();
        
        columnFecha.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,String>("Fecha"));
        columnNuevosCasos.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("NuevosCasos"));
        columnTotalContagiados.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("TotalContagiados"));
        columnRecuperados.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("Recuperados"));
        columnTotalRecuperados.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("TotalRecuperados"));
        columnFallecidos.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("Fallecidos"));
        columnTotalFallecidos.setCellValueFactory(new PropertyValueFactory<reporteEpidemiologico,Integer>("TotalFallecidos"));
        
        for (int i = 1; i < 10; i++) {
            try {
                conn = LoadDriver.getConnection();
                String sql = "SELECT * FROM reporteepidemiologico r WHERE r.region_id="+i+";";
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
