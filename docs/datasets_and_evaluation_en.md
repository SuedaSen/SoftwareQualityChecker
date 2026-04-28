# Datasets, Evaluation, and Reproducible Runs (Step-by-step)

This document explains—**from scratch**—how we go from a paper’s dataset to a runnable experiment in this repository, and how we generate **Precision/Recall/F1** reports.

## 0) What this repository can evaluate today

- **Ambiguity (single-requirement classification)**:
  - The tool outputs an ambiguity **score** per requirement (`0..1`) plus human-readable reasons.
  - We can evaluate it with a **gold** (labeled) dataset using **Precision / Recall / F1** by thresholding the score.

- **Inconsistency / conflict candidates (pairwise)**:
  - The tool outputs *candidate pairs* with similarity + evidence.
  - **F1 evaluation for conflicts is not implemented yet** because it requires a gold dataset that labels which pairs are truly conflicting (pair labels), not just single-line labels.

## 1) Overall workflow (same for every dataset)

### Step 1 — Find or obtain a dataset

For each paper:
- Check whether the dataset is **publicly downloadable**.
- If it is not public, decide whether to:
  - use a **public substitute dataset**, or
  - manually create a small gold set for demonstration.

### Step 2 — Convert the dataset to our gold format

For ambiguity evaluation we use a CSV with:

- `text` (**required**): the requirement sentence
- `ambiguous` (**required**): `true/false`, `1/0`, `yes/no`
- `id` (**optional but recommended**): unique row id (e.g., `R1`)

Example (`data/gold_ambiguity.csv`):

```csv
id,text,ambiguous
R1,"The system shall respond within 2 seconds under normal load.",false
R2,"The system should provide fast performance.",true
```

### Step 3 — Run the pipeline

We run the tool on an input dataset (CSV or TXT). The pipeline generates:
- ambiguity findings (score + reasons)
- conflict candidates (pairs + evidence)

### Step 4 — Run evaluation (F1) for ambiguity

We compute predicted labels using:

\[
\text{predicted\_ambiguous} = (\text{score} \ge \text{threshold})
\]

Then we compute TP/FP/FN/TN and report Precision / Recall / F1.

### Step 5 — Produce the report in English and Turkish

The CLI can output:
- English (`reports/report.md`)
- Turkish (`reports/report_tr.md`)
- both at once

## 2) Reproducible commands (CLI)

### 2.1 Build once

```bash
mvn -q -DskipTests package
```

### 2.2 Run analysis + F1 evaluation + bilingual Markdown reports

```bash
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.md --out-lang both --gold data/gold_ambiguity.csv --threshold 0.50"
```

Outputs:
- `reports/report.md` (English)
- `reports/report_tr.md` (Turkish)

## 3) Paper-by-paper dataset status (with links)

### Paper A — Wang et al. (2013): Automatic Detection of Ambiguous Terminology for Software Requirements

- **Paper PDF**: `https://www.eecis.udel.edu/~hfang/pubs/nldb13.pdf`
- **Datasets described**: 4 real-world SRS collections (PI, PII, PIII, PIV)
- **Public download**: not found (paper does not provide a direct dataset download link)
- **How we can run in this repo**:
  - Use a public substitute dataset (see Paper B / Paper D), or
  - Create a small gold set from your own requirements for demonstration

### Paper B — Mahbub et al. (2024): Can GPT-4 Aid in Detecting Ambiguities, Inconsistencies, and Incompleteness in Requirements Analysis?

- **MLV SRS repository**: `https://github.com/foselab/abz2024_casestudy_MLV`
- **Issues / discussions**: `https://github.com/foselab/abz2024_casestudy_MLV/issues`
- **Authors’ pipeline repo**: `https://github.com/Taslim-M/GPT4-Requirements-Analysis`
- **How we can run in this repo**:
  - Extract requirements from the SRS (PDF/TXT/CSV), then
  - Provide a labeled `gold_ambiguity.csv` (either from authors’ artifacts, or manually label a subset),
  - Run our CLI with `--gold` to compute F1

### Paper C — Brown (framework): Machine Learning Framework for Identifying Inconsistency in SRD

- **Paper PDF**: `https://www.micsymposium.org/mics2019/wp-content/uploads/2019/05/Framework__for_finding_inconsistency.pdf`
- **Public dataset**: not provided
- **How we can use it here**:
  - This paper is best referenced in the report as “method/architecture inspiration” rather than a runnable benchmark dataset.

### Paper D — Talha et al. (2025): A Semiautomated Approach for Detecting Ambiguities…

- **Paper DOI page**: `https://doi.org/10.1002/smr.70041`
- **Dataset described**: 425 functional requirements, 16 domains; labeled for ambiguity types (anaphoric / coordination / missing condition)
- **Dataset download**: the paper states “datasets and code are available in Appendix A”
- **How we can run in this repo**:
  - Download dataset from Appendix A (once we extract the exact link),
  - Convert to our `text,ambiguous` gold format,
  - Run CLI evaluation and generate bilingual report

### Paper E — Zhang & Ma (2023): Using ML for automated detection of ambiguity in building requirements

- **Paper PDF**: `https://ec-3.org/publications/conferences/EC32023/papers/EC32023_211.pdf`
- **Dataset described**: 464 labeled sentences from HBN 00-02 and HBN 00-03 (237 ambiguous / 227 unambiguous)
- **Public dataset download**: not found in the paper
- **How we can run in this repo**:
  - If labeled data is not published, we would need to recreate labels or choose another public dataset for F1 evaluation

## 4) Threshold selection (practical guidance)

- Start with `--threshold 0.50` (simple baseline).
- If you have a bigger gold dataset, sweep thresholds (e.g., 0.1..0.9) and pick the best F1.

## 5) ChatGPT “Explain with ChatGPT” UI feature (what it does)

In the Web UI tables:
- Each ambiguity `Reasons` cell includes an **Explain with ChatGPT** link.
- Each conflict `Evidence` cell includes an **Explain with ChatGPT** link.
- When clicked:
  - A prompt is copied to clipboard
  - ChatGPT is opened in a new tab: `https://chat.openai.com/`

The prompts are in English, and include:
- requirement id + text (+ reasons), or
- left/right ids + texts (+ similarity, kind, evidence)

