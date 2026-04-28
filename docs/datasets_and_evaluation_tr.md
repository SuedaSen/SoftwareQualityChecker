# Datasetler, Değerlendirme ve Tekrarlanabilir Koşular (Adım adım)

Bu doküman, **sıfırdan** başlayarak bir makaledeki dataset’i alıp bu repoda nasıl koşturacağımızı ve **Precision/Recall/F1** raporlarını nasıl üreteceğimizi anlatır.

## 0) Bu repo bugün neyi değerlendirebiliyor?

- **Belirsizlik (tek gereksinim sınıflandırma)**:
  - Araç, her gereksinim için bir belirsizlik **skoru** (`0..1`) ve nedenler üretir.
  - Etiketli bir **gold** dataset verildiğinde, skoru threshold ile etiketleyip **Precision / Recall / F1** hesaplayabiliriz.

- **Tutarsızlık adayları (çiftler)**:
  - Araç, benzerlik + kanıt ile “aday çift” üretir.
  - **Conflict için F1 şu an yok** çünkü bunun için “hangi çift gerçekten conflict?” diye etiketli **pair-level** gold dataset gerekir.

## 1) Genel akış (her dataset için aynı)

### Adım 1 — Dataset’i bul / edin

Her makale için:
- Dataset **public mi** kontrol et.
- Public değilse şu kararlardan birini seç:
  - **public alternatif dataset** kullan, veya
  - küçük bir alt-küme için **manuel gold** oluştur (demo için).

### Adım 2 — Dataset’i bizim gold formatımıza çevir

Belirsizlik (ambiguity) evaluation için CSV formatı:

- `text` (**zorunlu**): gereksinim cümlesi
- `ambiguous` (**zorunlu**): `true/false`, `1/0`, `yes/no`
- `id` (**opsiyonel ama önerilir**): satır kimliği (örn. `R1`)

Örnek (`data/gold_ambiguity.csv`):

```csv
id,text,ambiguous
R1,"The system shall respond within 2 seconds under normal load.",false
R2,"The system should provide fast performance.",true
```

### Adım 3 — Pipeline’ı çalıştır

Input dataset (CSV/TXT) verilir. Pipeline şunları üretir:
- belirsizlik bulguları (skor + gerekçeler)
- tutarsızlık adayları (çiftler + kanıt)

### Adım 4 — Belirsizlik için evaluation (F1)

Tahmin etiketi şu şekilde üretilir:

\[
\text{predicted\_ambiguous} = (\text{score} \ge \text{threshold})
\]

Sonra TP/FP/FN/TN ve Precision / Recall / F1 raporlanır.

### Adım 5 — Raporu İngilizce ve Türkçe üret

CLI şu çıktıları üretebilir:
- İngilizce (`reports/report.md`)
- Türkçe (`reports/report_tr.md`)
- ikisi birlikte

## 2) Tekrarlanabilir komutlar (CLI)

### 2.1 Önce build

```bash
mvn -q -DskipTests package
```

### 2.2 Analiz + F1 evaluation + iki dilli Markdown rapor

```bash
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.md --out-lang both --gold data/gold_ambiguity.csv --threshold 0.50"
```

Çıktılar:
- `reports/report.md` (English)
- `reports/report_tr.md` (Türkçe)

## 3) Makale bazında dataset durumu (linkli)

### Makale A — Wang et al. (2013): Automatic Detection of Ambiguous Terminology for Software Requirements

- **Makale PDF**: `https://www.eecis.udel.edu/~hfang/pubs/nldb13.pdf`
- **Dataset**: 4 gerçek SRS koleksiyonu (PI, PII, PIII, PIV)
- **Public indirme linki**: bulunamadı (makale doğrudan dataset linki vermiyor)
- **Bu repoda nasıl koşarız?**
  - Public alternatif dataset kullan (Makale B / Makale D), veya
  - kendi gereksinimlerinizden küçük bir gold set oluştur (demo)

### Makale B — Mahbub et al. (2024): Can GPT-4 Aid in Detecting…

- **MLV SRS repo**: `https://github.com/foselab/abz2024_casestudy_MLV`
- **Issues / tartışmalar**: `https://github.com/foselab/abz2024_casestudy_MLV/issues`
- **Yazarların pipeline repo’su**: `https://github.com/Taslim-M/GPT4-Requirements-Analysis`
- **Bu repoda nasıl koşarız?**
  - SRS’ten requirements çıkar (PDF/TXT/CSV), sonra
  - `gold_ambiguity.csv` (etiketli) üret (yazar artifact’i varsa oradan; yoksa alt-küme manuel),
  - `--gold` ile F1 raporu üret

### Makale C — Brown (framework): Machine Learning Framework for Identifying Inconsistency in SRD

- **Makale PDF**: `https://www.micsymposium.org/mics2019/wp-content/uploads/2019/05/Framework__for_finding_inconsistency.pdf`
- **Public dataset**: yok
- **Bu repoda nasıl kullanırız?**
  - “dataset koşturma” yerine raporda “yaklaşım/çerçeve” olarak referanslamak daha doğru.

### Makale D — Talha et al. (2025): A Semiautomated Approach for Detecting Ambiguities…

- **Makale DOI**: `https://doi.org/10.1002/smr.70041`
- **Dataset**: 425 functional requirements, 16 domain; ambiguity type etiketleri
- **Dataset indirme**: makale “Appendix A’da datasets and code var” diyor
- **Bu repoda nasıl koşarız?**
  - Appendix A linkini bul → dataset’i indir,
  - `text,ambiguous` gold formatına çevir,
  - CLI ile F1 + EN/TR rapor al

### Makale E — Zhang & Ma (2023): Using ML for automated detection of ambiguity in building requirements

- **Makale PDF**: `https://ec-3.org/publications/conferences/EC32023/papers/EC32023_211.pdf`
- **Dataset**: HBN 00-02 ve 00-03’ten 464 etiketli cümle (237 ambiguous / 227 unambiguous)
- **Public dataset indirme linki**: makalede açık bir “etiketli CSV indir” linki görünmüyor
- **Bu repoda nasıl koşarız?**
  - Etiketli data public değilse F1 için yeniden etiketleme gerekir veya başka public dataset seçilir.

## 4) Threshold seçimi (pratik)

- Başlangıç: `--threshold 0.50` (baseline).
- Daha büyük gold varsa: threshold sweep (0.1..0.9) yapıp en iyi F1’i seç.

## 5) Web UI: “Explain with ChatGPT” özelliği (ne yapıyor?)

Web UI tablolarında:
- Belirsizlik `Reasons` hücresinde **Explain with ChatGPT** linki var.
- Tutarsızlık `Evidence` hücresinde **Explain with ChatGPT** linki var.
- Tıklayınca:
  - prompt panoya kopyalanır
  - ChatGPT yeni sekmede açılır: `https://chat.openai.com/`

Prompt’lar İngilizcedir ve şunları içerir:
- requirement id + text (+ reasons), veya
- left/right id + text (+ similarity, kind, evidence)

