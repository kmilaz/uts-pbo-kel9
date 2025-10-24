package greenvalley;

import java.util.ArrayList; // WAJIB import

// WAJIB 'public' class
public class Lahan implements Perawatan {
    private int ukuran;
    private String jenisTanah;
    private ArrayList<Tanaman> daftarTanaman;

    // Biaya energi untuk panen per tanaman
    private static final int ENERGI_PANEN = 10;

    public Lahan(int ukuran, String jenisTanah) {
        this.ukuran = ukuran;
        this.jenisTanah = jenisTanah;
        this.daftarTanaman = new ArrayList<>();
    }
    
    /**
     * Overloading 1: Menanam satu tanaman
     */
    public boolean menanam(Tanaman tanaman) { 
        if (daftarTanaman.size() < ukuran) {
            daftarTanaman.add(tanaman);
            return true; // Berhasil
        } else {
            return false; // Gagal, lahan penuh
        }
    }

    /**
     * BARU: Overloading 2: Menanam banyak bibit sekaligus
     * Method ini memiliki nama yang sama ('menanam') tapi parameter berbeda
     * (ArrayList<Tanaman>) dari method di atas. Ini adalah contoh Overloading.
     */
    public void menanam(ArrayList<Tanaman> bibitList) {
        int berhasilDitanam = 0;
        int gagalDitanam = 0;
        
        System.out.println("\n[Aksi] Mencoba menanam " + bibitList.size() + " bibit...");
        for (Tanaman bibit : bibitList) {
            // Memanggil method menanam(Tanaman) versi pertama
            if (menanam(bibit)) { 
                System.out.println("- " + bibit.getNama() + " berhasil ditanam.");
                berhasilDitanam++;
            } else {
                System.out.println("- Gagal menanam " + bibit.getNama() + ", lahan penuh.");
                gagalDitanam++;
            }
        }
        System.out.println("[Info] Total berhasil ditanam: " + berhasilDitanam + ", Gagal: " + gagalDitanam);
    }


    @Override
    public void menyiram() { 
        for (Tanaman t : daftarTanaman) {
            t.siram();
        }
    }

    @Override
    public void memupuk() { 
        for (Tanaman t : daftarTanaman) {
            t.beriPupuk();
        }
    }
    
    @Override
    public boolean memberantasHama() { 
        boolean adaHama = false;
        for (Tanaman t : daftarTanaman) {
            if(t.isSakit()) {
                t.setSakit(false);
                adaHama = true;
            }
        }
        return adaHama;
    }
    
    public void updateHarian(Cuaca cuaca) { 
        for (Tanaman t : daftarTanaman) {
            t.tumbuh(cuaca); 
        }
    }

    public ArrayList<Tanaman> cekPanen(Pemain pemain) {
        ArrayList<Tanaman> yangDipanen = new ArrayList<>();
        for (int i = daftarTanaman.size() - 1; i >= 0; i--) {
            Tanaman t = daftarTanaman.get(i);
            if (t.isSiapPanen()) {
                if (pemain.pakaiEnergi(ENERGI_PANEN)) {
                    t.panen();
                    pemain.tambahHasilPanen(t.getNama(), 1); 
                    yangDipanen.add(t);
                    daftarTanaman.remove(i); 
                } else {
                    System.out.println("\n[Info] Energi habis! Tidak bisa memanen " + t.getNama());
                    break; 
                }
            }
        }
        return yangDipanen;
    }
    
    // Getters 
    public int getUkuran() { return ukuran; }
    public String getJenisTanah() { return jenisTanah; }
    public ArrayList<Tanaman> getDaftarTanaman() { return daftarTanaman; }
}