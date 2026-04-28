# Experiment Report — Requirement Quality Checker (Ambiguity F1)

This report documents an end-to-end experiment for **ambiguity detection evaluation** in this repository, including **formulas**, **assumptions**, and **reproducible commands**.

> Scope: This experiment evaluates **ambiguity** as a *binary classification* task using a labeled gold file.  
> Out of scope (for now): pairwise **conflict** evaluation (needs a pair-labeled gold dataset).

---

## 1) Goal

Evaluate the tool’s ambiguity detection capability by computing:

- Precision
- Recall
- F1-score
- Confusion matrix (TP/FP/FN/TN)

We do this by comparing the model’s ambiguity scores against a **gold-labeled dataset**.

---

## 2) System under test

### 2.1 Ambiguity scoring

For each requirement sentence \(r\), the system computes:

- an ambiguity score \(s(r)\in[0,1]\)
- a set of human-readable reasons (e.g., weak modal verbs, vague adjectives)

The scoring is rule-based (heuristics) in `AmbiguityAnalyzer`.

### 2.2 From score to predicted label (thresholding)

We turn the score into a binary prediction with a threshold \(\tau\):

\[
\hat{y}(r) =
\begin{cases}
1 & \text{if } s(r)\ge\tau \\
0 & \text{otherwise}
\end{cases}
\]

Where:
- \(1\) means “ambiguous”
- \(0\) means “not ambiguous”

---

## 3) Dataset(s)

### 3.1 Gold dataset format

The gold dataset is a CSV with:

- `text` (required)
- `ambiguous` (required; accepted: `true/false`, `1/0`, `yes/no`)
- `id` (optional but recommended)

Example:

```csv
id,text,ambiguous
R1,"The system shall respond within 2 seconds under normal load.",false
R2,"The system should provide fast performance.",true
```

### 3.2 Gold dataset used in this repository (demo)

This repository includes a small demo gold file:

- `data/gold_ambiguity.csv`

Important: the sample input file used by the tool (`data/sample_requirements.csv`) contains **more rows** than the demo gold dataset.  
Evaluation is therefore done by **matching on `id`**, and any non-matching rows are **skipped**.

### 3.3 Dataset availability in the selected papers (and what we downloaded)

Many papers describe datasets but do **not** provide a downloadable, labeled gold file. Summary:

- **Wang 2013 (PI–PIV)**: datasets are described, but no download link was found (not public).
- **Brown (framework)**: no dataset link (method/framework description).
- **Zhang & Ma 2023 (HBN 464)**: labeling is described, but no labeled CSV download link is provided in the paper.
- **Talha 2025**: states “datasets + code in Appendix A”; the Appendix A download link must be extracted and verified.
- **Mahbub 2024 (MLV)**: dataset is public as SRS PDFs, but not provided as a labeled gold file; F1 requires labels.

#### Concrete download performed (Mahbub 2024 / MLV)

We downloaded and prepared the MLV SRS for use in this repository:

- **Downloaded SRS PDF (v1.5)**:
  - `data/external/mlv/Mechanical_Lung_Ventilator_1_5.pdf`
  - Source: `https://raw.githubusercontent.com/foselab/abz2024_casestudy_MLV/main/Mechanical_Lung_Ventilator%201_5.pdf`
- **Extracted requirements CSV (unlabeled)**:
  - `data/external/mlv/mlv_requirements_v1_5.csv`
  - Extractor: `src/main/java/com/ser/reqcheck/MlvRequirementsExtractor.java`

Important: This extracted file contains `id,text` only. To compute F1 on MLV, you must add an `ambiguous` label column (manual or semi-automatic gold creation).

---

## 4) Metrics (with formulas)

### 4.0 Why these formulas (what we are measuring)

In this experiment, ambiguity detection is treated as a **binary classification** problem:

- gold label \(y(r)\in\{0,1\}\)
- predicted label \(\hat{y}(r)\in\{0,1\}\) derived from a score threshold

Precision/Recall/F1 are the standard metrics for this setting because:
- **Precision** penalizes “over-flagging” (too many false alarms).
- **Recall** penalizes “missing” ambiguous requirements.
- **F1** is the harmonic mean that summarizes the trade-off between both.

### 4.1 Confusion matrix

