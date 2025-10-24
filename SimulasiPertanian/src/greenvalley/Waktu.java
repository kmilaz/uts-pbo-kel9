package greenvalley;

// WAJIB 'public' class
public class Waktu {
    private int hari = 1;
    private String musim = "Kemarau";

    public void gantiHari() { 
        hari++;
        if (hari % 90 == 0) { 
            musim = (musim.equals("Kemarau")) ? "Hujan" : "Kemarau";
        }
    }
    public int getHari() { return hari; }
    public String getMusim() { return musim; }
}