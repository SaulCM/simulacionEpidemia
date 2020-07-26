/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Master
 */
public class reporteEpidemiologico {
    private String fecha;
    private int nuevosCasos;
    private int totalContagiados;
    private int recuperados;
    private int totalRecuperados;
    private int fallecidos;
    private int totalFallecidos;
    
    public reporteEpidemiologico(String fecha, int nuevosCasos,int totalContagiados, int recuperados, int totalRecuperados, int fallecidos, int totalFallecidos){
    this.fecha=fecha;
    this.nuevosCasos=nuevosCasos;
    this.totalContagiados=totalContagiados;
    this.recuperados=recuperados;
    this.totalRecuperados=totalRecuperados;
    this.fallecidos=fallecidos;
    this.totalFallecidos=totalFallecidos;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the nuevosCasos
     */
    public int getNuevosCasos() {
        return nuevosCasos;
    }

    /**
     * @param nuevosCasos the nuevosCasos to set
     */
    public void setNuevosCasos(int nuevosCasos) {
        this.nuevosCasos = nuevosCasos;
    }

    /**
     * @return the recuperados
     */
    public int getRecuperados() {
        return recuperados;
    }

    /**
     * @param recuperados the recuperados to set
     */
    public void setRecuperados(int recuperados) {
        this.recuperados = recuperados;
    }

    /**
     * @return the fallecidos
     */
    public int getFallecidos() {
        return fallecidos;
    }

    /**
     * @param fallecidos the fallecidos to set
     */
    public void setFallecidos(int fallecidos) {
        this.fallecidos = fallecidos;
    }

    /**
     * @return the totalContagiados
     */
    public int getTotalContagiados() {
        return totalContagiados;
    }

    /**
     * @param totalContagiados the totalContagiados to set
     */
    public void setTotalContagiados(int totalContagiados) {
        this.totalContagiados = totalContagiados;
    }

    /**
     * @return the totalRecuperados
     */
    public int getTotalRecuperados() {
        return totalRecuperados;
    }

    /**
     * @return the totalFallecidos
     */
    public int getTotalFallecidos() {
        return totalFallecidos;
    }
    
}
