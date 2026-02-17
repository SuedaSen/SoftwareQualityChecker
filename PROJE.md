# SER Gereksinim Kalite Kontrolü — Proje Açıklaması

## Proje adı ve kapsam

**SER (Software Engineering Requirements) Requirement Checker**  
Yazılım gereksinim dokümanlarındaki **belirsizlik** ve **tutarsızlık** adaylarını tespit eden bir uygulama. Java ile yazılmıştır; web arayüzü ve komut satırı (CLI) destekler.

---

## Amaçlar

1. **Belirsiz ifadeleri işaretlemek**  
   Gereksinim cümlelerinde “shall / should / may”, sayısal belirsizlikler ve zayıf ifadeler taranır. Her gereksinim için bir belirsizlik skoru ve gerekçe üretilir; insan incelemesi için öncelik verilebilir.

2. **Tutarsızlık / çakışma adaylarını bulmak**  
   Birbiriyle yüksek benzerlikte ama birbirine zıt veya farklı sayılar içeren gereksinim çiftleri aday olarak listelenir (örn. “2 saniye” vs “5 saniye”, “şifre sıfırlama e-posta ile” vs “e-posta ile sıfırlama yok”). Nihai karar yine insana bırakılır.

3. **Pratik kullanım**  
   Kullanıcı gereksinimleri metin olarak yapıştırabilir veya CSV, TXT, DOC, DOCX, PDF dosyası yükleyebilir; sonuçlar web arayüzünde veya CLI raporunda görüntülenir.

---

## Ne yapıldı? (Özellikler)

### Web arayüzünde ML nasıl anlaşılır?

- Sayfa açıldığında veya analiz sonrası **üstte bir etiket** vardır:
  - **“Bu analizde benzerlik: ML (kelime vektörü) ile hesaplanıyor”** (yeşilimsi) → ML kullanılıyor.
  - **“Bu analizde benzerlik: TF-IDF ile hesaplanıyor (ML yedek)”** (gri) → O an yedek yöntem kullanılıyor.
- Ayrıca **“ML (Makine Öğrenmesi) nedir? — Sıfırdan kısa açıklama”** bölümünde ML’in ne olduğu ve bu projede nerede kullanıldığı sade bir dille anlatılır.

### Analiz tarafı

- **Belirsizlik analizi (kural tabanlı)**  
  - Modallar (shall, should, may), sayısal ifadeler, zayıf sözcükler için kurallar.  
  - Çıktı: Her gereksinim için skor ve gerekçe listesi.

- **Tutarsızlık adayları**  
  - Gereksinimler arası **benzerlik**: varsayılan olarak **ML (kelime vektörü)** ile; kelime vektörü yoksa **TF-IDF** ile.  
  - Ek kurallar: Olumsuzluk farkı (biri “shall not”, diğeri “shall”), farklı yüzde/süre sayıları.  
  - Çıktı: Sol/Sağ gereksinim ID’leri, benzerlik, tür (negation_conflict, numeric_conflict, high_similarity_review), kanıt metni.

### Girdi / çıktı

- **Web arayüzü (Spring Boot + Thymeleaf)**  
  - Ana sayfa: Başlık, “Proje hakkında” açıklaması, girdi alanı.  
  - Girdi: Metin alanı (satır satır gereksinim) veya dosya yükleme (sürükle-bırak).  
  - Desteklenen dosya formatları: CSV (id, text), TXT (satır = gereksinim), DOC, DOCX, PDF (paragraf/satır = gereksinim).  
  - Çıktı: İki tablo — “Belirsizlik bulguları” ve “Tutarsızlık adayları”.  
  - **Sonuçları CSV olarak indir:** “Sonuçları CSV olarak indir” butonu ile her iki tablo tek bir CSV dosyasında indirilir (Excel veya metin editöründe açılabilir).  
  - Footer: Geliştirici isimleri (Sueda Sen, Mehmet Aksit).

- **CLI**  
  - Dosyadan (CSV/TXT) okuyup Markdown veya JSON rapor üretir.

### Teknik bileşenler

- **Kural tabanlı:** `AmbiguityAnalyzer`, `ConflictFinder` içindeki olumsuzluk ve sayı kuralları.  
- **İstatistik / ML:**  
  - `TfIdfCosine`: TF-IDF vektörleri ve kosinüs benzerliği (yedek).  
  - `WordVectorStore` + `EmbeddingSimilarity`: GloVe tarzı kelime vektörleri, cümle = kelime vektörleri ortalaması, L2 normalize, kosinüs benzerliği (varsayılan).  
- **Dosya okuma:** `RequirementsLoader` — CSV (OpenCSV), TXT, DOC/DOCX (Apache POI), PDF (PDFBox).

---

## Mimari (kısa)

- **Girdi:** Metin veya dosya → `RequirementsLoader` → `List<Requirement>`.  
- **Analiz:** `Pipeline.analyze(requirements)`  
  - Belirsizlik: `AmbiguityAnalyzer` → `List<AmbiguityRow>`.  
  - Benzerlik matrisi: `EmbeddingSimilarity` (vektör varsa) veya `TfIdfCosine`.  
  - Tutarsızlık: `ConflictFinder.findConflicts(..., similarityMatrix)` → `List<ConflictCandidate>`.  
- **Çıktı:** `AnalysisResult` → web için `ViewResult` (Thymeleaf) veya CLI için `ReportWriter`.

---

## Kullanılan teknikler

| Bileşen            | Teknik / yöntem |
|--------------------|------------------|
| Belirsizlik        | Rule-based (modallar, sayılar, zayıf ifadeler) |
| Benzerlik (varsayılan) | ML: kelime vektörü (GloVe tarzı) → cümle ortalaması → kosinüs benzerliği |
| Benzerlik (yedek)  | TF-IDF + kosinüs benzerliği |
| Tutarsızlık kuralları | Olumsuzluk desenleri, yüzde/süre çıkarımı, yüksek benzerlik eşiği |
| Web                | Spring Boot, Thymeleaf, multipart dosya yükleme |
| Dosya formatları   | OpenCSV, Apache POI (DOC/DOCX), PDFBox (PDF) |

---

## Nasıl çalıştırılır?

- **Web:**  
  `mvn spring-boot:run`  
  Tarayıcıda: **http://localhost:8080**

- **CLI (örnek):**  
  `mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.md"`

- **JAR:**  
  `mvn -q package` → `java -jar target/reqcheck-1.0.0.jar` (web arayüzü 8080’de açılır).

---

## Özet

Proje, gereksinim kalitesini artırmak için **belirsizlik** (kural tabanlı) ve **tutarsızlık** (ML benzerlik + kurallar) tespiti yapar; **web arayüzü** ve **CLI** ile kullanılır. Tutarsızlık tarafında ML (kelime vektörü) kullanımı tercih edilir, vektör dosyası yoksa TF-IDF ile devam edilir.
