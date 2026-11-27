# TugasKu - Aplikasi Agenda Tugas Kuliah

Proyek Ujian Tengah Semester (UTS) Mata Kuliah **Pemrograman Bergerak** (3 SKS) — Program Studi Informatika, Universitas Al-Azhar Indonesia.

---

## Identitas Mahasiswa

* **Nama:** Adham Maula Tirtasaputra
* **NIM:** 0102524001
* **Digit Terakhir NIM:** 1
* **Topik UTS:** Topik B — Agenda Tugas Kuliah (Aplikasi Pencatatan Tugas Kuliah)
* **Repositori GitHub:** [https://github.com/adhammmaula/PBUTS_TugasKu_AdhamMaula](https://github.com/adhammmaula/PBUTS_TugasKu_AdhamMaula)
* **Tautan Video Penjelasan & Demonstrasi:** `(https://drive.google.com/drive/folders/1d5ZPE4mvcvTrm3asJ-sQB9_Kf5cjARU2?usp=sharing)`

---

## Deskripsi Aplikasi

**TugasKu** adalah aplikasi Android berbasis Kotlin dan Jetpack Compose yang membantu mahasiswa mencatat, memantau, dan mengelola daftar tugas perkuliahan secara lokal. Pengguna dapat menambahkan tugas beserta nama mata kuliah dan catatan singkat, memperbarui status penyelesaian (*Belum selesai* / *Selesai*), mengubah atau menghapus data tugas, serta mengatur preferensi tampilan ringkas yang tetap tersimpan meskipun aplikasi ditutup.

---

## Fitur Utama

1. **Tiga Layar Utama dengan Navigation Compose:**
   * **Layar Daftar Tugas (`DaftarTugasScreen`):** Menampilkan seluruh daftar tugas menggunakan `LazyColumn`, tombol ubah status cepat, indikator status kosong (*empty state*), serta pengaturan **Mode tampilan ringkas**.
   * **Layar Detail Tugas (`DetailTugasScreen`):** Menampilkan rincian lengkap tugas, tombol ubah status pengerjaan, navigasi ke form edit, dan dialog konfirmasi hapus tugas.
   * **Layar Form Tambah/Ubah (`FormTugasScreen`):** Formulir untuk menambah tugas baru atau memperbarui data tugas yang sudah ada.
2. **Operasi CRUD & Pembaruan Status:** Mendukung Create, Read, Update, Delete, serta toggle status *Selesai / Belum selesai*.
3. **Validasi Input:** Memastikan kolom **Nama Tugas** tidak boleh kosong sebelum disimpan dengan menampilkan pesan error yang informatif.
4. **Manajemen State Reaktif (`ViewModel` & `StateFlow`):** Memisahkan logika bisnis dan pengelolaan state UI dari komponen Composable menggunakan `TugasViewModel` dan `TugasUiState`.
5. **Penyimpanan Lokal Persisten (`Room Database`):** Menyimpan entitas tugas (`id`, `namaTugas`, `mataKuliah`, `catatan`, `selesai`) secara lokal menggunakan SQLite melalui Room (`Entity`, `DAO`, `Database`, dan `Repository`).
6. **Preferensi Pengguna (`Preferences DataStore`):** Menyimpan pengaturan sakelar **Mode tampilan ringkas (ON/OFF)** agar tetap bertahan setelah aplikasi ditutup maupun dibuka kembali.

---

## Perangkat & Teknologi yang Digunakan

* **Bahasa Pemrograman:** Kotlin (2.2.10)
* **UI Toolkit:** Jetpack Compose & Material Design 3
* **Arsitektur:** MVVM (Compose UI → ViewModel + StateFlow → Repository → Room & DataStore)
* **Database Lokal:** AndroidX Room (2.7.1) dengan KSP (`2.2.10-2.0.2`)
* **Penyimpanan Preferensi:** Jetpack DataStore Preferences (1.1.4)
* **Navigasi:** Jetpack Navigation Compose (2.8.9)
* **IDE & Build System:** Android Studio, Android Gradle Plugin (9.4.1), Target SDK 37, Min SDK 24

---

## Struktur Proyek

```text
com.example.tugasku_adhammaula_0102524001
├── data
│   ├── local
│   │   ├── SettingsDataStore.kt   # Pengelolaan preferensi Mode tampilan ringkas (DataStore)
│   │   ├── TugasDao.kt            # Data Access Object untuk query CRUD Room
│   │   ├── TugasDatabase.kt       # Konfigurasi singleton Room Database
│   │   └── TugasEntity.kt         # Definisi tabel dan entitas tugas kuliah
│   └── repository
│       └── TugasRepository.kt     # Abstraksi sumber data antara DAO dan DataStore
├── ui
│   ├── navigation
│   │   └── AppNavigation.kt       # Konfigurasi NavHost antar layar Daftar, Detail, dan Form
│   ├── screens
│   │   ├── DaftarTugasScreen.kt   # Tampilan daftar tugas, switch mode ringkas, dan empty state
│   │   ├── DetailTugasScreen.kt   # Tampilan detail tugas, toggle status, edit, dan hapus
│   │   └── FormTugasScreen.kt     # Form tambah/edit tugas dengan validasi input
│   ├── theme                      # Konfigurasi warna, tipografi, dan tema Material 3
│   └── viewmodel
│       └── TugasViewModel.kt      # Pengelolaan UiState (StateFlow) dan event pengguna
└── MainActivity.kt                # Entry point aplikasi dan inisialisasi dependensi
