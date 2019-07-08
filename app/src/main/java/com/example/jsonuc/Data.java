package com.example.jsonuc;

public class Data {

    String _BinaryCode, _CountryName;
    int  _CountryID;

    public Data(String _BinaryCode, String _CountryName, int _CountryID) {
        this._BinaryCode = _BinaryCode;
        this._CountryName = _CountryName;
        this._CountryID = _CountryID;
    }

    public Data(int _CountryID){
        this._CountryID = _CountryID;
    }

    public int get_CountryID() {
        return _CountryID;
    }

    public String get_BinaryCode() {
        return _BinaryCode;
    }

    public String get_CountryName() {
        return _CountryName;
    }
}
