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

