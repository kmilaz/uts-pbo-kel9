package greenvalley;

// WAJIB 'public abstract' class
public abstract class Tanaman {
    private String nama;
    private int waktuPanen;
    private int kebutuhanAir;
    private int statusTumbuh;
    private boolean isSiapPanen;
    private boolean isSakit;

    public Tanaman(String nama, int waktuPanen, int kebutuhanAir) {
        this.nama = nama;
        this.waktuPanen = waktuPanen;
        this.kebutuhanAir = kebutuhanAir;
        this.statusTumbuh = 0;
        this.isSiapPanen = false;
        this.isSakit = false;
    }

    // Perlu 'Cuaca' dari package yg sama
    public abstract void tumbuh(Cuaca cuaca);
    public abstract void panen();

    public void siram() {
        this.kebutuhanAir = Math.max(0, this.kebutuhanAir - 10);
    }

    public void beriPupuk() {
        this.statusTumbuh = Math.min(100, this.statusTumbuh + 10);
        cekSiapPanen();
    }

    protected void cekSiapPanen() {
        if (this.statusTumbuh >= 100) {
            this.isSiapPanen = true;
        }
    }

    // Getters/Setters ini yang PENTING untuk file lain
    public String getNama() { return nama; }
    public boolean isSiapPanen() { return isSiapPanen; }
    public boolean isSakit() { return isSakit; }
    public int getKebutuhanAir() { return kebutuhanAir; }
    public int getStatusTumbuh() { return statusTumbuh; }
    protected void setSakit(boolean sakit) { this.isSakit = sakit; }
    protected void setStatusTumbuh(int status) { this.statusTumbuh = status; }
    protected int getWaktuPanen() { return waktuPanen; }
    protected void setKebutuhanAir(int air) { this.kebutuhanAir = air; }
}