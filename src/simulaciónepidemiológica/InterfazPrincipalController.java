/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciónepidemiológica;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private double probabilidadContagio;
    private double duracionEnfermedad;
    private double tasaDiariaInteraccion;
    private double tasaRecuperacion;
    private double tasaLetalidad;
    private double contagiadosIniciales=10;
            
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
    
    private double[][]datosCalculados;
    //private ArrayList<ArrayList<Double>> datosCalculados;
        public InterfazPrincipalController() {
    }
        @FXML
    private TextField PoblacionInicial;

    @FXML
    private TextField ProbabilidadDeContagio;

    @FXML
    private TextField DuracionEnfermedad;

    @FXML
    private TextField TasaRecuperacion;

    @FXML
    private TextField TasaLetalidad;

    @FXML
    private TextField TasaInteraccion;
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
        probabilidadContagio=Double.parseDouble(ProbabilidadDeContagio.getText());
        duracionEnfermedad=Double.parseDouble(DuracionEnfermedad.getText());
        tasaDiariaInteraccion=Double.parseDouble(TasaInteraccion.getText());
        tasaRecuperacion=Double.parseDouble(TasaRecuperacion.getText())/100;
        tasaLetalidad=Double.parseDouble(TasaLetalidad.getText())/100;
        
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
