# Gereksinim Kalite Raporu

## Değerlendirme (Belirsizlik)

- Eşik (threshold; score'u 0/1 etikete çeviren sınır): 0.50
- Precision (tahmin edilen positive'ların ne kadarı doğru): 0.500
- Recall (gold positive'ların ne kadarı yakalandı): 1.000
- F1 (Precision ve Recall'un harmonik ortalaması): 0.667
- TP/FP/FN/TN: 1/1/0/3
- Değerlendirilen satır: 5 (atlanmış: 10)

### En iyi eşik (threshold sweep)

- En iyi eşik (F1'i ençoklayan τ*): 0.60
- Precision (tahmin edilen positive'ların ne kadarı doğru): 1.000
- Recall (gold positive'ların ne kadarı yakalandı): 1.000
- F1 (Precision ve Recall'un harmonik ortalaması): 1.000
- TP/FP/FN/TN: 1/0/0/4

### Top 5 eşik (F1'e göre)

| Eşik | Precision | Recall | F1 | TP/FP/FN/TN |
|---:|---:|---:|---:|---|
| 0.60 | 1.000 | 1.000 | 1.000 | 1/0/0/4 |
| 0.05 | 0.500 | 1.000 | 0.667 | 1/1/0/3 |
| 0.10 | 0.500 | 1.000 | 0.667 | 1/1/0/3 |
| 0.15 | 0.500 | 1.000 | 0.667 | 1/1/0/3 |
| 0.20 | 0.500 | 1.000 | 0.667 | 1/1/0/3 |

## Belirsizlik bulguları

| ID | Skor | Metin | Gerekçe |
|---|---:|---|---|
| R3 | 0.60 | The system should provide fast performance. | Optionality/weak modal verbs (e.g., may/should/could).; Vague quality adjectives (e.g., fast/intuitive/robust). |
| R4 | 0.55 | The system may log user activity as needed. | Optionality/weak modal verbs (e.g., may/should/could).; Open-ended temporal phrases (e.g., as needed/immediately). |
| R7 | 0.25 | The user interface shall be intuitive and easy to use. | Vague quality adjectives (e.g., fast/intuitive/robust). |
| R11 | 0.20 | The system shall notify users immediately when a security event occurs. | Open-ended temporal phrases (e.g., as needed/immediately). |
| R10 | 0.10 | Audit logs shall be stored for at least 90 days. | Possible passive voice (may hide actor). |
| R1 | 0.00 | The system shall respond to user requests within 2 seconds under normal load. | No common ambiguity cues detected. |
| R12 | 0.00 | The system shall notify users within 24 hours when a security event occurs. | No common ambiguity cues detected. |
| R13 | 0.00 | The system shall allow password reset via email. | No common ambiguity cues detected. |
| R14 | 0.00 | Password reset shall not be possible via email. | No common ambiguity cues detected. |
| R15 | 0.00 | The service shall be available 99.9% of the time. | No common ambiguity cues detected. |
| R2 | 0.00 | The system shall respond to user requests within 5 seconds under normal load. | No common ambiguity cues detected. |
| R5 | 0.00 | The application shall encrypt all sensitive data at rest using AES-256. | No common ambiguity cues detected. |
| R6 | 0.00 | Sensitive data at rest shall not be encrypted to reduce overhead. | No common ambiguity cues detected. |
| R8 | 0.00 | The system shall support multiple languages including English and Turkish. | No common ambiguity cues detected. |
| R9 | 0.00 | The system shall store audit logs for at least 30 days. | No common ambiguity cues detected. |

## Tutarsızlık adayları

| Sol | Sağ | Benzerlik | Tür | Kanıt |
|---|---|---:|---|---|
| R1 | R2 | 1.00 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R2 | 1.00 | numeric_conflict | Different numbers: [2s] vs [5s] |
| R11 | R12 | 0.96 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R13 | 0.96 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R9 | R10 | 0.95 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R9 | R10 | 0.95 | numeric_conflict | Different numbers: [30days] vs [90days] |
| R1 | R3 | 0.94 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R3 | 0.94 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R7 | R13 | 0.93 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R7 | 0.93 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R12 | 0.93 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R12 | 0.93 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R12 | 0.93 | numeric_conflict | Different numbers: [2s] vs [24hours] |
| R2 | R12 | 0.93 | numeric_conflict | Different numbers: [5s] vs [24hours] |
| R1 | R13 | 0.93 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R13 | 0.93 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R12 | R13 | 0.92 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R11 | 0.92 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R11 | R13 | 0.91 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R12 | 0.90 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R11 | 0.90 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R11 | 0.90 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R7 | 0.90 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R7 | 0.90 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R5 | R6 | 0.90 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R5 | R6 | 0.90 | negation_conflict | One contains negation, the other does not. |
| R7 | R11 | 0.89 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R10 | R15 | 0.89 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R10 | R15 | 0.89 | numeric_conflict | Different numbers: [90days] vs [99.9%] |
| R4 | R9 | 0.89 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R13 | R15 | 0.88 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R9 | R15 | 0.87 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R9 | R15 | 0.87 | numeric_conflict | Different numbers: [30days] vs [99.9%] |
| R3 | R4 | 0.87 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R13 | R14 | 0.87 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R13 | R14 | 0.87 | negation_conflict | One contains negation, the other does not. |
| R7 | R12 | 0.87 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R12 | R15 | 0.87 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R12 | R15 | 0.87 | numeric_conflict | Different numbers: [24hours] vs [99.9%] |
| R14 | R15 | 0.87 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R14 | R15 | 0.87 | negation_conflict | One contains negation, the other does not. |
| R7 | R14 | 0.86 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R7 | R14 | 0.86 | negation_conflict | One contains negation, the other does not. |
| R1 | R4 | 0.86 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R4 | 0.86 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R4 | R12 | 0.86 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R7 | R15 | 0.86 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R15 | 0.86 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R15 | 0.86 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R15 | 0.86 | numeric_conflict | Different numbers: [2s] vs [99.9%] |
| R2 | R15 | 0.86 | numeric_conflict | Different numbers: [5s] vs [99.9%] |
| R8 | R9 | 0.86 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R4 | R11 | 0.85 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R14 | 0.85 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R14 | 0.85 | negation_conflict | One contains negation, the other does not. |
| R1 | R14 | 0.85 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R14 | 0.85 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R14 | 0.85 | negation_conflict | One contains negation, the other does not. |
| R2 | R14 | 0.85 | negation_conflict | One contains negation, the other does not. |
| R5 | R13 | 0.85 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R9 | R12 | 0.85 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R9 | R12 | 0.85 | numeric_conflict | Different numbers: [30days] vs [24hours] |
| R4 | R7 | 0.84 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R4 | R13 | 0.84 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R11 | R15 | 0.82 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R6 | R14 | 0.82 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R5 | R15 | 0.81 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R9 | R11 | 0.81 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R9 | R13 | 0.80 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R15 | 0.80 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R5 | 0.79 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R5 | 0.79 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R5 | 0.79 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R10 | R14 | 0.79 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R10 | R14 | 0.79 | negation_conflict | One contains negation, the other does not. |
| R1 | R9 | 0.79 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R9 | 0.79 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R9 | 0.79 | numeric_conflict | Different numbers: [2s] vs [30days] |
| R2 | R9 | 0.79 | numeric_conflict | Different numbers: [5s] vs [30days] |
| R4 | R15 | 0.78 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R4 | R14 | 0.78 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R4 | R14 | 0.78 | negation_conflict | One contains negation, the other does not. |
| R6 | R13 | 0.78 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R6 | R13 | 0.78 | negation_conflict | One contains negation, the other does not. |
| R3 | R6 | 0.78 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R6 | 0.78 | negation_conflict | One contains negation, the other does not. |
| R5 | R14 | 0.78 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R5 | R14 | 0.78 | negation_conflict | One contains negation, the other does not. |
| R8 | R15 | 0.77 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R4 | R10 | 0.77 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R8 | R10 | 0.77 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R7 | R9 | 0.76 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R6 | R7 | 0.76 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R6 | R7 | 0.76 | negation_conflict | One contains negation, the other does not. |
| R4 | R8 | 0.76 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R9 | 0.75 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R6 | 0.75 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R6 | 0.75 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R6 | 0.75 | negation_conflict | One contains negation, the other does not. |
| R2 | R6 | 0.75 | negation_conflict | One contains negation, the other does not. |
| R5 | R7 | 0.75 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R5 | R12 | 0.75 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R9 | R14 | 0.74 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R9 | R14 | 0.74 | negation_conflict | One contains negation, the other does not. |
| R10 | R13 | 0.74 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R10 | R12 | 0.74 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R10 | R12 | 0.74 | numeric_conflict | Different numbers: [90days] vs [24hours] |
| R8 | R12 | 0.73 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R11 | R14 | 0.73 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R11 | R14 | 0.73 | negation_conflict | One contains negation, the other does not. |
| R12 | R14 | 0.73 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R12 | R14 | 0.73 | negation_conflict | One contains negation, the other does not. |
| R7 | R10 | 0.73 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R8 | R13 | 0.72 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R5 | R11 | 0.72 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R10 | 0.71 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R10 | 0.71 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R10 | 0.71 | numeric_conflict | Different numbers: [2s] vs [90days] |
| R2 | R10 | 0.71 | numeric_conflict | Different numbers: [5s] vs [90days] |
| R10 | R11 | 0.70 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R6 | R15 | 0.70 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R6 | R15 | 0.70 | negation_conflict | One contains negation, the other does not. |
| R7 | R8 | 0.68 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R3 | R10 | 0.67 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R1 | R8 | 0.66 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R2 | R8 | 0.66 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R8 | R11 | 0.65 | high_similarity_review | High textual similarity; review for redundancy/contradiction. |
| R6 | R11 | 0.63 | negation_conflict | One contains negation, the other does not. |
| R8 | R14 | 0.62 | negation_conflict | One contains negation, the other does not. |
| R6 | R12 | 0.61 | negation_conflict | One contains negation, the other does not. |
| R4 | R6 | 0.50 | negation_conflict | One contains negation, the other does not. |
