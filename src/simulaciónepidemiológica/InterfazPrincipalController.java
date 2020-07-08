/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciónepidemiológica;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private double poblacionInicial;
    private double probabilidadContagio=0.10;
    private double duracionEnfermedad=7;
    private double tasaDiariaInteraccion=2.5;
    private double tasaRecuperacion=0.95;
    private double tasaLetalidad=0.05;
    private double contagiadosIniciales=10;
    private double medidasGobierno=0.0;
            
    private double contagios=0;
    private double recuperaciones=0;
    private double fallecimientos=0;


    private double susceptibles;
    private double infectados=10;
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
            34227,35528,36818,38071,39297,40509,41545));
    private ArrayList<Integer> datosNalRealCasosActivos=new ArrayList<Integer>(Arrays.asList(2,3,3,10,10,11,11,12,12,17,19,20,27,28,32,39,61,74,81,93,101,108,
                114,121,127,145,170,177,192,243,245,251,272,297,320,362,398,408,431,457,500,517,525,582,597,700,745,819,861,888,934,973,
                1029,1240,1352,1425,1529,1597,1760,1923,2065,2166,2421,2523,2667,2864,2979,3188,3426,3586,3759,4167,4411,4774,5066,5384,5752,6185,6799,7356,7682,8393,8683,
                9051,9317,9731,10172,10562,11002,11092,11315,11967,12508,13260,13939,14489,14735,15011,15472,16004,16482,17091,17686,18161,18816,19243,19816,20252,20753,21548,21993,22126,22756,
                23262,23899,24732,25421,26196,27104,27617));
    private ArrayList<Integer> datosNalRealRecuperados=new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                1,2,2,2,2,3,3,3,4,4,4,6,6,7,14,26,31,31,31,47,47,50,63,63,75,81,100,110,117,132,
                134,159,166,174,187,198,219,237,258,275,289,313,339,356,434,473,493,503,533,553,561,575,609,629,647,677,689,738,749,889,986,
                137,1298,1507,1658,1739,1902,2086,2159,2190,2261,2372,2431,2768,3113,3430,3752,4002,4320,4670,5086,5454,5857,6300,6795,7338,7736,8158,8517,8928,9340,
                9764,10358,10766,11272,11667,11929,12398));
    private ArrayList<Integer> datosNalRealFallecidos=new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,6,7,
                8,9,10,10,11,14,15,18,19,20,24,27,28,28,29,31,31,32,33,34,37,40,43,44,46,50,53,55,59,62,
                66,71,76,82,86,91,102,106,114,117,121,128,142,152,164,165,169,174,189,199,215,230,240,250,261,274,280,293,300,310,313,
                343,376,400,415,427,454,465,475,487,512,533,559,585,611,632,659,679,697,715,740,773,820,846,876,913,934,970,1014,1071,1123,
                1201,1271,1320,1378,1434,1476,1530));
    
    private double[][]datosCalculados;
    //private ArrayList<ArrayList<Double>> datosCalculados;
        public InterfazPrincipalController() {
    }
            @FXML
    private ComboBox<?> Region;
        @FXML
    private TextField PoblacionInicial;
            @FXML
        private CheckBox CheckBoxCuarentenaR;

    @FXML
    private CheckBox CheckBoxCuarentenaD;

    @FXML
    private CheckBox CheckBoxSinCuarentena;
        @FXML
    private LineChart<?, ?> GraficaNacionalReal;
            @FXML
    private HBox VistaMapaDeRiesgo;
    @FXML
    private HBox VistaNacional;
        @FXML
    private AnchorPane VistaMapa;
    @FXML
    private VBox ResultadosRealesNacional;

    @FXML
    private StackPane GraficasRealesNacional;

    
    
    
   /* @FXML
    private TextField ProbabilidadDeContagio;

    @FXML
    private TextField DuracionEnfermedad;

    @FXML
    private TextField TasaRecuperacion;

    @FXML
    private TextField TasaLetalidad;

    @FXML
    private TextField TasaInteraccion;
*/
    @FXML
    private LineChart<?, ?> grafica;

    @FXML
    private TextField ResMaxNumContagios;

    @FXML
    private TextField ResMaxNumInfectados;

    @FXML
    private TextField ResMaxNumRecuperados;

    @FXML
    private TextField ResMaxNumFallecidos;

        @FXML
    private TextField TotalInfectados;

    @FXML
    private TextField TotalRecuperados;

    @FXML
    private TextField TotalFallecidos;
    @FXML
    void Simular(MouseEvent event) {
        simulacion();

    }
    public void simulacion(){
        poblacionInicial=Double.parseDouble(PoblacionInicial.getText());
        //probabilidadContagio=Double.parseDouble(ProbabilidadDeContagio.getText());
        //duracionEnfermedad=Double.parseDouble(DuracionEnfermedad.getText());
        //tasaDiariaInteraccion=Double.parseDouble(TasaInteraccion.getText());
        //tasaRecuperacion=Double.parseDouble(TasaRecuperacion.getText())/100;
        //tasaLetalidad=Double.parseDouble(TasaLetalidad.getText())/100;
        
        System.out.println(probabilidadContagio);
        System.out.println(duracionEnfermedad);
        System.out.println(tasaDiariaInteraccion);
        System.out.println(tasaRecuperacion);
        System.out.println(tasaLetalidad);
        
        datosCalculados=new double[200][7];
        susceptibles=poblacionInicial-infectados;
        
        for (int i = 0; i < datosCalculados.length; i++) {
            contagios=infectados*tasaDiariaInteraccion*probabilidadContagio*susceptibles/(susceptibles+infectados+recuperados);
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

        //resultadoVan.setText(String.valueOf(df.format(van)));
        
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

             //XYChart.data data=new XYChart.Data(Double.toString(i+1),datosCalculados[i][4]);
            // Rectangle rect = new Rectangle(0, 0);
            //rect.setVisible(false);
            //data.setNode(rect);
            // series1.getData().add(xychart,datosCalculados[i][3]));
             series1.getData().add(new XYChart.Data(Double.toString(i+1),datosCalculados[i][3]));
             series2.getData().add(new XYChart.Data(Double.toString(i+1),datosCalculados[i][4]));
             series3.getData().add(new XYChart.Data(Double.toString(i+1),datosCalculados[i][5]));
             series4.getData().add(new XYChart.Data(Double.toString(i+1),datosCalculados[i][6]));
        }
         //series1.getData().add(new XYChart.Data("",2));
         grafica.setCreateSymbols(false);
        // grafica.getYAxis().setTickLabelsVisible(false);
         //grafica.getYAxis().setOpacity(0);
         grafica.getData().addAll(series1,series2,series3,series4);
         //grafica.getData().addAll(series1,series2,series3,series4);
        limpiarDatos();
    }
    public void mostrarResultados(){
        mayorContagios=0;// 1
        mayorInfectados=0; //5
        mayorRecuperaciones=0;// 2
        mayorFallecimientos=0;// 7
        totalInfectados=0;
        totalRecuperados=0;
        totalFallecidos=0;
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
        }
        DecimalFormat df = new DecimalFormat("#");
        
        ResMaxNumContagios.setText(String.valueOf(df.format(mayorContagios))+" Personas");
        ResMaxNumInfectados.setText(String.valueOf(df.format(mayorInfectados))+" Personas");
        ResMaxNumRecuperados.setText(String.valueOf(df.format(mayorRecuperaciones))+" Personas");
        ResMaxNumFallecidos.setText(String.valueOf(df.format(mayorFallecimientos))+" Personas");
        TotalInfectados.setText(String.valueOf(df.format(totalInfectados))+" Personas");
        TotalRecuperados.setText(String.valueOf(df.format(totalRecuperados))+" Personas");
        TotalFallecidos.setText(String.valueOf(df.format(totalFallecidos))+" Personas");
                
        
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
                 XYChart.Series series1=new XYChart.Series();
         XYChart.Series series2=new XYChart.Series();
         XYChart.Series series3=new XYChart.Series();
         XYChart.Series series4=new XYChart.Series();
         
         series1.setName("Casos totales");
         series2.setName("Casos activos");
         series3.setName("Recuperadoss");
         series4.setName("Fallecidos");
         
        
         for (int i = 0; i < datosNalRealCasosTotales.size(); i++) {

             //XYChart.data data=new XYChart.Data(Double.toString(i+1),datosCalculados[i][4]);
            // Rectangle rect = new Rectangle(0, 0);
            //rect.setVisible(false);
            //data.setNode(rect);
            // series1.getData().add(xychart,datosCalculados[i][3]));
             series1.getData().add(new XYChart.Data(Integer.toString(i+1),datosNalRealCasosTotales.get(i)));
             series2.getData().add(new XYChart.Data(Integer.toString(i+1),datosNalRealCasosActivos.get(i)));
             series3.getData().add(new XYChart.Data(Integer.toString(i+1),datosNalRealRecuperados.get(i)));
             series4.getData().add(new XYChart.Data(Integer.toString(i+1),datosNalRealFallecidos.get(i)));
             
        }
         //series1.getData().add(new XYChart.Data("",2));
         GraficaNacionalReal.setCreateSymbols(false);
        // grafica.getYAxis().setTickLabelsVisible(false);
         //grafica.getYAxis().setOpacity(0);
         GraficaNacionalReal.getData().addAll(series1,series2,series3);
    }
        @FXML
    void SelectedMapaDeRiesgo(MouseEvent event) {
        this.VistaMapaDeRiesgo.setVisible(true);
        this.VistaNacional.setVisible(false);
        this.GraficasRealesNacional.setVisible(true);
        this.ResultadosRealesNacional.setVisible(true);
        this.GraficaNacionalReal.setVisible(true);
        
    }

    @FXML
    void SelectedNacional(MouseEvent event) {
        this.VistaMapaDeRiesgo.setVisible(false);
        this.VistaNacional.setVisible(true);
        this.GraficasRealesNacional.setVisible(false);
        this.ResultadosRealesNacional.setVisible(false);
        this.GraficaNacionalReal.setVisible(false);
        this.VistaMapa.setVisible(false);
    }
        @FXML
    void ButtonGraficaNacionalReal(MouseEvent event) {
        this.GraficaNacionalReal.setVisible(true);
        this.GraficaNacionalReal.toFront();
        this.VistaMapa.setVisible(false);
    }

    @FXML
    void ButtonMapaNacionalReal(MouseEvent event) {
        this.VistaMapa.toFront();
        this.VistaMapa.setVisible(true);
                this.GraficaNacionalReal.setVisible(false);
    }
    @FXML
    void CheckCuarentenaDinamica(MouseEvent event) {
        if (CheckBoxCuarentenaD.isSelected()) {
            CheckBoxCuarentenaD.setSelected(true);
        CheckBoxCuarentenaR.setSelected(false);
        CheckBoxSinCuarentena.setSelected(false);
        tasaDiariaInteraccion=4;
        }
    }
    @FXML
    void CheckCuarentenaRigida(MouseEvent event) {
        if (CheckBoxCuarentenaR.isSelected()) {
            CheckBoxCuarentenaD.setSelected(false);
        CheckBoxCuarentenaR.setSelected(true);
        CheckBoxSinCuarentena.setSelected(false);
        tasaDiariaInteraccion=2.5;
        }
    }
    @FXML
    void CheckSinCuarentena(MouseEvent event) {
        if (CheckBoxSinCuarentena.isSelected()) {
            CheckBoxCuarentenaD.setSelected(false);
        CheckBoxCuarentenaR.setSelected(false);
        CheckBoxSinCuarentena.setSelected(true);
        tasaDiariaInteraccion=7;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graficarDatosNacionalesReales();

    }
    
}
