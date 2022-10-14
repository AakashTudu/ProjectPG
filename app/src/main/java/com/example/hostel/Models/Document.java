package com.example.hostel.Models;

public class Document {
    int cardIcon;
    String cardName;
    String cardDetail;

    public Document(int cardIcon, String cardName, String cardDetail) {
        this.cardIcon = cardIcon;
        this.cardName = cardName;
        this.cardDetail = cardDetail;
    }

    public int getCardIcon() {
        return cardIcon;
    }

    public void setCardIcon(int cardIcon) {
        this.cardIcon = cardIcon;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDetail() {
        return cardDetail;
    }

    public void setCardDetail(String cardDetail) {
        this.cardDetail = cardDetail;
    }
}
