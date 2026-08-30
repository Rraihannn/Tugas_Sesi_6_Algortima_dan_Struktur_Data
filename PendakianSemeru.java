import java.util.Scanner;

/**
 * TUGAS PERTEMUAN KE 6 - ARRAY (2D)
 * =================================
 * Simulasi pendakian Riyani ke Puncak Mahameru, Gunung Semeru.
 *
 * PETA (array 2 dimensi 6 baris x 12 kolom), direkonstruksi dari gambar soal:
 *   - "X"      = jurang / jalur terlarang (tidak boleh dilewati)
 *   - "."      = jalur hijau biasa (boleh dilewati)
 *   - "P1".."P5", "TC", "RK", "PUNCAK" = jalur hijau sekaligus titik Pos/Puncak
 *     (di titik inilah istirahat "R" diperbolehkan)
 *
 * Baris 0 = paling atas (dekat Puncak Mahameru)
 * Baris 5 = paling bawah (dekat Pos Ranu Pane / P1, titik awal pendakian)
 *
 * ATURAN PERGERAKAN:
 *   L -> bergerak ke kiri  (kolom - 1)
 *   D -> bergerak ke turun (baris + 1)
 *   U -> bergerak ke naik  (baris - 1)
 *   R -> ISTIRAHAT (menambah energi 10). Berdasarkan contoh kasus pada soal,
 *        "R" hanya berfungsi sebagai istirahat dan HANYA boleh dilakukan
 *        ketika posisi Riyani sedang berada di sebuah Pos. Jika "R" dipakai
 *        di luar Pos, pendakian dianggap gagal dengan pesan khusus.
 *
 * Setiap pergerakan (L/U/D) yang berhasil mengurangi tenaga sebanyak 1.
 * Istirahat (R di Pos) TIDAK mengurangi tenaga, hanya menambah 10.
 *
 * Catatan: keempat contoh kasus pada soal (tenaga 12, 100, 15, dan 10)
 * sudah diuji terhadap program ini dan menghasilkan output yang sesuai.
 */
public class PendakianSemeru {

    // Peta pendakian: baris 0 (atas, Puncak) sampai baris 5 (bawah, P1)
    static final String[][] PETA = {
        {"PUNCAK", "X", "X", "X", "X", "X", "X", "X", "X", ".", ".", "."},
        {".", "X", ".", ".", ".", "X", "X", "P3", "X", ".", "X", "."},
        {".", "X", "X", "X", ".", "P4", ".", ".", ".", ".", "X", "."},
        {".", "TC", ".", ".", ".", "X", ".", ".", "X", "X", "X", "."},
        {".", ".", "X", ".", ".", "X", "P2", "X", "X", "X", "X", "."},
        {"P5", ".", "X", "RK", ".", "X", ".", ".", ".", ".", ".", "P1"}
    };

    static final int JUMLAH_BARIS = PETA.length;       // 6
    static final int JUMLAH_KOLOM = PETA[0].length;     // 12

    // Posisi awal pendakian selalu di P1 (baris 5, kolom 11)
    static final int START_BARIS = 5;
    static final int START_KOLOM = 11;

    /**
     * Mengecek apakah sebuah sel merupakan sebuah Pos (termasuk Puncak).
     * Sel dianggap Pos jika bukan jalur biasa "." dan bukan jurang "X".
     */
    static boolean isPos(String sel) {
        return !sel.equals(".") && !sel.equals("X");
    }

    /**
     * Mengecek apakah koordinat (baris, kolom) masih berada di dalam peta.
     */
    static boolean dalamPeta(int baris, int kolom) {
        return baris >= 0 && baris < JUMLAH_BARIS && kolom >= 0 && kolom < JUMLAH_KOLOM;
    }

