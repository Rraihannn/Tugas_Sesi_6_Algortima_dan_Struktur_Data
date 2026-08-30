import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * UTS - TUGAS PRAKTIK ALGORITMA DAN PEMROGRAMAN (Java)
 * Studi Kasus : Sistem Filter Diskon Keranjang Belanja (E-Commerce)
 * =====================================================================
 * Sebuah aplikasi kasir minimarket ingin menghitung total belanja
 * pelanggan. Minimarket ini sedang mengadakan promo diskon per-item
 * (bukan diskon total) dengan aturan bervariasi tergantung harga barang:
 *
 *   - Harga barang lebih dari  Rp 100.000                -> diskon 20%
 *   - Harga barang antara      Rp 50.000 s/d Rp 100.000  -> diskon 10%
 *   - Harga barang kurang dari Rp 50.000                 -> tidak ada diskon (0%)
 *
 * Program ini menggunakan:
 *   1. ArrayList<Double> untuk menyimpan daftar harga barang di keranjang.
 *   2. Perulangan (for) untuk memeriksa setiap barang satu per satu.
 *   3. Percabangan (if - else if - else) untuk menentukan besar diskon.
 *
 * Untuk setiap barang, program mencetak riwayat harga sebelum dan
 * sesudah diskon, lalu setiap harga yang sudah didiskon dijumlahkan
 * ke variabel totalBayar. Di akhir eksekusi, program mencetak
 * total keseluruhan belanja yang harus dibayar.
 */
public class DiskonKeranjangBelanja {

    public static void main(String[] args) {

        // 1. ArrayList bertipe Double berisi daftar harga barang di keranjang
        //    (minimal 6 barang, dengan variasi harga)
        ArrayList<Double> keranjangBelanja = new ArrayList<>();
        keranjangBelanja.add(150000.0); // > 100.000  -> diskon 20%
        keranjangBelanja.add(46000.0);  // < 50.000   -> tidak ada diskon
        keranjangBelanja.add(75000.0);  // 50rb-100rb -> diskon 10%
        keranjangBelanja.add(120000.0); // > 100.000  -> diskon 20%
        keranjangBelanja.add(30000.0);  // < 50.000   -> tidak ada diskon
        keranjangBelanja.add(95000.0);  // 50rb-100rb -> diskon 10%

        // 2. Variabel untuk menampung total belanja yang harus dibayar, awalnya 0
        double totalBayar = 0;

        // Format angka rupiah agar mudah dibaca, contoh: 150000 -> "150.000"
        DecimalFormat formatRupiah = new DecimalFormat("#,###");

        System.out.println("=================================================");
        System.out.println("        RIWAYAT DISKON SETIAP BARANG");
        System.out.println("=================================================");

        // 3. Perulangan untuk memeriksa setiap barang dalam ArrayList
        for (int i = 0; i < keranjangBelanja.size(); i++) {
            double hargaAsli = keranjangBelanja.get(i);
            double hargaSetelahDiskon;
            double persenDiskon;

            // Percabangan if-else if-else untuk menentukan aturan diskon
            if (hargaAsli > 100000) {
                // Harga barang lebih dari Rp 100.000 -> diskon 20%
                persenDiskon = 0.20;
                hargaSetelahDiskon = hargaAsli - (hargaAsli * persenDiskon);
            } else if (hargaAsli >= 50000 && hargaAsli <= 100000) {
                // Harga barang antara Rp 50.000 sampai Rp 100.000 -> diskon 10%
                persenDiskon = 0.10;
                hargaSetelahDiskon = hargaAsli - (hargaAsli * persenDiskon);
            } else {
                // Harga barang kurang dari Rp 50.000 -> tidak ada diskon (0%)
                persenDiskon = 0.0;
                hargaSetelahDiskon = hargaAsli;
            }

            // 4. Cetak riwayat harga sebelum dan sesudah diskon untuk tiap barang
            System.out.println("Barang ke-" + (i + 1) + " -> Harga Asli: Rp"
                    + formatRupiah.format(hargaAsli)
                    + " -> Harga setelah diskon: Rp"
                    + formatRupiah.format(hargaSetelahDiskon)
                    + " (diskon " + (int) (persenDiskon * 100) + "%)");

            // 5. Tambahkan harga yang sudah didiskon ke totalBayar
            totalBayar += hargaSetelahDiskon;
        }

        // 6. Cetak total belanja keseluruhan yang harus dibayar di akhir eksekusi
        System.out.println("=================================================");
        System.out.println("Total Belanja Keseluruhan yang harus dibayar: Rp"
                + formatRupiah.format(totalBayar));
        System.out.println("=================================================");
    }
}
