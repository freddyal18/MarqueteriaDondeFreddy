package com.example.marqueteriadondefreddy;

import com.google.firebase.database.Exclude;

public class uploadInfo2 {
    public String imageName;
    public String imageURL;
    public String descripcion;
    public uploadInfo2(){}

    public uploadInfo2(String name, String descrip, String url) {
        if(name.trim().equals("")){
            name = "Sin nombre";
        }
        imageName = name;
        descripcion = descrip;
        imageURL = url;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