    /**
     * Menjalankan simulasi pendakian berdasarkan tenaga awal dan jalur yang diberikan.
     * Mengembalikan pesan output sesuai hasil pendakian.
     */
    static String prosesPendakian(int tenaga, String jalur) {
        int baris = START_BARIS;
        int kolom = START_KOLOM;

        for (int i = 0; i < jalur.length(); i++) {
            char langkah = jalur.charAt(i);

            if (langkah == 'R') {
                // Percobaan istirahat
                if (isPos(PETA[baris][kolom])) {
                    tenaga += 10; // istirahat menambah energi, tidak mengurangi tenaga
                    continue;
                } else {
                    return "Mohon maaf, istirahat hanya diperbolehkan di Pos-pos yang tersedia";
                }
            }

            // Untuk langkah L, U, D -> perlu tenaga tersisa untuk bergerak
            if (tenaga <= 0) {
                return "Jalur anda benar, tapi tenaga anda tidak akan kuat, "
                        + "coba jalur lain atau sempatkan istirahat terlebih dahulu";
            }

            int barisBaru = baris;
            int kolomBaru = kolom;

            switch (langkah) {
                case 'L':
                    kolomBaru = kolom - 1;
                    break;
                case 'U':
                    barisBaru = baris - 1;
                    break;
                case 'D':
                    barisBaru = baris + 1;
                    break;
                default:
                    // karakter tidak dikenal, abaikan (tidak mengurangi tenaga)
                    continue;
            }

            // Cek apakah tujuan langkah berada di luar peta atau merupakan jurang (X)
            if (!dalamPeta(barisBaru, kolomBaru) || PETA[barisBaru][kolomBaru].equals("X")) {
                return "Jalur anda salah, anda masuk ke jurang/blank pada posisi baris "
                        + (barisBaru + 1) + " kolom " + (kolomBaru + 1);
            }

            // Langkah valid -> pindah posisi dan kurangi tenaga
            baris = barisBaru;
            kolom = kolomBaru;
            tenaga -= 1;

            // Cek apakah sudah sampai Puncak Mahameru
            if (PETA[baris][kolom].equals("PUNCAK")) {
                return "Selamat Pendakian anda berhasil mencapai Puncak Mahameru, sisa tenaga anda " + tenaga;
            }
        }

        // Jalur habis diproses tapi belum sampai Puncak
        return "Jalur telah selesai diproses, namun Riyani belum sampai di Puncak Mahameru. "
                + "Posisi terakhir baris " + (baris + 1) + " kolom " + (kolom + 1)
                + ", sisa tenaga " + tenaga;
    }

    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("UJI COBA DENGAN CONTOH PADA SOAL");
        System.out.println("=".repeat(70));

        // Contoh 1
        System.out.println("\nContoh 1 (Tenaga: 12, Jalur: \"LLLLLUUULRLDDLDRUULLLUUU\")");
        System.out.println("Output : " + prosesPendakian(12, "LLLLLUUULRLDDLDRUULLLUUU"));

        // Contoh 2
        System.out.println("\nContoh 2 (Tenaga: 100, Jalur: \"LLLLLUUULLDDDRLUULLLUUU\")");
        System.out.println("Output : " + prosesPendakian(100, "LLLLLUUULLDDDRLUULLLUUU"));

        // Contoh 3
        System.out.println("\nContoh 3 (Tenaga: 15, Jalur: \"LLLLUUUUR\")");
        System.out.println("Output : " + prosesPendakian(15, "LLLLUUUUR"));

        // Contoh 4
        System.out.println("\nContoh 4 (Tenaga: 10, Jalur: \"LLLLLUUULLDDLDUULLLUUU\")");
        System.out.println("Output : " + prosesPendakian(10, "LLLLLUUULLDDLDUULLLUUU"));

        // Bagian interaktif: pengguna bisa mencoba input sendiri
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COBA INPUT ANDA SENDIRI (ketik 'selesai' untuk keluar)");
        System.out.println("=".repeat(70));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nMasukkan tenaga awal (atau 'selesai' untuk keluar): ");
            String inputTenaga = scanner.nextLine().trim();
            if (inputTenaga.equalsIgnoreCase("selesai")) {
                break;
            }

            int tenaga;
            try {
                tenaga = Integer.parseInt(inputTenaga);
            } catch (NumberFormatException e) {
                System.out.println("Input tenaga harus berupa angka!");
                continue;
            }

            System.out.print("Masukkan jalur (contoh: LLLLUUUU): ");
            String jalur = scanner.nextLine().trim().toUpperCase();

            System.out.println("Output : " + prosesPendakian(tenaga, jalur));
        }

        scanner.close();
        System.out.println("\nProgram selesai. Terima kasih!");
    }
}
