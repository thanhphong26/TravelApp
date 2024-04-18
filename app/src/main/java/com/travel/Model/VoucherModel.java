package com.travel.Model;

public class VoucherModel {
    private int voucherId;
    private String voucherCode;
    private String voucherDescription;
    private float voucherDiscount;
    public VoucherModel() {
    }
    public VoucherModel(int voucherId, String voucherCode, String voucherDescription, float voucherDiscount) {
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.voucherDescription = voucherDescription;
        this.voucherDiscount = voucherDiscount;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getVoucherDescription() {
        return voucherDescription;
    }

    public void setVoucherDescription(String voucherDescription) {
        this.voucherDescription = voucherDescription;
    }

    public float getVoucherDiscount() {
        return voucherDiscount;
    }

    public void setVoucherDiscount(float voucherDiscount) {
        this.voucherDiscount = voucherDiscount;
    }
}
