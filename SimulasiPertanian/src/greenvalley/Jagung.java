package greenvalley;

// WAJIB 'public' class
public class Jagung extends Tanaman {
    public Jagung() {
        super("Jagung", 60, 30);
    }

    @Override
    public void tumbuh(Cuaca cuaca) {
        if (isSakit()) return;
        if (cuaca.isHujan()) {
            setKebutuhanAir(getKebutuhanAir() - 10);
        } else {
            setKebutuhanAir(getKebutuhanAir() + 3);
        }
        if (getKebutuhanAir() <= 0) {
            int progres = getStatusTumbuh() + (100 / getWaktuPanen());
            setStatusTumbuh(Math.min(100, progres));
        } else if (getKebutuhanAir() > 40) {
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
