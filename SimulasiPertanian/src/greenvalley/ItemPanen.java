package greenvalley;

// WAJIB 'public' class
public class ItemPanen {
    private String nama;
    private int jumlah;

    public ItemPanen(String nama, int jumlah) {
        this.nama = nama;
        this.jumlah = jumlah;
    }
    public String getNama() { return nama; }
    public int getJumlah() { return jumlah; }
    public void tambahJumlah(int jumlah) { this.jumlah += jumlah; }
    public void kurangiJumlah(int jumlah) { this.jumlah -= jumlah; }
}