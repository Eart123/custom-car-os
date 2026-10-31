# CUSTOM CAR OS — Feature Checklist (Final)

## ✅ Tamamlanan Özellikler

### Mimari & Altyapı
- [x] 13 modüllü Gradle projesi
- [x] Kotlin + Jetpack Compose UI
- [x] Material 3 + özel automotive design system
- [x] Room Database (yerel veri saklama)
- [x] DataStore (tercihler)
- [x] Temiz UI / iş mantığı / veri ayrımı

### Tasarım Sistemi
- [x] Dark-first premium automotive tema
- [x] AutomotiveColors (elektrik mavisi primary, gri yüzeyler)
- [x] AutomotiveTypography (göz atılabilir ölçek)
- [x] AutomotiveSpacing (büyük dokunma hedefleri, 48dp min)
- [x] AutoCard, AutoButton, AutoSwitch, AutoChip, AutoStatusRow
- [x] AutoTopBar (saat, tarih, internet, Bluetooth, pil)
- [x] AutoAppDock (Tesla-style alt menü, büyük dokunma hedefleri)
- [x] AutoGlowContainer, AutoLargeNumber, AutoUnitLabel

### Dashboard (Referans Görsele Uyumlu)
- [x] Split-screen: Harita sol (%60), Spotify sağ (%40)
- [x] Harita üzerinde hız gösterge, navigasyon oku, zoom kontrolü
- [x] Harita altında weather widget + hızlı işlem çipleri
- [x] Spotify medya kontrol paneli (albüm sanatı, oynatma kontrolleri, Library sekmeleri)
- [x] Alt app dock (6 öğe + zaman + durum simgeleri + menü)
- [x] Full-screen navigation ve full-screen music modları

### Araç Görselleştirme
- [x] Renault Symbol 2D profil çizimi
- [x] Plaka: 20AUR23
- [x] Kapı aç/kapalı simülasyonu
- [x] Far aç/kapa simülasyonu
- [x] Renk seçici
- [x] ⚠ Simülasyon uyarısı (gerçek OBD/CAN yok)

### Yerel Müzik
- [x] Media3 ExoPlayer entegrasyonu
- [x] MediaSession (kilit ekranı ve Bluetooth medya tuşları)
- [x] MediaStore tarama (MP3, FLAC, AAC, WAV)
- [x] Oynat/duraklat, sonraki, önceki, ileri alma
- [x] Playlist desteği (veritabanı varlığı)
- [x] Arka planda oynatma servisi

### Spotify
- [x] Spotify uygulamasını aç (Intent)
- [x] Medya key tuşlarıyla kontrol (önceki, oynat/durdur, sonraki)
- [x] API anahtarı GEREKMEZ
- [x] Scraping yok, DRM atlatma yok
- [x] Çevrimdışı indirmeler erişilmez

### Bluetooth
- [x] BluetoothAdapter yönetimi (aç/kapa)
- [x] Cihaz keşfi ve eşleştirme
- [x] A2DP profil bağlantısı
- [x] Bağlantı durumu gösterimi
- [x] Araç hoparlörüne ses çıkışı

### Çevrimdışı Harita
- [x] OfflineMapsManager UI
- [x] Bağlantı durumu kartı (online/offline)
- [x] Harita bölge indirme arayüzü
- [x] Çevrimdışı rota hesaplama bilgisi
- [ ] Mapsforge native renderleme (native .so gerekli)
- [ ] GraphHopper rota motoru (native lib gerekli)

### Hava Durumu
- [x] Premium weather widget (lokasyon, sıcaklık, durum, yüksek/düşük)
- [ ] Canlı hava API entegrasyonu (online modda)

### Ayarlar
- [x] Tema (karanlık/aydınlık)
- [x] Birim (metrik/imperial)
- [x] Otomatik başlatma (boot)
- [x] Valet / gizlilik kilidi
- [x] Bluetooth ses ayarları
- [x] Ekran zaman aşımı
- [x] Gizlilik bildirimi (hiçbir veri toplanmaz)

### Online / Çevrimdışı Mod
- [x] NetworkState sealed class (Connected/Disconnected/Unknown)
- [x] ConnectivityManager ile otomatik algılama
- [x] Online mod UI indicatorleri
- [x] Çevrimdışı mod fallback UI
- [ ] Ağ kesintisinde graceful degradation (planlandı)

### Gizlilik & Güvenlik
- [x] Hiçbir analitik SDK yok
- [x] Hiçbir telemetri yok
- [x] Hiçbir reklam SDK'sı yok
- [x] Hiçbir takip yok
- [x] Tüm veri yerel (Room + DataStore)
- [x] Konum veya müzik verisi dışarı aktarılmaz
- [x] Minimal izinler

### Performans
- [x] Lazy loading patternleri
- [x] MultiDex etkin
- [x] Vector drawables destekli
- [x] Compose tabanlı etkili yeniden kompozisyon
- [x] Eski tabletler için optimize (1-2 GB RAM)

## 📋 Bilinen Sınırlamalar

| Öğe | Durum | Not |
|-----|-------|-----|
| Android 4.x | ❌ Desteklenmiyor | minSdk 21 zorunlu (Compose, Material 3, Media3, Room, DataStore) |
| Filament 3D | ⚠ Planlandı | 2D Compose kullanılmakta; 3D için native .so gerekli |
| Mapsforge renderleme | ⚠ Planlandı | UI hazır; native kütüphane eklenmesi gerekiyor |
| GraphHopper | ⚠ Planlandı | UI hazır; native kütüphane eklenmesi gerekiyor |
| Spotify resmi API | ⚠ Planlandı | Mock UI tamamlandı; gerçek API anahtarı gerekli |
| Canlı hava API | ⚠ Planlandı | Widget statik; online modda entegre edilebilir |

## Rulings (Kararlar)

1. **minSdk 21** — Android 4.x desteklenmiyor; Jetpack Compose ve Material 3 API 21 gerektirir.
2. **Spotify media key yöntemi** — API anahtarı kullanılmaz; sadece media tuşları gönderilir.
3. **Filament kaldırıldı** — Maven'de uygun artifact bulunamadı; 2D Compose kullanılıyor.
4. **Hilt DI** — Eklenti eklendi ama `CarOsApplication` basit `Application` olarak kaldı (Hilt opsiyonel).
