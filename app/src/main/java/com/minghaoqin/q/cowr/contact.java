package com.minghaoqin.q.cowr;

public class contact {

    // private variables

    String _name;
    byte[] _image;
    int _id;

    // Empty constructor
    public contact() {

    }

    // constructor
    public contact(int _id, String name, byte[] image) {

        this._name = name;
        this._image = image;
        this._id= _id;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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