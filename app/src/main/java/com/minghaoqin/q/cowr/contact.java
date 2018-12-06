package com.minghaoqin.q.cowr;

public class contact {

    // private variables

    String _name;
    byte[] _image;

    // Empty constructor
    public contact() {

    }

    // constructor
    public contact( String name, byte[] image) {

        this._name = name;
        this._image = image;

    }



    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting image
    public byte[] getImage() {
        return this._image;
    }

    // setting image
    public void setImage(byte[] image) {
        this._image = image;
    }
}