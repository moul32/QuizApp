package com.example.moulaye.quizapp;

public class Question {
    private String question;
    private String capitale;
    private String lat;
    private String lon;
    private String monnaie;
    private String drapeau;
    private String ques;
    private String reponse;
    public Question() {
    }
    public Question(String question, String capitale, String lat, String lon, String monnaie) {
        this.question = question;
        this.capitale = capitale;
        this.lat = lat;
        this.lon = lon;
        this.monnaie = monnaie;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCapitale() {
        return capitale;
    }

    public void setCapitale(String capitale) {
        this.capitale = capitale;
    }


    public String  getLat() { return lat; }

    public void setLat(String lat) { this.lat = lat;}

    public String getLon() {return lon; }

    public void setLon(String lon) { this.lon = lon; }

    public String getMonnaie() {return monnaie; }

    public void setMonnaie(String monnaie) { this.monnaie = monnaie; }

    public String getDrapeau() {return drapeau; }

    public void setDrapeau(String drapeau) { this.drapeau = drapeau; }
    public String getQues() {return ques; }

    public void setQues(String ques) { this.ques = ques; }

    public String getReponse() {return reponse; }

    public void setReponse(String reponse) { this.reponse = reponse; }



}
