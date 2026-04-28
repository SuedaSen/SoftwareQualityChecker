# Sistem Raporu — MLV (Mechanical Lung Ventilator) Dataset

Bu rapor, MLV gereksinim dosyası üzerinden üretilen çıktıları ve **F1 değerlendirmesini** özetler.

## Kısa sözlük (bu rapordaki terimler)

- F1 (Precision ve Recall’un harmonik ortalaması; FP ve FN dengesini ölçer).
- TP/FP/FN/TN (confusion matrix sayımları: doğru/yanlış positive/negative).
- Precision (tahmin edilen positive’ların ne kadarı doğru).
- Recall (gold positive’ların ne kadarı yakalandı).
- Threshold / eşik \(\tau\) (skoru ikili tahmine çeviren sınır değeri).
- Gold dataset (değerlendirme için insan tarafından etiketlenmiş “ground truth” veri).
- Threshold sweep (çoklu \(\tau\) denemesi ile F1’i ençoklayan \(\tau^\*\) değerini bulma).
- Skipped/atlanmış satır (gold etiketi olmayan satırlar; F1’e dahil edilmez ama bulgularda görünür).

## 1) Kullanılan girdiler

- **Analiz girdisi (CSV)**: `data/external/mlv/mlv_requirements_v1_5.csv`
  - Kaynak: MLV SRS dokümanından çıkarılan gereksinimler (Mahbub ve ark., 2024 vaka çalışması materyali).
- **Gold etiket (CSV, örnek)**: `data/external/mlv/mlv_gold_ambiguity_sample.csv`
  - Boyut: **20** etiketli ID (demo amaçlı, küçük bir manuel etiketli alt küme).

## 2) Koşu ayarları

- Eşik \(\tau\): **0.35**
- Kural: `score >= τ` ise ambiguous tahmini yapılır

## 3) Değerlendirme sonuçları (Belirsizlik)

`reports/mlv_report.md` içinden:

- **TP/FP/FN/TN**: **3 / 6 / 4 / 7**
- **Precision**: **0.333**
- **Recall**: **0.429**
- **F1**: **0.375**
- **Değerlendirilen satır**: **20**
- **Atlanan satır (skipped)**: **469**

“Skipped” neden büyük?
- Değerlendirme **ID ile** eşleştirir. `mlv_gold_ambiguity_sample.csv` içindeki 20 ID dışında kalan MLV gereksinimleri F1 için **atlanır** (ama bulgu tablolarında listelenmeye devam eder).

## 4) TP/FP/FN/TN nasıl oluştu? (ID bazında)

\(\tau=0.35\) için:

| ID | Skor \(s\) | Tahmin \(\hat{y}\) | Gold \(y\) | Sonuç |
|---|---:|---:|---:|---|
| AL.5 | 0.35 | 1 | 1 | TP |
| AL.8 | 0.10 | 0 | 0 | TN |
| CONT.38 | 0.25 | 0 | 0 | TN |
| CONT.41.2 | 0.20 | 0 | 1 | FN |
| CONT.46 | 0.00 | 0 | 0 | TN |
| FUN.1 | 0.00 | 0 | 0 | TN |
| FUN.2 | 0.00 | 0 | 0 | TN |
| FUN.22 | 0.30 | 0 | 1 | FN |
| FUN.23 | 0.35 | 1 | 1 | TP |
| FUN.5 | 0.45 | 1 | 0 | FP |
| FUN.5.2 | 0.20 | 0 | 0 | TN |
| FUN.6 | 0.45 | 1 | 0 | FP |
| FUN.7 | 0.10 | 0 | 0 | TN |
| GUI.2 | 0.45 | 1 | 1 | TP |
| GUI.3 | 0.35 | 1 | 0 | FP |
| GUI.4 | 0.00 | 0 | 1 | FN |
| GUI.49.1 | 0.20 | 0 | 1 | FN |
| GUI.57 | 0.45 | 1 | 0 | FP |
| GUI.61 | 0.35 | 1 | 0 | FP |
| GUI.62 | 0.35 | 1 | 0 | FP |

Toplam:
- TP = 3 (`AL.5`, `FUN.23`, `GUI.2`)
- FP = 6 (`FUN.5`, `FUN.6`, `GUI.3`, `GUI.57`, `GUI.61`, `GUI.62`)
- FN = 4 (`CONT.41.2`, `FUN.22`, `GUI.4`, `GUI.49.1`)
- TN = 7 (kalan yedi satır)

## 4.1) Yorum (bu değerler ne anlatıyor?)

- **Precision = 0.333 (3/9)**: sistem “ambiguous” dediğinde **her 3 tahminden 1’i doğru**; false positive yüksek (**FP=6**).
- **Recall = 0.429 (3/7)**: gold’da ambiguous olan 7 satırın **3’ünü yakaladı**, **4’ünü kaçırdı** (**FN=4**).
- **F1 = 0.375**: hem FP hem FN yüksek olduğu için dengeli skor düşük kaldı.

Pratik çıkarım:
- \(\tau=0.35\) ile bu alt kümede model, gold etiketlerle iyi hizalanmıyor: bir yandan çok yanlış alarm (FP) üretirken, diğer yandan bazı gerçek ambiguous satırları kaçırıyor (FN).

MLV’de bunun olası nedenleri:
- Mevcut belirsizlik skoru daha çok yüzeysel ipuçlarına dayanıyor (örn. **“may/should”, “as needed”, “immediately”**, belirsiz sıfatlar, zamirler). MLV metinleri ise teknik ve “shall” ağırlıklı; gold belirsizliği bazen bu basit kalıplarla yakalanamayan alan bilgisinden geliyor olabilir.
- MLV CSV çıkarımında **gürültü / doküman kırıntıları** (örn. içindekiler benzeri metinler) bulunabiliyor. Bu durum skorları ve aday üretimini bozabiliyor.
- Buradaki gold, demo amaçlı **küçük bir manuel alt küme (20 satır)**; etiket setini büyütmek/standartlaştırmak sonuçları değiştirebilir.

## 4.2) Opsiyonel sonraki adım: threshold sweep (MLV gold üzerinde en iyi \(\tau\))

Aynı gold alt küme üzerinde \(\tau \in [0,1]\) (örn. 0.05 adım) taraması yapıp şunları raporlayabiliriz:
- F1’i en çok yapan \(\tau^\*\)
- bu \(\tau^\*\) için TP/FP/FN/TN ve Precision/Recall/F1

## 5) Tekrarlanabilir komut

```bash
mvn -q -DskipTests package
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/external/mlv/mlv_requirements_v1_5.csv --format csv --out reports/mlv_report.md --out-lang both --gold data/external/mlv/mlv_gold_ambiguity_sample.csv --threshold 0.35"
```

## 6) Çıktı dosyaları

- English rapor: `reports/mlv_report.md`
- Türkçe rapor: `reports/mlv_report_tr.md`

