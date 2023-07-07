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
import etu1758.framework.*;
/**
 *
 * @author mahery
 */
@PathUpload(filePath = "/home/mahery/fianarana/tomcat/webapps/testFrame/image")
public class Emp {
     Integer id;
    String nom;
    String prenom;
    Date date_de_naissance;
    FileUpload fileEmp;
    public Emp(Integer id, String nom, String prenom, Date date_de_naissance, FileUpload fileEmp) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date_de_naissance = date_de_naissance;
        this.fileEmp = fileEmp;
    }
    public Emp(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    public Emp() {
    }
    public Emp(Integer id, String nom, String prenom, Date date_de_naissance) {
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);;
        this.setDate_de_naissance(date_de_naissance);;
    }
    @Url(method = "emp-jsp.gg")
    public ModelView methodeAAnnoter(){
        ModelView modelView = new ModelView("emp.jsp");
        return modelView;
    }

    public List<Emp> listeEmployers(){
        List<Emp> emps = new ArrayList<>();
        Emp employe1 = new Emp(1,"Jean", "Rabe", new Date(1997, 7, 1));
        Emp employe2 = new Emp(2,"Jack", "Andria", new Date(1994, 3, 26));
        Emp employe3 = new Emp(3,"Bert", "Part", new Date(1995, 12, 30));  
        emps.add(employe1);
        emps.add(employe2);
        emps.add(employe3);
        return emps;
    }

    @Url(method = "get-emp.gg")
    public ModelView getAllEmp(){
        ModelView modelView = new ModelView("listeEmp.jsp");
        List<Emp> emps = listeEmployers();
        modelView.addItem("allEmp", emps); 
        return modelView;
    }
    @Url(method = "add-emp.gg")
    public ModelView addEmp(){
        ModelView modelView = new ModelView("sprint71.jsp");
        return modelView;
    }


    @Url(method = "add-emp-with-arguments.gg")
    @Arguments(arguments = {"id", "nom", "prenom", "date_de_naissance" , "fileEmp"})
    public ModelView addEmpWithArguments(Integer id, String nom, String prenom, Date date_de_naissance , FileUpload fileEmp){
        ModelView modelView = new ModelView("sprint8.jsp");
        Emp emp = new Emp(id, nom, prenom, date_de_naissance , fileEmp);
        modelView.addItem("empr", emp); 
        return modelView;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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
    public FileUpload getFileEmp() {
        return fileEmp;
    }
    public void setFileEmp(FileUpload fileEmp) {
        this.fileEmp = fileEmp;
    }
}
