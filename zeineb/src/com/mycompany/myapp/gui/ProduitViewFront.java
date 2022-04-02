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

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.push.PushAction;
import com.codename1.push.PushActionCategory;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Commande;
import com.mycompany.myapp.entities.Panier;
import com.mycompany.myapp.services.ServiceCommande;
import com.mycompany.myapp.services.ServicePanier;
import com.mycompany.myapp.services.ServiceProduit;

//import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;

/**

 *
 
 */
public class ProduitViewFront extends BaseForm {
     List<Container> cntl = new ArrayList<Container>();

    public ProduitViewFront(com.codename1.ui.util.Resources resourceObjectInstance) {
   

        setTitle("Produit");
        setLayout(BoxLayout.y());

        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
 

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        addSideMenu(resourceObjectInstance);
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
         getToolbar().addSearchCommand(e -> search((String)e.getSource()));
        //rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
         

        
   
         ArrayList<com.mycompany.myapp.entities.Produit> produits = ServiceProduit.getInstance().getAllProduits();
        for(int i=0; i<produits.size ();i++){
        EncodedImage enc= EncodedImage.createFromImage(com.mycompany.myapp.MyApplication.theme.getImage("load.jpg"), false);
        Image img = URLImage.createToStorage(enc, "Produit_"+produits.get(i).getNom(), com.mycompany.myapp.utils.Statics.BASE_URL+"/uploads/images/"+produits.get(i).getPhotoPro(), URLImage.RESIZE_SCALE);
            cntl.add(addButton(img, produits.get(i).getNom(), false,  ""+produits.get(i).getQuantiteProduit()+"" , ""+produits.get(i).getDescriptionProduit()+"" , produits.get(i).getIdProduit()));
             ;

            }  
        //ajouter
    }
      void search(String text) {
        if(text == null || text.length() == 0) {
                 for(int i=0; i<cntl.size ();i++){
                        cntl.get(i).setHidden(false);
                     
                }  
        } else {
        ArrayList<com.mycompany.myapp.entities.Produit> produits = ServiceProduit.getInstance().getAllProduits();
             for(int i=0; i<cntl.size ();i++){
                 
                   boolean show =produits.get(i).getNom().toLowerCase().indexOf(text) > -1;
                    if(show){
                         cntl.get(i).setHidden(false);
                        
                    }
                    else{
                          cntl.get(i).setHidden(true);
                    
                    }
                }
        }
        getContentPane().animateLayout(200);
    }
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();   
    }
    private void addTab(Tabs swipe, Image img, Label spacer) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");   
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            spacer
                        )
                )
            );
        swipe.addTab("", page1);
    }
   private Container addButton(Image img, String title, boolean liked, String likeCount, String commentCount, int id) {
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
       Container cnt = BorderLayout.west(image);
       cnt.setLeadComponent(image);
       TextArea ta = new TextArea(title);
       ta.setUIID("NewsTopLine");
       ta.setEditable(false);    
        /*test
        ---------------------------------------------
        */
       
       Label likes = new Label(likeCount + "  ", "NewsBottomLine");
       likes.setTextPosition(RIGHT);
       if(!liked) {
           FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
       } else {
           Style s = new Style(likes.getUnselectedStyle());
           s.setFgColor(0xff2d55);
           FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
           likes.setIcon(heartImage);
       }
       Label comments = new Label(commentCount + " ", "NewsBottomLine");
       FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);
       
       
       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       ta,
                       BoxLayout.encloseX(likes, comments)
               ));
                  Button valider = new Button("Order Now");
            valider.getAllStyles().setBgColor(0x339bff);
      
            add(valider);
            
            
            valider.addActionListener(event -> {

             Panier p = new Panier();
           ServicePanier sp = new ServicePanier ();
          //  p.setId_prod(Integer.valueOf(.getText()));
            //sp.addPanier(p);
            Dialog.show("Success","Your order has been placed",new Command("OK"));
      //       com.mycompany.myapp.MyApplication.idA=id;
                ServicePanier.getInstance().addPanier(p,id);
            });
       add(cnt);
       Button edit = new Button("Ajouter au panier");
        edit.setUIID("NewsTopLine");
        Style editStyle = new Style(edit.getUnselectedStyle());
        editStyle.setFgColor(0xf21f1f);
        FontImage editImage = FontImage.createMaterial(FontImage.MATERIAL_EDIT, editStyle);
        edit.setIcon(editImage);
       edit.setTextPosition(RIGHT);
       edit.addActionListener(e -> {
                           com.mycompany.myapp.MyApplication.idA=id;
                           new EditProduit().show();
               });
       
       
  
       image.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
       return cnt;
   }
    
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
     public PushActionCategory[] getPushActionCategories() {
    return new PushActionCategory[]{
        new PushActionCategory("fo", new PushAction[]{
            new PushAction("yes", "Yes"),
            new PushAction("no", "No"),
            new PushAction("maybe", "Maybe", null, "Enter reason", "Reply")
        })

    };
}
     
}
