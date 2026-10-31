# CUSTOM CAR OS

Privacy-first Android automotive launcher inspired by Vivid Car Launcher and Tesla's infotainment interface.

## Araç Modeli

- **Model:** Renault Symbol

## Arayüz Tasarımı (Referansla Uyumlu)

Ana dashboard, referans görseldeki split-screen tasarımın geliştirilmiş versiyonudur:

- **Üst çubuk:** Saat, tarih, internet durumu, Bluetooth durumu, pil yüzdesi
- **Sol panel (%60):** Harita görselleştirme, hız gösterge (0 km/h), navigasyon oku, zoom +/-, weather widget (12°, Parçalı Bulutlu), hızlı işlem çipleri (Ses, Ev, Yakıt, Ara)
- **Sağ panel (%40):** Spotify medya kontrolü - albüm sanatı, track adı (Falling - Madeaux), beğen, önceki, oynat/durdur, sonraki, kuyruk, Library sekmeleri (Playlists, Artists, Albums, Podcasts)
- **Alt dock:** Ana Sayfa, Harita, Müzik, Uygulamalar, Bluetooth, Telefon, zaman (12:45), durum simgeleri, menü

## Özellikler

- **Dashboard**: Split-screen (harita + Spotify yan yana), premium Tesla-style arayüz
- **Araç Görselleştirme**: Renault Symbol 2D profil, kapı açılış/kapalama, far açma/kapatma, renk değiştirme, 34ABC567 plakası
- **Yerel Müzik**: Media3 ExoPlayer, MediaSession (kilit ekranı ve Bluetooth medya tuşları), MediaStore tarama (MP3, FLAC, AAC, WAV)
- **Spotify**: Basit medya kontrolü — Spotify uygulaması açılır, oynatma/duraklatma/sonraki/önceki tuşları Android media key olayıyla Spotify'a gönderilir. **API anahtarı gerekmez, ek kütüphane yok.**
- **Bluetooth**: Cihaz eşleştirme, A2DP ses çıkışı (fabrika hoparlörler), bağlantı durumu
- **Çevrimdışı Harita**: Mapsforge harita renderleme + GraphHopper rota motoru arayüzü (UI hazır, native kütüphaneler eklenebilir)
- **Online/Çevrimdışı**: ConnectivityManager ile otomatik internet algılama, graceful degradation
- **Ayarlar**: Tema, birim (metrik/imperial), otomatik başlatma, valet/privacy kilit, ekran zaman aşımı, gizlilik bilgisi
- **Hava Durumu**: Premium widget
- **Önyükleme ile Başlatma**: BOOT_COMPLETED receiver (ayarlardan etkinleştirilebilir)

## Teknoloji Yığını

- Kotlin
- Jetpack Compose + Material 3
- Android Media3 (ExoPlayer, MediaSession)
- Room Database + DataStore
- Hilt Dependency Injection (opsiyonel)
- Coil (image loading)
- Mapsforge (offline maps)
- GraphHopper (offline routing)

## Modüller (13 adet)

`app`, `core`, `design-system`, `dashboard`, `navigation`, `offline-maps`, `music`, `spotify`, `bluetooth`, `vehicle-visualization`, `settings`, `weather`, `storage`

## Gizlilik

- Hiçbir analitik SDK yok
- Hiçbir telemetri yok
- Hiçbir reklam SDK'sı yok
- Hiçbir takip yok
- Tüm veri yerel cihazda kalır (Room + DataStore)
- Spotify çevrimdışı indirmeleri bu uygulama tarafından erişilmez

## GitHub Üzerinden Derleme (Android Studio Gerekmez)

1. [github.com/new](https://github.com/new) adresinde `custom-car-os` repository'si oluştur
2. Projeyi yükle:
   ```bash
   cd "C:\Users\user\Documents\carlauncher"
   git init
   git add .
   git commit -m "Initial commit: Custom Car OS"
   git branch -M main
   git remote add origin https://github.com/KULLANICI_ADIN/custom-car-os.git
   git push -u origin main
   ```
3. GitHub Actions (`.github/workflows/android.yml`) otomatik olarak APK'yı derler
4. Artifacts'tan `custom-car-os-debug-apk` indir

## REA MCP Analizi (İsteğe Bağlı)

Klasörde `jadx-headless-mcp-0.8.0-all.jar` bulundu, ancak REA MCP sunucusu **0.7.1 sürümünü** istiyor. Sürüm uyuşmazlığı nedeniyle şu an REA ile APK dekompile edilemiyor. REA kullanmak istersen `jadx-headless-mcp 0.7.1` JAR dosyasını klasöre ekle ve `REA_JADX_MCP_JAR` ortam değişkenini ayarla.

## Minimum Sürüm

- **minSdk: 21 (Android 5.0 Lollipop)**
- **targetSdk: 34 (Android 14)**

Android 4.x (API 14-15) desteklenmiyor çünkü Jetpack Compose, Material 3, Media3, Room, DataStore hepsi API 21 gerektirir.

## Dosyalar

- `BUILD_INSTRUCTIONS.md` — Derleme rehberi
- `FEATURE_CHECKLIST.md` — Özellik durum listesi
- `COMPATIBILITY.md` — Uyumluluk ve platform sınırları
- `PRIVACY.md` — Gizlilik İlkesi
- `.github/workflows/android.yml` — GitHub Actions CI

## Lisans

Özel / Dahili kullanım.
