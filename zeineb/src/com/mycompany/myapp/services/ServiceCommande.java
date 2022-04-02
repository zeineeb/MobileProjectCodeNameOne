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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Commande;

import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SIHEM
 */
public class ServiceCommande {

    public ArrayList<Commande> paniers;
    
    public static ServiceCommande instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceCommande() {
         req = new ConnectionRequest();
    }

    public static ServiceCommande getInstance() {
        if (instance == null) {
            instance = new ServiceCommande();
        }
        return instance;
    }
    


    public ArrayList<Commande> parsePanies(String jsonText){
                try {

                    System.out.println(jsonText);
            paniers=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Commande a = new Commande();
                float id = Float.parseFloat(obj.get("id_com").toString());
                a.setId_com((int)id);
                a.setPrixtotal((float)(double) obj.get("prixtotal"));
                a.setEtatcommande(obj.get("etatcommande").toString());
                a.setDatecommande(obj.get("datecommande").toString());

                paniers.add(a);


            }
        } catch (IOException ex) {
            
        }
        return paniers;
    }
    public ArrayList<Commande> getAllCommande(){
        String url = Statics.BASE_URL+"affmobCommande";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                paniers = parsePanies(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return paniers;
    }

        
    public boolean addCommande(Commande u) {
        String url = Statics.BASE_URL +  "commande/new?total=" + u.getPrixtotal(); //création de l'URL
               req.setUrl(url);
                       req.setPost(false);

               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteCommande(Commande fi) {
        String url = Statics.BASE_URL + "Commande/del?id=" + fi.getId_com();
               req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

}
