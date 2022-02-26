package topica.edu.vn.model;

public class Music {
    private String ma;
    private String tenBaiHat;
    private String caSi;
    private boolean thich;

    public Music(String ma, String tenBaiHat, String caSi, boolean thich) {
        this.ma = ma;
        this.tenBaiHat = tenBaiHat;
        this.caSi = caSi;
        this.thich = thich;
    }

    public Music() {
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTen(String ten) {
        this.tenBaiHat= ten;
    }

    public String getCaSi() {
        return caSi;
    }

    public void setCaSi(String caSi) {
        this.caSi = caSi;
    }

    public boolean isThich() {
        return thich;
    }

    public void setThich(boolean thich) {
        this.thich = thich;
    }

}
