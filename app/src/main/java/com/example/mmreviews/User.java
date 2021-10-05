package com.example.mmreviews;

// data model klase je najbolje smestiti u poseban paket
public class User {

    private String username;
    private String fullName;
    private String email;


    public User(){

    }

    // ne znam kakav je tacno use case za ovu klasu u smislu manipulacije
    // podacima, ali uvek kada dizajniras model obratis paznju na to sta dozvoljavas
    // moj neki osecaj je da ako vec objekat zahteva da u konstruktoru prosledis
    // podatke, onda ti seteri mozda i nisu potrebni jer za ovaj konkretan slucaj
    // podaci se verovatno nece menjati (mozda i hoce, ali svakako uzmi hint u obzir :))
    public User(String username, String fullName, String email) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
