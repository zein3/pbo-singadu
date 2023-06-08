# Si-Ngadu (Sistem Pengaduan Terpadu)


## About

Sebuah aplikasi web sebagai sistem pelaporan masalah yang ditemui saat pencacah melakukan pencacahan.

1. Terdapat 3 jenis user, yaitu: Pencacah, Pengawas, Admin  
2. Pencacah dapat melaporkan masalah yang dialami saat pencacahan  
3. Pengawas dapat melihat dan meng-update laporan masalah yang dibuat oleh pencacah yang dialaminya, pengawas dapat meng-update status laporan menjadi 'selesai' jika masalah telah diselesaikan  
4. Admin dapat membuat akun dan manajemen jenis masalah  


## Deploy

1. Buat file jar

```sh
mvn clean package
```

2. Run

```sh
docker compose up -d
```

3. Login dengan email 'root@localhost' dan password 'root'