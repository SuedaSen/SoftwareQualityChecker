# Sistem Raporu — Software Requirement Quality Checker

Bu doküman, repodaki örnek veri ile sistemin ürettiği çıktıları ve F1 değerlendirme sonuçlarını özetler.

## 1) Kullanılan girdiler

- **Analiz girdisi (CSV)**: `data/sample_requirements.csv`
  - Dosyadaki gereksinim sayısı: **15**
  - ID’ler: `R1`…`R15`
- **Belirsizlik gold etiketleri (CSV)**: `data/gold_ambiguity.csv`
  - Etiketli öğe sayısı: **5** (`R1`…`R5`)

> Not: Gold dosyası 15 satırın sadece 5’ini etiketliyor. Değerlendirmede `id` ile eşleştirme yapıldığı için kalan 10 satır **skipped/atlanmış** sayılır.

## 2) Belirsizlik değerlendirmesi (F1) nasıl hesaplandı?

Her gereksinim \(r\) için sistem bir skor üretir: \(s(r)\in[0,1]\). Bu skor, \(\tau=0.50\) eşiği ile ikili etikete çevrilir:

\[
\hat{y}(r)=1 \text{ eğer } s(r)\ge 0.50,\quad \hat{y}(r)=0 \text{ aksi halde}
\]

Sonra \(\hat{y}(r)\) ile gold etiketi \(y(r)\) karşılaştırılarak:
- TP/FP/FN/TN sayılır
- Precision, Recall, F1 hesaplanır

Formüller:

\[
\text{Precision}=\frac{TP}{TP+FP},\quad
\text{Recall}=\frac{TP}{TP+FN},\quad
\text{F1}=\frac{2\cdot P\cdot R}{P+R}
\]

Kod referansları:
- `src/main/java/com/ser/reqcheck/AmbiguityEvaluator.java`
- `src/main/java/com/ser/reqcheck/EvaluationResult.java`
- `src/main/java/com/ser/reqcheck/Cli.java`

## 3) Belirsizlik değerlendirme sonuçları (CLI raporundan)

Koşu ayarları:
- Eşik \(\tau\): **0.50**
- Değerlendirilen satır: **5**
- Atlanan satır (skipped): **10**

Confusion matrix:
- **TP = 1**
- **FP = 1**
- **FN = 0**
- **TN = 3**

Metrikler:
- **Precision = 0.500**
- **Recall = 1.000**
- **F1 = 0.667**

## 3.1) TP/FP/FN/TN neden 1/1/0/3 çıktı? (ID bazında açıklama)

Gold etiketleri sadece `R1`…`R5` için var. \(\tau=0.50\) eşiği ile `score >= 0.50` ise “ambiguous=1” tahmini yapılır.

`reports/report.md` (skorlar) ve `data/gold_ambiguity.csv` (gold etiket) birlikte bakıldığında değerlendirilen ID’ler:

| ID | Skor \(s\) | Tahmin \(\hat{y}\) (\(\tau=0.50\)) | Gold \(y\) | Sonuç |
|---|---:|---:|---:|---|
| R1 | 0.00 | 0 | 0 | TN |
| R2 | 0.00 | 0 | 0 | TN |
| R3 | 0.60 | 1 | 1 | TP |
| R4 | 0.55 | 1 | 0 | FP |
| R5 | 0.00 | 0 | 0 | TN |

Dolayısıyla toplam:
- \(TP=1\) (sadece `R3`)
- \(FP=1\) (sadece `R4`)
- \(FN=0\) (gold=1 iken tahmin=0 olan durum yok)
- \(TN=3\) (`R1`, `R2`, `R5`)

Demo gold dosyası için önemli not:
- Değerlendirme **ID ile** eşleştirir. Bir ID’nin metni sample ve gold dosyasında farklı olsa bile, metrikler o ID’nin gold etiketine göre hesaplanır.

Manuel doğrulama:
- \(P=1/(1+1)=0.5\)
- \(R=1/(1+0)=1.0\)
- \(F1=2\cdot0.5\cdot1.0/(0.5+1.0)=0.666…\approx 0.667\)

## 3.2) Yorum (bu değerler ne anlatıyor?)

- **Precision = 0.500**: “ambiguous” diye işaretlenenlerin **yarısı doğru** (2 tahminin 1’i doğru).
- **Recall = 1.000**: bu küçük gold sette ambiguous olanı **kaçırmadı** (1/1).
- **F1 = 0.667**: Recall çok yüksek; fakat 1 adet false positive Precision’ı düşürdüğü için F1 orta seviyede kaldı.

Pratik çıkarım:
- \(\tau=0.50\) ile sistem bu küçük gold sette biraz “agresif”: ambiguous olanı yakalıyor ama 1 tane de yanlış alarm veriyor.

Sunum için önemli not:
- Gold set **çok küçük** (sadece 5 etiketli satır). 1 satırın etiketi bile değişse metrikler ciddi değişebilir.

## 3.3) Opsiyonel sonraki adım: threshold sweep (en iyi \(\tau\) değerini bulma)

Daha sağlam bir sonuç için \(\tau \in [0,1]\) aralığında (örn. 0.05 adım) tarama yapıp şunları raporlayabiliriz:
- F1’i en çok yapan \(\tau^\*\)
- bu \(\tau^\*\) için TP/FP/FN/TN ve Precision/Recall/F1

## 4) Sistem çıktıları (araç ne üretti?)

### 4.1 Belirsizlik bulguları (sıralı)

- Üretilen belirsizlik satırı: **15** (`sample_requirements.csv` içindeki her gereksinim için 1 satır)
- Her satır şunları içerir:
  - `id`, `score`, `text`, `reasons`

Tam tablo:
- `reports/report.md` (English)
- `reports/report_tr.md` (Türkçe)

### 4.2 Tutarsızlık adayları (çift bazlı)

- Üretilen candidate satırı: **131**
- Her aday şunları içerir:
  - `leftId`, `rightId`, `similarity`, `kind`, `evidence`

Önemli yorum:
- Bunlar “kesin tutarsızlık” değil, **insan incelemesi için aday** çiftlerdir.
- Aynı çift için birden fazla satır olabilir (örn. aynı `(left,right)` için hem `numeric_conflict` hem `high_similarity_review`).

## 5) Tekrarlanabilir komut

```bash
mvn -q -DskipTests package
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.md --out-lang both --gold data/gold_ambiguity.csv --threshold 0.50"
```

