/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fLawniita
 */
public class ServiceProduit {
        public ArrayList<Produit> Produit;
    public static ServiceProduit instance = null;
    public static boolean resultOK =true ;
    private ConnectionRequest req;
    
    
    public static ServiceProduit getInstance(){
        if(instance==null)
            instance = new ServiceProduit();
         return instance;   
    }
    public ServiceProduit(){
        req = new ConnectionRequest();
    }
    public ArrayList<Produit> parseElement(String jsonText){
        try {
            Produit=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Produit u = new Produit();
                u.setIdProduit(((int)Float.parseFloat(obj.get("id_produit").toString())));
                u.setDescriptionProduit(obj.get("description_produit").toString());
                u.setNom(obj.get("nom").toString());
                u.setPhotoPro(obj.get("photo_pro").toString());
                u.setQuantiteProduit((float) (double) obj.get("quantite_produit"));
                u.setPrixProduit((float) (double) obj.get("prix_produit"));
                Produit.add(u);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return Produit;
    }       
        
    public ArrayList<Produit> getAllProduits(){
        ArrayList<Produit> result =new ArrayList<>();
        
            String url = Statics.BASE_URL+"produit/getall";
        req.setUrl(url);
        
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Produit = parseElement(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Produit;
    }
    
    public boolean DeleteProduit(int id )
    {
        String url = Statics.BASE_URL+"produit/deleteapi/"+id;
         req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);
            }  
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    public boolean EditProduit(Produit r)
    {
        String url = Statics.BASE_URL+"produit/editapi/" + com.mycompany.myapp.MyApplication.idA +"?nom=" + r.getNom()+ "&descriptionProduit=" + r.getDescriptionProduit()+ "&quantiteProduit=" + r.getQuantiteProduit()+ 
                "&prixProduit=" + r.getPrixProduit();
        
         req.setUrl(url);
        
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);
            }  
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    public boolean addProduit(Produit r) {
        String url = Statics.BASE_URL + "produit/newapi?nom=" + r.getNom()+ "&descriptionProduit=" + r.getDescriptionProduit()+ "&quantiteProduit=" + r.getQuantiteProduit()+ 
                "&prixProduit=" + r.getPrixProduit();
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
}
