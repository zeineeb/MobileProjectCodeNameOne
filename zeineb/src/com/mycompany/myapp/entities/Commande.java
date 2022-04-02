/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author Zeineb Hamdi
 */
public class Commande {   
    private int id_com;
    private String datecommande;
    private float prixtotal;
      private String etatcommande="false";

    public Commande() {
    }

    public Commande(float prixtotal) {
        this.prixtotal = prixtotal;
    }

    public Commande(int id_com, String datecommande, float prixtotal, String etatcommande) {
        this.id_com = id_com;
        this.datecommande = datecommande;
        this.prixtotal = prixtotal;
        this.etatcommande = etatcommande;
    }


    public Commande(String datecommande, float prixtotal, String etatcommande) {
        this.datecommande = datecommande;
        this.prixtotal = prixtotal;
        this.etatcommande = etatcommande;
    }

    public Commande(int id_com, float prixtotal) {
        this.id_com = id_com;
        this.prixtotal = prixtotal;
    }

    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

 

    public String getDatecommande() {
        return datecommande;
    }

    public void setDatecommande(String datecommande) {
        this.datecommande = datecommande;
    }

    public float getPrixtotal() {
        return prixtotal;
    }

    public void setPrixtotal(float prixtotal) {
        this.prixtotal = prixtotal;
    }

    public String getEtatcommande() {
        return etatcommande;
    }

    public void setEtatcommande(String etatcommande) {
        this.etatcommande = etatcommande;
    }

    @Override
    public String toString() {
        return "Commande{" + "id_com=" + id_com + ", datecommande=" + datecommande + ", prixtotal=" + prixtotal + ", etatcommande=" + etatcommande + '}';
    }
    

    
}
