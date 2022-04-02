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
import com.mycompany.myapp.entities.Emplacement;
import com.mycompany.myapp.entities.Panier;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Zeineb Hamdi
 */
public class ServiceEmplacement {
        public ArrayList<Emplacement> paniers;
    
    public static ServiceEmplacement instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceEmplacement() {
         req = new ConnectionRequest();
    }

    public static ServiceEmplacement getInstance() {
        if (instance == null) {
            instance = new ServiceEmplacement();
        }
        return instance;
    }
    


    public ArrayList<Emplacement> parsePanies(String jsonText){
                try {

                    System.out.println(jsonText);
            paniers=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Emplacement a = new Emplacement();
                float id = Float.parseFloat(obj.get("id_emplacement").toString());
                a.setId_emplacement((int)id);
                a.setType_emplacement(obj.get("type_emplacement").toString());
         a.setDescription(obj.get("Description").toString());
         paniers.add(a);


            }
        } catch (IOException ex) {
            
        }
        return paniers;
    }
    public ArrayList<Emplacement> getAllPanier(){
        String url = Statics.BASE_URL+"affEmp";
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


//    public boolean addPanier(Panier u) {
//        String url = Statics.BASE_URL + "paniermobile/new?id_produit="+u.getId_prod(); //création de l'URL
//               req.setUrl(url);
//               System.out.println(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;
//    }
//
//        public boolean editPanier(Panier u) {
//        String url = Statics.BASE_URL + "paniermob/edit?id_produit="+u.getId_prod()+"&quantity="+u.getQuantite()+"&total="+u.getTotal(); 
//               req.setUrl(url);
//               System.out.println(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;
//    }
//
//    public boolean deletePanier(Panier fi) {
//        String url = Statics.BASE_URL + "Panier/del?id_produit=" + fi.getId_prod();
//               req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;
//    }

    
}
