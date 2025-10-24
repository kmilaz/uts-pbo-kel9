package greenvalley;

// WAJIB 'public' class
public class Cuaca {
    private String kondisi;
    private int suhu; 
    private boolean isHujan;

    public Cuaca() {
        this.kondisi = "Cerah";
        this.suhu = 28;
        this.isHujan = false;
    }

    public void prediksiCuaca() {
        suhu = 25 + (int)(Math.random() * 10); 
        int curahHujan = (int)(Math.random() * 100); 
        
        if (curahHujan > 50) { 
            isHujan = true;
            kondisi = "Hujan (Suhu: " + suhu + "C)";
        } else {
            isHujan = false;
            kondisi = "Cerah (Suhu: " + suhu + "C)";
        }
    }
    public boolean isHujan() { return isHujan; }
    public String getKondisi() { return kondisi; }
}