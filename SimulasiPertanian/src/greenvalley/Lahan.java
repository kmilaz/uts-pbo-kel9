package greenvalley;

import java.util.ArrayList;

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
    
    public boolean menanam(Tanaman tanaman) { 
        if (daftarTanaman.size() < ukuran) {
            daftarTanaman.add(tanaman);
            return true;
        } else {
            return false;
        }
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

    /**
     * DIMODIFIKASI: Sekarang method ini mengecek dan memakai energi
     * pemain untuk setiap tanaman yang dipanen.
     */
    public ArrayList<Tanaman> cekPanen(Pemain pemain) {
        ArrayList<Tanaman> yangDipanen = new ArrayList<>();
        for (int i = daftarTanaman.size() - 1; i >= 0; i--) {
            Tanaman t = daftarTanaman.get(i);
            if (t.isSiapPanen()) {
                
                // Cek energi SEBELUM memanen
                if (pemain.pakaiEnergi(ENERGI_PANEN)) {
                    // Energi cukup, lanjutkan panen
                    t.panen();
                    pemain.tambahHasilPanen(t.getNama(), 1); 
                    yangDipanen.add(t);
                    daftarTanaman.remove(i); 
                } else {
                    // Energi habis, cetak pesan dan hentikan panen
                    System.out.println("\n[Info] Energi habis! Tidak bisa memanen " + t.getNama());
                    break; // Hentikan loop panen
                }
            }
        }
        return yangDipanen;
    }
    
    public int getUkuran() { return ukuran; }
    public String getJenisTanah() { return jenisTanah; }
    public ArrayList<Tanaman> getDaftarTanaman() { return daftarTanaman; }
}