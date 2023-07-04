/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import utilitaire.ModelView;
import annotation.Annotation;
import utilitaire.Url;
/**
 *
 * @author mahery
 */
public class Emp {
    int id;
    String nom;
    String prenom;
    Date date_de_naissance;
    public Emp(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    public Emp() {
    }
    public Emp(int id, String nom, String prenom, Date date_de_naissance) {
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setDate_de_naissance(date_de_naissance);
    }
    @Url(method = "emp-jsp")
    public ModelView methodeAAnnoter(){
        ModelView modelView = new ModelView("employe.jsp");
        return modelView;
    }
    @Url(method = "get-emp")
    public ModelView getAllEmploye(){
        ModelView modelView = new ModelView("listeEmp.jsp");
        List<Emp> employes = new ArrayList<>();
        Emp employe1 = new Emp(1,"Jean", "Rabe", new Date(1997, 7, 1));
        Emp employe2 = new Emp(2,"Jack", "Andria", new Date(1994, 3, 26));
        Emp employe3 = new Emp(3,"Bert", "Part", new Date(1995, 12, 30));  
        employes.add(employe1);
        employes.add(employe2);
        employes.add(employe3);
        for (int i = 0; i < employes.size(); i++) {
            System.out.println(employes.get(i).getNom());
        }
        modelView.addItem("allEmploye", employes); 
        return modelView;
    }
    @Url(method = "add-emp")
    public ModelView addEmploye(){
        ModelView modelView = new ModelView("addEmp.jsp");
        return modelView;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Date getDate_de_naissance() {
        return date_de_naissance;
    }
    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }
}
