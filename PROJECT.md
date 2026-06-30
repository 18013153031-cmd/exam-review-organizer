# Exam Review Organizer

## Overview

Exam Review Organizer is a web application that helps students organize past exam questions by knowledge point rather than by paper.

The project is motivated by a real problem during exam preparation: after completing several past papers, students often realize that they are weak in specific concepts. The usual manual workflow is to take screenshots of related questions and reorganize them in note-taking software such as GoodNotes. This is repetitive and time-consuming.

This project automates the workflow of extracting, storing, tagging, and exporting questions.

The first version intentionally does **not** use AI. The user is responsible for deciding which knowledge point each question belongs to. The software focuses on reliable document processing, question organization, and PDF generation.

---

## MVP Goal

The MVP supports one complete workflow:

```text
Create Review Task
↓
Input Knowledge Points
↓
Upload PDF(s)
↓
Automatically Split Questions
↓
User Tags Questions
↓
Generate Organized PDF
```

---

## Target Users

Students preparing for university exams using multiple past papers.

---

## Functional Requirements

### 1. Review Task

A Review Task represents one revision session for one course.

Example:

```text
Course: Probability Theory and Statistics

Knowledge Points:
- Confidence Interval
- Hypothesis Testing
- Estimator
- Regression
- Central Limit Theorem
```

Knowledge points are entered manually by the user.

---

### 2. Upload Papers

Users can upload one or more PDF files.

When uploading a paper, the user must also specify the **year** of that past paper (e.g. 2023). This is entered manually by the user, not inferred from the file name or extracted from the PDF content, since the MVP does not perform OCR or semantic extraction.

The year is used to label questions during classification and in the final exported PDF, so the user can see which year each question came from (e.g. "Question 3 - 2023 Past Paper").

Only text-based PDFs are supported in the MVP.

Scanned PDFs are out of scope.

---

### 3. Question Extraction

The backend extracts text using Apache PDFBox.

The parser splits the paper into individual top-level questions.

Supported MVP format (numeric, period-separated, at the start of a line):

```text
1.
2.
3.
```

A top-level question and all of its sub-parts are treated as **one single question** and are **not** split further. Sub-parts are kept together as part of the same question's content.

Example of sub-parts that must stay together within one question:

```text
3.
(a) ...
(b) ...
(c) ...
```

The above is stored as a single `Question` record (question_number = 3), with the full text of (a), (b), and (c) included in `content_text`.

The parser identifies a new question only when it finds a new top-level numeric marker (e.g. `4.`) at the start of a line. Sub-part markers such as `(a)`, `(b)`, `a)`, `i.`, `ii.` etc. must NOT trigger a new question split — they are treated as part of the current question's body text.

Each extracted question is stored in the database as a single record, including all of its sub-parts.

The page number on which a question starts in the original PDF is also recorded (`page_number`). This is not used by any MVP feature, but is stored as low-cost groundwork for a possible future "trace back to original PDF" feature.

Advanced layouts, OCR, complex multi-column parsing, and image-region extraction are not part of the MVP.

---

### 4. Manual Classification

Questions are displayed one at a time.

The user selects one or more knowledge points for each question.

Multiple tags are allowed because one question may test several concepts.

Example:

```text
Question 12 - 2023 Past Paper

[x] Confidence Interval
[x] Estimator
[ ] Regression
[ ] Central Limit Theorem
```

---

### 5. Export

After questions have been tagged, the system generates a new PDF.

Questions are grouped by knowledge point.

Example output structure:

```text
Confidence Interval

Question 3 - 2023 Past Paper
...

Question 7 - 2022 Past Paper
...

-----------------------------

Hypothesis Testing

Question 2 - 2021 Past Paper
...

Question 5 - 2023 Past Paper
...
```

---

## Non-functional Requirements

The system should:

- support multiple uploaded PDFs
- preserve question order within each paper
- generate downloadable PDFs
- keep the architecture simple and maintainable
- expose a clear REST API between frontend and backend
- handle common user errors gracefully

Performance optimization is not part of the MVP.

---

## Out of Scope

The following features are intentionally excluded from the MVP:

- AI classification
- OCR
- scanned PDF support
- authentication
- user accounts
- search
- recommendation
- vector database
- cloud synchronization
- collaborative editing
- mobile application
- complex PDF layout reconstruction

---

## Known Limitations

