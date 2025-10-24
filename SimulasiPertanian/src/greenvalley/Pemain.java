package greenvalley;

import java.util.ArrayList;

public class Pemain {
    private int uang; 
    private int energi; 
    private int energiMaks; // Tambahkan ini
    private ArrayList<ItemPanen> inventori; 

    public Pemain(int uang, int energi) {
        this.uang = uang;
        this.energi = energi;
        this.energiMaks = 100; // Atur energi maks
        this.inventori = new ArrayList<>(); 
    }

    /**
     * BARU: Method untuk memakai energi
     * Mengembalikan true jika berhasil, false jika energi tidak cukup
     */
    public boolean pakaiEnergi(int jumlah) {
        if (this.energi >= jumlah) {
            this.energi -= jumlah;
            return true;
        } else {
            return false; // Energi tidak cukup
        }
    }

    /**
     * BARU: Method untuk memulihkan energi saat tidur
     */
    public void pulihkanEnergi(int jumlah) {
        this.energi += jumlah;
        if (this.energi > this.energiMaks) {
            this.energi = this.energiMaks;
        }
    }

    public void tambahHasilPanen(String namaTanaman, int jumlah) {
        for (ItemPanen item : inventori) {
            if (item.getNama().equals(namaTanaman)) {
                item.tambahJumlah(jumlah); 
                return; 
            }
        }
        inventori.add(new ItemPanen(namaTanaman, jumlah));
    }
    
    public boolean beli(String item, int harga, int jumlah) {
        if (uang >= harga * jumlah) {
            uang -= (harga * jumlah);
            return true;
        }
        return false;
    }
    
    public boolean jual(String namaItem, int harga, int jumlahJual) {
        ItemPanen itemDitemukan = null;
        for (ItemPanen item : inventori) {
            if (item.getNama().equals(namaItem)) {
                itemDitemukan = item;
                break;
            }
        }
        if (itemDitemukan != null) {
            if (itemDitemukan.getJumlah() >= jumlahJual) {
                itemDitemukan.kurangiJumlah(jumlahJual);
                uang += (harga * jumlahJual);
                if (itemDitemukan.getJumlah() == 0) {
                    inventori.remove(itemDitemukan);
                }
                return true;
            }
        }
        return false; 
    }

    public int getUang() { return uang; }
    public int getEnergi() { return energi; }
    public ArrayList<ItemPanen> getInventori() { return inventori; }
}