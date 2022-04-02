/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.myapp.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Commande;
import com.mycompany.myapp.entities.Panier;
import com.mycompany.myapp.services.ServiceCommande;
import com.mycompany.myapp.services.ServicePanier;
import java.util.ArrayList;

/**
 * 
 *
 * @author Zeineb Hamdi
 */
public class MonPanier extends BaseForm {
        Form current;
    public MonPanier(Resources res) {
         super(" Panier", BoxLayout.y());
              
                Label totalelabel = new Label();
        
                              //search
             Toolbar.setGlobalToolbar(true);
             add(new InfiniteProgress());
             
             
                Display.getInstance().scheduleBackgroundTask(()-> {
                    // this will take a while...
                    Display.getInstance().callSerially(() -> {
                    removeAll();
                    ArrayList <Panier> paniers = new ArrayList();
                        ServicePanier sa =new ServicePanier();
                    paniers=sa.getAllPanier();
                        float total=0;
            for (Panier fi : paniers) {
            total=total+fi.getTotal();
            }

                       if(paniers.isEmpty()){
           Label empty = new Label("Your Cart is Empty");
           add(empty);
       }
       else{
                             for (Panier fi : paniers) {
                         
                            MultiButton m = new MultiButton();
                            m.setTextLine1("Name : "+String.valueOf(fi.getTitle()));
                            m.setTextLine2("Prix :"+String.valueOf(fi.getPrix()));
                            m.setTextLine3("Quantity :"+String.valueOf(fi.getQuantite()));
                            m.setTextLine4("Total :"+String.valueOf(fi.getTotal()));
                            
                         
                            m.addLongPressListener(new ActionListener() {
                                            @Override
            public void actionPerformed(ActionEvent evt) {               
                if (Dialog.show("Confirmation", "Voulez vous Supprimer cette utilisateur ?", "Supprimer", "Annuler")) {
                        if( ServicePanier.getInstance().deletePanier(fi)){
                            {
                                   Dialog.show("Success","supprimer",new Command("OK"));
                                   new NewsfeedForm(res).show();
                            }
                   
                }
            }
                else
                {
                      if (Dialog.show("Confirmation", "Voulez vous Modifier cette article?", "Oui", "Non")) {
                                   new ModifierPanier(res,current,fi).show();
                                       }

                }
            }
        }); 
                            
                            

          
                            
                            
 
                    
                            
                            add(m);

                            
                            

                            
                             }
                             
                             
                                         Container cnt2 = new Container(new LayeredLayout());
            LayeredLayout ll2 = (LayeredLayout)cnt2.getLayout();
            
            totalelabel.setText(Float.toString(total));
            cnt2.add(totalelabel);
            Label totaltext = new Label("Total : ");
            
            
            ll2.setInsets(totalelabel,"auto 0 0 auto");
            ll2.setInsets(totaltext, "auto 0 0 43mm");
            cnt2.add(totaltext);
            
                             Button commander = new Button("Order Now");
                       
            
            commander.setPreferredSize(new Dimension(450,100));
                    
                            
                            add(cnt2);
                            add(commander);
                            
              commander.addActionListener(e -> {
                
                  
        
            Dialog d = new Dialog("Please enter your shipping address");
            
            
            TextField address = new TextField("","your address",15,TextArea.ANY);
            TextField phone = new TextField("","your phone number",15,TextArea.PHONENUMBER);
            TextField postalCode = new TextField("","your Postal Code",15,TextArea.NUMERIC);
            
            address.getAllStyles().setFgColor(0x000000);
            phone.getAllStyles().setFgColor(0x000000);
            postalCode.getAllStyles().setFgColor(0x000000);
            
            Button valider = new Button("Order Now");
            valider.getAllStyles().setBgColor(0x339bff);
            valider.setPreferredSize(new Dimension(450,100));
            d.setLayout(BoxLayout.y());
            d.add(address);
            d.add(phone);
            d.add(postalCode);
            d.add(valider);
            
            
            valider.addActionListener(event -> {
       // String addressComplet = address.getText()+" "+postalCode.getText();
             Commande c = new Commande();
           ServiceCommande sp = new ServiceCommande();
            c.setPrixtotal(Float.valueOf(totalelabel.getText()));
            sp.addCommande(c);
            Dialog.show("Success","Your order has been placed",new Command("OK"));
            new NewsfeedForm(res).show();
            
            

           

            
            
            
    });
            
            d.showPopupDialog(commander);
                
            });
                     revalidate();

                       }
                       
                                   
    
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

    MonPanier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
                              
}
