# 15-Day Development Plan: Past Paper Knowledge-Point Classifier (Phase 1 MVP)

> Goal: Complete the manual-classification version (no AI involved) within 15 days, fully working end-to-end and deployed.
> Background context: see CLAUDE.md.

## Overall Assessment

- Backend stack (Java/Spring Boot) is already familiar — lower risk, can move faster
- PDF processing (Apache PDFBox) is a completely new technical area — highest risk, 3 days buffered
- React frontend hands-on experience is uncertain — extra buffer included
- 15 days should be sufficient for current scope, with roughly 1 day of slack

---

## Day 1: Environment & Scaffolding

- [x] Install Claude Code, complete login/authorization
- [x] Create GitHub repository, clone locally
- [x] Add CLAUDE.md, PROJECT.md, and PLAN.md to the project root
- [x] Generate Spring Boot project skeleton via Spring Initializr (dependencies: Web, JPA, PostgreSQL Driver)
- [x] Install/configure PostgreSQL locally, verify app startup + DB connection
- [x] Create React project skeleton (Vite + JavaScript)
- [x] First commit + push (`feat: project scaffolding`)

---

## Day 2-3: Database & Entities + Core CRUD APIs

- [ ] Write JPA Entity classes per schema:
  - `review_tasks` (id, name, created_at)
  - `knowledge_points` (id, review_task_id, name, order_index)
  - `papers` (id, review_task_id, file_name, uploaded_at)
  - `questions` (id, paper_id, question_number, content_text, image_path, order_index)
  - `question_tags` (question_id, knowledge_point_id — many-to-many join table)
- [ ] CRUD endpoints for knowledge points
- [ ] Endpoint to tag a question with knowledge point(s)
- [ ] Endpoint to query all questions linked to a given knowledge point
- [ ] Test all endpoints with Postman
- [ ] Commit (`feat: core CRUD APIs`)

---

## Day 4-6: PDF Question-Splitting (Highest-Risk Stage)

- [ ] Extract PDF text using Apache PDFBox
- [ ] Write regex-based question-splitting logic (detect top-level numeric markers like `1.`, `2.`, `3.` at the start of a line)
- [ ] Ensure sub-parts (a)/(b)/(c) are NOT split into separate questions — they must stay within the same top-level question's content
- [ ] Test against real past papers, handle edge cases:
  - Questions spanning multiple pages
  - Multi-part questions with (a)/(b)/(c) sub-parts (must remain as one Question record)
  - Inconsistent numbering formats
- [ ] Build "upload PDF → trigger splitting → save to DB" API endpoint
- [ ] Build PDF export endpoint (group questions by knowledge point into a new PDF)
- [ ] Test with Postman
- [ ] Commit (`feat: PDF question splitting`, `feat: PDF export by knowledge point`)

---

## Day 7-8: React Frontend — Create Task Page + Upload Page

- [ ] Create-task page: enter task name, add knowledge points one by one
- [ ] Upload page: upload one or more PDFs, call backend splitting API
- [ ] Run and verify in browser, fix basic errors
- [ ] Commit

---

## Day 9-11: React Frontend — Question Classification Page (Core, Most Complex)

- [ ] Single-question display component (text content / question image)
- [ ] Knowledge point selector component (multi-select supported)
- [ ] "Next question" logic, handling sequential display across multiple papers
- [ ] Wire up tagging API, confirm tags are saved correctly
- [ ] Commit

---

## Day 12: React Frontend — Completion Page + Wrap-up

- [ ] Completion page: show processing summary ("X questions processed")
- [ ] "Generate PDF" button, calls export endpoint, provides download link
- [ ] Full frontend walkthrough, fix obvious issues
- [ ] Commit

---

## Day 13: Frontend-Backend Integration Testing

- [ ] Run the full flow end-to-end: create task → upload → classify questions → generate & download PDF
- [ ] Fix bugs found during integration
- [ ] Commit

---

## Day 14: Deployment

- [ ] Deploy backend to Render (including database)
- [ ] Deploy frontend to Vercel, configure connection to backend
- [ ] Test the full flow on the live deployment
- [ ] Commit (any deployment-related config changes)

---

## Day 15: Documentation & Wrap-up

- [ ] Write README (project overview, tech stack, architecture diagram, how to run locally, live link)
- [ ] Record a short demo video/screenshots (showing "upload PDF → get classified PDF" effect)
- [ ] Update the "Interview Talking Points" section in CLAUDE.md, covering:
  - Project motivation
  - Key design decisions (why Phase 1 avoids AI)
  - Edge cases hit during PDF splitting
  - Many-to-many relationship modeling approach
- [ ] Final commit + push

---

## Progress Log (update continuously during development)

> Used to track actual progress, issues encountered, and solutions — useful later for assembling interview stories.

| Day | Planned | Actual Progress | Issues / Notes |
|-----|---------|------------------|-----------------|
| 1   | Env & scaffolding | Spring Boot (Java 21, Web/JPA/PostgreSQL) and React+Vite skeletons created; local PostgreSQL 15 confirmed running, `exam_review_organizer` DB created; backend verified to connect via Hikari; committed as `3b27fb7` | PostgreSQL 15 was already installed but not on PATH and had no project DB yet; local trust-auth on 127.0.0.1 means no password needed for dev |
| 2-3 |         |                  |                 |
| 4-6 |         |                  |                 |
| 7-8 |         |                  |                 |
| 9-11|         |                  |                 |
| 12  |         |                  |                 |
| 13  |         |                  |                 |
| 14  |         |                  |                 |
| 15  |         |                  |                 |
