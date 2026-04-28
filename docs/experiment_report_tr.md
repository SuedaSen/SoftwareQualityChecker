# Deney Raporu — Gereksinim Kalite Kontrolü (Belirsizlik F1)

Bu rapor, bu repoda **belirsizlik (ambiguity)** değerlendirmesi için uçtan uca bir deneyi; **formüller**, **varsayımlar** ve **tekrarlanabilir komutlarla** belgeler.

Kısa sözlük (1 cümle):
- **Gold dataset** (tahminleri ölçmek için insan tarafından etiketlenmiş “ground truth” veri).
- **Threshold / eşik \(\tau\)** (skoru \(s(r)\) ikili etikete çeviren sınır değer).
- **TP/FP/FN/TN** (confusion matrix sayımları: doğru/yanlış positive/negative).
- **Precision** (tahmin edilen positive’ların ne kadarı doğru).
- **Recall** (gold positive’ların ne kadarı yakalandı).
- **F1-score** (Precision ve Recall’un harmonik ortalaması; FP ve FN dengesini ölçer).

> Kapsam: Etiketli gold dosyası ile **belirsizlik** için *binary classification* değerlendirmesi.  
> Kapsam dışı (şimdilik): çift bazlı **tutarsızlık/conflict** değerlendirmesi (pair-level gold gerekir).

---

## 1) Amaç

Aracın belirsizlik tespit performansını şu metriklerle ölçmek:

- Precision
- Recall
- F1-score
- Confusion matrix (TP/FP/FN/TN)

Bunu, aracın ürettiği skorları **gold (etiketli) dataset** ile karşılaştırarak yapıyoruz.

---

## 2) Test edilen sistem (System under test)

### 2.1 Belirsizlik skoru

Her gereksinim cümlesi \(r\) için sistem şunları üretir:

- belirsizlik skoru \(s(r)\in[0,1]\)
- insan tarafından okunabilir gerekçeler (örn. weak modal verbs, vague adjectives)

Skorlama `AmbiguityAnalyzer` içinde **kural tabanlı heuristics** ile yapılır.

### 2.2 Skordan etikete geçiş (threshold)

Skoru bir threshold \(\tau\) ile ikili etikete çeviririz:

\[
\hat{y}(r) =
\begin{cases}
1 & \text{eğer } s(r)\ge\tau \\
0 & \text{aksi halde}
\end{cases}
\]

Burada:
- \(1\) = “ambiguous / belirsiz”
- \(0\) = “not ambiguous / belirsiz değil”

---

## 3) Dataset(ler)

### 3.1 Gold dataset formatı

Gold dataset CSV formatı:

- `text` (zorunlu)
- `ambiguous` (zorunlu; kabul edilenler: `true/false`, `1/0`, `yes/no`)
- `id` (opsiyonel ama önerilir)

Örnek:

```csv
id,text,ambiguous
R1,"The system shall respond within 2 seconds under normal load.",false
R2,"The system should provide fast performance.",true
```

### 3.2 Repodaki demo gold dosyası

Repo içinde küçük bir demo gold dosyası var:

- `data/gold_ambiguity.csv`

Önemli: aracın örnek input dosyası (`data/sample_requirements.csv`) **gold’dan daha fazla satır** içeriyor.  
Bu yüzden evaluation **id ile eşleşen satırlarda** yapılır; eşleşmeyenler **skipped** sayılır.

### 3.3 Seçilen makalelerde dataset durumu (ve indirdiğimiz şey)

Birçok makale dataset’ten bahsediyor ancak **indirilebilir, etiketli (gold) dosya** yayınlamıyor. Özet:

- **Wang 2013 (PI–PIV)**: datasetler anlatılıyor ama download link bulunamadı (public değil).
- **Brown (framework)**: dataset linki yok (yaklaşım/çerçeve anlatımı).
- **Zhang & Ma 2023 (HBN 464)**: etiketleme anlatılıyor ama etiketli CSV indirme linki makalede görünmüyor.
- **Talha 2025**: “Appendix A’da dataset+code var” diyor; Appendix A download linki ayrıca bulunup doğrulanmalı.
- **Mahbub 2024 (MLV)**: dataset public (SRS PDF’leri var) ama etiketli gold olarak verilmemiş; F1 için etiket gerekir.

#### Somut indirme (Mahbub 2024 / MLV)

MLV SRS’i bu repoda kullanılabilir hale getirmek için şunları indirdik/hazırladık:

- **İndirilen SRS PDF (v1.5)**:
  - `data/external/mlv/Mechanical_Lung_Ventilator_1_5.pdf`
  - Kaynak: `https://raw.githubusercontent.com/foselab/abz2024_casestudy_MLV/main/Mechanical_Lung_Ventilator%201_5.pdf`
- **PDF’ten requirement CSV çıkarımı (etiketsiz)**:
  - `data/external/mlv/mlv_requirements_v1_5.csv`
  - Extractor: `src/main/java/com/ser/reqcheck/MlvRequirementsExtractor.java`

Önemli: Bu çıkarım dosyası sadece `id,text` içerir. MLV üstünde F1 için `ambiguous` etiketi ayrıca eklenmelidir (manuel / yarı otomatik gold üretimi).

---

## 4) Metrikler (formüllü)

### 4.0 Neden bu formüller? (neyi ölçüyoruz?)

Bu deneyde belirsizlik tespiti bir **ikili sınıflandırma (binary classification)** problemi olarak ele alınır:

