/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

/**
 *
 * @author mahery
 */
public class Tools {

    public Tools() {
    }
    
    public String getUrl(String allURL,String contextPath )
    {
        String[] sub = allURL.split(contextPath);
        String[] sub2 = sub[1].split("/",2);
        String url =sub2[1];
        
        return url;
    }
}
