# SER Requirement Quality Checker (Java)

This project analyzes software requirements and highlights:
- **Ambiguity findings** (possibly unclear statements)
- **Inconsistency candidates** (possibly conflicting requirement pairs)

It includes both a **Web UI** and a **CLI** workflow.

Detailed project notes are available in `PROJE.md`.

## Why This Project Exists

Requirements are often written in natural language, which can introduce:
- vague wording,
- hidden contradictions,
- and inconsistent numeric constraints (e.g., different time limits).

This tool provides a first-pass quality check so teams can review risky requirements earlier.

## Core Capabilities

- Analyze plain text requirements (one per line)
- Upload requirement documents: `CSV`, `TXT`, `DOC`, `DOCX`, `PDF`
- Show ambiguity and inconsistency tables
- Export analysis results as CSV in the Web UI
- Use multilingual interface support (Turkish / English)

## Technical Glossary

- **Requirement**: A statement that describes what the system must/should do.
- **Ambiguity**: A requirement that can be interpreted in more than one way.
- **Inconsistency**: Two requirements that cannot both be true at the same time, or that strongly disagree.
- **Rule-based analysis**: Detection based on explicit patterns/rules (e.g., negation phrases).
- **Machine Learning (ML)**: In this project, ML is used for semantic similarity by comparing learned vector representations of requirement text.
- **Word Embedding**: A numeric vector for each word, learned from large text corpora so semantically related words are closer in vector space.
- **Sentence Embedding (average pooling)**: A sentence vector built by averaging its word vectors; used here as a lightweight semantic representation.
- **Cosine Similarity**: A value between 0 and 1 showing how close two vectors are by direction; higher means more semantically similar.
- **Semantic Similarity**: Similarity based on meaning, not just exact keyword overlap.
- **Similarity Matrix**: Pairwise similarity scores for all requirement pairs (`N x N`), used before conflict rules are applied.
- **Threshold-based filtering**: Only pairs above configured similarity thresholds are considered for conflict candidate generation.
- **TF-IDF**: Term Frequency–Inverse Document Frequency, a keyword-frequency method; used here as a non-ML fallback.
- **Embedding fallback strategy**: If embedding-based similarity is unavailable, the pipeline falls back to TF-IDF similarity.
- **Fallback**: Backup method used when the preferred method is unavailable.
- **Thymeleaf**: Server-side template engine used for rendering HTML pages in Spring Boot.
- **Locale**: Language/region context used for interface translations.

## Tech Stack

- **Java 17**
- **Spring Boot** (Web + MVC)
- **Thymeleaf** (UI templating)
- **OpenCSV** (CSV parsing)
- **Apache POI** (DOC/DOCX parsing)
- **Apache PDFBox** (PDF parsing)
- **Maven** (build/dependency management)

## Prerequisites

- **JDK 17+**
- **Maven 3.6+**

## Build

```bash
cd ser-java
mvn -q package
```

## Run Web UI

```bash
mvn spring-boot:run
```

Open: `http://localhost:8080`

## Run CLI

Generate a Markdown report:

```bash
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.md"
```

Generate a JSON report:

```bash
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.json --out-format json"
```

Create output directory first if needed:

```bash
mkdir -p reports
```

## Run as JAR

```bash
mvn -q package
java -jar target/reqcheck-1.0.0.jar
```

## Run with Docker (local)

Build the image:

```bash
docker build -t ser-requirement-checker .
```

Run the container:

```bash
docker run --rm -p 8080:8080 ser-requirement-checker
```

Open: `http://localhost:8080`

## Deploy on Render (Docker)

This repository includes a `Dockerfile`, so Render can deploy it without Java/Maven runtime configuration.

1. In Render, create a **New Web Service**
2. Connect your public GitHub repository
3. Select **Environment/Runtime: Docker**
4. Keep Root Directory as repository root (default)
5. Deploy

After deployment, open your `onrender.com` service URL.

Notes:
- Free instances may spin down on inactivity and can take ~30–60 seconds to wake up.
- First Docker build can take several minutes.

## Course Context

This project was prepared for the **SER515** course as a practical requirement quality analysis tool combining rule-based and ML-supported techniques.
It was developed collaboratively by **Sueda Sen** and **Mehmet Aksit**.
