package greenvalley;

import java.util.ArrayList; // WAJIB import

// WAJIB 'public' class
public class Toko {
    private ArrayList<ItemHarga> hargaBeliBibit;
    private ArrayList<ItemHarga> hargaJualPanen;

    public Toko() {
        hargaBeliBibit = new ArrayList<>();
        hargaJualPanen = new ArrayList<>();
        
        hargaBeliBibit.add(new ItemHarga("Bibit Padi", 50));
        hargaBeliBibit.add(new ItemHarga("Bibit Jagung", 30));
        hargaJualPanen.add(new ItemHarga("Padi", 150));
        hargaJualPanen.add(new ItemHarga("Jagung", 80));
    }

    // Getters inilah yang memperbaiki error 'undefined method'
    public int getHargaJual(String namaHasil) {
        for (ItemHarga item : hargaJualPanen) {
            if (item.getNama().equals(namaHasil)) return item.getHarga();
        }
        return -1;
    }
    
    public int getHargaBeli(String namaBibit) {
        for (ItemHarga item : hargaBeliBibit) {
            if (item.getNama().equals(namaBibit)) return item.getHarga();
        }
        return -1;
    }

    public Tanaman beliBibit(Pemain pemain, String namaBibit) { 
        int hargaBeli = getHargaBeli(namaBibit);
        if (hargaBeli != -1) {
            if (pemain.beli(namaBibit, hargaBeli, 1)) {
                if (namaBibit.equals("Bibit Padi")) return new Padi();
                if (namaBibit.equals("Bibit Jagung")) return new Jagung();
            }
        }
        return null; 
    }
}