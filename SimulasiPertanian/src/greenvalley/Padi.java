package greenvalley;

// WAJIB 'public' class
public class Padi extends Tanaman {
    public Padi() {
        super("Padi", 90, 50); 
    }

    @Override 
    public void tumbuh(Cuaca cuaca) {
        if (isSakit()) return; 
        if (cuaca.isHujan()) {
            setKebutuhanAir(getKebutuhanAir() - 15);
        } else {
            setKebutuhanAir(getKebutuhanAir() + 5);
        }
        if (getKebutuhanAir() <= 0) {
            int progres = getStatusTumbuh() + (100 / getWaktuPanen());
            setStatusTumbuh(Math.min(100, progres));
        } else if (getKebutuhanAir() > 70) {
            setSakit(true); 
        }
        cekSiapPanen();
    }

    @Override
    public void panen() {
        if (isSiapPanen()) {
            System.out.println("-> " + getNama() + " telah dipanen!");
        }
    }
}
