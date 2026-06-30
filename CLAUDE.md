# Claude Code Development Guide

This document describes how Claude Code should assist with the development of this project.

---

## Project Goal

The objective is not to generate as much code as possible.

The objective is to build a clean, maintainable MVP while helping the developer understand the implementation.

This is a learning-focused software engineering project and a future SDE internship resume project.

---

## Product Context

@PROJECT.md

The full product requirements are imported above. Only implement features that directly support the MVP described there unless the developer explicitly asks otherwise.

---

## Development Plan & Progress

@PLAN.md

The full 15-day development plan and progress log are imported above. Use this to understand which day/stage the project is currently on, what has already been completed, and what the next planned step is. When the developer asks to continue work, check the Progress Log table first to see the latest recorded status before proposing next steps.

---

## General Principles

Always prioritize:

1. simplicity
2. readability
3. maintainability
4. incremental development
5. clear explanations

Avoid unnecessary abstractions.

Avoid over-engineering.

Avoid introducing technologies that are not required by the MVP.

---

## MVP First

Do not automatically add features outside the MVP.

Do not add these unless explicitly requested:

- authentication
- user accounts
- Docker
- Redis
- Kafka
- RabbitMQ
- Elasticsearch
- AI integration
- OCR
- caching layer
- WebSocket
- GraphQL
- microservices
- vector database

The first goal is a working local MVP.

---

## Development Workflow

Work incrementally.

Never implement multiple major features in one step.

Before making a large change:

1. explain the implementation plan
2. list the files that will be changed or created
3. explain any trade-offs
4. wait for confirmation if the change is significant

After implementation:

1. summarize what changed
2. explain why the design was chosen
3. mention how to test it
4. suggest the next logical step

---

## Teaching Mode

Assume the developer is learning.

When implementing important concepts, explain:

- why this design is used
- what alternatives exist
- what the trade-offs are
- what could go wrong

Do not simply generate code without explanation.

---

## Code Ownership Tiers

Not all parts of this project should be implemented the same way. Treat code in three tiers, and default to the matching behavior unless the developer says otherwise:

**Tier 1 — Implement directly (low-risk, repetitive, standard patterns):**
- Basic CRUD endpoints (e.g. knowledge point create/read/update/delete)
- Basic frontend page structure and styling
- For this tier: write the code directly, then briefly explain what it does and why. The developer wants to understand it, not necessarily write it themselves.

**Tier 2 — Explain first, let the developer write it, then review (core technical points this project is meant to demonstrate):**
- PDF question-splitting / parsing logic (regex design, edge case handling)
- Database relationship modeling and grouped/aggregate queries (e.g. many-to-many tagging, "questions grouped by knowledge point")
- For this tier: do NOT generate the full implementation immediately. First explain the approach and the reasoning options. Wait for the developer to attempt their own version (or explicitly ask you to write it) before providing code. When the developer shares their attempt, review it and explain what's correct, what could be improved, and why — rather than silently replacing it with your own version.

**Tier 3 — Developer decides, Claude Code only implements to spec (architecture/design decisions):**
- Schema design choices, what fields to add, overall data model shape
- These decisions should already be made by the developer (in PROJECT.md) before implementation begins. Do not unilaterally change schema design or architecture choices during implementation — if something seems wrong or could be improved, raise it as a question rather than silently changing it.

If unsure which tier a task falls into, ask the developer rather than assuming Tier 1.

---

## Architecture Rules

Use a layered architecture:

```text
Controller
↓
Service
↓
Repository
↓
Database
```

Controllers should stay thin.

Business logic belongs in services.

Repositories should only handle data access.

PDF parsing and PDF export logic should live in dedicated service classes, not inside controllers.

---

## Backend Code Style

Prefer:

- constructor injection
- small service methods
- descriptive names
- DTOs for API input/output when useful
- clear validation
- meaningful exceptions
- Spring Boot conventions

Avoid:

- giant service classes
- duplicated logic
- unnecessary inheritance
- business logic inside controllers
- exposing JPA entities directly if it creates messy API responses

---

## Frontend Code Style

Use:

- React
- JavaScript
- Vite
- simple component structure
- clear API client functions

Avoid complex state management libraries in the MVP.

Do not introduce Redux, Zustand, or similar tools unless explicitly requested.

Simple local state and small helper functions are preferred.

---

## Dependency Rules

Current stack:

Backend:

- Spring Boot
- Spring Data JPA
- PostgreSQL
- Apache PDFBox

Frontend:

- React
- JavaScript
- Vite

Do not introduce additional frameworks unless there is a clear reason and the developer agrees.

---

## Git Guidelines

Keep commits small and meaningful.

Suggested commit message examples:

```text
feat: create review task entity
feat: add knowledge point CRUD
feat: implement PDF upload endpoint
feat: add question parser
feat: build tagging API
feat: export grouped questions as PDF
fix: preserve question order
refactor: simplify parser service
test: add parser unit tests
docs: add setup instructions
```

Do not make one giant commit containing many unrelated features.

---

## Testing Guidelines

Add tests for important logic.

Prioritize tests for:

- question splitting
- tagging behavior
- export grouping
- service methods
- repository integration where useful

PDF parser tests are especially important because this is a core technical feature.

---

## PDF Parser Scope

For the MVP, only support text-based PDFs.

Supported top-level question marker (numeric, period-separated, at the start of a line):

```text
1.
2.
3.
```

A question and all of its sub-parts (e.g. `(a)`, `(b)`, `(c)`) must be treated as a single `Question` record. Do not split on sub-part markers. Only a new top-level numeric marker (e.g. `4.`) starts a new question.

Do not implement OCR.

Do not attempt to perfectly reconstruct visual PDF layout.

Keep the first parser simple and testable. When writing parser tests, include test cases for multi-part questions (a/b/c) to confirm they are NOT split into separate questions.

---

## If Unsure

Never guess project requirements.

Ask questions before making architectural decisions.

If there are multiple reasonable approaches, explain them and recommend one.

---

## Success Criteria

Success is defined by:

- a working MVP
- clean architecture
- understandable code
- meaningful tests
- the developer understanding the implementation

Speed is useful, but software quality and understanding are more important.
