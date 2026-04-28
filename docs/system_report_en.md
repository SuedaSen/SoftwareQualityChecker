# System Report — Software Requirement Quality Checker

This document summarizes the system outputs and evaluation results produced from the repository’s sample input and gold labels.

## Quick glossary (terms used in this report)

- F1 (harmonic mean of Precision and Recall; balances false positives and false negatives).
- TP/FP/FN/TN (counts from the confusion matrix: true/false positives/negatives).
- Precision (fraction of predicted positives that are correct).
- Recall (fraction of gold positives that were found).
- Threshold \(\tau\) (cutoff that turns a score into a binary prediction).
- Gold dataset (human-labeled ground truth used for evaluation).
- Threshold sweep (trying many \(\tau\) values to find \(\tau^\*\) that maximizes F1).

## 1) Inputs used

- **Analysis input (CSV)**: `data/sample_requirements.csv`
  - Requirements in file: **15**
  - IDs: `R1`…`R15`
- **Gold labels for ambiguity (CSV)**: `data/gold_ambiguity.csv`
  - Labeled items: **5** (`R1`…`R5`)

> Note: The gold file only labels 5 of the 15 sample requirements. During evaluation we match by `id`, so the other 10 items are **skipped**.

## 2) How ambiguity evaluation was computed

For each requirement \(r\), the system outputs a score \(s(r)\in[0,1]\). We convert it to a binary prediction using threshold \(\tau=0.50\):

\[
\hat{y}(r)=1 \text{ if } s(r)\ge 0.50,\quad \hat{y}(r)=0 \text{ otherwise}
\]

We then compare \(\hat{y}(r)\) to the gold label \(y(r)\) and count:
- TP/FP/FN/TN
- Precision, Recall, F1

Formulas:

\[
\text{Precision}=\frac{TP}{TP+FP},\quad
\text{Recall}=\frac{TP}{TP+FN},\quad
\text{F1}=\frac{2\cdot P\cdot R}{P+R}
\]

Implementation references:
- `src/main/java/com/ser/reqcheck/AmbiguityEvaluator.java`
- `src/main/java/com/ser/reqcheck/EvaluationResult.java`
- `src/main/java/com/ser/reqcheck/Cli.java`

## 3) Ambiguity evaluation results (from CLI report)

Run configuration:
- Threshold \(\tau\): **0.50**
- Evaluated rows: **5**
- Skipped rows: **10**

Confusion matrix:
- **TP = 1**
- **FP = 1**
- **FN = 0**
- **TN = 3**

Metrics:
- **Precision = 0.500**
- **Recall = 1.000**
- **F1 = 0.667**

## 3.1) Why TP/FP/FN/TN became 1/1/0/3 (ID-level breakdown)

Gold labels exist only for `R1`…`R5`. With \(\tau=0.50\), we predict “ambiguous” when `score >= 0.50`.

From `reports/report.md` (scores) and `data/gold_ambiguity.csv` (labels), the evaluated IDs are:

| ID | Score \(s\) | Pred \(\hat{y}\) with \(\tau=0.50\) | Gold \(y\) | Outcome |
|---|---:|---:|---:|---|
| R1 | 0.00 | 0 | 0 | TN |
| R2 | 0.00 | 0 | 0 | TN |
| R3 | 0.60 | 1 | 1 | TP |
| R4 | 0.55 | 1 | 0 | FP |
| R5 | 0.00 | 0 | 0 | TN |

So totals are:
- \(TP=1\) (only `R3`)
- \(FP=1\) (only `R4`)
- \(FN=0\) (no case where gold is 1 but prediction is 0)
- \(TN=3\) (`R1`, `R2`, `R5`)

Important note about this demo gold file:
- The evaluation matches by **ID**. If the text for an ID differs between the sample input and gold file, the metric still follows the gold label for that ID.

Manual check:
- \(P=1/(1+1)=0.5\)
- \(R=1/(1+0)=1.0\)
- \(F1=2\cdot0.5\cdot1.0/(0.5+1.0)=0.666…\approx 0.667\)

## 3.2) Interpretation (what these values mean)

- **Precision = 0.500**: among the items predicted as ambiguous, **half were correct** (1 correct / 2 predicted).
- **Recall = 1.000**: the system **did not miss** any gold-ambiguous item in this small gold set (1/1).
- **F1 = 0.667**: the harmonic mean balances the **high recall** with the **lower precision** caused by one false positive.

Practical takeaway:
- With \(\tau=0.50\), the system is slightly “aggressive” on this tiny gold set: it catches the ambiguous item, but also flags one non-ambiguous item as ambiguous.

Reliability note (important for presentation):
- The gold set here is **very small** (only 5 labeled rows). Metrics can change a lot if you add/remove a single labeled requirement.

## 3.3) Optional next step: threshold sweep (find best \(\tau\))

To make results more robust, we can run a sweep over \(\tau \in [0,1]\) (e.g., step 0.05) and report:
- the \(\tau^\*\) that maximizes F1
- the corresponding TP/FP/FN/TN and Precision/Recall/F1

## 4) System outputs (what the tool produced)

### 4.1 Ambiguity findings (ranked)

- Ambiguity rows produced: **15** (one per requirement in `sample_requirements.csv`)
- Each row includes:
  - `id`, `score`, `text`, and `reasons`

The full table is in:
- `reports/report.md` (English)
- `reports/report_tr.md` (Turkish)

### 4.2 Inconsistency candidates (pairwise)

- Conflict candidate rows produced: **131**
- Each candidate includes:
  - `leftId`, `rightId`, `similarity`, `kind`, `evidence`

Important interpretation note:
- These are **candidates** for human review (not a proven contradiction).
- Multiple rows can exist for the same pair (e.g., `numeric_conflict` + `high_similarity_review` for the same `(left,right)`).

## 5) Reproducibility command

```bash
mvn -q -DskipTests package
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.md --out-lang both --gold data/gold_ambiguity.csv --threshold 0.50"
```

