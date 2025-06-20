# TaskBuddy – Manajemen Tugas Personal (Console-Based)

## 📌 Deskripsi Proyek

Proyek ini merupakan implementasi sistem manajemen tugas berbasis terminal (Command Line Interface) yang memanfaatkan berbagai struktur data untuk mendukung fitur-fitur kompleks dan efisien. Sistem ini dirancang untuk mendemonstrasikan integrasi struktur data seperti Tree, Double Linked List, Queue, serta algoritma Sorting dan Searching dalam konteks dunia nyata, khususnya dalam pengelolaan tugas dan aktivitas multi-user.

## 🔧 Deskripsi Fitur:

- Struktur Hirarki Tugas (Tree): Tugas dan subtugas disusun dalam bentuk pohon untuk mendukung relasi hierarkis antara kategori dan subtugas.


- Pencatatan Log Aktivitas (Double Linked List): Setiap perubahan yang terjadi dalam sistem, seperti penambahan atau penghapusan tugas, dicatat dalam bentuk log berurutan yang dapat ditelusuri mundur maupun maju.


- Simulasi Multi-User (Queue): Pengguna diatur secara antrian untuk mensimulasikan interaksi bergiliran dalam sistem multi-user.

- Pengurutan Tugas (Bubble Sort): Tugas dapat diurutkan berdasarkan prioritas atau tenggat waktu, sehingga memudahkan pengguna dalam menentukan skala urgensi.

- Pencarian Tugas (Linear Search): Sistem menyediakan fitur pencarian tugas berdasarkan nama atau deskripsi untuk mempermudah navigasi dalam daftar tugas.

## 🧭 Alur Penggunaan Aplikasi

1. Login Pengguna:

- Pengguna masuk ke sistem berdasarkan antrian (Queue) dan    menjalankan sesi satu per satu.
- Identitas pengguna aktif beserta perannya akan ditampilkan.


2. Navigasi Menu Utama:
- Menu CLI mencakup fitur-fitur berikut:
    - Tambah tugas/kategori
    - Lihat struktur tugas
    - Edit atau hapus tugas
    - Lihat log aktivitas
    - Pencarian tugas
    - Pengurutan tugas
    - Ganti pengguna (berpindah ke pengguna berikutnya)

3. Manajemen Tugas:
- Tugas dan subtugas dimasukkan ke dalam struktur pohon sesuai dengan kategori induk.
- Setiap aktivitas akan dicatat otomatis dalam log aktivitas (Double Linked List).

4. Fitur Pendukung:
- Pencarian tugas melalui nama/deskripsi menggunakan Linear Search.
- Pengurutan tugas dengan Bubble Sort berdasarkan deadline atau prioritas.

5. Pergantian Pengguna:
- Setelah satu sesi selesai, sistem akan berpindah ke pengguna berikutnya dalam antrian.

6. Keluar dari Aplikasi:
- Aplikasi akan menyimpan log akhir sesi sebelum ditutup.

## 👥 Pembagian Tugas

Pengembangan proyek ini dilakukan secara kolaboratif dengan pembagian tanggung jawab sebagai berikut:

1. Prayoga Adi Setyawan: Mengimplementasikan struktur tugas berbasis pohon (Tree) yang menjadi inti dari manajemen kategori dan subtugas.

2. Zaidan Rabbani: Mengembangkan sistem pencatatan log aktivitas menggunakan Double Linked List untuk mendukung fitur undo/redo dan histori perubahan.
![Log Aktivitas](https://github.com/Telempek/TelempekIMG/blob/main/Screenshot%202025-06-20%20144455.png?raw=true)

3. Dhani Arizal: Menangani antrian pengguna (Queue) untuk mensimulasikan interaksi bergiliran dalam sistem multi-user.
![Queue](https://github.com/Telempek/TelempekIMG/blob/main/Screenshot%202025-06-20%20144455.png?raw=true)

4. Rafi Ahmad Dzulfaqar: Mengimplementasikan algoritma Bubble Sort untuk pengurutan dan Linear Search untuk pencarian tugas berdasarkan kata kunci.
![Sorting(Bubble Sort) dan Searching(Linear Searching)](https://github.com/Telempek/TelempekIMG/blob/main/Screenshot%202025-06-20%20154037.png?raw=true)

![](https://github.com/Telempek/TelempekIMG/blob/main/Screenshot%202025-06-20%20154051.png?raw=true)

![](https://github.com/Telempek/TelempekIMG/blob/main/Screenshot%202025-06-20%20154111.png?raw=true)

5. Rafael Seta Dewa: Merancang modul manajemen pengguna dan sistem peran untuk mendukung fleksibilitas dan pembagian hak akses.

6. Qoid Kafi: Membangun dan merapikan menu utama berbasis CLI serta integrasi logika utama sistem secara keseluruhan.
![Login Pengguna](https://github.com/Telempek/TelempekIMG/blob/dc6ef885adc67a8a248af19a1381787c73903e54/Screenshot%202025-06-20%20142618.png?raw=true)

## Team
- Prayoga Adi Setyawann [@setyawann](https://www.github.com/setyawannn)
- Qoid Kafi [@Telempek](https://github.com/Telempek)
- Zaidan Rabbani [@zaidanrabbani](https://github.com/zaidanrabbani)
- Rafael Seta Dewa [@paeldewo](https://github.com/paeldewo)
- Rafi Ahmad Dzulfaqar [@rafdzul](https://github.com/rafdzul)
- Dhani Arizal [@rijulcenat](https://github.com/rijulcenat)