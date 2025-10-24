package greenvalley;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class GreenValleySaga {

    // --- Konstanta ANSI Colors ---
    public static final String RESET = "\033[0m";
    public static final String BOLD = "\033[1m";
    public static final String MERAH = "\033[0;31m";
    public static final String HIJAU = "\033[0;32m";
    public static final String KUNING = "\033[0;33m";
    public static final String BIRU = "\033[0;34m";
    public static final String CYAN = "\033[0;36m";
    public static final String MERAH_BOLD = "\033[1;31m";
    public static final String HIJAU_BOLD = "\033[1;32m";
    public static final String KUNING_BOLD = "\033[1;33m";
    public static final String CYAN_BOLD = "\033[1;36m";

    // --- Biaya Energi ---
    private static final int ENERGI_TANAM_SATU = 15; // Energi tanam satu bibit
    private static final int ENERGI_TANAM_BANYAK_PER_BIBIT = 12; // Energi LEBIH MURAH per bibit jika tanam banyak
    private static final int ENERGI_SIRAM = 5;
    private static final int ENERGI_PUPUK = 7;
    private static final int ENERGI_BASMI_HAMA = 10;
    private static final int ENERGI_PULIH_TIDUR = 100;

    // --- Objek Game ---
    private static Scanner scanner = new Scanner(System.in);
    private static Pemain pemain = new Pemain(500, 100);
    private static Lahan lahan = new Lahan(5, "Subur");
    private static Toko toko = new Toko();
    private static Waktu waktu = new Waktu();
    private static Cuaca cuaca = new Cuaca();

    private static void bersihkanLayar() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void tunggu(int milidetik) {
        try {
            Thread.sleep(milidetik);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void tungguEnter(String... prompt) {
        if (prompt.length > 0) {
            System.out.print(prompt[0]);
        } else {
            System.out.print("\nTekan ENTER untuk melanjutkan...");
        }
        scanner.nextLine();
    }

    private static int bacaPilihan() {
        try {
            int pilihan = scanner.nextInt();
            scanner.nextLine();
            return pilihan;
        } catch (InputMismatchException e) {
            System.out.println(MERAH_BOLD + "Input tidak valid. Harap masukkan angka." + RESET);
            scanner.nextLine(); // Membersihkan buffer scanner yang salah
            tunggu(1000);
            return -1;
        }
    }

    private static void tampilkanLayarPembuka() {
        bersihkanLayar();
        System.out.println(HIJAU_BOLD);
        System.out.println("   ██████╗ ██████╗ ███████╗ ███████╗ ███╗   ██╗     ██╗   ██╗ █████╗ ██╗     ██╗     ██╗   ██╗ ");
        System.out.println("  ██╔════╝ ██╔══██╗██╔════╝ ██╔════╝ ████╗  ██║     ██║   ██║██╔══██╗██║     ██║     ╚██╗ ██╔╝ ");
        System.out.println("  ██║  ███╗██████╔╝█████╗   █████╗   ██╔██╗ ██║     ██║   ██║███████║██║     ██║      ╚████╔╝  ");
        System.out.println("  ██║   ██║██╔══██╗██╔══╝   ██╔══╝   ██║╚██╗██║     ██║   ██║██╔══██║██║     ██║       ╚██╔╝   ");
        System.out.println("  ╚██████╔╝██║  ██║███████╗ ███████╗ ██║ ╚████║     ╚██████╔╝██║  ██║███████╗███████╗   ██║    ");
        System.out.println("   ╚═════╝ ╚═╝  ╚═╝╚══════╝ ╚══════╝ ╚═╝  ╚═══╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝    ");
        System.out.println(RESET);
        System.out.println(KUNING + "                 --- Kelola lahan, tanam bibit, raih panen berlimpah! ---" + RESET);
        System.out.println();
        tungguEnter(CYAN + "Tekan ENTER untuk mulai..." + RESET);
    }

    private static void tampilkanDashboard() {
        System.out.println(CYAN_BOLD + "====================== DASHBOARD ======================" + RESET);
        System.out.println(BOLD + "Hari ke-" + waktu.getHari() + " (" + waktu.getMusim() + ")  |  Cuaca: " + cuaca.getKondisi() + RESET);
        System.out.println(HIJAU_BOLD + "Uang: " + pemain.getUang() + RESET + "  |  " + BIRU + "Energi: " + pemain.getEnergi() + "/100" + RESET);

        System.out.println(KUNING_BOLD + "\n--- Status Lahan (" + lahan.getDaftarTanaman().size() + "/" + lahan.getUkuran() + ") ---" + RESET);
        if (lahan.getDaftarTanaman().isEmpty()) {
            System.out.println("Lahan kosong. Siap ditanami!");
        } else {
            for (Tanaman t : lahan.getDaftarTanaman()) {
                String statusSakit = t.isSakit() ? MERAH_BOLD + " (SAKIT)" + RESET : "";
                String statusPanen = t.isSiapPanen() ? HIJAU_BOLD + " (SIAP PANEN)" + RESET : "";
                System.out.println(String.format("- %-10s [Tumbuh: %-3d%% | Air: %-3d] %s%s",
                    t.getNama(), t.getStatusTumbuh(), t.getKebutuhanAir(), statusSakit, statusPanen));
            }
        }

        System.out.println(KUNING_BOLD + "\n--- Inventori Pemain ---" + RESET);
        if (pemain.getInventori().isEmpty()) {
            System.out.println("Inventori kosong.");
        } else {
            for (ItemPanen item : pemain.getInventori()) {
                System.out.println("- " + item.getNama() + ": " + HIJAU_BOLD + item.getJumlah() + RESET + " buah");
            }
        }
        System.out.println(CYAN_BOLD + "=====================================================" + RESET);
    }

    /**
     * DIMODIFIKASI: Menu diubah, opsi tanam jadi 2
     */
    private static void tampilkanMenu() {
        System.out.println("\n--- Menu Aksi ---");
        System.out.println("1. Tanam 1 Bibit    (Energi: -" + ENERGI_TANAM_SATU + ")");
        System.out.println("2. Tanam Banyak Bibit (Energi: -" + ENERGI_TANAM_BANYAK_PER_BIBIT + " per bibit)"); // BARU
        System.out.println("3. Siram Lahan      (Energi: -" + ENERGI_SIRAM + ")");
        System.out.println("4. Beri Pupuk       (Energi: -" + ENERGI_PUPUK + ")");
        System.out.println("5. Basmi Hama       (Energi: -" + ENERGI_BASMI_HAMA + ")");
        System.out.println("6. Panen Tanaman    (Energi: -10 per tanaman)");
        System.out.println("7. Jual Hasil Panen (Ke Toko)");
        System.out.println(HIJAU_BOLD + "8. Tidur" + RESET + "            (Ganti Hari & Pulihkan Energi Penuh)");
        System.out.println(MERAH + "9. Keluar dari Game" + RESET);
        System.out.print(BOLD + "Pilihan Anda (1-9): " + RESET); // Range pilihan diubah
    }

    // --- Method Aksi (Controller) ---

    private static void prosesGantiHari() {
        waktu.gantiHari();
        cuaca.prediksiCuaca();
        lahan.updateHarian(cuaca);
        ArrayList<Tanaman> yangDipanen = lahan.cekPanen(pemain);
        if (!yangDipanen.isEmpty()) {
            System.out.println(HIJAU_BOLD + "[Panen] Tanaman berikut berhasil dipanen otomatis:" + RESET);
            for(Tanaman t : yangDipanen) {
                System.out.println("- " + t.getNama());
            }
        }
        if (Math.random() < 0.20) {
            ArrayList<Tanaman> daftarTanaman = lahan.getDaftarTanaman();
            if (!daftarTanaman.isEmpty()) {
                int indexTanaman = (int)(Math.random() * daftarTanaman.size());
                Tanaman target = daftarTanaman.get(indexTanaman);
                if (!target.isSakit()) {
                    target.setSakit(true);
                    System.out.println(MERAH_BOLD + "\n!!! EVENT: SERANGAN HAMA !!!" + RESET);
                    System.out.println(MERAH + "Tanaman " + target.getNama() + " Anda di lahan terserang penyakit!" + RESET);
                }
            }
        }
    }

    /**
     * DIMODIFIKASI: Method ini sekarang untuk tanam SATU bibit
     */
    private static void prosesTanamSatu() {
        System.out.println("\n--- Beli & Tanam 1 Bibit ---");
        System.out.println("1. Bibit Padi (Harga: " + HIJAU + toko.getHargaBeli("Bibit Padi") + RESET + ")");
        System.out.println("2. Bibit Jagung (Harga: " + HIJAU + toko.getHargaBeli("Bibit Jagung") + RESET + ")");
        System.out.println("3. Batal");
        System.out.print(BOLD + "Pilihan Bibit (1-3): " + RESET);

        int pilihan = bacaPilihan();
        Tanaman bibitBaru = null;

        if (pilihan == 1) {
            bibitBaru = toko.beliBibit(pemain, "Bibit Padi");
        } else if (pilihan == 2) {
            bibitBaru = toko.beliBibit(pemain, "Bibit Jagung");
        } else {
            System.out.println("...Batal menanam.");
            tunggu(1000);
            return;
        }

        if (bibitBaru != null) {
            if (lahan.menanam(bibitBaru)) { // Memanggil menanam(Tanaman)
                System.out.println(HIJAU + "[Aksi] " + bibitBaru.getNama() + " telah ditanam." + RESET);
            } else {
                System.out.println(MERAH + "[Info] Lahan penuh! Tanam gagal." + RESET);
            }
        } else {
            System.out.println(MERAH + "[Info] Pembelian bibit gagal. Uang tidak cukup." + RESET);
        }
        tunggu(1500);
    }

    /**
     * BARU: Method untuk Tanam BANYAK bibit (memanggil method overloading)
     */
    private static void prosesTanamBanyak() {
        System.out.println("\n--- Beli & Tanam Banyak Bibit ---");
        System.out.println("1. Bibit Padi (Harga: " + HIJAU + toko.getHargaBeli("Bibit Padi") + RESET + ")");
        System.out.println("2. Bibit Jagung (Harga: " + HIJAU + toko.getHargaBeli("Bibit Jagung") + RESET + ")");
        System.out.println("3. Batal");
        System.out.print(BOLD + "Pilih jenis Bibit (1-3): " + RESET);

        int pilihanJenis = bacaPilihan();
        String namaBibit = "";
        int hargaSatuan = 0;

        if (pilihanJenis == 1) {
            namaBibit = "Bibit Padi";
            hargaSatuan = toko.getHargaBeli(namaBibit);
        } else if (pilihanJenis == 2) {
            namaBibit = "Bibit Jagung";
            hargaSatuan = toko.getHargaBeli(namaBibit);
        } else {
            System.out.println("...Batal menanam.");
            tunggu(1000);
            return;
        }

        System.out.print(BOLD + "Jumlah bibit yang ingin dibeli dan ditanam: " + RESET);
        int jumlah = bacaPilihan();

        if (jumlah <= 0) {
            System.out.println(MERAH + "Jumlah tidak valid." + RESET);
            tunggu(1000);
            return;
        }

        int totalHarga = hargaSatuan * jumlah;
        int totalEnergi = ENERGI_TANAM_BANYAK_PER_BIBIT * jumlah;

        // Cek uang dan energi
        if (pemain.getUang() < totalHarga) {
            System.out.println(MERAH + "[Info] Uang tidak cukup untuk membeli " + jumlah + " " + namaBibit + "." + RESET);
            tunggu(1500);
            return;
        }
        if (pemain.getEnergi() < totalEnergi) {
             System.out.println(MERAH + "[Info] Energi tidak cukup untuk menanam " + jumlah + " bibit." + RESET);
             tunggu(1500);
            return;
        }

        // Proses pembelian dan pembuatan list bibit
        System.out.println(HIJAU + "\nMembeli " + jumlah + " " + namaBibit + "..." + RESET);
        boolean beliBerhasil = pemain.beli(namaBibit, hargaSatuan, jumlah); // Kurangi uang

        if (beliBerhasil) {
             ArrayList<Tanaman> bibitList = new ArrayList<>();
             for (int i = 0; i < jumlah; i++) {
                 if (namaBibit.equals("Bibit Padi")) {
                     bibitList.add(new Padi());
                 } else {
                     bibitList.add(new Jagung());
                 }
             }

             // Panggil method OVERLOADING menanam(ArrayList<Tanaman>)
             lahan.menanam(bibitList);
             pemain.pakaiEnergi(totalEnergi); // Kurangi energi
             
        } else {
             // Seharusnya tidak terjadi karena sudah dicek, tapi jaga-jaga
             System.out.println(MERAH + "[Error] Terjadi kesalahan saat pembelian." + RESET);
        }
        tungguEnter();
    }


     private static void prosesJual() {
        System.out.println("\n--- Jual Hasil Panen ---");
        System.out.println("1. Padi (Harga Jual: " + HIJAU + toko.getHargaJual("Padi") + RESET + ")");
        System.out.println("2. Jagung (Harga Jual: " + HIJAU + toko.getHargaJual("Jagung") + RESET + ")");
        System.out.println("3. Batal");
        System.out.print(BOLD + "Pilihan Panen (1-3): " + RESET);

        int pilihan = bacaPilihan();
        String namaItem = "";
        int hargaItem = 0;

        if (pilihan == 1) {
            namaItem = "Padi";
            hargaItem = toko.getHargaJual(namaItem);
        } else if (pilihan == 2) {
            namaItem = "Jagung";
            hargaItem = toko.getHargaJual(namaItem);
        } else {
             System.out.println("...Batal menjual.");
             tunggu(1000);
            return;
        }

        System.out.print(BOLD + "Jumlah yang ingin dijual: " + RESET);
        int jumlah = bacaPilihan();
        if (jumlah <= 0) {
            System.out.println(MERAH + "Jumlah tidak valid." + RESET);
            tunggu(1000);
            return;
        }

        if (pemain.jual(namaItem, hargaItem, jumlah)) {
             System.out.println(HIJAU + "[Aksi] Berhasil menjual " + jumlah + " " + namaItem + " seharga " + (hargaItem * jumlah) + RESET);
        } else {
            System.out.println(MERAH + "[Info] Gagal menjual. Cek inventori Anda." + RESET);
        }
        tunggu(1500);
     }


    /**
     * ==================================================
     * MAIN GAME LOOP
     * ==================================================
     */
    public static void main(String[] args) {

        tampilkanLayarPembuka();
        cuaca.prediksiCuaca();

        boolean isGameRunning = true;

        while (isGameRunning) {
            bersihkanLayar();
            tampilkanDashboard();
            tampilkanMenu();

            int pilihan = bacaPilihan();

            switch (pilihan) {
                case 1: // Tanam Satu
                    if (pemain.pakaiEnergi(ENERGI_TANAM_SATU)) {
                        prosesTanamSatu();
                    } else {
                        System.out.println(MERAH + "\n[Info] Energi tidak cukup untuk menanam!" + RESET);
                        tunggu(1500);
                    }
                    break;
                case 2: // Tanam Banyak (BARU)
                    // Pengecekan energi dilakukan di dalam prosesTanamBanyak
                    prosesTanamBanyak();
                    break;
                case 3: // Siram
                    if (pemain.pakaiEnergi(ENERGI_SIRAM)) {
                        System.out.println(BIRU + "\n[Aksi] Menyiram semua tanaman di lahan..." + RESET);
                        lahan.menyiram();
                        tunggu(1000);
                    } else {
                        System.out.println(MERAH + "\n[Info] Energi tidak cukup untuk menyiram!" + RESET);
                        tunggu(1500);
                    }
                    break;
                case 4: // Pupuk
                    if (pemain.pakaiEnergi(ENERGI_PUPUK)) {
                        System.out.println(KUNING + "\n[Aksi] Memupuk semua tanaman di lahan..." + RESET);
                        lahan.memupuk();
                        tunggu(1000);
                    } else {
                        System.out.println(MERAH + "\n[Info] Energi tidak cukup untuk memupuk!" + RESET);
                        tunggu(1500);
                    }
                    break;
                case 5: // Basmi Hama
                    if (pemain.pakaiEnergi(ENERGI_BASMI_HAMA)) {
                        System.out.println(MERAH + "\n[Aksi] Memberantas hama di lahan..." + RESET);
                        if(lahan.memberantasHama()) {
                            System.out.println(HIJAU + "Hama berhasil dibasmi!" + RESET);
                        } else {
                            System.out.println(BIRU + "Tidak ada hama yang perlu dibasmi." + RESET);
                        }
                        tunggu(1000);
                    } else {
                        System.out.println(MERAH + "\n[Info] Energi tidak cukup untuk basmi hama!" + RESET);
                        tunggu(1500);
                    }
                    break;
                case 6: // Panen
                    ArrayList<Tanaman> yangDipanen = lahan.cekPanen(pemain);
                    if (yangDipanen.isEmpty()) {
                        boolean adaSiapPanen = false;
                        for(Tanaman t : lahan.getDaftarTanaman()){
                            if(t.isSiapPanen()){
                                adaSiapPanen = true;
                                break;
                            }
                        }
                        if(adaSiapPanen && pemain.getEnergi() < 10) {
                             System.out.println(MERAH + "\n[Info] Ada tanaman siap panen, tapi energi tidak cukup!" + RESET);
                        } else {
                             System.out.println(BIRU + "\n[Info] Belum ada tanaman yang siap dipanen." + RESET);
                        }
                    } else {
                        System.out.println(HIJAU_BOLD + "\n[Panen] Berhasil memanen:" + RESET);
                         for(Tanaman t : yangDipanen) {
                            System.out.println("- " + t.getNama());
                        }
                    }
                    tunggu(1500);
                    break;
                case 7: // Jual Panen
                    prosesJual();
                    break;
                case 8: // Tidur
                    System.out.println(HIJAU + "\nSelamat tidur... Zzz..." + RESET);
                    tunggu(1500);
                    pemain.pulihkanEnergi(ENERGI_PULIH_TIDUR);
                    prosesGantiHari();
                    System.out.println(HIJAU_BOLD + "[Info] Energi telah pulih sepenuhnya!" + RESET);
                    tungguEnter();
                    break;
                case 9: // Keluar
                    isGameRunning = false;
                    System.out.println(KUNING_BOLD + "\nTerima kasih telah bermain!" + RESET);
                    break;
                case -1: // Input error
                    break;
                default:
                    // Range pilihan diubah menjadi 1-9
                    System.out.println(MERAH_BOLD + "\n[Error] Pilihan tidak valid (1-9)." + RESET);
                    tunggu(1000);
                    break;
            }
        }

        scanner.close();
    }
}