- gold etiket \(y(r)\in\{0,1\}\)
- tahmin etiketi \(\hat{y}(r)\in\{0,1\}\) (skor + threshold ile üretilir)

Precision/Recall/F1 bu problem için standart metriklerdir çünkü:
- **Precision**, “fazla işaretleme”yi (false alarm) cezalandırır.
- **Recall**, belirsiz gereksinimleri kaçırmayı cezalandırır.
- **F1**, ikisi arasındaki dengeyi harmonik ortalama ile özetler.

### 4.1 Confusion matrix

Değerlendirilen her gereksinim \(r\) için:

- **TP**: \(\hat{y}(r)=1\) ve \(y(r)=1\)
- **FP**: \(\hat{y}(r)=1\) ve \(y(r)=0\)
- **FN**: \(\hat{y}(r)=0\) ve \(y(r)=1\)
- **TN**: \(\hat{y}(r)=0\) ve \(y(r)=0\)

### 4.2 Precision

\[
\text{Precision} = \frac{TP}{TP+FP}
\]

Yorum: “Belirsiz diye işaretlediklerimizin kaçı gerçekten belirsiz?”

### 4.3 Recall

\[
\text{Recall} = \frac{TP}{TP+FN}
\]

Yorum: “Gerçekten belirsiz olanların kaçı yakalandı?”

### 4.4 F1-score

\[
\text{F1} = \frac{2\cdot \text{Precision}\cdot \text{Recall}}{\text{Precision}+\text{Recall}}
\]

Yorum: precision ve recall’u dengeleyen harmonik ortalama.

---

## 5) Deney prosedürü (adım adım)

1. Input requirements CSV/TXT’den yüklenir.
2. Pipeline çalıştırılır:
   - her satır için belirsizlik skoru \(s(r)\) + gerekçeler hesaplanır
   - tutarsızlık adayları da hesaplanır (bu deneyde evaluate edilmiyor)
3. Gold dataset `--gold` ile yüklenir.
4. Tahmin satırları gold ile **id üzerinden eşleştirilir**:
   - gold’da olmayan id’ler **skipped**
5. Her değerlendirilen satır için:
   - threshold ile \(\hat{y}(r)\) üretilir
   - TP/FP/FN/TN güncellenir
6. Precision/Recall/F1 formüllerle hesaplanır.
7. EN/TR Markdown raporları yazılır.

### 5.1 TP/FP/FN/TN sayımları kodda nasıl üretildi?

Uygulama tanımları birebir şu şekilde uygular:

- Threshold kuralı:
  - `predAmb = p.score() >= threshold`
- Gold etiketler CSV’den yüklenir ve **id üzerinden eşleştirilir**.
- Gold ile eşleşen her satır için sayımlar güncellenir:
  - `predAmb && goldAmb` → TP++
  - `predAmb` → FP++
  - `goldAmb` → FN++
  - aksi halde → TN++

Kod konumları:
- Evaluation döngüsü: `src/main/java/com/ser/reqcheck/AmbiguityEvaluator.java`
- Metrik formülleri: `src/main/java/com/ser/reqcheck/EvaluationResult.java`
- CLI akışı (args → evaluation → rapor): `src/main/java/com/ser/reqcheck/Cli.java`

---

## 6) Tekrarlanabilirlik (komutlar)

### 6.1 Build

```bash
mvn -q -DskipTests package
```

### 6.2 Deneyi koştur (analiz + evaluation + iki dilli rapor)

```bash
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.md --out-lang both --gold data/gold_ambiguity.csv --threshold 0.50"
```

Çıktılar:
- `reports/report.md` (English)
- `reports/report_tr.md` (Türkçe)

---

## 7) Sonuçlar (örnek koşu)

Bu repodaki örnek koşuda:

- threshold \(\tau = 0.50\)
- gold = `data/gold_ambiguity.csv`

Rapor şu değerleri verdi:

- Precision = 0.500
- Recall = 1.000
- F1 = 0.667

Confusion matrix:

- TP = 1
- FP = 1
- FN = 0
- TN = 3

Ayrıca:

- Evaluated rows = 5
- Skipped rows = 10 (input’ta var ama gold’da yok)

### 7.1 Sayımlardan F1’i doğrulama

Precision:
\[
\frac{TP}{TP+FP}=\frac{1}{1+1}=0.5
\]

Recall:
\[
\frac{TP}{TP+FN}=\frac{1}{1+0}=1.0
\]

F1:
\[
\frac{2\cdot 0.5\cdot 1.0}{0.5+1.0}=\frac{1}{1.5}=0.666\ldots \approx 0.667
\]

---

## 8) Geçerlilik tehditleri / limitler

- **Gold küçük**: bu demo gold istatistiksel olarak anlamlı değil.
- **Threshold hassas**: F1, \(\tau\)’ya bağlı; büyük gold varsa threshold sweep önerilir.
- **Etiket tanımı farklı olabilir**: paper’daki “ambiguity” tanımı bizim heuristic tanımımızla birebir örtüşmeyebilir.
- **Tutarsızlık evaluation yok**: conflict için F1, pair-level gold gerektirir.

---

## 9) Sonraki adımlar

- Daha büyük public gold dataset eklemek (örn. Talha et al. 2025 Appendix A’dan indirilebilirse).
- Otomatik **threshold sweep** ile en iyi F1 veren \(\tau\)’yu seçmek.
- Conflict için **pair-labeled gold format** ekleyip conflict Precision/Recall/F1 hesaplamak.

