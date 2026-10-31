# CUSTOM CAR OS — GitHub Derleme Rehberi (Android Studio Gerekmez)

## Ön Koşullar (GitHub Hesabı Haricinde Lokalde Hiçbir Şey Gerekmez)

GitHub Actions cloud'da derleme yapar. Lokalde sadece Git ve bir metin editörü yeterlidir.

## Adımlar

### 1. GitHub Repository Oluştur

[github.com/new](https://github.com/new) adresinden `custom-car-os` adlı yeni bir repository oluştur.

### 2. Projeyi Yükle

Windows PowerShell'de:

```powershell
cd "C:\Users\user\Documents\carlauncher"
git init
git add .
git commit -m "Initial commit: Custom Car OS launcher"
git branch -M main
git remote add origin https://github.com/KULLANICI_ADINIZ/custom-car-os.git
git push -u origin main
```

### 3. GitHub Actions Derlemesini Bekle

Push yaptıktan sonra GitHub repository'nin **Actions** sekmesine git. `Android CI` workflow'u otomatik başlayacak ve `app-debug.apk` dosyasını üretecektir.

### 4. APK'yı İndir

Actions çalıştığında **Artifacts** bölümünden `custom-car-os-debug-apk` artifact'ını indir. APK boyutu yaklaşık 15-40 MB arası olacaktır (birkaç GB'ı aşmaz).

### 5. Cihaza Yükle

```bash
adb install app-debug.apk
```

(ADB yoksa [Android Platform-Tools](https://developer.android.com/tools/releases/platform-tools) indir)

## Notlar

- `local.properties` dosyası repoya **eklenmez** (`.gitignore`'de hariç tutulmuştur).
- GitHub Actions otomatik olarak Android SDK'yı kurar.
- JDK 17 Temurin kullanılır.
- Derlenen APK 7 gün boyunca artifact olarak saklanır.
- Spotify entegrasyonu **API anahtarı gerektirmez** — sadece Spotify uygulamasını açar ve media key tuşları gönderir.

## Eksik / İleride Eklenecek Özellikler

| Özellik | Durum |
|---------|-------|
| Filament 3D araç motoru | Planlandı (native .so gerektirir) |
| Mapsforge offline harita renderleme | UI hazır, native lib eksik |
| GraphHopper rota motoru | UI hazır, native lib eksik |
| Spotify media key kontrolü | ✅ Tamamlandı (API anahtarı yok) |
| Otomatik ConnectivityManager algılama | Sınıf yazıldı, entegre edilecek |

## Sorun Giderme

**Build hatası alırsam ne yaparım?**
- GitHub Actions logs'unu kontrol et. Genellikle eksik SDK veya dependency hatasıdır.
- `libs.versions.toml` dosyasındaki sürümleri kontrol et.

**APK çok büyükse?**
- Release modunda R8/minifyEnabled etkinleştirilebilir (`app/build.gradle.kts`).
- Gereksiz native .so dosyaları çıkarılabilir.
