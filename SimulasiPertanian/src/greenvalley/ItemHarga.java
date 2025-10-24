package greenvalley;

// WAJIB 'public' class
public class ItemHarga {
    private String nama;
    private int harga;

    public ItemHarga(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }
    public String getNama() { return nama; }
    public int getHarga() { return harga; }
}