/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author fLawniita
 */
public class Produit {
    
    private int idProduit;
    private String nom;
    private float quantiteProduit;
    private String photoPro;
    private String descriptionProduit;
    private float prixProduit;

    public Produit(String nom, String descriptionProduit, float quantiteProduit, float prixProduit) {
        this.nom = nom;
        this.descriptionProduit = descriptionProduit;
        this.quantiteProduit = quantiteProduit;
        this.prixProduit = prixProduit;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getQuantiteProduit() {
        return quantiteProduit;
    }

    public void setQuantiteProduit(float quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public String getPhotoPro() {
        return photoPro;
    }

    public void setPhotoPro(String photoPro) {
        this.photoPro = photoPro;
    }

    public String getDescriptionProduit() {
        return descriptionProduit;
    }

    public void setDescriptionProduit(String descriptionProduit) {
        this.descriptionProduit = descriptionProduit;
    }

    public float getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(float prixProduit) {
        this.prixProduit = prixProduit;
    }

    public Produit() {
    }

    @Override
    public String toString() {
        return "Produit{" + "idProduit=" + idProduit + ", nom=" + nom + ", quantiteProduit=" + quantiteProduit + ", photoPro=" + photoPro + ", descriptionProduit=" + descriptionProduit + ", prixProduit=" + prixProduit + '}';
    }
    
    
    
    
    
    
}
