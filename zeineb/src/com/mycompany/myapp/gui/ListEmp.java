/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Emplacement;
import com.mycompany.myapp.entities.Panier;
import com.mycompany.myapp.services.ServiceEmplacement;
import com.mycompany.myapp.services.ServicePanier;
import java.util.ArrayList;

/**
 *
 * @author Zeineb Hamdi
 */
public class ListEmp extends BaseForm{
    
     Form current;
    public ListEmp(Resources res) {
        
                              //search
             Toolbar.setGlobalToolbar(true);
             add(new InfiniteProgress());
             
             
                Display.getInstance().scheduleBackgroundTask(()-> {
                    // this will take a while...
                    Display.getInstance().callSerially(() -> {
                    removeAll();
                    ArrayList <Emplacement> paniers = new ArrayList();
                        ServiceEmplacement sa =new ServiceEmplacement();
                    paniers=sa.getAllPanier();
     
                             for (Emplacement fi : paniers) {
                            MultiButton m = new MultiButton();
                            m.setTextLine1("Type : "+String.valueOf(fi.getType_emplacement()));
                            m.setTextLine2("Description :"+String.valueOf(fi.getDescription()));
                          
                            
//                            m.addLongPressListener(new ActionListener() {
//                                            @Override
//            public void actionPerformed(ActionEvent evt) {               
//                if (Dialog.show("Confirmation", "Voulez vous Supprimer cette utilisateur ?", "Supprimer", "Annuler")) {
//                        if( ServicePanier.getInstance().deletePanier(fi)){
//                            {
//                                   Dialog.show("Success","supprimer",new Command("OK"));
//                                   new NewsfeedForm(res).show();
//                            }
//                   
//                }
//            }
//                else
//                {
//                      if (Dialog.show("Confirmation", "Voulez vous Modifier cette article?", "Oui", "Non")) {
//                                   new ModifierPanier(res,current,fi).show();
//                                       }
//
//                }
//            }
//        });

                            add(m);
                             }
                     revalidate();
                       
                    });
                    
                });
                
    getToolbar().addSearchCommand( e -> {
    String text = (String)e.getSource();
    if(text == null || text.length() == 0) {
        // clear search
        for(Component cmp : getContentPane()) {
            cmp.setHidden(false);
            cmp.setVisible(true);
        }
        getContentPane().animateLayout(150);
    } else {
        text = text.toLowerCase();
        for(Component cmp : getContentPane()) {
            MultiButton mb = (MultiButton)cmp;
            String line1 = mb.getTextLine1();
            String line2 = mb.getTextLine2();
            boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
            line2 != null && line2.toLowerCase().indexOf(text) > -1;
            mb.setHidden(!show);
            mb.setVisible(show);
        }
        getContentPane().animateLayout(150);
    }
}, 4);

    
       
        getToolbar().addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Produit Back", FontImage.MATERIAL_SETTINGS, e -> new ProduitViewBack(res).show());
        getToolbar().addMaterialCommandToSideMenu("Produit Front", FontImage.MATERIAL_SETTINGS, e -> new ProduitViewFront(res).show());
        getToolbar().addMaterialCommandToSideMenu("Commande Back", FontImage.MATERIAL_SETTINGS, e -> new ListCommande(res).show());
        getToolbar().addMaterialCommandToSideMenu("Statistique", FontImage.MATERIAL_SETTINGS, e -> new StatPanier(res).show());
        getToolbar().addMaterialCommandToSideMenu("Panier", FontImage.MATERIAL_SHOPPING_CART, e -> new MonPanier(res).show());
        getToolbar().addMaterialCommandToSideMenu("Evenement", FontImage.MATERIAL_UPDATE, e -> new NewsfeedForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Reservation", FontImage.MATERIAL_UPDATE, e -> new NewsfeedForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new WalkthruForm(res).show());
    }
    
}