These are known limitations of the MVP parser, documented so they are not mistaken for bugs:

- **Figures/tables placed away from their referencing question**: Some past papers place a figure or table (e.g. a regression output table) physically after a later question, with a caption indicating which question it actually belongs to (e.g. "Figure 1: ... for question 8"). The MVP parser does not detect or relocate this content — it will be included in the `content_text` of whichever question it is physically adjacent to in the PDF, not the question referenced in the caption. The user should be aware of this when reviewing/tagging affected questions, and may need to manually note the mismatch. See Future Work for a possible non-AI fix.

---

## Database Design

Entities:

- `ReviewTask`
- `KnowledgePoint`
- `Paper`
- `Question`
- `QuestionTag`

Relationship:

```text
ReviewTask
├── KnowledgePoint
└── Paper
    └── Question
        └── QuestionTag
            └── KnowledgePoint
```

A `ReviewTask` has many `KnowledgePoint` records.

A `ReviewTask` has many `Paper` records.

A `Paper` has many `Question` records.

A `Question` can have many `KnowledgePoint` tags.

A `KnowledgePoint` can be assigned to many `Question` records.

Therefore, `Question` and `KnowledgePoint` form a many-to-many relationship through `QuestionTag`.

---

## Suggested Tables

### review_tasks

```text
id
name
created_at
```

### knowledge_points

```text
id
review_task_id
name
order_index
```

### papers

```text
id
review_task_id
file_name
year
uploaded_at
```

### questions

```text
id
paper_id
question_number
content_text
page_number
order_index
```

### question_tags

```text
question_id
knowledge_point_id
```

---

## Backend Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Apache PDFBox

---

## Frontend Stack

- React
- JavaScript
- Vite

---

## Architecture

```text
Frontend
↓
REST API
↓
Spring Boot Backend
↓
PostgreSQL
```

The frontend should not directly access the database.

The backend owns all business logic related to PDF parsing, tagging, and export generation.

---

## Suggested API Workflow

```text
POST /api/review-tasks
Create review task with knowledge points

GET /api/review-tasks/{id}
Fetch review task details

POST /api/review-tasks/{id}/papers
Upload PDF (with year specified by the user) and extract questions

GET /api/papers/{id}/questions
Get extracted questions for a paper

GET /api/review-tasks/{id}/questions
Get all questions in a review task

PUT /api/questions/{id}/tags
Replace tags for a question

GET /api/review-tasks/{id}/grouped-questions
Get questions grouped by knowledge point

POST /api/review-tasks/{id}/export
Generate organized PDF
```

---

## MVP Success Criteria

The MVP is successful if the user can:

1. create a review task
2. enter knowledge points
3. upload a text-based past paper PDF
4. automatically extract questions
5. manually tag each question
6. export a new PDF grouped by knowledge point

---

## Future Work

Possible future extensions include:

- **Solution PDF matching**: allow the user to additionally upload a solution PDF for each past paper. The system extracts solutions using the same question-splitting logic as the question parser (top-level numeric markers, e.g. `1.`, `2.`), then matches each solution to its corresponding question by question number. The matched solution would be included alongside its question in the exported, knowledge-point-grouped PDF, so the user does not need to manually search the original past paper for the answer when self-marking.
  - Risk depends on solution PDF formatting: works well if question numbers in the solution PDF align with question numbers in the past paper; harder if solutions are unstructured (no clear numbering) or merged into long-form text.
  - Likely requires a new `solutions` table (or a `solution_text` field linked to `Question`) and a matching service that pairs solutions to questions by `paper_id` + `question_number`.
  - This is a self-contained feature on top of the MVP — not required for the MVP's core value proposition (organizing questions by knowledge point) to be complete.
- **Figure/table relocation by caption parsing**: detect captions matching a pattern like `Figure N: ... for question M` (or similar), extract the referenced figure/table content out of the question it is physically adjacent to, and reattach it to the correct question (`M`) instead. This is a rule-based text-pattern fix, not an AI/semantic feature, but it is non-trivial because it requires reliably separating "question body text" from "caption + figure block" during parsing, and handling cases where the caption format is inconsistent across papers. See Known Limitations above.
- AI-assisted classification
- slide parsing
- semantic search
- OCR
- scanned PDF support
- improved PDF layout preservation
- question difficulty tagging
- full-text search
- deployment to cloud hosting

These features are not part of the MVP.