For each evaluated requirement \(r\):

- **TP** (true positive): \(\hat{y}(r)=1\) and \(y(r)=1\)
- **FP** (false positive): \(\hat{y}(r)=1\) and \(y(r)=0\)
- **FN** (false negative): \(\hat{y}(r)=0\) and \(y(r)=1\)
- **TN** (true negative): \(\hat{y}(r)=0\) and \(y(r)=0\)

### 4.2 Precision

\[
\text{Precision} = \frac{TP}{TP+FP}
\]

Interpretation: “Among the requirements we flagged as ambiguous, how many were truly ambiguous?”

### 4.3 Recall

\[
\text{Recall} = \frac{TP}{TP+FN}
\]

Interpretation: “Among the truly ambiguous requirements, how many did we successfully flag?”

### 4.4 F1-score

\[
\text{F1} = \frac{2\cdot \text{Precision}\cdot \text{Recall}}{\text{Precision}+\text{Recall}}
\]

Interpretation: harmonic mean that balances precision and recall.

---

## 5) Experimental procedure (step-by-step)

1. **Load input requirements** from CSV/TXT.
2. **Run pipeline**:
   - compute ambiguity score \(s(r)\) and reasons for each requirement
   - compute conflict candidates (not evaluated in this experiment)
3. **Load gold dataset** from `--gold`.
4. **Match predicted rows to gold rows by id**:
   - if a prediction id does not exist in gold, skip it
5. For every evaluated requirement:
   - compute \(\hat{y}(r)\) using threshold \(\tau\)
   - update TP/FP/FN/TN
6. Compute Precision, Recall, F1 using the formulas above.
7. Write **bilingual** Markdown reports (EN/TR).

### 5.1 How counts were produced in code (TP/FP/FN/TN)

The implementation follows the definitions above exactly:

- The thresholding rule is implemented as:
  - `predAmb = p.score() >= threshold`
- Gold labels are loaded from CSV and matched **by id**.
- For each predicted row that has a matching gold label, counts are updated:
  - if `predAmb && goldAmb` → TP++
  - else if `predAmb` → FP++
  - else if `goldAmb` → FN++
  - else → TN++

Code locations:
- Evaluation loop: `src/main/java/com/ser/reqcheck/AmbiguityEvaluator.java`
- Metric formulas: `src/main/java/com/ser/reqcheck/EvaluationResult.java`
- CLI wiring (args → evaluation → report): `src/main/java/com/ser/reqcheck/Cli.java`

---

## 6) Reproducibility (exact commands)

### 6.1 Build

```bash
mvn -q -DskipTests package
```

### 6.2 Run experiment (analysis + evaluation + bilingual report)

```bash
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.md --out-lang both --gold data/gold_ambiguity.csv --threshold 0.50"
```

Outputs:
- `reports/report.md` (English)
- `reports/report_tr.md` (Turkish)

---

## 7) Results (example run)

From an example run on this repository’s `data/sample_requirements.csv` with:

- threshold \(\tau = 0.50\)
- gold file `data/gold_ambiguity.csv`

The report produced:

- Precision = 0.500
- Recall = 1.000
- F1 = 0.667

Confusion matrix counts:

- TP = 1
- FP = 1
- FN = 0
- TN = 3

Also:

- Evaluated rows = 5
- Skipped rows = 10 (present in input but not present in gold by `id`)

### 7.1 Manual verification of F1 from counts

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

## 8) Threats to validity / limitations

- **Small gold set**: the included gold file is only a demo; results are not statistically meaningful.
- **Threshold sensitivity**: F1 depends on \(\tau\). A threshold sweep is recommended for larger gold datasets.
- **Label definition mismatch**: “ambiguous” in a paper dataset may differ from this tool’s ambiguity definition (heuristic cues).
- **Pairwise conflicts not evaluated**: F1 for inconsistency candidates requires a different gold format (pair labels).

---

## 9) Next steps (recommended improvements)

- Add a real, larger gold dataset (e.g., Talha et al. 2025, if Appendix A provides a downloadable file).
- Add an automatic **threshold sweep** command to find \(\tau\) that maximizes F1.
- Add **pair-labeled** gold format for conflict evaluation (precision/recall/F1 for conflicts).

