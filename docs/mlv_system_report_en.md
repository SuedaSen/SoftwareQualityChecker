# System Report — MLV (Mechanical Lung Ventilator) Dataset

This report summarizes the outputs and **F1 evaluation** produced on the extracted MLV requirements file.

## 1) Inputs used

- **Analysis input (CSV)**: `data/external/mlv/mlv_requirements_v1_5.csv`
  - Source: extracted from the MLV SRS (Mahbub et al., 2024 case study material).
- **Gold labels (CSV, sample)**: `data/external/mlv/mlv_gold_ambiguity_sample.csv`
  - Size: **20** labeled IDs (a small manually labeled subset used for demo evaluation).

## 2) Run configuration

- Threshold \(\tau\): **0.35**
- Rule: predict ambiguous if `score >= τ`

## 3) Evaluation results (Ambiguity)

From `reports/mlv_report.md`:

- **TP/FP/FN/TN**: **3 / 6 / 4 / 7**
- **Precision**: **0.333**
- **Recall**: **0.429**
- **F1**: **0.375**
- **Evaluated rows**: **20**
- **Skipped rows**: **469**

Why “skipped” is large:
- The evaluation matches by **ID**. Only the 20 IDs present in `mlv_gold_ambiguity_sample.csv` are evaluated; all other requirements in the MLV input are **skipped** for F1 (still included in the findings tables).

## 4) Where TP/FP/FN/TN came from (ID-level breakdown)

At \(\tau=0.35\):

| ID | Score \(s\) | Pred \(\hat{y}\) | Gold \(y\) | Outcome |
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

Totals:
- TP = 3 (`AL.5`, `FUN.23`, `GUI.2`)
- FP = 6 (`FUN.5`, `FUN.6`, `GUI.3`, `GUI.57`, `GUI.61`, `GUI.62`)
- FN = 4 (`CONT.41.2`, `FUN.22`, `GUI.4`, `GUI.49.1`)
- TN = 7 (the remaining seven)

## 4.1) Interpretation (what these values mean)

- **Precision = 0.333 (3/9)**: when the system predicted “ambiguous”, it was correct **1 out of 3 times**; false positives are relatively high (**FP=6**).
- **Recall = 0.429 (3/7)**: the system found **3 of 7** gold-ambiguous items; it missed **4** (**FN=4**).
- **F1 = 0.375**: both FP and FN are substantial, so the balanced score is low.

Practical takeaway:
- With \(\tau=0.35\), the detector is not reliably aligned with the gold labels on this subset: it raises many ambiguity flags that the gold marks as non-ambiguous (FP), while also missing several gold-ambiguous ones (FN).

Why this can happen on MLV:
- The current ambiguity scoring relies heavily on surface cues (e.g., **“may/should”, “as needed”, “immediately”**, vague adjectives, pronouns). Many MLV requirements are technical and “shall”-style; gold ambiguity may come from domain nuance not captured by these heuristics.
- The extracted MLV CSV contains some **noise / document fragments** (e.g., table-of-contents-like text). These can distort both scores and downstream candidate generation.
- Gold here is a **small manually labeled subset (20 rows)** for demonstration; expanding/standardizing labels usually changes the picture.

## 4.2) Optional next step: threshold sweep (find best \(\tau\) on MLV gold)

We can run a sweep over \(\tau \in [0,1]\) (e.g., step 0.05) on the same gold subset and report:
- the \(\tau^\*\) that maximizes F1
- the corresponding TP/FP/FN/TN and Precision/Recall/F1

## 5) Reproducibility command

```bash
mvn -q -DskipTests package
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/external/mlv/mlv_requirements_v1_5.csv --format csv --out reports/mlv_report.md --out-lang both --gold data/external/mlv/mlv_gold_ambiguity_sample.csv --threshold 0.35"
```

## 6) Output files

- English report: `reports/mlv_report.md`
- Turkish report: `reports/mlv_report_tr.md`

